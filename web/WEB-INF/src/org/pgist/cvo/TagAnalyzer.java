package org.pgist.cvo;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.pgist.tag.Tag;
import org.pgist.tag.TagDAO;

/**
 *
 * <p>
 * Title: Tag analysis for text information
 * </p>
 *
 * <p>
 * Description: This class provides the function of extracting tags based on tag
 * database and suggesting new tags based on nautral language processing
 * </p>
 *
 * @author Guirong
 * @version 1.0
 */
public class TagAnalyzer {

	private TagDAO tagDAO = null;
	private StopWordDAO stopWordDAO = null;

	public void setTagDAO(TagDAO tagDAO) {
		this.tagDAO = tagDAO;
	}

	public void setStopWordDAO(StopWordDAO stopDAO) {
		this.stopWordDAO = stopDAO;
	}

	/*
	 * ------------------------------------------------------------------------
	 */

	private static List all_tags = null;

	/**
	 * tt is a "trie" data structure to promote efficient retrieval of existing
	 * tags. tt[i][0] - the char tt[i][1] - index of the left child tt[i][2] -
	 * index of the right child tt[i][3] - ID of the tag, or -1 if it's not the
	 * end of a tag
	 */
	private long[][] tag_tree = null;

	private Set stop_words = null;

	public Map parseTextTokenized(String statement) {
            Map map = new HashMap();
            if (all_tags == null) {
			rebuildTree();
            }

		Collection suggestedStrings = new HashSet();
                Collection existingTags = new HashSet();

		long[][] tag_id_count = new long[all_tags.size()][2];
		for (int k = 0; k < tag_id_count.length; k++) {
			tag_id_count[k][0] = ((Tag) all_tags.get(k)).getId();
			tag_id_count[k][1] = 0;
		}
                int newtagnumber = 0;

		Reader reader = new StringReader(statement.toLowerCase());
		Tokenizer tkz = new StandardTokenizer(reader); //LowerCaseTokenizer

		try {
			Token t = tkz.next();
			while (t != null) {
				//find the logest appearance
				String foundtext = "";
				String trytext = t.termText();
				boolean found = false;
				long[] setting = { 0, -1 };
				int m = matchString(tag_tree, 0, trytext, setting);
				while (m == (foundtext.length() + trytext.length())) {
					found = true;
					foundtext += trytext;

					t = tkz.next();
					if (t == null)
						break;

					trytext = " " + t.termText();

					if (setting[0] >= 0)
						m += matchString(tag_tree, (int) setting[0], trytext,
								setting);
					else
						break;
				}

				if (foundtext.length() > 0) {
					if (setting[1] >= 0) {
						tag_id_count[(int) setting[1]][1]++;
						setting[1] = -1;
					}
				}

				if (!found){
					if(!stop_words.contains(t.termText().toLowerCase())){
                                            suggestedStrings.add(t.termText());
                                            newtagnumber ++;
                                        }
					t = tkz.next();
				}
			}//while

			tkz.close();
		} catch (Exception e) {
			System.out
					.println("error in parseTextTokenized: " + e.getMessage());
			e.printStackTrace();
		}

		//put all the found tags in the suggestedStrings
		for (int k = 0; k < tag_id_count.length; k++)
			if (tag_id_count[k][1] > 0) {
				existingTags.add(((Tag) all_tags.get(k)).getName());
			}

                map.put("tags", existingTags);
                map.put("potentialtags", suggestedStrings);

		return map;
	}//parseTextTokenized()

        public Map parseText(String statement) {
            Map parseResults = new HashMap();
            Collection suggestedStrings = new HashSet();

            long[][] tag_id_count = new long[all_tags.size()][2];
            for (int k = 0; k < tag_id_count.length; k++) {
                    tag_id_count[k][0] = ((Tag) all_tags.get(k)).getId();
                    tag_id_count[k][1] = 0;
            }

            StringBuffer inText = new StringBuffer(statement.toLowerCase());
            int cursorText = 0;
            int nextStart = 0;
            while(cursorText < inText.length()){
                long foundTag = -1;
                int cursorTree = 1;
                while(cursorTree >= 0){
                    if(tag_tree[cursorTree][0] == inText.charAt(cursorText)){
                        if (tag_tree[cursorTree][3] > 0) {
                            foundTag = tag_tree[cursorTree][3];
                            nextStart = cursorText;
                        }

                        cursorTree = (int) tag_tree[cursorTree][1];
                        cursorText++;
                    }else{
                        cursorTree = (int) tag_tree[cursorTree][2];
                    }
                }

                //do something with foundTag
                if(foundTag > 0){
                    tag_id_count[(int) foundTag][1]++;
                    cursorText = nextStart + 1;
                }

                //to avoid getting partial word
                while(inText.charAt(cursorText) >= 'a' &&
                            inText.charAt(cursorText) <= 'Z' )
                          cursorText++;

            }

            return parseResults;
        }
	/**
	 * This method recursively finds the longest matched part of a string
	 * against the tree, starting from a given position in the tree.
	 *
	 * @param tree
	 *            long[][]
	 * @param start
	 *            int
	 * @param s
	 *            String
	 * @param settings
	 *            long[]
	 * @return int
	 */
	private int matchString(long[][] tree, int start, String s, long[] settings) {
		if (s.length() == 0)
			return 0;

		if (tree[start][0] == s.charAt(0)) {
			settings[0] = tree[start][1]; // remember the location

			if (tree[start][3] >= 0)
				settings[1] = tree[start][3];

			if (tree[start][1] > 0) {
				return (1 + this.matchString(tree, (int) tree[start][1], s
						.substring(1), settings));
			} else
				return 1;
		} else {
			if (tree[start][2] > 0) {
				return this
						.matchString(tree, (int) tree[start][2], s, settings);
			}
		}

		return 0;
	}

	/**
	 * Add a given node to the tree.
	 *
	 * @param tree
	 *            long[][]
	 * @param tag
	 *            Tag
	 */
	public synchronized void addTag(Tag tag) {
		if(all_tags == null) rebuildTree();

		try {
			//tagDAO.save(tag);
			all_tags.add(tag);
			System.out.println("++++ add new tag: " + tag.getName() );
			System.out.println("---- buffer usage: " + + tag_tree[0][3] + "/" + tag_tree.length);
			if (tag_tree.length > (tag_tree[0][3] + tag.getName().length())) {
				addNode(tag_tree, tag.getName(), all_tags.size() - 1, 0, 1, 1);
			} else {
				System.out.println("++++ buffer full (" + tag_tree[0][3] + "/" + tag_tree.length
						+ "need to rebuild tree ++++");
				rebuildTree();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Tag tagExists(String tagName){
		if( tagName == null )
			return null;

		if( "".equals(tagName) )
			return null;

		if( all_tags == null )
			rebuildTree();

		long[] setting = { 0, -1 };
		String str = tagName.trim().toLowerCase();
		int m = matchString(tag_tree, 0, str, setting);
		if( m == str.length() && setting[1] > 0)
			return (Tag)all_tags.get((int) setting[1]);
		else
			return null;
	}

	/**
	 * Get data form the tag database, and build or rebuild the tree. It loops
	 * through all the tags and call addNode() to add all tags into the tree.
	 * The result of this method will be a refreshed tag_tree
	 */
	public void rebuildTree() {

		List all_tags_temp = new ArrayList();
		long[][] tag_tree_temp;

		try {
			Collection tags = tagDAO.getAllTags();
			stop_words = loadStopWords();
			System.out.println("====start to build tree, with " + tags.size()
					+ " tags.");

			int size = 0;
			int i;
			String s = "";

			for (Iterator itr = tags.iterator(); itr.hasNext();) {
				Tag tag = (Tag) itr.next();
				s = tag.getName();
				size += s.length();
				all_tags_temp.add(tag);
			}

			size += 27;

			tag_tree_temp = new long[size][4];

			for (i = 0; i < tag_tree_temp.length; i++)
				for (int j = 0; j < tag_tree_temp[i].length; j++)
					tag_tree_temp[i][j] = -1;

			for (i = 0; i <= 26; i++) {
				tag_tree_temp[i][0] = 'a' - 1 + i;
				tag_tree_temp[i][1] = tag_tree_temp[i][3] = -1;
				tag_tree_temp[i][2] = i + 1;
			}
			tag_tree_temp[0][0] = '$';
			tag_tree_temp[0][3] = 27; //the first available locaiton
			tag_tree_temp[26][2] = -1;

			for (i = 0; i < all_tags_temp.size(); i++) {
				s = ((Tag) all_tags_temp.get(i)).getName().toLowerCase();
				this.addNode(tag_tree_temp, s, i, //instead remember the ID(( (Term)list.get(i)).getId())
						0, 1, 1); //do the index in the list
				System.out.println("--added: " + s);
			}

			tag_tree = tag_tree_temp;
			all_tags = all_tags_temp;

			// printTreeNice(tag_tree, 0, "");
			System.out.println("==>>tag tree size: " + tag_tree_temp.length
					+ "; used: " + tag_tree_temp[0][3]);

		} catch (Exception ex) {
			System.out.println("==Error in TagAnalyzer.rebuildTree: "
					+ ex.getMessage());
		}

	}

	private static void printTreeNice(long[][] t, int start, String firstpart) {
		if (t[start][3] >= 0) {
			System.out.println(firstpart + (char) t[start][0]);
		}

		if (t[start][1] >= 0) {
			printTreeNice(t, (int) t[start][1], firstpart + (char) t[start][0]);
		}

		if (t[start][2] >= 0) {
			printTreeNice(t, (int) t[start][2], firstpart);
		}
	}

	/**
	 *
	 * @param tree
	 *            long[][] - tags as stored in an array.
	 * @param tag
	 *            String - a new tag to insert into the tag tree.
	 * @param tagid
	 *            long - the ID of the tag, for fast access.
	 * @param parent
	 *            int - the parent position of the node.
	 * @param current
	 *            int - the position of the current node.
	 * @param child
	 *            int - indicate to put the node at the left or right. 1 = left;
	 *            2 = right
	 */
	private void addNode(long[][] tree, String tag, long tagindex, int parent,
			int current, int child) {
		if (tag.length() == 0) {
			tree[parent][3] = tagindex; //this cell is used to keep the availale position
			return;
		}

		if (current == -1) {
			tree[(int) tree[0][3]][0] = tag.charAt(0); //this means to use a new record
			tree[parent][child] = tree[0][3]; //set the child to the new record position
			tree[0][3]++; //move the available position
			this.addNode(tree, tag.substring(1), tagindex,
					(int) tree[0][3] - 1, -1, 1);
			return;
		}

		if (tree[current][0] == tag.charAt(0)) {
			this.addNode(tree, tag.substring(1), tagindex, current,
					(int) tree[current][1], 1);
		} else {
			this.addNode(tree, tag, tagindex, current, (int) tree[current][2],
					2);
		}
	}

        /**
         *
         * This method is for internal use. It reads the database into the memory
         * @return Set
         */
        private Set loadStopWords(){
        Set stopwords = new TreeSet();
                try {
                    List list = stopWordDAO.getAllStopWords();

                    for (int i = 0; i < list.size(); i++) {
                        String sw = ((StopWord) list.get(i)).getName().
                                    toLowerCase().trim();
                        if ("".compareTo(sw) == 0) {
                            continue;
                        }

                        if (!stopwords.contains(sw)) {
                            stopwords.add(sw);
                            System.out.println(">>>>added stop word: " + sw);
                        }

                    }
                } catch (Exception ex) {
                }

		return stopwords;
	}

        /**
         * Add a stop word represented by a string into the memory only. No database
         * operation is performed.
         * @param sw String
         */
        public synchronized void addStopWord(String sw){
            if (stop_words == null)
                stop_words = new TreeSet();

            if (!stop_words.contains(sw)) {

                stop_words.add(sw);
            }
	}

        /**
         * Remove the given stop word from memeory storage.
         * @param sw String
         */
        public synchronized void deleteStopWord(Long id){
            if (stop_words == null)
                return;
            StopWord sw = null;
            try {
                sw = stopWordDAO.getStopWordById(id);
                if (!stop_words.contains(sw.getName())) {
                    stop_words.remove(sw.getName());
                }
            } catch (Exception ex) {
            }
        }

        /**
         * To see if a string exists in the memory as a stop word.
         * @param sw String
         * @return boolean
         */
        public boolean stopWordExits(String sw){
        if( stop_words == null ) return false;
		return( stop_words.contains( sw ) );
	}

    /**
     * Remove a tag from the tag list.
     *
     */
    public void removeTag(Tag tag) {
        // TODO To be finished by Gruiong
		// this method is not necessary, as this calss is not a "tag manager"
    }//removeTag()


}//class TagAnalyzer

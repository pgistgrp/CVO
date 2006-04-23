package org.pgist.cvo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import org.dom4j.Element;
import org.dom4j.DocumentHelper;
import org.dom4j.XPath;
import java.util.Set;
import java.util.HashSet;
import java.net.URL;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import java.io.StringReader;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import org.dom4j.*;
import java.io.*;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.LowerCaseTokenizer;

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

    public void setTagDAO(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
        //rebuildTree();
        System.out.println("in the setter");
    }


    /*
     * ------------------------------------------------------------------------
     */

    private static List all_tags = new ArrayList();

    /**
     * tt is a "trie" data structure to promote efficient retrieval of existing
     * tags. tt[i][0] - the char tt[i][1] - index of the left child tt[i][2] -
     * index of the right child tt[i][3] - ID of the tag, or -1 if it's not the
     * end of a tag
     */
    private long[][] tag_tree = null;

    /**
     * term_id_count is a useful data structure to index the found results: id
     * as the array index allows fast retrieval of a term, and count records the
     * number of appearance.
     */
    private long[] tag_id_count;

    public static Document parse(URL url) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }

    public static Document parse(String s) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new StringReader(s));

        return document;
    }

    /**
     * Extract existing tags for the given text.
     *
     * @param statement
     *            String
     * @return Map
     *         <li>map.get("found")=list of tags existing in tag database</li>
     *         <li>map.get("suggested")=list of strings as suggested tags</li>
     *
     */
    public Collection parseTextNLP(String statement) {
      Collection suggestedStrings = new HashSet();
      long[][] tag_id_count_temp = new long[tag_id_count.length][2];
      for(int k=0; k<tag_id_count.length; k++){
      	tag_id_count_temp[k][0] = tag_id_count[k]; 
      	tag_id_count_temp[k][1] = 0;
        }

      String line = null;
      String output = "";

      List results = null;
      try {
        Process process = Runtime.getRuntime().exec("c:/nlp/bin/nlp.cmd -xml");
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(process.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
            process.getOutputStream()));
        writer.write(statement);
        writer.flush();
        writer.close();

        while ( (line = reader.readLine()) != null) {
          output += line;
        }

        //do NLP parse on the statement and get a list of pharases
        XPath xpathSelector = DocumentHelper.createXPath(
            "//NG[count(W[@C='NN'])>0 or count(W[@C='NNP'])>0 or count(W[@C='NNS'])>0]");
        results = xpathSelector.selectNodes(parse(output));
      }
      catch (DocumentException ex) {
        return suggestedStrings;
      }
      catch (InvalidXPathException ex) {
        return suggestedStrings;
      }
      catch (IOException ex) {
        return suggestedStrings;
      }

      for ( Iterator iter = results.iterator(); iter.hasNext(); ) {
        Element element = (Element) iter.next();
        List l = element.elements();

        boolean suggestNew = true;

        for(int i=0; i<l.size();){

          String foundtext = "";
          String trytext = ((Element)l.get(i)).getText().toLowerCase();
          boolean found = false;
          long[] setting = {0, -1};
          int m = matchString(tag_tree, 0, trytext, setting);
          while( m == (foundtext.length() + trytext.length() ) ){
            found = true;
            foundtext += trytext;

            i++;
            if(i >= l.size())break;

            trytext = " " + ((Element)l.get(i)).getText().toLowerCase();

            if(setting[0] >=0 ) //
              m += matchString(tag_tree, (int)setting[0], trytext, setting);
            else
              break;
          }

          if(foundtext.length() > 0){
            if(setting[1] >= 0){ //
            	tag_id_count_temp[(int)setting[1]][1]++;
               setting[1] = -1;
               //System.out.println(foundtext);

               suggestNew = false;
            }
          }

          if(!found)
            i++;

        }

        if(suggestNew){
            suggestedStrings.add( makeSuggest(l));
        }
      }

      //put all the found tags in the suggestedStrings
      for(int k=0; k<tag_id_count_temp.length; k++)
        if(tag_id_count_temp[k][1] > 0){
          suggestedStrings.add( ">>" + ((Tag)all_tags.get(k)).getName() );
        }

      return suggestedStrings;
    }

    public Collection parseTextTokenized(String statement) {
        if(all_tags.size() == 0){
        	rebuildTree();
        	printTreeNice(tag_tree, 0, "");
        }
    	
        Collection suggestedStrings = new HashSet();
         //cleat out the parse results
        long[][] tag_id_count_temp = new long[tag_id_count.length][2];
        for(int k=0; k<tag_id_count.length; k++){
        	tag_id_count_temp[k][0] = tag_id_count[k]; 
        	tag_id_count_temp[k][1] = 0;
          }

        Reader reader = new StringReader(statement);
        Tokenizer tkz = new LowerCaseTokenizer(reader);	//StandardTokenizer

        try{
          Token t = tkz.next();
          while (t != null){
            //find the logest appearance
            String foundtext = "";
            String trytext = t.termText();
            boolean found = false;
            long[] setting = {0, -1};
            int m = matchString(tag_tree, 0, trytext, setting);
            while( m == (foundtext.length() + trytext.length() ) ){
              found = true;
              foundtext += trytext; 

              t = tkz.next();
              if(t == null)break;

              trytext = " " + t.termText(); 

              if(setting[0] >=0 ) 
                m += matchString(tag_tree, (int)setting[0], trytext, setting);
              else
                break;
            }

            if(foundtext.length() > 0){
              if(setting[1] >= 0){ 
            	  tag_id_count_temp[(int)setting[1]][1]++;
                 setting[1] = -1;
              }
            }

            if(!found)
              t = tkz.next();
          }//while

          tkz.close();
        }catch (Exception e){
          System.out.println("error: " + e.getMessage());
        }

        //put all the found tags in the suggestedStrings
        for(int k=0; k<tag_id_count_temp.length; k++)
          if(tag_id_count_temp[k][1] > 0){
            suggestedStrings.add( ((Tag)all_tags.get(k)).getName() );
          }

        return suggestedStrings;
      }
    
    /**
     *
     * @param elements
     *          List: a list of elements
     * @return String
     */
    private String makeSuggest(List elements){
      String s = "";
      for(int i=0; i<elements.size(); i++){
        if(s.length()>0)s += " ";

        String str = ((Element)elements.get(i)).getText();
        if(i==0){
          if (str.length() > 2
              && str.compareToIgnoreCase("the") != 0)
            s += str;
        }else
          s += str;
      }
      return s;
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
    public int matchString(long[][] tree, int start, String s, long[] settings){
      if(s.length() == 0)return 0;

      if(tree[start][0] == s.charAt(0)){
        settings[0] = tree[start][1];    //remember the location

        if(tree[start][3] >= 0)
          settings[1] = tree[start][3];

        if(tree[start][1] > 0){
          return (1+this.matchString(tree,
                                     (int) tree[start][1], s.substring(1), settings ));
        }else
          return 1;
      }
      else{
        if(tree[start][2]>0){
          return this.matchString(tree,
                                  (int) tree[start][2], s, settings);
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
    public void addTag(long[][] tree, Tag tag) {
    	try {
			tagDAO.save(tag);
	    	all_tags.add(tag);
	    	if( tree.length > ( tree[0][3] + tag.name.length() ))
	    		addNode(tree, tag.name, all_tags.size()-1, 0, 1, 1);
	    	else{
	    		rebuildTree();
	    	}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


    /**
     * Get data form the tag database, and build or rebuild the tree. It loops
     * through all the tags and call addNode() to add all tags into the tree.
     * The result of this method will be a refreshed tag_tree
     */
    public void rebuildTree() {
    	List all_tags_temp = new ArrayList();
    	long[] tag_id_count_temp;
    	long[][] tag_tree_temp;
    	
    	try {
          Collection tags = tagDAO.getAllTags();
          System.out.println("====start to build tree, with " + tags.size() + " tags.");

          int size = 0;
          int i = 0;
          String s = "";
          tag_id_count_temp = new long[tags.size()];

          for(Iterator itr = tags.iterator(); itr.hasNext();){
            Tag tag = (Tag)itr.next();
            all_tags_temp.add( tag );
            s = tag.getName();
            size += s.length();

            tag_id_count_temp[i] = tag.getId();

            i++;
          }

          size += 27;

          tag_tree_temp = new long[size][4];

          tag_tree_temp[0][3] = -1;  //reset this cell - it's used else where

          for ( i = 0; i < tag_tree_temp.length; i++)
            for (int j = 0; j < tag_tree_temp[i].length; j++)
            	tag_tree_temp[i][j] = -1;

          for ( i = 0; i <= 26; i++) {
        	  tag_tree_temp[i][0] = 'a' - 1 + i;
        	  tag_tree_temp[i][1] = tag_tree_temp[i][3] = -1;
        	  tag_tree_temp[i][2] = i + 1;
          }
          tag_tree_temp[0][0] = '$';
          tag_tree_temp[0][3] = 27;	//the first available locaiton
          tag_tree_temp[26][2] = -1;

          for( i=0; i<all_tags_temp.size(); i++){
            s = ( (Tag)all_tags_temp.get(i)).getName().toLowerCase();
            this.addNode(tag_tree_temp, s, i,        //instead remember the ID(( (Term)list.get(i)).getId())
                         0, 1, 1);          //do the index in the list
               System.out.println("--added: " + s);
          }
          
        synchronized(tag_tree){
	        tag_tree = tag_tree_temp;
	        tag_id_count = tag_id_count_temp;
	        all_tags = all_tags_temp;
          }
          
         //printTreeNice(tag_tree, 0, "");
          System.out.println("==>>tag tree size: " + tag_tree_temp.length + "; used: " + tag_tree_temp[0][3]);

        }
        catch (Exception ex) {
          System.out.println("==Error in TagAnalyzer.rebuildTree: " + ex.getMessage());
        }

    }

    private static void printTreeNice(long[][] t, int start, String firstpart){
    if(t[start][3] >= 0){
        System.out.println(firstpart + (char)t[start][0]);
      }

      if(t[start][1] >= 0){
        printTreeNice(t, (int)t[start][1], firstpart + (char)t[start][0]);
      }

      if(t[start][2] >= 0){
        printTreeNice(t, (int)t[start][2], firstpart);
      }
  }


    /**
     * For the given tagStr, check if exist corresponding Tag object, if yes then return it; else
     * create a new Tag object and return it.
     *
     * @param tagStr The tag string
     * @return A Tag object.
     */
    public synchronized Collection ensureTags(String[] tagStrs) throws Exception {
        List list = new ArrayList(tagStrs.length);

        //this portion should be synchronized
        for (int i=0; i<tagStrs.length; i++) {
            if (tagStrs[i]==null) continue;
            tagStrs[i] = tagStrs[i].trim();
            if ("".equals(tagStrs[i])) continue;

            long[] setting = {0, -1};
            int m = matchString(tag_tree, 0, tagStrs[i], setting);
            if( m == tagStrs[i].length() ){	//if tag exist
            		//find the tag and add count
            	System.out.println("==increase count for tag:" + setting[1]);
            	list.add( (Tag)all_tags.get( (int)setting[1]) );
            }else{
            	Tag tag = new Tag();
            	tag.setName(tagStrs[i]);
            	tag.setDescription(tagStrs[i]);
            	tag.setStatus(Tag.STATUS_CANDIDATE);

            	list.add(tag);
            	tagDAO.save(tag);
            }
        }//for i

        return list;
    }//ensureTags()

    /**
     *
     * @param tree long[][] - tags as stored in an array.
     * @param tag String - a new tag to insert into the tag tree.
     * @param tagid long - the ID of the tag, for fast access.
     * @param parent int - the parent position of the node.
     * @param current int - the position of the current node.
     * @param child int - indicate to put the node at the left or right. 1 = left; 2 = right
     */
    private void addNode(long[][] tree, String tag, long tagid,
                         int parent, int current, int child){
      if(tag.length() == 0){
        tree[parent][3] = tagid;  //this cell is used to keep the availale position
        return;
      }

      if(current == -1){
        tree[ (int)tree[0][3] ][0] = tag.charAt(0);  //this means to use a new record
        tree[parent][child] = tree[0][3];            //set the child to the new record position
        tree[0][3]++;                                //move the available position
        this.addNode(tree, tag.substring(1), tagid,
                     (int)tree[0][3]-1, -1, 1);
        return;
      }

      if(tree[current][0] == tag.charAt(0)){
        this.addNode(tree, tag.substring(1), tagid,
                     current,(int)tree[current][1], 1 );
      }
      else{
        this.addNode(tree, tag, tagid,
                     current, (int)tree[current][2], 2);
      }

    }


}//class TagAnalyzer

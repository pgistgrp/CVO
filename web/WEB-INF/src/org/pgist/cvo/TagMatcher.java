package org.pgist.cvo;

import java.util.*;


/**
 *
 * @author Jie
 * @version 1.0
 */
public class TagMatcher {
    public TagMatcher() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private TagDAO tagDAO;

    //private String data = "vehicle miles traveled vehicle migration vehicle migration i-5 vehicle miles traveled vehicle migration vehicle migration i-5";

    private Set<String> kbTags; // tags' names stored in a Set

    private Map<String, Tag> kbMap; //tags retrieved from the prepared knowledge base

    private Set<Tag> cdTags; //candidate tags selected from the statement by CST

    private final int MAX = 6; //the maximum number of words in a tag


    public void setTagDAO(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }


    /*
     * ------------------------------------------------------------------------
     */

    public void build() {
        if (kbMap != null)
            return;
        else {
            try {
                cdTags = new TreeSet<Tag>();
                kbMap = new HashMap<String, Tag>();
                kbTags = new TreeSet<String>();
                Collection c = tagDAO.getAllTags();
                Iterator it = c.iterator();
                while (it.hasNext()) {
                    Tag tag = (Tag) it.next();
                    kbTags.add(tag.getName());
                    kbMap.put(tag.getName(), tag);
                }
            } catch (Exception e) {
                System.out.println(
                        "could not retrieve tags from the prepared knowledge base");
            }
        }
    }


    /**
     * The input statement should be all lowercase.
     * This method does not increase or decrease the count of a tag
     */
    public Collection match(String statement) {
        this.build();

        // replace all nonuseful punctuation with blank space
        String data = statement.replace('.', ' '); //replace full stop
        data = data.replace('?', ' '); //replace question mark
        data = data.replace('!', ' '); //replace exclamation mark
        data = data.replace(',', ' '); //replace comma
        data = data.replace(':', ' '); //replace colon
        data = data.replace(';', ' '); //replace semicolon
        data = data.replace('(', ' '); //replace
        data = data.replace(')', ' '); //replace
        data = data.replace('"', ' '); //replace "
        data = data.replace('%', ' '); //replace %
        data = data.replace('&', ' '); // replace &
        data = data.replace('*', ' '); //replace *
        data = data.replace('/', ' '); //replace /
        data = data.replace('\\', ' '); //replace \, must use \\
        data = data.replace('|', ' '); //replace |
        data = data.replace('@', ' '); //replace @
        data = data.replace('`', ' '); //replace `
        data = data.replace('~', ' '); //replace ~
        data = data.replace('#', ' '); //replace #
        data = data.replace('$', ' '); //replace $
        data = data.replace('^', ' '); //replace ^
        data = data.replace('_', ' '); //replace _
        data = data.replace('{', ' '); //replace {
        data = data.replace('}', ' '); //replace }
        data = data.replace('<', ' '); //replace <
        data = data.replace('>', ' '); //replace >

        //match the statement with the prepared knowledgebase tags, if find,
        //put it into cdTags
        String[] concern = data.trim().split(" ");
        int size = concern.length;
        if (size < 1)
            throw new IllegalStateException("The user does not input any word");

        for (int i = 0; i < size; i++) {
            String s = concern[i];
            if (kbTags.contains(s))
                cdTags.add(kbMap.get(s));
            int j = 1;
            while ((i + j) < size && j < MAX) {
                s = s + " " + concern[i + j];
                if (kbTags.contains(s))
                    cdTags.add(kbMap.get(s));
                j++;
            }
        }

        //remove stop words from data
        Iterator it = cdTags.iterator();
        if (it.hasNext()) {
            String s2 = ((Tag) it.next()).getName();
            data = data.replace(s2, " ");
        }
        data = data.replaceAll(" the ", " ");
        data = data.replaceAll(" of ", " ");
        data = data.replaceAll(" and ", " ");
        data = data.replaceAll(" that ", " ");
        data = data.replaceAll(" for ", " ");
        data = data.replaceAll(" by ", " ");
        data = data.replaceAll(" as ", " ");
        data = data.replaceAll(" be ", " ");
        data = data.replaceAll(" or ", " ");
        data = data.replaceAll(" this ", " ");
        data = data.replaceAll(" then ", " ");
        data = data.replaceAll(" we ", " ");
        data = data.replaceAll(" which ", " ");
        data = data.replaceAll(" with ", " ");
        data = data.replaceAll(" at ", " ");
        data = data.replaceAll(" from ", " ");
        data = data.replaceAll(" under ", " ");
        data = data.replaceAll(" such ", " ");
        data = data.replaceAll(" there ", " ");
        data = data.replaceAll(" other ", " ");
        data = data.replaceAll(" if ", " ");
        data = data.replaceAll(" is ", " ");
        data = data.replaceAll(" it ", " ");
        data = data.replaceAll(" can ", " ");
        data = data.replaceAll(" now ", " ");
        data = data.replaceAll(" and ", " ");
        data = data.replaceAll(" to ", " ");
        data = data.replaceAll(" but ", " ");
        data = data.replaceAll(" already ", " ");
        data = data.replaceAll(" although ", " ");
        data = data.replaceAll(" always ", " ");
        data = data.replaceAll("among ", " ");
        data = data.replaceAll(" any ", " ");
        data = data.replaceAll(" anyone ", " ");
        data = data.replaceAll(" apparently ", " ");
        data = data.replaceAll(" are ", " ");
        data = data.replaceAll(" arise ", " ");
        data = data.replaceAll(" asidee ", " ");
        data = data.replaceAll(" away ", " ");
        data = data.replaceAll(" became ", " ");
        data = data.replaceAll(" become ", " ");
        data = data.replaceAll(" becomes ", " ");
        data = data.replaceAll(" been ", " ");
        data = data.replaceAll(" being ", " ");
        data = data.replaceAll(" both ", " ");
        data = data.replaceAll(" briefly ", " ");
        data = data.replaceAll(" ame ", " ");
        data = data.replaceAll(" cannot ", " ");
        data = data.replaceAll(" certain ", " ");
        data = data.replaceAll(" certainly ", " ");
        data = data.replaceAll(" could ", " ");
        data = data.replaceAll(" etc ", " ");
        data = data.replaceAll(" does ", " ");
        data = data.replaceAll(" done ", " ");
        data = data.replaceAll(" during ", " ");
        data = data.replaceAll(" each ", " ");
        data = data.replaceAll(" made ", " ");
        data = data.replaceAll(" mainly ", " ");
        data = data.replaceAll(" make ", " ");
        data = data.replaceAll(" many ", " ");
        data = data.replaceAll(" might ", " ");
        data = data.replaceAll(" more ", " ");
        data = data.replaceAll(" most ", " ");
        data = data.replaceAll(" mostly ", " ");
        data = data.replaceAll(" much ", " ");
        data = data.replaceAll(" must ", " ");
        data = data.replaceAll(" nearly ", " ");
        data = data.replaceAll(" necessarily ", " ");
        data = data.replaceAll(" neither ", " ");
        data = data.replaceAll(" next ", " ");
        data = data.replaceAll(" none ", " ");
        data = data.replaceAll(" nor ", " ");
        data = data.replaceAll(" normally ", " ");
        data = data.replaceAll(" not ", " ");
        data = data.replaceAll(" noted ", " ");
        data = data.replaceAll(" obtain ", " ");
        data = data.replaceAll(" obtained ", " ");
        data = data.replaceAll(" often ", " ");
        data = data.replaceAll(" only ", " ");
        data = data.replaceAll(" our ", " ");
        data = data.replaceAll(" put ", " ");
        data = data.replaceAll(" owing ", " ");
        data = data.replaceAll(" said ", " ");
        data = data.replaceAll(" same ", " ");
        data = data.replaceAll(" seem ", " ");
        data = data.replaceAll(" seen ", " ");
        data = data.replaceAll(" several ", " ");
        data = data.replaceAll(" shall ", " ");
        data = data.replaceAll(" should ", " ");
        data = data.replaceAll(" show ", " ");
        data = data.replaceAll(" showed ", " ");
        data = data.replaceAll(" shown ", " ");
        data = data.replaceAll(" shows ", " ");
        data = data.replaceAll(" significantly ", " ");
        data = data.replaceAll(" similar ", " ");
        data = data.replaceAll(" similarly ", " ");
        data = data.replaceAll(" slightly ", " ");
        data = data.replaceAll(" so ", " ");
        data = data.replaceAll(" sometime ", " ");
        data = data.replaceAll(" somewhat ", " ");
        data = data.replaceAll(" soon ", " ");
        data = data.replaceAll(" specifically ", " ");
        data = data.replaceAll(" state ", " ");
        data = data.replaceAll(" states ", " ");
        data = data.replaceAll(" strongly ", " ");
        data = data.replaceAll(" substantially ", " ");
        data = data.replaceAll(" successfully ", " ");
        data = data.replaceAll(" sufficiently ", " ");
        data = data.replaceAll(" their ", " ");
        data = data.replaceAll(" theirs ", " ");
        data = data.replaceAll(" upon ", " ");
        data = data.replaceAll(" where ", " ");
        data = data.replaceAll(" these ", " ");
        data = data.replaceAll(" when ", " ");
        data = data.replaceAll(" whether ", " ");
        data = data.replaceAll(" also ", " ");
        data = data.replaceAll(" than ", " ");
        data = data.replaceAll(" after ", " ");
        data = data.replaceAll(" within ", " ");
        data = data.replaceAll(" before ", " ");
        data = data.replaceAll(" because ", " ");
        data = data.replaceAll(" without ", " ");
        data = data.replaceAll(" however ", " ");
        data = data.replaceAll(" therefore ", " ");
        data = data.replaceAll(" between ", " ");
        data = data.replaceAll(" those ", " ");
        data = data.replaceAll(" since ", " ");
        data = data.replaceAll(" into ", " ");
        data = data.replaceAll(" out ", " ");
        data = data.replaceAll(" some ", " ");
        data = data.replaceAll(" about ", " ");
        data = data.replaceAll(" accordingly ", " ");
        data = data.replaceAll(" affecting ", " ");
        data = data.replaceAll(" affected ", " ");
        data = data.replaceAll(" again ", " ");
        data = data.replaceAll(" against ", " ");
        data = data.replaceAll(" all ", " ");
        data = data.replaceAll(" almost ", " ");
        data = data.replaceAll(" either ", " ");
        data = data.replaceAll(" else ", " ");
        data = data.replaceAll(" ever ", " ");
        data = data.replaceAll(" every ", " ");
        data = data.replaceAll(" following ", " ");
        data = data.replaceAll(" found ", " ");
        data = data.replaceAll(" further ", " ");
        data = data.replaceAll(" gave ", " ");
        data = data.replaceAll(" gets ", " ");
        data = data.replaceAll(" given ", " ");
        data = data.replaceAll(" giving ", " ");
        data = data.replaceAll(" gone ", " ");
        data = data.replaceAll(" got ", " ");
        data = data.replaceAll(" had ", " ");
        data = data.replaceAll(" hardly ", " ");
        data = data.replaceAll(" has ", " ");
        data = data.replaceAll(" have ", " ");
        data = data.replaceAll(" having ", " ");
        data = data.replaceAll(" here ", " ");
        data = data.replaceAll(" however ", " ");
        data = data.replaceAll(" itself ", " ");
        data = data.replaceAll(" just ", " ");
        data = data.replaceAll(" keep ", " ");
        data = data.replaceAll(" kept ", " ");
        data = data.replaceAll(" knowledge ", " ");
        data = data.replaceAll(" largely ", " ");
        data = data.replaceAll(" like ", " ");
        data = data.replaceAll(" particularly ", " ");
        data = data.replaceAll(" past ", " ");
        data = data.replaceAll(" perhaps ", " ");
        data = data.replaceAll(" please ", " ");
        data = data.replaceAll(" poorly ", " ");
        data = data.replaceAll(" possible ", " ");
        data = data.replaceAll(" possibly ", " ");
        data = data.replaceAll(" potentially ", " ");
        data = data.replaceAll(" present ", " ");
        data = data.replaceAll(" previously ", " ");
        data = data.replaceAll(" primarily ", " ");
        data = data.replaceAll(" probably ", " ");
        data = data.replaceAll(" prompt ", " ");
        data = data.replaceAll(" quickly ", " ");
        data = data.replaceAll(" quite ", " ");
        data = data.replaceAll(" rather ", " ");
        data = data.replaceAll(" readily ", " ");
        data = data.replaceAll(" really ", " ");
        data = data.replaceAll(" recently ", " ");
        data = data.replaceAll(" regarding ", " ");
        data = data.replaceAll(" regardless ", " ");
        data = data.replaceAll(" relatively ", " ");
        data = data.replaceAll(" respectively ", " ");
        data = data.replaceAll(" resulted ", " ");
        data = data.replaceAll(" resulting ", " ");
        data = data.replaceAll(" result ", " ");
        data = data.replaceAll(" results ", " ");
        data = data.replaceAll(" them ", " ");
        data = data.replaceAll(" they ", " ");
        data = data.replaceAll(" though ", " ");
        data = data.replaceAll(" through ", " ");
        data = data.replaceAll(" throughout ", " ");
        data = data.replaceAll(" too ", " ");
        data = data.replaceAll(" toward ", " ");
        data = data.replaceAll(" unless ", " ");
        data = data.replaceAll(" until ", " ");
        data = data.replaceAll(" use ", " ");
        data = data.replaceAll(" used ", " ");
        data = data.replaceAll(" usefully ", " ");
        data = data.replaceAll(" usefullness ", " ");
        data = data.replaceAll(" using ", " ");
        data = data.replaceAll(" usually ", " ");
        data = data.replaceAll(" various ", " ");
        data = data.replaceAll(" very ", " ");
        data = data.replaceAll(" was ", " ");
        data = data.replaceAll(" were ", " ");
        data = data.replaceAll(" what ", " ");
        data = data.replaceAll(" while ", " ");
        data = data.replaceAll(" who ", " ");
        data = data.replaceAll(" whose ", " ");
        data = data.replaceAll(" why ", " ");
        data = data.replaceAll(" widely ", " ");
        data = data.replaceAll(" will ", " ");
        data = data.replaceAll(" would ", " ");
        data = data.replaceAll(" yet ", " ");
        data = data.replaceAll(" i ", " ");
        data = data.replaceAll(" you ", " ");
        data = data.replaceAll(" only ", " ");
        data = data.replaceAll("  ", " ");
        data = data.replaceAll(" in ", " ");
        data = data.replaceAll(" me ", " ");
        data = data.replaceAll(" do ", " ");
        data = data.replaceAll(" well ", " ");
        data = data.replaceAll(" thing ", " ");
        data = data.replaceAll(" things ", " ");
        data = data.replaceAll(" think ", " ");
        data = data.replaceAll(" my ", " ");
        data = data.replaceAll(" get ", " ");
        data = data.replaceAll(" one ", " ");
        data = data.replaceAll(" know ", " ");
        data = data.replaceAll(" went ", " ");
        data = data.replaceAll(" am ", " ");
        data = data.replaceAll(" ways ", " ");
        data = data.replaceAll(" way ", " ");
        data = data.replaceAll(" here ", " ");
        data = data.replaceAll(" she ", " ");
        data = data.replaceAll(" off ", " ");
        data = data.replaceAll(" two ", " ");
        data = data.replaceAll(" she ", " ");
        data = data.replaceAll(" going ", " ");
        data = data.replaceAll(" fact ", " ");
        data = data.replaceAll(" khew ", " ");
        data = data.replaceAll(" people ", " ");
        data = data.replaceAll(" transportation ", " ");
        data = data.replaceAll(" stop ", " ");
        data = data.replaceAll(" take ", " ");
        data = data.replaceAll(" doing ", " ");
        data = data.replaceAll(" no ", " ");
        String[] cdString = data.trim().split(" ");
        int l = cdString.length;
        for(int i = 0; i < l; i++ ) {
            Tag t = new Tag();
            t.setName(cdString[i]);
            t.setStatus(t.STATUS_CANDIDATE);
            cdTags.add(t);
        }
        return cdTags;
    }

    private void jbInit() throws Exception {
    }


    /**
         public void printkbTags() {
        System.out.println("we have " + kbTags.size() +
                           " prepared tags in the PGIST knowledge base.");
        System.out.println("They are " + kbTags.toString());
        //This implementation provides guaranteed log(n) time cost for the basic operations (add, remove and contains).
        String s = "federal transportation administration";
        long start = new Date().getTime();
        //System.out.println(kbTags.contains(s));
        this.find();
        long end = new Date().getTime();
        System.out.println("finding candidate tags costing time: " +
                           (end - start));
        System.out.println("They are " + cdTags.toString());
         }

         public static void main(String[] args) {
        tagFinder a = new tagFinder();
        a.printkbTags();
         }*/


} //class TagMatcher

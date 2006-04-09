package org.pgist.cvo;

import java.util.Collection;
import java.util.Map;
import java.util.Set;


/**
 * 
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: PGIST
 * </p>
 * 
 * @author Jie
 * @version 1.0
 */
public class TagMatcher {
    
    
    private String data = "vehicle miles traveled vehicle migration vehicle migration i-5 vehicle miles traveled vehicle migration vehicle migration i-5";
    
    private Set<String> kbTags;
    
    private Map<String, Tag> kbMap; //tags retrieved from the prepared knowledge base
    
    private Set<Tag> cdTags; //candidate tags selected from the statement by CST
    
    private final int MAX = 6; //the maximum number of words in a tag
    
    private TagDAO tagDao;
    

/*    public void build() {
        if (kbMap != null)
            return;
        else {
            try {
                kbMap = new HashMap<String, Tag>();
                kbTags = new TreeSet<String>();
                Collection c = tagDao.getAllTags();
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
*/
    
    public Collection find(String statement) {
  //      this.build();
        data = statement;
        String[] concern = statement.split(" ");
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
        return cdTags;
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

    
}

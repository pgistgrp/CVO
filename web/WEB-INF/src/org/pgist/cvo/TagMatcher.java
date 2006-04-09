/**
 *
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: PGIST</p>
 *
 * @author Jie
 * @version 1.0
 */

package org.pgist.cvo;

import java.util.*;

public class TagMatcher {
    private static final String statement = "vehicle miles traveled vehicle migration vehicle migration i-5 vehicle miles traveled vehicle migration vehicle migration i-5";
    private final String blank =" ";
    private String[] concern;
    private Set<String> kbTags; //tags retrieved from the prepared knowledge base
    private HashMap<String, Tag> cdMap;
    private Set<String> cdTags; //candidate tags selected from the statement by CST
    private static final int MAX = 6; //the maximum number of words in a tag

    public void build() {
        if(kbTags != null)
            return;
        else

    }

    private Collection find() {
        concern = statement.split(blank);
        kbTags = new TreeSet<String> ();
        cdTags = new TreeSet<String> ();

        int size = concern.length;
        if(size < 1)
            throw new IllegalStateException("The user does not input any word");
        for(int i = 0; i < size; i++) {
                String s = concern[i];
                if(kbTags.contains(s))
                    cdTags.add(s);
                int j = 1;
                while((i+j) < size && j < MAX){
                    s = s + blank + concern[i+j];
                    if(kbTags.contains(s))
                        cdTags.add(s);
                    j++;
                }
        }
    }

    public void printkbTags() {
        System.out.println("we have " + kbTags.size() + " prepared tags in the PGIST knowledge base.");
        System.out.println("They are " + kbTags.toString());
        //This implementation provides guaranteed log(n) time cost for the basic operations (add, remove and contains).
        String s = "federal transportation administration";
        long start = new Date().getTime();
        //System.out.println(kbTags.contains(s));
        this.find();
        long end = new Date().getTime();
        System.out.println("finding candidate tags costing time: " + (end - start));
        System.out.println("They are " + cdTags.toString());
    }

    public static void main(String[] args) {
        tagFinder a = new tagFinder();
        a.printkbTags();
    }
}

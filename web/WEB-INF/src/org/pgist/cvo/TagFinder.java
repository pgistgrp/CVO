package org.pgist.cvo;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.StringReader;
import java.io.Reader;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.Token;
import java.util.Date;

public class TagFinder {
  private static List stop_words = new ArrayList();
  private static List all_tags = new ArrayList();
  private int parent_location;

  public TagFinder() {
  }

  public static void main(String[] args) {
    TagFinder x = new TagFinder();

    x.initCollections();

    String statement = "";
    //tokenize(statement);

    long[][] tt = x.makeTree(x.all_tags);

    x.printTreeNice(tt, 0, "");
    x.printTree(tt);
    //StopAnalyzer sa = new StopAnalyzer();

    //we need to take a 3-step approach:
    //1. extract existing tags;
    //2. remove stop-words;
    //3. present left words, paired as 2
    String ss = "bus rapid vehi";
    //System.out.println("matched length: " + x.matchString(tt, 0, ss));
    if(tt[0][3] > 0)
      System.out.print("full match:");
    else
      System.out.print("partial match:");
    System.out.print(tt[0][3]);

    long start, end;

/*    long start = new Date().getTime();
    x.tokenize(statement);
    long end = new Date().getTime();
    System.out.println("tokenize used time:" + (end - start));
*/
    start = new Date().getTime();
    x.find(tt, statement);
    end = new Date().getTime();
    System.out.println("tag tree used time:" + (end - start));


/*    if(x.matchString(tt, 0, ss) > 0)
      System.out.println("matched: " + ss);
    else
      System.out.println("not matched: " + ss);
*/
    tt = null;
  }

  private void find(long[][] tree, String inStatement){
    //List list = new ArrayList();

    Reader reader = new StringReader(inStatement);
    Tokenizer tkz = new StandardTokenizer(reader);

    try{
      Token t = tkz.next();
      while (t != null){
        //find the logest appearance
        String foundtext = "";
        String trytext = t.termText();
        boolean found = false;
        long[] setting = {0, -1};
        int m = matchString(tree, 0, trytext, setting);
        while( m == (foundtext.length() + trytext.length() ) ){ //while( m == trytext.length() ){
          found = true;
          foundtext += trytext; //foundtext = trytext;

          t = tkz.next();
          if(t == null)break;

          trytext = " " + t.termText(); //trytext += " " + t.termText();

          if(setting[0] >=0 ) //if(tree[0][1] >= 0)
            m += matchString(tree, (int)setting[0], trytext, setting);
            //m += matchString(tree, (int)tree[0][1], trytext); //m = matchString(tree, 0, trytext);
          else
            break;
        }

        if(foundtext.compareTo("") != 0){
          if(setting[1] >= 0){ //if(tree[0][3] >= 0){
            System.out.println("==>>matched: " + (String)all_tags.get((int)setting[1]));
            setting[1] = -1;
          }
        }

        if(!found)
          t = tkz.next();
      }

      tkz.close();
    }catch (Exception e){
      System.out.println("error: " + e.getMessage());
    }

    return ;
  }

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

  public long[][] makeTree(List list){
    int size = 0;
    String s;
    for(int i=0; i<list.size(); i++){
      s = (String) list.get(i);
      size += s.length();
    }
    size += 27;

    System.out.println("==>>size: " + size);
    long[][] tree = new long[size][4];
    initTree(tree);

    for(int i=0; i<list.size(); i++){
      s = (String) list.get(i);
//      addToTree(tree, s, i);
      this.addNode(tree, s, i,
                   0, 1, 1);
    }

    tree[0][3] = -1;  //reset this cell - it's used else where
    return tree;
  }

  public void addNode(long[][] tree, String tag, long tagid,
                      int parent, int current, int child){
    if(tag.length() == 0){
      tree[parent][3] = tagid;
      return;
    }

    if(current == -1){
      tree[ (int)tree[0][3] ][0] = tag.charAt(0);
      tree[parent][child] = tree[0][3];
      tree[0][3]++;
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

  public void addToTree(long[][] tree, String tag, long tagid){
    int parent = 0, tryindex = 1, maximum = tree.length;
    boolean isnew;
    int lastavailable=1;

    for(int i=0; i<maximum; i++){
      if(tree[i][0] == -1){
        lastavailable = i;
        break;
      }
    }

    for(int i=0; i<tag.length(); i++){
      char theChar = tag.charAt(i);

      isnew = true;
      if(tryindex >= 0 && tryindex <= maximum){
        if (theChar == tree[tryindex][0]) {
          isnew = false;

          parent = tryindex;
          tryindex = (int) tree[tryindex][1];
        }
        else while (tree[tryindex][2] > 0){
          parent = tryindex;
          tryindex = (int)tree[tryindex][2];

          if (theChar == tree[tryindex][0]) {
            isnew = false;

            parent = tryindex;
            tryindex = (int)tree[tryindex][1];
            break;
          }
        }
      }

      if(isnew){
        tree[lastavailable][0] = theChar;
        if(i==(tag.length()-1))tree[lastavailable][3] = i;
        if ( tree[parent][1] > 0 ){
          tree[tryindex][2] = lastavailable; //for the first letter of the tag string
        }else
          tree[parent][1] = lastavailable; //for the rest letters of the tag string

        parent = lastavailable;
        lastavailable++;
        tryindex = -1;
      }

      if (i == (maximum - 1)) //reaches the end of the tag string
        tree[tryindex][3] = tagid;
    }
  }

  public void initTree(long[][] t){
    for(int i=0; i<t.length; i++)
      for(int j=0; j<t[i].length; j++)
        t[i][j] = -1;

    for(int i=0; i<=26; i++){
      t[i][0] = 'a' - 1 + i;
      t[i][1] = t[i][3] = -1;
      t[i][2] = i + 1;
    }
    t[0][0] = '$';
    t[0][3] = 27;
    t[26][2] = -1;
  }

  public static void printTree(long[][] t){
    for(int i=0; i<t.length; i++){
      System.out.print(i + ": ");
      System.out.print((char)t[i][0] + " ");
      for (int j = 1; j < t[i].length; j++) {
        System.out.print(t[i][j]);
        System.out.print(" ");
      }
      System.out.println();
    }
  }

  public void printTreeNice(long[][] t, int start, String firstpart){
    if(t[start][3] >= 0){
      System.out.println(firstpart + (char)t[start][0]);
    }

    if(t[start][1] >= 0){
      this.printTreeNice(t, (int)t[start][1], firstpart + (char)t[start][0]);
    }

    if(t[start][2] >= 0){
      this.printTreeNice(t, (int)t[start][2], firstpart);
    }
  }

  public static void tokenize(String inStatement){
    int start = 0;
    int end = 0;
    String currentWord = "";

    //System.out.println("==>input statement: ");
    //System.out.println( inStatement );

    Reader reader = new StringReader(inStatement);
    Tokenizer tkz = new StandardTokenizer(reader);

    try{
      Token t = tkz.next();
      while (t != null){

        //find the logest appearance
        String trytext = t.termText();
        String foundtext = "";
        boolean found = false;
        while( canBeFound(all_tags, trytext) ){
          found = true;
          foundtext = trytext;

          t = tkz.next();
          trytext += " " + t.termText();
        }

        if(foundtext.compareTo("") != 0)
          System.out.println("==>>matched: " + foundtext);

        if(!found)
          t = tkz.next();
      }


      tkz.close();
    }catch (Exception e){

    }
    //anyString.substring(m,m) <=> empty string ""
  }

  private static boolean canBeFound(List list, String s){
    for(int i=0; i<list.size(); i++){
      int compare = ((String) list.get(i)).indexOf(s);
      if(compare != 0)compare = ((String) list.get(i)).indexOf(s + " ");

      if( compare == 0)
        return true;
    }

    return false;
  }

  private void initCollections(){
     stop_words.add("is");
     stop_words.add("are");
     stop_words.add("of");
     stop_words.add("a");
     stop_words.add("an");
     stop_words.add("the");

     all_tags.add("alaskan way viaduct");
     all_tags.add("alternatives to single occupancy car use");
     all_tags.add("ballot measure");
     all_tags.add("bicycle friendly street network");
     all_tags.add("bridge toll");
     all_tags.add("bike lanes");
     all_tags.add("business community support");
     all_tags.add("commuting alternatives");
     all_tags.add("competing transportation proposals");
     all_tags.add("construction employment");
     all_tags.add("disability access");
     all_tags.add("elected official");
     all_tags.add("employment density in urban centers");
     all_tags.add("bus");
     all_tags.add("busman");
     all_tags.add("busmanania");
     all_tags.add("car");
     all_tags.add("bus rapid transit");
     all_tags.add("bus rapid vehicle");
     all_tags.add("vehicle miles traveled");
     all_tags.add("vehicle migration");
     all_tags.add("environmental impact statement");
     all_tags.add("equity of transportation service");
     all_tags.add("federal transportation administration");
/*     all_tags.add("traffic on");
     all_tags.add("environment");
     all_tags.add("community");
     all_tags.add("safety");
     all_tags.add("transit");
*/  }
}

import java.util.*;

public class tagFinder {
    private static final String statement = "vehicle miles traveled vehicle migration vehicle migration i-5 vehicle miles traveled vehicle migration vehicle migration i-5";
    private static final String blank =" ";    
    private static String[] concern;
    //List<String> input; //user input
    Set<String> kbTags; //tags retrieved from the prepared knowledge base
    Set<String> cdTags; //candidate tags selected from the statement by CST 
    //public tagFinder(List<String> list) {
    public tagFinder() {
        //input = new LinkedList<String>();
        concern = statement.split(blank);         
        kbTags = new TreeSet<String> ();
        cdTags = new TreeSet<String> ();
        kbTags.add("safety of i-5");
        kbTags.add("seattle transit");
        kbTags.add("alaskan way viaduct");
        kbTags.add("alternatives to single occupancy car use");
        kbTags.add("ballot measure");
        kbTags.add("bicycle friendly street network");
        kbTags.add("bridge toll");
        kbTags.add("bike lanes");
        kbTags.add("business community support");
        kbTags.add("commuting alternatives");
        kbTags.add("competing transportation proposals");
        kbTags.add("construction employment");
        kbTags.add("disability access");
        kbTags.add("elected official");
        kbTags.add("employment density in urban centers");
        kbTags.add("bus");
        kbTags.add("busman");
        kbTags.add("busmanania");
        kbTags.add("car");
        kbTags.add("bus rapid transit");
        kbTags.add("bus rapid vehicle");
        kbTags.add("vehicle miles traveled");
        kbTags.add("vehicle migration");
        kbTags.add("i-5");
        kbTags.add("environmental impact statement");
        kbTags.add("equity of transportation service");
        kbTags.add("federal transportation administration");
    }
    
    private void find() {
        int size = concern.length;
        if(size < 1)
            throw new IllegalStateException("The user does not input any word");
        for(int i = 0; i < size; i++) {
                String s = concern[i];
                if(kbTags.contains(s))
                    cdTags.add(s); 
                int j = 1;                
                while((i+j) < size && j < 6){
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
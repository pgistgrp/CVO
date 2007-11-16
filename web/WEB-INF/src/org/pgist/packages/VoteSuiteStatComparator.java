package org.pgist.packages;

import java.util.Comparator;


public class VoteSuiteStatComparator implements Comparator {
    
    
    public VoteSuiteStatComparator() {
    }
    

    /*
     * ------------------------------------------------------------------------
     */


    public int compare(Object obj1, Object obj2) {
    	VoteSuiteStat o1 = (VoteSuiteStat) obj1;
    	VoteSuiteStat o2 = (VoteSuiteStat) obj2;
        
    	int o1Pos = o1.getHighVotes() + o1.getMediumVotes();
    	int o2Pos = o2.getHighVotes() + o2.getMediumVotes();
    	
    	int result = 0;
    	
    	if(o1Pos > o2Pos ) {
    		result = 1;
    	} else if(o1Pos == o2Pos){
    		if(o1.getHighVotes() > o2.getHighVotes()) {
				result = 1;
    		} else {
    			result = -1;
    		}
		} else {
			result = -1;
		}		
    	
    	//-1 less, 0 same, 1 greater
        
        return result*-1; //I want to reverse it
    }//compare()
    
    
}//class VoteSuiteStatComparator

package org.pgist.funding;

import java.util.Comparator;

public class FundingSourceAlternativeComparator implements Comparator {
    private boolean caseSensitive = false;
	public int compare(Object obj1, Object obj2) {
        FundingSourceAlternative ref1 = (FundingSourceAlternative) obj1;
        FundingSourceAlternative ref2 = (FundingSourceAlternative) obj2;
        
        int result;
        if (caseSensitive) {
        	result = ref1.getName().compareTo(ref2.getName());
        } else {
        	result = ref1.getName().compareToIgnoreCase(ref2.getName());
        }
        if(result == 0) {
        	result = ref1.getId().compareTo(ref2.getId());        	
        }
        return result;        
	}
}

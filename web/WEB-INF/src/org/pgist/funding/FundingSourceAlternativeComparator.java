package org.pgist.funding;

import java.util.Comparator;

public class FundingSourceAlternativeComparator implements Comparator {
    private boolean caseSensitive = false;
	public int compare(Object obj1, Object obj2) {
        FundingSourceAlternative ref1 = (FundingSourceAlternative) obj1;
        FundingSourceAlternative ref2 = (FundingSourceAlternative) obj2;
        
        if (caseSensitive) return ref1.getName().compareTo(ref2.getName());
        else return ref1.getName().compareToIgnoreCase(ref2.getName());
	}
}

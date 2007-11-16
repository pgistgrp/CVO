package org.pgist.funding;

import java.util.Comparator;

public class FundingSourceAltRefComparator implements Comparator {
    private boolean caseSensitive = false;
	public int compare(Object obj1, Object obj2) {
        FundingSourceAltRef ref1 = (FundingSourceAltRef) obj1;
        FundingSourceAltRef ref2 = (FundingSourceAltRef) obj2;
        
        if (caseSensitive) return ref1.getAlternative().getName().compareTo(ref2.getAlternative().getName());
        else return ref1.getAlternative().getName().compareToIgnoreCase(ref2.getAlternative().getName());
	}
}

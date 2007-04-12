package org.pgist.funding;

import java.util.Comparator;

public class UserFundingSourceTollComparator implements Comparator {
    private boolean caseSensitive = false;
	public int compare(Object obj1, Object obj2) {
        UserFundingSourceToll ref1 = (UserFundingSourceToll) obj1;
        UserFundingSourceToll ref2 = (UserFundingSourceToll) obj2;
        
        if (caseSensitive) return ref1.getName().compareTo(ref2.getName());
        else return ref1.getName().compareToIgnoreCase(ref2.getName());
	}
}

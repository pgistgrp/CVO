package org.pgist.tests.funding;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.pgist.funding.FundingSourceAltRef;
import org.pgist.funding.FundingSourceRef;

/**
 * Used to test the functionality of the FundingSuite
 * 
 * @author Matt Paulin
 */
public class TestFundingSourceRef {
	
	/**
	 * Test that an alt Ref can be removed from the project alt ref
	 */
	@Test
	public void testRemovingFundingSourceAltRef() {
		

		FundingSourceRef pRef = new FundingSourceRef();
		FundingSourceAltRef altRef1 = new FundingSourceAltRef();
		altRef1.setId(new Long(1));
		FundingSourceAltRef altRef2 = new FundingSourceAltRef();
		altRef2.setId(new Long(2));
		FundingSourceAltRef altRef3 = new FundingSourceAltRef();
		altRef3.setId(new Long(2));
		
		assertEquals(0, pRef.getNumAltRefs());
		
		pRef.getAltRefs().add(altRef1);
		assertEquals(1, pRef.getNumAltRefs());

		pRef.getAltRefs().add(altRef2);
		assertEquals(2, pRef.getNumAltRefs());
		
		pRef.removeAltRef(altRef1);
		assertEquals(1, pRef.getNumAltRefs());
		
		//Test removing with a different object with the same id
		pRef.removeAltRef(altRef3);
		assertEquals(0, pRef.getNumAltRefs());
		
	}
}

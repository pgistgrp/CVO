package org.pgist.tests.funding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.pgist.funding.FundingSource;
import org.pgist.funding.FundingSourceAltRef;
import org.pgist.funding.FundingSourceAlternative;
import org.pgist.funding.FundingSourceRef;
import org.pgist.funding.FundingSourceSuite;

/**
 * Used to test the functionality of the FundingSourceSuite
 * 
 * @author Matt Paulin
 */
public class TestFundingSourceSuite {
	
	/**
	 * Test that an alt Ref is added properly to the project suite
	 */
	@Test
	public void testGettingRefereces() {
		
		//Create the project suite
		FundingSourceSuite suite = new FundingSourceSuite();
		
		FundingSource project1 = new FundingSource();
		project1.setId(new Long(1));
		
		FundingSource project2 = new FundingSource();
		project2.setId(new Long(2));		
		
		FundingSourceAlternative pAlt1 = new FundingSourceAlternative();
		pAlt1.setId(new Long(1));
		pAlt1.setSource(project1);
		FundingSourceAlternative pAlt2 = new FundingSourceAlternative();
		pAlt2.setId(new Long(2));
		pAlt2.setSource(project1);
		FundingSourceAlternative pAlt3 = new FundingSourceAlternative();
		pAlt3.setId(new Long(3));
		pAlt3.setSource(project2);
		
		FundingSourceAltRef altRef1 = new FundingSourceAltRef();
		altRef1.setId(new Long(1));
		altRef1.setAlternative(pAlt1);
		FundingSourceAltRef altRef2 = new FundingSourceAltRef();
		altRef2.setId(new Long(2));
		altRef2.setAlternative(pAlt2);
		FundingSourceAltRef altRef3 = new FundingSourceAltRef();
		altRef3.setId(new Long(3));		
		altRef3.setAlternative(pAlt3);
		
		FundingSourceRef pRef1 = new FundingSourceRef();		
		pRef1.setSource(project1);
		pRef1.getAltRefs().add(altRef1);
		pRef1.getAltRefs().add(altRef2);
		
		FundingSourceRef pRef2 = new FundingSourceRef();
		pRef2.setSource(project2);
		pRef2.getAltRefs().add(altRef3);
		
		assertNull(suite.getFundingSourceReference(project1));
		assertNull(suite.getFundingSourceReference(project2));
		
		assertNull(suite.getFundingSourceReferece(altRef1));
		assertNull(suite.getFundingSourceReferece(altRef2));
		assertNull(suite.getFundingSourceReferece(altRef3));
		
		assertFalse(suite.containsAlts(new Long(1)));
		assertFalse(suite.containsAlts(new Long(2)));
		assertFalse(suite.containsAlts(new Long(3)));

		//Add a project
		suite.getReferences().add(pRef1);
		assertEquals(new Long(1), suite.getFundingSourceReference(project1).getSource().getId());
		assertNull(suite.getFundingSourceReference(project2));

		assertEquals(new Long(1), suite.getFundingSourceReferece(altRef1).getSource().getId());
		assertEquals(new Long(1), suite.getFundingSourceReferece(altRef2).getSource().getId());
		assertNull(suite.getFundingSourceReferece(altRef3));
		
		assertTrue(suite.containsAlts(new Long(1)));
		assertTrue(suite.containsAlts(new Long(2)));
		assertFalse(suite.containsAlts(new Long(3)));
		
		//Add the next project
		suite.getReferences().add(pRef2);
		assertEquals(new Long(1), suite.getFundingSourceReference(project1).getSource().getId());
		assertEquals(new Long(2), suite.getFundingSourceReference(project2).getSource().getId());
		
		assertEquals(new Long(1), suite.getFundingSourceReferece(altRef1).getSource().getId());
		assertEquals(new Long(1), suite.getFundingSourceReferece(altRef2).getSource().getId());
		assertEquals(new Long(2), suite.getFundingSourceReferece(altRef3).getSource().getId());

		assertTrue(suite.containsAlts(new Long(1)));
		assertTrue(suite.containsAlts(new Long(2)));
		assertTrue(suite.containsAlts(new Long(3)));
	}
}

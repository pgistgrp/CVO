package org.pgist.tests.funding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.pgist.funding.FundingServiceImpl;
import org.pgist.funding.FundingSource;
import org.pgist.funding.FundingSourceAltRef;
import org.pgist.funding.FundingSourceAlternative;
import org.pgist.funding.FundingSourceRef;
import org.pgist.funding.FundingSourceSuite;

/**
 * Used to test the functionality FundingSourceServiceImpl
 * 
 */
public class TestFundingServiceImpl {

	private FundingServiceImpl fundingService = new FundingServiceImpl();
	private MockFundingSourceDAO fundingDAO = null;
	
	@Before
	public void setUp() {
		fundingDAO = new MockFundingSourceDAO();
		fundingService.setFundingDAO(fundingDAO);		
	}
	
	/**
	 * Test adding a reference to the funding service
	 */
	/**
	 * @throws Exception
	 */
	@Test
	public void testRelate() throws Exception {
		Long suiteId = new Long(1);
		FundingSourceSuite suite = new FundingSourceSuite();
		suite.setId(suiteId);
		
		Long altId = new Long(42);
		FundingSourceAlternative alt = new FundingSourceAlternative();
		alt.setId(altId);
		FundingSource funding = new FundingSource();
		funding.setId(new Long(21));
		alt.setSource(funding);
		
		FundingSourceAltRef altRef = new FundingSourceAltRef();
		
		//Set all the preformed information
		fundingDAO.setFundingSuite(suite);
		fundingDAO.setFundingSourceAlternative(alt);
		fundingDAO.clearSaved();
				
		fundingService.relateFundingAlt(suiteId, altId);
		
		ArrayList saved = fundingDAO.getSaved();
		assertEquals(4, saved.size());
		Object obj;
		obj = saved.get(0);
		if(!(obj instanceof FundingSourceAltRef)) {
			fail("First should have saved the AltRef");
		}
		obj = saved.get(1);
		if(!(obj instanceof FundingSourceRef)) {
			fail("should have saved the FundingSourceRef");
		}
		obj = saved.get(2);		
		if(!(obj instanceof FundingSourceSuite)) {
			fail("should have saved the FundingSourceSuite");
		} else {
			suite = (FundingSourceSuite)obj;
		}
		obj = saved.get(3);
		if(!(obj instanceof FundingSourceRef)) {
			fail("should have saved the FundingSourceRef");
		}
		Set references = suite.getReferences();
		assertEquals(1, references.size());
		
		FundingSourceRef ref = (FundingSourceRef)references.toArray()[0];
		assertEquals(new Long(21), ref.getSource().getId());
		assertEquals(1, ref.getAltRefs().size());
		
		FundingSourceAltRef tempAltRef = (FundingSourceAltRef)ref.getAltRefs().toArray()[0];
		assertEquals(new Long(42), tempAltRef.getAlternative().getId());
		
		alt = new FundingSourceAlternative();
		altId = new Long(231);
		alt.setId(altId);
		funding = new FundingSource();
		funding.setId(new Long(21));
		alt.setSource(funding);		

		//Set all the preformed information
		fundingDAO.setFundingSuite(suite);
		fundingDAO.setFundingSourceAlternative(alt);
		fundingDAO.clearSaved();
				
		fundingService.relateFundingAlt(suiteId, altId);
		
		saved = fundingDAO.getSaved();
		assertEquals(2, saved.size());
		obj = saved.get(0);
		if(!(obj instanceof FundingSourceAltRef)) {
			fail("First should have saved the AltRef");
		}
		obj = saved.get(1);
		if(!(obj instanceof FundingSourceRef)) {
			fail("should have saved the FundingSourceRef");
		}
		references = suite.getReferences();
		assertEquals(1, references.size());
		
		ref = (FundingSourceRef)references.toArray()[0];
		assertEquals(new Long(21), ref.getSource().getId());
		assertEquals(2, ref.getAltRefs().size());
		
		tempAltRef = (FundingSourceAltRef)ref.getAltRefs().toArray()[1];
		if(tempAltRef.getAlternative().getId().equals(new Long(42))) {
			tempAltRef = (FundingSourceAltRef)ref.getAltRefs().toArray()[0];			
		}
		assertEquals(new Long(231), tempAltRef.getAlternative().getId());
		
		//Now to dereference
		
		//Set all the preformed information
		fundingDAO.setFundingSuite(suite);
		fundingDAO.setFundingSourceAlternative(alt);
		
		fundingDAO.clearSaved();
		fundingDAO.clearDeleted();
				
		Set<FundingSourceRef> altRefs = suite.getReferences();				
		assertEquals(2, ((FundingSourceRef)(altRefs.toArray())[0]).getAltRefs().size());
		
		Set<FundingSourceAltRef> projectAltRefs = ((FundingSourceRef)(altRefs.toArray())[0]).getAltRefs();
		FundingSourceAltRef altRef1 = (FundingSourceAltRef)projectAltRefs.toArray()[0];
		FundingSourceAltRef altRef2 = (FundingSourceAltRef)projectAltRefs.toArray()[1];
		
		fundingDAO.setFundingAlternativeReference(altRef1);
		
		fundingService.derelateFundingAlt(suiteId, altId);		
		assertEquals(1, fundingDAO.getSaved().size());
		assertEquals(1, fundingDAO.getDeleted().size());
		assertEquals(1, ((FundingSourceRef)(suite.getReferences().toArray())[0]).getAltRefs().size());
		
		fundingDAO.clearSaved();
		fundingDAO.clearDeleted();
		
		fundingDAO.setFundingAlternativeReference(altRef2);
		fundingService.derelateFundingAlt(suiteId, new Long(42));		
		assertEquals(0, ((FundingSourceRef)(suite.getReferences().toArray())[0]).getAltRefs().size());
		assertEquals(2, fundingDAO.getDeleted().size());
		assertEquals(0, fundingDAO.getSaved().size());		
	}
}


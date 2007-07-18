package org.pgist.tests.funding;

import java.util.ArrayList;
import java.util.Collection;

import org.pgist.funding.Consumption;
import org.pgist.funding.FundingDAO;
import org.pgist.funding.FundingSource;
import org.pgist.funding.FundingSourceAltRef;
import org.pgist.funding.FundingSourceAlternative;
import org.pgist.funding.FundingSourceRef;
import org.pgist.funding.FundingSourceSuite;
import org.pgist.funding.UnknownFundingSourceException;
import org.pgist.funding.UserCommute;
import org.pgist.funding.UserFundingSourceToll;
import org.pgist.funding.ZipCodeFactor;
import org.pgist.funding.ZipCodeGas;
import org.pgist.users.User;
import org.pgist.users.Vehicle;

/**
 * Use this class to create a mock interface to be used with
 * @author Matt Paulin
 *
 */
public class MockFundingSourceDAO implements FundingDAO {

	//A collection of the objects deleted
	private ArrayList deleted = new ArrayList();
	
	public ArrayList getDeleted() {
		return deleted;
	}
	
	
	public void initializeUser(User user) throws Exception {
		// TODO Auto-generated method stub
		
	}


	public void linkFundingSource(UserFundingSourceToll toll) throws UnknownFundingSourceException, Exception {
		// TODO Auto-generated method stub
		
	}


	/**
	 * Resets the deleted
	 */
	public void clearDeleted() {
		this.deleted.clear();
	}
	
	
	//A collection of all that was saved;
	private ArrayList saved = new ArrayList();
	
	public ArrayList getSaved() {
		return saved;		
	}
	
	/**
	 * Resets the saved collection
	 */
	public void clearSaved() {
		saved.clear();
	}
	public void delete(FundingSource source) {
		this.deleted.add(source);		
	}
	public void delete(FundingSourceAlternative alt) {
		this.deleted.add(alt);
	}
	public void delete(FundingSourceAltRef altRef) {
		this.deleted.add(altRef);
	}
	public void delete(FundingSourceRef fundingRef) {
		this.deleted.add(fundingRef);
	}
	public FundingSourceAlternative getFundingSourceAltById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	private FundingSourceAlternative alt;
	public void setFundingSourceAlternative(FundingSourceAlternative alt) {
		this.alt = alt;
	}
	public FundingSourceAlternative getFundingSourceAlternative(Long altId) {
		return alt;
	}
	
	private FundingSourceAltRef fundingSourceAltRef; 
	public void setFundingAlternativeReference(FundingSourceAltRef altRef1) {
		fundingSourceAltRef = altRef1;
	}
	public FundingSourceAltRef getFundingSourceAlternativeReference(Long altId) {
		return fundingSourceAltRef;
	}
	public FundingSource getFundingSourceById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public FundingSource getFundingSourceByName(String name) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public FundingSourceAlternative getFundingSourceAlternativeByName(String name) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public Collection getFundingSources() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	FundingSourceSuite funSuite;
	public void setFundingSuite(FundingSourceSuite suite) {
		this.funSuite = suite;
	}	
	public FundingSourceSuite getFundingSuite(Long suiteID) throws Exception {
		return this.funSuite;
	}
	public void save(FundingSource source) {
		this.saved.add(source);
		
	}
	public void save(FundingSourceAlternative alt) {
		this.saved.add(alt);		
	}
	public User getUserById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public Object load(Class klass, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public void save(Object object) throws Exception {
		this.saved.add(object);
	}
	public Consumption getConsumptionByIncome(Float incomeLevel) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public ZipCodeFactor getZipCodeFactorByZipCode(String zipcode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public ZipCodeGas getZipCodeGasByZipCode(String zipcode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public void save(Consumption zcg) throws Exception {
		// TODO Auto-generated method stub
		
	}
	public void save(ZipCodeFactor zcg) throws Exception {
		// TODO Auto-generated method stub
		
	}
	public void save(ZipCodeGas zcg) throws Exception {
		// TODO Auto-generated method stub
		
	}
	public void delete(Vehicle v) throws Exception {
		// TODO Auto-generated method stub
		
	}
	public Vehicle getVehicle(Long vehicleId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public void save(Vehicle v) throws Exception {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.pgist.funding.FundingDAO#getCommuteForUser(java.lang.Long)
	 */
	public UserCommute getCommuteForUser(Long userId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

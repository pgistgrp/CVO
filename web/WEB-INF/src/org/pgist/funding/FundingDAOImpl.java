package org.pgist.funding;

import java.util.Collection;
import java.util.List;

import org.pgist.system.BaseDAOImpl;
import org.pgist.users.Vehicle;


/**
 * 
 * @author kenny
 *
 */
public class FundingDAOImpl extends BaseDAOImpl implements FundingDAO {
    
	public FundingSource getFundingSourceById(Long id) throws Exception {
        return (FundingSource) getHibernateTemplate().load(FundingSource.class, id);
    }//getFundingSourceById()



    private static final String hql_getFundingSourceByName = "from FundingSource fs where lower(fs.name)=?";
    
    
    public FundingSource getFundingSourceByName(String name) throws Exception {
        List list = getHibernateTemplate().find(hql_getFundingSourceByName, name);
        
        if (list.size()==0) return null;
        
        return (FundingSource) list.get(0);
    }//getFundingSourceByName()


    public Collection getFundingSources() throws Exception {
    	return getHibernateTemplate().find("from FundingSource fs order by fs.name asc");
    }//getFundingSources()

	public void save(FundingSource source) {
		getHibernateTemplate().saveOrUpdate(source);		
	}

	public void delete(FundingSource source) {
        getHibernateTemplate().delete(source);		
	}
    
    public FundingSourceAlternative getFundingSourceAltById(Long id) throws Exception {
        return (FundingSourceAlternative) getHibernateTemplate().load(FundingSourceAlternative.class, id);
    }//getFundingSourceAltById()
    
	public void save(FundingSourceAlternative alt) {
		getHibernateTemplate().saveOrUpdate(alt);				
	}

	public void delete(FundingSourceAlternative alt) {
        getHibernateTemplate().delete(alt);		
	}
    
	
	
    // ************* Funding Suite ******************************** 
    
	public void delete(FundingSourceAltRef altRef) {
		getHibernateTemplate().delete(altRef);
	}


	public void delete(FundingSourceRef fundingRef) {
        getHibernateTemplate().delete(fundingRef);		
	}


	public FundingSourceAlternative getFundingSourceAlternative(Long altId) {
		return (FundingSourceAlternative)getHibernateTemplate().load(FundingSourceAlternative.class, altId);
	}


	public FundingSourceAltRef getFundingSourceAlternativeReference(Long altId) {
		return (FundingSourceAltRef)getHibernateTemplate().get(FundingSourceAltRef.class, altId);		
	}

	public FundingSourceSuite getFundingSuite(Long suiteID) throws Exception {
		return (FundingSourceSuite)getHibernateTemplate().load(FundingSourceSuite.class, suiteID);
	}	
	

    // ************* Tax Calculator ******************************** 
    private static final String hql_getZipCodeGasByZipCode = "from ZipCodeGas zcg where zcg.zipcode=?";
    public ZipCodeGas getZipCodeGasByZipCode(String zipcode) throws Exception {
        List list = getHibernateTemplate().find(hql_getZipCodeGasByZipCode, zipcode);
        
        if (list.size()==0) return null;
        
        return (ZipCodeGas) list.get(0);
    }//getFundingSourceAltById()
    
	public void save(ZipCodeGas zcg) throws Exception {
		getHibernateTemplate().saveOrUpdate(zcg);				
	}
	
    private static final String hql_getZipCodeFactorByZipCode = "from ZipCodeFactor zcg where zcg.zipcode=?";
    public ZipCodeFactor getZipCodeFactorByZipCode(String zipcode) throws Exception {
        List list = getHibernateTemplate().find(hql_getZipCodeFactorByZipCode, zipcode);
        
        if (list.size()==0) return null;
        
        return (ZipCodeFactor) list.get(0);
    }//getFundingSourceAltById()
    
	public void save(ZipCodeFactor zcg) throws Exception {
		getHibernateTemplate().saveOrUpdate(zcg);				
	}


    private static final String hql_getConsumptionByIncomeLevel = "from Consumption zcg where zcg.incomeLower <? and zcg.incomeUpper > ?";
    public Consumption getConsumptionByIncome(Float incomeLevel) throws Exception {
        List list = getHibernateTemplate().find(hql_getConsumptionByIncomeLevel, incomeLevel);
        
        if (list.size()==0) return null;
        
        return (Consumption) list.get(0);
    }//getFundingSourceAltById()
    
	public void save(Consumption zcg) throws Exception {
		getHibernateTemplate().saveOrUpdate(zcg);				
	}
	
    public void save(Vehicle v) throws Exception {
    	getHibernateTemplate().saveOrUpdate(v);
    }
    public void delete(Vehicle v) throws Exception {
    	getHibernateTemplate().delete(v);
    }
    public Vehicle getVehicle(Long vehicleId) throws Exception {
		return (Vehicle)getHibernateTemplate().get(Vehicle.class, vehicleId);		    	
    }
}//class FundingDAOImpl

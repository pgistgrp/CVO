package org.pgist.funding;

import java.util.Collection;

import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public class FundingServiceImpl implements FundingService {
    
    
    private FundingDAO fundingDAO;
    
    
    public void setFundingDAO(FundingDAO fundingDAO) {
        this.fundingDAO = fundingDAO;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public Collection getFundingSources() throws Exception {
        return fundingDAO.getFundingSources();
    }//getFundingSources()


    public Collection getFundingSources(PageSetting setting) throws Exception {
        return fundingDAO.getFundingSources(setting);
    }//getFundingSources()


    public FundingSource createFundingSource(String name) throws Exception {
        FundingSource funding = fundingDAO.getFundingSourceByName(name);
        if (funding!=null) throw new Exception("another funding source with the same name already exists");
        
        funding = new FundingSource();
        funding.setName(name);
        
        fundingDAO.save(funding);
        
        return funding;
    }//createFundingSource()


    public void editFundingSource(Long id, String name) throws Exception {
        FundingSource funding = fundingDAO.getFundingSourceById(id);
        if (funding==null) throw new Exception("can't find this funding source");
        
        funding.setName(name);
        
        fundingDAO.save(funding);
    }//editFundingSource()


    public FundingSourceAlternative createFundingSourceAlt(String name, float revenue, float taxRate) throws Exception {
        return null;
    }//createFundingSourceAlt()


    public void editFundingSourceAlt(Long id, String name, float revenue, float taxRate) throws Exception {
    }//editFundingSourceAlt()


    public void deleteFundingSource(Long id) throws Exception {
    }//deleteFundingSource()


    public void deleteFundingSourceAlt(Long id) throws Exception {
    }//deleteFundingSourceAlt()


    /**
     * Setup the association between funding sources and CCT.
     * 
     * @param cctId id of the CCT object to be associated
     * @param ids ids of FundingSource objects to be assosicated
     * @throws Exception
     */
    public void setupFundingSourcesForCCT(Long cctId, String[] ids) throws Exception {
        /*
         * TODO:
         *   Load CCT object by cctId, throw exception if failed
         *   Load each ProjectAlternative object by id, throw exception if any failed
         *   put each ProjectAlternative object to CCT.projects
         *   persist objects
         */
    }//setupFundingSourcesForCCT()
    
    
}//class FundingServiceImpl

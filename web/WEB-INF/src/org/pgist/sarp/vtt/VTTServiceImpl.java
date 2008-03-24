package org.pgist.sarp.vtt;

import java.util.List;

import org.pgist.sarp.bct.BCTDAO;
import org.pgist.sarp.cht.CHTDAO;
import org.pgist.sarp.cst.CSTDAO;
import org.pgist.sarp.cst.CategoryReference;
import org.pgist.sarp.drt.InfoObject;
import org.pgist.system.SystemDAO;
import org.pgist.tagging.TagDAO;
import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 */
public class VTTServiceImpl implements VTTService {
    
    
    private BCTDAO bctDAO = null;

    private CSTDAO cstDAO = null;
    
    private CHTDAO chtDAO = null;
    
    private VTTDAO vttDAO = null;
    
    private TagDAO tagDAO = null;
    
    private SystemDAO systemDAO = null;

    
    public void setCstDAO(CSTDAO cstDAO) {
        this.cstDAO = cstDAO;
    }


    public void setBctDAO(BCTDAO bctDAO) {
        this.bctDAO = bctDAO;
    }


    public void setChtDAO(CHTDAO chtDAO) {
        this.chtDAO = chtDAO;
    }


    public VTTDAO getVttDAO() {
        return vttDAO;
    }


    public void setVttDAO(VTTDAO vttDAO) {
        this.vttDAO = vttDAO;
    }


    public void setTagDAO(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }


    public void setSystemDAO(SystemDAO systemDAO) {
        this.systemDAO = systemDAO;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    @Override
    public void toggleVTT(Long chtId, boolean closed) throws Exception {
        // TODO Auto-generated method stub
    } //toggleVTT()


    @Override
    public InfoObject publish(Long vttId, String property) throws Exception {
        // TODO Auto-generated method stub
        return null;
    } //publish()


    @Override
    public VTT createVTT(Long id, Long chtId, String name, String purpose, String instruction) throws Exception {
        // TODO Auto-generated method stub
        return null;
    } //createVTT()


    @Override
    public List<User> getOtherUsers(VTT vtt) throws Exception {
        // TODO Auto-generated method stub
        return null;
    } //getOtherUsers()


    @Override
    public VTT getVTTById(Long id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    } //getVTTById()


    @Override
    public CategoryReference setRootCatReference(VTT vtt, User user) throws Exception {
        // TODO Auto-generated method stub
        return null;
    } //setRootCatReference()


    @Override
    public void setClearVTTWinner(Long vttId) throws Exception {
        // TODO Auto-generated method stub
    } //setClearVTTWinner()
    
    
}//class VTTServiceImpl

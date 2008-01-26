package org.pgist.sarp.drt;

import org.pgist.system.BaseDAOImpl;


/**
 * @author kenny
 *
 */
public class DRTDAOImpl extends BaseDAOImpl implements DRTDAO {
	
	
	@Override
	public InfoObject getInfoObjectById(Long oid) throws Exception {
		return (InfoObject) load(InfoObject.class, oid);
	}//getInfoObjectById()
	
	
}//class DRTDAOImpl

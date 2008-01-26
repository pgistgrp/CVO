package org.pgist.sarp.drt;

import org.pgist.system.BaseDAO;


/**
 * @author kenny
 *
 */
public interface DRTDAO extends BaseDAO {
	
	
	InfoObject getInfoObjectById(Long oid) throws Exception;
	
	
}//interface DRTDAO

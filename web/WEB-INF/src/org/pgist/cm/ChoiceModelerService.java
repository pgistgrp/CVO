package org.pgist.cm;

import java.util.List;


/**
 * 
 * @author kenny
 *
 */
public interface ChoiceModelerService {
    
    
    String echo(String name) throws Exception;
    
    List getList() throws Exception;

    int[] getArray(int[] x) throws Exception;

    void test() throws Exception;
    
    
}//interface ChoiceModelerService

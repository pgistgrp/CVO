package org.pgist.cm;

import java.util.List;

import org.pgist.ws.WebServiceProvider;


/**
 * 
 * @author kenny
 *
 */
public class ChoiceModelerServiceImpl implements ChoiceModelerService {
    
    
    private WebServiceProvider provider;
    
    
    public void setProvider(WebServiceProvider provider) {
        this.provider = provider;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public String echo(String name) throws Exception {
        return (String) provider.call("echo", name);
    }//echo()
    
    
    public List getList() throws Exception {
        return (List) provider.call("getList");
    }//echo()
    
    
    public int[] getArray(int[] x) throws Exception {
        return (int[]) provider.call("getArray", new Object[] { x });
    }//getArray()


    public void test() throws Exception {
        Object obj;
        
        obj = provider.call("getMap");
        System.out.println("getMap ---> "+obj.getClass().getName());
        
        obj = provider.call("getSet");
        System.out.println("getSet ---> "+obj.getClass().getName());
        
        obj = provider.call("getVector");
        System.out.println("getVector ---> "+obj.getClass().getName());
        
        obj = provider.call("getModel");
        System.out.println("getModel ---> "+obj.getClass().getName());
    }//test()
    
    
}//class ChoiceModelerServiceImpl

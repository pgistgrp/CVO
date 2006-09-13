package org.pgist.cm;

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
    
    
}//class ChoiceModelerServiceImpl

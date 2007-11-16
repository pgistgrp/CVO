package org.pgist.ws;

import java.net.URL;
import java.util.Properties;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;


/**
 * 
 * @author kenny
 *
 */
public class WebServiceProviderImpl implements WebServiceProvider {
    
    
    private URL endpoint = null;
    
    private Properties props = new Properties();
    
    
    public WebServiceProviderImpl() throws Exception {
        props.put("protocol", "http");
        props.put("hostname", "localhost");
        props.put("port", "8080");
        props.put("service", "/axis/WebService");
        
        endpoint = new URL("http://localhost:8080/axis/WebService");
    }//WebServiceProviderImpl()
    
    
    public void setProps(Properties props) throws Exception {
        this.props = props;
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(props.getProperty("protocol"))
          .append("://")
          .append(props.getProperty("hostname"))
          .append(":")
          .append(props.getProperty("port"))
          .append(props.getProperty("service"));
        
        endpoint = new URL(sb.toString());
    }//setProps()


    /**
     * @param method
     * @param args var arguments of Objects.
     */
    public Object call(String method, Object... args) throws Exception {
        Service service = new Service();
        
        Call calling = (Call) service.createCall();
        
        calling.setTargetEndpointAddress(endpoint);
        calling.setOperationName(new QName("http://soapinterop.org/", method));
        
        return calling.invoke(args);
    }//call()
    
    
}//class WebServiceProviderImpl

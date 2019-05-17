package services.hpexstream.engine;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class EngineWebServiceLocator extends Service implements EngineWebService{

	private String EngineServicePort_address;
	
	
	protected EngineWebServiceLocator(URL wsdlDocumentLocation, QName serviceName) {
		super(wsdlDocumentLocation, serviceName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public EwsComposeResponse compose(EwsComposeRequest ewsComposeRequest) throws EngineServiceException_Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

}

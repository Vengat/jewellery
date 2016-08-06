package org.jewelry.tomcatrelated;

import java.io.IOException;
import javax.servlet.*;
import org.apache.catalina.Valve;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.apache.log4j.*;


public class SimpleLoggingValve extends ValveBase{
	
	private static final Logger logger = Logger.getLogger(SimpleLoggingValve.class);
	
	@Override
	public void invoke(Request request, Response response) throws IOException, ServletException {
		
		String remoteAddress = request.getRemoteAddr();
		String requestUri = request.getRequestURI();
		logger.info("URI " + requestUri+ " accessed from remote address: "+remoteAddress); 	
		Valve nextValve = getNext(); 
		if(nextValve!=null){
			nextValve.invoke(request, response); 
		}
	 }


	}
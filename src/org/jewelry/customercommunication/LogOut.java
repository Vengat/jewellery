package org.jewelry.customercommunication;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.*;

/**
 * Servlet implementation class LogOut
 */
@WebServlet(description = "This servlet will log out user or end the user session", urlPatterns = { "/LogOut" })
public class LogOut extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(LogOut.class);
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.info("Logging out get");
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.info("Logging out");
		
		//Invalidating regular http
		//request.getSession(true).invalidate();
		
		// Invalidate the SSL Session
		org.apache.tomcat.util.net.SSLSessionManager mgr =
		    (org.apache.tomcat.util.net.SSLSessionManager)
		    request.getAttribute("javax.servlet.request.ssl_session_mgr");
		mgr.invalidateSession();
		
		// Close the connection since the SSL session will be active until the connection
		// is closed
		response.setHeader("Connection", "close");
		    
		
		response.setContentType("text/html");
		
		
		//response.sendRedirect("/jewellery/secure/login.jsp");
		PrintWriter out =  response.getWriter();
		
		String title= "You are logged out successfully";
		String docType =
			  "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
			  "Transitional//EN\">\n";

		out.println(docType +
			    "<HTML>\n" +
			    "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n" +
			    "<BODY BGCOLOR=\"#26E0EA\">" +
			    "<H1>" + title + "</H1>"+
			    "<BR><BR>");

		out.println("<a href=\"/jewellery/jsplib/index.jsp\"> Log me back in</a>");
		out.println("<P> Goodbye We hope you enjoyed spending time here!!!");
		out.println("<P> Looking forward for your next visit :)");
        out.println("<P> Do let us know your feedback and comments :-)");
        out.println("</BODY></HTML>");

        logger.info("Logged out");
		//RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jewellery/secure/login.jsp");
		//requestDispatcher.forward(request, response);


	}

}

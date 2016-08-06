package org.jewelry.customercommunication;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.*;

/**
 * Servlet implementation class WelcomePatron
 */
@WebServlet(description = "This servlet welcomes the customer who signs in", urlPatterns = { "/WelcomePatron" })
public class WelcomePatron extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(WelcomePatron.class);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String userName=(String) session.getAttribute("userName");	
		
		logger.info("userName in WelcomePatron--->"+userName);
		if(userName==null || userName.equalsIgnoreCase(request.getParameter("userName"))==false) userName=request.getParameter("userName");
		session.setAttribute("userName", userName);
		response.setContentType("text/html");
		PrintWriter out =  response.getWriter();
		
		String title="Welcome Patron";
		String docType =
			  "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
			  "Transitional//EN\">\n";
		out.println(docType +
			    "<HTML>\n" +
			    "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n" +
			    "<BODY BGCOLOR=\"#26E0EA\">" +
			    "<H1>" + title + "</H1>"+
			    "<BR><BR>");
		out.println("<P> Hello "+userName+"  Welcome. We hope you will enjoy your shopping experience here!!!");
        out.println("<P> Do let us know your feedback and comments :-)");
        out.println("</BODY></HTML>");
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	

}

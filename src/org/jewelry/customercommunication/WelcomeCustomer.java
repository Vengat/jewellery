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
 * Servlet implementation class WelcomeCustomer
 */
@WebServlet("/WelcomeCustomer")
public class WelcomeCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(WelcomeCustomer.class);

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.info("In WelcomeCustomer servlet");
		String accessingIP=request.getRemoteAddr();
		logger.info("Accessing ip--->"+accessingIP);
		
		String sslID = (String)request.getAttribute("javax.servlet.request.ssl_session_id"); 
		logger.info("SSL Session id is --->"+sslID);
		HttpSession session = request.getSession();
		String customer = request.getRemoteUser();
		
		session.setAttribute("userName", customer);
		response.setContentType("text/html");
		PrintWriter out =  response.getWriter();
		
		String title="Welcome Customer";
		
		String url = response.encodeURL("/jewellery/LogOut");
		
		String url1 = response.encodeURL("/jewellery/TechBooksPage");
		String techBooksCatalogLink = "<a href="+url1+"> Tech Books Catalog</a>";
		
		String url2 = response.encodeURL("/jewellery/KidsBooksPage");
		String kidsBooksCatalogLink = "<a href="+url2+"> Kids Books Catalog</a>";
		
		String docType =
			  "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
			  "Transitional//EN\">\n";
		out.println(docType +
			    "<HTML>\n" +
			    "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n" +
			    "<BODY BGCOLOR=\"#26E0EA\">" +
			    "<H1>" + title + "</H1>"+
			    "<BR><BR>");
		//out.println("<a href=\"/jewellery/LogOut\"> Logout</a>");
		out.println("<a href="+url+"> Logout</a>");
		out.println("<P> Hello "+customer+"  Welcome. We hope you will enjoy your shopping experience here!!!");
        out.println("<P> Do let us know your feedback and comments :-)");
        out.println("<P> We are monitoring your IP--->"+accessingIP);
        out.println("<P> Visit our Techbooks catalog here--->"+techBooksCatalogLink);
        out.println("<P> Visit our Kids' bookd catalog here--->"+kidsBooksCatalogLink);
        out.println("</BODY></HTML>");

	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);		
	}

}

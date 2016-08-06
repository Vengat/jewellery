package org.jewellery.catalog;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import org.apache.log4j.Logger;
import org.jewelry.utilities.ServletUtilities;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** Base class for pages showing catalog entries.
* Servlets that extend this class must specify
* the catalog entries that they are selling and the page
* title <I>before</I> the servlet is ever accessed. This
* is done by putting calls to setItems and setTitle
* in init.
*/

@WebServlet("/CatalogPage")
public abstract class CatalogPage extends HttpServlet {
	private static Logger logger=Logger.getLogger(CatalogPage.class);
	private CatalogItem[] items;
	private String[] itemIDs;
	private String title;
	/** Given an array of item IDs, look them up in the
	 * * into the items array. The CatalogItem contains a short
	 * description, a long description, and a price,
	 * using the item ID as the unique key.
	 * <P>
	 * Servlets that extend CatalogPage <B>must</B> call
	 * this method (usually from init) before the servlet
	 * is accessed
	 */
	
	protected void setItems(String[] itemIDs) {
		  logger.info("***************************************");
		  logger.info("Creating catalog page");
		  logger.info("***************************************");


		logger.info("setItems start");
		logger.info("itemIDs "+itemIDs);
		this.itemIDs = itemIDs;
		items = new CatalogItem[itemIDs.length];
		for(int i=0; i<items.length; i++) {
		    items[i] = Catalog.getItem(itemIDs[i]);
		    logger.info(items[i]);
		    logger.info(items[i].getShortDescription());
		    logger.info("setItems");
		}
		logger.info("-----------");
	}
	
	/** Sets the page title, which is displayed in
	* an H1 heading in resultant page.
	* <P>
	* Servlets that extend CatalogPage <B>must</B> call
	* this method (usually from init) before the servlet
	* is accessed.
	*/
	
	protected void setTitle(String title) {
		this.title = title;
	}
	
	/** First display title, then, for each catalog item,
	* put its short description in a level-two (H2) heading
	* with the price in parentheses and long description
	* below. Below each entry, put an order button
	* that submits info to the OrderPage servlet for
	* the associated catalog entry.
	* <P>
	* To see the HTML that results from this method, do
	* "View Source" on KidsBooksPage or TechBooksPage, two
	* concrete classes that extend this abstract class.
	*/
	
	public void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		
		String url = response.encodeURL("/jewellery/LogOut");
		
		HttpSession session = request.getSession();
		
		//sessionNonce =   session.getAttribute("org.apache.catalina.filters.CSRF_NONCE");
		//logger.info("sessionNonce --->"+sessionNonce);

		String sslID = (String)request.getAttribute("javax.servlet.request.ssl_session_id"); 
		logger.info("SSL Session id is --->"+sslID);

		Enumeration<String> sessionAttrs = session.getAttributeNames();
		while(sessionAttrs.hasMoreElements()){
			String sessionDets = (String) sessionAttrs.nextElement();
			
		}
		
		//String url1=response.encodeURL("/jewellery/jsplib/index.jsp");
		
		if (items == null) {
			response.sendError(response.SC_NOT_FOUND,
			"Missing Items.");
			return;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String docType =
			"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
			"Transitional//EN\">\n";
		out.println(docType +
				"<HTML>\n" +
				"<HEAD><TITLE>" + title + "</TITLE></HEAD>\n" +
				"<BODY BGCOLOR=\"#FDF5E6\">\n" +
				"<H1 ALIGN=\"CENTER\">" + title + "</H1>");
		
		 out.println(
	    		    "<div id=\"Main\">\n"+
	    		    "<div id=\"Tiles\">\n"+
	    		    "<ul id=\"listTiles\">\n"	    		    
	    		    );
		
		CatalogItem item;
		for(int i=0; i<items.length; i++) {
			
			out.println("<HR>");
			item = items[i];
			logger.info("Adding item "+item);
			// Show error message if subclass lists item ID
			// that's not in the catalog.
		if (item == null) {
			out.println("<FONT COLOR=\"RED\">" +
						"Unknown item ID " + itemIDs[i] +
						"</FONT>");
		}else{
			out.println();
			String formURL =
				"/jewellery/OrderPage";
				// Pass URLs that reference own site through encodeURL.
				formURL = response.encodeURL(formURL);
			
			/*int val = formURL.indexOf("?");
			String formURL1 = formURL.substring(val+1);
			logger.info(formURL1);	
			String[] splitformURL = formURL1.split("=");*/
			String[] splitformURL = ServletUtilities.nonceNameValue(formURL);
			String nonceName = splitformURL[0];
			String nonceValue = splitformURL[1];
			
				
				logger.info("formURL "+formURL+" item.getItemID()"+item.getItemID()+" item.getShortDescription() "+item.getShortDescription()+" item.getCost() "+item.getCost() +" item.getLongDescription()"+item.getLongDescription());
			/*out.println
				("<FORM ACTION=\"" + formURL + "\">\n" +
						"<INPUT TYPE=\"HIDDEN\" NAME=\"itemID\" " +
						" VALUE=\"" + item.getItemID() + "\">\n" +
						"<INPUT TYPE=\"HIDDEN\" NAME=\""+nonceName+"\" " +
						" VALUE=\"" + nonceValue+ "\">\n" +
						"<H2>" + item.getShortDescription() +
						" ($" + item.getCost() + ")</H2>\n" +
						item.getLongDescription() + "\n" +
						"<P>\n<CENTER>\n" +
						"<INPUT TYPE=\"SUBMIT\" " +
						"VALUE=\"Add to Shopping Cart\">\n" +
						"</CENTER>\n<P>\n</FORM>");*/
			
			out.println("<li id=\""+i+"\">\n"+
						"<a href="+formURL+"&itemID="+item.getItemID() +">Buy this</a>\n"+
						"<div id="+i+">\n"+
						"<span>" + item.getShortDescription() +"</span>\n"+
						"<span>($" + item.getCost() +")</span>\n"+
						"<span>" + item.getLongDescription() +"</span>\n");

				}
			}
		
		    
		    								
		    
			out.println("<HR>\n</BODY>\n");
			out.println("<a href="+url+"> Logout</a>\n");
			out.println("</HTML>");
		}
	}
	


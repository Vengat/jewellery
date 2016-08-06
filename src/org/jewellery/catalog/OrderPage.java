package org.jewellery.catalog;


import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;
import org.apache.log4j.Logger;
import org.jewelry.utilities.ServletUtilities;

/** Shows all items currently in ShoppingCart. Clients
 *  have their own session that keeps track of which
 *  ShoppingCart is theirs. If this is their first visit
 *  to the order page, a new shopping cart is created.
 *  Usually, people come to this page by way of a page
 *  showing catalog entries, so this page adds an additional
 *  item to the shopping cart. But users can also
 *  bookmark this page, access it from their history list,
 *  or be sent back to it by clicking on the "Update Order"
 *  button after changing the number of items ordered.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages 2nd Edition
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2003 Marty Hall; may be freely used or adapted.
 */
@WebServlet("/OrderPage")
public class OrderPage extends HttpServlet {
	
  private static Logger logger=Logger.getLogger(OrderPage.class);	
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
	  logger.info("***************************************");
	  logger.info("Creating a shopping cart in OrderPage");
	  logger.info("***************************************");
    HttpSession session = request.getSession();
    ShoppingCart cart;
    synchronized(session) {
      cart = (ShoppingCart)session.getAttribute("shoppingCart");
      // New visitors get a fresh shopping cart.
      // Previous visitors keep using their existing cart.
      if (cart == null) {
    	  logger.info("Creating a new cart instnce");
        cart = new ShoppingCart();
        session.setAttribute("shoppingCart", cart);
      }else{
    	  logger.info("Using existing cart instance");
      }
      
      String itemID = request.getParameter("itemID");
      if (itemID != null) {
        String numItemsString =
          request.getParameter("numItems");
        if (numItemsString == null) {
          // If request specified an ID but no number,
          // then customers came here via an "Add Item to Cart"
          // button on a catalog page.
          cart.addItem(itemID);
          logger.info("First time order");
          logger.info("Item id "+itemID);
        } else {
          // If request specified an ID and number, then
          // customers came here via an "Update Order" button
          // after changing the number of items in order.
          // Note that specifying a number of 0 results
          // in item being deleted from cart.
        	logger.info("Update order");
        	logger.info("Item id "+itemID+"numItems "+numItemsString);
          int numItems;
          try {
            numItems = Integer.parseInt(numItemsString);
          } catch(NumberFormatException nfe) {
            numItems = 1;
          }
          cart.setNumOrdered(itemID, numItems);
        }
      }
    }
    // Whether or not the customer changed the order, show
    // order status.
    
	String url1 = response.encodeURL("/jewellery/TechBooksPage");
	String techBooksCatalogLink = "<a href="+url1+"> Tech Books Catalog</a>";
	
	String url2 = response.encodeURL("/jewellery/KidsBooksPage");
	String kidsBooksCatalogLink = "<a href="+url2+"> Kids Books Catalog</a>";

    
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String title = "Status of Your Order";
    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    out.println(docType +
                "<HTML>\n" +
                "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n" +
                "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                "<H1 ALIGN=\"CENTER\">" + title + "</H1>\n");
    out.println(techBooksCatalogLink+"\n");
    out.println( kidsBooksCatalogLink+"\n"+"\n"+"\n");
    synchronized(session) {
      List itemsOrdered = cart.getItemsOrdered();
      if (itemsOrdered.size() == 0) {
        out.println("<H2><I>No items in your cart...</I></H2>");
      } else {
        // If there is at least one item in cart, show table
        // of items ordered.
        out.println
          ("<TABLE BORDER=1 ALIGN=\"CENTER\">\n" +
           "<TR BGCOLOR=\"#FFAD00\">\n" +
           "  <TH>Item ID<TH>Description\n" +
           "  <TH>Unit Cost<TH>Number<TH>Total Cost");
        ItemOrder order;
        // Rounds to two decimal places, inserts dollar
        // sign (or other currency symbol), etc., as
        // appropriate in current Locale.
        NumberFormat formatter =
          NumberFormat.getCurrencyInstance();
        // For each entry in shopping cart, make
        // table row showing ID, description, per-item
        // cost, number ordered, and total cost.
        // Put number ordered in textfield that user
        // can change, with "Update Order" button next
        // to it, which resubmits to this same page
        // but specifying a different number of items.
        
        String selfURL = "/jewellery/OrderPage";
        selfURL = response.encodeURL(selfURL);
		String[] splitselfURL = ServletUtilities.nonceNameValue(selfURL);
		String selfURLnonceName = splitselfURL[0];
		String selfURLnonceValue = splitselfURL[1];

        for(int i=0; i<itemsOrdered.size(); i++) {
        	logger.info("Submit to the same url");
        	
          order = (ItemOrder)itemsOrdered.get(i);
         out.println
            ("<TR>\n" +
             "  <TD>" + order.getItemID() + "\n" +
             "  <TD>" + order.getShortDescription() + "\n" +
             "  <TD>" +
             formatter.format(order.getUnitCost()) + "\n" +
             "  <TD>" +
             "<FORM ACTION="+selfURL+ ">\n" +  // Submit to current URL
             "<INPUT TYPE=\"HIDDEN\" NAME=\""+selfURLnonceName+ "\"\n" +
             "       VALUE=\"" + selfURLnonceValue + "\">\n" +
             "<INPUT TYPE=\"TEXT\" NAME=\"numItems\"\n" +
             "       SIZE=3 VALUE=\"" + 
             order.getNumItems() + "\">\n" +
             "<INPUT TYPE=\"HIDDEN\" NAME=\"itemID\"\n" +
             "       VALUE=\"" + order.getItemID() + "\">\n" +
             "<SMALL>\n" +
             "<INPUT TYPE=\"SUBMIT\"\n "+
             "       VALUE=\"Update Order\">\n" +
             "</SMALL>\n" +
             "</FORM>\n" +
             "  <TD>" +
             formatter.format(order.getTotalCost()));
        }
          
        /*  out.println
          ("<TR>\n" +
           "  <TD>" + order.getItemID() + "\n" +
           "  <TD>" + order.getShortDescription() + "\n" +
           "  <TD>" +
           formatter.format(order.getUnitCost()) + "\n" +
           "  <TD>" +
           "<FORM>\n" +  // Submit to current URL
           "<INPUT TYPE=\"HIDDEN\" NAME=\"itemID\"\n" +
           "       VALUE=\"" + order.getItemID() + "\">\n" +
           "<INPUT TYPE=\"TEXT\" NAME=\"numItems\"\n" +
           "       SIZE=3 VALUE=\"" + 
           order.getNumItems() + "\">\n" +
           "<a href="+selfURL + "&itemID="+order.getItemID()+ "&numItems="+order.getNumItems()+ ">Update Order</a>\n" +  // Submit to current URL
           "<SMALL>\n" +
           "<INPUT TYPE=\"SUBMIT\"\n "+
           //"       VALUE=\"Update Order\">\n" +
           "       VALUE=\"Update Order\">\n" +
           "</SMALL>\n" +
           "</FORM>\n" +
           "  <TD>" +
           formatter.format(order.getTotalCost()));
      }  */
        
        String originURL = request.getRequestURL().toString();
        originURL = response.encodeURL(originURL);
          
        String checkoutURL =
          response.encodeURL("formlib/Checkout.html");
        
		String[] splitformURL = ServletUtilities.nonceNameValue(checkoutURL);
		String nonceName = splitformURL[0];
		String nonceValue = splitformURL[1];

		
        
        // "Proceed to Checkout" button below table
        out.println
          ("</TABLE>\n" +
           "<FORM ACTION=\"" + checkoutURL + "\">\n" +
			"<INPUT TYPE=\"HIDDEN\" NAME=\""+nonceName+"\" " +
			" VALUE=\"" + nonceValue+ "\">\n" +
           "<BIG><CENTER>\n" +
           "<INPUT TYPE=\"SUBMIT\"\n" +
           "       VALUE=\"Proceed to Checkout\">\n" +
           "</CENTER></BIG></FORM>");
      }
      out.println("</BODY></HTML>");
    }
  }
}

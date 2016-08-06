package org.jewellery.catalog;

import java.util.*;

import org.apache.log4j.Logger;
/** A shopping cart data structure used to track orders.
* The OrderPage servlet associates one of these carts
* with each user session.
*/
public class ShoppingCart {
	
	private ArrayList itemsOrdered;
	
	private static Logger logger=Logger.getLogger(ShoppingCart.class);
	
	/** Builds an empty shopping cart. */
	
	public ShoppingCart() {
		  logger.info("***************************************");
		  logger.info("Creating a shopping cart in ShoppingCart.jave");
		  logger.info("***************************************");

		itemsOrdered = new ArrayList();
	}
	
	/** Returns List of ItemOrder entries giving
	* Item and number ordered. Declared as List instead
	* of ArrayList so that underlying implementation
	* can be changed later.
	*/
	
	public List getItemsOrdered() {
		return(itemsOrdered);
	}
	
	/** Looks through cart to see if it already contains
	* an order entry corresponding to item ID. If it does,
	* increments the number ordered. If not, looks up
	* Item in catalog and adds an order entry for it.
	*/
	
	public synchronized void addItem(String itemID) {
		ItemOrder order;
		logger.info("add item");
		for(int i=0; i<itemsOrdered.size(); i++) {
			order = (ItemOrder)itemsOrdered.get(i);
			if (order.getItemID().equals(itemID)) {
				order.incrementNumItems();
				return;
			}
		}
		logger.info("Creating a new ItemOrder");
		ItemOrder newOrder = new ItemOrder(Catalog.getItem(itemID));
		itemsOrdered.add(newOrder);
		}
	
		/** Looks through cart to find order entry corresponding
		 * to item ID listed. If the designated number
		 * is positive, sets it. If designated number is 0
		 * (or, negative due to a user input error), deletes
		 * item from cart.
		 */
	
		public synchronized void setNumOrdered(String itemID,
				int numOrdered) {
			ItemOrder order;
			for(int i=0; i<itemsOrdered.size(); i++) {
				order = (ItemOrder)itemsOrdered.get(i);
				if (order.getItemID().equals(itemID)) {
					if (numOrdered <= 0) {
						itemsOrdered.remove(i);
					} else {
						order.setNumItems(numOrdered);
					}
					return;
				}
			}
			
			ItemOrder newOrder =
				new ItemOrder(Catalog.getItem(itemID));
	
			itemsOrdered.add(newOrder);
		}
	}
	
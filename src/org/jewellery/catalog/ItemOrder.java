package org.jewellery.catalog;

import org.apache.log4j.Logger;

/** Associates a catalog Item with a specific order by
 *  keeping track of the number ordered and the total price.
 *  Also provides some convenience methods to get at the
 *  CatalogItem data without extracting the CatalogItem
 *  separately.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages 2nd Edition
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2003 Marty Hall; may be freely used or adapted.
 */

public class ItemOrder {
  private CatalogItem item;
  private int numItems;
  
  private static Logger logger=Logger.getLogger(ItemOrder.class);

  public ItemOrder(CatalogItem item) {
	  logger.info("***************************************");
	  logger.info("In ItemOrder.java");
	  logger.info("***************************************");

    setItem(item);
    setNumItems(1);
  }

  public CatalogItem getItem() {
	  logger.info("In ItemOrder.java getItem");
    return(item);
  }

  protected void setItem(CatalogItem item) {
	  logger.info("In ItemOrder.java setItem");
    this.item = item;
  }

  public String getItemID() {
	  logger.info("In ItemOrder.java getItemID");
    return(getItem().getItemID());
  }

  public String getShortDescription() {
	  logger.info("In ItemOrder.java getShortDescription");
    return(getItem().getShortDescription());
  }

  public String getLongDescription() {
	  logger.info("In ItemOrder.java getLongDescription");
    return(getItem().getLongDescription());
  }

  public double getUnitCost() {
	  logger.info("In ItemOrder.java getUnitCost");
    return(getItem().getCost());
  }
  
  public int getNumItems() {
	  logger.info("In ItemOrder.java getNumItems");
    return(numItems);
  }

  public void setNumItems(int n) {
	  logger.info("In ItemOrder.java setNumItems");
    this.numItems = n;
  }

  public void incrementNumItems() {
	  logger.info("incrementNumItems in ItemOrder");
    setNumItems(getNumItems() + 1);
  }

  public void cancelOrder() {
	  logger.info("Cancel Order");
    setNumItems(0);
  }

  public double getTotalCost() {
	  logger.info("In ItemOrder.java getTotalCost");
    return(getNumItems() * getUnitCost());
  }
}

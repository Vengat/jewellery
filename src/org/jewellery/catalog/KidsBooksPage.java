package org.jewellery.catalog;

import javax.servlet.annotation.WebServlet;

/** A specialization of the CatalogPage servlet that
* displays a page selling three famous kids-book series.
* Orders are sent to the OrderPage servlet.
*/

@WebServlet("/KidsBooksPage")
public class KidsBooksPage extends CatalogPage {
	public void init() {
		String[] ids = { "lewis001", "alexander001", "rowling001" };
		setItems(ids);
		setTitle("All-Time Best Children's Fantasy Books");
	}
}
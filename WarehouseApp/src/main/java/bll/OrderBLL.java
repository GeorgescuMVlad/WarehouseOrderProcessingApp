package bll;

import dao.OrderDAO;
import model.Order;

/**
 * Business logic class that encapsulates the application logic
 */
public class OrderBLL {
			
	/**
	 * @param o which is the order we want to insert
	 */		
	public static void insertOrder(Order o){
		OrderDAO.insertOrder(o);
	}
			
}

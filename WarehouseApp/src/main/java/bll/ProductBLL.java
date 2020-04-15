package bll;

import dao.ProductDAO;
import model.Product;

/**
 * Business logic class that encapsulates the application logic
 */
public class ProductBLL {

		/**
		 * @param product which is the product we want to insert
		 */
		public void insertProduct(Product product) {
			ProductDAO.insertProduct(product);
		}
		
		/**
		 * @param product which is the product we want to update
		 */
		public void editProduct(Product product) {
			ProductDAO.updateProduct(product);
		}
		
		/**
		 * @param productName which is the name of the product we want to delete
		 */
		public void deleteProduct(String productName) {
			ProductDAO.deleteProduct(productName);
		}

}

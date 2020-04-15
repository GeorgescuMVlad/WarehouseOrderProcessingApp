package start;

import presentation.ViewClient;
import presentation.ViewProduct;

/**
 * The driver class of the program that starts the application
 * @see ViewClient
 * @see ViewProduct
 */
public class Start {

	/**
	 * Main method that starts the application
	 * @param args array of string arguments
	 */
	public static void main(String[] args) {

		new ViewClient();
		new ViewProduct();	
	}

}

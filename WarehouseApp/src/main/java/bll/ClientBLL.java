package bll;

import dao.ClientDAO;
import model.Client;

/**
 * Business logic class that encapsulates the application logic
 */
public class ClientBLL {
	
	/**
	 * @param client which is the client we want to insert
	 */
	public static void insertClient(Client client) {
		ClientDAO.insertClient(client);
	}
	
	/**
	 * @param client which is the client we want to update
	 */
	public static void updateClient(Client client) {
		ClientDAO.updateClient(client);
	}
	
	/**
	 * @param clientName which is the name of the client we want to delete
	 */
	public static void deleteClient(String clientName) {
		ClientDAO.deleteClient(clientName);
	}
	
}

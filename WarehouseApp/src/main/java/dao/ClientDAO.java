package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionDataBase;
import model.Client;

/**
 * Class ClientDAO is a database connection class with the table client from warehouse database. It also contains querries for update,
 * insert and delete.
 */
public class ClientDAO {
	protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
	private static final String insertStatementString = "INSERT INTO client (name,address,city,email,telNumber)"
			+ " VALUES (?,?,?,?,?)";
	private static final String deleteStatementString = "DELETE FROM client WHERE name = ?";
	private static final String updateStatementString = "UPDATE client SET " + "address = ?," + "city = ?," + "email = ?," + "telNumber = ? " 
	+ "WHERE name = ?";
	
	/**
	 * Method to insert a Client into client table
	 * @param client The client to insert
	 * @return the insertedId of the inserted client
	 */
	public static int insertClient(Client client) {
		Connection dbConnection = ConnectionDataBase.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, client.getClientName());
			insertStatement.setString(2, client.getClientAddress());
			insertStatement.setString(3, client.getClientCity());
			insertStatement.setString(4, client.getClientEmail());
			insertStatement.setString(5, client.getClientTelNumber());
			
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
		} finally {
			ConnectionDataBase.close(insertStatement);
			ConnectionDataBase.close(dbConnection);
		}
		return insertedId;
	}
	
	/**
	 * Method to update a client into database
	 * @param client The client to be updated
	 * @return the id of the inserted client
	 */
	public static int updateClient(Client client) {
		Connection dbConnection = ConnectionDataBase.getConnection();

		PreparedStatement updateStatement = null;
		int insertedId = -1;
		try {
			updateStatement = dbConnection.prepareStatement(updateStatementString, Statement.RETURN_GENERATED_KEYS);
			updateStatement.setString(1, client.getClientAddress());
			updateStatement.setString(2, client.getClientCity());
			updateStatement.setString(3, client.getClientEmail());
			updateStatement.setString(4, client.getClientTelNumber());
			updateStatement.setString(5, client.getClientName());
			
			updateStatement.executeUpdate();

			ResultSet rs = updateStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:update " + e.getMessage());
		} finally {
			ConnectionDataBase.close(updateStatement);
			ConnectionDataBase.close(dbConnection);
		}
		return insertedId;
	}
	
	/**
	 * Method to delete a client by its name from the client table
	 * @param name Is a string containing the name of the client to be deleted
	 */
	public static void deleteClient(String name) {
		Connection dbConnection = ConnectionDataBase.getConnection();

		PreparedStatement deleteStatement = null;
		try {
			deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
			deleteStatement.setString(1, name);
		
			deleteStatement.executeUpdate();
			
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:delete " + e.getMessage());
		} finally {
			ConnectionDataBase.close(deleteStatement);
			ConnectionDataBase.close(dbConnection);
		}
	}
	
	
}

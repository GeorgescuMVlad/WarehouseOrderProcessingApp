package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import connection.ConnectionDataBase;
import model.Product;

/**
 * Class ProductDAO is a database connection class with the table product from warehouse database. It also contains querries for update,
 * insert and delete.
 */
public class ProductDAO {
	
	protected static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());
	private static final String insertStatementString = "INSERT INTO product (name,quantity,price)"
			+ " VALUES (?,?,?)";
	private static final String updateStatementString = "UPDATE product SET " + "quantity = ?," + "price = ?" + " WHERE name = ?";
	private static final String deleteStatementString = "DELETE FROM product WHERE name = ?";
	
	/**
	 * Method to insert a Product into product table
	 * @param product The product to insert
	 * @return the insertedId of the inserted product
	 */
	public static int insertProduct(Product product) {
		Connection dbConnection = ConnectionDataBase.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, product.getProductName());
			insertStatement.setLong(2, product.getProductQuantity());
			insertStatement.setDouble(3, product.getProductPrice());
			
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:insert " + e.getMessage());
		} finally {
			ConnectionDataBase.close(insertStatement);
			ConnectionDataBase.close(dbConnection);
		}
		return insertedId;
	}
	
	/**
	 * Method to update a product into database
	 * @param product The product to be updated
	 * @return the id of the inserted product
	 */
	public static int updateProduct(Product product) {
		Connection dbConnection = ConnectionDataBase.getConnection();

		PreparedStatement updateStatement = null;
		int insertedId = -1;
		try {
			updateStatement = dbConnection.prepareStatement(updateStatementString, Statement.RETURN_GENERATED_KEYS);
			updateStatement.setInt(1, product.getProductQuantity());
			updateStatement.setDouble(2, product.getProductPrice());
			updateStatement.setString(3, product.getProductName());
			
			updateStatement.executeUpdate();

			ResultSet rs = updateStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:update " + e.getMessage());
		} finally {
			ConnectionDataBase.close(updateStatement);
			ConnectionDataBase.close(dbConnection);
		}
		return insertedId;
	}

	/**
	 * Method to delete a product by its name from the product table
	 * @param name Is a string containing the name of the product to be deleted
	 */
	public static void deleteProduct(String name) {
		Connection dbConnection = ConnectionDataBase.getConnection();

		PreparedStatement deleteStatement = null;
		try {
			deleteStatement = dbConnection.prepareStatement(deleteStatementString, Statement.RETURN_GENERATED_KEYS);
			deleteStatement.setString(1, name);
		
			deleteStatement.executeUpdate();
			
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:delete " + e.getMessage());
		} finally {
			ConnectionDataBase.close(deleteStatement);
			ConnectionDataBase.close(dbConnection);
		}
	}
	
}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionDataBase;
import model.Order;

/**
 * Class OrderDAO is a database connection class with the table order from warehouse database. It also contains querries for update,
 * insert and delete.
 */
public class OrderDAO {
	
	protected static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());
	private static final String insertStatementString = "INSERT INTO warehouse.order (idClient,idProduct,total)"
			+ " VALUES (?,?,?)";
	
	/**
	 * Method to insert an Order into order table
	 * @param o The Order to be inserted
	 * @return the insertedId of the inserted order
	 */
	public static int insertOrder(Order o) {
		Connection dbConnection = ConnectionDataBase.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setInt(1, o.getOrderIdClient());
			insertStatement.setInt(2, o.getOrderIdProduct());
			insertStatement.setDouble(3, o.getPrice()*o.getTotal());
			
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "OrderDAO:insert " + e.getMessage());
		} finally {
			ConnectionDataBase.close(insertStatement);
			ConnectionDataBase.close(dbConnection);
		}
		return insertedId;
	}

}

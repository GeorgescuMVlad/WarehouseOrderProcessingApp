package presentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import bll.OrderBLL;
import bll.validators.ProductNameValidator;
import bll.validators.TelephoneValidator;
import connection.ConnectionDataBase;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Order;
import model.Product;

/**
 * Class ViewProduct is a presentation class which realises the UI, implements the listeners on buttons, create the head of the JTable
 * through reflexion and does the operations that user wishes in the database and in the UI.
 * @see Product
 * @see Order
 * @see OrderDAO
 * @see ProductDAO
 * @see OrderBLL
 */
public class ViewProduct extends JFrame implements ActionListener {

			public JTextField t0=makeTextFields();
			public JTextField t1=makeTextFields();
			public JTextField t2=makeTextFields();
			public JTextField t3=makeTextFields();
			
			DefaultTableModel models = new DefaultTableModel();
			
			JTable table = new JTable(models);
			
		    JPanel panel=makePrincipalPanels("Buttons Panel");
		    JPanel panelOut=makePrincipalPanels("JTable Data Panel");
		    
			SpringLayout springLayout = new SpringLayout();
			
			JFrame frame=makeFrame("Window for Product Operations");
		    
		    JLabel nameLabel=makeLabels("Product name: ");
		    JLabel quantityLabel=makeLabels("Product quantity: ");
		    JLabel priceLabel=makeLabels("Product price: ");
		    
		    JLabel quantityStockLabel=makeLabels("Quantity desired: ");

		    public JButton addButton=makeButtons("Add Product");
		    public JButton editButton=makeButtons("Edit Product");
		    public JButton deleteButton=makeButtons("Delete Product");
		    
		    public JButton orderButton=makeButtons("Make Order");
		    	    
		    /** Method for generating the head of the table through reflexion 
		     *@param object An object of type Object which helps in getting its class through reflexion
		     *@return An ArrayList of Strings containing the names of each fields of that class(in our case, the columns of the table)
		     *@throws SecurityException An exception thrown by the security manager to indicate a security violation
		     */
		    public ArrayList<String> createTable(Object object)
		    {
		    	ArrayList<String> str=new ArrayList<String>();
		    	Class cls = object.getClass(); 
		    	try {
					Field[] field = cls.getDeclaredFields();
					for(int i=0; i<field.length; i++)
			    	{
			    		str.add(field[i].getName());
			    	}		
				} catch (SecurityException e) {
					e.printStackTrace();
				} 
		    	return str;
		    }
		    
		   /** Constructor to setup the GUI */
		   public ViewProduct() {        
		        frame.add(panel);
		        frame.add(panelOut);
		        
		        panel.add(nameLabel);
		        panel.add(t0);
		        panel.add(quantityLabel);
		        panel.add(t1);
		        panel.add(priceLabel);
		        panel.add(t2);
		        
		        panel.add(addButton);
		        panel.add(editButton);
		        panel.add(deleteButton);
		        
		        panel.add(quantityStockLabel);
		        panel.add(t3);
		        t3.setPreferredSize(new Dimension(60, 27));
		        panel.add(orderButton);
		        
		        panel.setLayout(springLayout);
		        springLayout.putConstraint(SpringLayout.WEST, t0, 120, SpringLayout.WEST, nameLabel);
		   
		        springLayout.putConstraint(SpringLayout.SOUTH, t1, 40, SpringLayout.SOUTH, t0);
		        springLayout.putConstraint(SpringLayout.WEST, t1, 120, SpringLayout.WEST, quantityLabel);
		        springLayout.putConstraint(SpringLayout.SOUTH, quantityLabel, 40, SpringLayout.SOUTH, nameLabel);
		        
		        springLayout.putConstraint(SpringLayout.SOUTH, priceLabel, 40, SpringLayout.SOUTH, quantityLabel);
		        springLayout.putConstraint(SpringLayout.WEST, t2, 120, SpringLayout.WEST, priceLabel);
		        springLayout.putConstraint(SpringLayout.SOUTH, t2, 40, SpringLayout.SOUTH, t1);
		        
		        springLayout.putConstraint(SpringLayout.SOUTH, addButton, 60, SpringLayout.SOUTH, priceLabel);
		        springLayout.putConstraint(SpringLayout.SOUTH, addButton, 60, SpringLayout.SOUTH, t2);
		            
		        springLayout.putConstraint(SpringLayout.SOUTH, editButton, 60, SpringLayout.SOUTH, priceLabel);
		        springLayout.putConstraint(SpringLayout.SOUTH, editButton, 60, SpringLayout.SOUTH, t2);
		        springLayout.putConstraint(SpringLayout.WEST, editButton, 120, SpringLayout.WEST, addButton);
		        
		        springLayout.putConstraint(SpringLayout.SOUTH, deleteButton, 60, SpringLayout.SOUTH, priceLabel);
		        springLayout.putConstraint(SpringLayout.SOUTH, deleteButton, 60, SpringLayout.SOUTH, t2);
		        springLayout.putConstraint(SpringLayout.WEST, deleteButton, 120, SpringLayout.WEST, editButton);
		        
		        springLayout.putConstraint(SpringLayout.SOUTH, quantityStockLabel, 55, SpringLayout.SOUTH, addButton);
		        springLayout.putConstraint(SpringLayout.SOUTH, t3, 60, SpringLayout.SOUTH, addButton);
		        springLayout.putConstraint(SpringLayout.WEST, t3, 120, SpringLayout.WEST, quantityStockLabel);
		        
		        springLayout.putConstraint(SpringLayout.SOUTH, orderButton, 60, SpringLayout.SOUTH, addButton);
		        springLayout.putConstraint(SpringLayout.WEST, orderButton, 90, SpringLayout.WEST, t3);
		        
		        addButton.addActionListener(this);
				editButton.addActionListener(this);
				deleteButton.addActionListener(this);
				orderButton.addActionListener(this);
		         
		        Connection dbConnection = ConnectionDataBase.getConnection();
		        
		        /*
		         * Insert the columns of the table obtained by reflexion into the head of the table
		         */
		        Product p= new Product();
		        ArrayList<String> str=new ArrayList<String>();
		        str=createTable(p);
		        for(int i=0; i<str.size(); i++)
		        {
		        	models.addColumn(str.get(i));
		        }
		        
		        /*
				 * When the app is opened, the JTable shows the data existing in the product table from the database 
				 */
		        PreparedStatement findStatement = null;
				ResultSet rs = null;
				try {
					findStatement = dbConnection.prepareStatement("SELECT * FROM product");
					rs = findStatement.executeQuery();
					while(rs.next())
					{
						models.addRow(new Object[] {rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDouble(4) });
					}
				} catch (SQLException e){
					e.getMessage();
				} finally{
					ConnectionDataBase.close(rs);
					ConnectionDataBase.close(findStatement);
					ConnectionDataBase.close(dbConnection);
				}
		        
				JScrollPane pa = new JScrollPane(table);
		       
		        pa.setPreferredSize(new Dimension(765, 315));
		        pa.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		        	
		        panelOut.add(pa);
		        frame.setVisible(true);
		        }
		     
		   /**
		     * Method to take the last element inserted in the database from the database, which is the one inserted by the user in the UI 
		     * at this moment, and insert it in the JTable
		     */
		   public void takeTheLastInsertedFromDataBase()
			{
				Connection dbConnection = ConnectionDataBase.getConnection();
				 PreparedStatement findStatement = null;
					ResultSet rs = null;
					try {
						findStatement = dbConnection.prepareStatement("SELECT * FROM product");
						rs = findStatement.executeQuery();
						rs.last();
						models.addRow(new Object[] {rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDouble(4) });
					} catch (SQLException ex){
						ex.getMessage();
					} finally {
						ConnectionDataBase.close(rs);
						ConnectionDataBase.close(findStatement);
						ConnectionDataBase.close(dbConnection);
					}
			}
		   
		   public void actionPerformed(ActionEvent e) {
				String productName=getUserInput(t0);
				String productQuantity=getUserInput(t1);
				String productPrice=getUserInput(t2);
				
				int quant=Integer.parseInt(productQuantity);
				double price=Double.parseDouble(productPrice);
				
				Product prod=new Product(productName, quant, price);
				
				if (e.getSource() == addButton) 
				{
					if(ProductNameValidator.validate(prod)==0)
						JOptionPane.showMessageDialog(this, "Wrong product name. Must contain only characters.");
					else { 
					ProductDAO.insertProduct(prod);  //update database
					takeTheLastInsertedFromDataBase();  //update JTable
					}
				}
				
				if (e.getSource() == deleteButton) 
				{
					ProductDAO.deleteProduct(productName);  //update database
					
					for (int i = 0; i < models.getRowCount(); i++) { 
						   if (models.getValueAt(i, 1).equals(productName)){
							   	models.removeRow(i);  //update JTable
						   }
					}		   
				}
				
				if (e.getSource() == editButton) {
					if(ProductNameValidator.validate(prod)==0)
						JOptionPane.showMessageDialog(this, "Wrong product name. Must contain only characters.");
					else { 
					ProductDAO.updateProduct(prod);  //Update database
					for (int i = 0; i < models.getRowCount(); i++) {   //update JTabl
						   if (models.getValueAt(i, 1).equals(productName)){
							   	models.setValueAt(prod.getProductName(), i, 1);
							   	models.setValueAt(prod.getProductQuantity(), i, 2);
							   	models.setValueAt(prod.getProductPrice(), i, 3);
						   }
					}
					}
				}
				
				if (e.getSource() == orderButton) {
					int quantity=Integer.parseInt(t3.getText());
					int rowP=table.getSelectedRow();
					
					int stock=Integer.parseInt(models.getValueAt(rowP, 2).toString());
					double priceTable=Double.parseDouble(models.getValueAt(rowP, 3).toString());
					Order o=new Order(ViewClient.getSelectedClientId(), Integer.parseInt(models.getValueAt(rowP, 0).toString()), quantity, priceTable );
				
					if(quantity>stock)
					{	
					     JOptionPane.showMessageDialog(this, "UnderStock");  //error message
					}
					else {
							OrderBLL.insertOrder(o);  //update database
							stock-=quantity;
							Product p=new Product(models.getValueAt(rowP, 1).toString(), stock, Double.parseDouble(models.getValueAt(rowP, 3).toString()));
							ProductDAO.updateProduct(p);  //update database
							try {
								PrintWriter out = new PrintWriter("Orders.txt");
								out.println("Order Bill ");
								out.println("Order made by client with id: " + o.getOrderIdClient());
								out.println("Ordered product id is: " + o.getOrderIdProduct());
								out.println("Quantity ordered is: " + quantity);
								out.println("The total of the order is: " + o.getTotal()*o.getPrice());
								out.close();
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							}
							for (int i = 0; i < models.getRowCount(); i++) { 
								   if (models.getValueAt(i, 1).equals(p.getProductName())){
									   	models.setValueAt(stock, i, 2);  //update JTable
								   }
							}	
						}
				}
			}
		   
		   /**
		    * Method to create a frame
		    * @param frame_text which is the frame name
		    * @return frame
		    */
		   public JFrame makeFrame(String frame_text)
		   {
			    JFrame frame = new JFrame(frame_text);
		        frame.setPreferredSize(new Dimension(1600, 400));
		        frame.setLayout(new GridLayout(1, 2));
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frame.pack();
		        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		        int x = (int) ((dimension.getWidth() - frame.getWidth()) /1.2);
		        int y = (int) ((dimension.getHeight() - frame.getHeight()) /1.2);
		        frame.setLocation(x, y);
		        frame.setVisible(true);
		        return frame;
		   }
		 	   
		   /**
		    * Method to create a label
		    * @param label_text which is the label name
		    * @return the label
		    */
		   public JLabel makeLabels(String label_text)
		   {
		        JLabel label = new JLabel();
		        label.setText(label_text); 
		        label.setFont(new java.awt.Font("Times New Roman", 1, 15));
		        return label;
		   }
		   
		   /**
		    * Method to create a text field
		    * @return the text field
		    */
		   public JTextField makeTextFields()
		   {
		        JTextField t= new JTextField();
		        t.setPreferredSize(new Dimension(300, 30));
		        return t;
		   }
		   	  
		   /**
		    * Method to create a panel
		    * @param panel_name which is the panel name
		    * @return the panel
		    */
		   public JPanel makePrincipalPanels(String panel_name)
		   {
			   JPanel panel=new JPanel();
			   panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), panel_name));
		       panel.setBounds(500, 400, 500, 1020);   
			   return panel;
		   }
		   
		   /**
		    * Method to create a button
		    * @param button_text which is the button name
		    * @return the button
		    */
		   public JButton makeButtons(String button_text)
		   {
		        JButton button = new JButton(button_text);		        
		        return button;        
		   }
			
		   /**
		    * Method to get the text from the textfield
		    * @param t which is the text field
		    * @return the string from text field
		    */
		   public String getUserInput(JTextField t)
		   {
			   return t.getText();
		   }

}

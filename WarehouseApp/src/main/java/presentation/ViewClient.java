package presentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import bll.ClientBLL;
import bll.validators.EmailValidator;
import bll.validators.TelephoneValidator;
import connection.ConnectionDataBase;
import dao.ClientDAO;
import model.Client;

/**
 * Class ViewClient is a presentation class which realises the UI, implements the listeners on buttons, create the head of the JTable
 * through reflexion and does the operations that user wishes in the database and in the UI.
 * @see Client
 * @see ClientDAO
 * @see ClientBLL
 */
public class ViewClient extends JFrame implements ActionListener{
	
		public JTextField t0=makeTextFields();
		public JTextField t1=makeTextFields();
		public JTextField t2=makeTextFields();
		public JTextField t3=makeTextFields();
		public JTextField t4=makeTextFields();
				
		public static DefaultTableModel models = new DefaultTableModel();
		
		public static JTable table = new JTable(models);
		 
	    JPanel panel=makePrincipalPanels("Buttons Panel");
	    JPanel panelOut=makePrincipalPanels("JTable Data Panel");
	    
		SpringLayout springLayout = new SpringLayout();
		
		public JFrame frame=makeFrame("Window for Client Operations");
		    
	    JLabel nameLabel=makeLabels("Client name: ");
	    JLabel addressLabel=makeLabels("Client address: ");
	    JLabel cityLabel=makeLabels("Client city: ");
	    JLabel emailLabel=makeLabels("Client email: ");
	    JLabel telLabel=makeLabels("Client tel: ");
	    
	    public JButton addButton=makeButtons("Add Client");
	    public JButton editButton=makeButtons("Edit Client");
	    public JButton deleteButton=makeButtons("Delete Client");
	    
	        
	    /** Method for generating the head of the table through reflexion 
	     *@param object An object of type Object which helps in getting its class through reflexion
	     *@return An ArrayList of Strings containing the names of each fields of that class(in our case, the columns of the table)
	     *@throws SecurityException exception thrown by the security manager to indicate a security violation
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
					findStatement = dbConnection.prepareStatement("SELECT * FROM client");
					rs = findStatement.executeQuery();
					rs.last();
					models.addRow(new Object[] {rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6) });
				} catch (SQLException ex){
					ex.getMessage();
				} finally {
					ConnectionDataBase.close(rs);
					ConnectionDataBase.close(findStatement);
					ConnectionDataBase.close(dbConnection);
				}
		}
	        
	   /** Constructor to setup the GUI */
	   public ViewClient() {        
	        frame.add(panel);
	        frame.add(panelOut);
        
	        panel.add(cityLabel);
	        panel.add(t3);
	        panel.add(t0); 
	        panel.add(nameLabel);
	        panel.add(t1);
	        panel.add(addressLabel);
	        panel.add(t2);
	        panel.add(emailLabel);
	        panel.add(t4);
	        panel.add(telLabel);
	        
	        panel.add(addButton);
	        panel.add(editButton);
	        panel.add(deleteButton);
	        
	        panel.setLayout(springLayout);
	        springLayout.putConstraint(SpringLayout.WEST, t0, 120, SpringLayout.WEST, nameLabel);
	   
	        springLayout.putConstraint(SpringLayout.SOUTH, t1, 40, SpringLayout.SOUTH, t0);
	        springLayout.putConstraint(SpringLayout.WEST, t1, 120, SpringLayout.WEST, addressLabel);
	        springLayout.putConstraint(SpringLayout.SOUTH, addressLabel, 40, SpringLayout.SOUTH, nameLabel);
	        
	        springLayout.putConstraint(SpringLayout.SOUTH, cityLabel, 40, SpringLayout.SOUTH, addressLabel);
	        springLayout.putConstraint(SpringLayout.WEST, t2, 120, SpringLayout.WEST, cityLabel);
	        springLayout.putConstraint(SpringLayout.SOUTH, t2, 40, SpringLayout.SOUTH, t1);
	        
	        springLayout.putConstraint(SpringLayout.SOUTH, emailLabel, 40, SpringLayout.SOUTH, cityLabel);
	        springLayout.putConstraint(SpringLayout.WEST, t3, 120, SpringLayout.WEST, emailLabel); 
	        springLayout.putConstraint(SpringLayout.SOUTH, t3, 40, SpringLayout.SOUTH, t2); 
	        
	        springLayout.putConstraint(SpringLayout.SOUTH, telLabel, 40, SpringLayout.SOUTH, emailLabel);
	        springLayout.putConstraint(SpringLayout.WEST, t4, 120, SpringLayout.WEST, telLabel); 
	        springLayout.putConstraint(SpringLayout.SOUTH, t4, 40, SpringLayout.SOUTH, t3); 
	        
	        springLayout.putConstraint(SpringLayout.SOUTH, addButton, 80, SpringLayout.SOUTH, telLabel);
	        springLayout.putConstraint(SpringLayout.SOUTH, addButton, 80, SpringLayout.SOUTH, t4);
	            
	        springLayout.putConstraint(SpringLayout.SOUTH, editButton, 80, SpringLayout.SOUTH, telLabel);
	        springLayout.putConstraint(SpringLayout.SOUTH, editButton, 80, SpringLayout.SOUTH, t4);
	        springLayout.putConstraint(SpringLayout.WEST, editButton, 120, SpringLayout.WEST, addButton);
	        
	        springLayout.putConstraint(SpringLayout.SOUTH, deleteButton, 80, SpringLayout.SOUTH, telLabel);
	        springLayout.putConstraint(SpringLayout.SOUTH, deleteButton, 80, SpringLayout.SOUTH, t4);
	        springLayout.putConstraint(SpringLayout.WEST, deleteButton, 120, SpringLayout.WEST, editButton);
	  
	        addButton.addActionListener(this);
	        editButton.addActionListener(this);
			deleteButton.addActionListener(this);
	          
			
	        Connection dbConnection = ConnectionDataBase.getConnection();
	        
	        /*
	         * Insert the columns of the table obtained by reflexion into the head of the table
	         */
	        Client c= new Client();
	        ArrayList<String> str=new ArrayList<String>();
	        str=createTable(c);
	        for(int i=0; i<str.size(); i++)
	        {
	        	models.addColumn(str.get(i));
	        }
	        
	        /*
			 * When the app is opened, the JTable shows the data existing in the client table from the database 
			 */
	        PreparedStatement findStatement = null;
			ResultSet rs = null;
			try {
				findStatement = dbConnection.prepareStatement("SELECT * FROM client");
				rs = findStatement.executeQuery();
				while(rs.next())
				{
					models.addRow(new Object[] {rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6) });
					
				}
			} catch (SQLException e){
				e.getMessage();
			} finally {
				ConnectionDataBase.close(rs);
				ConnectionDataBase.close(findStatement);
				ConnectionDataBase.close(dbConnection);
			}
	        
			JScrollPane pa = new JScrollPane(table);
	         
	        table.getColumnModel().getColumn(0).setPreferredWidth(20);
	        table.getColumnModel().getColumn(1).setPreferredWidth(60);
	        table.getColumnModel().getColumn(2).setPreferredWidth(110);
	        table.getColumnModel().getColumn(3).setPreferredWidth(30);
	        table.getColumnModel().getColumn(4).setPreferredWidth(120);
	        table.getColumnModel().getColumn(5).setPreferredWidth(30);
	        
	        pa.setPreferredSize(new Dimension(765, 315));
	        pa.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
	        	
	        panelOut.add(pa);
	        frame.setVisible(true);
	        }
	   
	   
	   
	   public void actionPerformed(ActionEvent e) {
			String clientName=getUserInput(t0);
			String clientAddress=getUserInput(t1);
			String clientCity=getUserInput(t2);
			String clientEmail=getUserInput(t3);
			String clientTel=getUserInput(t4);
			
			Client cl=new Client(clientName, clientAddress, clientCity, clientEmail, clientTel);
			
			if (e.getSource() == addButton) {
				if(EmailValidator.validate(cl)==0)  
					JOptionPane.showMessageDialog(this, "Wrong email. Must have only 1 @ and at least 3 characters");
				else if(TelephoneValidator.validate(cl)==0)
					JOptionPane.showMessageDialog(this, "Wrong telephone number. Must have exactly 10 digits");
				else { 
						ClientBLL.insertClient(cl);  //update the database
					   takeTheLastInsertedFromDataBase();  //update the JTable				
				}
			}
			
			if (e.getSource() == deleteButton) {
				ClientBLL.deleteClient(clientName);  //Update the database
				for (int i = 0; i < models.getRowCount(); i++) { 
					   if (models.getValueAt(i, 1).equals(clientName)){
						   	models.removeRow(i); //update the table
					   }
				}		   
			}
			
			if (e.getSource() == editButton) {
				if(EmailValidator.validate(cl)==0)  
					JOptionPane.showMessageDialog(this, "Wrong email. Must have only 1 @ and at least 3 characters");
				else if(TelephoneValidator.validate(cl)==0)
					JOptionPane.showMessageDialog(this, "Wrong telephone number. Must have exactly 10 digits");
				else { 
				ClientBLL.updateClient(cl);  //update the database
				for (int i = 0; i < models.getRowCount(); i++) { 
					   if (models.getValueAt(i, 1).equals(clientName)){  //update the table
						   	models.setValueAt(cl.getClientName(), i, 1);
						   	models.setValueAt(cl.getClientAddress(), i, 2);
						   	models.setValueAt(cl.getClientCity(), i, 3);
						   	models.setValueAt(cl.getClientEmail(), i, 4);
						   	models.setValueAt(cl.getClientTelNumber(), i, 5);
					   }
					}	
				}
			}
		}
	     
	   /**
	    * Method to create a frame
	    * @param frame_text which is the frame name
	    * @return the frame
	    */
	   public JFrame makeFrame(String frame_text)
	   {
		    JFrame frame = new JFrame(frame_text);
	        frame.setPreferredSize(new Dimension(1600, 400));
	        frame.setLayout(new GridLayout(1, 2));
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.pack();
	        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	        int x = (int) ((dimension.getWidth() - frame.getWidth()) /20);
	        int y = (int) ((dimension.getHeight() - frame.getHeight()) /20);
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
		
	  /**
	   * Method to get the selected client id by the user from the JTable of clients
	   * @return an integer containing the selected client id
	   * @see ViewProduct
	   */
	  public static int getSelectedClientId()
	  {
		  int selectedRow= table.getSelectedRow();
		  String s=table.getValueAt(selectedRow, 0).toString();
		  return Integer.parseInt(s);
	  }
		
}

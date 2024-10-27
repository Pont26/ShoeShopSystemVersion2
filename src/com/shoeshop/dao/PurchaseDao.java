package com.shoeshop.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.shoessho.model.Customer;
import com.shoessho.model.Purchase;
import com.shoessho.model.Shoe;

public class PurchaseDao extends GeneralDao<Purchase>{
	private ConnectionDao connectionDao;
	private ShoeDao shoeDao;
	private CustomerDao customerDao;
	public PurchaseDao() {
		connectionDao = new ConnectionDao();
	     shoeDao = new ShoeDao();
	     customerDao =new CustomerDao();
	}
	
	//insertValue
	public void insertValue(int shoe_id,int customer_id,int qty) throws IOException, SQLException {
		String query = "INSERT INTO purchases ( shoe_id,customer_id, qty,total_price) VALUES (?, ?, ?,?)";

		Connection connection = connectionDao.connectionDao();
		PreparedStatement preparedStatement = connection.prepareStatement(query);
        double total_price = shoeDao.getShoePriceById(shoe_id) * qty;

		preparedStatement.setInt(1, customer_id);         
		preparedStatement.setInt(2, shoe_id);      
		preparedStatement.setInt(3, qty);         
		preparedStatement.setDouble(4, total_price);


		int rowAffect = preparedStatement.executeUpdate();
		System.out.println(rowAffect + " row(s) inserted.");
		preparedStatement.close();
		connection.close();
		
	}
	
	//select all
	   public List<Purchase> selectAllPurchase() throws IOException, SQLException {
	        List<Purchase> purchaseList = super.selectAll("SELECT * FROM purchases");
	        return purchaseList;
	    }
	   

	@Override
	public Purchase convertToObject(ResultSet rs) {
		  try {
	            int shoeId = rs.getInt("id"); 
	            int customerId = rs.getInt("id"); 
	            int qty = rs.getInt("qty");
	            double totalPrice = rs.getDouble("total_price");
	            

	            Shoe shoe = null;
	            Customer customer = null;
				try {
				 shoe= shoeDao.selectById(shoeId);
				 customer = customerDao.selectById(customerId);
				} catch (IOException e) {
					e.printStackTrace();
				} 
	            System.out.println("Shoe ID: " + shoeId + ", Customer ID: " + customerId + ", Quantity: " + qty + ", Total Price: " + totalPrice);

	            return new Purchase(shoe, customer, qty); 
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return null; 
	        }
	}

	

}

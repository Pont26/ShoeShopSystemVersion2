package com.shoeshop.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.shoessho.model.Customer;

public class CustomerDao extends GeneralDao<Customer> {
	
        public List<Customer> selectAllCustomer() throws IOException, SQLException{
        	 List<Customer> customerList = super.selectAll("SELECT * FROM customer");
        	 return customerList;
        }
	
	//insert
	public void insertValue(String name,String email,String phone,String address) {
		super.insert("INSERT INTO customer(name,email,phone,address) VALUEs (?,?,?,?)", name,email,phone,address);
	}
	
	//update 
	public void updateValue(int id,String name,String email,String phone, String address) throws IOException, SQLException {
		  String sql = "UPDATE customer SET name = ?, email = ?, phone = ?, address = ? WHERE id = ?";
		    super.update(sql, name, email, phone, address, id);
		    
	}
	//delete
	public void deleteRow(int id) throws IOException, SQLException {
		String sql= "DELETE FROM customer WHERE id = ?";
		super.deleteRow(sql, id);
		
	}
	//select by id
	public Customer selectById(int id) throws IOException, SQLException {
		String sql = "SELECT * From customer where id = ?";
		return super.selectById(sql, id);
	
	}

	@Override
	public Customer convertToObject(ResultSet rs) {
	    try {
	        String name = rs.getString("name");
	        String email = rs.getString("email");
	        String phone = rs.getString("phone");
	        String address = rs.getString("address");
	        Customer customer = new Customer(name, email, phone, address);
	        System.out.println("Name: " + name + ", Email: " + email + ", Phone: " + phone + ", Address: " + address);
	        
	        return customer;
	    } catch (SQLException e) {
	        e.printStackTrace(); 
	        throw new RuntimeException(e);
	    }
	}





	
	
	}





















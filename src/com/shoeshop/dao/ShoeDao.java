package com.shoeshop.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.shoessho.model.Customer;
import com.shoessho.model.Shoe;



public class ShoeDao extends GeneralDao<Shoe>{
	private ConnectionDao connectionDao;

	
	public ShoeDao() {
		connectionDao =new ConnectionDao();
		
	}
	//select all
	   public List<Shoe> selectAllShoe() throws IOException, SQLException {
	        List<Shoe> shoeList = super.selectAll("SELECT * FROM shoes");
	        return shoeList;
	    }
	   
	 //insert
		public void insertValue(String name,double size,double price) {
			super.insert("INSERT INTO shoes(name,size,price) VALUEs (?,?,?)", name,size,price);
		}
		
		//update 
		public void updateValue(int id,String name,double size,double price) throws IOException, SQLException {
			  String sql = "UPDATE shoes SET name = ?, size = ?, price = ? WHERE id = ?";
			    super.update(sql, name, size, price, id);
			    
		}
		//delete
		public void deleteRow(int id) throws IOException, SQLException {
			String sql= "DELETE FROM shoes WHERE id = ?";
			super.deleteRow(sql, id);
			
		}
		//select by id
		public Shoe selectById(int id) throws IOException, SQLException {
			String sql = "SELECT * From shoes where id = ?";
			return super.selectById(sql, id);
		
		}
		
		public double getShoePriceById(int id) throws SQLException, IOException {
	        String query = "SELECT price FROM shoes WHERE id = ?";
	        double price = 0.0;

	        try (Connection connection = connectionDao.connectionDao();
	             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	             
	            preparedStatement.setInt(1, id);
	            ResultSet resultSet = preparedStatement.executeQuery();

	            if (resultSet.next()) {
	                price = resultSet.getDouble("price");
	            } else {
	                System.out.println("Shoe with ID " + id + " not found.");
	            }
	            resultSet.close();
				 preparedStatement.close();
				 connection.close();
	        }
	        return price;
	    }

		
		  @Override
		    public Shoe convertToObject(ResultSet rs) {
		        try {
		            int id = rs.getInt("id");
		            String name = rs.getString("name");
		            double size = rs.getDouble("size");
		            double price = rs.getDouble("price");
		            System.out.println(" Name= " + name + ", Size= " + size + ", Price= " + price );
		            return new Shoe(id, name, size, price); 
		        } catch (SQLException e) {
		            e.printStackTrace();
		            return null; 
		        }
		    }
}

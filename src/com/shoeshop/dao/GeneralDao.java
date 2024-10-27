package com.shoeshop.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.shoessho.model.Customer;

public abstract class GeneralDao<T> {
public ConnectionDao connectionDao;
	
	public GeneralDao() {
		connectionDao = new ConnectionDao();
	}
	//public abstract PreparedStatement prepareStatement(PreparedStatement preparedStatement);
    
	//insert
	public void insert(String sql,Object ...objects) {
	String query = sql;
	try {
	Connection connection = connectionDao.connectionDao();
	PreparedStatement preparedStatment = connection.prepareStatement(query);
	int count = 1;
	for(Object object: objects) {
		preparedStatment.setObject(count, object);
		count++;
	}
	
	int rowAffect = preparedStatment.executeUpdate();
		preparedStatment.close();
		connection.close();	
		System.out.print("Roll effect == "+rowAffect);
	} catch (SQLException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
		
}
	
	public abstract T  convertToObject(ResultSet rs); 
	
	public List<T> selectAll(String sql) throws IOException, SQLException{
		List<T> list= new ArrayList<>();
		Connection connection = connectionDao.connectionDao();		
		Statement statment = connection.createStatement();
		ResultSet rs = statment.executeQuery(sql);
		
		while(rs.next()) {
	            list.add(convertToObject(rs));
		}
		rs.close();
		statment.close();
		connection.close();
		return list;
		
	}
	
	//Update Row
	
	public void update(String sql, Object ...objects) throws IOException, SQLException {
     Connection connection = connectionDao.connectionDao();
     PreparedStatement preparedStatement = connection.prepareStatement(sql);
 	 int count = 1;
 	 for(Object object: objects) {
 		preparedStatement.setObject(count, object);
 		count++;
 	}
 	int rowsAffected = preparedStatement.executeUpdate();
 	if (rowsAffected > 0) {
        System.out.println(" updated successfully.");
    } else {
        System.out.println("No row found with ID ");
    }
    preparedStatement.close();
    connection.close();

	}
	
	//DElete Row
	
	public void deleteRow(String sql,int id) throws IOException, SQLException {
		Connection connection = connectionDao.connectionDao();
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		int rowEffected = preparedStatement.executeUpdate();
		if(rowEffected > 0) {
	        System.out.println("Row with ID " + id + " deleted successfully.");	
		}else {
	        System.out.println("No row found with ID " + id);
			
		}
		
	}
	
	//Select by ID
	public T selectById(String sql,int id) throws IOException, SQLException {
		T object = null;
		Connection connection= connectionDao.connectionDao();
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		 if (resultSet.next()) {
			  object = (convertToObject(resultSet));  
		 }
		 resultSet.close();
		 preparedStatement.close();
		 connection.close();
		
		return object;
		
	}
	
	
	

}

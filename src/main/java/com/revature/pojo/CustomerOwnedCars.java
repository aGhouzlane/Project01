package com.revature.pojo;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.util.ConnectionFactory;
import com.revature.util.Record;

public class CustomerOwnedCars implements Record{
	
	private int id;
	private int carId;
	private int customerId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCarId() {
		return carId;
	}
	public void setCarId(int carId) {
		this.carId = carId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerOwnedCars other = (CustomerOwnedCars) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public void save() throws SQLException, IOException {
		
		if(id == 0) 
		{
			String sql = "insert into customers_owned_cars ( car_id, customer_id) "
					+ "values ('"+carId+"', '"+customerId+"') returning id"; 
			 ResultSet row = ConnectionFactory.getFirstRow(sql);
			 id = row.getInt("id");
		}
		else
		{
			String sql = "update cacustomers_owned_cars set car_id = '"+carId+"' , "
					+ "customer_id = '"+customerId+"' where id = "+id;
			ConnectionFactory.execute(sql);
		}
	}
	
	public Car getCar() throws SQLException, IOException 
	{
		ResultSet row = ConnectionFactory.getFirstRow("select * "
				+ "from cars where id = " +carId);
		
		Car car = new Car();
		
		car.setId(row.getInt("id"));
		car.setMake(row.getString("make"));
		car.setModel(row.getString("model"));
		car.setYear(row.getInt("year"));
		car.setPrice(row.getFloat("price"));
		
		return car;
	}
	
	public Customer getCustomer() throws SQLException, IOException 
	{
		ResultSet row = ConnectionFactory.getFirstRow("select * "
				+ "from customers where id = " +customerId);
		
		Customer customer = new Customer();
		
		customer.setId(row.getInt("id"));
		customer.setFirstName(row.getString("make"));
		
		return customer;
	}
	
	@Override
	public String toString() {
		return "CustomerOwnedCars [id=" + id + ", carId=" + carId + ", customerId=" + customerId + "]";
	}
	
	

}

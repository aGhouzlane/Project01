package com.revature.pojo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.util.ConnectionFactory;
import com.revature.util.Record;

public class Customer implements Record{

	private int id;
	private String firstName;
	
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
		Customer other = (Customer) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + "]";
	}

	@Override
	public void save() throws SQLException, IOException {
		if(id == 0) 
		{
			String sql = "insert into customers ( firstname ) "
					+ "values ('"+firstName+"') returning id"; 
			 ResultSet row = ConnectionFactory.getFirstRow(sql);
			 id = row.getInt("id");
		}
		else
		{
			String sql = "update customers set firstname = '"+firstName+"' where id = "+id;
			ConnectionFactory.execute(sql);
		}
		
	}
	
	public ArrayList<Car> getOwnedCars() throws SQLException
	{
		String sql = "select c.id, c.make, c.model, c.year, "
				+ "c.price from cars c, customers_owned_cars coc, customers cu "
				+ "where c.id = coc.car_id and coc.customer_id = cu.id";
		Connection connection = ConnectionFactory.getConnection();
		ResultSet row = connection.createStatement().executeQuery(sql);
		
		ArrayList<Car> cars = new ArrayList<Car>();
		
		while(row.next()) 
		{
			Car car = new Car();
			car.setId(row.getInt("id"));
			car.setMake(row.getString("make"));
			car.setModel(row.getString("model"));
			car.setYear(row.getInt("year"));
			car.setPrice(row.getFloat("price"));
			cars.add(car);
		}
		connection.close();
		return cars;	
	}
	
	public void buyCar(Car car) throws SQLException, IOException 
	{
		CustomerOwnedCars coc = new CustomerOwnedCars();
		coc.setCustomerId(id);
		coc.setCarId(car.getId());
		coc.save();
	}
	
	

}

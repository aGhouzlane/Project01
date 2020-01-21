package com.revature.pojo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.util.ConnectionFactory;
import com.revature.util.Record;

public class Car implements Record{

	private int id;
	private String model;
	private String make;
	private int year;
	private float price;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
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
		Car other = (Car) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Car [id=" + id + ", model=" + model + ", make=" + make + ", year=" + year + ", price=" + price + "]";
	}
	
	
	public void save() throws IOException, SQLException {
		if(id == 0) 
		{
			String sql = "insert into cars ( model, make, year, price) "
					+ "values ('"+model+"', '"+make+"', '"+year+"',"
							+ " '"+price+"') returning id"; 
			 ResultSet row = ConnectionFactory.getFirstRow(sql);
			 id = row.getInt("id");
		}
		else
		{
			String sql = "update cars set model = '"+model+"' , make = '"+make+"', "
					+ "year = '"+year+"', price = '"+price+"' where id = "+id;
			ConnectionFactory.execute(sql);
		}
	}
	
	public void delete(int carid) throws SQLException, IOException 
	{
		String sql = "delete from cars where id = " +carid;
		ConnectionFactory.execute(sql);
	}
	
	
	public static ArrayList<Car> all() throws SQLException
	{
		String sql = "select * from cars";
		Connection connection = ConnectionFactory.getConnection();
		ResultSet row = connection.createStatement().executeQuery(sql);
		
		ArrayList<Car> cars = new ArrayList<Car>();
		
		while(row.next()) 
		{
			Car car = new Car();
			car.id = row.getInt("id");
			car.make = row.getString("make");
			car.model = row.getString("model");
			car.year = row.getInt("year");
			car.price = row.getFloat("price");
			cars.add(car);
		}
		connection.close();
		return cars;
	}
	
	
	
}

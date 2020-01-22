package com.revature.pojo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.util.ConnectionFactory;
import com.revature.util.Record;

public class Offer implements Record{

	int id;
	float amount;
	boolean decision;
	int carId;
	
	
	
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
		Offer other = (Offer) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	public int getCarId() {
		return carId;
	}
	public void setCarId(int carId) {
		this.carId = carId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public boolean isDecision() {
		return decision;
	}
	public void setDecision(boolean decision) {
		this.decision = decision;
	}
	@Override
	public String toString() {
		return "Offer [id=" + id + ", amount=" + amount + ", decision=" + decision + "]";
	}
	
	public void update(int offerId) throws SQLException, IOException 
	{
		String sql = "update offers set decision = true where id = "+offerId;
		
		ConnectionFactory.execute(sql);
	}
	
	@Override
	public void save() throws SQLException, IOException 
	{
		
		if(id == 0) 
		{
			String sql = "insert into offers ( amount, decision, carId) "
					+ "values ('"+amount+"', '"+decision+"', '"+carId+"') returning id"; 
			 ResultSet row = ConnectionFactory.getFirstRow(sql);
			 id = row.getInt("id");
		}
		else
		{
			String sql = "update offers set amount = '"+amount+"' , decision = '"+decision+"', "
					+ "carId = '"+carId+"' where id = "+id;
			ConnectionFactory.execute(sql);
		}
	}
	
	public static ArrayList<Offer> all() throws SQLException
	{
		String sql = "select * from offers";
		Connection connection = ConnectionFactory.getConnection();
		ResultSet row = connection.createStatement().executeQuery(sql);
		
		ArrayList<Offer> offers = new ArrayList<Offer>();
		
		while(row.next()) 
		{
			Offer offer = new Offer();
			offer.id = row.getInt("id");
			offer.amount = row.getFloat("amount");
			offer.decision = row.getBoolean("decision");
			offer.carId = row.getInt("carId");
			offers.add(offer);
		}
		connection.close();
		return offers;
	}
	
}

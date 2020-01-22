package com.revature.driver;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.revature.pojo.Car;
import com.revature.pojo.Employee;
import com.revature.pojo.Offer;
import com.revature.pojo.User;
import com.revature.service.EmployeeLoginService;
import com.revature.service.UserLoginService;

public class App {

	private static UserLoginService uls = new UserLoginService();
	private static EmployeeLoginService els = new EmployeeLoginService();
	private static Offer offer = new Offer();
	private static Car car = new Car();
	private static User user = new User();
	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) throws SQLException {

		mainMenu();
	}

	private static void mainMenu() {

		System.out.println("---Welcome to Our Dealership!---");
		System.out.println("Please make a selection:");
		System.out.println("[1] Register");
		System.out.println("[2] Login");
		System.out.println("[3] Exit");

		Scanner input = new Scanner(System.in);

		switch (input.nextLine()) {
		case "1":
			registerMenu();
			break;
		case "2":
			loginMenu();
			break;
		case "3":
			return;
		default:
			System.out.println("Did not understand input, please try again!");
			mainMenu();
			break;
		}
	}

	private static void registerMenu() {

		System.out.println("[1] Register as User");
		System.out.println("[2] Register as Employee");
		System.out.println("[3] Main menu");

		Scanner registerInput = new Scanner(System.in);

		switch (registerInput.nextLine()) {
		case "1":
			User user = getUserInfo();
			uls.registerUser(user.getUsername(), user.getPassword());
			System.out.println("User was successfully registered.");
			mainMenu();
			break;
		case "2":
			Employee employee = getEmployeeInfo();
			els.registerEmployee(employee.getEmployeename(), employee.getPassword());
			System.out.println("Employee was successfully registered.");
			mainMenu();
			break;
		case "3":
			mainMenu();
			break;
		default:
			System.out.println("Did not understand input, please try again!");
			mainMenu();
			break;
		}
	}

	private static void loginMenu() {

		System.out.println("[1] Login as User");
		System.out.println("[2] Login as Employee");
		System.out.println("[3] Main menu");

		Scanner loginInput = new Scanner(System.in);

		switch (loginInput.next()) {
		case "1":
			if (((UserLoginService) uls).authenticateUser(getUserInfo())) {
				System.out.println("User Login Success");
				userMenu();
			} else {
				System.out.println("User Login Failure");
				mainMenu();
			}
			break;
		case "2":
			if (((EmployeeLoginService) els).authenticateEmployee(getEmployeeInfo())) {
				System.out.println("Employee Login Success");
				employeeMenu();
			} else {
				System.out.println("Employee Login Failure");
				mainMenu();
			}
			break;
		case "3":
			mainMenu();
			break;
		default:
			System.out.println("Did not understand input, please try again!");
			mainMenu();
			break;
		}
	}

	private static User getUserInfo() {

		Scanner scan = new Scanner(System.in);
		System.out.println("Enter username:");
		user.setUsername(scan.next());
		System.out.println("Enter password:");
		user.setPassword(scan.next());
		return user;
	}

	private static Employee getEmployeeInfo() {
		
		Scanner scan = new Scanner(System.in);
		Employee employee = new Employee();
		System.out.println("Enter Employee Name:");
		employee.setEmployeename(scan.nextLine());
		System.out.println("Enter Employee password:");
		employee.setPassword(scan.nextLine());
		return employee;
	}

	private static void employeeMenu() {

		System.out.println("---Welcome to the Employee Menu---");
		System.out.println("Please Make a Selection:");
		System.out.println("[1] To add a car");
		System.out.println("[2] To remove a Car");
		System.out.println("[3] To accept/reject an Offer");
		System.out.println("[4] View all payments");
		System.out.println("[5] Main menu");

		Scanner employeeInput = new Scanner(System.in);

		switch (employeeInput.nextLine()) {
		case "1":
			addCarMenu();
			System.out.println("Car was added successfully!");
			employeeMenu();
			break;
		case "2":
			deleteCarMenu();
			System.out.println("Car was successfully removed from the lot.");
			employeeMenu();
			break;
		case "3":
			displayOffers();
			employeeMenu();
			break;
		case "4":
			// TODO: view all payments
			employeeMenu();
			break;
		case "5":
			mainMenu();
			break;
		default:
			System.out.println("Did not understand input, Please try again!");
			employeeMenu();
			break;
		}
	}
	
	public static void displayOffers() 
	{
		try {
			ArrayList<Offer> offers = Offer.all();
			for(Offer o : offers) 
			{	
				System.out.println(o);
			}
			
			System.out.println("Choose an offerid you wish to make a decision on:");
			int chosenId = scan.nextInt();
			
			for(Offer o : offers) 
			{
				if(o.getId() == chosenId) 
				{
					System.out.println(o);
					System.out.println("Do you want to accept this offer?");
					
					if(scan.next().equals("yes")) 
					{
						try {
							offer.update(o.getId());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("Offer Accepted.");
						break;
					}
					else 
					{
						System.out.println("Offer Rejected.");
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	public static void addCarMenu() {
		
		System.out.println("Please enter car information:");
		System.out.println("Make:");
		car.setMake(scan.next());
		System.out.println("Model:");
		car.setModel(scan.next());
		System.out.println("Price:");
		car.setPrice(scan.nextFloat());
		System.out.println("Year:");
		car.setYear(scan.nextInt());

		try {
			car.save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private static void deleteCarMenu() 
	{
		System.out.println("Please enter the car id you wish to delete:");
		
		try {
			car.delete(scan.nextInt());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void makeOfferMenu() 
	{
		System.out.println("Please enter the id of the car you wish to buy:");
		offer.setCarId(scan.nextInt());
		
		System.out.println("Please enter your final offer:");
		offer.setAmount(scan.nextFloat());
		
		try {
			offer.save();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Offer submitted successfully.");
	}

	private static void userMenu() {
		
		System.out.println("---Welcome to the User Menu!---");
		System.out.println("Please make a selection:");
		System.out.println("[1] View a car");
		System.out.println("[2] Make an offer");
		System.out.println("[3] View owned cars");
		System.out.println("[4] Make a payment");
		System.out.println("[5] Main menu");

		Scanner userInput = new Scanner(System.in);

		switch (userInput.nextLine()) {
		case "1":
			
			try {
				System.out.println("Displaying all cars...");
				ArrayList<Car> cars = Car.all();

				for (Car car : cars) {
					System.out.println(car);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			userMenu();
			break;
		case "2":
			// TODO: make an offer
			makeOfferMenu(); 
			userMenu();
			break;
		case "3":
			// TODO: view owned cars
			userMenu();
			break;
		case "4":
			// TODO: make a payment
			userMenu();
			break;
		case "5":
			mainMenu();
			break;
		default:
			System.out.println("Did not understand input, Please try again!");
			userMenu();
			break;
		}
	}

}

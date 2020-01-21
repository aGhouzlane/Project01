package com.revature.service;

import com.revature.pojo.Employee;

public class EmployeeLoginService {

	private static Employee[] employeeDB;

	private static int employeeIndex;

	public Employee registerEmployee(String employeename, String password) {

		Employee newEmployee = new Employee();
		newEmployee.setPassword(password);
		newEmployee.setEmployeename(employeename);

		employeeDB[employeeIndex] = newEmployee;
		employeeIndex++;

		return newEmployee;
	}
	
	private int findEmployeeIndex(Employee employee) {
		for (int i = 0; i < employeeIndex; i++) {
			if (employeeDB[i] != null) {
				if (employeeDB[i].getEmployeename().equals(employee.getEmployeename())) {
					return i;
				}
			}
		}
		return -1; // denotes the user was not found
	}
	
	public boolean authenticateEmployee(Employee employee) {
		int index = findEmployeeIndex(employee);

		if (index > -1) {
			String userPassword = employeeDB[index].getPassword();
			return (userPassword.equals(employee.getPassword()));
		} else {
			return false;
		}
	}

	public EmployeeLoginService() {
		employeeDB = new Employee[10];
		employeeIndex = 0;
	}
}

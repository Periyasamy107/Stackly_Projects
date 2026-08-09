package service;

import dao.EmployeeDAO;
import exception.EmployeeNotFoundException;
import exception.ManagerNotFoundException;
import model.Employee;

import java.util.List;

public class EmployeeService {

    private EmployeeDAO employeeDAO;

    public EmployeeService() {
        employeeDAO = new EmployeeDAO();
    }

    public boolean registerEmployee(Employee Employee) {

        if (employeeDAO.employeeIdExists(Employee.getEmployeeId())) {
            System.out.println("Employee already present : " + Employee.getEmployeeName());
        }

        if (employeeDAO.employeeIdExists(Employee.getEmployeeId())) {
        }
        if (employeeDAO.emailExists(Employee.getEmail())) {
            System.out.println("\nEmployee email already exists.\n");
            return false;
        }

        if (!employeeDAO.managerExists(Employee.getManagerId())) {
            System.out.println("Manager not found : " + Employee.getManagerId());
        }

        return employeeDAO.registerEmployeeWithBalance(Employee);
    }



    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }



    public Employee getEmployeeById(String employeeId) {
        return employeeDAO.getEmployeeById(employeeId);
    }



    public boolean updateEmployee(Employee Employee)
        throws EmployeeNotFoundException, ManagerNotFoundException {
        if (!employeeDAO.employeeIdExists(Employee.getEmployeeId())) {
            throw new EmployeeNotFoundException("Employee not found : " + Employee.getEmployeeId());
        }
        if (!employeeDAO.managerExists(Employee.getManagerId())) {
            throw new ManagerNotFoundException("Manager not found : " + Employee.getManagerId());
        }
        return employeeDAO.updateEmployee(Employee);
    }



    public boolean deactiveEmployee(String employeeId) throws EmployeeNotFoundException {
        if (!employeeDAO.employeeIdExists(employeeId)) {
            throw new EmployeeNotFoundException("Employee not found : " + employeeId);
        }
        return employeeDAO.deactiveEmployee(employeeId);
    }
}
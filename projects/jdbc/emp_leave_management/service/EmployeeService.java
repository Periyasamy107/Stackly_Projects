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

    public boolean registerEmployee(Employee employee) {

        if (employeeDAO.employeeIdExists(employee.getEmployeeId())) {
            System.out.println("Employee already present : " + employee.getEmployeeName());
        }

        if (employeeDAO.employeeIdExists(employee.getEmployeeId())) {
        }
        if (employeeDAO.emailExists(employee.getEmail())) {
            System.out.println("\nEmployee email already exists.\n");
            return false;
        }

        if (!employeeDAO.managerExists(employee.getManagerId())) {
            System.out.println("Manager not found : " + employee.getManagerId());
        }

        return employeeDAO.registerEmployeeWithBalance(employee);
    }



    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }



    public Employee getEmployeeById(String employeeId) {
        return employeeDAO.getEmployeeById(employeeId);
    }



    public boolean updateEmployee(Employee employee)
        throws EmployeeNotFoundException, ManagerNotFoundException {
        if (!employeeDAO.employeeIdExists(employee.getEmployeeId())) {
            throw new EmployeeNotFoundException("Employee not found : " + employee.getEmployeeId());
        }
        if (!employeeDAO.managerExists(employee.getManagerId())) {
            throw new ManagerNotFoundException("Manager not found : " + employee.getManagerId());
        }
        return employeeDAO.updateEmployee(employee);
    }



    public boolean deactiveEmployee(String employeeId) throws EmployeeNotFoundException {
        if (!employeeDAO.employeeIdExists(employeeId)) {
            throw new EmployeeNotFoundException("Employee not found : " + employeeId);
        }
        return employeeDAO.deactiveEmployee(employeeId);
    }
}
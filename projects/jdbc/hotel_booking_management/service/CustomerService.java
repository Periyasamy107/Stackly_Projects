package service;

import dao.CustomerDAO;
import exception.CustomerNotFoundException;
import model.Customer;

import java.util.List;

public class CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerService() {
        customerDAO = new CustomerDAO();
    }

    public boolean registerCustomer(Customer customer) {
        return customerDAO.save(customer);
    }

    public Customer getCustomerById(int customerId) {
        Customer customer = customerDAO.findById(customerId);
        if(customer == null) {
            throw new CustomerNotFoundException("Customer ID " + customerId + " is not found.");
        }
        return customer;
    }

    public Customer getCustomerByPhone(String phone) {
        Customer customer = customerDAO.findByPhone(phone);
        if(customer == null) {
            throw  new CustomerNotFoundException("Customer phone number " + phone + " is not found.");
        }
        return customer;
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.findAll();
    }

    public boolean updateCustomer(Customer customer) {
        getCustomerById(customer.getCustomerId());
        return customerDAO.updateCustomer(customer);
    }

    public boolean removeCustomer(int customerId) {
        getCustomerById(customerId);
        return customerDAO.removeCustomer(customerId);
    }

}

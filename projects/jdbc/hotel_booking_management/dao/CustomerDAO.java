package dao;

import database.DBConnection;
import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public boolean save(Customer customer) {
        String sql = """
                INSERT INTO customers
                (customer_name, phone, email, address, id_proof)
                values (?, ?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getPhone());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getAddress());
            statement.setString(5, customer.getIdProof());

            int rows = statement.executeUpdate();

            if(rows > 0) {
                ResultSet keys = statement.getGeneratedKeys();
                if(keys.next()) {
                    customer.setCustomerId(keys.getInt(1));
                    return true;
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    public Customer findById(int customerId) {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";

        try (Connection connection = DBConnection.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);

            ResultSet result = statement.executeQuery();

            if(result.next()) {
                return mapCustomer(result);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public Customer findByPhone(String phone) {
        String sql = "SELECT * FROM customers WHERE phone = ?";

        try (Connection connection = DBConnection.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, phone);

            ResultSet result = statement.executeQuery();

            if(result.next()) {
                return mapCustomer(result);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Connection connection = DBConnection.getDatabaseConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            while (result.next()) {
                customers.add(mapCustomer(result));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customers;
    }


    public boolean updateCustomer (Customer customer) {
        String sql = """
                UPDATE customers
                SET customer_name = ?,
                    phone = ?,
                    email = ?,
                    address = ?,
                    id_proof = ?
                WHERE customer_id = ?
                """;

        try (Connection connection = DBConnection.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getPhone());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getAddress());
            statement.setString(5, customer.getIdProof());

            statement.setInt(6, customer.getCustomerId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    public boolean removeCustomer(int customerId) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";

        try (Connection connection = DBConnection.getDatabaseConnection();
             PreparedStatement statement= connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    private Customer mapCustomer(ResultSet result) throws SQLException {
        Customer customer = new Customer();

        customer.setCustomerId(result.getInt("customer_id"));
        customer.setCustomerName(result.getString("customer_name"));
        customer.setEmail(result.getString("email"));
        customer.setPhone(result.getString("phone"));
        customer.setAddress(result.getString("address"));
        customer.setIdProof(result.getString("id_proof"));

        return customer;

    }

}

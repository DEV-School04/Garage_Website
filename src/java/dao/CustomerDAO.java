package dao;

import java.sql.*;
import java.util.*;
import lib.MyConnection;
import models.Customer;

public class CustomerDAO {

    private void closeResources(Connection connect, PreparedStatement preSql, ResultSet resultTable) {
        try {
            if (resultTable != null) {
                resultTable.close();
            }
            if (preSql != null) {
                preSql.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }

    public List<Customer> getCustomers(String fieldName, String fieldValue) {
        List<Customer> customerList = new ArrayList<>();
        Connection connect = null;
        PreparedStatement preSql = null;
        ResultSet resultTable = null;
        String sql;

        try {
            connect = MyConnection.getConnection();
            if (fieldName == null || fieldValue == null || fieldValue.isEmpty()) {
                sql = "SELECT custID, custName, phone, sex, cusAddress FROM Customer";
                preSql = connect.prepareStatement(sql);
            } else {
                sql = "SELECT custID, custName, phone, sex, cusAddress FROM Customer WHERE " + fieldName + " = ?";
                preSql = connect.prepareStatement(sql);
                preSql.setString(1, fieldValue);
            }
            resultTable = preSql.executeQuery();
            while (resultTable.next()) {
                Customer customer = new Customer();
                customer.setCustID(resultTable.getString("custID"));
                customer.setCustName(resultTable.getString("custName"));
                customer.setPhone(resultTable.getString("phone"));
                customer.setSex(resultTable.getString("sex"));
                customer.setCustAddress(resultTable.getString("cusAddress"));
                customerList.add(customer);
            }
            return customerList;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return null;
        } finally {
            closeResources(connect, preSql, resultTable);
        }
    }

    public Customer getCustomerByNameAndPhone(String custName, String phone) {
        Customer customer = null;
        Connection connect = null;
        PreparedStatement preSql = null;
        ResultSet resultTable = null;
        String sql = "SELECT custID, custName, phone, sex, cusAddress FROM Customer WHERE custName = ? AND phone = ?";
        try {
            connect = MyConnection.getConnection();
            preSql = connect.prepareStatement(sql);

            preSql.setString(1, custName);
            preSql.setString(2, phone);

            resultTable = preSql.executeQuery();

            if (resultTable.next()) {
                customer = new Customer();
                customer.setCustID(resultTable.getString("custID").trim());
                customer.setCustName(resultTable.getString("custName").trim());
                customer.setPhone(resultTable.getString("phone").trim());
                customer.setSex(resultTable.getString("sex").trim());
                customer.setCustAddress(resultTable.getString("cusAddress").trim());
            }
            return customer;

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return null;
        } finally {
            closeResources(connect, preSql, resultTable);
        }

    }

    public boolean updateCustomer(String custID, String custName, String phone, String sex, String address) {
        Connection connect = null;
        PreparedStatement preSql = null;
        String sql = "UPDATE Customer SET custName = ?, phone = ?, sex = ?, cusAddress = ? WHERE custID = ?";

        try {
            connect = MyConnection.getConnection();
            preSql = connect.prepareStatement(sql);

            preSql.setString(1, custName);
            preSql.setString(2, phone);
            preSql.setString(3, sex);
            preSql.setString(4, address);
            preSql.setString(5, custID);

            int rowsAffected = preSql.executeUpdate();
            return rowsAffected > 0;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        } finally {
            closeResources(connect, preSql, null);
        }
    }
    
    

}

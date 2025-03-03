package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lib.MyConnection;
import models.Invoice;
import models.Car;
import models.Customer;
import models.SalesPerson;

public class InvoiceDAO {

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

    public List<Invoice> getInvoices(String fieldName, String fieldValue) {
        List<Invoice> invoices = new ArrayList<>();
        Connection connect = null;
        PreparedStatement preSql = null;
        ResultSet resultTable = null;
        String sql;
        try {
            connect = MyConnection.getConnection();
            if (fieldName == null || fieldValue == null || fieldValue.isEmpty()) {
                sql = "SELECT SI.invoiceID, SI.invoiceDate, "
                        + "SI.carID, C.serialNumber, C.model, C.colour, C.year, C.price, "
                        + "SP.salesID, SP.salesName, SP.birthday, SP.sex as sexSalesPerson, SP.salesAddress, "
                        + "CU.custID, CU.custName, CU.phone, CU.sex as sexCustomer, CU.cusAddress "
                        + "FROM SalesInvoice SI "
                        + "JOIN Cars C ON SI.carID = C.carID "
                        + "JOIN SalesPerson SP ON SI.salesID = SP.salesID "
                        + "JOIN Customer CU ON SI.custID = CU.custID ";
                preSql = connect.prepareStatement(sql);
            } else {
                sql = "SELECT SI.invoiceID, SI.invoiceDate, "
                        + "SI.carID, C.serialNumber, C.model, C.colour, C.year, C.price, "
                        + "SP.salesID, SP.salesName, SP.birthday, SP.sex as sexSalesPerson, SP.salesAddress, "
                        + "CU.custID, CU.custName, CU.phone, CU.sex as sexCustomer, CU.cusAddress "
                        + "FROM SalesInvoice SI "
                        + "JOIN Cars C ON SI.carID = C.carID "
                        + "JOIN SalesPerson SP ON SI.salesID = SP.salesID "
                        + "JOIN Customer CU ON SI.custID = CU.custID "
                        + "WHERE SI." + fieldName + " = ?";
                preSql = connect.prepareStatement(sql);
                preSql.setString(1, fieldValue);
            }
            resultTable = preSql.executeQuery();
            while (resultTable.next()) {
                Customer customer = new Customer(resultTable.getString("custID"),
                        resultTable.getString("custName"),
                        resultTable.getString("phone"),
                        resultTable.getString("sexCustomer"),
                        resultTable.getString("cusAddress"));

                SalesPerson salesPerson = new SalesPerson(resultTable.getString("salesID"),
                        resultTable.getString("salesName"),
                        resultTable.getDate("birthday"),
                        resultTable.getString("sexSalesPerson"),
                        resultTable.getString("salesAddress"));

                Car car = new Car(resultTable.getString("carID"),
                        resultTable.getString("serialNumber"),
                        resultTable.getString("model"),
                        resultTable.getString("colour"),
                        resultTable.getString("year"),
                        resultTable.getString("price")
                );

                Invoice invoice = new Invoice(
                        resultTable.getString("invoiceID"),
                        resultTable.getDate("invoiceDate").toLocalDate(),
                        customer,
                        salesPerson,
                        car
                );
                invoices.add(invoice);
            }
            return invoices;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return null;
        } finally {
            closeResources(connect, preSql, resultTable);
        }
    }

    public boolean createInvoice(String invoiceID, LocalDate invoiceDate, String customerID, String salesPersonID, String carID) {
        Connection connect = null;
        PreparedStatement preSql = null;
        String sql = "INSERT INTO SalesInvoice (invoiceID, invoiceDate, custID, salesID, carID) VALUES (?, ?, ?, ?, ?)";
        try {
            connect = MyConnection.getConnection();
            preSql = connect.prepareStatement(sql);
            preSql.setString(1, invoiceID);
            preSql.setDate(2, java.sql.Date.valueOf(invoiceDate));
            preSql.setString(3, customerID);
            preSql.setString(4, salesPersonID);
            preSql.setString(5, carID);
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

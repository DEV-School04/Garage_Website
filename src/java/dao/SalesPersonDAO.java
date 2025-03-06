/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lib.MyConnection;
import models.SalesPerson;

/**
 *
 * @author nhutt
 */
public class SalesPersonDAO {

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

    // Lấy danh sách nhân viên bán hàng (tương tự getCustomers)
    public List<SalesPerson> getSalesPersons(String fieldName, String fieldValue) {
        List<SalesPerson> salesPersonList = new ArrayList<>();
        Connection connect = null;
        PreparedStatement preSql = null;
        ResultSet resultTable = null;
        String sql;

        try {
            connect = MyConnection.getConnection();
            if (fieldName == null || fieldValue == null || fieldValue.isEmpty()) {
                sql = "SELECT salesID, salesName, birthday, sex, salesAddress FROM SalesPerson";
                preSql = connect.prepareStatement(sql);
            } else {
                sql = "SELECT salesID, salesName, birthday, sex, salesAddress FROM SalesPerson WHERE " + fieldName + " = ?";
                preSql = connect.prepareStatement(sql);
                preSql.setString(1, fieldValue);
            }
            resultTable = preSql.executeQuery();
            while (resultTable.next()) {
                SalesPerson salesPerson = new SalesPerson();
                salesPerson.setSalesID(resultTable.getString("salesID"));
                salesPerson.setSalesName(resultTable.getString("salesName"));
                salesPerson.setBirthday(resultTable.getDate("birthday"));
                salesPerson.setSex(resultTable.getString("sex"));
                salesPerson.setSalesAddress(resultTable.getString("salesAddress"));
                salesPersonList.add(salesPerson);
            }
            return salesPersonList;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return null;
        } finally {
            closeResources(connect, preSql, resultTable);
        }
    }

}

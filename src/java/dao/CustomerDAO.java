/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.*;
import lib.MyConnection;

/**
 *
 * @author nhutt
 */
public class CustomerDAO {

    public String SignUp(String custName, String phone, String custAddress, String sex) {
        String result = "";
        try {
            Connection connect = MyConnection.getConnection();
            if (connect != null) {
                String sql = "INSERT INTO Customer (custID, custName, phone, sex, cusAddress) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preSql = connect.prepareStatement(sql);
                preSql.setString(1, phone);
                preSql.setString(2, custName);
                preSql.setString(3, phone);
                preSql.setString(4, sex);
                preSql.setString(5, custAddress);
                int rowsInserted = preSql.executeUpdate();
                if (rowsInserted > 0) {
                    result = "Dữ liệu đã được thêm thành công!";
                }
                connect.close();
            }
        } catch (ClassNotFoundException | SQLException e) {
            result = "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage();
        }
        return result;
    }
}

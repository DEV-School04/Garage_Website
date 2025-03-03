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
import models.Car;

/**
 *
 * @author nhutt
 */
public class CarDAO {

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
    public List<Car> getCars(String fieldName, String fieldValue) {
        List<Car> carList = new ArrayList<>();
        Connection connect = null;
        PreparedStatement preSql = null;
        ResultSet resultTable = null;
        String sql;

        try {
            connect = MyConnection.getConnection();
            if (fieldName == null || fieldValue == null || fieldValue.isEmpty()) {
                sql = "SELECT carID, serialNumber, model, colour, year, price FROM Cars";
                preSql = connect.prepareStatement(sql);
            } else {
                sql = "SELECT carID, serialNumber, model, colour, year, price FROM Cars WHERE " + fieldName + " = ?";
                preSql = connect.prepareStatement(sql);
                preSql.setString(1, fieldValue);
            }
            resultTable = preSql.executeQuery();
            while (resultTable.next()) {
                Car car = new Car();
                car.setCarID(resultTable.getString("carID"));
                car.setSerialNumber(resultTable.getString("serialNumber"));
                car.setModel(resultTable.getString("model"));
                car.setColour(resultTable.getString("colour"));
                car.setYear(resultTable.getString("year"));
                car.setPrice(resultTable.getString("price"));
                carList.add(car);
            }
            return carList;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return null;
        } finally {
            closeResources(connect, preSql, resultTable);
        }
    }

}

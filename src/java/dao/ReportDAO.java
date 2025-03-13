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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lib.MyConnection;

/**
 *
 * @author nhutt
 */
public class ReportDAO {

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

    public Map<Integer, Integer> getCarsSoldByYear() {
        Map<Integer, Integer> carsByYear = new HashMap<>();
        Connection connect = null;
        PreparedStatement preSql = null;
        ResultSet resultTable = null;

        try {
            connect = MyConnection.getConnection();
            String sql = "SELECT YEAR(si.invoiceDate) AS sale_year, COUNT(si.carID) AS cars_sold "
                    + "FROM [dbo].[SalesInvoice] si "
                    + "GROUP BY YEAR(si.invoiceDate) "
                    + "ORDER BY YEAR(si.invoiceDate)";
            preSql = connect.prepareStatement(sql);
            resultTable = preSql.executeQuery();

            while (resultTable.next()) {
                int year = resultTable.getInt("sale_year");
                int carsSold = resultTable.getInt("cars_sold");
                carsByYear.put(year, carsSold);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return null;
        } finally {
            closeResources(connect, preSql, resultTable);
        }
        return carsByYear;
    }

    public Map<Integer, Double> getRevenueByYear() {
        Map<Integer, Double> revenueByYear = new HashMap<>();
        Connection connect = null;
        PreparedStatement preSql = null;
        ResultSet resultTable = null;

        try {
            connect = MyConnection.getConnection();
            String sql = "SELECT YEAR(si.invoiceDate) AS sale_year, SUM(c.price) AS total_revenue "
                    + "FROM [dbo].[SalesInvoice] si "
                    + "INNER JOIN [dbo].[Cars] c ON si.carID = c.carID "
                    + "GROUP BY YEAR(si.invoiceDate) "
                    + "ORDER BY YEAR(si.invoiceDate)";
            preSql = connect.prepareStatement(sql);
            resultTable = preSql.executeQuery();

            while (resultTable.next()) {
                int year = resultTable.getInt("sale_year");
                double revenue = resultTable.getDouble("total_revenue");
                revenueByYear.put(year, revenue);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return null;
        } finally {
            closeResources(connect, preSql, resultTable);
        }

        return revenueByYear;
    }

    public Map<String, Integer> getTopSellingModels() {
        Map<String, Integer> modelSales = new HashMap<>();
        Connection connect = null;
        PreparedStatement preSql = null;
        ResultSet resultTable = null;

        try {
            connect = MyConnection.getConnection();
            String sql = "SELECT c.model, COUNT(si.carID) AS sales_count "
                    + "FROM [dbo].[Cars] c "
                    + "LEFT JOIN [dbo].[SalesInvoice] si ON c.carID = si.carID "
                    + "GROUP BY c.model "
                    + "ORDER BY sales_count DESC";
            preSql = connect.prepareStatement(sql);
            resultTable = preSql.executeQuery();

            while (resultTable.next()) {
                String model = resultTable.getString("model");
                int salesCount = resultTable.getInt("sales_count");
                modelSales.put(model, salesCount);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return null;
        } finally {
            closeResources(connect, preSql, resultTable);
        }

        return modelSales;
    }

    public Map<String, Integer> getBestUsedParts() {
        Map<String, Integer> partsUsed = new HashMap<>();
        Connection connect = null;
        PreparedStatement preSql = null;
        ResultSet resultTable = null;

        try {
            connect = MyConnection.getConnection();
            String sql = "SELECT p.partName, SUM(pu.numberUsed) AS total_used "
                    + "FROM [dbo].[Parts] p "
                    + "LEFT JOIN [dbo].[PartsUsed] pu ON p.partID = pu.partID "
                    + "GROUP BY p.partName "
                    + "ORDER BY total_used DESC";
            preSql = connect.prepareStatement(sql);
            resultTable = preSql.executeQuery();

            while (resultTable.next()) {
                String partName = resultTable.getString("partName");
                int totalUsed = resultTable.getInt("total_used");
                partsUsed.put(partName, totalUsed);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return null;
        } finally {
            closeResources(connect, preSql, resultTable);
        }

        return partsUsed;
    }

    public Map<String, Integer> getPartsUsedCount() {
        Map<String, Integer> partsUsedCount = new HashMap<>();
        Connection connect = null;
        PreparedStatement preSql = null;
        ResultSet resultTable = null;

        try {
            connect = MyConnection.getConnection();
            String sql = "SELECT p.partName, SUM(pu.numberUsed) AS total_used "
                    + "FROM [dbo].[Parts] p "
                    + "INNER JOIN [dbo].[PartsUsed] pu ON p.partID = pu.partID "
                    + "GROUP BY p.partName "
                    + "ORDER BY total_used DESC";
            preSql = connect.prepareStatement(sql);
            resultTable = preSql.executeQuery();
            while (resultTable.next()) {
                String partName = resultTable.getString("partName");
                int totalUsed = resultTable.getInt("total_used");
                partsUsedCount.put(partName, totalUsed);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return null;
        } finally {
            closeResources(connect, preSql, resultTable);
        }
        return partsUsedCount;
    }

    public List<Map.Entry<String, Integer>> getTopMechanics() {
        Map<String, Integer> mechanicRepairs = new HashMap<>();
        Connection connect = null;
        PreparedStatement preSql = null;
        ResultSet resultTable = null;

        try {
            connect = MyConnection.getConnection();
            String sql = "SELECT TOP 3 m.mechanicName, COUNT(sm.serviceTicketID) AS repair_count "
                    + "FROM [dbo].[Mechanic] m "
                    + "LEFT JOIN [dbo].[ServiceMehanic] sm ON m.mechanicID = sm.mechanicID "
                    + "GROUP BY m.mechanicID, m.mechanicName "
                    + "ORDER BY repair_count DESC";
            preSql = connect.prepareStatement(sql);
            resultTable = preSql.executeQuery();

            while (resultTable.next()) {
                String mechanicName = resultTable.getString("mechanicName");
                int repairCount = resultTable.getInt("repair_count");
                mechanicRepairs.put(mechanicName, repairCount);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return null;
        } finally {
            closeResources(connect, preSql, resultTable);
        }

        // Chuyển Map thành List để lấy top 3
        return mechanicRepairs.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toList());
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.CustomerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Customer;

/**
 *
 * @author nhutt
 */
@WebServlet(name = "SignUpUser", urlPatterns = {"/signup/user"})
public class SignUpUser extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String name = request.getParameter("name");
            String phoneNumber = request.getParameter("phoneNumber");
            String address = request.getParameter("address");
            String sex = request.getParameter("sex");

            CustomerDAO custDAO = new CustomerDAO();
            Customer customerExist = custDAO.getCustomerByNameAndPhone(name, phoneNumber);

            if (customerExist != null) {
                out.println("Customer already exists.");
            } else {
                String id = custDAO.getCustomers(null, null).stream()
                        .max(Comparator.comparingLong(customer -> Long.parseLong(customer.getCustID())))
                        .map(customer -> Long.parseLong(customer.getCustID()) + 1) 
                        .map(String::valueOf)
                        .orElse(null);
                boolean success = custDAO.insertCustomer(id,name, phoneNumber, sex, address);
                if (success) {
                    Customer customer = custDAO.getCustomerByNameAndPhone(name, phoneNumber);
                    if (customer != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("user", customer);
                        response.setStatus(HttpServletResponse.SC_CREATED); // 201
                        out.println("Customer registered successfully.");
                        response.sendRedirect("../index.html");
                    } else {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); //500
                        out.println("Error retrieving newly created customer.");
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); //500
                    out.println("Error inserting new customer.");
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

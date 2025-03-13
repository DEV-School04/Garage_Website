/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Customer;

/**
 *
 * @author nhutt
 */
public class Controller extends HttpServlet {

    private final String DASHBOARD = "Dashboard";
    private final String LOGIN = "Login";
    private final String LOGOUT = "Logout";
    private final String CHANGE_PROFILE_USER = "ChangeProfileUser";
    private final String INVOICES = "Invoices";
    private final String ERROR = "Error";
    private final String ROLE = "Role";
    private final String REPORT = "Report";

    public String getUrlPagesJSPOfUser(String namePages) {
        return "/pages/customer/" + namePages + ".jsp";
    }

    private void SigupRole(HttpServletRequest request, String role) {
        request.getSession(true).setAttribute(ROLE, role);
    }

    private void handlerControllerFromCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String action = request.getParameter("action");
        String url = "";
        if ((Customer) session.getAttribute("user") == null) {
            action = LOGIN;
        } else if (action == null) {
            action = DASHBOARD;
        }
        if (action != null) {
            switch (action) {
                case LOGIN:
                    url = getUrlPagesJSPOfUser(LOGIN);
                    if (request.getMethod().equalsIgnoreCase("POST")) {
                        url = LOGIN + "User";
                    }
                    break;
                case DASHBOARD:
                    url = getUrlPagesJSPOfUser(DASHBOARD);
                    break;
                case LOGOUT:
                    session.invalidate();
                    url = "/pages/Role.jsp";
                    break;
                case CHANGE_PROFILE_USER:
                    url = CHANGE_PROFILE_USER;
                    break;
                case INVOICES:
                    url = getUrlPagesJSPOfUser(INVOICES);
                    break;
                case ERROR:
                    url = getUrlPagesJSPOfUser(ERROR + response.getStatus());
                    break;
                default:
                    url = "/pages/Error404.jsp";
                    break;
            }
        } else {
            url = "/pages/Error404.jsp";
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    private void handlerControllerFromSalesPerson(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        String url = "";

        if (action == null) {
            url = REPORT;
        } else {
            switch (action) {
                case DASHBOARD:
                    url = "/pages/Role.jsp";
                    break;
                case INVOICES:
                    url = "Invoices";
                    break;
                case "create":
                    url = "Invoices";
                    break;
                case REPORT:
                    url = "/page/" + REPORT + ".jsp";
                    break;
                default:
                    url = "/pages/Error404.jsp";
                    break;
            }
        }
        request.getRequestDispatcher(url).forward(request, response);
    }

    private void handlerControllerFromMechanics(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.getWriter().print(
                "<!DOCTYPE html>"
                + "<html><body>"
                + "<h1>Trang Mechanics</h1>"
                + "<a href='/Role.jsp'>Back</a>"
                + "</body></html>"
        );
    }

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

        String roleInput = request.getParameter("role");
        String roleSesstion = (String) request.getSession().getAttribute(ROLE);
        if (roleSesstion == null && roleInput == null) {
            request.getRequestDispatcher("/pages/Role.jsp").forward(request, response);
        } else if (roleInput != null) {
            SigupRole(request, roleInput);
            roleSesstion = (String) request.getSession().getAttribute(ROLE);
        }
        if (roleSesstion != null) {
            switch (roleSesstion.toLowerCase()) {
                case "customer":
                    handlerControllerFromCustomer(request, response);
                    break;
                case "sales":
                    handlerControllerFromSalesPerson(request, response);
                    break;
                case "mechanics":
                    handlerControllerFromMechanics(request, response);
                    break;
                default:
                    request.getRequestDispatcher("/pages/Role.jsp").forward(request, response);
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

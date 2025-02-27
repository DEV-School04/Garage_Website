/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    private final String INVOICE_DETAIL = "InvoiceDetail";
    private final String ERROR = "Error";
    private final String ROLE = "role";

    public String getUrlPagesJSPOfUser(String namePages) {
        return "./pages/customer/" + namePages + ".jsp";
    }

    private void SigupRole(HttpServletRequest request, String role) {
        request.getSession(true).setAttribute(ROLE, role);
    }

    private void handlerControlllerFromCustomer(HttpServletRequest request, HttpServletResponse response, String role) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        session.setAttribute(ROLE, role);
        String action = request.getParameter("action");
        String url = "";
        if (session.getAttribute("user") == null) {
            action = LOGIN;
        } else if (action == null) {
            action = DASHBOARD;
        }
        if (action != null) {
            switch (action) {
                case LOGIN:
                    url = getUrlPagesJSPOfUser(LOGIN);
                    break;
                case DASHBOARD:
                    url = getUrlPagesJSPOfUser(DASHBOARD);
                    break;
                case LOGOUT:
                    session.invalidate();
                    url = "./pages/Role.jsp";
                    break;
                case CHANGE_PROFILE_USER:
                    url = CHANGE_PROFILE_USER;
                    break;
                case INVOICES:
                    url = request.getAttribute("action") != null
                            && request.getAttribute("action").toString().equalsIgnoreCase("Invoices")
                            ? getUrlPagesJSPOfUser(INVOICES) : INVOICES;
                    break;
                case INVOICE_DETAIL:
                    url = INVOICE_DETAIL;
                    break;
                case ERROR:
                    url = getUrlPagesJSPOfUser(ERROR + response.getStatus());
                    break;
                default:
                    url = getUrlPagesJSPOfUser(ERROR);
                    break;
            }
        } else {
            url = getUrlPagesJSPOfUser(ERROR);
        }

        // Forward request
        request.getRequestDispatcher(url).forward(request, response);
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

        if (roleInput == null && roleSesstion == null) {
            request.getRequestDispatcher("./pages/Role.jsp").forward(request, response);
            return;
        }

        if (roleInput != null && roleSesstion == null) {
            SigupRole(request, roleInput);
            roleSesstion = roleInput;
        }

        if (roleSesstion != null) {
            switch (roleSesstion.toLowerCase()) {
                case "customer":
                    handlerControlllerFromCustomer(request, response, roleSesstion);
                    break;
                case "sale":
                    response.getWriter().print("<h1>Role: Sale</h1>");
                    break;
                case "mechanics":
                    response.getWriter().print("<h1>Role: Mechanics</h1>");
                    break;
                default:
                    request.getRequestDispatcher("./pages/Error.jsp").forward(request, response);
                    break;
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

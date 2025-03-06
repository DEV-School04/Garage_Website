package controllers;

import dao.InvoiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Customer;
import models.Invoice;

/**
 *
 * @author nhutt
 */
public class Invoices extends HttpServlet {

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
    }

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
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("user");
        if (customer == null) {
            response.sendRedirect(request.getContextPath() + "/Controller?action=Login");
            return;
        }

        List<Invoice> invoices;
        InvoiceDAO invoiceDAO = new InvoiceDAO();
        try {
            invoices = invoiceDAO.getInvoices("custID", customer.getCustID());
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi lấy danh sách hóa đơn: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            request.getRequestDispatcher("/Error.jsp").forward(request, response);
            return;
        }

        if (invoices != null && !invoices.isEmpty()) {
            request.setAttribute("invoices", invoices);
            request.setAttribute("action", "Invoices");
            request.getRequestDispatcher("/Controller").forward(request, response);
        } else {
            request.setAttribute("error", "Không tìm thấy hóa đơn nào.");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            request.getRequestDispatcher("/Error.jsp").forward(request, response);
        }
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        
        Date invoiceDate = Date.valueOf(request.getParameter("invoiceDate"));
        String custID = request.getParameter("customerID");
        String salesID = request.getParameter("salesPersonID");
        String carID = request.getParameter("carID");

        try (PrintWriter out = response.getWriter()) {
            
            InvoiceDAO invoiceDAO = new InvoiceDAO();
            boolean isCreated;

            
            int maxID = invoiceDAO.getMaxInvoiceID();
            String newInvoiceID;
            if (maxID == -1) {
                request.getSession().setAttribute("messageCreated", "Error retrieving max invoice ID.");
                response.sendRedirect("InvoiceCreate.jsp"); // Redirect về trang tạo hóa đơn mới
                return;
            } else {
                int nextID = maxID + 1;
                newInvoiceID = String.valueOf(nextID);
            }

            try {
                isCreated = invoiceDAO.createInvoice(newInvoiceID, invoiceDate, custID, salesID, carID);
            } catch (Exception e) {
                request.getSession().setAttribute("messageCreated", "Error creating invoice: " + e.getMessage());
                response.sendRedirect("InvoiceCreate.jsp");
                return;
            }

            if (isCreated) {
                request.getSession().setAttribute("messageCreated", "Invoice was created successfully!");
            } else {
                request.getSession().setAttribute("messageCreated", "Failed to create invoice. Please try again.");
            }
            response.sendRedirect("InvoiceCreate.jsp"); // Redirect về trang tạo hóa đơn mới
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet to manage invoices";
    }
}

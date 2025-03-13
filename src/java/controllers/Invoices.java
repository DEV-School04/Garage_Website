package controllers;

import dao.CarDAO;
import dao.CustomerDAO;
import dao.InvoiceDAO;
import dao.SalesPersonDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Car;
import models.Customer;
import models.SalesPerson;

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
        CustomerDAO customerDAO = new CustomerDAO();
        SalesPersonDAO salesPersonDAO = new SalesPersonDAO();
        CarDAO carDAO = new CarDAO();
        try {
            List<Customer> customers = customerDAO.getCustomers(null, null);
            List<SalesPerson> salesPersons = salesPersonDAO.getSalesPersons(null, null);
            List<Car> cars = carDAO.getCars(null, null);

            request.setAttribute("customers", customers);
            request.setAttribute("salesPersons", salesPersons);
            request.setAttribute("cars", cars);
            request.getRequestDispatcher("/pages/InvoiceCreate.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            request.setAttribute("Error", "Không thể truy xuất dữ liệu");
            request.getRequestDispatcher("/pages/Error500.jsp").forward(request, response);
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

        InvoiceDAO invoiceDAO = new InvoiceDAO();
        boolean isCreated;
        int maxID = invoiceDAO.getMaxInvoiceID();
        String newInvoiceID = maxID + 1 + "";
        try {
            isCreated = invoiceDAO.createInvoice(newInvoiceID, invoiceDate, custID, salesID, carID);
            if (isCreated) {
                request.setAttribute("messageCreated", "Invoice was created successfully!");
            } else {
                request.setAttribute("messageCreated", "Failed to create invoice. Please try again.");
            }
        } catch (Exception e) {
            request.setAttribute("messageCreated", "Không tạo được hóa đơn");
        }
        request.getRequestDispatcher("/pages/InvoiceCreate.jsp").forward(request, response);
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

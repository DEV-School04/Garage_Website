package controllers;

import dao.InvoiceDAO;
import dao.CustomerDAO;
import dao.SalesPersonDAO;
import dao.CarDAO;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Customer;
import models.SalesPerson;
import models.Car;
import models.Invoice;

/**
 *
 * @author nhutt
 */
public class Invoices extends HttpServlet {

    private InvoiceDAO invoiceDAO;
    private CustomerDAO customerDAO;
    private SalesPersonDAO salesPersonDAO;
    private CarDAO carDAO;

    
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
            request.getRequestDispatcher("Controller?action=Login").forward(request, response);
            return;
        }

        List<Invoice> invoices;
        try {
            invoices = invoiceDAO.getInvoices("custID", customer.getCustID());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            request.getRequestDispatcher("./Controller?action=Error").forward(request, response);
            return;
        }

        if (invoices != null) {
            request.setAttribute("invoices", invoices);
            response.setStatus(HttpServletResponse.SC_OK); // 200
            request.setAttribute("action", "Invoices");
            request.getRequestDispatcher("Controller").forward(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
            request.getRequestDispatcher("./Controller?action=Error").forward(request, response);
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
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("user");
        if (customer == null) {
            request.getRequestDispatcher("Controller?action=Login").forward(request, response);
            return;
        }

        String action = request.getParameter("action");
        if ("createInvoiceForm".equals(action)) {
            // Hiển thị form tạo hóa đơn mới
            prepareCreateInvoiceForm(request, response);
        } else if ("create".equals(action)) {
            // Tạo hóa đơn mới
            createInvoice(request, response);
        } else {
            // Nếu chỉ thay đổi dropdown (không có action cụ thể), hiển thị lại form với dữ liệu đã chọn
            prepareCreateInvoiceForm(request, response);
        }
    }

    /**
     * Chuẩn bị dữ liệu và hiển thị form tạo hóa đơn mới
     */
    private void prepareCreateInvoiceForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String customerID = request.getParameter("customerID");
        String salesPersonID = request.getParameter("salesPersonID");
        String carID = request.getParameter("carID");
        String invoiceDate = request.getParameter("invoiceDate");

        // Lấy chi tiết dựa trên ID đã chọn
        Customer selectedCustomer = null;
        if (customerID != null && !customerID.isEmpty()) {
            List<Customer> customerList = customerDAO.getCustomers("custID", customerID);
            if (customerList != null && !customerList.isEmpty()) {
                selectedCustomer = customerList.get(0); // Lấy khách hàng đầu tiên trong danh sách
            }
        }

        SalesPerson selectedSalesPerson = null;
        if (salesPersonID != null && !salesPersonID.isEmpty()) {
            List<SalesPerson> salesPersonList = salesPersonDAO.getSalesPersons("salesID", salesPersonID);
            if (salesPersonList != null && !salesPersonList.isEmpty()) {
                selectedSalesPerson = salesPersonList.get(0); // Lấy nhân viên đầu tiên trong danh sách
            }
        }

        Car selectedCar = null;
        if (carID != null && !carID.isEmpty()) {
            List<Car> carList = carDAO.getCars("carID", carID);
            if (carList != null && !carList.isEmpty()) {
                selectedCar = carList.get(0); // Lấy xe đầu tiên trong danh sách
            }
        }

        // Đặt dữ liệu để hiển thị trong JSP
        request.setAttribute("selectedCustomer", selectedCustomer);
        request.setAttribute("selectedSalesPerson", selectedSalesPerson);
        request.setAttribute("selectedCar", selectedCar);
        request.setAttribute("invoiceDate", invoiceDate);

        // Load danh sách cho dropdown
        List<Customer> customers = customerDAO.getCustomers(null, null);
        List<SalesPerson> salesPersons = salesPersonDAO.getSalesPersons(null, null);
        List<Car> cars = carDAO.getCars(null, null);

        // Kiểm tra nếu load danh sách thất bại
        if (customers == null || salesPersons == null || cars == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            request.setAttribute("error", "Không thể load danh sách dữ liệu. Vui lòng thử lại.");
            request.getRequestDispatcher("./Controller?action=Error").forward(request, response);
            return;
        }

        request.setAttribute("customers", customers);
        request.setAttribute("salesPersons", salesPersons);
        request.setAttribute("cars", cars);

        // Chuyển tiếp đến JSP
        response.setStatus(HttpServletResponse.SC_OK); // 200
        request.getRequestDispatcher("/CreateInvoice.jsp").forward(request, response);
    }

    /**
     * Xử lý tạo hóa đơn mới
     */
    private void createInvoice(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String customerID = request.getParameter("customerID");
        String salesPersonID = request.getParameter("salesPersonID");
        String carID = request.getParameter("carID");
        String invoiceDate = request.getParameter("invoiceDate");

        // Kiểm tra dữ liệu đầu vào
        if (customerID == null || salesPersonID == null || carID == null || invoiceDate == null
                || customerID.isEmpty() || salesPersonID.isEmpty() || carID.isEmpty() || invoiceDate.isEmpty()) {
            request.setAttribute("error", "Vui lòng điền đầy đủ thông tin.");
            prepareCreateInvoiceForm(request, response);
            return;
        }

        // Tạo ID cho hóa đơn (có thể thay đổi logic tạo ID tùy theo yêu cầu)
        String invoiceID = "INV" + System.currentTimeMillis();
        LocalDate date;
        try {
            date = LocalDate.parse(invoiceDate);
        } catch (Exception e) {
            request.setAttribute("error", "Ngày lập hóa đơn không hợp lệ.");
            prepareCreateInvoiceForm(request, response);
            return;
        }

        // Kiểm tra tồn tại của khách hàng, nhân viên, và xe trước khi tạo hóa đơn
        List<Customer> customerCheck = customerDAO.getCustomers("custID", customerID);
        List<SalesPerson> salesPersonCheck = salesPersonDAO.getSalesPersons("salesID", salesPersonID);
        List<Car> carCheck = carDAO.getCars("carID", carID);

        if (customerCheck == null || customerCheck.isEmpty()
                || salesPersonCheck == null || salesPersonCheck.isEmpty()
                || carCheck == null || carCheck.isEmpty()) {
            request.setAttribute("error", "Dữ liệu không hợp lệ (khách hàng, nhân viên, hoặc xe không tồn tại).");
            prepareCreateInvoiceForm(request, response);
            return;
        }

        // Lưu hóa đơn vào database
        boolean success = invoiceDAO.createInvoice(invoiceID, date, customerID, salesPersonID, carID);
        if (success) {
            response.sendRedirect(request.getContextPath() + "/invoices");
        } else {
            request.setAttribute("error", "Không thể tạo hóa đơn. Vui lòng thử lại.");
            prepareCreateInvoiceForm(request, response);
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

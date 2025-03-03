<%-- 
    Document   : Invoice
    Created on : Feb 20, 2025, 9:55:02 AM
    Author     : nhutt
--%>

<%@page import="models.Car"%>
<%@page import="models.SalesPerson"%>
<%@page import="models.Customer"%>
<%@page import="java.util.List"%>
<%@page import="models.Invoice"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Danh sách hóa đơn</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">

        <style>
            .modal-header {
                background: #007bff;
                color: white;
            }
            .modal-body {
                background: #f8f9fa;
            }
            .info-box {
                background: white;
                padding: 15px;
                border-radius: 10px;
                box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1);
            }
            .info-box h6 {
                color: #007bff;
            }
            .icon {
                font-size: 20px;
                margin-right: 8px;
                color: #dc3545;
            }
        </style>
    </head>
    <body class="bg-light">
        <div class="container mt-5 p-4 bg-white rounded shadow">

            <!-- Home Button -->
            <a href="Controller?action=Dashboard" class="btn btn-danger mb-3">
                Trang chủ
            </a>

            <h1 class="text-center text-danger fw-bold">Danh sách hóa đơn</h1>

            <!-- Invoice Table -->
            <div class="table-responsive">
                <table class="table table-bordered text-center">
                    <thead class="table-danger text-white">
                        <tr>
                            <th>Mã hóa đơn</th>
                            <th>Ngày lập</th>
                            <th>Khách hàng</th>
                            <th>Nhân viên bán hàng</th>
                            <th>Xe</th>
                            <th>Chi tiết</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty invoices}">
                                <c:forEach var="invoice" items="${invoices}">
                                    <tr>
                                        <td><c:out value="${invoice.invoiceID}" /></td>
                                        <td><c:out value="${invoice.invoiceDate}" /></td>
                                        <td><c:out value="${invoice.customer != null ? invoice.customer.custName : 'N/A'}" /></td>
                                        <td><c:out value="${invoice.salesPerson != null ? invoice.salesPerson.salesName : 'N/A'}" /></td>
                                        <td><c:out value="${invoice.car != null ? invoice.car.model.concat(' - ').concat(invoice.car.year) : 'N/A'}" /></td>
                                        <td>
                                            <button class="btn btn-danger text-white" data-bs-toggle="modal" data-bs-target="#invoiceModal${invoice.invoiceID}">
                                                Xem chi tiết
                                            </button>
                                        </td>
                                    </tr>
                                <div class="modal fade" id="invoiceModal${invoice.invoiceID}" tabindex="-1" aria-labelledby="invoiceModalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-lg">
                                        <div class="modal-content">
                                            <div class="modal-header bg-danger text-white">
                                                <h5 class="modal-title"><i class="icon bi bi-receipt"></i> Hóa đơn #<c:out value="${invoice.invoiceID}" /></h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <p><i class="icon bi bi-calendar"></i> <strong>Ngày lập:</strong> <c:out value="${invoice.invoiceDate}" /></p>

                                                <div class="row">
                                                    <!-- Thông tin khách hàng -->
                                                    <div class="col-md-6">
                                                        <div class="info-box bg-light">
                                                            <h6 class="text-danger"><i class="icon bi bi-person-circle"></i> Khách hàng</h6>
                                                            <p><strong>Tên:</strong> <c:out value="${invoice.customer != null ? invoice.customer.custName : 'Không có dữ liệu'}" /></p>
                                                            <p><strong>Giới tính:</strong> 
                                                                <c:choose>
                                                                    <c:when test="${invoice.customer != null}">
                                                                        <c:out value="${invoice.customer.sex == 'M' ? 'Nam' : 'Nữ'}" />
                                                                    </c:when>
                                                                    <c:otherwise>Không có dữ liệu</c:otherwise>
                                                                </c:choose>
                                                            </p>
                                                            <p><strong>Số điện thoại:</strong> <c:out value="${invoice.customer != null ? '0'.concat(invoice.customer.phone) : 'Không có dữ liệu'}" /></p>
                                                            <p><strong>Địa chỉ:</strong> <c:out value="${invoice.customer != null ? invoice.customer.custAddress : 'Không có dữ liệu'}" /></p>
                                                        </div>
                                                    </div>

                                                    <!-- Thông tin nhân viên bán hàng -->
                                                    <div class="col-md-6">
                                                        <div class="info-box bg-light">
                                                            <h6 class="text-danger"><i class="icon bi bi-briefcase"></i> Nhân viên bán hàng</h6>
                                                            <p><strong>ID:</strong> <c:out value="${invoice.salesPerson != null ? invoice.salesPerson.salesID : 'Không có dữ liệu'}" /></p>
                                                            <p><strong>Tên:</strong> <c:out value="${invoice.salesPerson != null ? invoice.salesPerson.salesName : 'Không có dữ liệu'}" /></p>
                                                            <p><strong>Giới tính:</strong> 
                                                                <c:choose>
                                                                    <c:when test="${invoice.salesPerson != null}">
                                                                        <c:out value="${invoice.salesPerson.sex == 'M' ? 'Nam' : 'Nữ'}" />
                                                                    </c:when>
                                                                    <c:otherwise>Không có dữ liệu</c:otherwise>
                                                                </c:choose>
                                                            </p>
                                                        </div>
                                                    </div>
                                                </div>

                                                <!-- Thông tin xe -->
                                                <div class="info-box bg-light mt-3">
                                                    <h6 class="text-danger"><i class="icon bi bi-car-front"></i> Xe</h6>
                                                    <p><strong>Mã xe:</strong> <c:out value="${invoice.car != null ? invoice.car.carID : 'Không có dữ liệu'}" /></p>
                                                    <p><strong>Model:</strong> <c:out value="${invoice.car != null ? invoice.car.model : 'Không có dữ liệu'}" /></p>
                                                    <p><strong>Năm sản xuất:</strong> <c:out value="${invoice.car != null ? invoice.car.year : 'Không có dữ liệu'}" /></p>
                                                    <p><strong>Màu sắc:</strong> <c:out value="${invoice.car != null ? invoice.car.colour : 'Không có dữ liệu'}" /></p>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="6" class="text-center">Không có hóa đơn nào</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>
        </div> 
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
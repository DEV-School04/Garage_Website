<%-- 
    Document   : newjsp
    Created on : Mar 3, 2025, 9:18:29 AM
    Author     : nhutt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Tạo hóa đơn mới</title>
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
            .form-label {
                font-weight: bold;
            }
            .detail-field {
                margin-top: 10px;
                padding: 10px;
                background: #f8f9fa;
                border-radius: 5px;
            }
            .error {
                color: red;
                text-align: center;
            }
        </style>
    </head>
    <body class="bg-light">
        <div class="container mt-5 p-4 bg-white rounded shadow">
            <!-- Home Button -->
            <a href="Controller?action=Dashboard" class="btn btn-danger mb-3">
                Trang chủ
            </a>

            <h1 class="text-center text-danger fw-bold">Tạo hóa đơn mới</h1>

            <!-- Hiển thị thông báo lỗi nếu có -->
            <c:if test="${not empty error}">
                <p class="error">${error}</p>
            </c:if>

            <!-- Form để tạo hóa đơn -->
            <form action="${pageContext.request.contextPath}/Invoices" method="POST" class="p-4">
                <div class="row">
                    <!-- Ngày lập hóa đơn -->
                    <div class="col-md-12 mb-3">
                        <div class="info-box">
                            <h6 class="text-danger"><i class="icon bi bi-calendar"></i> Thông tin hóa đơn</h6>
                            <div class="mb-3">
                                <label for="invoiceDate" class="form-label">Ngày lập hóa đơn</label>
                                <input type="date" class="form-control" id="invoiceDate" name="invoiceDate" value="${invoiceDate}" required>
                            </div>
                        </div>
                    </div>

                    <!-- Thông tin khách hàng -->
                    <div class="col-md-6 mb-3">
                        <div class="info-box">
                            <h6 class="text-danger"><i class="icon bi bi-person-circle"></i> Chọn khách hàng</h6>
                            <div class="mb-3">
                                <label for="customerID" class="form-label">Khách hàng</label>
                                <select class="form-select" id="customerID" name="customerID" onchange="this.form.submit()" required>
                                    <option value="">-- Chọn khách hàng --</option>
                                    <c:forEach var="customer" items="${customers}">
                                        <option value="${customer.custID}" ${customer.custID eq param.customerID ? 'selected' : ''}>
                                            ${customer.custName} (ID: ${customer.custID})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <!-- Khu vực hiển thị chi tiết khách hàng -->
                            <div class="detail-field">
                                <p><strong>Tên:</strong> 
                                    <c:out value="${selectedCustomer != null ? selectedCustomer.custName : ''}"/>
                                </p>
                                <p><strong>Giới tính:</strong> 
                                    <c:out value="${selectedCustomer != null ? (selectedCustomer.sex == 'M' ? 'Nam' : 'Nữ') : ''}"/>
                                </p>
                                <p><strong>Số điện thoại:</strong> 
                                    <c:out value="${selectedCustomer != null ? '0'.concat(selectedCustomer.phone) : ''}"/>
                                </p>
                                <p><strong>Địa chỉ:</strong> 
                                    <c:out value="${selectedCustomer != null ? selectedCustomer.custAddress : ''}"/>
                                </p>
                            </div>
                        </div>
                    </div>

                    <!-- Thông tin nhân viên bán hàng -->
                    <div class="col-md-6 mb-3">
                        <div class="info-box">
                            <h6 class="text-danger"><i class="icon bi bi-briefcase"></i> Chọn nhân viên bán hàng</h6>
                            <div class="mb-3">
                                <label for="salesPersonID" class="form-label">Nhân viên bán hàng</label>
                                <select class="form-select" id="salesPersonID" name="salesPersonID" onchange="this.form.submit()" required>
                                    <option value="">-- Chọn nhân viên --</option>
                                    <c:forEach var="salesPerson" items="${salesPersons}">
                                        <option value="${salesPerson.salesID}" ${salesPerson.salesID eq param.salesPersonID ? 'selected' : ''}>
                                            ${salesPerson.salesName} (ID: ${salesPerson.salesID})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <!-- Khu vực hiển thị chi tiết nhân viên -->
                            <div class="detail-field">
                                <p><strong>Tên:</strong> 
                                    <c:out value="${selectedSalesPerson != null ? selectedSalesPerson.salesName : ''}"/>
                                </p>
                                <p><strong>Giới tính:</strong> 
                                    <c:out value="${selectedSalesPerson != null ? (selectedSalesPerson.sex == 'M' ? 'Nam' : 'Nữ') : ''}"/>
                                </p>
                                <p><strong>Địa chỉ:</strong>
                                    <c:out value="${selectedSalesPerson != null ? selectedSalesPerson.salesAddress : ''}"/>
                                </p>
                            </div>
                        </div>
                    </div>

                    <!-- Thông tin xe -->
                    <div class="col-md-12 mb-3">
                        <div class="info-box">
                            <h6 class="text-danger"><i class="icon bi bi-car-front"></i> Chọn xe</h6>
                            <div class="mb-3">
                                <label for="carID" class="form-label">Xe</label>
                                <select class="form-select" id="carID" name="carID" onchange="this.form.submit()" required>
                                    <option value="">-- Chọn xe --</option>
                                    <c:forEach var="car" items="${cars}">
                                        <option value="${car.carID}" ${car.carID eq param.carID ? 'selected' : ''}>
                                            ${car.model} - ${car.year} (Màu: ${car.colour})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <!-- Khu vực hiển thị chi tiết xe -->
                            <div class="detail-field">
                                <p><strong>Model:</strong> 
                                    <c:out value="${selectedCar != null ? selectedCar.model : ''}"/>
                                </p>
                                <p><strong>Năm sản xuất:</strong> 
                                    <c:out value="${selectedCar != null ? selectedCar.year : ''}"/>
                                </p>
                                <p><strong>Màu sắc:</strong> 
                                    <c:out value="${selectedCar != null ? selectedCar.colour : ''}"/>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Nút hành động -->
                <div class="text-center">
                    <button type="submit" name="action" value="create" class="btn btn-success">Tạo hóa đơn</button>
                    <a href="${pageContext.request.contextPath}/Invoices" class="btn btn-secondary">Hủy</a>
                </div>
            </form>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

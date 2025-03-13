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

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">

        <style>
            .modal-header { background: #007bff; color: white; }
            .modal-body { background: #f8f9fa; }
            .info-box { background: white; padding: 15px; border-radius: 10px; box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1); }
            .info-box h6 { color: #007bff; }
            .icon { font-size: 20px; margin-right: 8px; color: #dc3545; }
            .form-label { font-weight: bold; }
            .detail-field { margin-top: 10px; padding: 10px; background: #f8f9fa; border-radius: 5px; }
            .error { color: red; text-align: center; }
        </style>
    </head>
    <body class="bg-light">

        <c:if test="${not empty messageCreated}">
            <div id="message" class="alert alert-${messageCreated.contains('successfully') ? 'success' : 'danger'} alert-dismissible fade show" role="alert">
                ${messageCreated}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <div class="container mt-5 p-4 bg-white rounded shadow">
            <a href="Controller?action=Dashboard" class="btn btn-danger mb-3">Trang chủ</a>
            <h1 class="text-center text-danger fw-bold">Tạo hóa đơn mới</h1>

            <c:if test="${not empty error}">
                <p class="error">${error}</p>
            </c:if>

            <form action="Controller" method="POST" class="p-4" id="invoiceForm">
                <div class="row">
                    <div class="col-md-12 mb-3">
                        <div class="info-box">
                            <h6 class="text-danger"><i class="icon bi bi-calendar"></i> Thông tin hóa đơn</h6>
                            <div class="mb-3">
                                <label for="invoiceDate" class="form-label">Ngày lập hóa đơn</label>
                                <input type="date" class="form-control" id="invoiceDate" name="invoiceDate" value="${invoiceDate}" required>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6 mb-3">
                        <div class="info-box">
                            <h6 class="text-danger"><i class="icon bi bi-person-circle"></i> Chọn khách hàng</h6>
                            <div class="mb-3">
                                <label for="customerID" class="form-label">Khách hàng</label>
                                <select class="form-select" id="customerID" name="customerID" required>
                                    <option value="">-- Chọn khách hàng --</option>
                                    <c:forEach var="customer" items="${customers}">
                                        <option value="${customer.custID}" ${customer.custID eq param.customerID ? 'selected' : ''}>
                                            ${customer.custName} (ID: ${customer.custID})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="detail-field" id="customerDetails">
                                <p><strong>Tên:</strong> <span id="custName"></span></p>
                                <p><strong>Giới tính:</strong> <span id="custSex"></span></p>
                                <p><strong>Số điện thoại:</strong> <span id="custPhone"></span></p>
                                <p><strong>Địa chỉ:</strong> <span id="custAddress"></span></p>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6 mb-3">
                        <div class="info-box">
                            <h6 class="text-danger"><i class="icon bi bi-briefcase"></i> Chọn nhân viên bán hàng</h6>
                            <div class="mb-3">
                                <label for="salesPersonID" class="form-label">Nhân viên bán hàng</label>
                                <select class="form-select" id="salesPersonID" name="salesPersonID" required>
                                    <option value="">-- Chọn nhân viên --</option>
                                    <c:forEach var="salesPerson" items="${salesPersons}">
                                        <option value="${salesPerson.salesID}" ${salesPerson.salesID eq param.salesPersonID ? 'selected' : ''}>
                                            ${salesPerson.salesName} (ID: ${salesPerson.salesID})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="detail-field" id="salesPersonDetails">
                                <p><strong>Tên:</strong> <span id="salesName"></span></p>
                                <p><strong>Giới tính:</strong> <span id="salesSex"></span></p>
                                <p><strong>Địa chỉ:</strong> <span id="salesAddress"></span></p>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-12 mb-3">
                        <div class="info-box">
                            <h6 class="text-danger"><i class="icon bi bi-car-front"></i> Chọn xe</h6>
                            <div class="mb-3">
                                <label for="carID" class="form-label">Xe</label>
                                <select class="form-select" id="carID" name="carID" required>
                                    <option value="">-- Chọn xe --</option>
                                    <c:forEach var="car" items="${cars}">
                                        <option value="${car.carID}" ${car.carID eq param.carID ? 'selected' : ''}>
                                            ${car.model} - ${car.year} (Màu: ${car.colour})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="detail-field" id="carDetails">
                                <p><strong>Model:</strong> <span id="carModel"></span></p>
                                <p><strong>Năm sản xuất:</strong> <span id="carYear"></span></p>
                                <p><strong>Màu sắc:</strong> <span id="carColour"></span></p>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="text-center">
                    <button type="submit" name="action" value="create" class="btn btn-success">Tạo hóa đơn</button>
                </div>
            </form>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            const customers = [
            <c:forEach var="customer" items="${customers}" varStatus="loop">
            {
            custID: "${customer.custID}",
                    custName: "${customer.custName}",
                    sex: "${customer.sex}",
                    phone: "0${customer.phone}",
                    custAddress: "${customer.custAddress}"
            }${loop.last ? '' : ','}
            </c:forEach>
            ];
            const salesPersons = [
            <c:forEach var="salesPerson" items="${salesPersons}" varStatus="loop">
            {
            salesID: "${salesPerson.salesID}",
                    salesName: "${salesPerson.salesName}",
                    sex: "${salesPerson.sex}",
                    salesAddress: "${salesPerson.salesAddress}"
            }${loop.last ? '' : ','}
            </c:forEach>
            ];
            const cars = [
            <c:forEach var="car" items="${cars}" varStatus="loop">
            {
            carID: "${car.carID}",
                    model: "${car.model}",
                    year: "${car.year}",
                    colour: "${car.colour}"
            }${loop.last ? '' : ','}
            </c:forEach>
            ];
            document.addEventListener('DOMContentLoaded', () => {
            document.getElementById('customerID').addEventListener('change', updateCustomerDetails);
            document.getElementById('salesPersonID').addEventListener('change', updateSalesPersonDetails);
            document.getElementById('carID').addEventListener('change', updateCarDetails);
            updateCustomerDetails();
            updateSalesPersonDetails();
            updateCarDetails();
            });
            function updateCustomerDetails() {
            const customerID = document.getElementById('customerID').value.toString();
            const customer = customers.find(c => c.custID.toString() === customerID);
            if (customer) {
            document.getElementById('custName').textContent = customer.custName;
            document.getElementById('custSex').textContent = customer.sex === 'M' ? 'Nam' : 'Nữ';
            document.getElementById('custPhone').textContent = customer.phone;
            document.getElementById('custAddress').textContent = customer.custAddress;
            } else {
            clearCustomerDetails();
            }
            }

            function updateSalesPersonDetails() {
            const salesPersonID = document.getElementById('salesPersonID').value.toString();
            const salesPerson = salesPersons.find(s => s.salesID.toString() === salesPersonID);
            if (salesPerson) {
            document.getElementById('salesName').textContent = salesPerson.salesName;
            document.getElementById('salesSex').textContent = salesPerson.sex === 'M' ? 'Nam' : 'Nữ';
            document.getElementById('salesAddress').textContent = salesPerson.salesAddress;
            } else {
            clearSalesPersonDetails();
            }
            }

            function updateCarDetails() {
            const carID = document.getElementById('carID').value.toString();
            const car = cars.find(c => c.carID.toString() === carID);
            if (car) {
            document.getElementById('carModel').textContent = car.model;
            document.getElementById('carYear').textContent = car.year;
            document.getElementById('carColour').textContent = car.colour;
            } else {
            clearCarDetails();
            }
            }

            function clearCustomerDetails() {
            document.getElementById('custName').textContent = '';
            document.getElementById('custSex').textContent = '';
            document.getElementById('custPhone').textContent = '';
            document.getElementById('custAddress').textContent = '';
            }

            function clearSalesPersonDetails() {
            document.getElementById('salesName').textContent = '';
            document.getElementById('salesSex').textContent = '';
            document.getElementById('salesAddress').textContent = '';
            }

            function clearCarDetails() {
            document.getElementById('carModel').textContent = '';
            document.getElementById('carYear').textContent = '';
            document.getElementById('carColour').textContent = '';
            }
        </script>
    </body>
</html>
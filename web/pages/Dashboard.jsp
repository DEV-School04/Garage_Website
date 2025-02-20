<%-- 
    Document   : Dashboard
    Created on : Feb 10, 2025, 10:05:41 AM
    Author     : nhutt
--%>

<%@page import="models.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>Dịch Vụ Ô Tô & Sửa Chữa Xe</title>
        
        <!-- Bootstrap & Icons -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" />

        <style>
            .avatar-btn {
                font-size: 32px;
                cursor: pointer;
                color: #fff;
                background: transparent;
                border: none;
                position: relative;
            }

            .avatar-btn:hover {
                color: #e9ecef;
            }

            .dropdown-menu .dropdown-item:hover {
                cursor: pointer;
            }
            .dropdown-menu .dropdown-item:active {
                background-color: #e9ecef !important;
            }
        </style>
    </head>

    <body>
        <%
            Object userObj = session.getAttribute("user");
            Customer cust = null;
            if (userObj instanceof Customer) {
                cust = (Customer) userObj;
            } else {
                request.getRequestDispatcher("Controller").forward(request, response);
                return;
            }
        %>

        <!-- Navigation Bar -->
        <nav class="navbar navbar-expand-lg navbar-dark bg-transparent fixed-top">
            <div class="container">
                <a class="navbar-brand d-flex align-items-center gap-2" href="#">
                    <i class="bi bi-car-front"></i>
                    <span>Dịch Vụ Ô Tô</span>
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item"><a class="nav-link active" href="#">Trang Chủ</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">Giới Thiệu</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">Dịch Vụ</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">Liên Hệ</a></li>
                    </ul>
                </div>
                <div class="dropdown">
                    <button class="avatar-btn dropdown-toggle" type="button" id="avatarDropdown" data-bs-toggle="dropdown">
                        <i class="bi bi-person-circle"></i>
                    </button>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <li><p class="dropdown-item text-danger" data-bs-toggle="modal" data-bs-target="#profileModal">Edit Profile</p></li>
                        <li><a class="dropdown-item text-danger" href="Controller?action=Logout">Logout</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <!-- Hero Section -->
        <header class="bg-danger text-white py-5 text-center d-flex align-items-center justify-content-center"
            style="background-image: linear-gradient(to bottom, rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.8)), url('./images/banner.jpg'); background-size: cover; background-position: center; height: 80vh;">
            <div class="container">
                <h1 class="display-4">Chào Mừng Đến Với Dịch Vụ Ô Tô</h1>
                <p class="lead">Đối tác đáng tin cậy của bạn cho sửa chữa và bảo dưỡng xe</p>
                <a href="#services" class="btn btn-light btn-lg">Khám Phá Dịch Vụ</a>
            </div>
        </header>

        <!-- Services Section -->
        <section id="services" class="py-5">
            <div class="container text-center">
                <h2 class="fw-bold">Dịch Vụ Của Chúng Tôi</h2>
                <p class="text-muted">Cung cấp các dịch vụ sửa chữa và bảo dưỡng xe chất lượng cao</p>
                <div class="row g-4">
                    <div class="col-lg-4 col-md-6">
                        <div class="card shadow text-center">
                            <div class="card-body">
                                <i class="bi bi-tools fs-1 text-danger"></i>
                                <h5 class="card-title mt-3">Sửa Chữa Tổng Quát</h5>
                                <p class="card-text">Dịch vụ sửa chữa toàn diện cho tất cả các dòng xe.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-6">
                        <div class="card shadow text-center">
                            <div class="card-body">
                                <i class="bi bi-gear-fill fs-1 text-danger"></i>
                                <h5 class="card-title mt-3">Chẩn Đoán Động Cơ</h5>
                                <p class="card-text">Chẩn đoán tiên tiến để phát hiện và sửa chữa vấn đề động cơ.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-6">
                        <div class="card shadow text-center">
                            <div class="card-body">
                                <i class="bi bi-battery-charging fs-1 text-danger"></i>
                                <h5 class="card-title mt-3">Dịch Vụ Bình Điện</h5>
                                <p class="card-text">Kiểm tra, thay thế và sạc bình điện.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- Footer -->
        <footer class="bg-dark text-white py-4 text-center">
            <p>&copy; 2025 Dịch Vụ Ô Tô. Bảo Lưu Mọi Quyền.</p>
        </footer>

        <!-- Profile Modal -->
        <div class="modal fade" id="profileModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header bg-danger text-white">
                        <h5 class="modal-title">Change Profile</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <form action="./ChangeProfileUser" method="POST">
                            <div class="mb-3">
                                <label for="id" class="form-label">ID</label>
                                <input type="text" class="form-control" id="id" name="id" value="<%= (cust != null) ? cust.getCustID() : ""%>" disabled>
                                <!-- Hidden field to ensure ID is sent -->
                                <input type="hidden" name="id" value="<%= (cust != null) ? cust.getCustID() : ""%>">
                            </div>
                            <div class="mb-3">
                                <label for="name" class="form-label">Name</label>
                                <input type="text" class="form-control" id="name" name="name" value="<%= (cust != null) ? cust.getCustName() : ""%>" required>
                            </div>
                            <div class="mb-3">
                                <label for="phone" class="form-label">Phone</label>
                                <input type="tel" class="form-control" id="phone" name="phone" value="<%= (cust != null) ? cust.getPhone() : ""%>" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Sex</label>
                                <div>
                                    <input type="radio" id="male" name="sex" value="M" <%= (cust != null && "M".equalsIgnoreCase(cust.getSex())) ? "checked" : ""%>>
                                    <label for="male">Male</label>

                                    <input type="radio" id="female" name="sex" value="F" <%= (cust != null && "F".equalsIgnoreCase(cust.getSex())) ? "checked" : ""%>>
                                    <label for="female">Female</label>
                                </div>
                            </div>
                            <div class="mb-3">
                                <label for="address" class="form-label">Address</label>
                                <input type="text" class="form-control" id="address" name="address" value="<%= (cust != null) ? cust.getCustAddress() : ""%>" required>
                            </div>
                            <div class="d-grid">
                                <button type="submit" class="btn btn-danger" name="action" value="ChangeProfileUser">Save Changes</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                let message = "<%= request.getAttribute("messageUpdated") != null ? request.getAttribute("messageUpdated") : "" %>";
                if (message.trim() !== "") alert(message);
            });
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

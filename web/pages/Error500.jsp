<%-- 
    Document   : Error500
    Created on : Feb 20, 2025, 7:03:58 AM
    Author     : nhutt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <title>Lỗi 500 - Lỗi Máy Chủ</title>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            />
        <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
            />
    </head>
    <body>
        <div class="container d-flex flex-column min-vh-100">
            <!-- Main content -->
            <div class="row flex-grow-1 justify-content-center align-items-center">
                <div class="col-lg-10">
                    <div class="row justify-content-center align-items-center">
                        <!-- Text content -->
                        <div class="col-md-6 text-center text-md-start">
                            <div class="mb-5">
                                <a
                                    href="index.jsp"
                                    class="d-flex align-items-center gap-2 text-decoration-none"
                                    >
                                    <i class="bi bi-car-front fs-3 text-black"></i>
                                    <div>
                                        <h5 class="m-0 fw-bold text-dark">Dịch vụ ô tô</h5>
                                    </div>
                                </a>
                            </div>
                            <span
                                class="badge bg-danger bg-opacity-25 text-danger px-3 py-2 rounded-pill mb-3"
                                >Lỗi Hệ Thống :(</span
                            >
                            <h1 class="display-4 fw-bold mb-3">500 Lỗi Máy Chủ</h1>
                            <p class="lead text-muted mb-4">
                                Xin lỗi, đã xảy ra lỗi với máy chủ. Vui lòng thử lại sau hoặc liên hệ quản trị viên.
                            </p>
                            <a href="index.jsp" class="btn btn-outline-danger"
                               >Quay lại trang chủ</a
                            >
                        </div>
                        <!-- Image -->
                        <div class="col-md-6">
                            <img
                                src="images/error500.jpg"
                                alt="Error 500 Image"
                                class="img-fluid"
                                />
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>


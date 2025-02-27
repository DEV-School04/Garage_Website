<%-- 
    Document   : Role
    Created on : Feb 25, 2025, 9:29:09 AM
    Author     : nhutt
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Đăng Nhập</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
        <style>
            body {
                background-image: url('./images/banner.jpg');
                background-size: cover;
                background-position: center;
                height: 100vh;
                display: flex;
                align-items: center;
                justify-content: center;
                backdrop-filter: blur(5px);
            }
            .login-container {
                background: rgba(255, 255, 255, 0.85);
                padding: 40px;
                border-radius: 12px;
                box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
                text-align: center;
                max-width: 400px;
            }
            .role-select {
                width: 100%;
                padding: 10px;
                border-radius: 8px;
                border: 1px solid #ccc;
                cursor: pointer;
            }
            .btn-login {
                margin-top: 15px;
                width: 100%;
                padding: 10px;
                font-size: 18px;
                border-radius: 8px;
                transition: 0.3s ease-in-out;
            }
            .btn-login:hover {
                background: #c82333;
                color: #fff;
            }
        </style>
    </head>
    <body>
        <div class="login-container">
            <h2 class="mb-3">Chào Mừng Đến Với Dịch Vụ Ô Tô</h2>
            <p class="text-muted">Vui lòng chọn vai trò của bạn để tiếp tục</p>
            <form action="Controller">
                <div class="mb-3">
                    <select class="form-select role-select" name="role">
                        <option value="customer" selected>&#128100; Khách Hàng</option>
                        <option value="sale">&#128179; Nhân Viên Bán Hàng</option>
                        <option value="mechanics">&#128295; Thợ Máy</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-danger btn-login">
                    Tiếp Tục <i class="bi bi-arrow-right"></i>
                </button>
            </form>
        </div>
    </body>
</html>

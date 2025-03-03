<%-- 
    Document   : Report
    Created on : Feb 26, 2025, 9:00:57 PM
    Author     : nhutt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Báo cáo Thống kê Ngành Xe hơi</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            body {
                padding: 20px;
                background-color: #f8f9fa;
            }
            .section {
                margin-bottom: 20px;
                padding: 20px;
                background-color: white;
                border-radius: 8px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }
            canvas {
                max-height: 300px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1 class="text-center mb-4">Báo cáo Thống kê Ngành Xe hơi (26/02/2025)</h1>

            <!-- Thống kê xe bán ra theo năm -->
            <div class="section">
                <h2>Thống kê xe bán ra theo năm</h2>
                <table class="table table-striped">
                    <thead class="table-dark">
                        <tr>
                            <th>Năm</th>
                            <th>Số xe bán ra</th>
                            <th>Danh sách xe</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${carsByYear}" var="entry">
                            <tr>
                                <td>${entry.key}</td>
                                <td>${entry.value.size()}</td>
                                <td>
                                    <ul>
                                        <c:forEach items="${entry.value}" var="car">
                                            <li>${car.model}</li>
                                        </c:forEach>
                                    </ul>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <canvas id="carsSoldByYearChart"></canvas>
                <button class="btn btn-primary mt-2">Tải dữ liệu chi tiết</button>
            </div>

            <!-- Các section khác giữ nguyên -->
            <div class="section">
                <h2>Mẫu xe bán chạy nhất</h2>
                <ul class="list-group mb-3" id="topSellingModelsList"></ul>
                <canvas id="topModelsChart"></canvas>
            </div>

            <div class="section">
                <h2>Thống kê linh kiện đã qua sử dụng phổ biến nhất</h2>
                <table class="table table-striped" id="usedPartsTable">
                    <thead class="table-dark">
                        <tr>
                            <th>Linh kiện</th>
                            <th>Số lượng sử dụng</th>
                            <th>Tỷ lệ (%)</th>
                        </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>

            <div class="section">
                <h2>Top 3 thợ cơ khí thực hiện nhiều sửa chữa nhất</h2>
                <ul class="list-group mb-3" id="topMechanicsList"></ul>
                <canvas id="topMechanicsChart"></canvas>
            </div>

            <div class="section text-center">
                <button class="btn btn-success">Xuất báo cáo (PDF/Excel)</button>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            // Chuẩn bị dữ liệu cho biểu đồ từ JSTL
            const carsSoldByYearData = {
                labels: [
                    <c:forEach items="${carsByYear}" var="entry" varStatus="loop">
                        "${entry.key}"${loop.last ? '' : ','}
                    </c:forEach>
                ],
                data: [
                    <c:forEach items="${carsByYear}" var="entry" varStatus="loop">
                        ${entry.value.size()}${loop.last ? '' : ','}
                    </c:forEach>
                ]
            };

            // Biểu đồ đường: Xe bán ra theo năm
            const carsSoldByYearCtx = document.getElementById('carsSoldByYearChart').getContext('2d');
            new Chart(carsSoldByYearCtx, {
                type: 'line',
                data: {
                    labels: carsSoldByYearData.labels,
                    datasets: [{
                        label: 'Số xe bán ra',
                        data: carsSoldByYearData.data,
                        borderColor: '#007bff',
                        fill: false
                    }]
                },
                options: {scales: {y: {beginAtZero: true}}}
            });

            // Các phần JavaScript khác giữ nguyên
            const reportData = {
                topSellingModels: [
                    {modelName: "chevrolet", salesCount: 3},
                    {modelName: "Escape", salesCount: 3},
                    {modelName: "TLX", salesCount: 2}
                ],
                usedParts: [
                    {partName: "Bộ lọc không khí", usageCount: 3, percentage: 18.75},
                    {partName: "Kính chắn gió", usageCount: 2, percentage: 12.5}
                ],
                topMechanics: [
                    {name: "Cao Xuân Trường", repairCount: 4},
                    {name: "Nguyễn Nam Ninh", repairCount: 3}
                ]
            };

            // ... (phần JavaScript còn lại giữ nguyên)
        </script>
    </body>
</html>
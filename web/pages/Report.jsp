<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Báo cáo thống kê</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            body {
                background: #f5f6fa;
                font-family: 'Poppins', sans-serif;
                color: #2c3e50;
                margin: 0;
                padding: 0;
            }
            .dashboard {
                max-width: 1400px;
                margin: 30px auto;
                padding: 20px;
            }
            .dashboard-header {
                background: #ffffff;
                padding: 20px;
                text-align: center;
                border-radius: 12px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
                margin-bottom: 30px;
            }
            .dashboard-header h1 {
                font-size: 2rem;
                margin: 0;
                color: #34495e;
            }
            .dashboard-content {
                display: grid;
                grid-template-columns: repeat(2, 1fr);
                gap: 20px;
            }
            .widget {
                background: #ffffff;
                border-radius: 12px;
                padding: 20px;
                box-shadow: 0 2px 15px rgba(0, 0, 0, 0.05);
                transition: box-shadow 0.3s ease;
            }
            .widget:hover {
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            }
            .widget-header {
                font-size: 1.2rem;
                font-weight: 600;
                color: #34495e;
                padding-bottom: 10px;
                border-bottom: 1px solid #ecf0f1;
                display: flex;
                align-items: center;
                gap: 10px;
            }
            .widget-body {
                padding-top: 15px;
            }
            canvas {
                max-height: 300px;
                width: 100%;
            }
            .alert {
                background: #e74c3c;
                color: white;
                border-radius: 8px;
                padding: 10px;
                text-align: center;
                margin-bottom: 20px;
            }
            .full-width {
                grid-column: span 2;
            }
            .half-width {
                grid-column: span 1;
            }
            @media (max-width: 992px) {
                .dashboard-content {
                    grid-template-columns: 1fr;
                }
                .full-width, .half-width {
                    grid-column: span 1;
                }
            }
        </style>
    </head>
    <body>
        <div class="dashboard">
            <div class="dashboard-header">
                <h1><i class="fas fa-chart-bar"></i> Báo cáo thống kê</h1>
            </div>
            <c:if test="${requestScope.Error}">
                <p class="text-danger">${requestScope.Error}</p>
                <c:remove scope="request" var="Error" />
            </c:if>
            <div class="dashboard-content">

                <div class="widget full-width">
                    <div class="widget-header"><i class="fas fa-car-side"></i> Xe bán ra theo năm</div>
                    <div class="widget-body">
                        <canvas id="carsSoldByYearChart"></canvas>
                    </div>
                </div>


                <div class="widget full-width">
                    <div class="widget-header"><i class="fas fa-wallet"></i> Doanh thu bán xe theo năm</div>
                    <div class="widget-body">
                        <canvas id="revenueByYearChart"></canvas>
                    </div>
                </div>


                <div class="widget half-width">
                    <div class="widget-header"><i class="fas fa-trophy"></i> Mẫu xe bán chạy nhất</div>
                    <div class="widget-body">
                        <canvas id="topModelsChart"></canvas>
                    </div>
                </div>


                <div class="widget half-width">
                    <div class="widget-header"><i class="fas fa-user-cog"></i> Top thợ cơ khí</div>
                    <div class="widget-body">
                        <canvas id="topMechanicsChart"></canvas>
                    </div>
                </div>


                <div class="widget full-width">
                    <div class="widget-header"><i class="fas fa-tools"></i> Số linh kiện được sử dụng</div>
                    <div class="widget-body">
                        <canvas id="partsUsedChart"></canvas>
                    </div>
                </div>
            </div>
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function () {

                const carsByYearLabels = [<c:forEach items="${carsByYear}" var="entry" varStatus="loop">"${entry.key}"${loop.last ? '' : ','}</c:forEach>];
                const carsByYearData = [<c:forEach items="${carsByYear}" var="entry" varStatus="loop">${entry.value}${loop.last ? '' : ','}</c:forEach>];


                const revenueByYearLabels = [<c:forEach items="${revenueByYear}" var="entry" varStatus="loop">"${entry.key}"${loop.last ? '' : ','}</c:forEach>];
                const revenueByYearData = [<c:forEach items="${revenueByYear}" var="entry" varStatus="loop">${entry.value}${loop.last ? '' : ','}</c:forEach>];


                const topModelsLabels = [<c:forEach items="${topModels}" var="model" varStatus="loop">"${model.key}"${loop.last ? '' : ','}</c:forEach>];
                const topModelsData = [<c:forEach items="${topModels}" var="model" varStatus="loop">${model.value}${loop.last ? '' : ','}</c:forEach>];


                const topMechanicsLabels = [<c:forEach items="${topMechanics}" var="mechanic" varStatus="loop">"${mechanic.key}"${loop.last ? '' : ','}</c:forEach>];
                const topMechanicsData = [<c:forEach items="${topMechanics}" var="mechanic" varStatus="loop">${mechanic.value}${loop.last ? '' : ','}</c:forEach>];


                const partsUsedLabels = [<c:forEach items="${partsUsedCount}" var="part" varStatus="loop">"${part.key}"${loop.last ? '' : ','}</c:forEach>];
                const partsUsedData = [<c:forEach items="${partsUsedCount}" var="part" varStatus="loop">${part.value}${loop.last ? '' : ','}</c:forEach>];

                new Chart(document.getElementById("carsSoldByYearChart"), {
                    type: "line",
                    data: {labels: carsByYearLabels, datasets: [{label: "Số xe bán ra", data: carsByYearData, borderColor: "#3498db", backgroundColor: "rgba(52, 152, 219, 0.2)", fill: true}]},
                    options: {responsive: true, plugins: {legend: {position: 'top', labels: {color: '#34495e'}}}, scales: {x: {ticks: {color: '#34495e'}}, y: {ticks: {color: '#34495e'}}}}
                });

                new Chart(document.getElementById("revenueByYearChart"), {
                    type: "bar",
                    data: {labels: revenueByYearLabels, datasets: [{label: "Doanh thu (VNĐ)", data: revenueByYearData, backgroundColor: "#2ecc71"}]},
                    options: {responsive: true, plugins: {legend: {position: 'top', labels: {color: '#34495e'}}}, scales: {x: {ticks: {color: '#34495e'}}, y: {ticks: {color: '#34495e'}}}}
                });

                new Chart(document.getElementById("topModelsChart"), {
                    type: "pie",
                    data: {labels: topModelsLabels, datasets: [{data: topModelsData, backgroundColor: ["#3498db", "#2ecc71", "#f1c40f", "#e74c3c", "#9b59b6"]}]},
                    options: {responsive: true, plugins: {legend: {position: 'right', labels: {color: '#34495e'}}}}
                });

                new Chart(document.getElementById("topMechanicsChart"), {
                    type: "bar",
                    data: {labels: topMechanicsLabels, datasets: [{label: "Số lần sửa chữa", data: topMechanicsData, backgroundColor: "#e74c3c"}]},
                    options: {responsive: true, plugins: {legend: {position: 'top', labels: {color: '#34495e'}}}, scales: {x: {ticks: {color: '#34495e'}}, y: {ticks: {color: '#34495e'}}}}
                });

                new Chart(document.getElementById("partsUsedChart"), {
                    type: "bar",
                    data: {labels: partsUsedLabels, datasets: [{label: "Số lượng sử dụng", data: partsUsedData, backgroundColor: "#f1c40f"}]},
                    options: {responsive: true, plugins: {legend: {position: 'top', labels: {color: '#34495e'}}}, scales: {x: {ticks: {color: '#34495e'}}, y: {ticks: {color: '#34495e'}}}}
                });
            });
        </script>
    </body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="container mt-4">
    <h4 class="mb-4">📊 Dashboard Cửa hàng</h4>

    <!-- 1. Thống kê số liệu tổng quan -->
    <div class="row g-3 mb-4">
        <div class="col-md-3">
            <div class="card text-center shadow-sm">
                <div class="card-body">
                    <i class="bi bi-people-fill fs-2 mb-2 text-primary"></i>
                    <h6 class="card-title">Khách hàng</h6>
                    <p class="fw-bold mb-0">${totalCustomers}</p>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card text-center shadow-sm">
                <div class="card-body">
                    <i class="bi bi-box-seam fs-2 mb-2 text-success"></i>
                    <h6 class="card-title">Sản phẩm</h6>
                    <p class="fw-bold mb-0">${totalProducts}</p>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card text-center shadow-sm">
                <div class="card-body">
                    <i class="bi bi-cart-check-fill fs-2 mb-2 text-warning"></i>
                    <h6 class="card-title">Đơn hàng</h6>
                    <p class="fw-bold mb-0">${totalOrders}</p>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card text-center shadow-sm">
                <div class="card-body">
                    <i class="bi bi-cash-stack fs-2 mb-2 text-danger"></i>
                    <h6 class="card-title">Doanh thu</h6>
                    <p class="fw-bold mb-0">₫<fmt:formatNumber value="${totalRevenue}" type="currency" currencySymbol=""/></p>
                </div>
            </div>
        </div>
    </div>

    <!-- 2. Biểu đồ phân tích -->
    <h5 class="mb-3">Phân tích kinh doanh</h5>
    <div class="row g-3 mb-4">
        <div class="col-md-6">
            <div class="card shadow-sm h-100">
                <div class="card-body">
                    <h6 class="card-title">Doanh thu theo tháng</h6>
                    <canvas id="revenueChart"></canvas>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card shadow-sm h-100">
                <div class="card-body">
                    <h6 class="card-title">Xu hướng đơn hàng</h6>
                    <canvas id="orderTrendChart"></canvas>
                </div>
            </div>
        </div>
    </div>

    <div class="row g-3 mb-4">
        <div class="col-md-6">
            <div class="card shadow-sm h-100">
                <div class="card-body">
                    <h6 class="card-title">Tỷ lệ trạng thái đơn hàng</h6>
                    <canvas id="orderStatusChart"></canvas>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card shadow-sm h-100">
                <div class="card-body">
                    <h6 class="card-title">Tỷ lệ danh mục sản phẩm</h6>
                    <canvas id="categoryChart"></canvas>
                </div>
            </div>
        </div>
    </div>

    <!-- 3. Báo cáo chi tiết -->
    <h5 class="mb-3">Báo cáo chi tiết</h5>
    <div class="row g-3 mb-4">
        <!-- Báo cáo doanh thu -->
        <div class="col-md-12">
            <div class="card shadow-sm h-100">
                <div class="card-body">
                    <h6 class="card-title">Doanh thu theo tháng</h6>
                    <table class="table table-sm table-bordered text-center mt-3">
                        <thead class="table-light">
                            <tr>
                                <th>Tháng</th>
                                <th>Tổng đơn</th>
                                <th>Doanh thu</th>
                                <th>Lợi nhuận</th>
                                <th>Hủy (%)</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr><td>01</td><td>120</td><td>₫12,000,000</td><td>₫3,000,000</td><td>3%</td></tr>
                            <tr><td>02</td><td>98</td><td>₫8,500,000</td><td>₫2,200,000</td><td>5%</td></tr>
                            <tr><td>03</td><td>160</td><td>₫14,500,000</td><td>₫3,600,000</td><td>2%</td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- Báo cáo top sản phẩm -->
        <div class="col-md-12">
            <div class="card shadow-sm h-100 mt-3">
                <div class="card-body">
                    <h6 class="card-title">Top sản phẩm bán chạy</h6>
                    <table class="table table-sm table-bordered text-center mt-3">
                        <thead class="table-light">
                            <tr>
                                <th>Sản phẩm</th>
                                <th>Số lượng bán</th>
                                <th>Doanh thu</th>
                                <th>Đánh giá TB</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr><td>Tai nghe Bluetooth</td><td>120</td><td>₫1,200,000</td><td>4.7</td></tr>
                            <tr><td>Giày Sneaker</td><td>95</td><td>₫650,000</td><td>4.5</td></tr>
                            <tr><td>Áo thun</td><td>80</td><td>₫320,000</td><td>4.3</td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Thư viện Chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    // Doanh thu (Bar)
    new Chart(document.getElementById('revenueChart'), {
        type: 'bar',
        data: {
            labels: ['Th1','Th2','Th3','Th4','Th5'],
            datasets: [{
                label: 'Doanh thu (VNĐ)',
                data: [12000000, 8500000, 14500000, 10000000, 17000000],
                backgroundColor: 'rgba(54,162,235,0.7)'
            }]
        },
        options: { responsive: true, scales: { y: { beginAtZero: true } } }
    });

    // Xu hướng đơn hàng (Line)
    new Chart(document.getElementById('orderTrendChart'), {
        type: 'line',
        data: {
            labels: ['01/09','05/09','10/09','15/09','20/09','25/09'],
            datasets: [{
                label: 'Đơn hàng',
                data: [12,19,14,25,30,22],
                borderColor: '#ff6384',
                backgroundColor: 'rgba(255,99,132,0.2)',
                fill: true,
                tension: 0.4
            }]
        },
        options: { responsive: true }
    });

    // Trạng thái đơn hàng (Pie)
    new Chart(document.getElementById('orderStatusChart'), {
        type: 'pie',
        data: {
            labels: ['Đã giao','Đang giao','Hủy'],
            datasets: [{
                data: [450,180,60],
                backgroundColor: ['#28a745','#ffc107','#dc3545']
            }]
        },
        options: { responsive: true, plugins: { legend: { position: 'bottom' } } }
    });

    // Danh mục sản phẩm (Doughnut)
    new Chart(document.getElementById('categoryChart'), {
        type: 'doughnut',
        data: {
            labels: ['Điện tử','Thời trang','Thực phẩm','Khác'],
            datasets: [{
                data: [35,25,20,20],
                backgroundColor: ['#36a2eb','#ff6384','#ffcd56','#4bc0c0']
            }]
        },
        options: { responsive: true, plugins: { legend: { position: 'right' } } }
    });
</script>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Thống kê doanh thu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body class="bg-light">
<div class="container py-4">
    <h3 class="text-primary mb-4"><i class="bi bi-bar-chart"></i> Quản lý doanh thu sàn thương mại</h3>

    <!-- 🧭 Thanh điều khiển phần trăm chiết khấu -->
    <form method="get" action="${pageContext.request.contextPath}/admin/revenue" class="mb-4">
        <div class="card shadow-sm">
            <div class="card-body">
                <label for="feeRange" class="form-label fw-bold text-secondary">
                    Tỷ lệ chiết khấu (%)
                </label>
                <div class="d-flex align-items-center">
                    <input type="range" class="form-range me-3" id="feeRange" name="fee"
                           min="0.01" max="0.30" step="0.01"
                           value="${feeRate / 100.0}" oninput="feeValue.value = (this.value * 100).toFixed(0) + '%'">
                    <output id="feeValue" class="fw-bold text-primary">${feeRate}%</output>
                </div>
                <button class="btn btn-primary mt-2">Cập nhật</button>
            </div>
        </div>
    </form>

    <!-- 🧾 Thông tin tổng hợp -->
    <div class="row text-center mb-4">
        <div class="col-md-6">
            <div class="card border-success shadow-sm">
                <div class="card-body">
                    <h6 class="text-secondary">Doanh thu sau chiết khấu</h6>
                    <h4 class="fw-bold text-success">
                        <fmt:formatNumber value="${totalRevenue}" type="currency" currencySymbol="₫"/>
                    </h4>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card border-danger shadow-sm">
                <div class="card-body">
                    <h6 class="text-secondary">Phí sàn thương mại (${feeRate}%)</h6>
                    <h4 class="fw-bold text-danger">
                        <fmt:formatNumber value="${platformFee}" type="currency" currencySymbol="₫"/>
                    </h4>
                </div>
            </div>
        </div>
    </div>

    <!-- 📊 Biểu đồ doanh thu -->
    <div class="card shadow-sm mb-4">
        <div class="card-body">
            <h5 class="text-primary mb-3">Doanh thu theo tháng</h5>
            <canvas id="revenueChart" height="90"></canvas>
        </div>
    </div>

    <!-- 🥧 Biểu đồ phí -->
    <div class="card shadow-sm mb-4">
        <div class="card-body">
            <h5 class="text-primary mb-3">Tỷ lệ doanh thu & phí sàn</h5>
            <canvas id="feeChart" height="100"></canvas>
        </div>
    </div>

    <!-- 🧠 Phân tích -->
    <div class="card shadow-sm">
        <div class="card-body">
            <h5 class="text-primary mb-3">Phân tích doanh thu</h5>
            <p class="text-muted">${analysis}</p>
        </div>
    </div>
</div>

<!-- Chart.js -->
<script>
    const ctx1 = document.getElementById('revenueChart').getContext('2d');
    new Chart(ctx1, {
        type: 'bar',
        data: {
            labels: [${months}],
            datasets: [{
                label: 'Doanh thu (₫)',
                data: [${revenues}],
                backgroundColor: 'rgba(54, 162, 235, 0.6)',
                borderColor: 'rgb(54, 162, 235)',
                borderWidth: 1
            }]
        },
        options: { scales: { y: { beginAtZero: true } } }
    });

    const ctx2 = document.getElementById('feeChart').getContext('2d');
    new Chart(ctx2, {
        type: 'pie',
        data: {
            labels: ['Doanh thu sau phí', 'Phí sàn'],
            datasets: [{
                data: [${totalRevenue}, ${platformFee}],
                backgroundColor: ['#28a745', '#dc3545']
            }]
        }
    });
</script>

<form method="get" action="${pageContext.request.contextPath}/admin/revenue/export" class="mt-3">
    <input type="hidden" name="fee" value="${feeRate / 100.0}" />
    <button class="btn btn-danger">
        <i class="bi bi-file-earmark-pdf"></i> Xuất báo cáo PDF
    </button>
</form>

</body>
</html>

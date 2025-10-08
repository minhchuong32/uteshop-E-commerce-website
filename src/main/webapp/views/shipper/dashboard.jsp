<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h4 class="mb-3">📦 Hiệu suất giao hàng</h4>

<div class="row g-3 mb-4 text-center">
    <div class="col-md-3">
        <div class="card shadow-sm">
            <div class="card-body">
                <i class="bi bi-truck text-primary fs-2"></i>
                <h6>Đơn đã nhận</h6>
                <p class="fw-bold mb-0">${assignedCount}</p>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card shadow-sm">
            <div class="card-body">
                <i class="bi bi-clock text-warning fs-2"></i>
                <h6>Đang giao</h6>
                <p class="fw-bold mb-0">${deliveringCount}</p>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card shadow-sm">
            <div class="card-body">
                <i class="bi bi-check-circle text-success fs-2"></i>
                <h6>Đã giao</h6>
                <p class="fw-bold mb-0">${deliveredCount}</p>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card shadow-sm">
            <div class="card-body">
                <i class="bi bi-x-circle text-danger fs-2"></i>
                <h6>Hủy / Trả</h6>
                <p class="fw-bold mb-0">${canceledCount}</p>
            </div>
        </div>
    </div>
</div>

<!-- Hai biểu đồ song song -->
<div class="row g-3 mb-4">
    <div class="col-md-6">
        <div class="card shadow-sm h-100">
            <div class="card-body">
                <h6 class="card-title">Tỷ lệ giao thành công theo tháng</h6>
                <canvas id="successRateChart"></canvas>
            </div>
        </div>
    </div>

    <div class="col-md-6">
        <div class="card shadow-sm h-100">
            <div class="card-body">
                <h6 class="card-title">Tỷ lệ giao hàng thành công tổng thể</h6>
                <canvas id="successPieChart"></canvas>
            </div>
        </div>
    </div>
</div>

<!-- Chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
    // ===== Line Chart: Success Rate By Month =====
    const labels = [<c:forEach var="r" items="${successRateByMonth}" varStatus="loop">'Tháng ${r[0]}'<c:if test="${!loop.last}">,</c:if></c:forEach>];
    const successRates = [<c:forEach var="r" items="${successRateByMonth}" varStatus="loop">${(r[1]*100)/r[2]}<c:if test="${!loop.last}">,</c:if></c:forEach>];

    new Chart(document.getElementById('successRateChart'), {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Tỷ lệ giao thành công (%)',
                data: successRates,
                borderColor: '#28a745',
                backgroundColor: 'rgba(40,167,69,0.2)',
                tension: 0.4,
                fill: true
            }]
        },
        options: {
            scales: { y: { beginAtZero: true, max: 100 } },
            plugins: { legend: { position: 'bottom' } }
        }
    });

    // ===== Pie Chart: Overall Success vs Remaining =====
    document.addEventListener("DOMContentLoaded", function() {
        const ctx = document.getElementById('successPieChart');
        if (ctx) {
            // Lấy dữ liệu từ server, làm tròn tại đây (JS)
            const successRate = parseFloat("${successRate}");
            const failedRate = parseFloat("${failedRate}");

            new Chart(ctx, {
                type: 'pie',
                data: {
                    labels: ['Đã giao', 'Còn lại'],
                    datasets: [{
                        data: [successRate.toFixed(2), failedRate.toFixed(2)],
                        backgroundColor: ['#4CAF50', '#f44336']
                    }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: { position: 'bottom' },
                        tooltip: {
                            callbacks: {
                                label: function(context) {
                                    return context.label + ': ' + context.parsed.toFixed(2) + '%';
                                }
                            }
                        }
                    }
                }
            });
        }
    });
</script>

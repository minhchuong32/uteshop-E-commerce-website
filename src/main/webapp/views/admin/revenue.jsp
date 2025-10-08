<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<title>Thống kê doanh thu</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body class="bg-light">
	<div class="container py-4">
		<h3 class="text-primary-custom-custom mb-4">
			<i class="bi bi-bar-chart"></i> Quản lý doanh thu sàn thương mại
		</h3>
		<!-- 🔹 Bộ lọc doanh thu -->
		<form method="get"
			action="${pageContext.request.contextPath}/admin/revenue"
			class="row g-3 mb-4" onsubmit="return validateDateRange();">

			<div class="col-md-3">
				<label class="form-label fw-bold text-primary-custom-custom">Từ
					ngày</label> <input type="date" class="form-control" id="startDate"
					name="startDate" value="${startDate}">
			</div>

			<div class="col-md-3">
				<label class="form-label fw-bold text-primary-custom-custom">Đến
					ngày</label> <input type="date" class="form-control" id="endDate"
					name="endDate" value="${endDate}">
			</div>

			<div class="col-md-4">
				<label class="form-label fw-bold text-primary-custom-custom">Shop</label>
				<select class="form-select" name="shopId">
					<option value="all">Tất cả</option>
					<c:forEach var="s" items="${shops}">
						<option value="${s.shopId}"
							${selectedShopId == s.shopId ? "selected" : ""}>
							${s.name}</option>
					</c:forEach>
				</select>
			</div>

			<div class="col-md-2 d-flex align-items-end">
				<button class="btn btn-primary-custom w-100">Lọc</button>
			</div>
		</form>

		<!-- 🔸 Thông báo lỗi (ẩn mặc định) -->
		<div id="alertBox"
			class="alert alert-warning alert-dismissible fade show d-none"
			role="alert">
			⚠️ Vui lòng chọn đầy đủ <strong>Từ ngày</strong> và <strong>Đến
				ngày</strong> trước khi lọc.
			<button type="button" class="btn-close" data-bs-dismiss="alert"
				aria-label="Close"></button>
		</div>


		<!--  Thanh điều chỉnh chiết khấu -->
		<form method="get"
			action="${pageContext.request.contextPath}/admin/revenue"
			class="mb-4">
			<div class="card shadow-sm">
				<div class="card-body">
					<label for="feeRange"
						class="form-label fw-bold text-primary-custom-custom">Tỷ
						lệ chiết khấu (%)</label>
					<div class="d-flex align-items-center">
						<input type="range" class="form-range me-3" id="feeRange"
							name="fee" min="0.01" max="0.30" step="0.01"
							value="${feeRate / 100.0}"
							oninput="feeValue.value = (this.value * 100).toFixed(0) + '%'">
						<output id="feeValue" class="fw-bold text-primary-custom">${feeRate}%</output>
					</div>
					<button class="btn btn-primary-custom mt-2">Cập nhật</button>
				</div>
			</div>
		</form>

		<!--  Tổng hợp -->
		<div class="row text-center mb-4">
			<div class="col-md-6">
				<div class="card border-success shadow-sm">
					<div class="card-body">
						<h6 class="text-primary-custom">Doanh thu sau chiết khấu</h6>
						<h4 class="fw-bold text-success">
							<fmt:formatNumber value="${totalRevenue}" type="currency"
								currencySymbol="₫" />
						</h4>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="card border-danger shadow-sm">
					<div class="card-body">
						<h6 class="text-primary-custom">Phí sàn thương mại
							(${feeRate}%)</h6>
						<h4 class="fw-bold text-danger">
							<fmt:formatNumber value="${platformFee}" type="currency"
								currencySymbol="₫" />
						</h4>
					</div>
				</div>
			</div>
		</div>

		<!--  Biểu đồ doanh thu -->
		<div class="card shadow-sm mb-4">
			<div class="card-body">
				<h5 class="text-primary-custom mb-3">Doanh thu theo tháng</h5>
				<canvas id="revenueChart" height="90"></canvas>
			</div>
		</div>

		<!-- === Biểu đồ phí + Phân tích nâng cao (song song) === -->
		<div class="row g-4 mb-4">
			<!-- Biểu đồ phí -->
			<div class="col-md-6">
				<div class="card shadow-sm h-100">
					<div class="card-body">
						<h5 class="text-primary-custom mb-3">Tỷ lệ doanh thu & phí
							sàn</h5>
						<canvas id="feeChart" height="160"></canvas>
					</div>
				</div>
			</div>

			<!-- Phân tích nâng cao -->
			<div class="col-md-6">
				<div class="card shadow-sm h-100">
					<div class="card-body">
						<h5 class="text-primary-custom mb-3">
							<i class="bi bi-graph-up-arrow"></i> Phân tích nâng cao
						</h5>
						<p class="text-muted">${advancedAnalysis}</p>
						<canvas id="growthChart" height="160"></canvas>
					</div>
				</div>
			</div>
		</div>



		<!--  Phân tích -->
		<div class="card shadow-sm">
			<div class="card-body">
				<h5 class="text-primary-custom mb-3">Phân tích doanh thu</h5>
				<p class="text-muted">${analysis}</p>
			</div>
		</div>
	</div>

	<!--  Chart.js -->
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
    options: {
        responsive: true,
        scales: {
            y: {
                beginAtZero: true,
                title: { display: true, text: 'VNĐ', font: { weight: 'bold' } },
                ticks: { callback: value => value.toLocaleString('vi-VN') + ' ₫' }
            },
            x: { title: { display: true, text: 'Tháng', font: { weight: 'bold' } } }
        },
        plugins: {
            legend: { display: false },
            tooltip: {
                callbacks: {
                    label: ctx => ctx.dataset.label + ': ' + ctx.parsed.y.toLocaleString('vi-VN') + ' ₫'
                }
            }
        }
    }
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


function validateDateRange() {
    const start = document.getElementById("startDate").value;
    const end = document.getElementById("endDate").value;
    const alertBox = document.getElementById("alertBox");

    // Nếu chưa chọn ngày → hiển thị cảnh báo
    if (!start || !end) {
        alertBox.classList.remove("d-none"); // hiện alert
        window.scrollTo({ top: 0, behavior: 'smooth' }); // cuộn lên đầu trang
        return false; // chặn submit
    }

    // Nếu ngày bắt đầu sau ngày kết thúc → cảnh báo lỗi logic
    if (new Date(start) > new Date(end)) {
        alertBox.classList.remove("d-none");
        alertBox.classList.add("alert-danger");
        alertBox.innerHTML = "⚠️ Ngày bắt đầu không thể sau ngày kết thúc.";
        window.scrollTo({ top: 0, behavior: 'smooth' });
        return false;
    }

    // Ẩn cảnh báo nếu hợp lệ
    alertBox.classList.add("d-none");
    return true;
}


const growthCtx = document.getElementById('growthChart').getContext('2d');
const revenues = [${revenues}];
const growthRates = [];

for (let i = 1; i < revenues.length; i++) {
    const prev = revenues[i - 1];
    const curr = revenues[i];
    const rate = prev > 0 ? ((curr - prev) / prev) * 100 : 0;
    growthRates.push(rate.toFixed(2));
}

new Chart(growthCtx, {
    type: 'line',
    data: {
        labels: [${months}].slice(1),
        datasets: [{
            label: 'Tăng trưởng (%)',
            data: growthRates,
            borderColor: 'rgb(255, 99, 132)',
            tension: 0.3,
            fill: false,
            borderWidth: 2,
            pointRadius: 4
        }]
    },
    options: {
        responsive: true,
        scales: {
            y: {
                beginAtZero: true,
                title: { display: true, text: '% Tăng trưởng' }
            },
            x: {
                title: { display: true, text: 'Tháng' }
            }
        },
        plugins: {
            tooltip: {
                callbacks: {
                    label: ctx => ctx.parsed.y + '%'
                }
            }
        }
    }
});
</script>
</body>
</html>

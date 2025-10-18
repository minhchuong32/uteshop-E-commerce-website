<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>

<html>
<head>
    <title>Thống kê doanh thu</title>
</head>

<body class="bg-light">
	<div class="container py-4">
		<h3 class="text-primary-custom fw-bold mb-4">
			<i class="bi bi-bar-chart"></i> Quản lý doanh thu sàn thương mại
		</h3>
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

		<div id="alertBox"
			class="alert alert-warning alert-dismissible fade show d-none"
			role="alert">
			⚠️ Vui lòng chọn đầy đủ <strong>Từ ngày</strong> và <strong>Đến
				ngày</strong> trước khi lọc.
			<button type="button" class="btn-close" data-bs-dismiss="alert"
				aria-label="Close"></button>
		</div>

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

		<div class="card shadow-sm mb-4">
			<div class="card-body">
				<h5 class="text-primary-custom mb-3">Doanh thu theo tháng</h5>
				<canvas id="revenueChart" height="90"></canvas>
			</div>
		</div>

		<div class="row g-4 mb-4">
			<div class="col-md-6">
				<div class="card shadow-sm h-100">
					<div class="card-body">
						<h5 class="text-primary-custom mb-3">Tỷ lệ doanh thu và phí sàn</h5>
						<canvas id="feeChart" height="160"></canvas>
					</div>
				</div>
			</div>

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

		<div class="card shadow-sm">
			<div class="card-body">
				<h5 class="text-primary-custom mb-3">Phân tích doanh thu</h5>
				<p class="text-muted">${analysis}</p>
			</div>
		</div>
	</div>

    <%-- 
        =====================================================================
        TRUYỀN DỮ LIỆU TỪ JSP SANG FILE JAVASCRIPT BÊN NGOÀI
        - Tạo một object JavaScript `revenuePageData`.
        - Gán các giá trị từ server (qua EL) vào các thuộc tính của object.
        - File revenue-list.js sẽ đọc dữ liệu từ object toàn cục này.
        =====================================================================
    --%>
    <script>
        const revenuePageData = {
            months: [${months}],
            revenues: [${revenues}],
            totalRevenue: ${totalRevenue},
            platformFee: ${platformFee}
        };
    </script>

</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- CSS riêng -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/dashboard-vendor.css">
	
<div class="container mt-4 text-primary-custom fw-bold mb-4">
    <h4 class="mb-4">📊 Dashboard Cửa hàng</h4>

    <!-- 1. Thống kê số liệu tổng quan -->
    <div class="row g-3 mb-4">
        <div 	class="col-md-3">
         <a href="${pageContext.request.contextPath}/vendor/orders"
			class="dashboard-card text-decoration-none">
            <div class="card text-center shadow-sm">
                <div class="card-body">
                    <i class="bi bi-people-fill fs-2 mb-2 text-primary-custom"></i>
                    <h6 class="card-title">Khách hàng</h6>
                    <p class="fw-bold mb-0">${totalCustomers}</p>
                </div>
            </div>
            </a>
        </div>
        <div class="col-md-3">
        <a href="${pageContext.request.contextPath}/vendor/products"
			class="dashboard-card text-decoration-none">
            <div class="card text-center shadow-sm">
                <div class="card-body">
                    <i class="bi bi-box-seam fs-2 mb-2 text-success"></i>
                    <h6 class="card-title">Sản phẩm</h6>
                    <p class="fw-bold mb-0">${totalProducts}</p>
                </div>
            </div>
            </a>
        </div>
        <div class="col-md-3">
        <a href="${pageContext.request.contextPath}/vendor/orders"
        class="dashboard-card text-decoration-none">
            <div class="card text-center shadow-sm">
                <div class="card-body">
                    <i class="bi bi-cart-check-fill fs-2 mb-2 text-warning"></i>
                    <h6 class="card-title">Đơn hàng</h6>
                    <p class="fw-bold mb-0">${totalOrders}</p>
                </div>
            </div>
            </a>
        </div>
        <div class="col-md-3">
        <a href="${pageContext.request.contextPath}/vendor/stats"
        class="dashboard-card text-decoration-none">
            <div class="card text-center shadow-sm">
                <div class="card-body">
                    <i class="bi bi-cash-stack fs-2 mb-2 text-danger"></i>
                    <h6 class="card-title">Doanh thu</h6>
                    <p class="fw-bold mb-0">₫<fmt:formatNumber value="${totalRevenue}" type="currency" currencySymbol=""/></p>
                </div>
            </div>
            </a>
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
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="r" items="${revenueByMonth}">
                                <tr>
                                    <td><c:out value="${r[0]}" /></td>
                                    <td><c:out value="${r[1]}" /></td>
                                    <td>₫<fmt:formatNumber value="${r[1]}" type="number" groupingUsed="true"/></td>
                                </tr>
                            </c:forEach>
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
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="p" items="${topProducts}">
                                <tr>
                                    <td><c:out value="${p[0].name}"/></td>
                                    <td><c:out value="${p[1]}"/></td>
                                    <td>₫<fmt:formatNumber value="${p[2]}" type="number" groupingUsed="true"/></td>
                                </tr>
                            </c:forEach>
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
    // Doanh thu theo tháng
    const revenueLabels = [<c:forEach var="r" items="${revenueByMonth}" varStatus="status"> 'Tháng ${r[0]}'<c:if test="${!status.last}">, </c:if> </c:forEach>];
    const revenueData = [<c:forEach var="r" items="${revenueByMonth}" varStatus="status">${r[1]}<c:if test="${!status.last}">, </c:if> </c:forEach>];

    new Chart(document.getElementById('revenueChart'), {
        type: 'bar',
        data: { labels: revenueLabels, datasets: [{ label: 'Doanh thu (VNĐ)', data: revenueData, backgroundColor: 'rgba(54,162,235,0.7)' }]},
        options: { responsive: true, scales: { y: { beginAtZero: true } } }
    });

</script>
<script>
	const orderTrendLabels = [
	<c:forEach var="r" items="${orderTrend}" varStatus="loop">
	'${r[0]}'
	<c:if test="${!loop.last}">, </c:if>
	</c:forEach>
	];
	
	const orderTrendData = [
	<c:forEach var="r" items="${orderTrend}" varStatus="loop">
	${r[1]}
	<c:if test="${!loop.last}">, </c:if>
	</c:forEach>
	];
	
	new Chart(document.getElementById('orderTrendChart'), {
	    type: 'line',
	    data: {
	        labels: orderTrendLabels,
	        datasets: [{
	            label: 'Đơn hàng',
	            data: orderTrendData,
	            borderColor: '#ff6384',
	            backgroundColor: 'rgba(255,99,132,0.2)',
	            fill: true,
	            tension: 0.4
	        }]
	    },
	    options: { responsive: true }
	});
</script>
<script>
	// --- Order Status Chart ---
	const orderStatusLabels = [
	<c:forEach var="r" items="${orderStatus}" varStatus="loop">
	'${r[0]}'
	<c:if test="${!loop.last}">, </c:if>
	</c:forEach>
	];
	
	const orderStatusData = [
	<c:forEach var="r" items="${orderStatus}" varStatus="loop">
	${r[1]}
	<c:if test="${!loop.last}">, </c:if>
	</c:forEach>
	];

	// Màu đa dạng cho order status
	const orderColors = [
	    '#4e79a7', '#f28e2b', '#e15759', '#76b7b2', '#59a14f', 
	    '#edc948', '#b07aa1', '#ff9da7', '#9c755f', '#bab0ab'
	];

	new Chart(document.getElementById('orderStatusChart'), {
	    type: 'pie',
	    data: {
	        labels: orderStatusLabels,
	        datasets: [{
	            data: orderStatusData,
	            backgroundColor: orderColors.slice(0, orderStatusLabels.length)
	        }]
	    },
	    options: {
	        responsive: true,
	        plugins: {
	            legend: { position: 'bottom' }
	        }
	    }
	});
</script>

<script>
	// --- Category Chart ---
	const categoryLabels = [
	<c:forEach var="r" items="${categoryStats}" varStatus="loop">
	'${r[0]}'
	<c:if test="${!loop.last}">, </c:if>
	</c:forEach>
	];
	
	const categoryData = [
	<c:forEach var="r" items="${categoryStats}" varStatus="loop">
	${r[1]}
	<c:if test="${!loop.last}">, </c:if>
	</c:forEach>
	];

	// Màu đa dạng cho category
	const categoryColors = [
	    '#36a2eb', '#ff6384', '#ffcd56', '#4bc0c0', '#9966ff',
	    '#c9cbcf', '#ff9f40', '#8dd3c7', '#bebada', '#fb8072'
	];

	new Chart(document.getElementById('categoryChart'), {
	    type: 'doughnut',
	    data: {
	        labels: categoryLabels,
	        datasets: [{
	            data: categoryData,
	            backgroundColor: categoryColors.slice(0, categoryLabels.length)
	        }]
	    },
	    options: {
	        responsive: true,
	        plugins: {
	            legend: { position: 'right' }
	        }
	    }
	});
</script>

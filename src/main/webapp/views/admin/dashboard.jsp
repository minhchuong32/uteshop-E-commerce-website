<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý thông báo | Admin - UteShop</title>
  
    <!-- CSS riêng -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/dashboard-admin.css">
    
</head>

<h3 class="text-primary-custom fw-bold mb-4">
	<i class="bi bi-speedometer2"></i> Thống kê nhanh
</h3>
<!-- === Thống kê nhanh === -->
<div class="row g-3 mb-4">

	<!-- Người dùng -->
	<div class="col-md-3">
		<a href="${pageContext.request.contextPath}/admin/users"
			class="dashboard-card">
			<div class="card text-center shadow-sm">
				<div class="card-body">
					<i class="bi bi-people-fill fs-2 text-primary"></i>
					<h6 class="card-title">Người dùng</h6>
					<p class="fw-bold mb-0">${totalUsers}</p>
				</div>
			</div>
		</a>
	</div>

	<!-- Sản phẩm -->
	<div class="col-md-3">
		<a href="${pageContext.request.contextPath}/admin/products"
			class="dashboard-card">
			<div class="card text-center shadow-sm">
				<div class="card-body">
					<i class="bi bi-box-seam fs-2 text-success"></i>
					<h6 class="card-title">Sản phẩm</h6>
					<p class="fw-bold mb-0">${totalProducts}</p>
				</div>
			</div>
		</a>
	</div>

	<!-- Đơn hàng -->
	<div class="col-md-3">
		<a href="${pageContext.request.contextPath}/admin/deliveries"
			class="dashboard-card">
			<div class="card text-center shadow-sm">
				<div class="card-body">
					<i class="bi bi-cart-check-fill fs-2 text-warning"></i>
					<h6 class="card-title">Đơn hàng</h6>
					<p class="fw-bold mb-0">${totalOrders}</p>
				</div>
			</div>
		</a>
	</div>

	<!-- Doanh thu -->
	<div class="col-md-3">
		<a href="${pageContext.request.contextPath}/admin/revenue"
			class="dashboard-card">
			<div class="card text-center shadow-sm">
				<div class="card-body">
					<i class="bi bi-cash-stack fs-2 text-danger"></i>
					<h6 class="card-title">Doanh thu</h6>
					<p class="fw-bold mb-0 text-success">
						<fmt:formatNumber value="${totalRevenue}" type="currency"
							currencySymbol="₫" />
					</p>
				</div>
			</div>
		</a>
	</div>
</div>


<!-- === Đơn hàng gần đây & Sản phẩm bán chạy === -->
<div class="row g-3 mb-4">
	<!-- Đơn hàng gần đây -->
	<div class="col-md-6">
		<div class="card shadow-sm h-100">
			<div class="card-body">
				<h5 class="card-title mb-3">
					<i class="bi bi-bag-check"></i> Đơn hàng gần đây
				</h5>

				<c:if test="${empty recentOrders}">
					<p class="text-muted">Chưa có đơn hàng nào gần đây.</p>
				</c:if>

				<c:forEach var="o" items="${recentOrders}">
					<div class="border-bottom pb-2 mb-3">
						<div class="d-flex justify-content-between align-items-center">
							<div>
								<strong>Đơn hàng #${o.orderId}</strong><br> <small
									class="text-muted"> <fmt:formatDate
										value="${o.createdAt}" pattern="dd/MM/yyyy HH:mm" />
								</small>
							</div>
							<div class="text-end">
								<span class="fw-bold"> <fmt:formatNumber
										value="${o.totalAmount}" type="currency" currencySymbol="₫" />
								</span><br> <span
									class="${o.status eq 'Đã giao' ? 'text-success' : 'text-primary'}">
									${o.status} </span>
							</div>
						</div>

						<!-- Danh sách sản phẩm trong đơn hàng -->
						<div class="mt-2 ps-2">
							<c:forEach var="d" items="${o.orderDetails}">
								<div class="d-flex align-items-center mb-2">
									<img
										src="${empty d.productVariant.product.imageUrl
										? pageContext.request.contextPath.concat('/assets/images/default-product.png') 
										: pageContext.request.contextPath.concat('/assets/').concat(d.productVariant.product.imageUrl)}"
										class="rounded me-2" alt="${d.productVariant.product.name}"
										style="width: 45px; height: 45px; object-fit: cover;">
									<div>
										<span class="fw-semibold">${d.productVariant.product.name}</span><br>
										<small class="text-muted"> SL: ${d.quantity} × <fmt:formatNumber
												value="${d.price}" type="currency" currencySymbol="₫" />
										</small>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>

	<!-- Sản phẩm bán chạy -->
	<div class="col-md-6">
		<div class="card shadow-sm h-100">
			<div class="card-body">
				<h5 class="card-title mb-3">
					<i class="bi bi-star-fill"></i> Top sản phẩm bán chạy
				</h5>

				<c:if test="${empty bestSellers}">
					<p class="text-muted">Chưa có sản phẩm nào được bán.</p>
				</c:if>

				<c:forEach var="p" items="${bestSellers}" varStatus="i">
					<div
						class="d-flex justify-content-between align-items-center border-bottom py-2">
						<div class="d-flex align-items-center pb-2 mb-3">
							<!-- Nếu có ảnh sản phẩm, hiển thị; nếu không, dùng ảnh mặc định -->
							<img
								src="${empty p.productImage 
										? pageContext.request.contextPath.concat('/assets/images/default-product.png') 
										: pageContext.request.contextPath.concat('/assets/').concat(p.productImage)}"
								class="rounded me-2" alt="${p.productName}"
								style="width: 50px; height: 50px; object-fit: cover;">

							<div>
								<strong>${p.productName}</strong><br> <small
									class="text-muted">${p.totalSold} lượt bán</small><br> <small
									class="text-muted">Shop: ${p.shopName}</small>
							</div>
						</div>

						<div class="text-end">
							<span class="fw-bold text-success"> <fmt:formatNumber
									value="${p.minPrice}" type="currency" currencySymbol="₫" />
							</span><br> <span class="text-muted">~ <fmt:formatNumber
									value="${p.maxPrice}" type="currency" currencySymbol="₫" />
							</span>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>

</div>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/theme.css">

<div class="container">
	<c:if test="${not empty successMessage}">
		<div class="alert alert-success text-center fw-bold mt-3 mb-3">
			${successMessage}</div>
	</c:if>

	<h3 class="fw-bold text-primary-custom mt-4 mb-4">Lịch sử đơn hàng</h3>

	<!-- Bộ lọc trạng thái -->
	<form method="get"
		action="${pageContext.request.contextPath}/user/orders"
		class="mb-4 d-flex align-items-center gap-2">
		<label for="status" class="fw-bold">Lọc theo trạng thái:</label> <select
			name="status" id="status" class="form-select w-auto"
			onchange="this.form.submit()">
			<option value="">Tất cả</option>
			<option value="Mới"
				${param.status eq 'Mới' ? 'selected' : ''}>Đang xử lý</option>
			<option value="Đã xác nhận"
				${param.status eq 'Đã xác nhận' ? 'selected' : ''}>Đã xác nhận</option>
			<option value="Đang giao"
				${param.status eq 'Đang giao' ? 'selected' : ''}>Đang giao</option>
			<option value="Đã giao"
				${param.status eq 'Đã giao' ? 'selected' : ''}>Đã giao</option>
			<option value="Đã hủy" ${param.status eq 'Đã hủy' ? 'selected' : ''}>Đã
				hủy</option>
		</select>
	</form>


	<!-- Nếu không có đơn hàng -->
	<c:if test="${not empty message}">
		<div class="text-center my-5">
			<h4 class="fw-bold">Giỏ hàng trống</h4>
			<p>Hãy thêm sản phẩm để tiếp tục mua sắm.</p>
			<a href="${pageContext.request.contextPath}/user/home"
				class="btn btn-primary"> Tiếp tục mua sắm </a>
		</div>
	</c:if>

	<!-- Nếu có đơn hàng -->
	<c:if test="${not empty orders}">
		<c:forEach var="o" items="${orders}">
			<div class="card shadow-sm mb-3">
				<div
					class="card-body d-flex justify-content-between align-items-center">
					<div>
						<!-- Mã đơn hàng -->
						<h5 class="fw-bold">Đơn hàng #ORD-${o.orderId}</h5>

						<!-- Trạng thái -->
						<span
							class="badge
                            <c:choose>
                                <c:when test='${o.status eq "Đã giao"}'>bg-success</c:when>
                                <c:when test='${o.status eq "Đang giao"}'>bg-primary</c:when>
                                <c:when test='${o.status eq "Đã xác nhận"}'>bg-warning</c:when>
                                <c:when test='${o.status eq "Mới"}'>bg-warning</c:when>
                                <c:when test='${o.status eq "Đã hủy"}'>bg-danger</c:when>
                                <c:otherwise>bg-secondary</c:otherwise>
                            </c:choose>">
							${o.status} </span>

						<p class="mb-1">
							Ngày đặt:
							<fmt:formatDate value="${o.createdAt}" pattern="dd/MM/yyyy HH:mm" />
						</p>
						<p class="mb-1">
							Tổng tiền:
							<fmt:formatNumber value="${o.totalAmount}" type="currency"
								currencySymbol="₫" />
						</p>
						<p class="mb-0">Phương thức thanh toán: ${o.paymentMethod}</p>
					</div>

					<div class="text-end">
						<!-- Nút xem chi tiết -->
						<a class="btn btn-link text-primary" data-bs-toggle="collapse"
							href="#detail-${o.orderId}" role="button" aria-expanded="false">
							Xem chi tiết </a>

						<!-- Nút khiếu nại chỉ hiển thị nếu đơn hàng đã giao -->
						<c:if test="${o.status eq 'Đã giao'}">
							<a
								href="${pageContext.request.contextPath}/user/complaints/add?orderId=${o.orderId}"
								class="btn btn-outline-danger btn-sm mt-1"> <i
								class="bi bi-exclamation-octagon-fill me-1"></i> Gửi khiếu nại
							</a>
						</c:if>
					</div>
				</div>

				<!-- Chi tiết đơn hàng -->
				<div class="collapse" id="detail-${o.orderId}">
					<div class="card-body border-top">
						<div class="row">
							<!-- Sản phẩm -->
							<div class="col-md-6">
								<h6 class="fw-bold mb-2">Sản phẩm</h6>
								<c:forEach var="d" items="${o.orderDetails}">
									<div class="d-flex mb-2">
										<img
											src="${pageContext.request.contextPath}/assets/${d.productVariant.imageUrl}"
											class="me-2 rounded" width="50" height="50" />
										<div>
											<p class="mb-0">${d.productVariant.product.name}</p>
											<small>Số lượng: ${d.quantity} × <fmt:formatNumber
													value="${d.price}" type="currency" currencySymbol="₫" />
											</small>
										</div>
									</div>
								</c:forEach>
							</div>

							<!-- Thông tin giao hàng -->
							<div class="col-md-6">
								<h6 class="fw-bold mb-2">Thông tin giao hàng</h6>
								<p class="mb-0">${o.user.username}</p>
								<p class="mb-0">${o.user.email}</p>
								<p class="mb-0">${o.user.phone}</p>
								<p class="mb-0">${o.address}</p>
							</div>
						</div>

						<!-- Timeline (delivery history) -->
						<div class="mt-3">
							<h6 class="fw-bold mb-2">Tiến trình đơn hàng</h6>
							<ul class="list-unstyled">
								<c:forEach var="deli" items="${o.deliveries}">
									<li class="mb-1"><c:choose>
											<c:when test='${deli.status eq "Đã giao"}'>
												<span class="fw-bold text-success">●</span>
												<span class="text-success">${deli.status}</span>
											</c:when>
											<c:when test='${deli.status eq "Đã hủy"}'>
												<span class="fw-bold text-danger">●</span>
												<span class="text-danger">${deli.status}</span>
											</c:when>
											<c:otherwise>
												<span class="fw-bold text-warning">●</span>
												<span class="text-warning">${deli.status}</span>
											</c:otherwise>
										</c:choose> <small class="text-muted"> (<fmt:formatDate
												value="${deli.createdAt}" pattern="dd/MM/yyyy HH:mm" />)
									</small></li>
								</c:forEach>
							</ul>
						</div>

					</div>
				</div>
			</div>
		</c:forEach>
	</c:if>

</div>

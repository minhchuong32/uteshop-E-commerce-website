<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/theme.css">

<div class="container my-5">

	<!-- Thông báo thêm sản phẩm thành công -->
	<c:if test="${not empty sessionScope.cartMessage}">
		<div class="alert alert-success alert-dismissible fade show"
			role="alert">
			${sessionScope.cartMessage}
			<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
		</div>
		<c:remove var="cartMessage" scope="session" />
	</c:if>
	<!-- Nếu giỏ hàng trống -->
	<c:if test="${empty cartItems}">
		<div class="text-center my-5">
			<h4 class="fw-bold">Giỏ hàng trống</h4>
			<p>Hãy thêm sản phẩm vào giỏ để tiếp tục mua sắm.</p>
			<a href="${pageContext.request.contextPath}/user/home"
				class="btn btn-primary-custom"> Tiếp tục mua sắm </a>
		</div>
	</c:if>

	<!-- Nếu giỏ hàng có sản phẩm -->
	<c:if test="${not empty cartItems}">
		<h3 class="fw-bold text-dark mb-4">Giỏ hàng của bạn</h3>
		<div class="card shadow-sm">
			<div class="card-body">
				<c:forEach var="item" items="${cartItems}">
					<div
						class="d-flex align-items-center justify-content-between border-bottom pb-3 mb-3">
						<!-- Hình ảnh + tên sp -->
						<div class="d-flex align-items-center">
							<img
								src="${pageContext.request.contextPath}/uploads/${item.product.imageUrl}"
								alt="${item.product.name}" class="rounded me-3"
								style="width: 70px; height: 70px; object-fit: cover;">
							<div>
								<p class="mb-1 fw-bold">${item.product.name}</p>
								<p class="text-muted mb-0">${item.product.price}₫</p>
							</div>
						</div>

						<!-- Nút tăng/giảm số lượng -->
						<div class="d-flex align-items-center">
							<form action="${pageContext.request.contextPath}/user/cart"
								method="post" class="d-flex align-items-center">
								<input type="hidden" name="productId"
									value="${item.product.productId}">
								<button type="submit" name="action" value="decrease"
									class="btn btn-sm btn-outline-secondary">-</button>
								<input type="text" readonly
									class="form-control mx-1 text-center" style="width: 50px;"
									value="${item.quantity}">
								<button type="submit" name="action" value="increase"
									class="btn btn-sm btn-outline-secondary">+</button>
							</form>

							<!-- Tổng tiền -->
							<p class="fw-bold mb-0 ms-3">${item.product.price * item.quantity}
								₫</p>

							<!-- Xóa sản phẩm -->
							<form action="${pageContext.request.contextPath}/user/cart"
								method="post" class="ms-3">
								<input type="hidden" name="productId"
									value="${item.product.productId}"> <input type="hidden"
									name="cartItemId" value="${item.cartItemId}">
								<button type="submit" name="action" value="remove"
									class="btn btn-sm btn-danger">🗑</button>
							</form>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>

		<!-- Nút thanh toán -->
		<div class="mt-4 text-end">
			<a href="${pageContext.request.contextPath}/user/checkout"
				class="btn btn-primary-custom px-4">Thanh toán</a>
		</div>
	</c:if>
</div>

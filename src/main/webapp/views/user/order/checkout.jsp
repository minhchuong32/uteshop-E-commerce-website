<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<fmt:setLocale value="vi_VN" />

<div class="container my-5">
	<h2 class="mb-4">Thanh toán</h2>

	<!-- ✅ Hiển thị thông báo -->
	<c:if test="${not empty error}">
		<div class="alert alert-danger">${error}</div>
	</c:if>

	<c:if test="${not empty sessionScope.message}">
		<div class="alert alert-success">${sessionScope.message}</div>
		<c:remove var="message" scope="session" />
	</c:if>

	<form action="${pageContext.request.contextPath}/user/checkout"
		method="post">
		<div class="row">
			<!-- Cột trái -->
			<div class="col-md-8">
				<!-- ✅ Sản phẩm được chọn -->
				<div class="card mb-4 shadow-sm">
					<div class="card-header bg-white">
						<h5 class="mb-0">Sản phẩm đã chọn</h5>
					</div>
					<div class="card-body">
						<c:if test="${empty cartItems}">
							<p class="text-muted">Không có sản phẩm nào được chọn.</p>
						</c:if>
						<c:if test="${not empty cartItems}">
							<c:set var="subtotal" value="0" />
							<c:forEach var="item" items="${cartItems}">
								<div
									class="d-flex align-items-center border-bottom py-2 cart-item">
									<!-- Ảnh sản phẩm -->
									<img
										src="${pageContext.request.contextPath}${item.productVariant.imageUrl}"
										alt="${item.productVariant.product.name}" class="me-3"
										style="width: 60px; height: 60px; object-fit: cover;">

									<!-- Thông tin -->
									<div class="flex-fill">
										<h6 class="mb-0">${item.productVariant.product.name}</h6>
										<small class="text-muted">${item.productVariant.optionValue}</small>
										<div>
											<small class="text-muted">Số lượng: ${item.quantity}</small>
										</div>
									</div>

									<!-- Giá -->
									<div class="text-end">
										<p class="mb-0 fw-semibold text-danger">
											<fmt:formatNumber
												value="${item.productVariant.price * item.quantity}"
												type="currency" currencySymbol="₫" />
										</p>
										<small class="text-muted"> (Đơn giá: <fmt:formatNumber
												value="${item.productVariant.price}" type="currency"
												currencySymbol="₫" />)
										</small>
									</div>

									<!-- Truyền id sản phẩm -->
									<input type="hidden" name="selectedItems"
										value="${item.cartItemId}">
								</div>
								<c:set var="subtotal"
									value="${subtotal + (item.productVariant.price * item.quantity)}" />
							</c:forEach>
						</c:if>
					</div>
				</div>

				<!-- ✅ Mã khuyến mãi -->
				<div class="card mb-4 shadow-sm">
					<div class="card-header bg-white">
						<h5 class="mb-0">
							<i class="bi bi-ticket-perforated"></i> Phiếu giảm giá
						</h5>
					</div>
					<div class="card-body">
						<select name="promotionId" class="form-select">
							<option value="">-- Không dùng mã --</option>
							<c:forEach var="promo" items="${promotions}">
								<option value="${promo.promotionId}">
									<c:choose>
										<c:when test="${promo.discountType eq 'percent'}">
                ${promo.value}%
            </c:when>
										<c:otherwise>
											<fmt:formatNumber value="${promo.value}" type="number"
												groupingUsed="true" />₫
            </c:otherwise>
									</c:choose>
								</option>
							</c:forEach>

						</select>
					</div>
				</div>

				<!-- ✅ Thông tin giao hàng -->
				<div class="card mb-4 shadow-sm">
					<div class="card-header bg-white">
						<h5 class="mb-0">
							<i class="bi bi-truck"></i> Thông tin giao hàng
						</h5>
					</div>
					<div class="card-body">
						<div class="mb-3">
							<label class="form-label">Họ và tên</label> <input type="text"
								name="fullname" class="form-control" required>
						</div>
						<div class="mb-3">
							<label class="form-label">Số điện thoại</label> <input
								type="text" name="phone" class="form-control" required>
						</div>
						<div class="mb-3">
							<label class="form-label">Địa chỉ giao hàng</label>
							<textarea name="address" class="form-control" rows="3" required></textarea>
						</div>
					</div>
				</div>

				<!-- ✅ Phương thức thanh toán -->
				<div class="card shadow-sm">
					<div class="card-header bg-white">
						<h5 class="mb-0">
							<i class="bi bi-credit-card"></i> Phương thức thanh toán
						</h5>
					</div>
					<div class="card-body">
						<div class="form-check">
							<input class="form-check-input" type="radio" name="payment"
								value="COD" checked> <label class="form-check-label">Thanh
								toán khi nhận hàng (COD)</label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="radio" name="payment"
								value="Momo"> <label class="form-check-label">Ví
								MoMo</label>
						</div>
						<div class="form-check">
							<input class="form-check-input" type="radio" name="payment"
								value="VNPay"> <label class="form-check-label">VNPay</label>
						</div>
					</div>
				</div>
			</div>

			<!-- Cột phải -->
			<div class="col-md-4">
				<div class="card shadow-sm">
					<div class="card-header bg-white">
						<h5 class="mb-0">Tóm tắt đơn hàng</h5>
					</div>
					<div class="card-body">
						<p class="d-flex justify-content-between">
							<span>Tạm tính (${fn:length(cartItems)} sản phẩm)</span> <strong><fmt:formatNumber
									value="${subtotal}" type="number" groupingUsed="true" />₫</strong>
						</p>
						<p class="d-flex justify-content-between">
							<span>Phí vận chuyển</span> <strong>30.000₫</strong>
						</p>
						<hr>
						<p class="d-flex justify-content-between fs-5">
							<span>Tổng cộng</span> <strong class="text-danger"><fmt:formatNumber
									value="${subtotal + 30000}" type="number" groupingUsed="true" />₫</strong>
						</p>
						<button type="submit" class="btn btn-success w-100">Đặt
							hàng</button>
						<small class="text-muted d-block text-center mt-2"> Miễn
							phí vận chuyển cho đơn hàng từ 1.000.000₫ </small>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>

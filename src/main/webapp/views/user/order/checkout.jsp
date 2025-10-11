<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>
<fmt:setLocale value="vi_VN" />

<div class="container my-5">
	<h2 class="mb-4">Thanh toán</h2>

	<!-- Thông báo -->
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
			<div class="col-md-8">
				<!-- ✅ Lặp qua từng shop -->
				<c:forEach var="entry" items="${itemsByShop}">
					<c:set var="shopId" value="${entry.key}" />
					<c:set var="shopItems" value="${entry.value}" />
					<c:set var="promos" value="${promosByShop[shopId]}" />

					<div class="card mb-4 shadow-sm">
						<div class="card-header bg-light">
							<h5 class="mb-0">🏬 Cửa hàng:
								${shopItems[0].productVariant.product.shop.name}</h5>
						</div>

						<div class="card-body">
							<c:set var="subtotal" value="0" />

							<!-- ✅ Hiển thị sản phẩm của shop -->
							<c:forEach var="item" items="${shopItems}">
								<div class="d-flex align-items-center border-bottom py-2">
									<img
										src="${pageContext.request.contextPath}${item.productVariant.imageUrl}"
										alt="${item.productVariant.product.name}"
										style="width: 60px; height: 60px; object-fit: cover;"
										class="me-3">

									<div class="flex-fill">
										<h6 class="mb-0">${item.productVariant.product.name}</h6>
										<small class="text-muted">${item.productVariant.optionValue}</small>
										<div>
											<small class="text-muted">Số lượng: ${item.quantity}</small>
										</div>
									</div>

									<div class="text-end">
										<p class="mb-0 fw-semibold text-danger">
											<fmt:formatNumber value="${item.price * item.quantity}"
												type="currency" currencySymbol="₫" />
										</p>
										<small class="text-muted">(Đơn giá: <fmt:formatNumber
												value="${item.price}" type="currency" currencySymbol="₫" />)
										</small>
									</div>

									<input type="hidden" name="selectedItems"
										value="${item.cartItemId}">
								</div>
								<c:set var="subtotal"
									value="${subtotal + (item.price * item.quantity)}" />
							</c:forEach>

							<!-- ✅ Chọn mã khuyến mãi riêng shop -->
							<div class="mt-3">
								<label class="form-label">🎟️ Mã khuyến mãi của shop</label> <select
									name="promotionId[${shopId}]" class="form-select">
									<option value="">-- Không dùng mã --</option>
									<c:forEach var="promo" items="${promos}">
										<option value="${promo.promotionId}">
											<c:choose>
												<c:when test="${promo.discountType eq 'percent'}">
                                                    Giảm ${promo.value}% (đến ${promo.endDate})
                                                </c:when>
												<c:otherwise>
                                                    Giảm <fmt:formatNumber
														value="${promo.value}" type="number" groupingUsed="true" />₫ (đến ${promo.endDate})
                                                </c:otherwise>
											</c:choose>
										</option>
									</c:forEach>
								</select>
							</div>

							<!-- ✅ Tổng tiền shop -->
							<div class="mt-3 border-top pt-2 d-flex justify-content-between">
								<span>Tạm tính:</span> <strong><fmt:formatNumber
										value="${subtotal}" type="number" groupingUsed="true" />₫</strong>
							</div>
							<div class="d-flex justify-content-between">
								<span>Phí vận chuyển:</span> <strong>30.000₫</strong>
							</div>
						</div>
					</div>
				</c:forEach>

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

			<!-- ✅ Tóm tắt đơn hàng -->
			<div class="col-md-4">
				<div class="card shadow-sm">
					<div class="card-header bg-white">
						<h5 class="mb-0">🧾 Tóm tắt thanh toán</h5>
					</div>
					<div class="card-body">
						<c:set var="grandTotal" value="0" />

						<!-- ✅ Lặp qua từng shop để tính tổng -->
						<c:forEach var="entry" items="${itemsByShop}">
							<c:set var="shopId" value="${entry.key}" />
							<c:set var="shopItems" value="${entry.value}" />
							<c:set var="shopSubtotal" value="0" />

							<c:forEach var="item" items="${shopItems}">
								<c:set var="shopSubtotal"
									value="${shopSubtotal + (item.price * item.quantity)}" />
							</c:forEach>

							<!-- ✅ Thêm phí ship -->
							<c:set var="shopTotal" value="${shopSubtotal + 30000}" />
							<c:set var="grandTotal" value="${grandTotal + shopTotal}" />

							<!-- ✅ Hiển thị tóm tắt theo shop -->
							<div class="mb-3 pb-2 border-bottom">
								<strong>🏬
									${shopItems[0].productVariant.product.shop.name}</strong><br> <small
									class="text-muted"> ${fn:length(shopItems)} mặt hàng </small><br>
								<span>Tổng: <strong class="text-danger"> <fmt:formatNumber
											value="${shopTotal}" type="number" groupingUsed="true" />₫
								</strong>
								</span>
							</div>
						</c:forEach>

						<!-- ✅ Tổng toàn bộ đơn hàng -->
						<div class="mt-3 border-top pt-3">
							<h6 class="d-flex justify-content-between">
								<span>Tổng cộng:</span> <span class="text-success fw-bold">
									<fmt:formatNumber value="${grandTotal}" type="number"
										groupingUsed="true" />₫
								</span>
							</h6>
							<small class="text-muted">Đã bao gồm phí vận chuyển từng
								shop</small>
						</div>

						<button type="submit" class="btn btn-success w-100 mt-3">Đặt
							hàng</button>
					</div>
				</div>
			</div>

		</div>
	</form>
</div>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>
<fmt:setLocale value="vi_VN" />

<div class="container my-5">
	<h2 class="mb-4">Thanh toán</h2>

	<!-- Thông báo -->
	<c:if test="${not empty error}">
		<div class="alert alert-danger">${error}</div>
	</c:if>

	<c:if test="${not empty message}">
		<div class="alert alert-success">${message}</div>
		<c:remove var="message" scope="session" />
	</c:if>

	<form action="${pageContext.request.contextPath}/user/checkout"
		method="post">
		<div class="row">
			<!-- Thông tin giao hàng -->
			<div class="mb-4">
				<h5>📦 Thông tin giao hàng</h5>
				<div class="card p-3">
					<div class="card mb-3">
						<div class="card-header bg-light fw-bold">📦 Chọn địa chỉ
							giao hàng</div>
						<div class="card-body">
							<c:choose>
								<c:when test="${not empty addresses}">
									<c:forEach var="addr" items="${addresses}">
										<div class="form-check mb-2 p-2 border rounded">
											<input class="form-check-input" type="radio"
												name="selectedAddressId" id="address-${addr.addressId}"
												value="${addr.addressId}"
												<c:if test="${defaultAddress != null && addr.addressId == defaultAddress.addressId}">checked</c:if>>
											<label class="form-check-label"
												for="address-${addr.addressId}"> <strong>${addr.recipientName}</strong>
												(${addr.phoneNumber})<br> ${addr.addressLine},
												${addr.ward}, ${addr.district}, ${addr.city} <c:if
													test="${addr.isDefault}">
													<span class="badge bg-success ms-2">Mặc định</span>
												</c:if>
											</label>
										</div>
									</c:forEach>
									<a href="${pageContext.request.contextPath}/user/address"
										class="btn btn-sm btn-outline-primary mt-2">✏️ Quản lý địa
										chỉ</a>
								</c:when>
								<c:otherwise>
									<div class="alert alert-warning">
										Bạn chưa có địa chỉ giao hàng. <a
											href="${pageContext.request.contextPath}/user/address"
											class="alert-link">Thêm ngay</a>.
									</div>
								</c:otherwise>
							</c:choose>
						</div>
					</div>


				</div>
			</div>
			<div class="mt-2 mb-2">
				<label class="form-label">🚚 Đơn vị vận chuyển</label> <select
					name="carrierId" class="form-select">
					<c:forEach var="c" items="${carriers}">
						<option value="${c.carrierId}">${c.carrierName}(+
							<fmt:formatNumber value="${c.carrierFee}" type="number"
								groupingUsed="true" />₫)
						</option>
					</c:forEach>
				</select>
			</div>

			<!-- Phương thức thanh toán -->
			<div class="mb-4">
				<h5>💳 Phương thức thanh toán</h5>
				<div class="card p-3">
					<div class="form-check">
						<input class="form-check-input" type="radio" name="paymentMethod"
							id="cod" value="COD" checked> <label
							class="form-check-label" for="cod">Thanh toán khi nhận
							hàng (COD)</label>
					</div>
					<div class="form-check">
						<input class="form-check-input" type="radio" name="paymentMethod"
							id="vnpay" value="VNPAY"> <label class="form-check-label"
							for="vnpay">Thanh toán qua VNPAY</label>
					</div>
					<div class="form-check">
						<input class="form-check-input" type="radio" name="paymentMethod"
							id="momo" value="MOMO"> <label class="form-check-label"
							for="momo">Thanh toán qua MoMo</label>
					</div>

				</div>
			</div>

			<div class="col-md-8">
				<!--  Lặp qua từng shop -->
				<c:set var="grandTotal" value="0" />
				<c:set var="totalItems" value="0" />
				<c:set var="shopCount" value="0" />

				<c:forEach var="entry" items="${itemsByShop}">
					<c:set var="shopId" value="${entry.key}" />
					<c:set var="shopItems" value="${entry.value}" />
					<c:set var="promos" value="${promosByShop[shopId]}" />

					<c:set var="subtotal" value="0" />
					<c:set var="shopCount" value="${shopCount + 1}" />

					<!--  từng shop -->
					<div class="card mb-3">
						<div class="card-header bg-light">
							<h5 class="mb-0">🏬 Cửa hàng:
								${shopItems[0].productVariant.product.shop.name}</h5>
						</div>

						<div class="card-body" data-subtotal="${subtotal}">
							<!--  Hiển thị sản phẩm -->
							<c:forEach var="item" items="${shopItems}">
								<div class="d-flex align-items-center border-bottom py-2">
									<img
										src="${pageContext.request.contextPath}/assets${item.productVariant.imageUrl}"
										alt="${item.productVariant.product.name}"
										style="width: 60px; height: 60px; object-fit: cover;"
										class="me-3">

									<div class="flex-fill">
										<h6 class="mb-0">${item.productVariant.product.name}</h6>
										<small class="text-muted">${item.productVariant.optionValue}</small>
										<div>
											<small class="text-muted">Số lượng: ${item.quantity}</small>
										</div>

										<!-- 🎟️ Mã khuyến mãi theo sản phẩm -->
										<div class="mt-2">
											<label class="form-label">🎟️ Mã khuyến mãi</label> <select
												name="promotionId_product[${item.productVariant.product.productId}]"
												class="form-select promotion-select"
												data-product-id="${item.productVariant.product.productId}">
												<option value="" data-type="none" data-value="0">--
													Không dùng mã --</option>
												<c:forEach var="promo"
													items="${promosByProduct[item.productVariant.product.productId]}">
													<option value="${promo.promotionId}"
														data-type="${promo.discountType}"
														data-value="${promo.value}">
														<c:choose>
															<c:when test="${promo.discountType eq 'percent'}">
									Giảm ${promo.value}% (đến ${promo.endDate})
								</c:when>
															<c:otherwise>
									Giảm <fmt:formatNumber value="${promo.value}" type="number"
																	groupingUsed="true" />₫ (đến ${promo.endDate})
								</c:otherwise>
														</c:choose>
													</option>
												</c:forEach>
											</select>
										</div>
									</div>

									<div class="text-end">
										<p class="mb-0 fw-semibold text-danger"
											data-product-id="${item.productVariant.product.productId}">
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
								<c:set var="totalItems" value="${totalItems + item.quantity}" />
							</c:forEach>

							<!--  Tổng tiền shop -->
							<div class="mt-3 border-top pt-2 d-flex justify-content-between">
								<span>Tạm tính:</span> <strong class="shop-subtotal"
									data-shop-id="${shopId}" data-subtotal="${subtotal}">
									<fmt:formatNumber value="${subtotal}" type="number"
										groupingUsed="true" />₫
								</strong>
							</div>
							<div class="d-flex justify-content-between">
								<span>Phí vận chuyển:</span> <strong>30.000₫</strong>
							</div>

							<c:set var="shopTotal" value="${subtotal + 30000}" />

							<div class="d-flex justify-content-between mt-2">
								<span>Sau giảm giá:</span> <strong
									class="shop-total text-danger" id="shop-total-${shopId}"
									data-shop-id="${shopId}"> <fmt:formatNumber
										value="${shopTotal}" type="number" groupingUsed="true" />₫
								</strong>
							</div>

							<c:set var="grandTotal" value="${grandTotal + shopTotal}" />
						</div>
					</div>

				</c:forEach>
			</div>

			<!--  Cột tóm tắt thanh toán -->
			<div class="col-md-4">
				<div class="card shadow-sm">
					<div class="card-header bg-white">
						<h5 class="mb-0">🧾 Tóm tắt thanh toán</h5>
					</div>
					<div class="card-body">

						<!-- 🔸 Lặp qua từng shop -->
						<c:set var="grandTotal" value="0" />
						<c:forEach var="entry" items="${itemsByShop}">
							<c:set var="shopId" value="${entry.key}" />
							<c:set var="shopItems" value="${entry.value}" />

							<!-- Tính tổng và số lượng -->
							<c:set var="shopSubtotal" value="0" />
							<c:set var="itemCount" value="0" />
							<c:forEach var="item" items="${shopItems}">
								<c:set var="shopSubtotal"
									value="${shopSubtotal + (item.price * item.quantity)}" />
								<c:set var="itemCount" value="${itemCount + item.quantity}" />
							</c:forEach>

							<!-- Tổng có ship -->
							<c:set var="shopTotal" value="${shopSubtotal + 30000}" />
							<c:set var="grandTotal" value="${grandTotal + shopTotal}" />

							<!--  Hiển thị từng shop -->
							<div class="mb-3 border-bottom pb-2">
								<strong>🏪
									${shopItems[0].productVariant.product.shop.name}</strong><br> <span>${itemCount}
									sản phẩm</span><br>

								<div class="d-flex justify-content-between">
									<span>Tạm tính:</span> <span class="shop-summary-subtotal"
										data-shop-id="${shopId}"> <fmt:formatNumber
											value="${shopSubtotal}" type="number" groupingUsed="true" />₫
									</span>
								</div>

								<div class="d-flex justify-content-between">
									<span>Giảm giá:</span> <span
										class="text-danger shop-summary-discount"
										data-shop-id="${shopId}">- 0₫</span>
								</div>

								<div class="d-flex justify-content-between">
									<span>Phí vận chuyển:</span> <span
										class="shop-summary-shipping" data-shop-id="${shopId}">30.000₫</span>
								</div>

								<div class="d-flex justify-content-between fw-bold mt-1">
									<span>Tổng:</span> <span class="text-danger shop-summary-total"
										id="shop-summary-total-${shopId}" data-shop-id="${shopId}"
										data-value="${shopTotal}"> <fmt:formatNumber
											value="${shopTotal}" type="number" groupingUsed="true" />₫
									</span>
								</div>

							</div>
						</c:forEach>

						<hr>
						<h5 class="d-flex justify-content-between">
							<span>Tổng cộng:</span> <span class="text-success fw-bold"
								id="grand-total"> <fmt:formatNumber value="${grandTotal}"
									type="number" groupingUsed="true" />₫
							</span>
						</h5>
						<small class="text-muted">Đã bao gồm phí vận chuyển từng
							shop</small>

						<button type="submit" class="btn btn-success w-100 mt-3">
							Đặt hàng</button>
					</div>
				</div>
			</div>

		</div>
	</form>
</div>

<script
	src="${pageContext.request.contextPath}/assets/js/user/checkout.js"></script>

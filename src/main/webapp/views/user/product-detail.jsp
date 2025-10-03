<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>${product.name}|UteShop</title>
</head>
<body>
	<div class="container py-4">
		<div class="row g-4">
			<!-- Hình ảnh sản phẩm -->
			<div class="col-md-6">
				<!-- ảnh chính -->
				<c:forEach var="img" items="${images}">
					<c:if test="${img.main}">
						<img id="mainImg"
							src="${pageContext.request.contextPath}/assets/images/products/${img.imageUrl}"
							alt="${product.name}" class="product-detail-img mb-3" />
					</c:if>
				</c:forEach>

				<!-- thumbnails -->
				<div class="d-flex gap-2">
					<c:forEach var="img" items="${images}">
						<img
							src="${pageContext.request.contextPath}/assets/images/products/${img.imageUrl}"
							class="thumb-img ${img.main ? 'active' : ''}"
							onclick="changeImage(this)" />
					</c:forEach>
				</div>
			</div>


			<!-- Thông tin sản phẩm -->
			<div class="col-md-6">
				<h3 class="fw-bold">${product.name}</h3>
				<p class="text-muted">Cung cấp bởi UteShop</p>
				<div class="mb-2 text-warning">
					★★★★☆ <span class="text-muted">(89 đánh giá)</span>
				</div>

				<h4 class="text-danger fw-bold">
					<fmt:formatNumber value="${product.price}" type="currency"
						currencySymbol="₫" />
					<small class="text-muted text-decoration-line-through ms-2">
						<fmt:formatNumber value="${product.oldPrice}" type="currency"
							currencySymbol="₫" />
					</small> <span class="badge bg-danger ms-2">Tiết kiệm 10%</span>
				</h4>

				<p class="mt-3">${product.description}</p>

				<!-- Kích cỡ -->
				<div class="mb-3">
					<label class="fw-bold d-block">Kích cỡ</label>
					<div class="btn-group flex-wrap" role="group"
						aria-label="Chọn kích cỡ">
						<input type="radio" class="btn-check" name="size" id="sizeXS">
						<label class="btn btn-outline-primary" for="sizeXS">XS</label> <input
							type="radio" class="btn-check" name="size" id="sizeS"> <label
							class="btn btn-outline-primary" for="sizeS">S</label> <input
							type="radio" class="btn-check" name="size" id="sizeM"> <label
							class="btn btn-outline-primary" for="sizeM">M</label> <input
							type="radio" class="btn-check" name="size" id="sizeL"> <label
							class="btn btn-outline-primary" for="sizeL">L</label> <input
							type="radio" class="btn-check" name="size" id="sizeXL"> <label
							class="btn btn-outline-primary" for="sizeXL">XL</label>
					</div>
				</div>

				<!-- Màu sắc -->
				<div class="mb-3">
					<label class="fw-bold d-block">Màu sắc</label>
					<div class="btn-group flex-wrap" role="group"
						aria-label="Chọn màu sắc">
						<input type="radio" class="btn-check" name="color" id="colorWhite">
						<label class="btn btn-outline-primary" for="colorWhite">White</label>

						<input type="radio" class="btn-check" name="color" id="colorBlack">
						<label class="btn btn-outline-primary" for="colorBlack">Black</label>

						<input type="radio" class="btn-check" name="color" id="colorNavy">
						<label class="btn btn-outline-primary" for="colorNavy">Navy</label>

						<input type="radio" class="btn-check" name="color" id="colorGray">
						<label class="btn btn-outline-primary" for="colorGray">Gray</label>
					</div>
				</div>

				<!-- Số lượng -->
				<div class="mb-3 d-flex align-items-center">
					<label class="fw-bold me-3">Số lượng</label>
					<button class="btn btn-outline-secondary btn-sm"
						onclick="changeQty(-1)">-</button>
					<input id="qty" type="text" class="form-control mx-2 text-center"
						value="1" style="width: 60px;">
					<button class="btn btn-outline-secondary btn-sm"
						onclick="changeQty(1)">+</button>
				</div>

				<!-- Nút hành động -->
				<div class="d-flex gap-2 mb-3">
					<button class="btn btn-primary-custom flex-fill">
						<i class="bi bi-cart-plus"></i> Thêm vào giỏ
					</button>
					<button class="btn btn-dark flex-fill">Mua ngay</button>
					<button class="btn btn-outline-secondary">
						<i class="bi bi-heart"></i>
					</button>
				</div>

				<div class="d-flex gap-4 text-muted small">
					<span><i class="bi bi-truck"></i> Miễn phí vận chuyển</span> <span><i
						class="bi bi-shield-check"></i> Bảo hành 2 năm</span> <span><i
						class="bi bi-arrow-repeat"></i> Đổi trả 30 ngày</span>
				</div>
			</div>
		</div>

		<!-- Thông tin cửa hàng  -->

		<!-- Thông tin sản phẩm  -->


		<!-- Tabs mô tả & đánh giá -->
		<div class="tab-pane " id="reviews">

			<div class="p-3">
				<h5 class="fw-bold text-uppercase text-primary-custom">
					<i class="bi bi-grid me-2"></i> Đánh giá sản phẩm
				</h5>
			</div>
			<!-- Tổng quan đánh giá -->
			<div class="mb-3">
				<div class="text-warning">
					<c:forEach var="i" begin="1" end="5">
						<i
							class="bi ${i <= product.averageRating ? 'bi-star-fill' : 'bi-star'}"></i>
					</c:forEach>
					<span class="text-muted"> (<fmt:formatNumber
							value="${product.averageRating}" maxFractionDigits="1" />/5 từ
						${product.reviewsCount} đánh giá)
					</span>

				</div>
			</div>


			<c:if test="${hasPurchased}">
				<div class="mb-4 d-flex align-items-start">
					<!-- Avatar -->
					<img
						src="${pageContext.request.contextPath}/uploads/${sessionScope.account.avatar}"
						alt="Avatar" class="rounded-circle me-3"
						style="width: 50px; height: 50px; object-fit: cover;">

					<div class="flex-grow-1">
						<form action="${pageContext.request.contextPath}/review/add"
							method="post">
							<input type="hidden" name="productId"
								value="${product.productId}" />

							<!-- Người dùng chọn rating -->
							<div class="mb-2 text-warning">
								<c:forEach var="i" begin="1" end="5">
									<input type="radio" class="btn-check" name="rating"
										id="star${i}" value="${i}">
									<label class="bi bi-star-fill btn btn-outline-warning"
										for="star${i}"></label>
								</c:forEach>
							</div>

							<!-- Comment -->
							<textarea name="comment" rows="3" class="form-control mb-2"
								placeholder="Chia sẻ cảm nhận của bạn..."></textarea>

							<!-- Nút gửi -->
							<button type="submit" class="btn btn-primary-custom">Gửi
								đánh giá</button>
						</form>
					</div>
				</div>
			</c:if>

			<c:if test="${not hasPurchased}">
				<p class="text-muted">Bạn cần mua sản phẩm này để viết đánh giá.</p>
			</c:if>


			<!-- Danh sách các review -->
			<c:forEach var="r" items="${reviews}">
				<div class="border-bottom pb-2 mb-3 d-flex">
					<img
						src="${pageContext.request.contextPath}/uploads/${r.user.avatar}"
						class="rounded-circle me-3"
						style="width: 40px; height: 40px; object-fit: cover;">
					<div>
						<strong>${r.user.username}</strong>
						<div class="text-warning small">
							<c:forEach var="i" begin="1" end="5">
								<i class="bi ${i <= r.rating ? 'bi-star-fill' : 'bi-star'}"></i>
							</c:forEach>
						</div>
						<p class="mb-0">${r.comment}</p>
					</div>
				</div>
			</c:forEach>

			<!-- Nếu chưa có review nào -->
			<c:if test="${empty reviews}">
				<p>Chưa có đánh giá nào.</p>
			</c:if>
		</div>


	</div>
	<script
		src="${pageContext.request.contextPath}/assets/js/product-detail.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

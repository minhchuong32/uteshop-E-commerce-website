<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>
<!-- GUEST -->
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

				<!-- Ảnh chính -->
				<c:forEach var="img" items="${images}">
					<c:if test="${img.main}">
						<c:choose>
							<c:when test="${fn:startsWith(img.imageUrl, '/assets/')}">
								<img id="mainImg"
									src="${pageContext.request.contextPath}${img.imageUrl}"
									alt="${product.name}" class="product-detail-img mb-3" />
							</c:when>
							<c:otherwise>
								<img id="mainImg"
									src="${pageContext.request.contextPath}/assets${img.imageUrl.startsWith('/') ? img.imageUrl : '/' + img.imageUrl}"
									alt="${product.name}" class="product-detail-img mb-3" />
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>

				<!-- Thumbnails -->
				<div class="d-flex gap-2">
					<c:forEach var="img" items="${images}">
						<c:choose>
							<c:when test="${fn:startsWith(img.imageUrl, '/assets/')}">
								<img src="${pageContext.request.contextPath}${img.imageUrl}"
									class="thumb-img ${img.main ? 'active' : ''}"
									onclick="changeImage(this)" />
							</c:when>
							<c:otherwise>
								<img
									src="${pageContext.request.contextPath}/assets${img.imageUrl.startsWith('/') ? img.imageUrl : '/' + img.imageUrl}"
									class="thumb-img ${img.main ? 'active' : ''}"
									onclick="changeImage(this)" />
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</div>

			</div>


			<!-- Thông tin sản phẩm -->
			<div class="col-md-6">
				<h3 class="fw-bold">${product.name}</h3>
				<p class="text-muted">
					Cung cấp bởi <a
						href="${pageContext.request.contextPath}/web/shop/detail?id=${product.shop.shopId}">
						${product.shop.name} </a>
				</p>

				<!-- Hiển thị rating trung bình và số lượng đánh giá -->
				<div class="mb-2 text-warning">
					<c:forEach var="i" begin="1" end="5">
						<i
							class="bi ${i <= product.averageRating ? 'bi-star-fill' : 'bi-star'}"></i>
					</c:forEach>
					<span class="text-muted"> (<fmt:formatNumber
							value="${product.averageRating}" maxFractionDigits="1" />/5 từ
						${product.reviewsCount} đánh giá)
					</span>
				</div>


				<h4 class="text-danger fw-bold">
					<c:if test="${not empty minVariant}">
						<span id="current-price"> <fmt:formatNumber
								value="${minVariant.price}" type="currency" currencySymbol="₫" />
						</span>
						<c:if
							test="${not empty minVariant.oldPrice && minVariant.oldPrice > minVariant.price}">
							<small id="old-price"
								class="text-muted text-decoration-line-through ms-2"> <fmt:formatNumber
									value="${minVariant.oldPrice}" type="currency"
									currencySymbol="₫" />
							</small>
							<span class="badge bg-danger ms-2">Tiết kiệm</span>
						</c:if>
					</c:if>
					<c:if test="${empty minVariant}">
						<span class="text-muted">Chưa có phân loại</span>
					</c:if>
				</h4>

				<p class="mt-3">${product.description}</p>

				<!-- Thuộc tính sản phẩm -->
				<c:if test="${not empty optionMap}">
					<c:forEach var="entry" items="${optionMap}">
						<div class="mb-3">
							<!-- Option name row -->
							<label class="fw-bold d-block mb-2">${entry.key}</label>

							<!-- Values row -->
							<div class="btn-group flex-wrap" role="group">
								<c:forEach var="val" items="${entry.value}">
									<input type="radio" class="btn-check" name="${entry.key}"
										id="${entry.key}_${val}" value="${val}" required>
									<label class="btn btn-outline-primary mb-2"
										for="${entry.key}_${val}">${val}</label>
								</c:forEach>
							</div>
						</div>
					</c:forEach>
				</c:if>

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
					<c:choose>
						<c:when test="${not empty minVariant and minVariant.stock > 0}">
							<!-- Nếu chưa đăng nhập -->
							<c:if test="${empty sessionScope.account}">
								<form action="${pageContext.request.contextPath}/login"
									method="get" onsubmit="return validateSelection()">
									<input type="hidden" name="redirect"
										value="${pageContext.request.requestURI}">
									<button type="submit" class="btn btn-primary-custom w-100">
										<i class="bi bi-cart-plus"></i> Thêm vào giỏ
									</button>
								</form>
							</c:if>
							<c:if test="${empty sessionScope.account}">
								<form action="${pageContext.request.contextPath}/login"
									method="get" onsubmit="return validateSelection()">
									<input type="hidden" name="redirect"
										value="${pageContext.request.requestURI}"> <input
										type="hidden" name="quantity" id="formQtyNow" value="1">
									<input type="hidden" name="action" value="buyNow">
									<button type="submit" class="btn btn-dark w-100">Mua
										ngay</button>
								</form>
							</c:if>

						</c:when>

						<c:otherwise>
							<button class="btn btn-secondary w-100 flex-fill" disabled>
								<i class="bi bi-x-circle"></i> Hết hàng
							</button>
						</c:otherwise>
					</c:choose>

					<button class="btn btn-outline-secondary">
						<i class="bi bi-heart"></i>
					</button>
				</div>
			</div>
		</div>

		<!-- Thông tin cửa hàng -->
		<div class="card mt-4 border-0 shadow-sm">
			<div class="card-body">
				<h5 class="fw-bold text-primary-custom mb-3">
					<i class="bi bi-shop me-2"></i>Thông tin cửa hàng
				</h5>
				<div class="d-flex align-items-center gap-3">
					<img
						src="${pageContext.request.contextPath}/assets${product.shop.logo}"
						alt="${product.shop.name}" class="rounded-circle"
						style="width: 80px; height: 80px; object-fit: cover;">

					<div class="flex-grow-1">
						<h6 class="mb-1 fw-bold">${product.shop.name}</h6>
						<p class="mb-1 text-muted small">
							<i class="bi bi-person-circle"></i> ${product.shop.user.username}
						</p>
						<p class="mb-1 text-muted small">
							<i class="bi bi-calendar-event"></i>
							<fmt:formatDate value="${product.shop.createdAt}"
								pattern="dd/MM/yyyy" />
						</p>
						<p class="mb-0 text-muted small">
							<i class="bi bi-box-seam"></i> ${productCount} sản phẩm
						</p>
					</div>

					<a
						href="${pageContext.request.contextPath}/web/shop/detail?id=${product.shop.shopId}"
						class="btn btn-outline-primary">Xem Shop</a>
				</div>
			</div>
		</div>

	

		<!-- BẢNG THÔNG TIN SẢN PHẨM -->
		<div class="card mt-4 border-0 shadow-sm">
			<div class="card-body">
				<h5 class="fw-bold text-primary-custom mb-3">
					<i class="bi bi-info-circle me-2"></i>Chi tiết sản phẩm
				</h5>
				<table class="table table-borderless mb-0">
					<tr class="border-bottom">
						<th class="py-3" style="width: 200px;">Danh mục</th>
						<td class="py-3">${product.category != null ? product.category.name : '-'}</td>
					</tr>
					<tr class="border-bottom">
						<th class="py-3">Giá hiện tại</th>
						<td class="py-3"><span id="price-value"
							class="text-danger fw-bold"> <c:choose>
									<c:when test="${not empty minVariant}">
										<fmt:formatNumber value="${minVariant.price}" type="currency"
											currencySymbol="₫" />
									</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
						</span></td>
					</tr>
					<tr class="border-bottom">
						<th class="py-3">Giá cũ</th>
						<td class="py-3"><span id="oldprice-value"
							class="text-muted text-decoration-line-through"> <c:choose>
									<c:when
										test="${not empty minVariant.oldPrice and minVariant.oldPrice > minVariant.price}">
										<fmt:formatNumber value="${minVariant.oldPrice}"
											type="currency" currencySymbol="₫" />
									</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
						</span></td>
					</tr>
					<tr class="border-bottom">
						<th class="py-3">Tồn kho</th>
						<td class="py-3"><span id="stock-value"> <c:choose>
									<c:when test="${not empty minVariant and minVariant.stock > 0}">
										<span class="badge bg-success">Còn hàng
											(${minVariant.stock})</span>
									</c:when>
									<c:otherwise>
										<span class="badge bg-danger">Hết hàng</span>
									</c:otherwise>
								</c:choose>
						</span></td>
					</tr>
					<tr>
						<th class="py-3">Mô tả</th>
						<td class="py-3">${product.description}</td>
					</tr>
				</table>
			</div>
		</div>


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

				</div>
			</c:if>

			<c:if test="${not hasPurchased}">
				<p class="text-muted">Bạn cần mua sản phẩm này để viết đánh giá.</p>
			</c:if>


			<c:choose>
				<c:when test="${not empty reviews}">
					<c:forEach var="r" items="${reviews}">
						<div class="d-flex gap-3 border-bottom pb-3 mb-3">
							<img
								src="${pageContext.request.contextPath}/assets/images${r.user.avatar}"
								class="rounded-circle"
								style="width: 40px; height: 40px; object-fit: cover;">
							<div class="flex-grow-1">
								<strong class="d-block">${r.user.username}</strong>
								<div class="text-warning small mb-1">
									<c:forEach var="i" begin="1" end="5">
										<i class="bi ${i <= r.rating ? 'bi-star-fill' : 'bi-star'}"></i>
									</c:forEach>
								</div>

								<!-- Nội dung đánh giá -->
								<p class="mb-1 text-muted">${r.comment}</p>

								<!-- ✅ Hiển thị ảnh hoặc video nếu có -->
								<c:if test="${not empty r.mediaUrl}">
									<c:choose>
										<c:when
											test="${fn:endsWith(r.mediaUrl, '.mp4') || fn:endsWith(r.mediaUrl, '.mov') || fn:endsWith(r.mediaUrl, '.avi')}">
											<video controls
												style="width: 200px; border-radius: 8px; display: block; margin-top: 6px;">
												<source
													src="${pageContext.request.contextPath}/${r.mediaUrl}"
													type="video/mp4">
												Trình duyệt không hỗ trợ phát video.
											</video>
										</c:when>
										<c:otherwise>
											<img src="${pageContext.request.contextPath}/${r.mediaUrl}"
												alt="Ảnh đánh giá"
												style="width: 200px; height: 200px; object-fit: cover; border-radius: 8px; display: block; margin-top: 6px;">
										</c:otherwise>
									</c:choose>
								</c:if>
							</div>
						</div>
					</c:forEach>

				</c:when>
				<c:otherwise>
					<p class="text-muted text-center py-4">Chưa có đánh giá nào.</p>
				</c:otherwise>
			</c:choose>
			<style>
/* ẢNH CHÍNH SẢN PHẨM */
.product-detail-img {
	width: 100%; /* Ảnh full chiều ngang container */
	height: auto; /* Tự động điều chỉnh chiều cao đúng tỉ lệ */
	max-height: 500px; /* Giới hạn chiều cao để không quá to */
	object-fit: contain; /* Giữ nguyên tỉ lệ, không bị méo */
	background-color: #f9f9f9; /* Màu nền nếu ảnh không lấp đầy */
	border-radius: 10px;
	display: block;
	margin: 0 auto;
}

/* ẢNH THUMBNAIL DƯỚI */
.thumb-img {
	width: 80px;
	height: 80px;
	object-fit: cover; /* Thumbnail nên đồng kích thước, không bị dư */
	border-radius: 8px;
	cursor: pointer;
	transition: all 0.2s ease;
}

.thumb-img.active, .thumb-img:hover {
	border: 2px solid #0d6efd;
	transform: scale(1.05);
}
</style>
		</div>
		<script
			src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
		<!-- Truyền biến từ JSP sang JS -->
		<div id="product-detail" data-product-id="${product.productId}"
			data-context="${pageContext.request.contextPath}"></div>

		<script
			src="${pageContext.request.contextPath}/assets/js/product-detail.js"></script>
</body>
</html>


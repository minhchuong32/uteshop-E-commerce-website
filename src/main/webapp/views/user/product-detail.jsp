<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>
<!-- USER -->
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
						<c:choose>
							<c:when test="${fn:startsWith(img.imageUrl, '/assets/')}">
								<img id="mainImg"
									src="${pageContext.request.contextPath}${img.imageUrl}"
									alt="${product.name}" class="product-detail-img mb-3" />
							</c:when>
							<c:otherwise>
								<img id="mainImg"
									src="${pageContext.request.contextPath}/assets${img.imageUrl}"
									alt="${product.name}" class="product-detail-img mb-3" />
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>

				<!-- thumbnails -->
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
									src="${pageContext.request.contextPath}/assets${img.imageUrl}"
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
						href="${pageContext.request.contextPath}/user/shop/detail?id=${product.shop.shopId}">
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
						<span class="text-muted">Chưa có biến thể</span>
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
					<!-- Thêm vào giỏ -->
					<form id="addToCartForm"
						action="${pageContext.request.contextPath}/user/cart/add"
						method="post" class="flex-fill"
						onsubmit="return validateSelection()">

						<input type="hidden" name="variantId" id="variantId"> <input
							type="hidden" name="quantity" id="formQty" value="1">
						<button type="submit" class="btn btn-primary-custom w-100">
							<i class="bi bi-cart-plus"></i> Thêm vào giỏ
						</button>
					</form>

					<!-- Mua ngay -->
					<form action="${pageContext.request.contextPath}/user/cart"
						method="post" class="flex-fill"
						onsubmit="return validateSelection()">
						<input type="hidden" name="productId" value="${product.productId}">
						<input type="hidden" name="quantity" id="formQtyNow" value="1">
						<input type="hidden" name="action" value="buyNow">
						<button type="submit" class="btn btn-dark w-100">Mua ngay</button>
					</form>

					<button class="btn btn-outline-secondary">
						<i class="bi bi-heart"></i>
					</button>
				</div>

				<script>
					// Đồng bộ số lượng từ input qty vào 2 form
					function syncQty() {
						let qty = document.getElementById("qty").value;
						document.getElementById("formQty").value = qty;
						document.getElementById("formQtyNow").value = qty;
					}
					document.getElementById("qty").addEventListener("input",
							syncQty);
				</script>


			</div>
		</div>

		<!-- Thông tin cửa hàng -->
		<div class="mt-5">
			<h5 class="fw-bold text-uppercase text-primary-custom mb-3">
				<i class="bi bi-shop me-2"></i> Thông tin cửa hàng
			</h5>
			<div class="d-flex align-items-center border rounded p-3 bg-light">
				<!-- Logo shop (dùng ảnh mặc định nếu chưa có) -->
				<img
					src="${pageContext.request.contextPath}/assets${product.shop.logo}"
					alt="${product.shop.name}" class="rounded me-3"
					style="width: 80px; height: 80px; object-fit: cover;">

				<!-- Chi tiết shop -->
				<div class="flex-grow-1">
					<h6 class="mb-1 fw-bold">${product.shop.name}</h6>
					<p class="mb-1 text-muted small">
						<i class="bi bi-person-circle"></i> Chủ shop:
						${product.shop.user.username}
					</p>
					<p class="mb-1 text-muted small">
						<i class="bi bi-calendar-event"></i> Tham gia ngày
						<fmt:formatDate value="${product.shop.createdAt}"
							pattern="dd/MM/yyyy" />
					</p>
					<p class="mb-0 text-muted small">
						<i class="bi bi-box-seam"></i> Sản phẩm đang bán: ${productCount}
					</p>
				</div>

				<!-- Nút xem shop -->
				<div>
					<a
						href="${pageContext.request.contextPath}/user/shop/detail?id=${product.shop.shopId}"
						class="btn btn-outline-primary"> Xem Shop </a>

				</div>
			</div>
		</div>
		<c:set var="variants" value="${product.variants}" />

		<!-- BẢNG THÔNG TIN -->
		<table class="table table-bordered align-middle">
			<tr>
				<th>Danh mục</th>
				<td>${product.category != null ? product.category.name : '-'}</td>
			</tr>

			<tr>
				<th>Giá hiện tại</th>
				<td id="current-price"><span id="price-value"> <c:choose>
							<c:when test="${not empty minVariant}">
								<fmt:formatNumber value="${minVariant.price}" type="currency"
									currencySymbol="₫" />
							</c:when>
							<c:otherwise>-</c:otherwise>
						</c:choose>
				</span></td>
			</tr>

			<tr>
				<th>Giá cũ</th>
				<td id="old-price"><span id="oldprice-value"> <c:choose>
							<c:when
								test="${not empty minVariant.oldPrice and minVariant.oldPrice > minVariant.price}">
								<fmt:formatNumber value="${minVariant.oldPrice}" type="currency"
									currencySymbol="₫" />
							</c:when>
							<c:otherwise>-</c:otherwise>
						</c:choose>
				</span></td>
			</tr>

			<tr>
				<th>Tồn kho</th>
				<td id="stock-status"><span id="stock-value"> <c:choose>
							<c:when test="${not empty minVariant and minVariant.stock > 0}">
                    Còn hàng (${minVariant.stock})
                </c:when>
							<c:otherwise>
								<span class="text-danger">Hết hàng</span>
							</c:otherwise>
						</c:choose>
				</span></td>
			</tr>
			<tr>
				<th>Mô tả</th>
				<td>${product.description}</td>
			</tr>
		</table>


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
				<p class="text-muted">Vui lòng vào Đơn hàng/Đánh giá để chia sẻ trải nghiệm của bạn</p>		
			</c:if>

			<c:if test="${not hasPurchased}">
				<p class="text-muted">Bạn cần mua sản phẩm này để viết đánh giá.</p>
			</c:if>


			<!-- Danh sách các review -->
			<c:forEach var="r" items="${reviews}">
				<div class="border-bottom pb-2 mb-3 d-flex">
					<img
						src="${pageContext.request.contextPath}/assets/images${r.user.avatar}"
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
	<div id="tempAlert"
		class="alert d-none position-fixed top-0 start-50 translate-middle-x mt-3 shadow-lg"
		style="z-index: 1055; min-width: 300px; text-align: center; border-radius: 10px;">
	</div>
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

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Truyền biến từ JSP sang JS -->
	<div id="product-detail" data-product-id="${product.productId}"
		data-context="${pageContext.request.contextPath}"></div>
	<c:if test="${not empty sessionScope.cartMessage}">
		<script>
			document.addEventListener("DOMContentLoaded", function() {
				showTempAlert("${fn:escapeXml(sessionScope.cartMessage)}",
						"${sessionScope.cartMessageType}", 3000);
			});
		</script>
		<c:remove var="cartMessage" scope="session" />
		<c:remove var="cartMessageType" scope="session" />
	</c:if>

	<script
		src="${pageContext.request.contextPath}/assets/js/product-detail.js"></script>
</body>
</html>

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
					<fmt:formatNumber value="${minVariant.price}" type="currency"
						currencySymbol="₫" />
					<c:if
						test="${not empty minVariant.oldPrice && minVariant.oldPrice > minVariant.price}">
						<small class="text-muted text-decoration-line-through ms-2">
							<fmt:formatNumber value="${minVariant.oldPrice}" type="currency"
								currencySymbol="₫" />
						</small>
						<span class="badge bg-danger ms-2">Tiết kiệm</span>
					</c:if>
				</h4>

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
					<form action="${pageContext.request.contextPath}/web/cart"
						method="post" class="flex-fill"
						onsubmit="return validateSelection()">
						<input type="hidden" name="productId" value="${product.productId}">
						<input type="hidden" name="quantity" id="formQty" value="1">
						<input type="hidden" name="action" value="add">
						<button type="submit" class="btn btn-primary-custom w-100">
							<i class="bi bi-cart-plus"></i> Thêm vào giỏ
						</button>
					</form>

					<!-- Mua ngay -->
					<form action="${pageContext.request.contextPath}/web/cart"
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
					src="${pageContext.request.contextPath}/assets/images/shops/default-shop-logo.png"
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
						href="${pageContext.request.contextPath}/web/shop/detail?id=${product.shop.shopId}"
						class="btn btn-outline-primary"> Xem Shop </a>

				</div>
			</div>
		</div>

		<!-- Thông tin sản phẩm -->
		<div class="mt-5">
			<h5 class="fw-bold text-uppercase text-primary-custom mb-3">
				<i class="bi bi-info-circle me-2"></i> Thông tin sản phẩm
			</h5>
			<table class="table table-bordered">
				<tbody>
					<tr>
						<th style="width: 200px;">Tên sản phẩm</th>
						<td>${product.name}</td>
					</tr>
					<tr>
						<th>Danh mục</th>
						<td>${product.category.name}</td>
					</tr>
					<tr>
						<th>Giá hiện tại</th>
						<td><fmt:formatNumber value="${minVariant.price}"
								type="currency" currencySymbol="₫" /></td>
					</tr>
					<tr>
						<th>Giá cũ</th>
						<td><c:if
								test="${not empty minVariant.oldPrice && minVariant.oldPrice > minVariant.price}">
								<fmt:formatNumber value="${minVariant.oldPrice}" type="currency"
									currencySymbol="₫" />
							</c:if> <c:if
								test="${empty minVariant.oldPrice || minVariant.oldPrice <= minVariant.price}">-</c:if>
						</td>
					</tr>

					<tr>
						<th>Tồn kho</th>
						<td><c:choose>
								<c:when test="${minVariant != null && minVariant.stock > 0}">Còn hàng (${minVariant.stock})
            </c:when>
								<c:otherwise>
									<span class="text-danger">Hết hàng</span>
								</c:otherwise>
							</c:choose></td>
					</tr>


					<tr>
						<th>Mô tả</th>
						<td>${product.description}</td>
					</tr>
				</tbody>
			</table>
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
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/product-detail.js"></script>
		
</body>
</html>



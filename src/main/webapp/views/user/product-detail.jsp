<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>${product.name}|UteShop</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
	<div class="container py-4">
		<div class="row g-4">
			<!-- Hình ảnh sản phẩm -->
			<div class="col-md-6">
				<c:set var="mainImage"
					value="${images.stream().filter(img -> img.main).findFirst().orElse(null)}" />
				<c:set var="imgPath"
					value="${fn:startsWith(mainImage.imageUrl, '/assets/') ? mainImage.imageUrl : '/assets'.concat(mainImage.imageUrl)}" />

				<img id="mainImg" src="${pageContext.request.contextPath}${imgPath}"
					alt="${product.name}" class="w-100 rounded-3 mb-3"
					style="height: 500px; object-fit: contain; background: #f9f9f9;">

				<!-- Thumbnails -->
				<div class="d-flex gap-2 flex-wrap">
					<c:forEach var="img" items="${images}">
						<c:set var="thumbPath"
							value="${fn:startsWith(img.imageUrl, '/assets/') ? img.imageUrl : '/assets'.concat(img.imageUrl)}" />
						<img src="${pageContext.request.contextPath}${thumbPath}"
							class="thumb-img ${img.main ? 'active' : ''}"
							onclick="changeImage(this)">
					</c:forEach>
				</div>

			</div>

			<!-- Thông tin sản phẩm -->
			<div class="col-md-6">
				<h3 class="fw-bold">${product.name}</h3>
				<p class="text-muted mb-2">
					Cung cấp bởi <a
						href="${pageContext.request.contextPath}/user/shop/detail?id=${product.shop.shopId}"
						class="text-decoration-none">${product.shop.name}</a>
				</p>

				<!-- Rating -->
				<div class="mb-3 text-warning">
					<c:forEach var="i" begin="1" end="5">
						<i
							class="bi ${i <= product.averageRating ? 'bi-star-fill' : 'bi-star'}"></i>
					</c:forEach>
					<span class="text-muted ms-1"> (<fmt:formatNumber
							value="${product.averageRating}" maxFractionDigits="1" />/5 từ
						${product.reviewsCount} đánh giá)
					</span>
				</div>

				<!-- Giá -->
				<h4 class="text-danger fw-bold mb-3">
					<c:choose>
						<c:when test="${not empty minVariant}">
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
						</c:when>
						<c:otherwise>
							<span class="text-muted">Chưa có biến thể</span>
						</c:otherwise>
					</c:choose>
				</h4>

				<p class="text-muted">${product.description}</p>

				<!-- Thuộc tính sản phẩm -->
				<c:if test="${not empty optionMap}">
					<c:forEach var="entry" items="${optionMap}">
						<div class="mb-3">
							<label class="fw-bold d-block mb-2">${entry.key}</label>
							<div class="btn-group flex-wrap" role="group">
								<c:forEach var="val" items="${entry.value}">
									<input type="radio" class="btn-check" name="${entry.key}"
										id="${entry.key}_${val}" value="${val}" required>
									<label class="btn btn-outline-primary"
										for="${entry.key}_${val}">${val}</label>
								</c:forEach>
							</div>
						</div>
					</c:forEach>
				</c:if>

				<!-- Số lượng -->
				<div class="mb-3 d-flex align-items-center gap-2">
					<label class="fw-bold">Số lượng</label>
					<div class="input-group" style="width: 150px;">
						<button class="btn btn-outline-secondary" onclick="changeQty(-1)">−</button>
						<input id="qty" type="number" class="form-control text-center"
							value="1" min="1">
						<button class="btn btn-outline-secondary" onclick="changeQty(1)">+</button>
					</div>
				</div>

				<!-- Nút hành động -->
				<div class="d-flex gap-2 mb-3">
					<c:choose>
						<c:when test="${not empty minVariant and minVariant.stock > 0}">
							<form id="addToCartForm" class="flex-fill">
								<input type="hidden" name="variantId" id="variantId"> <input
									type="hidden" name="quantity" id="formQty" value="1">
								<button type="button" class="btn btn-primary-custom w-100"
									onclick="addToCart()">
									<i class="bi bi-cart-plus"></i> Thêm vào giỏ
								</button>
							</form>


							<form id="buyNowForm" method="post" class="flex-fill">
								<input type="hidden" name="variantId" id="variantIdNow">
								<input type="hidden" name="quantity" id="formQtyNow" value="1">
								<button type="submit" class="btn btn-danger w-100">Mua
									ngay</button>
							</form>
						</c:when>
						<c:otherwise>
							<button class="btn btn-secondary w-100" disabled>
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
						href="${pageContext.request.contextPath}/user/shop/detail?id=${product.shop.shopId}"
						class="btn btn-outline-primary">Xem Shop</a>
				</div>
			</div>
		</div>

		<!-- Thông tin chi tiết -->
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

		<!-- Đánh giá -->
		<div class="card mt-4 border-0 shadow-sm">
			<div class="card-body">
				<h5 class="fw-bold text-primary-custom mb-3">
					<i class="bi bi-star me-2"></i>Đánh giá sản phẩm
				</h5>

				<div class="mb-3 text-warning">
					<c:forEach var="i" begin="1" end="5">
						<i
							class="bi ${i <= product.averageRating ? 'bi-star-fill' : 'bi-star'}"></i>
					</c:forEach>
					<span class="text-muted ms-2"> (<fmt:formatNumber
							value="${product.averageRating}" maxFractionDigits="1" />/5 từ
						${product.reviewsCount} đánh giá)
					</span>
				</div>

				<p class="text-muted small">${hasPurchased ? 'Vui lòng vào Đơn hàng/Đánh giá để chia sẻ trải nghiệm của bạn' : 
					'Bạn cần mua sản phẩm này để viết đánh giá.'}
				</p>

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
														src="${pageContext.request.contextPath}/assets${r.mediaUrl}"
														type="video/mp4">
													Trình duyệt không hỗ trợ phát video.
												</video>
											</c:when>
											<c:otherwise>
												<img src="${pageContext.request.contextPath}/assets${r.mediaUrl}"
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
			</div>
		</div>
	</div>

	<!-- Alert -->
	<div id="tempAlert"
	class="alert d-none position-fixed start-50 translate-middle-x shadow-lg rounded-3"
	style="z-index: 2000; top: 80px; min-width: 320px;"></div>


	<!-- Product Data -->
	<div id="product-detail" data-product-id="${product.productId}"
		data-context="${pageContext.request.contextPath}" class="d-none"></div>

	<script
		src="${pageContext.request.contextPath}/assets/js/product-detail.js"></script>
</body>
</html>
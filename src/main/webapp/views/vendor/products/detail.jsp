<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<title>Chi tiết sản phẩm | ${product.name}</title>
<!-- CSS riêng -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/product-detail-vendor.css">

</head>
<body>
	<div class="container py-4">

		<div class="row g-4 align-items-start">
			<!-- Cột ảnh -->
			<div class="col-md-6 position-relative">
				<!-- Ảnh chính -->
				<img id="mainImage"
					src="${pageContext.request.contextPath}/assets/${product.imageUrl}"
					alt="${product.name}" class="main-img">

				<!-- Nút chuyển -->
				<button class="carousel-btn prev" onclick="prevImage()">‹</button>
				<button class="carousel-btn next" onclick="nextImage()">›</button>

				<!-- Ảnh phụ (từ ProductImage + Variant.imageUrl) -->
				<div class="thumbs mt-3" id="thumbContainer">
					<!-- Ảnh đại diện -->
					<c:if test="${not empty product.imageUrl}">
						<img
							src="${pageContext.request.contextPath}/assets/${product.imageUrl}"
							class="active" onclick="changeImage(this)">
					</c:if>

					<!-- Ảnh từ ProductImage -->
					<c:forEach var="img" items="${product.images}">
						<c:if test="${!img.main}">
					        <img
					            src="${pageContext.request.contextPath}/assets${img.imageUrl}"
					            onclick="changeImage(this)">
					    </c:if>
					</c:forEach>

					<!-- Ảnh từ variant -->
					<c:forEach var="v" items="${product.variants}">
						<c:if test="${not empty v.imageUrl}">
							<img
								src="${pageContext.request.contextPath}/assets/${v.imageUrl}"
								onclick="changeImage(this)">
						</c:if>
					</c:forEach>
				</div>
			</div>

			<!-- Cột thông tin sản phẩm -->
			<div class="col-md-6">
				<h3 class="fw-bold">${product.name}</h3>
				<p class="text-muted mb-1">
					<i class="bi bi-shop"></i> <a
						href="${pageContext.request.contextPath}/vendor/shop/detail?id=${product.shop.shopId}">
						${product.shop.name} </a>
				</p>

				<!-- Đánh giá -->
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

				<!-- Giá -->
				<h4 class="text-danger fw-bold">
					<c:choose>
						<c:when test="${minPrice > 0}">
							<fmt:formatNumber value="${minPrice}" type="currency"
								currencySymbol="₫" />
						</c:when>
						<c:otherwise>Chưa có giá</c:otherwise>
					</c:choose>
				</h4>

				<!-- Mô tả -->
				<p class="mt-3">${product.description}</p>

				<!-- Tổng tồn kho -->
				<p>
					<b>Tổng tồn kho:</b> ${totalStock} sản phẩm
				</p>
			</div>
		</div>

		<hr class="my-4">

		<!-- Đánh giá sản phẩm -->
		<div id="reviews">
			<h5 class="fw-bold text-uppercase text-primary-custom mb-3">
				<i class="bi bi-chat-dots me-2"></i> Đánh giá sản phẩm
			</h5>

			<c:choose>
				<c:when test="${not empty reviews}">
					<c:forEach var="r" items="${reviews}">
						<div class="border-bottom pb-3 mb-3 d-flex">
							<img
								src="${pageContext.request.contextPath}/assets/images${r.user.avatar}"
								class="rounded-circle me-3"
								style="width: 45px; height: 45px; object-fit: cover;">
							<div>
								<strong>${r.user.username}</strong>
								<div class="text-warning small mb-1">
									<c:forEach var="i" begin="1" end="5">
										<i class="bi ${i <= r.rating ? 'bi-star-fill' : 'bi-star'}"></i>
									</c:forEach>
								</div>
								<p class="mb-1">${r.comment}</p>
								<c:if test="${not empty r.mediaUrl}">
									<img
										src="${pageContext.request.contextPath}/assets/${r.mediaUrl}"
										class="img-fluid rounded mt-2"
										style="max-height: 150px; object-fit: cover;">
								</c:if>
							</div>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<p class="text-muted">Chưa có đánh giá nào cho sản phẩm này.</p>
				</c:otherwise>
			</c:choose>
		</div>

		<!-- Nút quay lại danh sách sản phẩm -->
		<div class="mb-3 text-end">
			<a href="${pageContext.request.contextPath}/vendor/products"
				class="btn btn-outline-secondary"> ⬅ Quay lại </a>
		</div>
	</div>

	<script>
    const thumbs = Array.from(document.querySelectorAll('#thumbContainer img'));
    let currentIndex = 0;

    function changeImage(el) {
        document.getElementById('mainImage').src = el.src;
        thumbs.forEach(t => t.classList.remove('active'));
        el.classList.add('active');
        currentIndex = thumbs.indexOf(el);
    }

    function nextImage() {
        currentIndex = (currentIndex + 1) % thumbs.length;
        thumbs[currentIndex].click();
    }

    function prevImage() {
        currentIndex = (currentIndex - 1 + thumbs.length) % thumbs.length;
        thumbs[currentIndex].click();
    }
</script>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>${product.name}| UteShop</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/user-home.css">
</head>
<body>
	<div class="container py-4">
		<div class="row g-4">
			<!-- Hình ảnh sản phẩm -->
			<div class="col-md-6">
				<img id="mainImg"
					src="${pageContext.request.contextPath}/assets/images/products/${product.imageUrl}"
					alt="${product.name}" class="product-detail-img mb-3" />
				<div class="d-flex gap-2">
					<img
						src="${pageContext.request.contextPath}/assets/images/products/${product.imageUrl}"
						class="thumb-img active" onclick="changeImage(this)" /> <img
						src="${pageContext.request.contextPath}/assets/images/products/sample2.jpg"
						class="thumb-img" onclick="changeImage(this)" /> <img
						src="${pageContext.request.contextPath}/assets/images/products/sample3.jpg"
						class="thumb-img" onclick="changeImage(this)" />
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

		<!-- Tabs mô tả & đánh giá -->
		<div class="mt-5">
			<ul class="nav nav-tabs">
				<li class="nav-item"><a class="nav-link active"
					data-bs-toggle="tab" href="#desc">Mô tả</a></li>
				<li class="nav-item"><a class="nav-link" data-bs-toggle="tab"
					href="#reviews">Đánh giá (0)</a></li>
			</ul>
			<div class="tab-content p-3 border border-top-0 rounded-bottom">
				<div class="tab-pane fade show active" id="desc">
					${product.description}
					<h6 class="fw-bold mt-3">Đặc điểm nổi bật</h6>
					<ul>
						<li>Chất liệu cao cấp, bền đẹp</li>
						<li>Thiết kế thoải mái, phù hợp nhiều dịp</li>
						<li>Bảo hành chính hãng</li>
						<li>Có nhiều màu sắc và kích cỡ</li>
					</ul>
				</div>
				<div class="tab-pane fade" id="reviews">
					<p>Chưa có đánh giá nào.</p>
				</div>
			</div>
		</div>
	</div>

	<script>
function changeImage(el) {
    document.getElementById("mainImg").src = el.src;
    document.querySelectorAll(".thumb-img").forEach(img => img.classList.remove("active"));
    el.classList.add("active");
}
function changeQty(delta) {
    let qty = document.getElementById("qty");
    let val = parseInt(qty.value) + delta;
    if (val < 1) val = 1;
    qty.value = val;
}
</script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

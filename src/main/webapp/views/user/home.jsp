<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Uteshop | Trang chủ User</title>

<!-- Favicon PNG -->
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/assets/images/favicon.png">

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
	rel="stylesheet">

<!-- CSS riêng -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/index.css">
</head>
<body>

	<!-- Header -->
	<%@ include file="/views/layout/header.jsp"%>

	<!-- Banner -->
	<div id="bannerDiv" class="carousel slide" data-bs-ride="carousel">
		<div class="carousel-inner">
			<div class="carousel-item active">
				<img
					src="${pageContext.request.contextPath}/assets/images/banner3.png"
					class="d-block w-100 banner-img" alt="banner3">
			</div>
			<div class="carousel-item">
				<img
					src="${pageContext.request.contextPath}/assets/images/banner2.png"
					class="d-block w-100 banner-img" alt="banner2">
			</div>
			<div class="carousel-item">
				<img
					src="${pageContext.request.contextPath}/assets/images/banner1.png"
					class="d-block w-100 banner-img" alt="banner1">
			</div>
		</div>
		<!-- Nút điều hướng -->
		<button class="carousel-control-prev" type="button" id="btnPrev">
			<span class="carousel-control-prev-icon"></span>
		</button>
		<button class="carousel-control-next" type="button" id="btnNext">
			<span class="carousel-control-next-icon"></span>
		</button>
	</div>

	<!-- Danh mục -->
	<div class="container my-4">
		<h2 class="mb-4 px-2 py-1 border rounded bg-light"
			style="font-size: 20px; color: #4B87AE;">DANH MỤC</h2>


		<div class="row text-center g-3">
			<!-- Hàng 1 -->
			<div class="col-2 container-item">
				<img
					src="${pageContext.request.contextPath}/assets/images/danhmuc_T-shrit.png"
					alt="Thời Trang Nam" class="img-fluid rounded-circle mb-2" />
				<p>Thời Trang Nam</p>
			</div>
			<div class="col-2 container-item">
				<img
					src="${pageContext.request.contextPath}/assets/images/danhmuc_DienThoai.png"
					alt="Điện Thoại & Phụ Kiện" class="img-fluid rounded-circle mb-2" />
				<p>Điện Thoại và Phụ Kiện</p>
			</div>
			<div class="col-2 container-item">
				<img
					src="${pageContext.request.contextPath}/assets/images/danhmuc_ThietbiDienTu.png"
					alt="Thiết Bị Điện Tử" class="img-fluid rounded-circle mb-2" />
				<p>Thiết Bị Điện Tử</p>
			</div>
			<div class="col-2 container-item">
				<img
					src="${pageContext.request.contextPath}/assets/images/danhmuc_Laptop.png"
					alt="Máy Tính & Laptop" class="img-fluid rounded-circle mb-2" />
				<p>Máy Tính và Laptop</p>
			</div>
			<div class="col-2 container-item">
				<img
					src="${pageContext.request.contextPath}/assets/images/danhmuc_Mayanh.png"
					alt="Máy Ảnh & Máy Quay Phim" class="img-fluid rounded-circle mb-2" />
				<p>Máy Ảnh và Máy Quay Phim</p>
			</div>
			<div class="col-2 container-item">
				<img
					src="${pageContext.request.contextPath}/assets/images/danhmuc_Dongho.png"
					alt="Đồng Hồ" class="img-fluid rounded-circle mb-2" />
				<p>Đồng Hồ</p>
			</div>

			<!-- Hàng 2 -->
			<div class="col-2 container-item">
				<img
					src="${pageContext.request.contextPath}/assets/images/danhmuc_giayDep.png"
					alt="Giày Dép Nam" class="img-fluid rounded-circle mb-2" />
				<p>Giày Dép Nam</p>
			</div>
			<div class="col-2 container-item">
				<img
					src="${pageContext.request.contextPath}/assets/images/danhmuc_thietBiGiaDung.png"
					alt="Thiết Bị Điện Gia Dụng" class="img-fluid rounded-circle mb-2" />
				<p>Thiết Bị Điện Gia Dụng</p>
			</div>
			<div class="col-2 container-item">
				<img
					src="${pageContext.request.contextPath}/assets/images/danhmuc_TheThao.png"
					alt="Thể Thao & Du Lịch" class="img-fluid rounded-circle mb-2" />
				<p>Thể Thao và Du Lịch</p>
			</div>
			<div class="col-2 container-item">
				<img
					src="${pageContext.request.contextPath}/assets/images/danhmuc_BaloTuiXach.png"
					alt="Ô Tô & Xe Máy & Xe Đạp" class="img-fluid rounded-circle mb-2" />
				<p>Ba lô</p>
			</div>
			<div class="col-2 container-item">
				<img
					src="${pageContext.request.contextPath}/assets/images/danhmuc_doChoi.png"
					alt="Đồ chơi" class="img-fluid rounded-circle mb-2" />
				<p>Đồ chơi</p>
			</div>
			<div class="col-2 container-item">
				<img
					src="${pageContext.request.contextPath}/assets/images/danhmuc_SachOnline.png"
					alt="Sức Khỏe / Bách Hóa Online"
					class="img-fluid rounded-circle mb-2" />
				<p>Nhà Sách Online</p>
			</div>
		</div>
	</div>

	<!-- Sản phẩm gợi ý -->
	<div class="container my-4">
		<h2 class="mb-4 px-2 py-1 border rounded bg-light"
			style="font-size: 20px; color: #4B87AE;">SẢN PHẨM GỢI Ý</h2>

		<!-- Bộ lọc / Sort -->
		<div class="d-flex flex-wrap align-items-center mb-4 gap-2">
			<!-- Sort giá -->
			<select class="form-select w-auto" id="priceSort">
				<option value="">-- Sắp xếp theo giá --</option>
				<option value="asc">Giá: Thấp → Cao</option>
				<option value="desc">Giá: Cao → Thấp</option>
			</select>
		
			<!-- Nút filter -->
			<button class="btn" id="newestBtn">Mới
				nhất</button>
			<button class="btn" id="popularBtn">Phổ
				biến</button>
		</div>


		<div class="row g-3">
			<!-- Item -->
			<div class="col-12 col-sm-6 col-lg-4">
				<div
					class="card border shadow-sm h-100 overflow-hidden product-card">
					<a href="/product/1"> <img
						src="${pageContext.request.contextPath}/assets/images/balo.png"
						class="card-img-top img-fluid product-img"
						alt="Premium Wireless Headphones">
					</a>
					<div class="card-body d-flex flex-column justify-content-between"
						style="height: 150px;">
						<h5 class="card-title mb-1" style="font-size: 16px;">Balo</h5>

						<!-- Rating -->
						<div class="mb-1">
							<span class="text-warning">★★★★☆</span> <small class="text-muted">(23
								reviews)</small>
						</div>

						<!-- Giá -->
						<div class="d-flex align-items-center gap-2">
							<p class="card-text text-danger fw-bold mb-0"
								style="font-size: 15px;">₫599,000</p>
							<p class="card-text mb-0"
								style="font-size: 12px; text-decoration: line-through; color: #6c757d;">₫799,000</p>
						</div>

						<!-- Button thêm vào giỏ hàng -->
						<button class="btn btn-sm w-100"
							style="background-color: #00558D; color: #fff;">Thêm vào
							giỏ hàng</button>
					</div>
				</div>
			</div>

			<div class="col-12 col-sm-6 col-lg-4">
				<div
					class="card border shadow-sm h-100 overflow-hidden product-card">
					<a href="/product/1"> <img
						src="${pageContext.request.contextPath}/assets/images/iphone.png"
						class="card-img-top img-fluid product-img"
						alt="Premium Wireless Headphones">
					</a>
					<div class="card-body d-flex flex-column justify-content-between"
						style="height: 150px;">
						<h5 class="card-title mb-1" style="font-size: 16px;">Iphone
							16 Pro Max</h5>

						<!-- Rating -->
						<div class="mb-1">
							<span class="text-warning">★★★★☆</span> <small class="text-muted">(2323
								reviews)</small>
						</div>

						<!-- Giá -->
						<div class="d-flex align-items-center gap-2">
							<p class="card-text text-danger fw-bold mb-0"
								style="font-size: 15px;">₫1,799,000</p>
							<p class="card-text mb-0"
								style="font-size: 12px; text-decoration: line-through; color: #6c757d;">₫2,199,000</p>
						</div>

						<!-- Button thêm vào giỏ hàng -->
						<button class="btn btn-sm w-100"
							style="background-color: #00558D; color: #fff;">Thêm vào
							giỏ hàng</button>
					</div>
				</div>
			</div>

			<div class="col-12 col-sm-6 col-lg-4">
				<div
					class="card border shadow-sm h-100 overflow-hidden product-card">
					<a href="/product/1"> <img
						src="${pageContext.request.contextPath}/assets/images/tainghe.png"
						class="card-img-top img-fluid product-img"
						alt="Premium Wireless Headphones">
					</a>
					<div class="card-body d-flex flex-column justify-content-between"
						style="height: 150px;">
						<h5 class="card-title mb-1" style="font-size: 16px;">Tai nghe</h5>

						<!-- Rating -->
						<div class="mb-1">
							<span class="text-warning">★★★★☆</span> <small class="text-muted">(98
								reviews)</small>
						</div>

						<!-- Giá -->
						<div class="d-flex align-items-center gap-2">
							<p class="card-text text-danger fw-bold mb-0"
								style="font-size: 15px;">₫399,000</p>
							<p class="card-text mb-0"
								style="font-size: 12px; text-decoration: line-through; color: #6c757d;">499,000</p>
						</div>

						<!-- Button thêm vào giỏ hàng -->
						<button class="btn btn-sm w-100"
							style="background-color: #00558D; color: #fff;">Thêm vào
							giỏ hàng</button>
					</div>
				</div>
			</div>

			<div class="col-12 col-sm-6 col-lg-4">
				<div
					class="card border shadow-sm h-100 overflow-hidden product-card">
					<a href="/product/1"> <img
						src="${pageContext.request.contextPath}/assets/images/dongho.png"
						class="card-img-top img-fluid product-img"
						alt="Premium Wireless Headphones">
					</a>
					<div class="card-body d-flex flex-column justify-content-between"
						style="height: 150px;">
						<h5 class="card-title mb-1" style="font-size: 16px;">Đồng hồ</h5>

						<!-- Rating -->
						<div class="mb-1">
							<span class="text-warning">★★★★☆</span> <small class="text-muted">(232
								reviews)</small>
						</div>

						<!-- Giá -->
						<div class="d-flex align-items-center gap-2">
							<p class="card-text text-danger fw-bold mb-0"
								style="font-size: 15px;">₫6,499,000</p>
							<p class="card-text mb-0"
								style="font-size: 12px; text-decoration: line-through; color: #6c757d;">₫7,499,000</p>
						</div>

						<!-- Button thêm vào giỏ hàng -->
						<button class="btn btn-sm w-100"
							style="background-color: #00558D; color: #fff;">Thêm vào
							giỏ hàng</button>
					</div>
				</div>
			</div>

			<div class="col-12 col-sm-6 col-lg-4">
				<div
					class="card border shadow-sm h-100 overflow-hidden product-card">
					<a href="/product/1"> <img
						src="${pageContext.request.contextPath}/assets/images/giay.png"
						class="card-img-top img-fluid product-img"
						alt="Premium Wireless Headphones">
					</a>
					<div class="card-body d-flex flex-column justify-content-between"
						style="height: 150px;">
						<h5 class="card-title mb-1" style="font-size: 16px;">Giày thể
							thao</h5>

						<!-- Rating -->
						<div class="mb-1">
							<span class="text-warning">★★★★☆</span> <small class="text-muted">(223
								reviews)</small>
						</div>

						<!-- Giá -->
						<div class="d-flex align-items-center gap-2">
							<p class="card-text text-danger fw-bold mb-0"
								style="font-size: 15px;">₫1,499,000</p>
							<p class="card-text mb-0"
								style="font-size: 12px; text-decoration: line-through; color: #6c757d;">₫2,499,000</p>
						</div>

						<!-- Button thêm vào giỏ hàng -->
						<button class="btn btn-sm w-100"
							style="background-color: #00558D; color: #fff;">Thêm vào
							giỏ hàng</button>
					</div>
				</div>
			</div>

			<div class="col-12 col-sm-6 col-lg-4">
				<div
					class="card border shadow-sm h-100 overflow-hidden product-card">
					<a href="/product/1"> <img
						src="${pageContext.request.contextPath}/assets/images/banphim.png"
						class="card-img-top img-fluid product-img"
						alt="Premium Wireless Headphones">
					</a>
					<div class="card-body d-flex flex-column justify-content-between"
						style="height: 150px;">
						<h5 class="card-title mb-1" style="font-size: 16px;">Bàn phím</h5>

						<!-- Rating -->
						<div class="mb-1">
							<span class="text-warning">★★★★☆</span> <small class="text-muted">(123
								reviews)</small>
						</div>

						<!-- Giá -->
						<div class="d-flex align-items-center gap-2">
							<p class="card-text text-danger fw-bold mb-0"
								style="font-size: 15px;">₫499,000</p>
							<p class="card-text mb-0"
								style="font-size: 12px; text-decoration: line-through; color: #6c757d;">₫699,000</p>
						</div>

						<!-- Button thêm vào giỏ hàng -->
						<button class="btn btn-sm w-100"
							style="background-color: #00558D; color: #fff;">Thêm vào
							giỏ hàng</button>
					</div>
				</div>
			</div>

		</div>
	</div>

	<!-- Phân trang -->
	<nav aria-label="Page navigation example"
		class="mt-4 d-flex justify-content-center">
		<ul class="pagination">
			<li class="page-item"><a class="page-link" href="#"
				aria-label="Previous">Trước</a></li>
			<li class="page-item"><a class="page-link page-active" href="#">1</a></li>
			<li class="page-item"><a class="page-link" href="#">2</a></li>
			<li class="page-item"><a class="page-link" href="#">3</a></li>
			<li class="page-item"><a class="page-link" href="#"
				aria-label="Next">Sau</a></li>
		</ul>
	</nav>



	<!-- Footer -->
	<%@ include file="/views/layout/footer.jsp"%>

	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

	<!-- JS riêng -->
	<script src="${pageContext.request.contextPath}/assets/js/index.js"></script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Uteshop | Trang chủ</title>

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
	<%@ include file="/WEB-INF/views/layout/header.jsp"%>

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
		<h2 class="mb-3">Danh mục</h2>
		<div class="row g-3 text-center">
			<div class="col-6 col-md-2">
				<div class="p-3 bg-light rounded">Điện thoại</div>
			</div>
			<div class="col-6 col-md-2">
				<div class="p-3 bg-light rounded">Máy tính</div>
			</div>
			<div class="col-6 col-md-2">
				<div class="p-3 bg-light rounded">Thời trang</div>
			</div>
			<div class="col-6 col-md-2">
				<div class="p-3 bg-light rounded">Gia dụng</div>
			</div>
			<div class="col-6 col-md-2">
				<div class="p-3 bg-light rounded">Mỹ phẩm</div>
			</div>
		</div>
	</div>

	<!-- Sản phẩm gợi ý -->
	<div class="container my-4">
		<h2 class="mb-3">Sản phẩm gợi ý</h2>
		<div class="row g-4">
			<div class="col-6 col-md-3">
				<div class="card h-100 text-center">
					<img src="https://via.placeholder.com/200" class="card-img-top"
						alt="Sản phẩm">
					<div class="card-body">
						<h5 class="card-title">Điện thoại Samsung</h5>
						<p class="card-text">5.000.000đ</p>
					</div>
				</div>
			</div>
			<!-- Thêm các sản phẩm khác tương tự -->
		</div>
	</div>
	
	<!-- Footer -->
	<%@ include file="/WEB-INF/views/layout/footer.jsp"%>

	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

	<!-- JS riêng -->
	<script src="${pageContext.request.contextPath}/assets/js/index.js"></script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Uteshop | Admin</title>

<!-- Favicon -->
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/assets/images/favicon.png">
<!-- Css -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/common.css">
<!-- Bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
	rel="stylesheet">
<!-- Bootstrap JS (bundle) -->
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</head>
<body class="d-flex flex-column min-vh-100">

	<!-- Header -->
	<%@ include file="/commons/vendor/header.jsp"%>

	<!-- Body -->
	<div class="container-fluid flex-grow-1">
		<div class="row">
			<!-- Sidebar -->

			<nav
				class="col-12 col-md-2 col-xl-2 shadow-sm pt-4 d-flex flex-column position-sticky top-0 vh-100 rounded-end"
				style="height: calc(100vh - 76px); background-color: #00558D; margin-top: 76px">
				<h5
					class="text-center mb-4 text-white border-bottom border-white pb-3">Quản lý cửa hàng</h5>

				<ul class="nav flex-column flex-grow-1">
					<li class="nav-item mb-1"><a
						class="nav-link ${page=='dashboard' ? 'active' : ''}"
						href="${pageContext.request.contextPath}/vendor/home?page=dashboard">
							<i class="bi bi-speedometer2 me-2"></i> Dashboard
					</a></li>

					<li class="nav-item mb-1"><a
						class="nav-link ${page=='shops' ? 'active' : ''}"
						href="${pageContext.request.contextPath}/vendor/home?page=products">
							<i class="bi bi-shop me-2"></i> Quản lý Sản phẩm
					</a></li>
					<li class="nav-item mb-1"><a
						class="nav-link ${page=='orders' ? 'active' : ''}"
						href="${pageContext.request.contextPath}/vendor/home?page=orders">
							<i class="bi bi-cart-check-fill me-2"></i> Quản lý Đơn hàng
					</a></li>
					<li class="nav-item mb-1"><a
						class="nav-link ${page=='stats' ? 'active' : ''}"
						href="${pageContext.request.contextPath}/vendor/home?page=stats">
							<i class="bi bi-bar-chart-line me-2"></i> Thống kê
					</a></li>
					<li class="nav-item mb-1"><a
						class="nav-link ${page=='settings' ? 'active' : ''}"
						href="${pageContext.request.contextPath}/vendor/home?page=settings">
							<i class="bi bi-gear-fill me-2"></i> Cài đặt
					</a></li>
				</ul>

				<!-- Đăng xuất (nằm cuối) -->
				<div class="mt-auto p-3">
					<a href="${pageContext.request.contextPath}/logout"
						class="btn btn-danger w-100"> <i
						class="bi bi-box-arrow-right me-2"></i> Đăng xuất
					</a>
				</div>
			</nav>

			<!-- Main -->
			<main class="col-12 col-md-10 col-xl-10 p-4"
				style="margin-top: 76px;">
				<jsp:include page="${view}" />
			</main>
		</div>
	</div>
	<!-- Header -->
	<%@ include file="/commons/vendor/footer.jsp"%>

</body>
</html>

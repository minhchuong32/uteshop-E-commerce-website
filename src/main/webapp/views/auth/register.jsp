<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>


<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Uteshop | Đăng ký</title>

<!-- Bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Header -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/header.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
	rel="stylesheet">

<!-- Favicon -->
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/assets/images/favicon.png">

<!-- CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/common.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/auth/register.css">

</head>
<body>

	<!-- Modern Loading Overlay -->
	<div id="preloader"
		class="position-fixed top-0 start-0 w-100 h-100 d-flex flex-column justify-content-center align-items-center"
		style="z-index: 9999;">

		<img
			src="${pageContext.request.contextPath}/assets/images/logo_strong.png"
			alt="UteShop" width="90" class="mb-4">
		<div class="loader-dots mb-4">
			<div></div>
			<div></div>
			<div></div>
		</div>

		<h5 class="fw-bold text-primary-custom">Đang tải UteShop...</h5>
	</div>
	<!-- Header -->
	<%@ include file="/commons/auth/header.jsp"%>


	<!-- Main content -->
	<div class="main-content mt-5 py-4">
		<div class="register-card" style="margin-top: 80px;">
			<!-- Title -->
			<div class="d-flex align-items-center justify-content-between mb-3">
				<h3 class="m-0 fw-bold" style="color: #00558D;">Đăng Ký</h3>
				<a class="navbar-brand d-flex align-items-center"
					href="${pageContext.request.contextPath}/"> <img
					src="${pageContext.request.contextPath}/assets/images/logo_cart_auth.png"
					alt="Logo" height="50" class="ms-2">
				</a>
			</div>

			<!-- Register Form -->
			<form action="register" method="post">
				<c:if test="${alert !=null}">
					<h3 class="alert alert-danger">${alert}</h3>
				</c:if>
				<!-- Username -->
				<div class="mb-3">
					<div class="input-group">
						<span class="input-group-text"><i class="bi bi-person"></i></span>
						<input type="text" class="form-control" id="fullname"
							name="username" placeholder="Tên đăng nhập" required>
					</div>
				</div>
				<!-- Email -->
				<div class="mb-3">
					<div class="input-group">
						<span class="input-group-text"><i class="bi bi-envelope"></i></span>
						<input type="email" class="form-control" id="email" name="email"
							placeholder="Email" required>
					</div>
				</div>
				<!-- Password -->
				<div class="mb-3">
					<div class="input-group">
						<span class="input-group-text"><i class="bi bi-lock"></i></span> 
						<input type="password" class="form-control" id="password"
							name="password" placeholder="Mật khẩu" required>
					</div>
				</div>
				<!-- Confirm Password -->
				<div class="mb-3">
					<div class="input-group">
						<span class="input-group-text"><i class="bi bi-shield-lock"></i></span>
						<input type="password" class="form-control" id="confirmPassword"
							name="confirmPassword" placeholder="Xác nhận mật khẩu" required>
					</div>
				</div>
				<!-- Submit -->
				<button type="submit" class="btn w-100"
					style="background-color: #00558D; color: white;">Đăng ký</button>
			</form>

			<!-- Divider -->
			<div class="divider">
				<span>hoặc</span>
			</div>

			<!-- Google Login Button -->
			<a href="${pageContext.request.contextPath}/login-google" class="btn-google w-100">
				<svg class="google-icon" viewBox="0 0 24 24">
					<path fill="#4285F4" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
					<path fill="#34A853" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
					<path fill="#FBBC05" d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"/>
					<path fill="#EA4335" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"/>
				</svg>
				<span>Đăng ký bằng Google</span>
			</a>

			<!-- Login link -->
			<div class="text-center mt-3">
				<span class="text-muted">Đã có tài khoản? </span> <a
					href="${pageContext.request.contextPath}/login" class="fw-semibold">Đăng
					nhập</a>
			</div>
		</div>
	</div>

	<!-- Footer -->
	<%@ include file="/commons/auth/footer.jsp"%>

	<!-- Scripts -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


	<!-- Script điều khiển hiệu ứng loading -->
	<script src="${pageContext.request.contextPath}/assets/js/loading.js"></script>
</body>
</html>
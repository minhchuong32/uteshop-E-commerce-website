<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Uteshop | Đăng nhập</title>

<!-- Bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Header -->
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
	href="${pageContext.request.contextPath}/assets/css/auth/login.css">

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
		<div class="login-card" style="margin-top: 80px;">
			<!-- Logo + Title -->
			<div class="d-flex align-items-center justify-content-between mb-3">
				<h3 class="m-0 fw-bold" style="color: #00558D;">Đăng Nhập</h3>
				<a class="navbar-brand d-flex align-items-center"
					href="${pageContext.request.contextPath}/"> <img
					src="${pageContext.request.contextPath}/assets/images/logo_cart_auth.png"
					alt="Logo" height="50" class="ms-2">
				</a>
			</div>

			<form action="login" method="post">
				<c:if test="${alert != null}">
					<p class="alert alert-danger">${alert}</p>
				</c:if>

				<!-- Email -->
				<div class="mb-3">
					<div class="input-group">
						<span class="input-group-text"><i class="bi bi-envelope"></i></span>
						<input type="text" class="form-control" id="email" name="email"
							placeholder="Email" value="${savedEmail}" required>
					</div>
				</div>

				<!-- Password -->
				<div class="mb-3">
					<div class="input-group">
						<span class="input-group-text"><i class="bi bi-lock"></i></span> <input
							type="password" class="form-control" id="password"
							name="password" placeholder="Mật khẩu" value="${savedPassword}"
							required>
					</div>
				</div>

				<!-- Remember & Forgot -->
				<div class="d-flex justify-content-between align-items-center mb-3">
					<div class="form-check">
						<input class="form-check-input" type="checkbox" id="rememberMe"
							name="remember" checked> <label class="form-check-label"
							for="rememberMe">Remember me</label>
					</div>
					<a href="${pageContext.request.contextPath}/forgot-password"
						class="text-decoration-none">Quên mật khẩu?</a>
				</div>

				<!-- Submit -->
				<button type="submit" class="btn w-100"
					style="background-color: #00558D; color: white;">Đăng nhập
				</button>
			</form>


			<!-- Register -->
			<div class="text-center mt-3">
				<span class="text-muted">Chưa có tài khoản? </span> <a
					href="${pageContext.request.contextPath}/register"
					class="fw-semibold">Đăng ký</a>
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

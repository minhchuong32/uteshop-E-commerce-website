<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Uteshop | Đặt lại mật khẩu</title>

<!-- Bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Icons -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
	rel="stylesheet">

<!-- Favicon -->
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/assets/images/favicon.png">

<style>
body {
	min-height: 100vh;
	margin: 0;
	display: flex;
	flex-direction: column; /* xếp dọc */
	background: linear-gradient(to bottom right, #00558D, #ffffff);
}

.main-content {
	flex: 1; /* chiếm hết chiều cao còn lại để đẩy footer xuống */
	display: flex;
	align-items: center;
	justify-content: center;
	margin-top:100px;
}

.reset-card {
	max-width: 420px;
	width: 100%;
	padding: 2rem;
	border-radius: 1rem;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
	background: #fff;
	text-align: center;
}

.reset-card h4 {
	margin-bottom: 1.5rem;
	font-weight: bold;
	color: #333;
}

.reset-card .form-control {
	height: 50px;
	border-radius: 0.5rem;
}

.reset-card .btn {
	height: 50px;
	font-weight: 600;
	border-radius: 0.5rem;
}
.back-btn {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    font-size: 30px;
    color: #00558D;
    text-decoration: none;
    margin-bottom: 1rem; /* tạo khoảng cách với tiêu đề */
    width: fit-content; /* chỉ chiếm chỗ icon */
}

.back-btn:hover {
    opacity: 0.4;
}


</style>
</head>
<body>
	<!-- Header -->
	<header>
		<nav class="navbar navbar-expand-lg navbar-dark fixed-top"
			style="background-color: #FFFFFF">
			<div class="container">
				<!-- Logo -->
				<a class="navbar-brand d-flex align-items-center w-29px"
					href="${pageContext.request.contextPath}/"> <img
					src="${pageContext.request.contextPath}/assets/images/logo_strong.png"
					alt="Logo" height="50" class="me-2">
				</a>
				<!-- Toggle button -->
				<button class="navbar-toggler" type="button"
					data-bs-toggle="collapse" data-bs-target="#navbarContent"
					aria-controls="navbarContent" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<!-- Menu -->
				<div class="collapse navbar-collapse" id="navbarContent">
					<ul
						class="navbar-nav mb-2 mb-lg-0 align-items-center user-links ms-auto">
						<!-- Đăng nhập-->
						<li class="nav-item"><a
							class="btn btn-sm login-btn text-white"
							style="background-color: #00558D;"
							href="${pageContext.request.contextPath}/login"> Đăng nhập </a></li>

						<!-- Dấu | -->
						<li class="nav-item px-2 text-primary d-none d-lg-block"
							style="color: #00558D !important;">|</li>

						<!-- Đăng ký -->
						<li class="nav-item fw-semibold register-btn"><a
							class="nav-link text-primary" style="color: #00558D !important;"
							href="${pageContext.request.contextPath}/register"> Đăng ký </a></li>
					</ul>
				</div>
			</div>
		</nav>
	</header>

	<!-- Main content -->
	<div class="main-content">
		<div class="reset-card py-4 mb-3">
			<a href="${pageContext.request.contextPath}/login" class="back-btn">
				<i class="bi bi-arrow-left"></i>
			</a>
			<h4>Đặt lại mật khẩu</h4>
			<form action="reset-password" method="post">
				<div class="mb-3">
					<input type="text" class="form-control" name="emailOrPhone"
						placeholder="Email/Số Điện thoại" required>
				</div>
				<button type="submit" class="btn w-100 text-white"
					style="background-color: #00558D;">TIẾP THEO</button>
			</form>
		</div>
	</div>

	

	<!-- Bootstrap Script -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

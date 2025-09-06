<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Uteshop | Reset mật khẩu thành công</title>

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
	flex-direction: column;
	background: linear-gradient(to bottom right, #00558D, #ffffff);
}

.main-content {
	flex: 1;
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
	margin-bottom: 1rem;
	font-weight: bold;
	color: #28a745; /* xanh lá báo thành công */
}

.reset-card p {
	color: #555;
	margin-bottom: 1.5rem;
}

.reset-card .btn {
	height: 50px;
	font-weight: 600;
	border-radius: 0.5rem;
}
</style>
</head>
<body>
	<div class="main-content">
		<div class="reset-card py-4 mb-3">
			<i class="bi bi-check-circle-fill text-success" style="font-size: 60px;"></i>
			<h4>Đặt lại mật khẩu thành công!</h4>
			<p>Mật khẩu của bạn đã được cập nhật. Vui lòng đăng nhập để tiếp tục.</p>
			
			<a href="${pageContext.request.contextPath}/login"
				class="btn w-100 d-flex align-items-center justify-content-center text-white"
				style="background-color: #00558D; border-radius: 8px;">
				ĐĂNG NHẬP
			</a>
		</div>
	</div>
</body>
</html>

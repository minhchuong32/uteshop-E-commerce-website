<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Uteshop | Xác thực OTP</title>

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
	margin-top: 100px;
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
	text-align: center;
	font-size: 18px;
	letter-spacing: 5px;
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
			<h4>Nhập mã OTP</h4>
			<form
				action="${pageContext.request.contextPath}/forgot-password/verify-otp"
				method="post">
				<c:if test="${not empty error}">
					<div class="alert alert-danger">${error}</div>
				</c:if>
				<c:if test="${not empty message}">
					<div class="alert alert-success">${message}</div>
				</c:if>
				<div class="mb-3">
					<input type="text" class="form-control" name="otp"
						placeholder="Nhập mã OTP" required>
				</div>
				<button type="submit"
					class="btn w-100 d-flex align-items-center justify-content-center text-white"
					style="background-color: #00558D; height: 45px; border-radius: 8px;">
					TIẾP THEO</button>
			</form>

		</div>
	</div>
</body>
</html>

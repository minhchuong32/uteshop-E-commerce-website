<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

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
<!-- CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/common.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/auth/verify-otp.css">

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

	<!-- Footer -->
	<%@ include file="/commons/auth/footer.jsp"%>

	<!-- Script điều khiển hiệu ứng loading -->
	<script src="${pageContext.request.contextPath}/assets/js/loading.js"></script>
</body>
</html>

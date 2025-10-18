<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

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

<!-- CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/common.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/auth/reset-success.css">

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
			<i class="bi bi-check-circle-fill text-success"
				style="font-size: 60px;"></i>
			<h4>Đặt lại mật khẩu thành công!</h4>
			<p>Mật khẩu của bạn đã được cập nhật. Vui lòng đăng nhập để tiếp
				tục.</p>

			<a href="${pageContext.request.contextPath}/login"
				class="btn w-100 d-flex align-items-center justify-content-center text-white"
				style="background-color: #00558D; border-radius: 8px;"> ĐĂNG
				NHẬP </a>
		</div>
	</div>
	
	<!-- Footer -->
	<%@ include file="/commons/auth/footer.jsp"%>
	
	<!-- Script điều khiển hiệu ứng loading -->
	<script src="${pageContext.request.contextPath}/assets/js/loading.js"></script>
</body>
</html>

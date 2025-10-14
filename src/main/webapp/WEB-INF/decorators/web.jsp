<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>UteShop</title>

<!-- Favicon -->
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/assets/images/favicon.png">

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
	rel="stylesheet">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/user-home.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/detail-product.css">
<style>
/* Hiệu ứng fade-in/fade-out */
#preloader {
	transition: opacity 0.5s ease;
}
#preloader.hidden {
	opacity: 0;
	visibility: hidden;
}
</style>
</head>
<body>
	<!-- Loading Overlay -->
	<div id="preloader"
		class="position-fixed top-0 start-0 w-100 h-100 bg-white d-flex flex-column justify-content-center align-items-center"
		style="z-index: 9999;">
		<img
			src="${pageContext.request.contextPath}/assets/images/logo_strong.png"
			alt="UteShop" width="90" class="mb-3">
		<div class="spinner-border text-primary-custom mb-3" role="status"
			style="width: 3rem; height: 3rem;">
			<span class="visually-hidden text-primary-custom">Loading...</span>
		</div>
		<h5 class="fw-bold text-primary-custom">Đang tải UteShop...</h5>
	</div>

	<%@ include file="/commons/web/header.jsp"%>
	<div id="main-content">
		<sitemesh:write property="body" />
	</div>
	<%@ include file="/commons/web/footer.jsp"%>
	<!-- Script điều khiển hiệu ứng loading -->
	<script>
	const loader = document.getElementById("preloader");

	// Ẩn preloader sau khi trang load xong
	window.addEventListener("load", () => {
		setTimeout(() => loader.classList.add("hidden"), 300);
	});

	// Hiện preloader khi rời trang
	window.addEventListener("beforeunload", () => {
		loader.classList.remove("hidden");
	});

	// Ẩn preloader nếu người dùng quay lại trang (Back/Forward cache)
	window.addEventListener("pageshow", (event) => {
		if (event.persisted) {
			setTimeout(() => loader.classList.add("hidden"), 100);
		}
	});
	</script>

</body>
</html>

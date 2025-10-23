<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>UteShop | Admin</title>

<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/images/favicon.png">

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/chat.css">


<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">

<link href="https://cdn.datatables.net/1.13.7/css/dataTables.bootstrap5.min.css" rel="stylesheet">

<%-- jQuery đã được XÓA khỏi đây và chuyển xuống cuối body --%>
<script src="https://cdn.jsdelivr.net/npm/chart.js" defer></script>

</head>

<body class="d-flex flex-column min-vh-100">

	<div id="preloader" class="position-fixed top-0 start-0 w-100 h-100 d-flex flex-column justify-content-center align-items-center" style="z-index: 9999;">
		<img src="${pageContext.request.contextPath}/assets/images/logo_strong.png" alt="UteShop" width="90" class="mb-4">
		<div class="loader-dots mb-4">
			<div></div>
			<div></div>
			<div></div>
		</div>
		<h5 class="fw-bold text-primary-custom">Đang tải UteShop...</h5>
	</div>

	<%@ include file="/commons/admin/header.jsp"%>

	<div class="container-fluid flex-grow-1">
		<div class="row">
			<nav class="col-12 col-md-2 col-xl-2 shadow-sm pt-4 d-flex flex-column position-sticky top-0 vh-100 rounded-end" style="height: calc(100vh - 76px); background-color: #00558D; margin-top: 76px">
				<h5 class="text-center mb-4 text-white border-bottom border-white pb-3">Quản trị hệ thống</h5>
				<ul class="nav flex-column flex-grow-1">
					<li class="nav-item mb-1"><a class="nav-link ${page=='dashboard' ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/home?page=dashboard"> <i class="bi bi-speedometer2 me-2"></i> Dashboard </a></li>
					<li class="nav-item mb-1"><a class="nav-link ${page=='users' ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/home?page=users"> <i class="bi bi-people-fill me-2"></i> Quản lý Người dùng </a></li>
					<li class="nav-item mb-1"><a class="nav-link ${page=='products' ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/home?page=products"> <i class="bi bi-box-seam-fill me-2"></i> Quản lý Sản phẩm </a></li>
					<li class="nav-item mb-1"><a class="nav-link ${page=='categories' ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/home?page=categories"> <i class="bi bi-tags-fill me-2"></i> Quản lý Danh mục </a></li>
					<li class="nav-item mb-1"><a class="nav-link ${page=='shops' ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/home?page=shops"> <i class="bi bi-shop me-2"></i> Quản lý Cửa hàng </a></li>
					<li class="nav-item mb-1"><a class="nav-link ${page=='carriers' ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/home?page=carriers"> <i class="bi bi-truck me-2"></i> Quản lý Vận chuyển </a></li>
					<li class="nav-item mb-1"><a class="nav-link ${page=='orders' ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/home?page=orders"> <i class="bi bi-cart me-2"></i> Quản lý Đơn hàng </a></li>
					<li class="nav-item mb-1"><a class="nav-link ${page=='complaints' ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/complaints"> <i class="bi bi-chat-dots me-2"></i> Quản lý Khiếu nại </a></li>
					<li class="nav-item mb-1"><a class="nav-link ${page=='revenue' ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/home?page=revenue"> <i class="bi bi-cash-stack me-2"></i> Quản lý Doanh thu </a></li>
					<li class="nav-item mb-1"><a class="nav-link ${page=='promotions' ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/home?page=promotions"> <i class="bi bi-currency-dollar me-2"></i> Quản lý Khuyến mại </a></li>
					<li class="nav-item mb-1"><a class="nav-link ${page=='settings' ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/home?page=settings"> <i class="bi bi-gear-fill me-2"></i> Cài đặt </a></li>
				</ul>
				<div class="mt-auto p-3">
					<a href="${pageContext.request.contextPath}/logout" class="btn btn-danger w-100"> <i class="bi bi-box-arrow-right me-2"></i> Đăng xuất </a>
				</div>
			</nav>

			<main class="col-12 col-md-10 col-xl-10 p-4" style="margin-top: 76px;">
				<jsp:include page="${view}" />
			</main>
		</div>
	</div>

	<%@ include file="/commons/admin/footer.jsp"%>

	<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"></script>
	<script src="https://cdn.datatables.net/1.13.7/js/dataTables.bootstrap5.min.js"></script>
	
	<script src="${pageContext.request.contextPath}/assets/js/admin/modal-delete.js"></script>

	<c:if test="${page == 'categories'}">
		<script src="${pageContext.request.contextPath}/assets/js/admin/category-list.js"></script>
	</c:if>
	<c:if test="${page == 'complaints'}">
		<script src="${pageContext.request.contextPath}/assets/js/admin/complaint-list.js"></script>
	</c:if>
	<c:if test="${page == 'orders'}">
		<script src="${pageContext.request.contextPath}/assets/js/admin/order-list.js"></script>
	</c:if>
	<c:if test="${page == 'products'}">
		<script src="${pageContext.request.contextPath}/assets/js/admin/product-list.js"></script>
	</c:if>
	<c:if test="${page == 'promotions'}">
		<script src="${pageContext.request.contextPath}/assets/js/admin/promotion-list.js"></script>
	</c:if>
	<c:if test="${page == 'shops'}">
		<script src="${pageContext.request.contextPath}/assets/js/admin/shop-list.js"></script>
	</c:if>
	<c:if test="${page == 'users'}">
		<script src="${pageContext.request.contextPath}/assets/js/admin/user-list.js"></script>
	</c:if>
	<c:if test="${page == 'carriers'}">
		<script src="${pageContext.request.contextPath}/assets/js/admin/carrier-list.js"></script>
	</c:if>
	<c:if test="${page == 'revenue'}">
		<script src="${pageContext.request.contextPath}/assets/js/admin/revenue-list.js"></script>
	</c:if>

	<script src="${pageContext.request.contextPath}/assets/js/loading.js"></script>

</body>
</html>
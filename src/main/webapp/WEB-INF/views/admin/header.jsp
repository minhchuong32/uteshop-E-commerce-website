<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!-- Header Bootstrap -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/header.css">

<header>
	<nav class="navbar navbar-expand-lg navbar-light fixed-top shadow-sm"
		style="background-color: #FFFFFF;">
		<div class="container">
			<!-- Logo -->
			<a class="navbar-brand d-flex align-items-center"
				href="${pageContext.request.contextPath}/admin/home"> <img
				src="${pageContext.request.contextPath}/assets/images/logo_strong.png"
				alt="Logo" height="50" class="me-2">
			</a>

			<!-- Toggle button cho mobile -->
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarContent"
				aria-controls="navbarContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<!-- Menu và tìm kiếm -->
			<div class="collapse navbar-collapse" id="navbarContent">


				<!-- Avatar admin -->
				<ul class="navbar-nav mb-2 mb-lg-0 align-items-center ms-auto">

					<!-- Avatar Admin -->
					<li class="nav-item dropdown"><a
						class="nav-link d-flex align-items-center" href="#"
						id="adminDropdown" role="button" data-bs-toggle="dropdown"
						aria-expanded="false"> <img
							src="${pageContext.request.contextPath}/assets/images/admin_avt.png"
							alt="Admin" width="40" height="40"
							class="rounded-circle border me-2"> <span
							class="d-none d-lg-inline fw-semibold text-dark">Admin</span>
					</a>

						<ul class="dropdown-menu dropdown-menu-end shadow"
							aria-labelledby="adminDropdown">
							<li><a class="dropdown-item"
								href="${pageContext.request.contextPath}/admin/profile">Thông
									tin cá nhân</a></li>
							<li><a class="dropdown-item"
								href="${pageContext.request.contextPath}/admin/settings">Cài
									đặt</a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item text-danger"
								href="${pageContext.request.contextPath}/">Đăng xuất</a></li>
						</ul></li>

				</ul>


			</div>
		</div>
	</nav>
</header>

<style>
/* Ẩn mũi tên dropdown mặc định */
.navbar .dropdown-toggle::after {
	display: none;
}

/* Avatar hover dropdown */
.nav-item.dropdown:hover .dropdown-menu {
	display: block;
}

/* Avatar hình tròn + border */
.nav-item.dropdown img {
	object-fit: cover;
}
</style>

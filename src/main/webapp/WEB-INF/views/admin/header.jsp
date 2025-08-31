<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!-- Header Bootstrap -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/header.css">

<header>
	<nav class="navbar navbar-expand-lg navbar-light fixed-top shadow-sm"
		style="background-color: #FFFFFF;">
		<div class="container">
			<!-- Logo -->
			<a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/admin/home">
				<img src="${pageContext.request.contextPath}/assets/images/logo_strong.png"
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
				<!-- Thanh tìm kiếm -->
				<form class="d-flex mx-auto me-3 search-form"
					action="${pageContext.request.contextPath}/search" method="get"
					style="max-width: 600px; width: 100%;">
					<div class="input-group">
						<input type="search" class="form-control" name="keyword"
							placeholder="Tìm sản phẩm, thương hiệu..." aria-label="Search">
						<button class="btn btn-primary btn-search" type="submit"
							style="background-color: #00558D; border: none;">
							<img width="20" height="20"
								src="${pageContext.request.contextPath}/assets/icon/search.png"
								alt="search" style="filter: invert(1);" />
						</button>
					</div>
				</form>

				<!-- Avatar admin + giỏ hàng -->
				<ul class="navbar-nav mb-2 mb-lg-0 align-items-center ms-auto">

					<!-- Avatar Admin -->
					<li class="nav-item dropdown">
						<a class="nav-link d-flex align-items-center" href="#" id="adminDropdown" role="button"
						   data-bs-toggle="dropdown" aria-expanded="false">
							<img src="${pageContext.request.contextPath}/assets/images/admin_avt.png"
								alt="Admin" width="40" height="40" class="rounded-circle border">
						</a>
						<ul class="dropdown-menu dropdown-menu-end shadow" aria-labelledby="adminDropdown">
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/profile">Thông tin cá nhân</a></li>
							<li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/settings">Cài đặt</a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
						</ul>
					</li>

					<!-- Giỏ hàng -->
					<li class="nav-item cart-item position-relative ms-3">
						<a class="nav-link" href="${pageContext.request.contextPath}/order/cart.jsp">
							<img width="25" height="25"
								src="${pageContext.request.contextPath}/assets/icon/cart.png"
								alt="cart" />
						</a>
						<!-- Mini Cart -->
						<div class="mini-cart shadow rounded">
							<div class="text-center p-3">
								<img src="${pageContext.request.contextPath}/assets/images/empty_cart.png"
									alt="Empty Cart" width="100" class="mb-2">
								<p class="text-muted mb-0">Chưa có sản phẩm</p>
							</div>
						</div>
					</li>

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

/* Mini cart mặc định ẩn */
.mini-cart {
	display: none;
	position: absolute;
	top: 120%;
	right: 0;
	width: 220px;
	background: #fff;
	z-index: 100;
	box-shadow: 0 0 8px rgba(0,0,0,0.15);
	border-radius: 6px;
}

/* Hiện mini cart khi hover */
.cart-item:hover .mini-cart {
	display: block;
}

/* Avatar hover dropdown */
.nav-item.dropdown:hover .dropdown-menu {
	display: block;
}

/* Avatar hình tròn + border */
.nav-item.dropdown img {
	object-fit: cover;
}

/* Responsive: search bar thu nhỏ mobile */
@media (max-width: 991px) {
	.search-form {
		margin: 10px 0;
	}
	.navbar-nav.ms-auto {
		margin-top: 10px;
	}
}
</style>

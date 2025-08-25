<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!-- Header Bootstrap -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/header.css">

<header>
	<nav class="navbar navbar-expand-lg navbar-dark"
		style="background-color: #FFFFFF">
		<div class="container">
			<!-- Logo -->
			<a class="navbar-brand d-flex align-items-center"
				href="${pageContext.request.contextPath}/"> <img
				src="${pageContext.request.contextPath}/assets/images/logo.png"
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

				<!-- Liên kết người dùng -->
				<ul class="navbar-nav mb-2 mb-lg-0 align-items-center user-links">
					<!-- Đăng nhập có background -->
					<li class="nav-item"><a
						class="btn btn-sm login-btn text-white"
						style="background-color: #00558D;"
						href="${pageContext.request.contextPath}/auth/login.jsp"> Đăng
							nhập </a></li>

					<!-- Dấu | -->
					<li class="nav-item px-2 text-primary d-none d-lg-block"
						style="color: #00558D !important;">|</li>

					<!-- Đăng ký -->
					<li class="nav-item fw-semibold register-btn"><a
						class="nav-link text-primary" style="color: #00558D !important;"
						href="${pageContext.request.contextPath}/auth/register.jsp">
							Đăng ký </a></li>

					<!-- Giỏ hàng -->
					<li class="nav-item cart-item position-relative"><a
						class="nav-link"
						href="${pageContext.request.contextPath}/order/cart.jsp"> <img
							width="25" height="25"
							src="${pageContext.request.contextPath}/assets/icon/cart.png"
							alt="cart" />
					</a> <!-- Mini Cart -->
						<div class="mini-cart shadow rounded">
							<div class="text-center p-3">
								<img
									src="${pageContext.request.contextPath}/assets/images/empty_cart.png"
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

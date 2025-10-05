<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!-- Header Bootstrap -->

<header>
	<nav class="navbar navbar-expand-lg navbar-dark fixed-top"
		style="background-color: #FFFFFF">
		<div class="container">
			<!-- Logo -->
			<a class="navbar-brand d-flex align-items-center w-29px"
				href="${pageContext.request.contextPath}/web/home"> <img
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

			<!-- Menu + Navigation + Search -->
			<div class="collapse navbar-collapse" id="navbarContent">

				<!-- Navigation -->
				<ul class="navbar-nav me-3">
					<li class="nav-item"><a class="nav-link-header nav-link fw-semibold"
						href="#main">Trang chủ</a></li>

					<li class="nav-item"><a class="nav-link-header nav-link fw-semibold"
						href="#product">Sản phẩm</a></li>

					<li class="nav-item"><a class="nav-link-header nav-link fw-semibold"
						href="#category">Danh mục</a></li>

					<li class="nav-item"><a class="nav-link-header nav-link fw-semibold"
						href="${pageContext.request.contextPath}/web/contact">Liên hệ</a>
					</li>
				</ul>

				<!-- Thanh tìm kiếm nhỏ gọn -->
				<form class="d-flex mx-auto me-3 search-form"
					action="${pageContext.request.contextPath}/web/home" method="get"
					style="max-width: 500px; width: 100%;">
					<div class="input-group">
						<input type="search" class="form-control form-control-sm"
							name="keyword" value="${param.keyword}" placeholder="Tìm kiếm..."
							aria-label="Search">
						<button class="btn btn-primary btn-search" type="submit"
							style="background-color: #00558D; border: none;">
							<img width="18" height="18"
								src="${pageContext.request.contextPath}/assets/icon/search.png"
								alt="search" style="filter: invert(1);" />
						</button>
					</div>
				</form>


				<!-- Liên kết người dùng -->
				<ul class="navbar-nav mb-2 mb-lg-0 align-items-center user-links">
					<!-- Đăng nhập-->
					<li class="nav-item"><a
						class="btn btn-sm login-btn text-white"
						style="background-color: #00558D;"
						href="${pageContext.request.contextPath}/login">Đăng nhập</a></li>

					<!-- Dấu | -->
					<li class="nav-item px-2 text-primary d-none d-lg-block"
						style="color: #00558D !important;">|</li>

					<!-- Đăng ký -->
					<li class="nav-item fw-semibold register-btn"><a
						class="nav-link text-primary" style="color: #00558D !important;"
						href="${pageContext.request.contextPath}/register">Đăng ký</a></li>

					<!-- Giỏ hàng -->
					<li class="nav-item cart-item position-relative"><a
						class="nav-link"
						href="${pageContext.request.contextPath}/web/cart"> <img
							width="25" height="25"
							src="${pageContext.request.contextPath}/assets/icon/cart.png"
							alt="cart" />
					</a></li>
				</ul>
			</div>
		</div>
	</nav>
</header>
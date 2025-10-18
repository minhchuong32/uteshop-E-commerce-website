<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<header>
	<nav class="navbar navbar-expand-lg navbar-dark fixed-top"
		style="background-color: #FFFFFF">
		<div class="container">
			<!-- Logo -->
			<a class="navbar-brand d-flex align-items-center w-29px"
				href="${pageContext.request.contextPath}/"> <img
				src="${pageContext.request.contextPath}/assets/images/logo_strong.png"
				alt="Logo" height="50" class="me-2">
			</a>
			<!-- Toggle button -->
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarContent"
				aria-controls="navbarContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<!-- Menu -->
			<div class="collapse navbar-collapse" id="navbarContent">

				<!-- Liên kết người dùng -->
				<ul
					class="navbar-nav mb-2 mb-lg-0 align-items-center user-links ms-auto">
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
				</ul>
			</div>
		</div>
	</nav>
</header>
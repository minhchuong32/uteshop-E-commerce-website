<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<header>
	<nav class="navbar navbar-expand-lg fixed-top shadow-sm"
		style="background-color: #ffffff; height: 76px;">
		<div class="container-fluid px-4">

			<!-- Logo + tên hệ thống -->
			<a class="navbar-brand d-flex align-items-center"
				href="${pageContext.request.contextPath}/shipper/home"> <img
				src="${pageContext.request.contextPath}/assets/images/logo_strong.png"
				alt="Logo" height="50" class="me-2">
			</a>

			<!-- Nút toggle cho mobile -->
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarContent"
				aria-controls="navbarContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<i class="bi bi-list"></i>
			</button>

			<!-- Nội dung bên phải -->
			<div class="collapse navbar-collapse" id="navbarContent">
				<ul class="navbar-nav ms-auto mb-2 mb-lg-0 align-items-center">



					<!-- Avatar Admin -->
					<li class="nav-item dropdown"><a
							class="nav-link dropdown-toggle d-flex align-items-center"
							href="#" id="userDropdown" role="button"
							data-bs-toggle="dropdown" aria-expanded="false"> <c:choose>
									<c:when test="${not empty sessionScope.account.avatar}">
										<img
											src="${pageContext.request.contextPath}/assets/images${sessionScope.account.avatar}"
											alt="avatar" class="rounded-circle me-2" width="32"
											height="32" style="object-fit: cover;">
									</c:when>
									<c:otherwise>
										<img
											src="${pageContext.request.contextPath}/assets/images/default.jpg"
											alt="avatar" class="rounded-circle me-2" width="32"
											height="32" style="object-fit: cover;">
									</c:otherwise>
								</c:choose> <span class="fw-semibold text-dark">${sessionScope.account.username}</span>
								<i class="bi bi-caret-down-fill text-secondary small"></i>
						</a>

						<ul class="dropdown-menu shadow dropdown-menu-end"
							aria-labelledby="adminDropdown">
						
							<li><a class="dropdown-item"
								href="${pageContext.request.contextPath}/shipper/home?page=settings">
									<i class="bi bi-gear me-2"></i> Cài đặt
							</a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item text-danger"
								href="${pageContext.request.contextPath}/logout"> <i
									class="bi bi-box-arrow-right me-2"></i> Đăng xuất
							</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
	</nav>
</header>

<style>
/* Ẩn mũi tên dropdown */
.navbar .dropdown-toggle::after {
	display: none;
}

/* Avatar fit */
.nav-item.dropdown img {
	object-fit: cover;
}

/* Fix dropdown bị lệch màn hình */
.dropdown-menu {
	min-width: 200px;
	right: 0;
	left: auto !important;
	transform: translateX(0) !important;
}
</style>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<header>
	<nav class="navbar navbar-expand-lg fixed-top shadow-sm"
		style="background-color: #ffffff; height: 76px;">
		<div class="container-fluid px-4">

			<!-- Logo + tên hệ thống -->
			<a class="navbar-brand d-flex align-items-center"
				href="${pageContext.request.contextPath}/admin/home"> <img
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

					<!-- Nút thông báo -->
					<li class="nav-item dropdown me-3"><a
						class="nav-link position-relative" href="#" id="notifDropdown"
						role="button" data-bs-toggle="dropdown" aria-expanded="false">
							<!-- Chuông màu xanh --> <i class="bi bi-bell fs-5 text-black"></i>
							<!-- Badge xanh --> <span
							class="position-absolute top-0 start-100 translate-middle 
                 badge rounded-pill bg-danger">
								3 </span>
					</a> <!-- Danh sách thông báo -->
						<ul class="dropdown-menu dropdown-menu-end shadow"
							aria-labelledby="notifDropdown"
							style="width: 300px; max-height: 400px; overflow: auto;">
							<li class="dropdown-header fw-semibold">Thông báo</li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item small" href="#">🛒 Đơn hàng
									#1024 vừa được tạo</a></li>
							<li><a class="dropdown-item small" href="#">👤 Người
									dùng mới: Nguyễn Văn A</a></li>
							<li><a class="dropdown-item small" href="#">⚠️ Máy chủ
									báo dung lượng cao</a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item text-center text-primary"
								href="#">Xem tất cả</a></li>
						</ul></li>


					<!-- Avatar Admin -->
					<li class="nav-item dropdown"><a
							class="nav-link dropdown-toggle d-flex align-items-center"
							href="#" id="userDropdown" role="button"
							data-bs-toggle="dropdown" aria-expanded="false"> <c:choose>
									<c:when test="${not empty sessionScope.account.avatar}">
										<img
											src="${pageContext.request.contextPath}/uploads/${sessionScope.account.avatar}"
											alt="avatar" class="rounded-circle me-2" width="32"
											height="32" style="object-fit: cover;">
									</c:when>
									<c:otherwise>
										<img
											src="${pageContext.request.contextPath}/assets/images/default_avatar.png"
											alt="avatar" class="rounded-circle me-2" width="32"
											height="32" style="object-fit: cover;">
									</c:otherwise>
								</c:choose> <span class="fw-semibold text-dark">${sessionScope.account.username}</span>
						</a>

						<ul class="dropdown-menu shadow dropdown-menu-end"
							aria-labelledby="adminDropdown">
						
							<li><a class="dropdown-item"
								href="${pageContext.request.contextPath}/admin/home?page=settings">
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

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<c:if test="${empty sessionScope.account}">
    <script>
        // Nếu chưa đăng nhập, tự động redirect đến /login
        window.location.href = "<c:url value='/login'/>";
    </script>
</c:if>

<header>
	<nav class="navbar navbar-expand-lg navbar-dark fixed-top"
		style="background-color: #FFFFFF;">
		<div class="container">
			<!-- Logo -->
			<a class="navbar-brand d-flex align-items-center"
				href="${pageContext.request.contextPath}/user/home">
				<img src="${pageContext.request.contextPath}/assets/images/logo_strong.png"
					alt="Logo" height="50" class="me-2">
			</a>

			<!-- Toggle cho mobile -->
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
				data-bs-target="#navbarContent" aria-controls="navbarContent"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="navbarContent">

				<!-- Navigation -->
				<ul class="navbar-nav me-3">
					<li class="nav-item"><a
						class="nav-link nav-link-header text-dark fw-semibold"
						href="${pageContext.request.contextPath}/user/home#main">Trang chủ</a></li>
					<li class="nav-item"><a
						class="nav-link nav-link-header text-dark fw-semibold"
						href="${pageContext.request.contextPath}/user/home#product">Sản phẩm</a></li>
					<li class="nav-item"><a
						class="nav-link nav-link-header text-dark fw-semibold"
						href="${pageContext.request.contextPath}/user/home#category">Danh mục</a></li>
					<li class="nav-item"><a
						class="nav-link nav-link-header text-dark fw-semibold"
						href="${pageContext.request.contextPath}/web/contact">Liên hệ</a></li>
				</ul>

				<!-- Thanh tìm kiếm -->
				<form class="d-flex mx-auto me-3 search-form"
					action="${pageContext.request.contextPath}/user/home" method="get"
					style="max-width: 400px; width: 100%;">
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

				

				<!-- Avatar người dùng + giỏ hàng -->
				<ul class="navbar-nav mb-2 mb-lg-0 align-items-center user-links ms-auto">

					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle d-flex align-items-center"
							href="#" id="userDropdown" role="button"
							data-bs-toggle="dropdown" aria-expanded="false">
							<c:choose>
								<c:when test="${not empty sessionScope.account.avatar}">
								  <img src="${pageContext.request.contextPath}/assets/images/${sessionScope.account.avatar}"
										alt="avatar" class="rounded-circle me-2" width="32" height="32"
										style="object-fit: cover;">
								</c:when>
								<c:otherwise>
									<img src="${pageContext.request.contextPath}/assets/images/default.jpg"
										alt="avatar" class="rounded-circle me-2" width="32" height="32"
										style="object-fit: cover;">
								</c:otherwise>
							</c:choose>
							<span class="fw-semibold text-dark">
								${sessionScope.account.username}
							</span>
							
							<i class="bi bi-caret-down-fill text-secondary small"></i>
						</a>

						<ul class="dropdown-menu dropdown-menu-end"
							aria-labelledby="userDropdown">
							<li><a class="dropdown-item"
								href="${pageContext.request.contextPath}/user/profile">
								<i class="bi bi-person-circle me-2"></i> Thông tin cá nhân
							</a></li>
							<li><a class="dropdown-item"
								href="${pageContext.request.contextPath}/user/orders">
								<i class="bi bi-bag-check me-2"></i> Đơn hàng
							</a></li>
							<li><a class="dropdown-item"
								href="${pageContext.request.contextPath}/user/complaints">
								<i class="bi bi-exclamation-diamond me-2"></i> Khiếu nại của tôi
							</a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item text-danger"
								href="${pageContext.request.contextPath}/logout">
								<i class="bi bi-box-arrow-right me-2"></i> Đăng xuất
							</a></li>
						</ul>
					</li>

					<!-- Giỏ hàng -->
					<li class="nav-item cart-item position-relative ms-3">
						<a class="nav-link"
							href="${pageContext.request.contextPath}/user/cart">
							<img width="25" height="25"
								src="${pageContext.request.contextPath}/assets/icon/cart.png"
								alt="cart" />
						</a>
					</li>
				</ul>
				
				<!-- 🔔 Thông báo -->
				<ul class="navbar-nav align-items-center me-3">
					<li class="nav-item dropdown position-relative">
						<a class="nav-link position-relative" href="#" id="notifDropdown"
							role="button" data-bs-toggle="dropdown" aria-expanded="false">
							<i class="bi bi-bell-fill fs-5 text-primary-custom"></i>
							<c:if test="${not empty notifications && fn:length(notifications) > 0}">
								<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger"
									style="font-size: 0.7rem; padding: 3px 6px;">
									${fn:length(notifications)}
								</span>
							</c:if>
						</a>

						<!-- Danh sách thông báo -->
						<ul class="dropdown-menu dropdown-menu-end shadow"
							aria-labelledby="notifDropdown"
							style="width: 320px; max-height: 400px; overflow-y: auto;">
							<li class="dropdown-header fw-semibold">Thông báo</li>
							<li><hr class="dropdown-divider"></li>

							<c:choose>
								<c:when test="${not empty notifications}">
									<c:forEach var="n" items="${notifications}">
										<li>
											<a class="dropdown-item small text-wrap"
												href="${pageContext.request.contextPath}/user/notifications/view?id=${n.id}">
												<div class="d-flex align-items-start">
													<i class="bi bi-chat-dots-fill text-primary me-2 fs-5"></i>
													<div>
														<strong class="text-dark">
															Khiếu nại #${n.relatedComplaint.complaintId}
														</strong><br>
														${n.message}<br>
														<small class="text-muted">
															<fmt:formatDate value="${n.createdAt}" pattern="dd/MM/yyyy HH:mm" />
														</small>
													</div>
												</div>
											</a>
										</li>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<li>
										<p class="dropdown-item text-muted small mb-0">
											Không có thông báo mới.
										</p>
									</li>
								</c:otherwise>
							</c:choose>

							<li><hr class="dropdown-divider"></li>
							<li>
								<a class="dropdown-item text-center text-primary fw-semibold"
								   href="${pageContext.request.contextPath}/user/notifications">
									Xem tất cả
								</a>
							</li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</nav>
</header>

<style>
.navbar .dropdown-toggle::after {
	display: none;
}

.nav-item.dropdown img {
	object-fit: cover;
}

.dropdown-menu {
	min-width: 200px;
	right: 0;
	left: auto !important;
	transform: translateX(0) !important;
}

.dropdown-item small {
	line-height: 1.2;
}
</style>

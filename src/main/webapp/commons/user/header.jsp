<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>
<header>
	<nav class="navbar navbar-expand-lg navbar-dark fixed-top"
		style="background-color: #FFFFFF">
		<div class="container">
			<!-- Logo -->
			<a class="navbar-brand d-flex align-items-center"
				href="${pageContext.request.contextPath}/user/home"> <img
				src="${pageContext.request.contextPath}/assets/images/logo_strong.png"
				alt="Logo" height="50" class="me-2">
			</a>
			<!-- Toggle cho mobile -->
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarContent"
				aria-controls="navbarContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarContent">

				<!-- Navigation -->
				<ul class="navbar-nav me-3">
					<li class="nav-item"><a class="nav-link text-dark fw-semibold"
						href="#main">Trang chủ</a></li>

					<li class="nav-item"><a class="nav-link text-dark fw-semibold"
						href="#product">Sản phẩm</a></li>

					<li class="nav-item"><a class="nav-link text-dark fw-semibold"
						href="#category">Danh mục</a></li>

					<li class="nav-item"><a class="nav-link text-dark fw-semibold"
						href="${pageContext.request.contextPath}/web/contact">Liên hệ</a>
					</li>
				</ul>

				<!-- Thanh tìm kiếm nhỏ gọn -->
				<form class="d-flex mx-auto me-3 search-form"
					action="${pageContext.request.contextPath}/user/home" method="get"
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



				<!-- Người dùng + Giỏ hàng -->
				<ul
					class="navbar-nav mb-2 mb-lg-0 align-items-center user-links ms-auto">

					<c:if test="${not empty sessionScope.account}">
						<!-- Avatar + Username -->
						<li class="nav-item dropdown"><a
							class="nav-link dropdown-toggle d-flex align-items-center"
							href="#" id="userDropdown" role="button"
							data-bs-toggle="dropdown" aria-expanded="false"> <img
								src="<c:choose>
                        <c:when test='${not empty sessionScope.account.avatar}'>
                          ${pageContext.request.contextPath}/uploads/${sessionScope.account.avatar}
                        </c:when>
                        <c:otherwise>
                        ${pageContext.request.contextPath}/assets/images/default_avatar.png}
                        </c:otherwise>
                      </c:choose>"
								alt="avatar" class="rounded-circle me-2" width="32" height="32"
								style="object-fit: cover;"> <span
								class="fw-semibold text-dark">${sessionScope.account.username}</span>
						</a>
							<ul class="dropdown-menu dropdown-menu-end"
								aria-labelledby="userDropdown">
								<li><a class="dropdown-item"
									href="${pageContext.request.contextPath}/user/profile"> <i
										class="bi bi-person-circle me-2"></i> Thông tin cá nhân
								</a></li>
								<li><a class="dropdown-item"
									href="${pageContext.request.contextPath}/user/orders"> <i
										class="bi bi-bag-check me-2"></i> Đơn hàng
								</a></li>
								<li><hr class="dropdown-divider"></li>
								<li><a class="dropdown-item"
									href="${pageContext.request.contextPath}/logout"> <i
										class="bi bi-box-arrow-right me-2"></i> Đăng xuất
								</a></li>
							</ul></li>
					</c:if>

					<!-- Nếu chưa đăng nhập -->
					<c:if test="${empty sessionScope.account}">
						<li class="nav-item"><a
							class="nav-link text-dark fw-semibold"
							href="${pageContext.request.contextPath}/login">Đăng nhập</a></li>
					</c:if>

					<!-- Giỏ hàng -->
					<li class="nav-item cart-item position-relative ms-3"><a
						class="nav-link"
						href="${pageContext.request.contextPath}/user/cart"> <img
							width="25" height="25"
							src="${pageContext.request.contextPath}/assets/icon/cart.png"
							alt="cart" />
					</a></li>
				</ul>

			</div>
		</div>
	</nav>
</header>
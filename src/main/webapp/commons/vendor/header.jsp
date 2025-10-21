<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<header>
	<nav class="navbar navbar-expand-lg fixed-top shadow-sm"
		style="background-color: #ffffff; height: 76px;">
		<div class="container-fluid px-4">

			<!-- Logo + tên hệ thống -->
			<a class="navbar-brand d-flex align-items-center"
				href="${pageContext.request.contextPath}/vendor/home"> <img
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

					<!-- 🔔 Thông báo -->
					<li class="nav-item dropdown me-3">
					    <a class="nav-link position-relative" href="#" id="notifDropdown"
					       role="button" data-bs-toggle="dropdown" aria-expanded="false">
					        <i class="bi bi-bell fs-5 text-black"></i>
					
					        <!-- Badge đỏ hiển thị số thông báo chưa đọc -->
					        <c:if test="${not empty notifications}">
					            <c:set var="unreadCount" value="0" />
					            <c:forEach var="n" items="${notifications}">
					                <c:if test="${!n.read}">
					                    <c:set var="unreadCount" value="${unreadCount + 1}" />
					                </c:if>
					            </c:forEach>
					            <c:if test="${unreadCount > 0}">
					                <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger"
					                      style="font-size: 0.7rem; padding: 3px 6px;">
					                    ${unreadCount}
					                </span>
					            </c:if>
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
					                <c:forEach var="n" items="${notifications}" varStatus="loop">
					                    <c:if test="${loop.index < 5}">
					                        <li>
					                            <a class="dropdown-item small text-wrap"
					                               href="${pageContext.request.contextPath}/vendor/notifications/view?id=${n.id}">
					                                <div class="d-flex align-items-start">
					                                    <div>
					                                        <span class="${n.read ? 'text-muted' : 'fw-semibold text-dark'}">${n.message}</span><br>
					                                        <small class="text-muted">
					                                            <fmt:formatDate value="${n.createdAt}" pattern="dd/MM/yyyy HH:mm" />
					                                        </small>
					                                    </div>
					                                </div>
					                            </a>
					                        </li>
					                    </c:if>
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
					               href="${pageContext.request.contextPath}/vendor/notifications">
					                Xem tất cả
					            </a>
					        </li>
					    </ul>
					</li>
					
					<!-- Avatar Admin -->
					<li class="nav-item dropdown"><a
							class="nav-link dropdown-toggle d-flex align-items-center"
							href="#" id="userDropdown" role="button"
							data-bs-toggle="dropdown" aria-expanded="false"> <c:choose>
									<c:when test="${not empty account.avatar}">
										  <img src="${pageContext.request.contextPath}/assets/images${account.avatar}"
											alt="avatar" class="rounded-circle me-2" width="32"
											height="32" style="object-fit: cover;">
									</c:when>
									<c:otherwise>
										<img
											src="${pageContext.request.contextPath}/assets/images/default.jpg"
											alt="avatar" class="rounded-circle me-2" width="32"
											height="32" style="object-fit: cover;">
									</c:otherwise>
								</c:choose> <span class="fw-semibold text-dark">${account.username}</span>
								<i class="bi bi-caret-down-fill text-secondary small"></i>
						</a>

						<ul class="dropdown-menu shadow dropdown-menu-end"
							aria-labelledby="adminDropdown">
						
							<li><a class="dropdown-item"
								href="${pageContext.request.contextPath}/vendor/home?page=settings">
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

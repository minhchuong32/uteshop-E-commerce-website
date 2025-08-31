<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Uteshop | Trang Admin</title>

    <!-- Favicon -->
    <link rel="icon" type="image/png"
          href="${pageContext.request.contextPath}/assets/images/favicon.png">
    <!-- Css -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/admin.css">
    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100">

<!-- Header -->
<%@ include file="/WEB-INF/views/admin/header.jsp" %>

<!-- Body -->
<div class="container-fluid flex-grow-1">
    <div class="row">
        <!-- Sidebar -->
        <nav class="col-12 col-md-2 col-xl-2 shadow-sm pt-4 position-sticky top-0 vh-100 rounded-end"
             style="height: calc(100vh - 76px); background-color: #00558D; margin-top: 76px">
         <h5 class="text-center mb-4 text-white border-bottom border-white pb-3">Quản trị</h5>

            <ul class="nav flex-column">
                <li class="nav-item mb-1 ">
                    <a class="nav-link d-flex align-items-center rounded p-2 ${param.page == null || param.page=='dashboard' ? 'active' : ''}"
                          href="${pageContext.request.contextPath}/admin/home?page=dashboard">
                        <i class="bi bi-speedometer2 me-2"></i> Dashboard
                    </a>
                </li>
                <li class="nav-item mb-1">
                    <a class="nav-link d-flex align-items-center rounded p-2 ${param.page=='users' ? 'active' : ''}"
                      href="${pageContext.request.contextPath}/admin/home?page=users">
                        <i class="bi bi-people-fill me-2"></i> Quản lý Users
                    </a>
                </li>
                <li class="nav-item mb-1">
                    <a class="nav-link d-flex align-items-center rounded p-2 ${param.page=='products' ? 'active' : ''}"
                       href="${pageContext.request.contextPath}/admin/home?page=products">
                        <i class="bi bi-box-seam me-2"></i> Quản lý Sản phẩm
                    </a>
                </li>
                <li class="nav-item mb-1">
                    <a class="nav-link d-flex align-items-center rounded p-2 ${param.page=='orders' ? 'active' : ''}"
                       href="${pageContext.request.contextPath}/admin/home?page=orders">
                        <i class="bi bi-cart-check-fill me-2"></i> Quản lý Đơn hàng
                    </a>
                </li>
                <li class="nav-item mb-1">
                    <a class="nav-link d-flex align-items-center rounded p-2 ${param.page=='stats' ? 'active' : ''}"
                        href="${pageContext.request.contextPath}/admin/home?page=stats">
                        <i class="bi bi-bar-chart-line me-2"></i> Thống kê
                    </a>
                </li>
                <li class="nav-item mb-1">
                    <a class="nav-link d-flex align-items-center rounded p-2 ${param.page=='settings' ? 'active' : ''}"
                        href="${pageContext.request.contextPath}/admin/home?page=settings">
                        <i class="bi bi-gear-fill me-2"></i> Cài đặt
                    </a>
                </li>
            </ul>
        </nav>

        <!-- Main -->
        <main class="col-12 col-md-10 col-xl-10 p-4" style="margin-top: 76px;">
            <c:choose>
                <c:when test="${param.page=='users'}">
                    <jsp:include page="users.jsp"/>
                </c:when>
                <c:when test="${param.page=='products'}">
                    <jsp:include page="products.jsp"/>
                </c:when>
                <c:when test="${param.page=='orders'}">
                    <jsp:include page="orders.jsp"/>
                </c:when>
                <c:when test="${param.page=='stats'}">
                    <jsp:include page="stats.jsp"/>
                </c:when>
                <c:when test="${param.page=='settings'}">
                    <jsp:include page="settings.jsp"/>
                </c:when>
                <c:otherwise>
                    <jsp:include page="dashboard.jsp"/>
                </c:otherwise>
            </c:choose>
        </main>
    </div>
</div>
</body>
</html>

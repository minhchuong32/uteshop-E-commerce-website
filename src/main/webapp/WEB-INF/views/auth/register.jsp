<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Uteshop | Đăng ký</title>

<!-- Bootstrap -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Header -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/header.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

<!-- Favicon -->
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/images/favicon.png">

<style>
html, body {
  height: 100%;
  margin: 0;
}
body {
  display: flex;
  flex-direction: column;
  background: linear-gradient(to bottom left, #00558D, #ffffff);
}
.main-content {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}
.register-card {
  max-width: 460px;
  width: 100%;
  padding: 2rem;
  border-radius: 1rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  background: #fff;
}
</style>
</head>
<body>
    <!-- Header -->
    <header>
        <nav class="navbar navbar-expand-lg navbar-dark fixed-top" style="background-color: #FFFFFF">
            <div class="container">
                <!-- Logo -->
                <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/">
                    <img src="${pageContext.request.contextPath}/assets/images/logo_strong.png" alt="Logo" height="50" class="me-2">
                </a>
                <!-- Toggle button -->
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent"
                    aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <!-- Menu -->
                <div class="collapse navbar-collapse" id="navbarContent">
                    <ul class="navbar-nav mb-2 mb-lg-0 align-items-center user-links ms-auto">
                        <li class="nav-item">
                            <a class="btn btn-sm login-btn text-white" style="background-color: #00558D;"
                               href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                        </li>                   
                    </ul>
                </div>
            </div>
        </nav>
    </header>

    <!-- Main content -->
    <div class="main-content mt-5 py-4">
        <div class="register-card" style="margin-top: 80px;">
            <!-- Title -->
            <div class="d-flex align-items-center justify-content-between mb-3">
                <h3 class="m-0 fw-bold" style="color: #00558D;">Đăng Ký</h3>
                <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/">
                    <img src="${pageContext.request.contextPath}/assets/images/logo_cart_auth.png" alt="Logo" height="50" class="ms-2">
                </a>
            </div>

            <!-- Register Form -->
            <form action="register" method="post">
            <c:if test="${alert !=null}">
				<h3 class="alert alertdanger">${alert}</h3>
			</c:if>
                <!-- Username -->
                <div class="mb-3">
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-person"></i></span>
                        <input type="text" class="form-control" id="fullname" name="username" placeholder="Tên đăng nhập" required>
                    </div>
                </div>
                <!-- Email -->
                <div class="mb-3">
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                        <input type="email" class="form-control" id="email" name="email" placeholder="Email" required>
                    </div>
                </div>
                <!-- Password -->
                <div class="mb-3">
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-lock"></i></span>
                        <input type="password" class="form-control" id="password" name="password" placeholder="Mật khẩu" required>
                    </div>
                </div>
                <!-- Confirm Password -->
                <div class="mb-3">
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-shield-lock"></i></span>
                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="Xác nhận mật khẩu" required>
                    </div>
                </div>
                <!-- Submit -->
                <button type="submit" class="btn w-100" style="background-color: #00558D; color: white;">Đăng ký</button>
            </form>

            <!-- Login link -->
            <div class="text-center mt-3">
                <span class="text-muted">Đã có tài khoản? </span>
                <a href="${pageContext.request.contextPath}/login" class="fw-semibold">Đăng nhập</a>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <%@ include file="/WEB-INF/views/layout/footer.jsp" %>

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

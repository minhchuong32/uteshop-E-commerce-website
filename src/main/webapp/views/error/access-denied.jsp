<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ute.shop.entity.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Truy cập bị từ chối</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="d-flex flex-column justify-content-center align-items-center vh-100 bg-light">

    <%
        // Lấy thông tin người dùng từ session (nếu có)
        User account = (User) session.getAttribute("account");
        String redirectUrl = request.getContextPath() + "/login"; // mặc định nếu chưa login

        if (account != null) {
            String role = account.getRole();
            switch (role) {
                case "Admin":
                    redirectUrl = request.getContextPath() + "/admin/home";
                    break;
                case "Vendor":
                    redirectUrl = request.getContextPath() + "/vendor/home";
                    break;
                case "User":
                    redirectUrl = request.getContextPath() + "/user/home";
                    break;
                case "Shipper":
                    redirectUrl = request.getContextPath() + "/shipper/home";
                    break;
                default:
                    redirectUrl = request.getContextPath() + "/login";
                    break;
            }
        }
    %>

    <div class="text-center shadow-sm p-5 bg-white rounded-4 border">
        <h1 class="text-danger fw-bold mb-3">
            <i class="bi bi-shield-lock-fill"></i> Truy cập bị từ chối
        </h1>
        <p class="text-muted mb-4">Bạn không có quyền truy cập vào khu vực này.</p>

        <a href="<%= redirectUrl %>" class="btn btn-primary rounded-pill px-4">
            <i class="bi bi-house-door-fill me-2"></i> Về trang chủ
        </a>
    </div>

</body>
</html>

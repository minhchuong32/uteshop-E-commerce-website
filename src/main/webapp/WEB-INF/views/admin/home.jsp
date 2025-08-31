<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Uteshop | Trang Admin</title>

<!-- Favicon -->
<link rel="icon" type="image/png"
	href="${pageContext.request.contextPath}/assets/images/favicon.png">

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">

<!-- CSS riêng -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
</head>
<body>

<!-- Header -->
<%@ include file="/WEB-INF/views/admin/header.jsp" %>

<!-- Sidebar + Main content -->
<div class="d-flex">
    <!-- Sidebar -->
    <div class="sidebar">
        <h4 class="text-center mb-4">Quản trị</h4>
        <a href="#">Dashboard</a>
        <a href="#">Quản lý Users</a>
        <a href="#">Quản lý Sản phẩm</a>
        <a href="#">Quản lý Đơn hàng</a>
        <a href="#">Thống kê</a>
        <a href="#">Cài đặt</a>
    </div>

    <!-- Main content -->
    <div class="main flex-grow-1 p-4">
        <!-- Thống kê nhanh -->
        <div class="row g-3 mb-4">
            <div class="col-md-3">
                <div class="stat-card text-center">
                    <i class="bi bi-people-fill fs-2 mb-2 text-primary"></i>
                    <h5>Users</h5>
                    <p class="mb-0">150</p>
                </div>
            </div>
            <div class="col-md-3">
                <div class="stat-card text-center">
                    <i class="bi bi-box-seam fs-2 mb-2 text-success"></i>
                    <h5>Sản phẩm</h5>
                    <p class="mb-0">320</p>
                </div>
            </div>
            <div class="col-md-3">
                <div class="stat-card text-center">
                    <i class="bi bi-cart-check-fill fs-2 mb-2 text-warning"></i>
                    <h5>Đơn hàng</h5>
                    <p class="mb-0">75</p>
                </div>
            </div>
            <div class="col-md-3">
                <div class="stat-card text-center">
                    <i class="bi bi-cash-stack fs-2 mb-2 text-danger"></i>
                    <h5>Doanh thu</h5>
                    <p class="mb-0">₫125,000,000</p>
                </div>
            </div>
        </div>

        <!-- Quản lý sản phẩm -->
        <h4 class="mb-3">Quản lý Sản phẩm</h4>
        <div class="row g-3">
            <!-- Product card -->
            <div class="col-12 col-sm-6 col-lg-4">
                <div class="card product-card">
                    <img src="${pageContext.request.contextPath}/assets/images/balo.png" class="card-img-top" alt="Balo">
                    <div class="card-body">
                        <h5 class="card-title">Balo</h5>
                        <p class="card-text">₫599,000</p>
                        <div class="d-flex gap-2">
                            <button class="btn btn-sm btn-primary w-50">Edit</button>
                            <button class="btn btn-sm btn-danger w-50">Delete</button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Thêm các product card khác tương tự -->
        </div>
    </div>
</div>


<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h5 class="mb-3">Thống kê nhanh</h5>
<div class="row g-3 mb-4">
    <div class="col-md-3">
        <div class="card text-center shadow-sm">
            <div class="card-body">
                <i class="bi bi-people-fill fs-2 mb-2 text-primary"></i>
                <h6 class="card-title">Users</h6>
                <p class="mb-0">150</p>
            </div>
        </div>
    </div>
    <div class="col-md-3">
			<div class="card text-center shadow-sm">
				<div class="card-body">
					<i class="bi bi-box-seam fs-2 mb-2 text-success"></i>
					<h6 class="card-title">Sản phẩm</h6>
					<p class="mb-0">320</p>
				</div>
			</div>
		</div>
		<div class="col-md-3">
			<div class="card text-center shadow-sm">
				<div class="card-body">
					<i class="bi bi-cart-check-fill fs-2 mb-2 text-warning"></i>
					<h6 class="card-title">Đơn hàng</h6>
					<p class="mb-0">75</p>
				</div>
			</div>
		</div>
		<div class="col-md-3">
			<div class="card text-center shadow-sm">
				<div class="card-body">
					<i class="bi bi-cash-stack fs-2 mb-2 text-danger"></i>
					<h6 class="card-title">Doanh thu</h6>
					<p class="mb-0">₫125,000,000</p>
				</div>
			</div>
		</div>
    <!-- Thêm các card khác -->
</div>
<div class="row g-3 mb-4">
    <!-- Đơn hàng gần đây -->
    <div class="col-md-6">
        <div class="card shadow-sm h-100">
            <div class="card-body">
                <h5 class="card-title mb-3">Đơn hàng gần đây</h5>

                <div class="d-flex justify-content-between align-items-center border-bottom py-2">
                    <div>
                        <strong>Đơn hàng #ORD-001</strong><br>
                        <small class="text-muted">15/01/2024</small>
                    </div>
                    <div class="text-end">
                        <span class="fw-bold">₫7,200,000</span><br>
                        <span class="text-success">Đã giao</span>
                    </div>
                </div>

                <div class="d-flex justify-content-between align-items-center border-bottom py-2">
                    <div>
                        <strong>Đơn hàng #ORD-002</strong><br>
                        <small class="text-muted">20/01/2024</small>
                    </div>
                    <div class="text-end">
                        <span class="fw-bold">₫1,400,000</span><br>
                        <span class="text-primary">Đang giao</span>
                    </div>
                </div>

                <!-- Thêm các đơn hàng khác -->
            </div>
        </div>
    </div>

  <!-- Sản phẩm bán chạy -->
<div class="col-md-6">
    <div class="card shadow-sm h-100">
        <div class="card-body">
            <h5 class="card-title mb-3">Sản phẩm bán chạy</h5>

            <div class="d-flex justify-content-between align-items-center border-bottom py-2">
                <div class="d-flex align-items-center">
                    <img src="${pageContext.request.contextPath}/assets/images/tainghe.png" 
                         class="rounded me-2" alt="Sản phẩm"
                         style="width: 50px; height: 50px; object-fit: cover;">
                    <div>
                        <strong>Tai nghe không dây cao cấp</strong><br>
                        <small class="text-muted">124 đánh giá</small>
                    </div>
                </div>
                <div class="text-end">
                    <span class="fw-bold">₫7,200,000</span><br>
                    <span class="text-muted">4.8/5</span>
                </div>
            </div>

            <div class="d-flex justify-content-between align-items-center border-bottom py-2">
                <div class="d-flex align-items-center">
                    <img src="${pageContext.request.contextPath}/assets/images/giay.png" 
                         class="rounded me-2" alt="Sản phẩm"
                         style="width: 50px; height: 50px; object-fit: cover;">
                    <div>
                        <strong>Giày thể thao</strong><br>
                        <small class="text-muted">89 đánh giá</small>
                    </div>
                </div>
                <div class="text-end">
                    <span class="fw-bold">₫690,000</span><br>
                    <span class="text-muted">4.5/5</span>
                </div>
            </div>

            <!-- Thêm các sản phẩm khác -->
        </div>
    </div>
</div>

</div>

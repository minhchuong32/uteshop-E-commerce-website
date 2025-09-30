<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h5 class="mb-3">Thống kê nhanh - Shipper</h5>
<div class="row g-3 mb-4">
    <!-- Tổng đơn được gán -->
    <div class="col-md-3">
        <div class="card text-center shadow-sm">
            <div class="card-body">
                <i class="bi bi-truck fs-2 mb-2 text-primary"></i>
                <h6 class="card-title">Đơn được gán</h6>
                <p class="mb-0">${stats.assignedCount}</p>
            </div>
        </div>
    </div>

    <!-- Đơn đang giao -->
    <div class="col-md-3">
        <div class="card text-center shadow-sm">
            <div class="card-body">
                <i class="bi bi-box-seam fs-2 mb-2 text-warning"></i>
                <h6 class="card-title">Đơn đang giao</h6>
                <p class="mb-0">${stats.deliveringCount}</p>
            </div>
        </div>
    </div>

    <!-- Đơn đã giao -->
    <div class="col-md-3">
        <div class="card text-center shadow-sm">
            <div class="card-body">
                <i class="bi bi-check2-circle fs-2 mb-2 text-success"></i>
                <h6 class="card-title">Đơn đã giao</h6>
                <p class="mb-0">${stats.deliveredCount}</p>
            </div>
        </div>
    </div>

    <!-- Đơn bị hủy / trả -->
    <div class="col-md-3">
        <div class="card text-center shadow-sm">
            <div class="card-body">
                <i class="bi bi-x-circle fs-2 mb-2 text-danger"></i>
                <h6 class="card-title">Đơn hủy / trả</h6>
                <p class="mb-0">${stats.canceledCount}</p>
            </div>
        </div>
    </div>
</div>

<div class="row g-3 mb-4">
    <!-- Đơn hàng gần đây -->
    <div class="col-md-12">
        <div class="card shadow-sm h-100">
            <div class="card-body">
                <h5 class="card-title mb-3">Đơn hàng gần đây</h5>

                <c:forEach var="d" items="${recentDeliveries}">
                    <div class="d-flex justify-content-between align-items-center border-bottom py-2">
                        <div>
                            <strong>Đơn hàng #${d.order.orderId}</strong><br>
                            <small class="text-muted">
                                ${d.order.createdAt}
                            </small>
                        </div>
                        <div class="text-end">
                            <span class="fw-bold">₫${d.order.totalAmount}</span><br>
                            <span class="
                                <c:choose>
                                    <c:when test="${d.status eq 'Đang giao'}">text-primary</c:when>
                                    <c:when test="${d.status eq 'Đã giao'}">text-success</c:when>
                                    <c:when test="${d.status eq 'Hủy'}">text-danger</c:when>
                                    <c:otherwise>text-secondary</c:otherwise>
                                </c:choose>
                            ">
                                ${d.status}
                            </span>
                        </div>
                    </div>
                </c:forEach>

            </div>
        </div>
    </div>
</div>

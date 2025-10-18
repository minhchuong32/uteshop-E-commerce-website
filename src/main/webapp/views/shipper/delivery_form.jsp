<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/commons/taglib.jsp" %>

<div class="container py-5">
    <h3 class="mb-4 text-center text-primary-custom fw-bold">
        <i class="bi bi-truck me-2"></i>Tạo phiếu giao hàng
    </h3>

    <div class="card shadow-sm mx-auto" style="max-width: 600px;">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/shipper/delivery/form" method="post">
                <input type="hidden" name="deliveryId" value="${delivery.deliveryId}" />

                <!-- Tên người nhận -->
                <div class="mb-3">
                    <label class="form-label fw-semibold">Tên người nhận</label>
                    <input type="text" name="receiverName"
                           class="form-control"
                           value="${delivery.order.user.name}"
                           placeholder="Nhập tên người nhận" required>
                </div>

                <!-- Số điện thoại -->
                <div class="mb-3">
                    <label class="form-label fw-semibold">Số điện thoại</label>
                    <input type="text" name="phone"
                           class="form-control"
                           value="${delivery.order.user.phone}"
                           placeholder="Nhập số điện thoại" required>
                </div>

                <!-- Ghi chú -->
                <div class="mb-3">
                    <label class="form-label fw-semibold">Ghi chú</label>
                    <textarea name="note" class="form-control" rows="3"
                              placeholder="Ghi chú thêm (nếu có)">${delivery.noteText}</textarea>
                </div>

                <div class="text-center mt-4">
                    <button type="submit" class="btn btn-primary me-2">
                        <i class="bi bi-file-earmark-pdf-fill"></i> Xuất phiếu PDF
                    </button>
                    <a href="${pageContext.request.contextPath}/shipper/orders" class="btn btn-outline-secondary">
                        <i class="bi bi-arrow-left"></i> Quay lại
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>


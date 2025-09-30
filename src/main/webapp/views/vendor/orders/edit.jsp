<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">
    <h3 class="mb-4 fw-bold">Chỉnh sửa đơn hàng</h3>

    <div class="card shadow-sm">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/vendor/orders/edit" method="post">
                <!-- Order ID -->
                <input type="hidden" name="id" value="${order.orderId}">

                <!-- User -->
                <div class="mb-3">
                    <label class="form-label fw-bold">Người dùng</label>
                    <input type="text" class="form-control"
                           value="ID: ${order.user.userId} - ${order.user.username}"
                           readonly>
                </div>

                <!-- Total Amount -->
                <div class="mb-3">
                    <label class="form-label fw-bold">Tổng tiền</label>
                    <input type="number" step="0.01" class="form-control"
                           name="total_amount" value="${order.totalAmount}" required>
                </div>

                <!-- Status -->
                <div class="mb-3">
                    <label class="form-label fw-bold">Trạng thái</label>
                    <select name="status" class="form-select">
                        <option value="new"        ${order.status == 'new' ? 'selected' : ''}>Mới</option>
                        <option value="processing" ${order.status == 'processing' ? 'selected' : ''}>Đang xử lý</option>
                        <option value="completed"  ${order.status == 'completed' ? 'selected' : ''}>Hoàn tất</option>
                        <option value="cancelled"  ${order.status == 'cancelled' ? 'selected' : ''}>Đã hủy</option>
                    </select>
                </div>

                <!-- Payment Method -->
                <div class="mb-3">
                    <label class="form-label fw-bold">Phương thức thanh toán</label>
                    <select name="payment_method" class="form-select">
                        <option value="COD"  ${order.paymentMethod == 'COD' ? 'selected' : ''}>COD</option>
                        <option value="BANK" ${order.paymentMethod == 'BANK' ? 'selected' : ''}>Chuyển khoản</option>
                        <option value="CARD" ${order.paymentMethod == 'CARD' ? 'selected' : ''}>Thẻ</option>
                    </select>
                </div>

                <!-- Buttons -->
                <div class="d-flex justify-content-between">
                    <a href="${pageContext.request.contextPath}/vendor/orders"
                       class="btn btn-secondary px-4">Hủy</a>
                    <button type="submit" class="btn btn-primary px-4">Cập nhật</button>
                </div>
            </form>
        </div>
    </div>
</div>

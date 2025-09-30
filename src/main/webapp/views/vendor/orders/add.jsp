<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">
    <h3 class="mb-4 fw-bold">Thêm đơn hàng</h3>

    <div class="card shadow-sm">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/vendor/orders/add" method="post">

                <!-- User ID -->
                <div class="mb-3">
                    <label class="form-label fw-bold">User ID</label>
                    <input type="number" class="form-control" name="user_id" required>
                </div>

                <!-- Total Amount -->
                <div class="mb-3">
                    <label class="form-label fw-bold">Tổng tiền</label>
                    <input type="number" step="0.01" class="form-control" name="total_amount" required>
                </div>

                <!-- Status -->
                <div class="mb-3">
                    <label class="form-label fw-bold">Trạng thái</label>
                    <select name="status" class="form-select">
                        <option value="new">Mới</option>
                        <option value="processing">Đang xử lý</option>
                        <option value="completed">Hoàn tất</option>
                        <option value="cancelled">Đã hủy</option>
                    </select>
                </div>

                <!-- Payment Method -->
                <div class="mb-3">
                    <label class="form-label fw-bold">Phương thức thanh toán</label>
                    <select name="payment_method" class="form-select">
                        <option value="COD">COD</option>
                        <option value="BANK">Chuyển khoản</option>
                        <option value="CARD">Thẻ</option>
                    </select>
                </div>

                <!-- Buttons -->
                <div class="d-flex justify-content-between">
                    <a href="${pageContext.request.contextPath}/vendor/orders"
                       class="btn btn-secondary px-4">Hủy</a>
                    <button type="submit" class="btn btn-success px-4">Lưu</button>
                </div>
            </form>
        </div>
    </div>
</div>

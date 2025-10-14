<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglib.jsp" %>

<div class="container py-5" style="max-width: 700px;">
    <h3 class="mb-4 fw-semibold text-center text-primary-custom">Gửi Khiếu Nại</h3>

    <div class="card shadow-sm border-0 rounded-4 p-4">
        <form action="${pageContext.request.contextPath}/user/complaints/add" method="post" enctype="multipart/form-data">
            <input type="hidden" name="orderId" value="${order.orderId}">

            <div class="mb-3">
                <label class="form-label fw-semibold">Đơn hàng:</label>
                <input type="text" class="form-control" value="#${order.orderId}" readonly>
            </div>

            <div class="mb-3">
                <label for="title" class="form-label fw-semibold">Tiêu đề khiếu nại:</label>
                <input type="text" id="title" name="title" class="form-control" placeholder="Nhập tiêu đề..." required>
            </div>

            <div class="mb-3">
                <label for="content" class="form-label fw-semibold">Nội dung chi tiết:</label>
                <textarea id="content" name="content" class="form-control" rows="5"
                          placeholder="Mô tả chi tiết vấn đề bạn gặp phải..." required></textarea>
            </div>

            <div class="mb-3">
                <label for="attachment" class="form-label fw-semibold">Minh chứng (ảnh hoặc file):</label>
                <input type="file" id="attachment" name="attachment" class="form-control" accept="image/*,.pdf">
                <small class="text-muted">Tùy chọn. Bạn có thể đính kèm ảnh sản phẩm lỗi hoặc hóa đơn.</small>
            </div>

            <div class="text-center mt-4">
                <button type="submit" class="btn btn-primary-custom rounded-pill px-4">
                    <i class="bi bi-send-fill me-2"></i> Gửi Khiếu Nại
                </button>
                <a href="${pageContext.request.contextPath}/user/orders"
                   class="btn btn-outline-secondary rounded-pill px-4 ms-2">
                    <i class="bi bi-arrow-left-circle me-2"></i> Quay lại
                </a>
            </div>
        </form>
    </div>
</div>

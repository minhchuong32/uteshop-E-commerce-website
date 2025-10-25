<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container mt-5" style="max-width: 700px;">
    <div class="card shadow-sm">
        <div class="card-header bg-primary text-white text-center fw-bold">
            🏪 Bạn chưa có cửa hàng - Đăng ký cửa hàng của bạn
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/vendor/register-shop" 
                  method="post" enctype="multipart/form-data">

                <div class="mb-3">
                    <label class="form-label fw-semibold">Tên cửa hàng <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" name="name" required placeholder="Nhập tên cửa hàng">
                </div>

                <div class="mb-3">
                    <label class="form-label fw-semibold">Mô tả cửa hàng</label>
                    <textarea class="form-control" name="description" rows="4"
                              placeholder="Giới thiệu ngắn gọn về cửa hàng của bạn..."></textarea>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-semibold">Logo cửa hàng</label>
                    <input type="file" class="form-control" name="logo" accept="image/*">
                </div>

                <div class="text-center">
                    <button type="submit" class="btn btn-success px-4">Đăng ký</button>
                    <a href="${pageContext.request.contextPath}/user/home" class="btn btn-secondary px-4">Hủy</a>
                </div>
            </form>
        </div>
    </div>
</div>

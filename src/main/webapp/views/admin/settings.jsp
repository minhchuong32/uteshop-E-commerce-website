<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container-fluid">
    <h3 class="mb-4 fw-bold">Cài đặt hệ thống</h3>

    <!-- Thông tin cửa hàng -->
    <div class="card shadow-sm mb-4">
        <div class="card-header fw-bold bg-light">Thông tin cửa hàng</div>
        <div class="card-body">
            <form>
                <div class="mb-3">
                    <label class="form-label">Tên cửa hàng</label>
                    <input type="text" class="form-control" name="store_name" value="UteShop">
                </div>
                <div class="mb-3">
                    <label class="form-label">Email liên hệ</label>
                    <input type="email" class="form-control" name="email" value="support@uteshop.com">
                </div>
                <div class="mb-3">
                    <label class="form-label">Hotline</label>
                    <input type="text" class="form-control" name="hotline" value="0123 456 789">
                </div>
                <div class="mb-3">
                    <label class="form-label">Địa chỉ</label>
                    <textarea class="form-control" name="address" rows="2">123 Nguyễn Văn Bảo, Q. Gò Vấp, TP.HCM</textarea>
                </div>
                <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
            </form>
        </div>
    </div>

    <!-- Cấu hình thanh toán -->
    <div class="card shadow-sm mb-4">
        <div class="card-header fw-bold bg-light">Cấu hình thanh toán</div>
        <div class="card-body">
            <form>
                <div class="form-check form-switch mb-2">
                    <input class="form-check-input" type="checkbox" id="cod" checked>
                    <label class="form-check-label" for="cod">Thanh toán khi nhận hàng (COD)</label>
                </div>
                <div class="form-check form-switch mb-2">
                    <input class="form-check-input" type="checkbox" id="momo" checked>
                    <label class="form-check-label" for="momo">Thanh toán qua MoMo</label>
                </div>
                <div class="form-check form-switch mb-2">
                    <input class="form-check-input" type="checkbox" id="vnpay">
                    <label class="form-check-label" for="vnpay">Thanh toán qua VNPAY</label>
                </div>
                <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
            </form>
        </div>
    </div>

    <!-- Cấu hình giao diện -->
    <div class="card shadow-sm mb-4">
        <div class="card-header fw-bold bg-light">Cấu hình giao diện</div>
        <div class="card-body">
            <form>
                <div class="mb-3">
                    <label class="form-label">Chọn theme</label>
                    <select class="form-select" name="theme">
                        <option value="default" selected>Mặc định</option>
                        <option value="dark">Tối</option>
                        <option value="light">Sáng</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Logo</label>
                    <input type="file" class="form-control" name="logo">
                </div>
                <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
            </form>
        </div>
    </div>

    <!-- Thông tin cá nhân -->
    <h3 class="mb-4 fw-bold">Thông tin cá nhân</h3>

    <div class="card shadow-sm mb-4">
        <div class="card-header fw-bold bg-light">Cập nhật thông tin Admin</div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/admin/profile/update" method="post">
                <div class="mb-3">
                    <label class="form-label">User ID</label>
                    <input type="text" class="form-control" value="${sessionScope.account.user_id}" readonly style="opacity:0.5;">
                </div>
                <div class="mb-3">
                    <label class="form-label">Username</label>
                    <input type="text" class="form-control" name="username" value="${sessionScope.account.username}" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="email" class="form-control" name="email" value="${sessionScope.account.email}" required>
                </div>
                <div class="mb-3 position-relative">
                    <label class="form-label">Password (hash)</label>
                    <div class="input-group">
                        <input type="text" id="passwordField" class="form-control" value="${sessionScope.account.password}" readonly>
                        <button class="btn btn-outline-secondary" type="button" id="togglePassword">
                            <i class="bi bi-eye-fill"></i>
                        </button>
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Đổi mật khẩu</label>
                    <input type="password" name="newPassword" class="form-control" placeholder="Để trống nếu không đổi">
                </div>
                <div class="mb-3">
                    <label class="form-label">Role</label>
                    <input type="text" class="form-control" value="${sessionScope.account.role}" readonly style="opacity:0.5;">
                </div>
                <div class="mb-3">
                    <label class="form-label">Trạng thái</label>
                    <input type="text" class="form-control" value="${sessionScope.account.status}" readonly style="opacity:0.5;">
                </div>
                <div class="mb-3">
                    <label class="form-label">Ngày tạo</label>
                    <input type="text" class="form-control" value="${sessionScope.account.createDate}" readonly style="opacity:0.5;">
                </div>

                <button type="submit" class="btn btn-primary">Cập nhật thông tin</button>
            </form>
        </div>
    </div>
</div>

<!-- Toast container góc phải -->
<div aria-live="polite" aria-atomic="true" class="position-relative">
    <div class="toast-container position-fixed top-0 end-0 p-3">
        <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="toast-header">
                <strong class="me-auto" id="toastTitle">Thông báo</strong>
                <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
            <div class="toast-body" id="toastBody"></div>
        </div>
    </div>
</div>

<!-- JS hiển thị toast -->
<c:if test="${not empty message}">
    <script>
        const toastEl = document.getElementById('liveToast');
        const toastTitle = document.getElementById('toastTitle');
        const toastBody = document.getElementById('toastBody');

        const message = "${message}";
        const type = "${messageType}"; // success / error

        toastTitle.innerHTML = type === 'success' ? "✅ Thành công" : "❌ Lỗi";
        toastBody.innerHTML = message;

        const toast = new bootstrap.Toast(toastEl, { delay: 3000 });
        toast.show();

        // Toggle hiển thị password hash
        const toggleBtn = document.getElementById('togglePassword');
        const passwordField = document.getElementById('passwordField');
        toggleBtn.addEventListener('click', function() {
            if (passwordField.type === 'password') {
                passwordField.type = 'text';
                toggleBtn.innerHTML = '<i class="bi bi-eye-slash-fill"></i>';
            } else {
                passwordField.type = 'password';
                toggleBtn.innerHTML = '<i class="bi bi-eye-fill"></i>';
            }
        });
    </script>
</c:if>


		<script>
    const toggleBtn = document.getElementById('togglePassword');
    const passwordField = document.getElementById('passwordField');

    toggleBtn.addEventListener('click', function() {
        if (passwordField.type === 'password') {
            passwordField.type = 'text';
            toggleBtn.innerHTML = '<i class="bi bi-eye-slash-fill"></i>';
        } else {
            passwordField.type = 'password';
            toggleBtn.innerHTML = '<i class="bi bi-eye-fill"></i>';
        }
    });
</script>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">
    <h3 class="mb-4 fw-bold text-primary-custom">Thêm người dùng</h3>

    <form action="${pageContext.request.contextPath}/admin/users/add"
          method="post" enctype="multipart/form-data"
          class="card shadow-sm p-4 border-0">

        <!-- Tên thật -->
        <div class="mb-3">
            <label class="form-label fw-semibold">Tên hiển thị</label>
            <input type="text" name="name" class="form-control" placeholder="Ví dụ: Nguyễn Văn A" required />
        </div>

        <!-- Tên đăng nhập -->
        <div class="mb-3">
            <label class="form-label fw-semibold">Tên đăng nhập</label>
            <input type="text" name="username" class="form-control" required />
        </div>

        <!-- Email -->
        <div class="mb-3">
            <label class="form-label fw-semibold">Email</label>
            <input type="email" name="email" class="form-control" required />
        </div>

        <!-- Mật khẩu -->
        <div class="mb-3">
            <label class="form-label fw-semibold">Mật khẩu</label>
            <input type="password" name="password" class="form-control" required />
        </div>

        <!-- Số điện thoại -->
        <div class="mb-3">
            <label class="form-label fw-semibold">Số điện thoại</label>
            <input type="text" name="phone" class="form-control" placeholder="Ví dụ: 0901234567" />
        </div>

        <!-- Địa chỉ -->
        <div class="mb-3">
            <label class="form-label fw-semibold">Địa chỉ</label>
            <input type="text" name="address" class="form-control" placeholder="Nhập địa chỉ cư trú" />
        </div>

        <!-- Ảnh đại diện -->
        <div class="mb-3">
            <label class="form-label fw-semibold">Ảnh đại diện</label>
            <input type="file" name="avatar" class="form-control" accept="image/*" onchange="previewImage(event)" />
            <div class="mt-2">
                <img id="avatarPreview" src="${pageContext.request.contextPath}/assets/images/default-avatar.png"
                     class="rounded-circle border" width="80" height="80" style="object-fit:cover;">
            </div>
        </div>

        <script>
        function previewImage(event) {
            const reader = new FileReader();
            reader.onload = function(){
                document.getElementById('avatarPreview').src = reader.result;
            };
            reader.readAsDataURL(event.target.files[0]);
        }
        </script>

        <!-- Vai trò -->
        <div class="mb-3">
            <label class="form-label fw-semibold">Vai trò</label>
            <select name="role" class="form-select">
                <option value="User">User</option>
                <option value="Admin">Admin</option>
                <option value="Vendor">Vendor</option>
                <option value="Shipper">Shipper</option>
            </select>
        </div>

        <div class="d-flex justify-content-between mt-4">
            <a href="${pageContext.request.contextPath}/admin/users"
               class="btn btn-outline-primary-custom rounded-pill px-4">Quay lại</a>
            <button type="submit" class="btn btn-primary-custom rounded-pill px-4">Thêm mới</button>
        </div>
    </form>
</div>

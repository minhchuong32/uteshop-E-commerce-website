<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">
    <h3 class="mb-4 fw-bold">Thêm người dùng</h3>

    <form action="${pageContext.request.contextPath}/admin/users/add" method="post" class="card shadow-sm p-4">
        <div class="mb-3">
            <label class="form-label">Tên đăng nhập</label>
            <input type="text" name="username" class="form-control" required />
        </div>

        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" name="email" class="form-control" required />
        </div>

        <div class="mb-3">
            <label class="form-label">Mật khẩu</label>
            <input type="password" name="password" class="form-control" required />
        </div>

        <div class="mb-3">
            <label class="form-label">Vai trò</label>
            <select name="role" class="form-select">
                <option value="User">User</option>
                <option value="Admin">Admin</option>
                <option value="Vendor">Vendor</option>
                <option value="Shipper">Shipper</option>
            </select>
        </div>

        <div class="d-flex justify-content-between">
            <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-secondary">Quay lại</a>
            <button type="submit" class="btn btn-success">Thêm mới</button>
        </div>
    </form>
</div>

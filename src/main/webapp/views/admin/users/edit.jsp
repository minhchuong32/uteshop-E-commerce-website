<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">
	<h3 class="mb-4 fw-bold">Chỉnh sửa người dùng</h3>

	<form action="${pageContext.request.contextPath}/admin/users/edit"
		method="post" class="card shadow-sm p-4">
		<input type="hidden" name="id" value="${user.userId}" />

		<div class="mb-3">
			<label class="form-label">Tên đăng nhập</label> <input type="text"
				name="username" value="${user.username}" class="form-control"
				required />
		</div>

		<div class="mb-3">
			<label class="form-label">Email</label> <input type="email"
				name="email" value="${user.email}" class="form-control" required />
		</div>

		<div class="mb-3 position-relative">
			<label class="form-label">Password (hash)</label>
			<div class="input-group">
				<input type="text" id="passwordField" class="form-control"
					value="${user.password}" readonly>
				<button class="btn btn-outline-secondary" type="button"
					id="togglePassword">
					<i class="bi bi-eye-fill"></i>
				</button>
			</div>
		</div>

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
		<div class="mb-3">
			<label class="form-label">Vai trò</label> <select name="role"
				class="form-select">
				<option value="User" ${user.role eq 'User' ? 'selected' : ''}>User</option>
				<option value="Admin" ${user.role eq 'Admin' ? 'selected' : ''}>Admin</option>
				<option value="Vendor" ${user.role eq 'Vendor' ? 'selected' : ''}>Vendor</option>
				<option value="Shipper" ${user.role eq 'Shipper' ? 'selected' : ''}>Shipper</option>
			</select>
		</div>

		<div class="mb-3">
			<label class="form-label">Trạng thái</label> <select name="status"
				class="form-select">
				<option value="active" ${user.status eq 'active' ? 'selected' : ''}>Active</option>
				<option value="banned" ${user.status eq 'banned' ? 'selected' : ''}>Banned</option>
			</select>
		</div>

		<div class="d-flex justify-content-between">
			<a href="${pageContext.request.contextPath}/admin/users"
				class="btn btn-secondary">Quay lại</a>
			<button type="submit" class="btn btn-primary">Cập nhật</button>
		</div>
	</form>
</div>

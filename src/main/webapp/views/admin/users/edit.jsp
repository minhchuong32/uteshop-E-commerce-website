<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">
	<!-- Hiển thị thông báo lỗi -->
	<c:if test="${not empty error}">
		<div
			class="alert alert-danger alert-dismissible fade show mt-3 shadow-sm"
			role="alert">
			<i class="bi bi-exclamation-triangle-fill me-2"></i> ${error}
			<button type="button" class="btn-close" data-bs-dismiss="alert"
				aria-label="Đóng"></button>
		</div>
	</c:if>
	<h3 class="mb-4 fw-bold text-primary-custom">Chỉnh sửa người dùng</h3>

	<form action="${pageContext.request.contextPath}/admin/users/edit"
		method="post" enctype="multipart/form-data"
		class="card shadow-sm p-4 border-0">

		<input type="hidden" name="id" value="${user.userId}" />

		<!-- Ảnh đại diện -->
		<div class="text-center mb-4">
			<img id="avatarPreview"
				src="${empty user.avatar 
        ? pageContext.request.contextPath.concat('/assets/images/avatars/default.jpg')
        : pageContext.request.contextPath.concat('/assets/images/avatars/').concat(user.avatar.substring(user.avatar.lastIndexOf('/') + 1))}"
				class="rounded-circle border mb-2" width="100" height="100"
				style="object-fit: cover;">
			<div>
				<input type="file" name="avatar"
					class="form-control mt-2 w-auto mx-auto" accept="image/*"
					onchange="previewImage(event)">
			</div>
		</div>

		<script>
            function previewImage(event) {
                const reader = new FileReader();
                reader.onload = function() {
                    document.getElementById('avatarPreview').src = reader.result;
                };
                reader.readAsDataURL(event.target.files[0]);
            }
        </script>

		<!-- Tên hiển thị -->
		<div class="mb-3">
			<label class="form-label fw-semibold">Tên hiển thị</label> <input
				type="text" name="name" value="${user.name}" class="form-control" />
		</div>

		<!-- Username -->
		<div class="mb-3">
			<label class="form-label fw-semibold">Tên đăng nhập</label> <input
				type="text" name="username" value="${user.username}"
				class="form-control" required />
		</div>

		<!-- Email -->
		<div class="mb-3">
			<label class="form-label fw-semibold">Email</label> <input
				type="email" name="email" value="${user.email}" class="form-control"
				required />
		</div>

		<!-- Số điện thoại -->
		<div class="mb-3">
			<label class="form-label fw-semibold">Số điện thoại</label> <input
				type="text" name="phone" value="${user.phone}" class="form-control" />
		</div>

		<!-- Địa chỉ -->
		<div class="mb-3">
			<label class="form-label fw-semibold">Địa chỉ</label> <input
				type="text" name="address" value="${user.address}"
				class="form-control" />
		</div>

		<!-- Vai trò -->
		<div class="mb-3">
			<label class="form-label fw-semibold">Vai trò</label> <select
				name="role" class="form-select">
				<option value="User" ${user.role eq 'User' ? 'selected' : ''}>User</option>
				<option value="Admin" ${user.role eq 'Admin' ? 'selected' : ''}>Admin</option>
				<option value="Vendor" ${user.role eq 'Vendor' ? 'selected' : ''}>Vendor</option>
				<option value="Shipper" ${user.role eq 'Shipper' ? 'selected' : ''}>Shipper</option>
			</select>
		</div>

		<!-- Trạng thái -->
		<div class="mb-3">
			<label class="form-label fw-semibold">Trạng thái</label> <select
				name="status" class="form-select">
				<option value="active" ${user.status eq 'active' ? 'selected' : ''}>Hoạt
					động</option>
				<option value="banned" ${user.status eq 'banned' ? 'selected' : ''}>Bị
					khóa</option>
			</select>
		</div>

		<div class="d-flex justify-content-between mt-4">
			<a href="${pageContext.request.contextPath}/admin/users"
				class="btn btn-outline-primary-custom rounded-pill px-4">Quay
				lại</a>
			<button type="submit"
				class="btn btn-primary-custom rounded-pill px-4">Cập nhật</button>
		</div>
	</form>
</div>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid px-4">
	<c:if test="${not empty error}">
		<div
			class="alert alert-danger alert-dismissible fade show mb-3 shadow-sm"
			role="alert">
			<i class="bi bi-exclamation-triangle-fill me-2"></i> ${error}
			<button type="button" class="btn-close" data-bs-dismiss="alert"
				aria-label="Đóng"></button>
		</div>
	</c:if>

	<div class="card shadow-sm border-0">
		<div class="card-header bg-primary-custom">
			<h4 class="mb-0 text-white">
				<i class="bi bi-person-plus-fill me-2"></i>Thêm người dùng mới
			</h4>
		</div>

		<div class="card-body p-4">
			<form action="${pageContext.request.contextPath}/admin/users/add"
				method="post" enctype="multipart/form-data">
				<div class="row">
					<div class="col-lg-4 text-center">
						<label class="form-label fw-bold d-block mb-2">Ảnh đại
							diện</label> <img id="avatarPreview"
							src="${pageContext.request.contextPath}/assets/images/avatars/default.jpg"
							class="rounded-circle border mb-3" width="150" height="150"
							style="object-fit: cover; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);">

						<div>
							<label for="avatarInput" class="btn btn-outline-secondary">
								<i class="bi bi-upload me-1"></i> Chọn ảnh
							</label> <input type="file" name="avatar" id="avatarInput" class="d-none"
								accept="image/*" onchange="previewImage(event)">
						</div>
					</div>

					<div class="col-lg-8">
						<div class="row">
							<div class="col-md-6 mb-3">
								<label for="username" class="form-label fw-bold">Tên
									đăng nhập</label> <input type="text" id="username" name="username"
									class="form-control" value="<c:out value='${user.username}' />"
									required />
							</div>
							<div class="col-md-6 mb-3">
								<label for="name" class="form-label fw-bold">Tên hiển
									thị</label> <input type="text" id="name" name="name"
									class="form-control" value="<c:out value='${user.name}' />"
									placeholder="Ví dụ: Nguyễn Văn A" required />

							</div>
						</div>
						<div class="row">
							<div class="col-md-6 mb-3">
								<label for="email" class="form-label fw-bold">Email</label> <input
									type="email" id="email" name="email" class="form-control"
									value="<c:out value='${user.email}' />" required />
							</div>
							<div class="col-md-6 mb-3">
								<label for="password" class="form-label fw-bold">Mật
									khẩu</label> <input type="password" id="password" name="password"
									class="form-control" required />
							</div>
						</div>
						<div class="mb-3">
							<label for="phone" class="form-label fw-bold">Số điện
								thoại</label> <input type="text" id="phone" name="phone"
								class="form-control" placeholder="Ví dụ: 0901234567" />
						</div>
						<div class="mb-3">
							<label for="address" class="form-label fw-bold">Địa chỉ</label> <input
								type="text" id="address" name="address" class="form-control"
								placeholder="Nhập địa chỉ cư trú" />
						</div>
						<div class="mb-3">
							<label for="role" class="form-label fw-bold">Vai trò</label> <select
								id="role" name="role" class="form-select">
								<option value="User" ${user.role == 'User' ? 'selected' : ''}>User</option>
								<option value="Admin" ${user.role == 'Admin' ? 'selected' : ''}>Admin</option>
								<option value="Vendor"
									${user.role == 'Vendor' ? 'selected' : ''}>Vendor</option>
								<option value="Shipper"
									${user.role == 'Shipper' ? 'selected' : ''}>Shipper</option>
							</select>
						</div>
					</div>
				</div>

				<hr class="hr-primary mt-4" style="opacity: 0.2;">

				<div class="d-flex justify-content-end gap-2">
					<a href="${pageContext.request.contextPath}/admin/users"
						class="btn btn-outline-secondary"> <i
						class="bi bi-arrow-left me-1"></i> Quay lại
					</a>
					<button type="submit" class="btn btn-primary-custom">
						<i class="bi bi-plus-lg me-1"></i> Thêm người dùng
					</button>
				</div>
			</form>
		</div>
	</div>
</div>

<script>
	function previewImage(event) {
		const input = event.target;
		if (input.files && input.files[0]) {
			const reader = new FileReader();
			reader.onload = function(e) {
				document.getElementById('avatarPreview').src = e.target.result;
			};
			reader.readAsDataURL(input.files[0]);
		}
	}
</script>
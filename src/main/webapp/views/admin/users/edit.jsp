<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid px-4">
	<c:if test="${not empty error}">
		<div class="alert alert-danger alert-dismissible fade show mb-3 shadow-sm" role="alert">
			<i class="bi bi-exclamation-triangle-fill me-2"></i> ${error}
			<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Đóng"></button>
		</div>
	</c:if>

	<div class="card shadow-sm border-0">
		<div class="card-header bg-primary-custom">
			<h4 class="mb-0 text-white">Chỉnh Sửa Thông Tin Người Dùng</h4>
		</div>

		<div class="card-body p-4">
			<form action="${pageContext.request.contextPath}/admin/users/edit" method="post" enctype="multipart/form-data">
				<input type="hidden" name="id" value="${user.userId}" />

				<div class="row">
					<div class="col-lg-4 col-md-5 text-center mb-4 mb-md-0">
						<label class="form-label fw-bold">Ảnh đại diện</label>
						<div class="mt-2">
							<img id="avatarPreview"
								src="${empty user.avatar 
                                    ? pageContext.request.contextPath.concat('/assets/images/avatars/default.jpg')
                                    : pageContext.request.contextPath.concat('/assets/images/avatars/').concat(user.avatar.substring(user.avatar.lastIndexOf('/') + 1))}"
								class="rounded-circle border mb-3" width="150" height="150" style="object-fit: cover; box-shadow: 0 4px 8px rgba(0,0,0,0.1);">
							
							<label for="avatarInput" class="btn btn-sm btn-outline-secondary">
								<i class="bi bi-upload me-1"></i> Chọn ảnh mới
							</label>
							<input type="file" name="avatar" id="avatarInput" class="d-none" accept="image/*" onchange="previewImage(event)">
						</div>
					</div>

					<div class="col-lg-8 col-md-7">
						<div class="row">
							<div class="col-md-6 mb-3">
								<label class="form-label fw-bold">Tên đăng nhập</label>
								<input type="text" name="username" value="${user.username}" class="form-control" required />
							</div>
							<div class="col-md-6 mb-3">
								<label class="form-label fw-bold">Tên hiển thị</label>
								<input type="text" name="name" value="${user.name}" class="form-control" />
							</div>
						</div>

						<div class="mb-3">
							<label class="form-label fw-bold">Email</label>
							<input type="email" name="email" value="${user.email}" class="form-control" required />
						</div>

						<div class="mb-3">
							<label class="form-label fw-bold">Số điện thoại</label>
							<input type="text" name="phone" value="${user.phone}" class="form-control" />
						</div>

						<div class="mb-3">
							<label class="form-label fw-bold">Địa chỉ</label>
							<input type="text" name="address" value="${user.address}" class="form-control" />
						</div>
						
						<div class="row">
							<div class="col-md-6 mb-3">
								<label class="form-label fw-bold">Vai trò</label>
								<select name="role" class="form-select">
									<option value="User" ${user.role eq 'User' ? 'selected' : ''}>User</option>
									<option value="Admin" ${user.role eq 'Admin' ? 'selected' : ''}>Admin</option>
									<option value="Vendor" ${user.role eq 'Vendor' ? 'selected' : ''}>Vendor</option>
									<option value="Shipper" ${user.role eq 'Shipper' ? 'selected' : ''}>Shipper</option>
								</select>
							</div>
							<div class="col-md-6 mb-3">
								<label class="form-label fw-bold">Trạng thái</label>
								<select name="status" class="form-select">
									<option value="active" ${user.status eq 'active' ? 'selected' : ''}>Hoạt động</option>
									<option value="banned" ${user.status eq 'banned' ? 'selected' : ''}>Bị khóa</option>
								</select>
							</div>
						</div>
					</div>
				</div>

				<hr class="hr-primary mt-3" style="opacity: 0.2;">

				<div class="d-flex justify-content-end gap-2 mt-3">
					<a href="${pageContext.request.contextPath}/admin/users" class="btn btn-outline-secondary">
						<i class="bi bi-arrow-left me-1"></i> Quay lại
					</a>
					<button type="submit" class="btn btn-primary-custom">
						<i class="bi bi-check-lg me-1"></i> Cập nhật
					</button>
				</div>
			</form>
		</div>
	</div>
</div>

<script>
    function previewImage(event) {
        const reader = new FileReader();
        const output = document.getElementById('avatarPreview');
        reader.onload = function() {
            output.src = reader.result;
        };
        if (event.target.files[0]) {
            reader.readAsDataURL(event.target.files[0]);
        }
    }
</script>
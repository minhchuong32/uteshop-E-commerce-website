<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid px-4">
	<div class="card shadow-sm border-0">
		<div class="card-header bg-primary-custom">
			<h4 class="mb-0 text-white"><i class="bi bi-shop-window me-2"></i>Thêm cửa hàng mới</h4>
		</div>

		<div class="card-body p-4">
			<form action="${pageContext.request.contextPath}/admin/shops/add" method="post" enctype="multipart/form-data">
				<div class="row">
					<div class="col-lg-4 text-center">
						<label class="form-label fw-bold d-block mb-2">Logo cửa hàng</label>
						<img id="logoPreview"
							 src="${pageContext.request.contextPath}/assets/images/shops/default-shop-logo.png"
							 alt="Logo Preview" class="rounded border mb-3"
							 style="width: 150px; height: 150px; object-fit: cover; box-shadow: 0 4px 8px rgba(0,0,0,0.1);">
						
						<div>
							<label for="logoInput" class="btn btn-outline-secondary">
								<i class="bi bi-upload me-1"></i> Chọn logo
							</label>
							<input type="file" class="d-none" id="logoInput" name="logo" accept="image/*">
						</div>
					</div>

					<div class="col-lg-8">
						<div class="mb-3">
							<label for="name" class="form-label fw-bold">Tên cửa hàng</label>
							<input type="text" class="form-control" id="name" name="name" placeholder="Nhập tên cửa hàng" required>
						</div>

						<div class="mb-3">
							<label for="user_id" class="form-label fw-bold">Chủ sở hữu (Vendor)</label>
							<select class="form-select" id="user_id" name="user_id" required>
								<option value="" selected disabled>-- Chọn chủ sở hữu --</option>
								<c:forEach var="v" items="${vendors}">
									<option value="${v.userId}">ID: ${v.userId} - ${empty v.name ? v.username : v.name}</option>
								</c:forEach>
							</select>
							<small class="form-text text-muted">Chỉ những người dùng có vai trò "Vendor" mới được hiển thị.</small>
						</div>

						<div class="mb-3">
							<label for="description" class="form-label fw-bold">Mô tả cửa hàng</label>
							<textarea class="form-control" id="description" name="description" rows="4" placeholder="Nhập mô tả chi tiết..." required></textarea>
						</div>
					</div>
				</div>

				<hr class="hr-primary mt-4" style="opacity: 0.2;">

				<div class="d-flex justify-content-end gap-2">
					<a href="${pageContext.request.contextPath}/admin/shops" class="btn btn-outline-secondary">
						<i class="bi bi-x-lg me-1"></i> Hủy
					</a>
					<button type="submit" class="btn btn-primary-custom">
						<i class="bi bi-plus-lg me-1"></i> Thêm cửa hàng
					</button>
				</div>
			</form>
		</div>
	</div>
</div>
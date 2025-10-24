<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid px-4">
	<div class="card shadow-sm border-0">
		<div class="card-header bg-primary-custom">
			<h4 class="mb-0 text-white"><i class="bi bi-shop me-2"></i>Chỉnh sửa thông tin cửa hàng</h4>
		</div>

		<div class="card-body p-4">
			<form action="${pageContext.request.contextPath}/admin/shops/edit" method="post" enctype="multipart/form-data">

				<input type="hidden" name="id" value="${shop.shopId}">

				<div class="row">
					<div class="col-lg-4 text-center">
						<label class="form-label fw-bold d-block mb-2">Logo cửa hàng</label>
						<img id="logoPreview"
							 src="${empty shop.logo 
								? pageContext.request.contextPath.concat('/assets/images/shops/default-shop-logo.png')
								: pageContext.request.contextPath.concat('/assets/images/shops/').concat(shop.logo.substring(shop.logo.lastIndexOf('/') + 1))}"
							 alt="Logo hiện tại" class="rounded border mb-3"
							 style="width: 150px; height: 150px; object-fit: cover; box-shadow: 0 4px 8px rgba(0,0,0,0.1);">
						
						<div>
							<label for="logoInput" class="btn btn-outline-secondary">
								<i class="bi bi-upload me-1"></i> Thay đổi logo
							</label>
							<input type="file" class="d-none" id="logoInput" name="logo" accept="image/*">
						</div>
					</div>

					<div class="col-lg-8">
						<div class="mb-3">
							<label for="name" class="form-label fw-bold">Tên cửa hàng</label>
							<input type="text" class="form-control" id="name" name="name" value="${shop.name}" required>
						</div>

						<div class="mb-3">
							<label for="description" class="form-label fw-bold">Mô tả</label>
							<textarea class="form-control" id="description" name="description" rows="5" required>${shop.description}</textarea>
						</div>

						<div class="mb-3">
							<label class="form-label fw-bold">Chủ sở hữu</label>
							<div class="form-control-plaintext ps-2">ID: ${shop.user.userId} - ${shop.user.username}</div>
						</div>

						<div class="mb-3">
							<label class="form-label fw-bold">Ngày tạo</label>
							<div class="form-control-plaintext ps-2"><fmt:formatDate value='${shop.createdAt}' pattern='dd/MM/yyyy HH:mm'/></div>
						</div>
					</div>
				</div>

				<hr class="hr-primary mt-4" style="opacity: 0.2;">

				<div class="d-flex justify-content-end gap-2">
					<a href="${pageContext.request.contextPath}/admin/shops" class="btn btn-outline-secondary">
						<i class="bi bi-x-lg me-1"></i> Hủy
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
document.getElementById('logoInput').addEventListener('change', function(e) {
    const file = e.target.files[0];
    if (file) {
        const preview = document.getElementById('logoPreview');
        preview.src = URL.createObjectURL(file);
        // Giải phóng bộ nhớ khi không cần URL nữa
        preview.onload = function() {
            URL.revokeObjectURL(preview.src) 
        }
    }
});
</script>
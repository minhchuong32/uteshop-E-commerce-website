<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid px-4">
	<div class="card shadow-sm border-0">
		<div class="card-header bg-primary-custom">
			<h4 class="mb-0 text-white"><i class="bi bi-plus-circle-fill me-2"></i>Thêm danh mục mới</h4>
		</div>

		<div class="card-body p-4">
			<form action="${pageContext.request.contextPath}/admin/categories/add" method="post" enctype="multipart/form-data">

				<div class="mb-4">
					<label for="categoryName" class="form-label fw-bold">Tên danh mục</label>
					<input type="text" id="categoryName" name="name" class="form-control" placeholder="Nhập tên danh mục..." required>
				</div>

				<div class="text-center">
					<label class="form-label fw-bold d-block mb-2">Hình ảnh</label>
					<img id="imagePreview"
						 src="${pageContext.request.contextPath}/assets/images/categories/default-category.jpg"
						 class="rounded border mb-3" width="200" height="200" style="object-fit: cover; box-shadow: 0 4px 8px rgba(0,0,0,0.1);">
					
					<div>
						<label for="imageInput" class="btn btn-outline-secondary">
							<i class="bi bi-upload me-1"></i> Chọn ảnh
						</label>
						<input type="file" name="image" id="imageInput" class="d-none" accept="image/*" onchange="previewImage(event)">
					</div>
				</div>

				<hr class="hr-primary mt-4" style="opacity: 0.2;">

				<div class="d-flex justify-content-end gap-2">
					<a href="${pageContext.request.contextPath}/admin/categories" class="btn btn-outline-secondary">
						<i class="bi bi-arrow-left me-1"></i> Quay lại
					</a>
					<button type="submit" class="btn btn-primary-custom">
						<i class="bi bi-plus-lg me-1"></i> Thêm mới
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
				document.getElementById('imagePreview').src = e.target.result;
			};
			reader.readAsDataURL(input.files[0]);
		}
	}
</script>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid px-4">
	<div class="card shadow-sm border-0">
		<div class="card-header bg-primary-custom">
			<h4 class="mb-0 text-white"><i class="bi bi-tags-fill me-2"></i>Chỉnh sửa danh mục</h4>
		</div>

		<div class="card-body p-4">
			<form action="${pageContext.request.contextPath}/admin/categories/edit" method="post" enctype="multipart/form-data">

				<input type="hidden" name="id" value="${category.categoryId}" />

				<div class="text-center mb-4">
					<label class="form-label fw-bold d-block mb-2">Hình ảnh</label>
					<img id="imagePreview"
						 src="${empty category.image 
							 ? pageContext.request.contextPath.concat('/assets/images/categories/default-category.jpg') 
							 : pageContext.request.contextPath.concat('/assets').concat(category.image)}"
						 class="rounded border mb-3" width="200" height="200" style="object-fit: cover; box-shadow: 0 4px 8px rgba(0,0,0,0.1);">
					
					<div>
						<label for="imageInput" class="btn btn-outline-secondary">
							<i class="bi bi-upload me-1"></i> Thay đổi ảnh
						</label>
						<input type="file" name="image" id="imageInput" class="d-none" accept="image/*" onchange="previewImage(event)">
					</div>
				</div>

				<div class="mb-3">
					<label for="categoryName" class="form-label fw-bold">Tên danh mục</label>
					<input type="text" id="categoryName" name="name" value="${category.name}" class="form-control" required>
				</div>
				
				<hr class="hr-primary mt-4" style="opacity: 0.2;">

				<div class="d-flex justify-content-end gap-2">
					<a href="${pageContext.request.contextPath}/admin/categories" class="btn btn-outline-secondary">
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
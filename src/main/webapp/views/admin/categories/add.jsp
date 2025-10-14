<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">
	<h3 class="mb-4 fw-bold text-primary-custom">Thêm danh mục</h3>

	<form action="${pageContext.request.contextPath}/admin/categories/add"
		  method="post" enctype="multipart/form-data"
		  class="card shadow-sm p-4 border-0">

		<!-- Tên danh mục -->
		<div class="mb-3">
			<label class="form-label fw-semibold">Tên danh mục</label>
			<input type="text" name="name" class="form-control" placeholder="Nhập tên danh mục..." required>
		</div>

		<!-- Hình ảnh -->
		<div class="mb-3 text-center">
			<label class="form-label fw-semibold d-block">Hình ảnh danh mục</label>
			<input type="file" name="image" class="form-control w-auto mx-auto" accept="image/*" onchange="previewImage(event)">
			<div class="mt-3">
				<img id="imagePreview"
					 src="${pageContext.request.contextPath}/assets/images/categories/default-category.jpg"
					 class="rounded border" width="200" height="200" style="object-fit: cover;">
			</div>
		</div>

		<script>
		function previewImage(event) {
			const reader = new FileReader();
			reader.onload = function() {
				document.getElementById('imagePreview').src = reader.result;
			};
			reader.readAsDataURL(event.target.files[0]);
		}
		</script>

		<div class="d-flex justify-content-between mt-4">
			<a href="${pageContext.request.contextPath}/admin/categories"
			   class="btn btn-outline-primary-custom rounded-pill px-4">Quay lại</a>
			<button type="submit" class="btn btn-primary-custom rounded-pill px-4">Thêm mới</button>
		</div>
	</form>
</div>

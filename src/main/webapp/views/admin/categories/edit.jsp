<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">
	<h3 class="mb-4 fw-bold text-primary-custom">Chỉnh sửa danh mục</h3>

	<form action="${pageContext.request.contextPath}/admin/categories/edit"
		  method="post" enctype="multipart/form-data"
		  class="card shadow-sm p-4 border-0">

		<input type="hidden" name="id" value="${category.categoryId}"/>

		<!-- Hình ảnh -->
		<div class="text-center mb-4">
			<img id="imagePreview"
				 src="${empty category.image 
					 ? pageContext.request.contextPath.concat('/assets/images/categories/default-category.jpg') 
					 : pageContext.request.contextPath.concat('/assets').concat(category.image)}"
				 class="rounded border mb-2" width="200" height="200" style="object-fit: cover;">
			<div>
				<input type="file" name="image" class="form-control mt-2 w-auto mx-auto" accept="image/*" onchange="previewImage(event)">
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

		<!-- Tên danh mục -->
		<div class="mb-3">
			<label class="form-label fw-semibold">Tên danh mục</label>
			<input type="text" name="name" value="${category.name}" class="form-control" required>
		</div>

		<div class="d-flex justify-content-between mt-4">
			<a href="${pageContext.request.contextPath}/admin/categories"
			   class="btn btn-outline-primary-custom rounded-pill px-4">Quay lại</a>
			<button type="submit" class="btn btn-primary-custom rounded-pill px-4">Cập nhật</button>
		</div>
	</form>
</div>

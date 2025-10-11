<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">
	<h3 class="mb-4 fw-bold text-primary-custom">Thêm sản phẩm</h3>

	<form action="${pageContext.request.contextPath}/admin/products/add"
		method="post" enctype="multipart/form-data"
		class="card shadow-sm p-4 border-0">

		<!-- Tên sản phẩm -->
		<div class="mb-3">
			<label class="form-label fw-semibold">Tên sản phẩm</label>
			<input type="text" name="name" class="form-control" 
				   placeholder="Ví dụ: iPhone 15 Pro Max" required />
		</div>

		<!-- Mô tả -->
		<div class="mb-3">
			<label class="form-label fw-semibold">Mô tả sản phẩm</label>
			<textarea name="description" class="form-control" rows="4" 
					  placeholder="Nhập mô tả chi tiết về sản phẩm..."></textarea>
		</div>

		<!-- Danh mục -->
		<div class="mb-3">
			<label class="form-label fw-semibold">Danh mục</label>
			<select name="categoryId" class="form-select" required>
				<option value="">-- Chọn danh mục --</option>
				<c:forEach var="cat" items="${categories}">
					<option value="${cat.categoryId}">${cat.name}</option>
				</c:forEach>
			</select>
		</div>

		<!-- Cửa hàng -->
		<div class="mb-3">
			<label class="form-label fw-semibold">Cửa hàng</label>
			<select name="shopId" class="form-select" required>
				<option value="">-- Chọn cửa hàng --</option>
				<c:forEach var="shop" items="${shops}">
					<option value="${shop.shopId}">${shop.name}</option>
				</c:forEach>
			</select>
		</div>

		<!-- Hình ảnh sản phẩm -->
		<div class="mb-3">
			<label class="form-label fw-semibold">Hình ảnh sản phẩm</label>
			<input type="file" name="imageUrl" class="form-control" 
				   accept="image/*" onchange="previewImage(event)" />
			<div class="mt-3 text-center">
				<img id="imagePreview" 
					 src="${pageContext.request.contextPath}/assets/images/default-product.png"
					 class="rounded border" width="200" height="200" 
					 style="object-fit:cover;">
			</div>
		</div>

		<script>
		function previewImage(event) {
			const reader = new FileReader();
			reader.onload = function(){
				document.getElementById('imagePreview').src = reader.result;
			};
			reader.readAsDataURL(event.target.files[0]);
		}
		</script>

		<div class="d-flex justify-content-between mt-4">
			<a href="${pageContext.request.contextPath}/admin/products"
				class="btn btn-outline-primary-custom rounded-pill px-4">Quay lại</a>
			<button type="submit" class="btn btn-primary-custom rounded-pill px-4">Thêm mới</button>
		</div>
	</form>
</div>
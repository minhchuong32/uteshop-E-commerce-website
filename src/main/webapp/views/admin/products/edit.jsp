<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">
	<h3 class="mb-4 fw-bold text-primary-custom">Chỉnh sửa sản phẩm</h3>

	<form action="${pageContext.request.contextPath}/admin/products/edit"
		method="post" enctype="multipart/form-data"
		class="card shadow-sm p-4 border-0">

		<input type="hidden" name="id" value="${product.productId}" />

		<!-- Hình ảnh sản phẩm -->
		<div class="text-center mb-4">
			<img id="imagePreview"
				src="${empty product.imageUrl ? pageContext.request.contextPath.concat('/assets/images/default-product.png') 
				: pageContext.request.contextPath.concat('/assets/images/products/').concat(product.imageUrl)}"
				class="rounded border mb-2" width="200" height="200"
				style="object-fit: cover;">
			<div>
				<input type="file" name="imageUrl" class="form-control mt-2 w-auto mx-auto"
					accept="image/*" onchange="previewImage(event)">
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

		<!-- Tên sản phẩm -->
		<div class="mb-3">
			<label class="form-label fw-semibold">Tên sản phẩm</label>
			<input type="text" name="name" value="${product.name}" 
				   class="form-control" required />
		</div>

		<!-- Mô tả -->
		<div class="mb-3">
			<label class="form-label fw-semibold">Mô tả sản phẩm</label>
			<textarea name="description" class="form-control" rows="4">${product.description}</textarea>
		</div>

		<!-- Danh mục -->
		<div class="mb-3">
			<label class="form-label fw-semibold">Danh mục</label>
			<select name="categoryId" class="form-select" required>
				<c:forEach var="cat" items="${categories}">
					<option value="${cat.categoryId}" 
						${cat.categoryId eq product.category.categoryId ? 'selected' : ''}>
						${cat.name}
					</option>
				</c:forEach>
			</select>
		</div>

		<!-- Cửa hàng -->
		<div class="mb-3">
			<label class="form-label fw-semibold">Cửa hàng</label>
			<select name="shopId" class="form-select" required>
				<c:forEach var="shop" items="${shops}">
					<option value="${shop.shopId}" 
						${shop.shopId eq product.shop.shopId ? 'selected' : ''}>
						${shop.name}
					</option>
				</c:forEach>
			</select>
		</div>

		<div class="d-flex justify-content-between mt-4">
			<a href="${pageContext.request.contextPath}/admin/products"
				class="btn btn-outline-primary-custom rounded-pill px-4">Quay lại</a>
			<button type="submit" class="btn btn-primary-custom rounded-pill px-4">Cập nhật</button>
		</div>
	</form>
</div>
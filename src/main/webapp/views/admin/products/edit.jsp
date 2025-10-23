<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">
	<h3 class="text-primary-custom fw-bold mb-4">
		<i class="bi bi-pencil-square me-2"></i> Chỉnh sửa sản phẩm
	</h3>

	<form action="${pageContext.request.contextPath}/admin/products/edit"
		  method="post" enctype="multipart/form-data"
		  class="card shadow-sm p-4 border-0">

		<input type="hidden" name="id" value="${product.productId}" />

		<!--  Thông tin sản phẩm -->
		<div class="mb-3">
			<label class="form-label fw-semibold">Tên sản phẩm</label>
			<input type="text" name="name" value="${product.name}" class="form-control"
				   ${empty product.name ? 'required' : ''}>
		</div>

		<div class="mb-3">
			<label class="form-label fw-semibold">Mô tả</label>
			<textarea name="description" class="form-control" rows="3"
					  ${empty product.description ? 'required' : ''}>${product.description}</textarea>
		</div>

		<div class="row mb-3">
			<div class="col-md-6">
				<label class="form-label fw-semibold">Danh mục</label>
				<select name="categoryId" class="form-select"
						${empty product.category ? 'required' : ''}>
					<option value="">-- Chọn danh mục --</option>
					<c:forEach var="c" items="${categories}">
						<option value="${c.categoryId}" ${c.categoryId eq product.category.categoryId ? 'selected' : ''}>${c.name}</option>
					</c:forEach>
				</select>
			</div>

			<div class="col-md-6">
				<label class="form-label fw-semibold">Cửa hàng</label>
				<select name="shopId" class="form-select"
						${empty product.shop ? 'required' : ''}>
					<option value="">-- Chọn cửa hàng --</option>
					<c:forEach var="s" items="${shops}">
						<option value="${s.shopId}" ${s.shopId eq product.shop.shopId ? 'selected' : ''}>${s.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<!-- Ảnh chính -->
		<div class="mb-3 text-center">
			<img id="mainPreview"
				 src="${pageContext.request.contextPath}/assets${product.imageUrl}"
				 class="rounded border mb-3"
				 width="180" height="180" style="object-fit:cover;">
			<input type="file" name="imageUrl" id="mainImageInput"
				   class="form-control w-auto mx-auto" accept="image/*"
				   ${empty product.imageUrl ? 'required' : ''}
				   onchange="previewMainImage(this)">
			<small class="text-muted d-block mt-1">Chọn ảnh sản phẩm nếu muốn thay đổi</small>
		</div>

		<hr class="my-4">
		<h5 class="fw-bold text-primary-custom">Danh sách biến thể</h5>

		<table id="variantTable" class="table table-bordered align-middle mt-3">
			<thead class="table-light">
				<tr>
					<th>Tên tùy chọn</th>
					<th>Giá trị tùy chọn</th>
					<th>Giá (₫)</th>
					<th>Giá cũ</th>
					<th>Tồn kho</th>
					<th>Ảnh</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="v" items="${variants}">
					<tr>
						<td><input type="text" name="optionName" value="${v.optionName}" class="form-control"
								   ${empty v.optionName ? 'required' : ''}></td>
						<td><input type="text" name="optionValue" value="${v.optionValue}" class="form-control"
								   ${empty v.optionValue ? 'required' : ''}></td>
						<td><input type="number" step="0.01" name="price" value="${v.price}" class="form-control"
								   ${empty v.price ? 'required' : ''}></td>
						<td><input type="number" step="0.01" name="oldPrice" value="${v.oldPrice}" class="form-control"
								   ${empty v.oldPrice ? 'required' : ''}></td>
						<td><input type="number" name="stock" value="${v.stock}" class="form-control"
								   ${empty v.stock ? 'required' : ''}></td>
						<td class="text-center">
							<img src="${pageContext.request.contextPath}/assets${v.imageUrl != null ? v.imageUrl : '/images/variants/default-product.jpg'}"
								 class="img-preview border rounded mb-2"
								 width="70" height="70" style="object-fit:cover;">
							<input type="file" name="variantImage" class="form-control form-control-sm"
								   accept="image/*"
								   ${empty v.imageUrl ? 'required' : ''}
								   onchange="previewVariantImage(this)">
						</td>
						<td class="text-center">
							<button type="button" class="btn btn-danger btn-sm removeRow"><i class="bi bi-x"></i></button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<!-- Thêm dòng mới -->
		<button type="button" id="addRow" class="btn btn-outline-primary-custom btn-sm mb-3">
			<i class="bi bi-plus"></i> Thêm biến thể
		</button>

		<div class="d-flex justify-content-between mt-4">
			<a href="${pageContext.request.contextPath}/admin/products" class="btn btn-outline-secondary">Quay lại</a>
			<button type="submit" class="btn btn-primary-custom">Cập nhật</button>
		</div>
	</form>
</div>

<!-- Script thêm dòng + preview ảnh -->
<script>
document.getElementById("addRow").addEventListener("click", () => {
	const tbody = document.querySelector("#variantTable tbody");
	const row = document.createElement("tr");
	row.innerHTML = `
		<td><input type="text" name="optionName" class="form-control" placeholder="VD: Màu sắc" required></td>
		<td><input type="text" name="optionValue" class="form-control" placeholder="VD: Xanh dương" required></td>
		<td><input type="number" step="0.01" name="price" class="form-control" required></td>
		<td><input type="number" step="0.01" name="oldPrice" class="form-control" required></td>
		<td><input type="number" name="stock" class="form-control" required></td>
		<td class="text-center">
			<img src="${pageContext.request.contextPath}/assets/images/products/default-product.jpg"
				class="img-preview border rounded mb-2" width="70" height="70" style="object-fit:cover;">
			<input type="file" name="variantImage" class="form-control form-control-sm" accept="image/*"
				onchange="previewVariantImage(this)" required>
		</td>
		<td class="text-center">
			<button type="button" class="btn btn-danger btn-sm removeRow"><i class="bi bi-x"></i></button>
		</td>
	`;
	tbody.appendChild(row);
});

// Xóa dòng
document.addEventListener("click", e => {
	if (e.target.closest(".removeRow")) e.target.closest("tr").remove();
});

// Preview ảnh chính
function previewMainImage(input) {
	const file = input.files[0];
	if (!file) return;
	const reader = new FileReader();
	reader.onload = e => {
		document.getElementById("mainPreview").src = e.target.result;
	};
	reader.readAsDataURL(file);
}

// Preview ảnh biến thể
function previewVariantImage(input) {
	const file = input.files[0];
	if (!file) return;
	const reader = new FileReader();
	reader.onload = function(e) {
		input.parentElement.querySelector('.img-preview').src = e.target.result;
	};
	reader.readAsDataURL(file);
}
</script>

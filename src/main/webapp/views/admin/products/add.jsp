<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">
	<h3 class="text-primary-custom fw-bold mb-4">
		<i class="bi bi-plus-circle me-2"></i> Thêm sản phẩm mới
	</h3>

	<form action="${pageContext.request.contextPath}/admin/products/add" method="post"
		enctype="multipart/form-data" class="card shadow-sm p-4 border-0">

		<!--  Thông tin sản phẩm -->
		<div class="mb-3">
			<label class="form-label fw-semibold">Tên sản phẩm</label>
			<input type="text" name="name" class="form-control" required>
		</div>

		<div class="mb-3">
			<label class="form-label fw-semibold">Mô tả</label>
			<textarea name="description" class="form-control" rows="3"></textarea>
		</div>

		<div class="row mb-3">
			<div class="col-md-6">
				<label class="form-label fw-semibold">Danh mục</label>
				<select name="categoryId" class="form-select">
					<c:forEach var="c" items="${categories}">
						<option value="${c.categoryId}">${c.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-md-6">
				<label class="form-label fw-semibold">Cửa hàng</label>
				<select name="shopId" class="form-select">
					<c:forEach var="s" items="${shops}">
						<option value="${s.shopId}">${s.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<!--  Ảnh chính sản phẩm -->
		<div class="mb-4 text-center">
			<label class="form-label fw-semibold d-block">Ảnh sản phẩm chính</label>
			<img id="mainImagePreview"
				src="${pageContext.request.contextPath}/assets/images/products/default-product.jpg"
				class="border rounded mb-2 shadow-sm"
				width="160" height="160" style="object-fit:cover;">
			<input type="file" name="imageUrl" class="form-control w-auto mx-auto" accept="image/*"
				onchange="previewMainImage(this)">
		</div>

		<hr class="my-4">
		<h5 class="fw-bold text-primary-custom">Biến thể sản phẩm</h5>

		<!--  Bảng biến thể -->
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
				<tr>
					<td><input type="text" name="optionName" class="form-control" placeholder="VD: Màu sắc"></td>
					<td><input type="text" name="optionValue" class="form-control" placeholder="VD: Đỏ"></td>
					<td><input type="number" step="0.01" name="price" class="form-control"></td>
					<td><input type="number" step="0.01" name="oldPrice" class="form-control"></td>
					<td><input type="number" name="stock" class="form-control"></td>
					<td class="text-center">
						<img src="${pageContext.request.contextPath}/assets/images/products/default-product.jpg"
							class="img-preview border rounded mb-2" width="70" height="70" style="object-fit:cover;">
						<input type="file" name="variantImage" class="form-control form-control-sm" accept="image/*"
							onchange="previewVariantImage(this)">
					</td>
					<td class="text-center">
						<button type="button" class="btn btn-danger btn-sm removeRow"><i class="bi bi-x"></i></button>
					</td>
				</tr>
			</tbody>
		</table>

		<!--  Nút thêm dòng -->
		<button type="button" id="addRow" class="btn btn-outline-primary-custom btn-sm mb-3">
			<i class="bi bi-plus"></i> Thêm biến thể
		</button>

		<div class="d-flex justify-content-between mt-4">
			<a href="${pageContext.request.contextPath}/admin/products" class="btn btn-outline-secondary">Quay lại</a>
			<button type="submit" class="btn btn-primary-custom">Lưu sản phẩm</button>
		</div>
	</form>
</div>

<!--  Script preview + thêm dòng -->
<script>
document.getElementById("addRow").addEventListener("click", () => {
	const tbody = document.querySelector("#variantTable tbody");
	const row = document.createElement("tr");
	row.innerHTML = `
		<td><input type="text" name="optionName" class="form-control" placeholder="VD: Kích thước"></td>
		<td><input type="text" name="optionValue" class="form-control" placeholder="VD: Size L"></td>
		<td><input type="number" step="0.01" name="price" class="form-control"></td>
		<td><input type="number" step="0.01" name="oldPrice" class="form-control"></td>
		<td><input type="number" name="stock" class="form-control"></td>
		<td class="text-center">
			<img src="${pageContext.request.contextPath}/assets/images/products/default-product.jpg"
				class="img-preview border rounded mb-2" width="70" height="70" style="object-fit:cover;">
			<input type="file" name="variantImage" class="form-control form-control-sm" accept="image/*"
				onchange="previewVariantImage(this)">
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

// Preview ảnh biến thể
function previewVariantImage(input) {
	const file = input.files[0];
	const preview = input.parentElement.querySelector('.img-preview');
	if (!file) {
		preview.src = "${pageContext.request.contextPath}/assets/images/products/default-product.jpg";
		return;
	}
	const reader = new FileReader();
	reader.onload = e => preview.src = e.target.result;
	reader.readAsDataURL(file);
}

// Preview ảnh chính sản phẩm
function previewMainImage(input) {
	const file = input.files[0];
	const preview = document.getElementById('mainImagePreview');
	if (!file) {
		preview.src = "${pageContext.request.contextPath}/assets/images/products/default-product.jpg";
		return;
	}
	const reader = new FileReader();
	reader.onload = e => preview.src = e.target.result;
	reader.readAsDataURL(file);
}
</script>

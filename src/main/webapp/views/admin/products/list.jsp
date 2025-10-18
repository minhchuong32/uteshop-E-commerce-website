<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">
	<h3 class="text-primary-custom fw-bold mb-4">
		<i class="bi bi-box-seam me-2"></i> Quản lý sản phẩm
	</h3>

	<!-- Nút thêm -->
	<div class="mb-3">
		<a href="${pageContext.request.contextPath}/admin/products/add" class="btn btn-success">
			<i class="bi bi-plus"></i> Thêm sản phẩm
		</a>
	</div>

	<!-- Bảng sản phẩm -->
	<div class="card shadow-sm">
		<div class="card-body table-responsive">
			<table id="productTable" class="table table-hover align-middle mb-0">
				<thead class="table-light">
					<tr>
						<th class="text-center">ID</th>
						<th class="text-center">Ảnh</th>
						<th>Tên sản phẩm</th>
						<th class="text-center">Danh mục</th>
						<th class="text-center">Cửa hàng</th>
						<th class="text-center">Đánh giá</th>
						<th class="text-center">Hành động</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="p" items="${products}">
						<tr class="product-row" 
							data-id="${p.productId}" 
							data-name="${p.name}" 
							data-desc="${p.description}" 
							data-category="${p.category.name}" 
							data-shop="${p.shop.name}" 
							data-img="${pageContext.request.contextPath}/assets${p.imageUrl}">
							
							<td class="text-center">${p.productId}</td>
							<td class="text-center">
								<img src="${pageContext.request.contextPath}/assets${p.imageUrl}" 
									class="rounded border" width="70" height="70" style="object-fit:cover;">
							</td>
							<td>${p.name}</td>
							<td class="text-center"><span class="badge bg-info text-dark">${p.category.name}</span></td>
							<td class="text-center"><span class="badge bg-primary">${p.shop.name}</span></td>
							<td class="text-center">
								<i class="bi bi-star-fill text-warning"></i> 
								${p.averageRating > 0 ? String.format("%.1f", p.averageRating) : "N/A"}
							</td>
							<td class="text-center">
								<a href="${pageContext.request.contextPath}/admin/products/edit?id=${p.productId}" class="text-warning me-3">
									<i class="bi bi-pencil-square fs-5"></i>
								</a>
								<!-- Xóa -->
								<a href="javascript:void(0);"
									class="text-danger me-2" data-bs-toggle="modal"
									data-bs-target="#confirmDeleteModal" data-id="${p.productId}"
									data-url="${pageContext.request.contextPath}/admin/products/delete"
									title="Xóa">
									<i class="bi bi-trash-fill fs-5"></i>
								</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<!-- Modal chi tiết sản phẩm -->
<div class="modal fade" id="productDetailModal" tabindex="-1" aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered">
		<div class="modal-content border-0 shadow-lg">
			<div class="modal-header bg-primary text-white">
				<h5 class="modal-title fw-bold"><i class="bi bi-info-circle me-2"></i>Chi tiết sản phẩm</h5>
				<button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-4 text-center mb-3">
						<img id="detailImage" src="" class="rounded border shadow-sm" width="100%" style="max-width:180px;object-fit:cover;">
					</div>
					<div class="col-md-8">
						<h5 id="detailName" class="fw-bold text-primary-custom mb-2"></h5>
						<p id="detailDesc" class="text-muted"></p>
						<p><strong>Danh mục:</strong> <span id="detailCategory" class="text-info fw-semibold"></span></p>
						<p><strong>Cửa hàng:</strong> <span id="detailShop" class="text-primary fw-semibold"></span></p>
					</div>
				</div>

				<hr>

				<h6 class="fw-bold text-primary-custom">Biến thể sản phẩm</h6>
				<div class="table-responsive">
					<table id="variantTable" class="table table-sm table-bordered mt-2">
						<thead class="table-light">
							<tr>
								<th>Ảnh</th>
								<th>Tên tùy chọn</th>
								<th>Giá trị</th>
								<th>Giá (₫)</th>
								<th>Giá cũ</th>
								<th>Tồn kho</th>
							</tr>
						</thead>
						<tbody id="variantBody">
							<tr><td colspan="6" class="text-center text-muted">Đang tải...</td></tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Đóng</button>
			</div>
		</div>
	</div>
</div>

<!-- Modal xác nhận xóa -->
<div class="modal fade" id="confirmDeleteModal" tabindex="-1" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content shadow-lg border-0 rounded-3">
			<div class="modal-header bg-danger text-white">
				<h5 class="modal-title">
					<i class="bi bi-exclamation-triangle-fill me-2"></i> Xác nhận xóa
				</h5>
				<button type="button" class="btn-close btn-close-white"
					data-bs-dismiss="modal" aria-label="Đóng"></button>
			</div>
			<div class="modal-body">
				<p>
					Bạn có chắc muốn xóa sản phẩm này không? Hành động này <strong>không
						thể hoàn tác</strong>.
				</p>
			</div>
			<div class="modal-footer">
				<button type="button"
					class="btn btn-outline-secondary rounded-pill px-4"
					data-bs-dismiss="modal">Hủy</button>
				<a id="deleteConfirmBtn" href="#"
					class="btn btn-danger rounded-pill px-4"> <i
					class="bi bi-trash-fill me-1"></i> Xóa
				</a>
			</div>
		</div>
	</div>
</div>

<!-- Script khởi tạo xử lý click sản phẩm -->
<script>
// Context path cho AJAX requests
const contextPath = '${pageContext.request.contextPath}';

// Khởi tạo xử lý click vào dòng sản phẩm
document.addEventListener('DOMContentLoaded', function() {
    if (typeof handleProductRowClick === 'function') {
        handleProductRowClick(contextPath);
    }
});
</script>

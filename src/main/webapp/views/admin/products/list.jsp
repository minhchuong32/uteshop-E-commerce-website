<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>

<!-- CSS DataTables -->
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.13.7/css/dataTables.bootstrap5.min.css">

<div class="container-fluid">
	<h3 class="text-primary-custom fw-bold mb-4">
		<i class="bi bi-box-seam me-2"></i> Quản lý sản phẩm
	</h3>

	<!-- Nút thêm mới -->
	<div class="mb-3">
		<a href="${pageContext.request.contextPath}/admin/products/add"
			class="btn btn-success"> <i class="bi bi-plus"></i>
			Thêm sản phẩm
		</a>
	</div>

	<div class="card shadow-sm">
		<div class="card-body table-responsive">
			<!-- Hiển thị thông báo thành công -->
			<c:if test="${not empty sessionScope.success}">
				<div
					class="alert alert-success alert-dismissible fade show mt-3 shadow-sm"
					role="alert">
					<i class="bi bi-check-circle-fill me-2"></i>
					${sessionScope.success}
					<button type="button" class="btn-close" data-bs-dismiss="alert"
						aria-label="Đóng"></button>
				</div>
				<c:remove var="success" scope="session" />
			</c:if>

			<!-- Hiển thị thông báo lỗi -->
			<c:if test="${not empty error}">
				<div
					class="alert alert-danger alert-dismissible fade show mt-3 shadow-sm"
					role="alert">
					<i class="bi bi-exclamation-triangle-fill me-2"></i> ${error}
					<button type="button" class="btn-close" data-bs-dismiss="alert"
						aria-label="Đóng"></button>
				</div>
			</c:if>

			<table id="productTable" class="table table-hover align-middle mb-0">
				<thead class="table-light">
					<tr>
						<th class="text-center">ID</th>
						<th class="text-center">Hình ảnh</th>
						<th>Tên sản phẩm</th>
						<th class="text-center">Danh mục</th>
						<th class="text-center">Cửa hàng</th>
						<th class="text-center">Đánh giá</th>
						<th class="text-center">Hành động</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="p" items="${products}" varStatus="loop">
						<tr>
							<td class="text-center">${loop.index + 1}</td>

							<td class="text-center">
								<img
									src="${empty p.imageUrl 
										? pageContext.request.contextPath.concat('/assets/images/default-product.png') 
										: pageContext.request.contextPath.concat('/assets/').concat(p.imageUrl)}"
									alt="product" class="rounded"
									style="width: 80px; height: 80px; object-fit: cover;">
							</td>

							<td>
								<div class="fw-bold">${p.name}</div>
								<small class="text-muted">
									${p.description != null && p.description.length() > 60 
										? p.description.substring(0, 60).concat('...') 
										: p.description}
								</small>
							</td>

							<td class="text-center">
								<span class="badge bg-info text-dark">${p.category.name}</span>
							</td>

							<td class="text-center">
								<span class="badge bg-primary">${p.shop.name}</span>
							</td>

							<td class="text-center">
								<div>
									<i class="bi bi-star-fill text-warning"></i>
									<span class="fw-bold">${p.averageRating > 0 ? String.format("%.1f", p.averageRating) : "N/A"}</span>
								</div>
								<small class="text-muted">(${p.reviewsCount} đánh giá)</small>
							</td>

							<td class="text-center">
								<!-- Edit -->
								<a href="${pageContext.request.contextPath}/admin/products/edit?id=${p.productId}"
								   class="text-warning me-3" title="Sửa">
									<i class="bi bi-pencil-square fs-5"></i>
								</a>
								
								<!-- Delete -->
								<a href="javascript:void(0);"
								   class="text-danger"
								   data-bs-toggle="modal"
								   data-bs-target="#confirmDeleteModal"
								   data-id="${p.productId}"
								   data-url="${pageContext.request.contextPath}/admin/products/delete"
								   title="Xóa sản phẩm">
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

<!-- Modal Xác nhận Xóa -->
<div class="modal fade" id="confirmDeleteModal" tabindex="-1"
	aria-hidden="true">
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
					class="btn btn-danger rounded-pill px-4">
					<i class="bi bi-trash-fill me-1"></i> Xóa
				</a>
			</div>
		</div>
	</div>
</div>

<!-- JS DataTables -->
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script
	src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"></script>
<script
	src="https://cdn.datatables.net/1.13.7/js/dataTables.bootstrap5.min.js"></script>

<!-- JS Xử lý Modal Xóa -->
<script
	src="${pageContext.request.contextPath}/assets/js/admin/modal-delete.js"></script>

<script>
	$(document).ready(function() {
		$('#productTable').DataTable({
			"pageLength" : 10,
			"lengthChange" : false,
			"ordering" : true,
			"searching" : true,
			"language" : {
				"search" : "Tìm kiếm:",
				"paginate" : {
					"first" : "Đầu",
					"last" : "Cuối",
					"next" : "›",
					"previous" : "‹"
				},
				"info" : "Hiển thị _START_ - _END_ / _TOTAL_ sản phẩm",
				"infoEmpty" : "Không có dữ liệu",
				"zeroRecords" : "Không tìm thấy kết quả phù hợp"
			},
			"columnDefs" : [ {
				"orderable" : false,
				"targets" : [ 1, 6 ]
			} ]
		});
	});
</script>
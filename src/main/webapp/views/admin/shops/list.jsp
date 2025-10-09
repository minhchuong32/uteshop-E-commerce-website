<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>

<!-- CSS DataTables -->
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.13.7/css/dataTables.bootstrap5.min.css">

<div class="container-fluid">
	<h3 class="mb-4 fw-bold text-primary-custom">
		<i class="bi bi-shop me-2"></i> Quản lý cửa hàng
	</h3>

	<!-- Nút thêm mới -->
	<div class="mb-3">
		<a href="${pageContext.request.contextPath}/admin/shops/add"
			class="btn btn-success"> <i class="bi bi-plus"></i> Thêm cửa
			hàng
		</a>
	</div>

	<!-- Bảng danh sách -->
	<div class="card shadow-sm border-0">
		<div class="card-body table-responsive">

			<!-- Thông báo thành công -->
			<c:if test="${not empty sessionScope.success}">
				<div
					class="alert alert-success alert-dismissible fade show shadow-sm"
					role="alert">
					<i class="bi bi-check-circle-fill me-2"></i>${sessionScope.success}
					<button type="button" class="btn-close" data-bs-dismiss="alert"
						aria-label="Đóng"></button>
				</div>
				<c:remove var="success" scope="session" />
			</c:if>

			<!-- Thông báo lỗi -->
			<c:if test="${not empty error}">
				<div
					class="alert alert-danger alert-dismissible fade show shadow-sm"
					role="alert">
					<i class="bi bi-exclamation-triangle-fill me-2"></i>${error}
					<button type="button" class="btn-close" data-bs-dismiss="alert"
						aria-label="Đóng"></button>
				</div>
			</c:if>

			<table id="shopTable" class="table table-hover align-middle mb-0">
				<thead class="table-light">
					<tr>
						<th class="text-center" style="width: 60px;">ID</th>
						<th class="text-center">Cửa hàng</th>
						<th class="text-center">Chủ sở hữu</th>
						<th class="text-center">Mô tả</th>
						<th class="text-center">Ngày tạo</th>
						<th class="text-center" style="width: 130px;">Hành động</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="s" items="${shops}" varStatus="loop">
						<tr>
							<td class="text-center fw-semibold">${loop.index + 1}</td>

							<!-- Avatar + tên cửa hàng -->
							<td>
								<div class="d-flex align-items-center">
									<img
										src="${empty s.logo 
										? pageContext.request.contextPath.concat('/assets/images//shops/default-shop-logo.png') 
										: pageContext.request.contextPath.concat('/assets/images/shops/').concat(s.logo)}"
										class="rounded me-3 border"
										style="width: 48px; height: 48px; object-fit: cover;"
										alt="shop-logo">

								</div>
							</td>

							<!-- Chủ sở hữu -->
							<td>
								<div class="fw-semibold">${s.user.username}</div> <small
								class="text-muted">${s.user.email}</small>
							</td>

							<!-- Mô tả -->
							<td style="max-width: 250px;"><c:choose>
									<c:when test="${empty s.description}">
										<span class="text-muted">-</span>
									</c:when>
									<c:otherwise>
										<span>${fn:length(s.description) > 60 
											? fn:substring(s.description, 0, 60).concat("...") 
											: s.description}</span>
									</c:otherwise>
								</c:choose></td>

							<!-- Ngày tạo -->
							<td><fmt:formatDate value="${s.createdAt}"
									pattern="dd/MM/yyyy" /></td>

							<!-- Hành động -->
							<td class="text-center">
								<!-- Sửa --> <a
								href="${pageContext.request.contextPath}/admin/shops/edit?id=${s.shopId}"
								class="text-warning me-2" title="Sửa"> <i
									class="bi bi-pencil-square fs-5"></i>
							</a> <!-- Xóa --> <a href="javascript:void(0);"
								class="text-danger me-2" data-bs-toggle="modal"
								data-bs-target="#confirmDeleteModal" data-id="${s.shopId}"
								data-url="${pageContext.request.contextPath}/admin/shops/delete"
								title="Xóa"> <i class="bi bi-trash-fill fs-5"></i>
							</a>

							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<!-- Modal xác nhận xóa -->
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
					Bạn có chắc muốn xóa cửa hàng này không? Hành động này <strong>không
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
	$('#shopTable').DataTable({
		"pageLength": 10,
		"lengthChange": false,
		"ordering": true,
		"searching": true,
		"language": {
			"search": "Tìm kiếm:",
			"paginate": {
				"first": "Đầu",
				"last": "Cuối",
				"next": "›",
				"previous": "‹"
			},
			"info": "Hiển thị _START_ - _END_ / _TOTAL_ cửa hàng",
			"infoEmpty": "Không có dữ liệu",
			"zeroRecords": "Không tìm thấy kết quả phù hợp",
			"emptyTable": "Không có dữ liệu"
		},
		"columnDefs": [
			{ "orderable": false, "targets": [1, 5] } // không sắp xếp cột ảnh và hành động
		]
	});
});
</script>

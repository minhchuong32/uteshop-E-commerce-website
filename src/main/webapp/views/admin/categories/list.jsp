<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">
	<h3 class="text-primary-custom fw-bold mb-4">
		<i class="bi bi-tags-fill me-2"></i> Quản lý danh mục
	</h3>

	<!-- Nút thêm mới -->
	<div class="mb-3">
		<a href="${pageContext.request.contextPath}/admin/categories/add"
			class="btn btn-success"> <i class="bi bi-plus"></i> Thêm danh mục
		</a>
	</div>

	<div class="card shadow-sm">
		<div class="card-body table-responsive">

			<!-- Thông báo thành công -->
			<c:if test="${not empty sessionScope.success}">
				<div
					class="alert alert-success alert-dismissible fade show mt-3 shadow-sm"
					role="alert">
					<i class="bi bi-check-circle-fill me-2"></i>${sessionScope.success}
					<button type="button" class="btn-close" data-bs-dismiss="alert"
						aria-label="Đóng"></button>
				</div>
				<c:remove var="success" scope="session" />
			</c:if>

			<!-- Thông báo lỗi -->
			<c:if test="${not empty sessionScope.error}">
				<div
					class="alert alert-danger alert-dismissible fade show mt-3 shadow-sm"
					role="alert">
					<i class="bi bi-exclamation-triangle-fill me-2"></i>${sessionScope.error}
					<button type="button" class="btn-close" data-bs-dismiss="alert"
						aria-label="Đóng"></button>
				</div>
				<c:remove var="error" scope="session" />
			</c:if>

			<table id="categoryTable" class="table table-hover align-middle mb-0">
				<thead class="table-light">
					<tr>
						<th class="text-center">ID</th>
						<th class="text-center">Hình ảnh</th>
						<th>Tên danh mục</th>
						<th class="text-center">Số sản phẩm</th>
						<th class="text-center">Hành động</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="c" items="${categories}" varStatus="loop">
						<tr>
							<td class="text-center">${loop.index + 1}</td>
							<td class="text-center"><img
								src="${empty c.image 
										? pageContext.request.contextPath.concat('/assets/images/categories/default-category.jpg') 
										: pageContext.request.contextPath.concat('/assets').concat(c.image)}"
								class="rounded border"
								style="width: 80px; height: 80px; object-fit: cover;" /></td>
							<td class="fw-bold">${c.name}</td>
							<td class="text-center"><span
								class="badge bg-info text-dark">${c.products.size()} SP</span></td>
							<td class="text-center">
								<!-- Edit -->
								<button type="button" class="btn btn-warning btn-sm me-2"
									title="Sửa danh mục"
									onclick="window.location.href='${pageContext.request.contextPath}/admin/categories/edit?id=${c.categoryId}'">
									<i class="bi bi-pencil-square"></i>
								</button> <!-- Delete -->
								<button type="button" class="btn btn-danger btn-sm deleteBtn"
									title="Xóa danh mục" data-bs-toggle="modal"
									data-bs-target="#confirmDeleteModal" data-id="${c.categoryId}"
									data-url="${pageContext.request.contextPath}/admin/categories/delete">
									<i class="bi bi-trash-fill"></i>
								</button>

							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<!-- Modal Xóa -->
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
					Bạn có chắc muốn xóa danh mục này không? <strong>Hành động
						này không thể hoàn tác!</strong>
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
<script
	src="${pageContext.request.contextPath}/assets/js/admin/modal-delete.js"></script>

<script>
	$(document)
			.ready(
					function() {
						$('#categoryTable')
								.DataTable(
										{
											pageLength : 5,
											ordering : true,
											lengthMenu : [
													[ 5, 10, 25, 50, -1 ],
													[ 5, 10, 25, 50, "Tất cả" ] ],
											language : {
												lengthMenu : "Hiển thị _MENU_ dòng",
												search : "Tìm kiếm:",
												paginate : {
													previous : "Trước",
													next : "Sau"
												},
												info : "Hiển thị _START_–_END_ / _TOTAL_ Danh Mục",
												emptyTable : "Không có dữ liệu"
											}
										});

					});
</script>

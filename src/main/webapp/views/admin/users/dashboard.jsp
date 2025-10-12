<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>


<div class="container-fluid">
	<h3 class="text-primary-custom fw-bold mb-4">
		<i class="bi bi-people-fill me-2"></i> Quản lý người dùng
	</h3>

	<!-- Nút thêm mới -->
	<div class="mb-3">
		<a href="${pageContext.request.contextPath}/admin/users/add"
			class="btn btn-success"> <i class="bi bi-plus"></i>
			Thêm người dùng
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

			<table id="userTable" class="table table-hover align-middle mb-0">
				<thead class="table-light">
					<tr>
						<th class="text-center">ID</th>
						<th class="text-center">Người dùng</th>
						<th class="text-center">Email</th>
						<th class="text-center">Số điện thoại</th>
						<th class="text-center">Địa chỉ</th>
						<th class="text-center">Vai trò</th>
						<th class="text-center">Trạng thái</th>
						<th class="text-center">Hành động</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="u" items="${users}" varStatus="loop">
						<tr>
							<td class="text-center">${loop.index + 1}</td>

							<td>
								<div class="d-flex align-items-center">
									<img
										src="${pageContext.request.contextPath}/assets/images/default_avatar.png"
										alt="avatar" class="rounded-circle me-3"
										style="width: 48px; height: 48px; object-fit: cover;">
									<div>
										<div class="fw-bold">${empty u.name ? u.username : u.name}</div>
										<small class="text-muted">@${u.username}</small>
									</div>
								</div>
							</td>

							<td>${u.email}</td>
							<td>${empty u.phone ? '-' : u.phone}</td>
							<td>${empty u.address ? '-' : u.address}</td>

							<td><c:choose>
									<c:when test="${u.role eq 'Admin'}">
										<span class="badge bg-dark">Admin</span>
									</c:when>
									<c:when test="${u.role eq 'Vendor'}">
										<span class="badge bg-primary">Vendor</span>
									</c:when>
									<c:when test="${u.role eq 'Shipper'}">
										<span class="badge bg-warning text-dark">Shipper</span>
									</c:when>
									<c:when test="${u.role eq 'Guest'}">
										<span class="badge bg-secondary">Guest</span>
									</c:when>
									<c:otherwise>
										<span class="badge bg-info text-dark">User</span>
									</c:otherwise>
								</c:choose></td>

							<td><c:choose>
									<c:when test="${u.status eq 'active'}">
										<span class="badge bg-success">Active</span>
									</c:when>
									<c:when test="${u.status eq 'inactive'}">
										<span class="badge bg-secondary">Inactive</span>
									</c:when>
									<c:otherwise>
										<span class="badge bg-danger">Banned</span>
									</c:otherwise>
								</c:choose></td>

							<td class="text-center">
								<!-- Edit --> <a
								href="${pageContext.request.contextPath}/admin/users/edit?id=${u.userId}"
								class="text-warning me-3" title="Sửa"> <i
									class="bi bi-pencil-square fs-5"></i>
							</a> <!-- Delete --> <a href="javascript:void(0);"
								class="text-danger me-3" data-bs-toggle="modal"
								data-bs-target="#confirmDeleteModal" data-id="${u.userId}"
								data-url="${pageContext.request.contextPath}/admin/users/delete"
								title="Xóa người dùng"> <i class="bi bi-trash-fill fs-5"></i>
							</a> <!-- Lock / Unlock --> <c:choose>
									<c:when test="${u.status eq 'active'}">
										<a
											href="${pageContext.request.contextPath}/admin/users/lock?id=${u.userId}"
											class="text-danger" title="Khóa tài khoản"> <i
											class="bi bi-lock-fill fs-5"></i>
										</a>
									</c:when>
									<c:otherwise>
										<a
											href="${pageContext.request.contextPath}/admin/users/unlock?id=${u.userId}"
											class="text-success" title="Mở khóa tài khoản"> <i
											class="bi bi-unlock-fill fs-5"></i>
										</a>
									</c:otherwise>
								</c:choose>
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
					Bạn có chắc muốn xóa người dùng này không? Hành động này <strong>không
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
		$('#userTable').DataTable({
			"pageLength" : 10, // Mỗi trang 10 user
			"lengthChange" : false, // Ẩn lựa chọn số dòng
			"ordering" : true, // Cho phép sắp xếp
			"searching" : true, // Ô tìm kiếm
			"language" : { // Dịch sang tiếng Việt
				"search" : "Tìm kiếm:",
				"paginate" : {
					"first" : "Đầu",
					"last" : "Cuối",
					"next" : "›",
					"previous" : "‹"
				},
				"info" : "Hiển thị _START_ - _END_ / _TOTAL_ người dùng",
				"infoEmpty" : "Không có dữ liệu",
				"zeroRecords" : "Không tìm thấy kết quả phù hợp"
			},
			"columnDefs" : [ {
				"orderable" : false,
				"targets" : [ 1, 7 ]
			} // Không cho sắp xếp cột ảnh + hành động
			]
		});
	});
</script>

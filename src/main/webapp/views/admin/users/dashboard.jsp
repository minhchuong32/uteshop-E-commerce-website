<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>
<div class="container-fluid">
	<h3 class="mb-4 fw-bold">Quản lý người dùng</h3>

	<!-- Nút thêm mới -->
	<div class="mb-3">
		<a href="${pageContext.request.contextPath}/admin/users/add"
			class="btn btn-success"> <i class="bi bi-person-plus-fill me-2"></i>
			Thêm người dùng
		</a>
	</div>

	<div class="card shadow-sm">
		<div class="card-body">
			<table class="table align-middle mb-0">
				<thead class="table-light">
					<tr>
						<th>Người dùng</th>
						<th>Vai trò</th>
						<th>Trạng thái</th>
						<th class="text-center">Hành động</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="u" items="${users}">
						<tr>
							<!-- Tên + email -->
							<td>
								<div class="fw-bold">${u.username}</div> <small
								class="text-muted">${u.email}</small>
							</td>

							<!-- Vai trò -->
							<td><c:choose>
									<c:when test="${u.role eq 'Admin'}">
										<span class="badge rounded-pill bg-dark text-white">Admin</span>
									</c:when>
									<c:when test="${u.role eq 'Vendor'}">
										<span
											class="badge rounded-pill bg-primary-subtle text-primary">Vendor</span>
									</c:when>
									<c:when test="${u.role eq 'Shipper'}">
										<span
											class="badge rounded-pill bg-warning-subtle text-warning">Shipper</span>
									</c:when>
									<c:otherwise>
										<span class="badge rounded-pill bg-light text-dark">User</span>
									</c:otherwise>
								</c:choose></td>

							<!-- Trạng thái -->
							<td><c:choose>
									<c:when test="${u.status eq 'active'}">
										<span
											class="badge rounded-pill bg-success-subtle text-success">active</span>
									</c:when>
									<c:otherwise>
										<span class="badge rounded-pill bg-danger-subtle text-danger">banned</span>
									</c:otherwise>
								</c:choose></td>


							<!-- Action -->
							<td class="text-center">
								<!-- Edit --> <a
								href="${pageContext.request.contextPath}/admin/users/edit?id=${u.userId}"
								class="text-warning me-3" title="Sửa"> <i
									class="bi bi-pencil-square"></i>
							</a> <!-- Delete (sử dụng modal Bootstrap) --> <a
								href="javascript:void(0);" class="text-danger me-3"
								data-bs-toggle="modal" data-bs-target="#confirmDeleteModal"
								data-id="${u.userId}"
								data-url="${pageContext.request.contextPath}/admin/users/delete"
								title="Xóa"> <i class="bi bi-trash-fill"></i>
							</a> <!-- Lock / Unlock --> <c:choose>
									<c:when test="${u.status eq 'active'}">
										<a
											href="${pageContext.request.contextPath}/admin/users/lock?id=${u.userId}"
											class="text-danger" title="Khóa tài khoản"> <i
											class="bi bi-lock-fill"></i>
										</a>
									</c:when>
									<c:otherwise>
										<a
											href="${pageContext.request.contextPath}/admin/users/unlock?id=${u.userId}"
											class="text-success" title="Mở khóa tài khoản"> <i
											class="bi bi-unlock-fill"></i>
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
				<button type="button" class="btn btn-secondary rounded-pill px-4"
					data-bs-dismiss="modal">Hủy</button>
				<a id="deleteConfirmBtn" href="#"
					class="btn btn-danger rounded-pill px-4">Xóa</a>
			</div>
		</div>
	</div>
</div>

<script
	src="${pageContext.request.contextPath}/assets/js/admin/modal-delete.js"></script>


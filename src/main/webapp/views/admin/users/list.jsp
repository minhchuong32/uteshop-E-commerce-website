<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid px-4">
	<%-- KHỐI HIỂN THỊ THÔNG BÁO --%>
	<c:if test="${not empty param.message}">
		<div
			class="alert alert-success alert-dismissible fade show mb-3 shadow-sm"
			role="alert">
			<i class="bi bi-check-circle-fill me-2"></i>
			<c:choose>
				<c:when test="${param.message == 'successUnlock'}">Đã mở khóa tài khoản thành công!</c:when>
				<c:when test="${param.message == 'successLock'}">Đã khóa tài khoản người dùng!</c:when>
				<c:when test="${param.message == 'successDel'}">Xóa người dùng thành công!</c:when>
				<c:when test="${param.message == 'successAdd'}">Thêm người dùng thành công!</c:when>
				<c:when test="${param.message == 'successEdit'}">Cập nhật người dùng thành công!</c:when>
				<c:otherwise>Thao tác thành công!</c:otherwise>
			</c:choose>
			<button type="button" class="btn-close" data-bs-dismiss="alert"
				aria-label="Đóng"></button>
		</div>
	</c:if>

	<c:if test="${not empty param.error}">
		<div
			class="alert alert-danger alert-dismissible fade show mb-3 shadow-sm"
			role="alert">
			<i class="bi bi-exclamation-triangle-fill me-2"></i>
			<c:choose>
				<c:when test="${param.error == 'errorNotfound'}">Không tìm thấy người dùng.</c:when>
				<c:when test="${param.error == 'errorMail'}">Email đã tồn tại, vui lòng chọn email khác.</c:when>
				<c:when test="${param.error == 'invalidId'}">ID không hợp lệ.</c:when>
				<c:when test="${param.error == 'errorGet'}">Có lỗi trong quá trình lấy dữ liệu !</c:when>
				<c:when test="${param.error == 'errorPost'}">Có lỗi trong quá trình chỉnh sửa dữ liệu !</c:when>
				<c:otherwise>Có lỗi xảy ra. Vui lòng thử lại.</c:otherwise>
			</c:choose>
			<button type="button" class="btn-close" data-bs-dismiss="alert"
				aria-label="Đóng"></button>
		</div>
	</c:if>

	<h3 class="text-primary-custom fw-bold mb-4">
		<i class="bi bi-people-fill me-2"></i> Quản lý người dùng
	</h3>
	<div class="mb-3">
		<a href="${pageContext.request.contextPath}/admin/users/add"
			class="btn btn-primary-custom"> <i class="bi bi-plus-lg"></i>
			Thêm người dùng
		</a>
	</div>
	<%-- BẢNG DỮ LIỆU --%>
	<div class="card shadow-sm border-0">
		<div class="card-body">
			<div class="table-responsive">
				<table id="userTable" class="table table-hover align-middle mb-0"
					style="width: 100%">
					<thead class="table-light">
						<tr>
							<th>#</th>
							<th>Người dùng</th>
							<th>Liên hệ</th>
							<th>Địa chỉ</th>
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
									<div class="d-flex align-items-center gap-3">
										<img
											src="${empty u.avatar ? pageContext.request.contextPath.concat('/assets/images/avatars/default.jpg') : pageContext.request.contextPath.concat('/assets/images').concat(u.avatar)}"
											alt="Avatar" class="rounded-circle border"
											style="width: 48px; height: 48px; object-fit: cover;">
										<div>
											<div class="fw-bold">${empty u.name ? u.username : u.name}</div>
											<small class="text-muted">@${u.username}</small>
										</div>
									</div>
								</td>
								<td>
									<div>${u.email}</div> <small class="text-muted">${empty u.phone ? '-' : u.phone}</small>
								</td>
								<td>${empty u.address ? '-' : u.address}</td>
								<td class="text-center"><c:choose>
										<c:when test="${u.role eq 'Admin'}">
											<span class="badge bg-dark">Admin</span>
										</c:when>
										<c:when test="${u.role eq 'Vendor'}">
											<span class="badge bg-primary">Vendor</span>
										</c:when>
										<c:when test="${u.role eq 'Shipper'}">
											<span class="badge bg-warning text-dark">Shipper</span>
										</c:when>
										<c:otherwise>
											<span class="badge bg-info text-dark">User</span>
										</c:otherwise>
									</c:choose></td>
								<td class="text-center"><c:choose>
										<c:when test="${u.status eq 'active'}">
											<span class="badge bg-success">Hoạt động</span>
										</c:when>
										<c:otherwise>
											<span class="badge bg-danger">Bị khóa</span>
										</c:otherwise>
									</c:choose></td>
								<td class="text-center">
									<div class="btn-group btn-group-sm">

										<!-- Edit -->
										<button type="button" class="btn btn-warning btn-sm me-2"
											title="Sửa danh mục"
											onclick="window.location.href='${pageContext.request.contextPath}/admin/users/edit?id=${u.userId}'">
											<i class="bi bi-pencil-square"></i>
										</button>

										<!-- Delete -->
										<button type="button"
											class="btn btn-danger btn-sm me-2 deleteBtn"
											title="Xóa người dùng" data-bs-toggle="modal"
											data-bs-target="#confirmDeleteModal" data-id="${u.userId}"
											data-url="${pageContext.request.contextPath}/admin/users/delete">
											<i class="bi bi-trash-fill"></i>
										</button>

										<!-- Lock / Unlock -->
										<c:choose>
											<c:when test="${u.status eq 'active'}">
												<button type="button"
													class="btn btn-outline-secondary btn-sm me-2"
													title="Khóa tài khoản"
													onclick="window.location.href='${pageContext.request.contextPath}/admin/users/lock?id=${u.userId}'">
													<i class="bi bi-lock-fill"></i>
												</button>
											</c:when>
											<c:otherwise>
												<button type="button" class="btn btn-outline-success btn-sm me-2"
													title="Mở tài khoản"
													onclick="window.location.href='${pageContext.request.contextPath}/admin/users/unlock?id=${u.userId}'">
													<i class="bi bi-unlock-fill"></i>
												</button>
											</c:otherwise>
										</c:choose>
										<!-- Liên hệ -->
										<button type="button" class="btn btn-info btn-sm"
											title="Liên hệ người dùng"
											onclick="window.location.href='${pageContext.request.contextPath}/admin/users/contact?id=${u.userId}'">
											<i class="bi bi-envelope-fill"></i>
										</button>


									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
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
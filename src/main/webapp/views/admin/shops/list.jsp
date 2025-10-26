<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">

	<h3 class="mb-4 fw-bold text-primary-custom">
		<i class="bi bi-shop me-2"></i> Quản lý cửa hàng
	</h3>

	<!-- Nút thêm mới -->
	<div class="mb-3">
		<a href="${pageContext.request.contextPath}/admin/shops/add"
			class="btn btn-primary-custom"> <i class="bi bi-plus"></i> Thêm
			cửa hàng
		</a>
	</div>

	<!-- Bảng danh sách -->
	<div class="card shadow-sm border-0">
		<div class="card-body table-responsive">

			<%-- ======================== ALERT MESSAGES ======================== --%>
			<c:if test="${not empty param.message}">
				<div
					class="alert alert-success alert-dismissible fade show mb-3 shadow-sm"
					role="alert">
					<i class="bi bi-check-circle-fill me-2"></i>
					<c:choose>
						<c:when test="${param.message == 'DelSuccess'}">Xóa thành công!</c:when>
						<c:when test="${param.message == 'AddSuccess'}">Thêm thành công!</c:when>
						<c:when test="${param.message == 'EditSuccess'}">Cập nhật thành công!</c:when>
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
						<c:when test="${param.error == 'errorPost'}">Có lỗi trong quá trình sửa hoặc thêm!. Vui lòng thử lại.</c:when>
						<c:when test="${param.error == 'errorGet'}">Có lỗi trong quá trình lấy dữ liệu!</c:when>
						<c:otherwise>Có lỗi xảy ra. Vui lòng thử lại.</c:otherwise>
					</c:choose>
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
								<div class="d-flex align-items-center justify-content-center">
									<div
										class="rounded-circle border overflow-hidden d-flex align-items-center justify-content-center"
										style="width: 64px; height: 64px; background-color: #f8f9fa;">
										<img
											src="${empty s.logo 
        ? pageContext.request.contextPath.concat('/assets/images/shops/default-shop-logo.png')
        : pageContext.request.contextPath.concat('/assets/images/shops/').concat(s.logo.substring(s.logo.lastIndexOf('/') + 1))}"
											alt="avatar" class="rounded-circle border"
											style="width: 48px; height: 48px; object-fit: cover;">

									</div>
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
								<!-- Nút Sửa -->
								<button type="button"
									class="btn btn-outline-warning btn-sm me-2" title="Sửa"
									onclick="window.location.href='${pageContext.request.contextPath}/admin/shops/edit?id=${s.shopId}'">
									<i class="bi bi-pencil-square fs-5"></i>
								</button> <!-- Nút Xóa -->
								<button type="button" class="btn btn-outline-danger btn-sm"
									title="Xóa" data-bs-toggle="modal"
									data-bs-target="#confirmDeleteModal" data-id="${s.shopId}"
									data-url="${pageContext.request.contextPath}/admin/shops/delete">
									<i class="bi bi-trash-fill fs-5"></i>
								</button>
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




<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>


<!-- sửa lại -> products  -->
<div class="container-fluid">
	<h3 class="mb-4 fw-bold">Quản lý cửa hàng</h3>

	<!-- Nút thêm mới -->
	<div class="mb-3">
		<a href="${pageContext.request.contextPath}/admin/shops/add"
			class="btn btn-success"> 
            <i class="bi bi-shop me-2"></i> Thêm cửa hàng
		</a>
	</div>

	<div class="card shadow-sm">
		<div class="card-body">
			<table class="table align-middle mb-0">
				<thead class="table-light">
					<tr>
						<th>Tên cửa hàng</th>
						<th>Người sở hữu</th>
						<th>Mô tả</th>
						<th>Ngày tạo</th>
						<th class="text-center">Hành động</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="s" items="${shops}">
						<tr>
							<td>${s.name}</td>
							<td>
								<div class="fw-bold">${s.user.username}</div>
								<small class="text-muted">${s.user.email}</small>
							</td>
							<td>${s.description}</td>
							<td>${s.createdAt}</td>

							<td class="text-center">
								<!-- Edit -->
								<a href="${pageContext.request.contextPath}/admin/shops/edit?id=${s.shopId}"
								   class="text-warning me-3" title="Sửa">
								   <i class="bi bi-pencil-square"></i>
								</a> 

								<!-- Delete dùng modal chung -->
								<a href="javascript:void(0);"
								   class="text-danger me-3"
								   data-bs-toggle="modal"
								   data-bs-target="#confirmDeleteModal"
								   data-id="${s.shopId}"
								   data-url="${pageContext.request.contextPath}/admin/shops/delete"
								   title="Xóa">
								   <i class="bi bi-trash-fill"></i>
								</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<!-- Modal Xác nhận Xóa (dùng chung cho tất cả entity) -->
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
				<p>Bạn có chắc muốn xóa mục này không? Hành động này 
				   <strong>không thể hoàn tác</strong>.
				</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary rounded-pill px-4"
					data-bs-dismiss="modal">Hủy</button>
				<a id="deleteConfirmBtn" href="#" class="btn btn-danger rounded-pill px-4">Xóa</a>
			</div>
		</div>
	</div>
</div>

<!-- Import file JS dùng chung -->
<script src="${pageContext.request.contextPath}/assets/js/admin/modal-delete.js"></script>

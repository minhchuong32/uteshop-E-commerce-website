<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">
	<h3 class="text-primary-custom fw-bold mb-4">
		<i class="bi bi-truck"></i> Quản lý đơn vị vận chuyển
	</h3>

	<div class="mb-3 text-end">
		<button id="btnAddCarrier" class="btn btn-primary">
			<i class="bi bi-plus-circle"></i> Thêm đơn vị vận chuyển
		</button>
	</div>

	<div class="card shadow-sm">
		<div class="card-body">
			<table id="carrierTable"
				class="table table-bordered table-striped align-middle">
				<thead class="table-primary">
					<tr>
						<th>ID</th>
						<th>Tên đơn vị</th>
						<th>Phí vận chuyển</th>
						<th>Mô tả</th>
						<th class="text-center">Hành động</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="carrier" items="${carriers}">
						<tr>
							<td>${carrier.carrierId}</td>
							<td>${carrier.carrierName}</td>
							<td><fmt:formatNumber value="${carrier.carrierFee}"
									type="currency" currencySymbol="₫" /></td>
							<td>${carrier.carrierDescription}</td>
							<td class="text-center">
								<button class="btn btn-sm btn-warning editBtn"
									data-id="${carrier.carrierId}"
									data-name="${carrier.carrierName}"
									data-fee="${carrier.carrierFee}"
									data-description="${carrier.carrierDescription}">
									<i class="bi bi-pencil"></i> Sửa
								</button>
								<button class="btn btn-sm btn-danger deleteBtn"
									data-id="${carrier.carrierId}">
									<i class="bi bi-trash"></i> Xóa
								</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<!-- ====================== MODAL THÊM ====================== -->
<div class="modal fade" id="addCarrierModal" tabindex="-1"
	aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content rounded-4">
			<div class="modal-header bg-primary text-white">
				<h5 class="modal-title">
					<i class="bi bi-plus-circle me-2"></i>Thêm đơn vị vận chuyển
				</h5>
				<button type="button" class="btn-close btn-close-white"
					data-bs-dismiss="modal"></button>
			</div>
			<form id="addCarrierForm"
				action="${pageContext.request.contextPath}/admin/carriers/add"
				method="post">
				<div class="modal-body">
					<div class="mb-3">
						<label class="form-label fw-semibold">Tên đơn vị vận
							chuyển</label> <input type="text" class="form-control" name="carrierName"
							required>
					</div>
					<div class="mb-3">
						<label class="form-label fw-semibold">Phí vận chuyển (VNĐ)</label>
						<input type="number" class="form-control" name="carrierFee"
							min="0" required>
					</div>
					<div class="mb-3">
						<label class="form-label fw-semibold">Mô tả</label>
						<textarea class="form-control" name="carrierDescription" rows="3"></textarea>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button"
						class="btn btn-outline-secondary rounded-pill px-4"
						data-bs-dismiss="modal">Hủy</button>
					<button type="submit" class="btn btn-primary rounded-pill px-4">
						<i class="bi bi-save2 me-1"></i> Lưu
					</button>
				</div>
			</form>
		</div>
	</div>
</div>

<!-- ====================== MODAL SỬA ====================== -->
<div class="modal fade" id="editCarrierModal" tabindex="-1"
	aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content rounded-4">
			<div class="modal-header bg-warning text-dark">
				<h5 class="modal-title">
					<i class="bi bi-pencil-square me-2"></i>Sửa đơn vị vận chuyển
				</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
			</div>
			<form id="editCarrierForm"
				action="${pageContext.request.contextPath}/admin/carriers/edit"
				method="post">
				<input type="hidden" name="carrierId" id="editId">
				<div class="modal-body">
					<div class="mb-3">
						<label class="form-label fw-semibold">Tên đơn vị vận
							chuyển</label> <input type="text" class="form-control" name="carrierName"
							id="editName" required>
					</div>
					<div class="mb-3">
						<label class="form-label fw-semibold">Phí vận chuyển (VNĐ)</label>
						<input type="number" class="form-control" name="carrierFee"
							id="editFee" min="0" required>
					</div>
					<div class="mb-3">
						<label class="form-label fw-semibold">Mô tả</label>
						<textarea class="form-control" name="carrierDescription"
							id="editDescription" rows="3"></textarea>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button"
						class="btn btn-outline-secondary rounded-pill px-4"
						data-bs-dismiss="modal">Hủy</button>
					<button type="submit" class="btn btn-warning rounded-pill px-4">
						<i class="bi bi-save me-1"></i> Cập nhật
					</button>
				</div>
			</form>
		</div>
	</div>
</div>

<!-- ====================== MODAL XÓA ====================== -->
<div class="modal fade" id="confirmDeleteModal" tabindex="-1"
	aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header bg-danger text-white">
				<h5 class="modal-title">
					<i class="bi bi-exclamation-triangle-fill me-2"></i> Xác nhận xóa
				</h5>
				<button type="button" class="btn-close btn-close-white"
					data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<p>Bạn có chắc muốn xóa đơn vị vận chuyển này không?</p>
			</div>
			<div class="modal-footer">
				<button type="button"
					class="btn btn-outline-secondary rounded-pill px-4"
					data-bs-dismiss="modal">Hủy</button>
				<a id="deleteConfirmBtn" href="#"
					class="btn btn-danger rounded-pill px-4"> <i
					class="bi bi-trash-fill me-1"></i>Xóa
				</a>
			</div>
		</div>
	</div>
</div>
<!-- Biến JSP truyền cho file JS -->
<script>
    const contextPath = '<%= request.getContextPath() %>';
</script>

<!-- File JS xử lý riêng -->


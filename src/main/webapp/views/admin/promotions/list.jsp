<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<c:if test="${not empty param.message}">
	<div
		class="alert alert-success alert-dismissible fade show mb-3 shadow-sm"
		role="alert">
		<i class="bi bi-check-circle-fill me-2"></i>
		<c:choose>
			<c:when test="${param.message == 'DelSuccess'}">Xóa thành công!</c:when>
			<c:when test="${param.message == 'AddSuccess'}">Thêm  thành công!</c:when>
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

<h3 class="fw-bold text-primary-custom mb-4">
	<i class="bi bi-gift"></i> Quản lý khuyến mãi
</h3>

<div class="row align-items-stretch mb-4">
	<div class="col-md-6 d-flex">
		<div class="card shadow-sm flex-fill h-100">
			<div class="card-body d-flex flex-column">
				<h5 class="text-primary-custom fw-bold mb-3">
					<i class="bi bi-pie-chart"></i> Phân bố loại khuyến mãi
				</h5>
				<div
					class="flex-grow-1 d-flex align-items-center justify-content-center">
					<%-- Truyền dữ liệu cho JS qua data attributes --%>
					<canvas id="typeChart" data-percent-count="${percentCount}"
						data-fixed-count="${fixedCount}">
					</canvas>
				</div>
			</div>
		</div>
	</div>

	<div class="col-md-6 d-flex">
		<div class="card shadow-sm flex-fill h-100">
			<div class="card-body d-flex flex-column">
				<h5 class="text-primary-custom fw-bold mb-3">
					<i class="bi bi-graph-up"></i> Giá trị trung bình
				</h5>
				<div
					class="flex-grow-1 d-flex align-items-center justify-content-center">
					<%-- Truyền dữ liệu cho JS qua data attributes --%>
					<canvas id="avgChart" data-avg-percent="${avgPercent}"
						data-avg-fixed="${avgFixed}">
					</canvas>
				</div>
				<div class="mt-2 text-muted small">
					<i class="bi bi-info-circle"></i> <b>TB %:</b>
					<fmt:formatNumber value="${avgPercent}" pattern="#,##0.##" />
					% &nbsp; | &nbsp; <b>TB ₫:</b>
					<fmt:formatNumber value="${avgFixed}" type="currency"
						currencySymbol="₫" />
				</div>
			</div>
		</div>
	</div>
</div>

<div class="text-end mb-3">
	<a href="${pageContext.request.contextPath}/admin/promotions/add"
		class="btn btn-success"> <i class="bi bi-plus-circle"></i> Thêm
		khuyến mãi
	</a>
</div>

<div class="card shadow-sm">
	<div class="card-body">
		<table id="promotionTable" class="table table-striped align-middle">
			<thead class="table-light">
				<tr>
					<th>ID</th>
					<th>Shop</th>
					<th>Sản phẩm</th>
					<th>Loại giảm</th>
					<th>Giá trị</th>
					<th>Bắt đầu</th>
					<th>Kết thúc</th>
					<th class="text-center">Hành động</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="p" items="${promotions}">
					<tr>
						<td>${p.promotionId}</td>
						<td>${p.shop.name}</td>
						<td>${p.product != null ? p.product.name : '<i>Toàn shop</i>'}</td>
						<td><span
							class="badge ${p.discountType eq 'percent' ? 'bg-info' : 'bg-success'}">
								${p.discountType eq 'percent' ? 'Giảm %' : 'Giảm cố định'} </span></td>
						<td><strong> <c:choose>
									<c:when test="${p.discountType eq 'percent'}">${p.value}%</c:when>
									<c:otherwise>
										<fmt:formatNumber value="${p.value}" type="currency"
											currencySymbol="₫" />
									</c:otherwise>
								</c:choose>
						</strong></td>
						<td><fmt:formatDate value="${p.startDate}"
								pattern="dd/MM/yyyy" /></td>
						<td><fmt:formatDate value="${p.endDate}" pattern="dd/MM/yyyy" /></td>
						<td class="text-center"><a
							href="${pageContext.request.contextPath}/admin/promotions/edit?id=${p.promotionId}"
							class="btn btn-sm btn-warning"> <i class="bi bi-pencil"></i>
						</a>
							<button type="button" class="btn btn-sm btn-danger"
								data-bs-toggle="modal" data-bs-target="#confirmDeleteModal"
								data-delete-url="${pageContext.request.contextPath}/admin/promotions/delete?id=${p.promotionId}">
								<i class="bi bi-trash"></i>
							</button></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div class="modal fade" id="confirmDeleteModal" tabindex="-1">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content border-0 shadow-lg rounded-3">
			<div class="modal-header bg-danger text-white">
				<h5 class="modal-title">
					<i class="bi bi-exclamation-triangle-fill me-2"></i>Xác nhận xóa
				</h5>
				<button type="button" class="btn-close btn-close-white"
					data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				Bạn có chắc muốn xóa khuyến mãi này không?<br> <strong>Hành
					động này không thể hoàn tác.</strong>
			</div>
			<div class="modal-footer">
				<button class="btn btn-outline-secondary" data-bs-dismiss="modal">Hủy</button>
				<a id="deleteConfirmBtn" href="#" class="btn btn-danger"> <i
					class="bi bi-trash-fill me-1"></i> Xóa
				</a>
			</div>
		</div>
	</div>
</div>


<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>
<!-- ====================== BIỂU ĐỒ HIỆU SUẤT ====================== -->
<h4 class="fw-bold text-primary-custom mb-3 mt-4">
	<i class="bi bi-bar-chart-fill me-2"></i>Hiệu suất giao hàng
</h4>

<div class="card shadow-sm border-0 mb-4">
	<div class="card-body">
		<canvas id="deliveryChart" height="100"></canvas>
	</div>
</div>

<!-- ====================== THÔNG BÁO ====================== -->
<c:if test="${not empty sessionScope.message}">
	<div
		class="alert alert-success alert-dismissible fade show alert-floating"
		role="alert">
		<i class="bi bi-check-circle-fill me-2"></i>${sessionScope.message}
		<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
	</div>
	<c:remove var="message" scope="session" />
</c:if>

<c:if test="${not empty sessionScope.error}">
	<div
		class="alert alert-danger alert-dismissible fade show alert-floating"
		role="alert">
		<i class="bi bi-exclamation-triangle-fill me-2"></i>${sessionScope.error}
		<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
	</div>
	<c:remove var="error" scope="session" />
</c:if>

<!-- ====================== DANH SÁCH ĐƠN HÀNG ====================== -->
<h3 class="fw-bold text-primary-custom mb-3">
	<i class="bi bi-cart"></i> Quản lý đơn hàng
</h3>

<div class="card shadow-sm border-0">
	<div class="card-body">
		<!-- Bộ lọc nhanh -->
		<div class="row mb-3">
			<div class="col-md-3">
				<label class="form-label small">Lọc theo trạng thái đơn:</label> <select
					id="filterOrderStatus" class="form-select form-select-sm">
					<option value="">Tất cả</option>
					<option value="Mới">Mới</option>
					<option value="Đang giao">Đang giao</option>
					<option value="Đã giao">Đã giao</option>
					<option value="Đã hủy">Đã hủy</option>
				</select>
			</div>
			<div class="col-md-3">
				<label class="form-label small">Lọc theo trạng thái giao
					hàng:</label> <select id="filterDeliveryStatus"
					class="form-select form-select-sm">
					<option value="">Tất cả</option>
					<option value="Chờ xử lý">Chờ xử lý</option>
					<option value="Đã gán">Đã gán</option>
					<option value="Đang giao">Đang giao</option>
					<option value="Đã giao">Đã giao</option>
					<option value="Đã hủy">Đã hủy</option>
				</select>
			</div>
			<div class="col-md-3">
				<label class="form-label small">Lọc theo phương thức:</label> <select
					id="filterPayment" class="form-select form-select-sm">
					<option value="">Tất cả</option>
					<option value="COD">COD</option>
					<option value="MoMo">Chuyển khoản</option>
					<option value="VNPay">Ví điện tử</option>
				</select>
			</div>
			<div class="col-md-3">
				<button id="resetFilters"
					class="btn btn-outline-secondary btn-sm w-100 mt-4">
					<i class="bi bi-arrow-clockwise"></i> Đặt lại bộ lọc
				</button>
			</div>
		</div>

		<div class="table-responsive">
			<table id="ordersTable"
				class="table table-bordered table-hover align-middle mb-0"
				style="width: 100%">
				<thead class="text-center table-light">
					<tr>
						<th>ID Đơn hàng</th>
						<th>Khách hàng</th>
						<th>Địa chỉ</th>
						<th>Thanh toán</th>
						<th>Tổng tiền</th>
						<th>Trạng thái đơn</th>
						<th>Shipper</th>
						<th>Hãng vận chuyển</th>
						<th>Trạng thái giao hàng</th>
						<th>Ghi chú</th>
						<th>Ngày tạo</th>
						<th>Hành động</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="o" items="${orders}">
						<c:forEach var="d" items="${o.deliveries}">
							<tr>
								<td class="text-center"><b>#${o.orderId}</b></td>
								<td><b><c:out value="${o.user.name}" /></b><br> <small
									class="text-muted"> <c:out value="${o.user.email}" /><br>
										<c:out value="${o.user.phone}" />
								</small></td>
								<td><c:out value="${o.address}" /></td>
								<td class="text-center"><c:out value="${o.paymentMethod}" /></td>
								<td class="text-end"><fmt:formatNumber
										value="${o.totalAmount}" type="currency" currencySymbol="₫" />
								</td>
								<td class="text-center"><span
									class="badge bg-${
                        o.status == 'Đã giao' ? 'success' :
                        o.status == 'Mới' ? 'warning' :
                        o.status == 'Đang giao' ? 'info' :
                        o.status == 'Đã hủy' ? 'danger' : 'secondary'
                    }"><c:out
											value="${o.status}" /></span></td>

								<td><c:choose>
										<c:when test="${not empty d.shipper}">
											<b><c:out value="${d.shipper.name}" /></b>
											<br>
											<small class="text-muted"> <c:out
													value="${d.shipper.email}" /><br> <c:out
													value="${d.shipper.phone}" />
											</small>
										</c:when>
										<c:otherwise>
											<span class="text-muted"><i>Chưa có</i></span>
										</c:otherwise>
									</c:choose></td>

								<td><c:choose>
										<c:when test="${not empty d.carrier}">
											<b><c:out value="${d.carrier.carrierName}" /></b>
											<br>
											<small class="text-muted"><c:out
													value="${d.carrier.carrierDescription}" /></small>
										</c:when>
										<c:otherwise>
											<span class="text-muted"><i>Chưa chọn</i></span>
										</c:otherwise>
									</c:choose></td>

								<td class="text-center"><span
									class="badge bg-${
                        d.status == 'Đã giao' ? 'success' :
                        d.status == 'Đang giao' ? 'warning' :
                        d.status == 'Đã hủy' ? 'danger' :
                        d.status == 'Chờ xử lý' ? 'secondary' :
                        d.status == 'Đã gán' ? 'info' : 'dark'
                    }">
										<c:out value="${d.status}" />
								</span></td>

								<td class="text-center"><c:out
										value="${not empty d.noteText ? d.noteText : 'Chưa có'}" /></td>
								<td class="text-center"><fmt:formatDate
										value="${o.createdAt}" pattern="dd/MM/yyyy HH:mm" /></td>

								<td class="text-center">
									<div class="d-flex justify-content-center gap-1">
										<a
											href="${pageContext.request.contextPath}/admin/orders/form?orderId=${o.orderId}&deliveryId=${d.deliveryId}"
											class="btn btn-outline-primary btn-sm" title="Chỉnh sửa">
											<i class="bi bi-pencil-square"></i>
										</a>
										<!-- ===== Nút Xóa ===== -->
										<button type="button" class="btn btn-sm btn-danger"
											data-bs-toggle="modal" data-bs-target="#confirmDeleteOrderModal"
											data-delete-url="${pageContext.request.contextPath}/admin/orders/delete?orderId=${o.orderId}&deliveryId=${d.deliveryId}">
											<i class="bi bi-trash"></i>
										</button>
									</div>
								</td>
							</tr>
						</c:forEach>

					</c:forEach>
				</tbody>

			</table>
		</div>
	</div>
</div>
<!-- ====== MODAL XÓA ====== -->
<div class="modal fade" id="confirmDeleteOrderModal" tabindex="-1">
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
				Bạn có chắc muốn xóa đơn hàng này không?<br> <strong>Hành
					động này không thể hoàn tác.</strong>
			</div>
			<div class="modal-footer">
				<button class="btn btn-outline-secondary" data-bs-dismiss="modal">Hủy</button>
				<a id="deleteConfirmBtn" href="#" class="btn btn-danger"><i
					class="bi bi-trash-fill me-1"></i> Xóa</a>
			</div>
		</div>
	</div>
</div>
<!-- Script khởi tạo dữ liệu biểu đồ -->
<script>
// Dữ liệu cho biểu đồ hiệu suất giao hàng
const shipperNames = [], totalDeliveries = [], successRates = [];
<c:forEach var="s" items="${stats}">
    shipperNames.push("${s[0]}");
    totalDeliveries.push(${s[1]});
    successRates.push(${s[2]});
</c:forEach>

// Khởi tạo biểu đồ khi document ready
document.addEventListener('DOMContentLoaded', function() {
    if (typeof initDeliveryChart === 'function') {
        initDeliveryChart(shipperNames, totalDeliveries, successRates);
    }
});
</script>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>

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

<!-- ====================== BẢNG QUẢN LÝ VẬN CHUYỂN ====================== -->
<h3 class="fw-bold text-primary-custom mb-3">
	<i class="bi bi-truck"></i> Quản lý vận chuyển
</h3>

<div class="card shadow-sm border-0">
	<div class="card-body table-responsive">
		<table class="table table-bordered table-hover align-middle mb-0">
			<thead class="text-center">
				<tr>
					<th>ID</th>
					<th>Shipper</th>
					<th>Đơn hàng</th>
					<th>Trạng thái</th>
					<th>Ghi chú</th>
					<th>Ngày tạo</th>
					<th>Hành động</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="d" items="${deliveries}">
					<tr>
						<td class="text-center">${d.deliveryId}</td>
						<td><b>${d.shipper.name}</b><br> <small>${d.shipper.email}<br>${d.shipper.phone}</small>
						</td>
						<td><b>#${d.order.orderId}</b> - ${d.order.status}<br>
							${d.order.paymentMethod}<br>${d.order.address}<br> <fmt:formatNumber
								value="${d.order.totalAmount}" type="currency"
								currencySymbol="₫" /></td>
						<td class="text-center"><span
							class="badge bg-${
        d.status == 'Đã giao' ? 'success' :
        d.status == 'Đang giao' ? 'warning' :
        d.status == 'Đã hủy' ? 'danger' :
        d.status == 'Đã hoàn hàng' ? 'secondary' :
        d.status == 'Đã phân công' ? 'info' : 'dark'
    }">
								${d.status} </span></td>

						<td class="text-center">
							<button class="btn btn-outline-success btn-sm rounded-pill"
								data-bs-toggle="modal" data-bs-target="#addNoteModal"
								data-id="${d.deliveryId}" data-note="${d.noteText}">
								<i class="bi bi-plus-circle me-1"></i>Thêm ghi chú
							</button> <c:if test="${not empty d.noteText}">
								<div class="mt-2 text-muted small">
									<i class="bi bi-chat-left-text me-1"></i>${d.noteText}
								</div>
							</c:if>
						</td>
						<td class="text-center"><fmt:formatDate
								value="${d.createdAt}" pattern="dd/MM/yyyy HH:mm" /></td>
						<td class="text-center">
							<button class="btn btn-outline-primary btn-sm rounded-pill"
								data-bs-toggle="modal" data-bs-target="#editDeliveryModal"
								data-id="${d.deliveryId}" data-status="${d.status}"
								data-address="${d.order.address}"
								data-payment="${d.order.paymentMethod}">
								<i class="bi bi-pencil-square"></i>
							</button> <a href="javascript:void(0);"
							class="btn btn-danger btn-sm rounded-pill" data-bs-toggle="modal"
							data-bs-target="#confirmDeleteModal" data-id="${d.deliveryId}"
							data-url="${pageContext.request.contextPath}/admin/deliveries/delete">
								<i class="bi bi-trash-fill"></i>
						</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<!-- ====================== BIỂU ĐỒ HIỆU SUẤT ====================== -->
<h4 class="fw-bold text-primary-custom mb-3 mt-4">
	<i class="bi bi-bar-chart-fill me-2"></i>Hiệu suất giao hàng
</h4>

<div class="card shadow-sm border-0 mb-4">
	<div class="card-body">
		<canvas id="deliveryChart" height="100"></canvas>
	</div>
</div>

<!-- ====================== MODAL GHI CHÚ ====================== -->
<div class="modal fade" id="addNoteModal" tabindex="-1"
	aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<form class="modal-content"
			action="${pageContext.request.contextPath}/admin/deliveries/note"
			method="post">
			<div class="modal-header bg-primary-custom text-white">
				<h5 class="modal-title">
					<i class="bi bi-file-earmark-text me-2"></i>Thêm ghi chú
				</h5>
				<button type="button" class="btn-close btn-close-white"
					data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<input type="hidden" name="deliveryId" id="noteDeliveryId">
				<div class="mb-3">
					<label class="form-label fw-bold">Nội dung ghi chú</label>
					<textarea class="form-control" id="noteText" name="noteText"
						rows="3" placeholder="Nhập nội dung ghi chú..."></textarea>
				</div>
			</div>
			<div class="modal-footer">
				<button type="submit"
					class="btn btn-primary-custom rounded-pill px-4">
					<i class="bi bi-plus-circle me-1"></i>Thêm ghi chú
				</button>
			</div>
		</form>
	</div>
</div>

<!-- ====================== MODAL SỬA ====================== -->
<div class="modal fade" id="editDeliveryModal" tabindex="-1"
	aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<form class="modal-content"
			action="${pageContext.request.contextPath}/admin/deliveries/update"
			method="post">
			<div class="modal-header bg-primary-custom text-white">
				<h5 class="modal-title">
					<i class="bi bi-pencil-square me-2"></i>Chỉnh sửa thông tin giao
					hàng
				</h5>
				<button type="button" class="btn-close btn-close-white"
					data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<input type="hidden" name="deliveryId" id="editDeliveryId">

				<div class="mb-3">
					<label class="form-label fw-bold">Trạng thái giao hàng</label><select
						class="form-select" name="status" id="editStatus">
						<option value="Đã phân công">Đã phân công</option>
						<option value="Đang giao">Đang giao</option>
						<option value="Đã giao">Đã giao</option>
						<option value="Đã hủy">Đã hủy</option>
						<option value="Đã hoàn hàng">Đã hoàn hàng</option>
					</select>

				</div>


				<div class="mb-3">
					<label class="form-label fw-bold">Địa chỉ giao hàng</label> <input
						type="text" class="form-control" name="address" id="editAddress">
				</div>

				<div class="mb-3">
					<label class="form-label fw-bold">Phương thức thanh toán</label> <select
						class="form-select" name="paymentMethod" id="editPayment">
						<option value="COD">COD</option>
						<option value="Banking">Banking</option>
						<option value="Momo">Momo</option>
					</select>
				</div>
			</div>
			<div class="modal-footer">
				<button type="submit"
					class="btn btn-primary-custom rounded-pill px-4">
					<i class="bi bi-check2-circle me-1"></i>Xác nhận
				</button>
			</div>
		</form>
	</div>
</div>

<!-- ====================== MODAL XÓA ====================== -->
<div class="modal fade" id="confirmDeleteModal" tabindex="-1"
	aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header bg-danger text-white">
				<h5 class="modal-title">
					<i class="bi bi-exclamation-triangle-fill me-2"></i>Xác nhận xóa
				</h5>
				<button type="button" class="btn-close btn-close-white"
					data-bs-dismiss="modal"></button>
			</div>
			<div class="modal-body">
				<p>Bạn có chắc muốn xóa phiếu giao hàng này không?</p>
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

<!-- ====================== SCRIPT ====================== -->
<script>
document.addEventListener("DOMContentLoaded", function () {
    // Ghi chú
    const addNoteModal = document.getElementById('addNoteModal');
    addNoteModal.addEventListener('show.bs.modal', e => {
        const btn = e.relatedTarget;
        document.getElementById('noteDeliveryId').value = btn.getAttribute('data-id');
        document.getElementById('noteText').value = btn.getAttribute('data-note') || "";
    });

    // Xóa
    const deleteModal = document.getElementById('confirmDeleteModal');
    const confirmBtn = document.getElementById('deleteConfirmBtn');
    deleteModal.addEventListener('show.bs.modal', e => {
        const btn = e.relatedTarget;
        confirmBtn.href = btn.getAttribute('data-url') + '?id=' + btn.getAttribute('data-id');
    });

    // Sửa
    const editModal = document.getElementById('editDeliveryModal');
    editModal.addEventListener('show.bs.modal', e => {
        const btn = e.relatedTarget;
        document.getElementById('editDeliveryId').value = btn.getAttribute('data-id');
        document.getElementById('editStatus').value = btn.getAttribute('data-status');
        document.getElementById('editAddress').value = btn.getAttribute('data-address');
        document.getElementById('editPayment').value = btn.getAttribute('data-payment');
    });

    // Alert tự ẩn
    setTimeout(() => {
        document.querySelectorAll('.alert-floating').forEach(a => new bootstrap.Alert(a).close());
    }, 5000);

    // Vẽ biểu đồ Chart.js
    const shipperNames = [], totalDeliveries = [], successRates = [];
    <c:forEach var="s" items="${stats}">
        shipperNames.push("${s[0]}");
        totalDeliveries.push(${s[1]});
        successRates.push(${s[2]});
    </c:forEach>;
    new Chart(document.getElementById('deliveryChart'), {
        type: 'bar',
        data: {
            labels: shipperNames,
            datasets: [
                { label: 'Tổng đơn hàng', data: totalDeliveries, backgroundColor: 'rgba(0,85,141,0.6)' },
                { label: 'Tỷ lệ giao thành công (%)', data: successRates, backgroundColor: 'rgba(40,167,69,0.6)', yAxisID: 'y1' }
            ]
        },
        options: {
            responsive: true,
            scales: {
                y: { beginAtZero: true, title: { display: true, text: 'Tổng số đơn' } },
                y1: { beginAtZero: true, position: 'right', title: { display: true, text: 'Tỷ lệ (%)' }, max: 100, grid: { drawOnChartArea: false } }
            }
        }
    });
});
</script>

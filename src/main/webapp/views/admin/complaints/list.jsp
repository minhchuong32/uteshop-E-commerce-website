<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>
<h3 class="fw-bold text-primary-custom mb-4">
	<i class="bi bi-chat-dots me-2"></i> Quản lý khiếu nại
</h3>
<div class="table-responsive">
	<table id="complaintTable" class="table table-striped align-middle">
		<thead class="table-light">
			<tr>
				<th class="text-center">ID</th>
				<th>Người gửi</th>
				<th>Đơn hàng</th>
				<th>Tiêu đề</th>
				<th>Nội dung</th>
				<th>Trạng thái</th>
				<th>Ngày tạo</th>
				<th class="text-center">Hành động</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="c" items="${complaints}">
				<tr>
					<td style="min-width: 60px !important;">${c.complaintId}</td>
					<td>${c.user.name}</td>
					<td>${c.order.orderId}</td>
					<td>${c.title}</td>
					<td>${c.content}</td>
					<td><span class="badge bg-info">${c.status}</span></td>
					<td>${c.createdAt}</td>
					<td class="text-center">
						<div class="btn-group" role="group">

							<!-- Nút Chat -->
							<button type="button" class="btn btn-success btn-sm me-2"
								title="Trao đổi khiếu nại"
								onclick="window.location.href='${pageContext.request.contextPath}/admin/complaints/chat?id=${c.complaintId}'">
								<i class="bi bi-chat-dots-fill"></i>
							</button>

							<!-- Nút Sửa -->
							<button type="button" class="btn btn-primary btn-sm me-2"
								title="Chỉnh sửa"
								onclick="window.location.href='${pageContext.request.contextPath}/admin/complaints/edit?id=${c.complaintId}'">
								<i class="bi bi-pencil-fill"></i>
							</button>

							<!-- Nút Xóa -->
							<button type="button" class="btn btn-danger btn-sm deleteBtn"
								title="Xóa khiếu nại" data-bs-toggle="modal"
								data-bs-target="#confirmDeleteComplaintModal"
								data-id="${c.complaintId}"
								data-url="${pageContext.request.contextPath}/admin/complaints/delete">
								<i class="bi bi-trash-fill"></i>
							</button>

						</div>

					</td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<!-- Modal xác nhận xóa khiếu nại -->
<div class="modal fade" id="confirmDeleteComplaintModal" tabindex="-1"
	aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content shadow-lg border-0 rounded-3">
			<div class="modal-header bg-danger text-white">
				<h5 class="modal-title">
					<i class="bi bi-exclamation-triangle-fill me-2"></i> Xác nhận xóa
					khiếu nại
				</h5>
				<button type="button" class="btn-close btn-close-white"
					data-bs-dismiss="modal" aria-label="Đóng"></button>
			</div>
			<div class="modal-body">
				<p>
					Bạn có chắc muốn xóa <strong>khiếu nại này</strong> không? Hành
					động này <strong>không thể hoàn tác</strong>.
				</p>
			</div>
			<div class="modal-footer">
				<button type="button"
					class="btn btn-outline-secondary rounded-pill px-4"
					data-bs-dismiss="modal">Hủy</button>
				<a id="deleteComplaintBtn" href="#"
					class="btn btn-danger rounded-pill px-4"> <i
					class="bi bi-trash-fill me-1"></i> Xóa
				</a>
			</div>
		</div>
	</div>
</div>


<h3 class="fw-bold text-primary-custom mb-4 mt-4">
	<i class="bi bi-graph-up"></i> Phân tích dữ liệu khiếu nại
</h3>

<!-- Biểu đồ -->
<div class="row g-4">
	<!-- Theo trạng thái -->
	<div class="col-md-6">
		<div class="card shadow-sm">
			<div class="card-body">
				<h5 class="fw-bold mb-3">
					<i class="bi bi-pie-chart-fill"></i> Tỷ lệ khiếu nại theo trạng
					thái
				</h5>
				<canvas id="statusChart"></canvas>
			</div>
		</div>
	</div>

	<!-- Theo tháng -->
	<div class="col-md-6">
		<div class="card shadow-sm">
			<div class="card-body">
				<h5 class="fw-bold mb-3">
					<i class="bi bi-bar-chart-line-fill"></i> Số khiếu nại theo tháng
				</h5>
				<canvas id="monthChart"></canvas>
			</div>
		</div>
		<div class="card shadow-sm text-center">
			<div class="card-body">
				<i class="bi bi-chat-dots-fill fs-2 text-primary mb-2"></i>
				<h6>Tổng số khiếu nại</h6>
				<h3 class="fw-bold">${totalComplaints}</h3>
			</div>
		</div>
	</div>
</div>

<!-- Top người gửi -->
<div class="card shadow-sm mt-4">
	<div class="card-body">
		<h5 class="fw-bold mb-3">
			<i class="bi bi-person-lines-fill"></i> Top người gửi khiếu nại
		</h5>
		<table class="table table-bordered align-middle text-center">
			<thead class="table-light">
				<tr>
					<th>Người gửi</th>
					<th>Số khiếu nại</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="t" items="${topUsers}">
					<tr>
						<td>${t[0]}</td>
						<td>${t[1]}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<!-- Script khởi tạo dữ liệu cho biểu đồ -->
<script>
// Dữ liệu trạng thái
const statusLabels = [
    <c:forEach var="s" items="${statusData}" varStatus="i">
        "${s[0]}"${!i.last ? ',' : ''}
    </c:forEach>
];
const statusCounts = [
    <c:forEach var="s" items="${statusData}" varStatus="i">
        ${s[1]}${!i.last ? ',' : ''}
    </c:forEach>
];

// Dữ liệu theo tháng
const monthLabels = [
    <c:forEach var="m" items="${monthData}" varStatus="i">
        "Tháng ${m[0]}"${!i.last ? ',' : ''}
    </c:forEach>
];
const monthCounts = [
    <c:forEach var="m" items="${monthData}" varStatus="i">
        ${m[1]}${!i.last ? ',' : ''}
    </c:forEach>
];

// Khởi tạo biểu đồ khi document ready
document.addEventListener('DOMContentLoaded', function() {
    if (typeof initStatusChart === 'function') {
        initStatusChart(statusLabels, statusCounts);
    }
    if (typeof initMonthChart === 'function') {
        initMonthChart(monthLabels, monthCounts);
    }
});
</script>

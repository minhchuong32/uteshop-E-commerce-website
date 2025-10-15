<%@ page contentType="text/html;charset=UTF-8"%>
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
						<!-- Nút Chat --> <a
						href="${pageContext.request.contextPath}/admin/complaints/chat?id=${c.complaintId}"
						class="text-success me-3" title="Trao đổi khiếu nại"> <i
							class="bi bi-chat-dots-fill fs-5"></i>
					</a> <!-- Nút Sửa --> <a
						href="${pageContext.request.contextPath}/admin/complaints/edit?id=${c.complaintId}"
						class="text-primary me-3" title="Chỉnh sửa"> <i
							class="bi bi-pencil-fill"></i>
					</a> <!-- Nút Xóa --> <a href="javascript:void(0);" class="text-danger"
						data-bs-toggle="modal"
						data-bs-target="#confirmDeleteComplaintModal"
						data-id="${c.complaintId}"
						data-url="${pageContext.request.contextPath}/admin/complaints/delete"
						title="Xóa khiếu nại"> <i class="bi bi-trash-fill fs-5"></i>
					</a>
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
<!-- ====== SCRIPT ====== -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script
	src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"></script>
<script
	src="https://cdn.datatables.net/1.13.7/js/dataTables.bootstrap5.min.js"></script>

<!-- Chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
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

const ctx1 = document.getElementById('statusChart');
new Chart(ctx1, {
    type: 'pie',
    data: {
        labels: statusLabels,
        datasets: [{
            data: statusCounts,
            backgroundColor: ['#007bff', '#ffc107', '#28a745', '#dc3545', '#6c757d']
        }]
    }
});

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
const ctx2 = document.getElementById('monthChart');
new Chart(ctx2, {
    type: 'bar',
    data: {
        labels: monthLabels,
        datasets: [{
            label: 'Số khiếu nại',
            data: monthCounts,
            backgroundColor: 'rgba(54, 162, 235, 0.6)',
            borderColor: 'rgba(54, 162, 235, 1)',
            borderWidth: 1
        }]
    },
    options: { scales: { y: { beginAtZero: true } } }
});

// DataTable
$('#complaintTable').DataTable({
  pageLength: 5,
  lengthMenu: [[5, 10, 25, 50, -1], [5, 10, 25, 50, "Tất cả"]],
  language: {
    lengthMenu: "Hiển thị _MENU_ dòng",
    search: "Tìm kiếm:",
    paginate: { previous: "Trước", next: "Sau" },
    info: "Hiển thị _START_–_END_ / _TOTAL_ Khiếu nại",
    emptyTable: "Không có dữ liệu"
  }
});

document.addEventListener("DOMContentLoaded", function() {
		const deleteModal = document
				.getElementById('confirmDeleteComplaintModal');
		const confirmBtn = document.getElementById('deleteComplaintBtn');

		if (deleteModal) {
			deleteModal.addEventListener('show.bs.modal', function(event) {
				const button = event.relatedTarget; // nút mở modal
				const itemId = button.getAttribute('data-id');
				const baseUrl = button.getAttribute('data-url');

				if (itemId && baseUrl) {
					const deleteUrl = `${baseUrl}?id=${itemId}`;
					confirmBtn.setAttribute('href', deleteUrl);
				}
			});
		}
	});
</script>



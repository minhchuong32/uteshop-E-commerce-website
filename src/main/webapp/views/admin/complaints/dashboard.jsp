<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h3 class="mb-4">Quản lý khiếu nại</h3>
<div class="table-responsive">
	<table class="table table-bordered align-middle">
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
					<td style=" min-width: 60px !important;">${c.complaintId}</td>
					<td>${c.user.name}</td>
					<td>${c.order.orderId}</td>
					<td>${c.title}</td>
					<td>${c.content}</td>
					<td><span class="badge bg-info">${c.status}</span></td>
					<td>${c.createdAt}</td>
					<td class="text-center"><a
						href="${pageContext.request.contextPath}/admin/complaints/edit?id=${c.complaintId}"
						class="text-primary me-3"><i class="bi bi-pencil-fill"></i></a> <a
						href="javascript:void(0);" class="text-danger"
						data-bs-toggle="modal"
						data-bs-target="#confirmDeleteComplaintModal"
						data-id="${c.complaintId}"
						data-url="${pageContext.request.contextPath}/admin/complaints/delete"
						title="Xóa khiếu nại"> <i class="bi bi-trash-fill fs-5"></i>
					</a></td>
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

<script>
document.addEventListener("DOMContentLoaded", function () {
    const deleteModal = document.getElementById('confirmDeleteComplaintModal');
    const confirmBtn = document.getElementById('deleteComplaintBtn');

    if (deleteModal) {
        deleteModal.addEventListener('show.bs.modal', function (event) {
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



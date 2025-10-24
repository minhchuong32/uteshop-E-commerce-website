<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid px-4">
	<%-- ======================== ALERT MESSAGES ======================== --%>
	<c:if test="${not empty param.message}">
		<div
			class="alert alert-success alert-dismissible fade show mb-3 shadow-sm"
			role="alert">
			<i class="bi bi-check-circle-fill me-2"></i>
			<c:choose>
				<c:when test="${param.message == 'replySuccess'}">Gửi phản hồi thành công!</c:when>
				<c:when test="${param.message == 'deleteSuccess'}">Xóa liên hệ thành công!</c:when>
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
				<c:when test="${param.error == 'replyFailed'}">Gửi phản hồi thất bại. Vui lòng thử lại.</c:when>
				<c:when test="${param.error == 'invalidId'}">ID không hợp lệ.</c:when>
				<c:otherwise>Có lỗi xảy ra. Vui lòng thử lại.</c:otherwise>
			</c:choose>
			<button type="button" class="btn-close" data-bs-dismiss="alert"
				aria-label="Đóng"></button>
		</div>
	</c:if>

	<%-- ======================== MAIN CONTENT CARD ======================== --%>
	<div class="card shadow-sm border-0">
		<h3 class="text-primary-custom fw-bold mb-4">
			<i class="bi bi-envelope-paper-fill me-2"></i> Quản lý Liên hệ
		</h3>
		<div class="card-body p-4">
			<div class="table-responsive">
				<table id="contactTable" class="table table-hover align-middle">
					<thead class="table-light">
						<tr>
							<th scope="col">#</th>
							<th scope="col">Họ Tên</th>
							<th scope="col">Email</th>
							<th scope="col">Nội dung</th>
							<th scope="col">Ngày gửi</th>
							<th scope="col" class="text-center">Hành động</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${contacts}" var="contact" varStatus="loop">
							<tr>
								<th scope="row">${loop.index + 1}</th>
								<td><c:out value="${contact.fullName}" /></td>
								<td><c:out value="${contact.email}" /></td>
								<td
									style="max-width: 350px; white-space: pre-wrap; word-break: break-word;">
									<c:out value="${contact.content}" />
								</td>
								<td><fmt:formatDate value="${contact.createdAt}"
										pattern="dd-MM-yyyy HH:mm" /></td>
								<td class="text-center">
									<div class="btn-group btn-group-sm">
										<%-- Nút phản hồi --%>
										<a
											href="${pageContext.request.contextPath}/admin/contacts/reply?id=${contact.contactId}"
											class="btn btn-outline-primary" title="Phản hồi"> <i
											class="bi bi-reply-fill"></i>
										</a>

										<%-- Nút xóa kích hoạt modal --%>
										<button type="button" class="btn btn-outline-danger"
											data-bs-toggle="modal" data-bs-target="#confirmDeleteModal"
											data-id="${contact.contactId}"
											data-url="${pageContext.request.contextPath}/admin/contacts/delete"
											title="Xóa">
											<i class="bi bi-trash-fill"></i>
										</button>
									</div>
								</td>
							</tr>
						</c:forEach>
						<c:if test="${empty contacts}">
							<tr>
								<td colspan="6" class="text-center text-muted py-4">Chưa có
									liên hệ nào.</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<%-- ======================== DELETE CONFIRMATION MODAL ======================== --%>
<div class="modal fade" id="confirmDeleteModal" tabindex="-1"
	aria-labelledby="deleteModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content shadow">
			<div class="modal-header">
				<h5 class="modal-title" id="deleteModalLabel">
					<i class="bi bi-exclamation-triangle text-danger me-2"></i>Xác nhận
					Xóa
				</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">Bạn có chắc chắn muốn xóa mục này
				không? Hành động này không thể hoàn tác.</div>
			<div class="modal-footer border-0">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">Hủy</button>
				<%-- JavaScript sẽ điền href cho nút này --%>
				<a href="#" id="deleteConfirmBtn" class="btn btn-danger">Xóa</a>
			</div>
		</div>
	</div>
</div>

<script>
  // Tự động ẩn alert sau 3 giây
  document.addEventListener("DOMContentLoaded", function() {
    const alertEl = document.querySelector(".alert");
    if (alertEl) {
      setTimeout(() => {
        alertEl.classList.add("fade");
        alertEl.style.transition = "opacity 0.5s ease";
        alertEl.style.opacity = "0";
        setTimeout(() => alertEl.remove(), 500); // Xóa khỏi DOM sau khi mờ
      }, 3000);
    }
  });
</script>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid px-4">
	<div class="card shadow-sm">
		<div class="card-header bg-primary-custom">
			<h4 class="mb-0 text-white">Chi Tiết & Cập Nhật Khiếu Nại</h4>
		</div>
		<div class="card-body p-4">
			<form action="${pageContext.request.contextPath}/admin/complaints/edit" method="post" enctype="multipart/form-data">
				<input type="hidden" name="complaintId" value="${complaint.complaintId}" />

				<div class="mb-3">
					<label class="form-label fw-bold">Tiêu đề</label>
					<input type="text" class="form-control-plaintext ps-2" value="${complaint.title}" readonly />
				</div>

				<div class="mb-3">
					<label class="form-label fw-bold">Nội dung</label>
					<div class="form-control-plaintext ps-2 border rounded p-2" style="min-height: 100px;">${complaint.content}</div>
				</div>

				<div class="mb-4">
					<label class="form-label fw-bold">File đính kèm hiện tại</label>
					<div class="border rounded p-3 bg-light">
						<c:choose>
							<c:when test="${not empty complaint.attachment}">
								<c:set var="fileExt" value="${fn:toLowerCase(fn:substringAfter(complaint.attachment, '.'))}" />
								<c:set var="filePath" value="${pageContext.request.contextPath}/assets/images${complaint.attachment}" />
								
								<c:if test="${fileExt == 'png' || fileExt == 'jpg' || fileExt == 'jpeg' || fileExt == 'gif'}">
									<a href="${filePath}" data-bs-toggle="tooltip" title="Nhấn để xem ảnh gốc">
										<img src="${filePath}" alt="File hiện tại" class="img-thumbnail" style="max-width: 200px; max-height: 150px;">
									</a>
								</c:if>
								<c:if test="${fileExt != 'png' && fileExt != 'jpg' && fileExt != 'jpeg' && fileExt != 'gif'}">
									<a href="${filePath}" download class="btn btn-outline-secondary btn-sm">
										<i class="bi bi-download me-2"></i> Tải xuống: ${fn:substringAfter(complaint.attachment, 'complaints/')}
									</a>
								</c:if>
							</c:when>
							<c:otherwise>
								<span class="text-muted fst-italic">Không có file đính kèm.</span>
							</c:otherwise>
						</c:choose>
					</div>
				</div>

				<div class="mb-4">
					<label for="statusSelect" class="form-label fw-bold">Trạng thái</label>
					<select class="form-select" name="status" id="statusSelect">
						<option value="Chờ xử lý" ${complaint.status eq 'Chờ xử lý' ? 'selected' : ''}>Chờ xử lý</option>
						<option value="Đang xử lý" ${complaint.status eq 'Đang xử lý' ? 'selected' : ''}>Đang xử lý</option>
						<option value="Đã giải quyết" ${complaint.status eq 'Đã giải quyết' ? 'selected' : ''}>Đã giải quyết</option>
					</select>
				</div>
				
				<hr class="hr-primary" style="opacity: 0.2;">

				<div class="d-flex justify-content-end gap-2">
					<a href="${pageContext.request.contextPath}/admin/complaints" class="btn btn-outline-secondary">
						<i class="bi bi-arrow-left me-1"></i> Quay lại
					</a>
					<button type="submit" class="btn btn-primary-custom">
						<i class="bi bi-check-lg me-1"></i> Lưu thay đổi
					</button>
				</div>
			</form>
		</div>
	</div>
</div>
	<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Thông báo của tôi | UteShop</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body class="bg-light">
	<div class="container py-5">
		<h3 class="mb-4 text-center text-primary fw-bold">
			<i class="bi bi-bell-fill me-2"></i>Thông báo của bạn
		</h3>

		<c:choose>
			<c:when test="${empty notifications}">
				<div class="text-center text-muted py-5">
					<i class="bi bi-bell-slash fs-2"></i>
					<p>Hiện bạn chưa có thông báo nào.</p>
				</div>
			</c:when>
			<c:otherwise>
				<c:forEach var="n" items="${notifications}">
					<div class="card mb-3 shadow-sm">
						<div class="card-body">
							<h6 class="fw-bold mb-1 text-primary">${n.message}</h6>
							<small class="text-muted"> <fmt:formatDate
									value="${n.createdAt}" pattern="dd/MM/yyyy HH:mm" />
							</small>

							<!-- Nút xem chi tiết -->
							<div class="mt-2">
								<a class="btn btn-sm btn-outline-primary"
									href="${pageContext.request.contextPath}/user/notifications?id=${n.relatedComplaintId}">
									<i class="bi bi-eye"></i> Xem chi tiết
								</a>
							</div>

							<!-- Collapse chi tiết -->
							<c:if test="${selectedId == n.relatedComplaintId}">
								<div class="mt-3 border-top pt-3">
									<h6 class="text-secondary mb-2">
										<i class="bi bi-chat-left-text"></i> Chi tiết khiếu nại:
										${selectedComplaint.title}
									</h6>
									<p class="mb-2">${selectedComplaint.content}</p>
									<p>
										<strong>Trạng thái:</strong> ${selectedComplaint.status}
									</p>

									<div class="bg-light rounded p-3"
										style="max-height: 300px; overflow-y: auto;">
										<c:forEach var="m" items="${messages}">
											<div class="mb-2">
												<strong
													class="${m.sender.role eq 'ADMIN' ? 'text-danger' : 'text-primary'}">
													${m.sender.username}: </strong> <span>${m.content}</span><br> <small
													class="text-muted"> <fmt:formatDate
														value="${m.createdAt}" pattern="dd/MM/yyyy HH:mm" />
												</small>
											</div>
										</c:forEach>
									</div>

									<!-- Nút chat realtime -->
									<div class="text-end mt-3">
										<a
											href="${pageContext.request.contextPath}/user/chat?complaintId=${selectedComplaint.complaintId}"
											class="btn btn-sm btn-success"> <i
											class="bi bi-chat-dots-fill"></i> Chat với Admin
										</a>
									</div>
								</div>
							</c:if>

						</div>
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>

		<div class="text-center mt-4">
			<a href="${pageContext.request.contextPath}/user/home"
				class="btn btn-outline-secondary"> <i class="bi bi-arrow-left"></i>
				Quay lại trang chủ
			</a>
		</div>
	</div>
</body>
</html>

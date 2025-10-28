<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<%-- Thêm logging để debug --%>
<c:if test="${empty pageContext.request.contextPath}">
    <script>console.error('contextPath is empty');</script>
</c:if>
<c:if test="${empty complaint.complaintId}">
    <script>console.error('complaint.complaintId is empty');</script>
</c:if>
<c:if test="${empty requestScope.account.userId}">
    <script>console.error('account.userId is empty');</script>
</c:if>

<%-- QUAN TRỌNG: Di chuyển data attributes vào div thay vì body --%>
<div id="chatContainer" 
    data-context-path="${pageContext.request.contextPath}"
    data-complaint-id="${complaint.complaintId}"
    data-current-user-id="${requestScope.account.userId}">

<div class="container py-5">
	<h4 class="text-center text-primary-custom mb-4">
		<i class="bi bi-chat-dots"></i> Trao đổi khiếu nại #${complaint.complaintId}
	</h4>
	<p><strong>Tiêu đề:</strong> ${complaint.title}</p>
	<hr>

	<div id="errorMessageContainer" class="mb-3"></div>

	<div id="chatBox" class="border bg-white rounded p-3 mb-3" style="height: 450px; overflow-y: auto;">
		<%-- Lịch sử tin nhắn --%>
		<c:forEach var="m" items="${messages}">
			<c:set var="isSentByCurrentUser" value="${m.senderId == requestScope.account.userId}" />
			<c:set var="avatarPath" value="${not empty m.senderAvatar ? m.senderAvatar : '/avatars/default.jpg'}" />

			<div class="message-container mb-3 ${isSentByCurrentUser ? 'sent' : 'received'}" data-sender-id="${m.senderId}">
				<img src="${pageContext.request.contextPath}/assets/images${avatarPath}"
					 alt="Avatar của ${m.senderUsername}" class="chat-avatar"
					 onerror="this.src='${pageContext.request.contextPath}/assets/images/avatars/default.jpg';">
				
				<div class="message-bubble d-inline-block p-2 rounded ${isSentByCurrentUser ? 'bg-primary-custom' : 'bg-secondary'} text-white">
					<strong>${isSentByCurrentUser ? 'Bạn' : m.senderUsername}:</strong><br>

					<c:choose>
						<c:when test="${m.type == 'FILE'}">
							<c:set var="fileExt" value="${fn:toLowerCase(fn:substringAfter(m.originalFilename, '.'))}" />
							<c:choose>
								<c:when test="${fn:contains('png,jpg,jpeg,gif,webp', fileExt)}">
									<div class="file-preview mt-2">
										<a href="${pageContext.request.contextPath}${m.content}" target="_blank">
											<img src="${pageContext.request.contextPath}${m.content}" alt="${m.originalFilename}" 
												 class="img-fluid rounded" style="max-height: 200px; cursor: pointer;">
										</a>
										<div class="file-info mt-1">
											<a href="${pageContext.request.contextPath}${m.content}" download="${m.originalFilename}" class="text-white text-decoration-none">
												${m.originalFilename} <i class="bi bi-download"></i>
											</a>
										</div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="file-attachment p-2 rounded" style="background-color: rgba(255, 255, 255, 0.2);">
										<a href="${pageContext.request.contextPath}${m.content}" download="${m.originalFilename}" 
										   title="Tải xuống ${m.originalFilename}" class="text-white text-decoration-none d-flex align-items-center">
											<i class="bi bi-file-earmark-arrow-down fs-4 me-3"></i>
											<div class="flex-grow-1">
												<div class="fw-bold">${m.originalFilename}</div>
											</div>
											<i class="bi bi-download ms-2"></i>
										</a>
									</div>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:out value="${m.content}" />
						</c:otherwise>
					</c:choose>

					<br>
					<small style="opacity: 0.8;">${m.createdAt}</small>
				</div>
			</div>
		</c:forEach>
	</div>

	<%-- Phần input --%>
	<div class="d-flex align-items-center gap-2">
		<a href="${pageContext.request.contextPath}/admin/complaints" class="btn btn-secondary">
			<i class="bi bi-arrow-left"></i>
		</a>
		<div class="input-group flex-grow-1">
			<input type="file" id="fileInput" class="d-none">
			<button id="attachFileButton" class="btn btn-outline-secondary" type="button" title="Đính kèm file">
				<i class="bi bi-paperclip"></i>
			</button>
			<input id="messageInput" type="text" class="form-control" placeholder="Nhập tin nhắn..." autocomplete="off">
			<button id="sendButton" class="btn btn-primary-custom" type="button">
				<i class="bi bi-send-fill"></i> Gửi
			</button>
		</div>
	</div>
</div>

</div><%-- Đóng chatContainer --%>

<script src="${pageContext.request.contextPath}/assets/js/chat-socket.js"></script>
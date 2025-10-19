<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Chat với Admin | UteShop</title>
</head>

<body class="bg-light">
	<div class="container py-5">
		<h4 class="text-center text-primary-custom mb-4">
			<i class="bi bi-chat-dots"></i> Chat với Admin - Khiếu nại
			#${complaint.complaintId}
		</h4>
		<p>
			<strong>Tiêu đề:</strong> ${complaint.title}
		</p>
		<hr>

		<div id="chatBox" class="border bg-white rounded p-3 mb-3"
			style="height: 450px; overflow-y: auto;">
			<c:forEach var="m" items="${messages}">
				<c:choose>
					<c:when test="${m.sender.userId == sessionScope.account.userId}">
						<div class="text-end mb-3">
							<div
								class="d-inline-block p-2 rounded bg-primary-custom text-white"
								style="max-width: 70%;">
								<strong>Bạn:</strong><br> ${m.content}<br> <small
									style="opacity: 0.8;"> <fmt:formatDate
										value="${m.createdAt}" pattern="dd/MM/yyyy HH:mm" />
								</small>
							</div>
						</div>
					</c:when>

					<c:otherwise>
						<div class="text-start mb-3">
							<div class="d-inline-block p-2 rounded bg-danger text-white"
								style="max-width: 70%;">
								<strong>${m.sender.username}:</strong><br> ${m.content}<br>
								<small style="opacity: 0.8;"> <fmt:formatDate
										value="${m.createdAt}" pattern="dd/MM/yyyy HH:mm" />
								</small>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>

		<form method="post"
			action="${pageContext.request.contextPath}/user/chat/send">
			<input type="hidden" name="complaintId"
				value="${complaint.complaintId}">
			<div class="input-group">
				<input name="content" type="text" class="form-control"
					placeholder="Nhập tin nhắn..." required autocomplete="off">
				<button class="btn btn-primary-custom" type="submit">
					<i class="bi bi-send-fill"></i> Gửi
				</button>
			</div>
		</form>
	</div>

	<!-- Tự động cuộn xuống cuối -->
	<script>
    const chatBox = document.getElementById('chatBox');
    chatBox.scrollTop = chatBox.scrollHeight;
</script>

</body>
</html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Trao đổi khiếu nại | Admin - UteShop</title>

</head>

<body class="bg-light">
	<div class="container py-5">

		<h4 class="text-center text-primary-custom mb-3">
			<i class="bi bi-chat-square-text"></i> Trao đổi khiếu nại
			#${complaint.complaintId}
		</h4>
		<p>
			<strong>Người gửi:</strong> ${complaint.user.username}
		</p>
		<p>
			<strong>Tiêu đề:</strong> ${complaint.title}
		</p>
		<hr>

		<div id="chatBox" class="border bg-white rounded p-3 mb-3"
			style="height: 450px; overflow-y: auto;">
			<%-- Lịch sử tin nhắn --%>
			<c:forEach var="m" items="${messages}">
				<c:choose>
					<c:when test="${m.sender.userId == requestScope.account.userId}">
						<div class="text-end mb-3">
							<div
								class="d-inline-block p-2 rounded bg-primary-custom text-white"
								style="max-width: 70%;">
								<strong>Bạn:</strong><br> ${m.content}<br> <small
									style="opacity: 0.8;"><fmt:formatDate
										value="${m.createdAt}" pattern="dd/MM/yyyy HH:mm" /></small>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="text-start mb-3">
							<div class="d-inline-block p-2 rounded bg-secondary text-white"
								style="max-width: 70%;">
								<strong>${m.sender.username}:</strong><br> ${m.content}<br>
								<small style="opacity: 0.8;"><fmt:formatDate
										value="${m.createdAt}" pattern="dd/MM/yyyy HH:mm" /></small>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>
		<div class="d-flex align-items-center gap-2">
			<a href="${pageContext.request.contextPath}/admin/complaints"
				class="btn btn-secondary"> <i class="bi bi-arrow-left"></i> Quay
				lại
			</a>
			<div class="input-group">
				<input id="messageInput" type="text" class="form-control"
					placeholder="Nhập tin nhắn..." autocomplete="off">
				<button id="sendButton" class="btn btn-primary-custom" type="button">
					<i class="bi bi-send-fill"></i> Gửi
				</button>
			</div>
		</div>

	</div>

	<script>
	// === Lấy các phần tử trên trang ===
	const chatBox = document.getElementById('chatBox');
	const messageInput = document.getElementById('messageInput');
	const sendButton = document.getElementById('sendButton');

	// === TỰ ĐỘNG CẤU HÌNH CHO MỌI MÔI TRƯỜNG (DEV VÀ PRODUCTION) ===
	const complaintIdStr = '${complaint.complaintId}';
	const currentUserIdStr = '${requestScope.account.userId}';
	
	// 1. Tự động chọn giao thức ws:// (cho http) hoặc wss:// (cho https)
	const wsProtocol = "${pageContext.request.scheme == 'https' ? 'wss' : 'ws'}"; 
	
	// 2. Tự động lấy tên miền và cổng
	const serverHost = "${pageContext.request.serverName}:${pageContext.request.serverPort}"; 
	
	// 3. Tự động lấy context path
	const contextPath = "${pageContext.request.contextPath}";

	// === Kiểm tra và khởi tạo WebSocket ===
	if (!complaintIdStr || !currentUserIdStr) {
	    console.error("LỖI: complaintId hoặc currentUserId không được truyền từ server!");
	    alert("Không thể tải dữ liệu chat. Vui lòng thử lại.");
	} else {
	    const complaintId = parseInt(complaintIdStr, 10);
	    const currentUserId = parseInt(currentUserIdStr, 10);
	    
	    console.log(`DEBUG INFO: complaintId=${complaintId}, currentUserId=${currentUserId}`);
	
	    function scrollToBottom() {
	        chatBox.scrollTop = chatBox.scrollHeight;
	    }
	    scrollToBottom();
	
	    // --- LOGIC WEBSOCKET ---
	    // Đảm bảo cú pháp escape là \${...} chính xác
	    const wsUrl = `\${wsProtocol}://\${serverHost}\${contextPath}/chat/\${complaintId}/\${currentUserId}`;
	    
	    console.log("Connecting to WebSocket URL:", wsUrl);
	
	    const socket = new WebSocket(wsUrl);
	
	    socket.onopen = function(event) {
	        console.log("WebSocket connection established.");
	    };

	    socket.onmessage = function(event) {
	        const messageData = JSON.parse(event.data);
	        displayMessage(messageData);
	    };

	    socket.onclose = function(event) {
	        console.log("WebSocket connection closed.");
	    };

	    socket.onerror = function(error) {
	        console.error("WebSocket error:", error);
	    };

        function sendMessage() {
            const messageContent = messageInput.value.trim();
            if (messageContent) {
                socket.send(messageContent);
                messageInput.value = '';
            }
        }

        sendButton.addEventListener('click', sendMessage);
        messageInput.addEventListener('keypress', function(event) {
            if (event.key === 'Enter') {
                sendMessage();
            }
        });

        function displayMessage(msg) {
            let messageHtml;
			// Đảm bảo tất cả các biến JS trong đây đều được escape đúng cách
            if (msg.senderId === currentUserId) {
                messageHtml = `
                    <div class="text-end mb-3">
                        <div class="d-inline-block p-2 rounded bg-primary-custom text-white" style="max-width: 70%;">
                            <strong>Bạn:</strong><br> \${msg.content}<br>
                            <small style="opacity: 0.8;">\${msg.createdAt}</small>
                        </div>
                    </div>
                `;
            } else {
                messageHtml = `
                    <div class="text-start mb-3">
                        <div class="d-inline-block p-2 rounded bg-secondary text-white" style="max-width: 70%;">
                            <strong>\${msg.senderUsername}:</strong><br> \${msg.content}<br>
                            <small style="opacity: 0.8;">\${msg.createdAt}</small>
                        </div>
                    </div>
                `;
            }
            chatBox.insertAdjacentHTML('beforeend', messageHtml);
            scrollToBottom();
        }
	}
</script>

</body>
</html>
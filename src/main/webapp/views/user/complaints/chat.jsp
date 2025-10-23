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
		<p><strong>Tiêu đề:</strong> ${complaint.title}</p>
		<hr>
		
		<div id="chatBox" class="border bg-white rounded p-3 mb-3"
			style="height: 450px; overflow-y: auto;">
			<%-- Lịch sử tin nhắn được tải từ Server --%>
			<c:forEach var="m" items="${messages}">
				<%-- ✅ FIX: So sánh đúng userId --%>
				<c:set var="isSentByCurrentUser"
					value="${m.sender.userId == requestScope.account.userId}" />
				<%-- ✅ FIX: Avatar của NGƯỜI GỬI tin nhắn đó, không phải current user --%>
				<c:set var="avatarPath"
					value="${not empty m.sender.avatar ? m.sender.avatar : '/avatars/default.jpg'}" />

				<div class="message-container mb-3 ${isSentByCurrentUser ? 'sent' : 'received'}"
					data-sender-id="${m.sender.userId}">
					<img src="${pageContext.request.contextPath}/assets/images${avatarPath}"
						alt="Avatar của ${m.sender.username}" class="chat-avatar"
						onerror="this.src='${pageContext.request.contextPath}/assets/images/avatars/default.jpg';">
					<div class="message-bubble d-inline-block p-2 rounded ${isSentByCurrentUser ? 'bg-primary-custom' : 'bg-secondary'} text-white">
						<strong>${isSentByCurrentUser ? 'Bạn' : m.sender.username}:</strong><br>
						<%-- Logic hiển thị: TEXT hoặc FILE --%>
						<c:choose>
							<c:when test="${m.messageType == 'FILE'}">
								<c:set var="isImageFile"
									value="${fn:endsWith(fn:toLowerCase(m.originalFilename), '.png') || fn:endsWith(fn:toLowerCase(m.originalFilename), '.jpg') || fn:endsWith(fn:toLowerCase(m.originalFilename), '.jpeg') || fn:endsWith(fn:toLowerCase(m.originalFilename), '.gif')}" />
								<c:choose>
									<c:when test="${isImageFile}">
										<a href="${pageContext.request.contextPath}${m.content}" target="_blank">
											<img src="${pageContext.request.contextPath}${m.content}"
												alt="${m.originalFilename}" class="img-fluid rounded mt-2"
												style="max-height: 200px; cursor: pointer;">
										</a>
									</c:when>
									<c:otherwise>
										<div class="file-attachment">
											<i class="bi bi-file-earmark-arrow-down"></i>
											<a href="${pageContext.request.contextPath}${m.content}"
												download="${m.originalFilename}"
												title="Tải xuống ${m.originalFilename}">${m.originalFilename}</a>
										</div>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								${m.content}
							</c:otherwise>
						</c:choose>
						<br>
						<small style="opacity: 0.8;">
							<fmt:formatDate value="${m.createdAt}" pattern="dd/MM/yyyy HH:mm" />
						</small>
					</div>
				</div>
			</c:forEach>
		</div>

		<div class="d-flex align-items-center gap-2">
			<a href="${pageContext.request.contextPath}/user/complaints"
				class="btn btn-secondary">
				<i class="bi bi-arrow-left"></i> Quay lại
			</a>
			<div class="input-group flex-grow-1">
				<input type="file" id="fileInput" class="d-none">
				<button id="attachFileButton" class="btn btn-outline-secondary"
					type="button" title="Đính kèm file">
					<i class="bi bi-paperclip"></i>
				</button>
				<input id="messageInput" type="text" class="form-control"
					placeholder="Nhập tin nhắn..." autocomplete="off">
				<button id="sendButton" class="btn btn-primary-custom" type="button">
					<i class="bi bi-send-fill"></i> Gửi
				</button>
			</div>
		</div>
	</div>

	<script>
		const chatBox = document.getElementById('chatBox');
		const messageInput = document.getElementById('messageInput');
		const sendButton = document.getElementById('sendButton');
		const fileInput = document.getElementById('fileInput');
		const attachFileButton = document.getElementById('attachFileButton');

		const contextPath = "${pageContext.request.contextPath}";
		const complaintIdStr = '${complaint.complaintId}';
		const currentUserIdStr = '${requestScope.account.userId}';
		const wsProtocol = "${pageContext.request.scheme == 'https' ? 'wss' : 'ws'}";
		const serverHost = "${pageContext.request.serverName}:${pageContext.request.serverPort}";

		if (!complaintIdStr || !currentUserIdStr) {
			alert("LỖI: complaintId hoặc currentUserId không được truyền từ server!");
		} else {
			const complaintId = parseInt(complaintIdStr, 10);
			const currentUserId = parseInt(currentUserIdStr, 10);

			console.log("Current User ID:", currentUserId);

			function scrollToBottom() {
				chatBox.scrollTop = chatBox.scrollHeight;
			}
			scrollToBottom();

			const wsUrl = wsProtocol + "://" + serverHost + contextPath + "/chat/" + complaintId + "/" + currentUserId;
			console.log("Connecting to WebSocket URL:", wsUrl);

			const socket = new WebSocket(wsUrl);

			socket.onopen = function(event) {
				console.log("✅ WebSocket connected");
			};

			socket.onmessage = function(event) {
				const messageData = JSON.parse(event.data);
				console.log("📨 Received:", messageData);
				displayMessage(messageData);
			};

			socket.onclose = function(event) {
				console.log("❌ WebSocket closed");
			};

			socket.onerror = function(error) {
				console.error("⚠️ WebSocket error:", error);
			};

			function sendMessage(type, content, originalFilename) {
				originalFilename = originalFilename || null;
				if (!content || !content.trim()) return;
				socket.send(JSON.stringify({
					type: type,
					content: content,
					originalFilename: originalFilename
				}));
				if (type.toUpperCase() === 'TEXT') messageInput.value = '';
			}

			async function uploadFileAndSend(file) {
				const formData = new FormData();
				formData.append('file', file);
				try {
					const response = await fetch(contextPath + '/api/upload-chat-file', {
						method: 'POST',
						body: formData
					});
					const result = await response.json();
					if (result.success) {
						sendMessage('file', result.url, result.filename);
					} else {
						alert('Lỗi upload file: ' + result.message);
					}
				} catch (error) {
					console.error('Error uploading file:', error);
					alert('Đã xảy ra lỗi nghiêm trọng khi upload file.');
				}
			}

			sendButton.addEventListener('click', function() {
				sendMessage('text', messageInput.value);
			});

			messageInput.addEventListener('keypress', function(e) {
				if (e.key === 'Enter') sendMessage('text', messageInput.value);
			});

			attachFileButton.addEventListener('click', function() {
				fileInput.click();
			});

			fileInput.addEventListener('change', function(event) {
				const file = event.target.files[0];
				if (file) uploadFileAndSend(file);
				event.target.value = null;
			});

			function displayMessage(msg) {
				const isSentByCurrentUser = msg.senderId === currentUserId;
				
				// ⚠️ CRITICAL: Avatar từ msg.senderAvatar - KHÔNG bị ghi đè
				const avatarPath = msg.senderAvatar && msg.senderAvatar.trim() !== '' 
					? msg.senderAvatar 
					: '/avatars/default.jpg';
				
				console.log("💬 Display:", {
					sender: msg.senderUsername,
					senderId: msg.senderId,
					avatar: avatarPath,
					isCurrentUser: isSentByCurrentUser
				});

				const isImage = msg.originalFilename && /\.(png|jpg|jpeg|gif)$/i.test(msg.originalFilename);

				let contentHtml = '';
				if (msg.type.toUpperCase() === 'FILE') {
					if (isImage) {
						contentHtml = '<a href="' + contextPath + msg.content + '" target="_blank">' +
							'<img src="' + contextPath + msg.content + '" alt="' + msg.originalFilename + '" ' +
							'class="img-fluid rounded mt-2" style="max-height: 200px; cursor: pointer;">' +
							'</a>';
					} else {
						contentHtml = '<div class="file-attachment">' +
							'<i class="bi bi-file-earmark-arrow-down"></i> ' +
							'<a href="' + contextPath + msg.content + '" download="' + msg.originalFilename + '" ' +
							'title="Tải xuống ' + msg.originalFilename + '">' + msg.originalFilename + '</a>' +
							'</div>';
					}
				} else {
					const escapedContent = msg.content.replace(/</g, "&lt;").replace(/>/g, "&gt;");
					contentHtml = escapedContent;
				}

				const senderName = isSentByCurrentUser ? 'Bạn' : msg.senderUsername;
				const containerClass = isSentByCurrentUser ? 'sent' : 'received';
				const bubbleClass = isSentByCurrentUser ? 'bg-primary-custom' : 'bg-secondary';

				const messageHtml = 
					'<div class="message-container ' + containerClass + ' mb-3" data-sender-id="' + msg.senderId + '">' +
						'<img src="' + contextPath + '/assets/images' + avatarPath + '" ' +
							'alt="Avatar của ' + msg.senderUsername + '" class="chat-avatar" ' +
							'onerror="this.src=\'' + contextPath + '/assets/images/avatars/default.jpg\';">' +
						'<div class="message-bubble d-inline-block p-2 rounded ' + bubbleClass + ' text-white">' +
							'<strong>' + senderName + ':</strong><br>' +
							contentHtml +
							'<br>' +
							'<small style="opacity: 0.8;">' + msg.createdAt + '</small>' +
						'</div>' +
					'</div>';

				chatBox.insertAdjacentHTML('beforeend', messageHtml);
				scrollToBottom();
			}
		}
	</script>
</body>
</html>
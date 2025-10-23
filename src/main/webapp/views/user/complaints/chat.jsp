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
			<i class="bi bi-chat-dots"></i> Chat với Admin

		</h4>
		<p>
			<strong>Tiêu đề:</strong> ${complaint.title}
		</p>
		<hr>

		<div id="chatBox" class="border bg-white rounded p-3 mb-3"
			style="height: 450px; overflow-y: auto;">
			<%-- Lịch sử tin nhắn được tải từ Server --%>
			<c:forEach var="m" items="${messages}">
				<c:set var="isSentByCurrentUser"
					value="${m.sender.userId == requestScope.account.userId}" />
				<c:set var="avatarPath"
					value="${not empty m.sender.avatar ? m.sender.avatar : '/avatars/default.jpg'}" />

				<div
					class="message-container mb-3 ${isSentByCurrentUser ? 'sent' : 'received'}"
					data-sender-id="${m.sender.userId}">
					<img
						src="${pageContext.request.contextPath}/assets/images${avatarPath}"
						alt="Avatar của ${m.sender.username}" class="chat-avatar"
						onerror="this.src='${pageContext.request.contextPath}/assets/images/avatars/default.jpg';">
					<div
						class="message-bubble d-inline-block p-2 rounded ${isSentByCurrentUser ? 'bg-primary-custom' : 'bg-secondary'} text-white">
						<strong>${isSentByCurrentUser ? 'Bạn' : m.sender.username}:</strong><br>

						<%-- Logic hiển thị: TEXT hoặc FILE --%>
						<c:choose>
							<c:when test="${m.messageType == 'FILE'}">
								<%-- Lấy extension file --%>
								<c:set var="fileExt"
									value="${fn:toLowerCase(fn:substringAfter(m.originalFilename, '.'))}" />

								<%-- HÌNH ẢNH --%>
								<c:if
									test="${fileExt == 'png' || fileExt == 'jpg' || fileExt == 'jpeg' || fileExt == 'gif' || fileExt == 'webp'}">
									<div class="file-preview mt-2">
										<%-- Ảnh xem trước --%>
										<a href="${pageContext.request.contextPath}${m.content}"
											target="_blank"> <img
											src="${pageContext.request.contextPath}${m.content}"
											alt="${m.originalFilename}" class="img-fluid rounded"
											style="max-height: 200px; cursor: pointer;">
										</a>
										<%-- Link tải xuống bên dưới ảnh --%>
										<div class="file-info mt-1">
											<a href="${pageContext.request.contextPath}${m.content}"
												download="${m.originalFilename}"
												class="text-white text-decoration-none">
												${m.originalFilename} <i class="bi bi-download"></i>
											</a>
										</div>
									</div>
								</c:if>


								<%-- CÁC FILE KHÁC (PDF, Word, Excel...) --%>
								<c:if
									test="${fileExt != 'png' && fileExt != 'jpg' && fileExt != 'jpeg' && fileExt != 'gif' && fileExt != 'webp'}">
									<div class="file-attachment">
										<%-- Icon theo loại file --%>
										<c:choose>
											<c:when test="${fileExt == 'pdf'}">
												<i class="bi bi-file-earmark-pdf text-danger"></i>
											</c:when>
											<c:when test="${fileExt == 'doc' || fileExt == 'docx'}">
												<i class="bi bi-file-earmark-word text-primary"></i>
											</c:when>
											<c:when test="${fileExt == 'xls' || fileExt == 'xlsx'}">
												<i class="bi bi-file-earmark-excel text-success"></i>
											</c:when>
											<c:when test="${fileExt == 'ppt' || fileExt == 'pptx'}">
												<i class="bi bi-file-earmark-ppt text-warning"></i>
											</c:when>
											<c:when
												test="${fileExt == 'zip' || fileExt == 'rar' || fileExt == '7z'}">
												<i class="bi bi-file-earmark-zip"></i>
											</c:when>
											<c:otherwise>
												<i class="bi bi-file-earmark-arrow-down"></i>
											</c:otherwise>
										</c:choose>

										<%-- ✅ MỚI: Thêm icon vào link tải xuống --%>
										<a href="${pageContext.request.contextPath}${m.content}"
											download="${m.originalFilename}"
											title="Tải xuống ${m.originalFilename}"
											class="text-white text-decoration-none">
											${m.originalFilename} <i class="bi bi-download"></i>
										</a>
									</div>
								</c:if>
							</c:when>
							<%-- Tin nhắn dạng TEXT --%>
							<c:otherwise>
								${m.content}
							</c:otherwise>
						</c:choose>

						<br> <small style="opacity: 0.8;"> <fmt:formatDate
								value="${m.createdAt}" pattern="dd/MM/yyyy HH:mm" />
						</small>
					</div>
				</div>
			</c:forEach>
		</div>

		<%-- Phần input không thay đổi --%>
		<div class="d-flex align-items-center gap-2">
			<a href="${pageContext.request.contextPath}/user/complaints"
				class="btn btn-secondary"> <i class="bi bi-arrow-left"></i> Quay
				lại
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
		// ... (Phần code khởi tạo và WebSocket không thay đổi) ...
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

			function scrollToBottom() {
				chatBox.scrollTop = chatBox.scrollHeight;
			}
			scrollToBottom();
			
			const wsUrl = wsProtocol + "://" + serverHost + contextPath + "/chat/" + complaintId + "/" + currentUserId;
			const socket = new WebSocket(wsUrl);

			socket.onopen = function(event) { console.log("✅ WebSocket connected"); };
			socket.onclose = function(event) { console.log("❌ WebSocket closed"); };
			socket.onerror = function(error) { console.error("⚠️ WebSocket error:", error); };
			
			socket.onmessage = function(event) {
				const messageData = JSON.parse(event.data);
				displayMessage(messageData);
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

			sendButton.addEventListener('click', () => sendMessage('text', messageInput.value));
			messageInput.addEventListener('keypress', (e) => {
				if (e.key === 'Enter') sendMessage('text', messageInput.value);
			});
			attachFileButton.addEventListener('click', () => fileInput.click());
			fileInput.addEventListener('change', (event) => {
				const file = event.target.files[0];
				if (file) uploadFileAndSend(file);
				event.target.value = null;
			});

			function getFileType(filename) {
				const ext = filename.toLowerCase().split('.').pop();
				if (['png', 'jpg', 'jpeg', 'gif', 'webp'].includes(ext)) return 'image';
				return 'document';
			}

			function getFileIcon(filename) {
				const ext = filename.toLowerCase().split('.').pop();
				if (ext === 'pdf') return '<i class="bi bi-file-earmark-pdf text-danger"></i>';
				if (['doc', 'docx'].includes(ext)) return '<i class="bi bi-file-earmark-word text-primary"></i>';
				if (['xls', 'xlsx'].includes(ext)) return '<i class="bi bi-file-earmark-excel text-success"></i>';
				if (['ppt', 'pptx'].includes(ext)) return '<i class="bi bi-file-earmark-ppt text-warning"></i>';
				if (['zip', 'rar', '7z'].includes(ext)) return '<i class="bi bi-file-earmark-zip"></i>';
				return '<i class="bi bi-file-earmark-arrow-down"></i>';
			}

			/**
			 * Thêm icon tải xuống cho các loại file
			 */
			function displayMessage(msg) {
				const isSentByCurrentUser = msg.senderId === currentUserId;
				const avatarPath = msg.senderAvatar && msg.senderAvatar.trim() !== '' 
					? msg.senderAvatar 
					: '/avatars/default.jpg';

				let contentHtml = '';
				if (msg.type.toUpperCase() === 'FILE') {
					const fileType = getFileType(msg.originalFilename);
					
					if (fileType === 'image') {
						contentHtml = '<div class="file-preview mt-2">' +
							// Ảnh xem trước
							'<a href="' + contextPath + msg.content + '" target="_blank">' +
								'<img src="' + contextPath + msg.content + '" alt="' + msg.originalFilename + '" ' +
								'class="img-fluid rounded" style="max-height: 200px; cursor: pointer;">' +
							'</a>' +
							// Link tải xuống
							'<div class="file-info mt-1">' +
							   '<a href="' + contextPath + msg.content + '" download="' + msg.originalFilename + '" class="text-white text-decoration-none">' +
								 msg.originalFilename + ' <i class="bi bi-download"></i>' +
							   '</a>' +
							'</div>' +
						  '</div>';
					} else { // Document
						contentHtml = '<div class="file-attachment">' +
							getFileIcon(msg.originalFilename) + ' ' +
							'<a href="' + contextPath + msg.content + '" download="' + msg.originalFilename + '" ' +
							'title="Tải xuống ' + msg.originalFilename + '" class="text-white text-decoration-none">' +
								msg.originalFilename + ' <i class="bi bi-download"></i>' +
							'</a>' +
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
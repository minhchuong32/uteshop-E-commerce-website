<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container py-5">
	<h4 class="text-center text-primary-custom mb-4">
		<i class="bi bi-chat-dots"></i> Trao đổi khiếu nại
		#${complaint.complaintId}
	</h4>
	<p>
		<strong>Tiêu đề:</strong> ${complaint.title}
	</p>
	<hr>
	<!-- Error Container - Đặt TRƯỚC chatBox để dễ nhìn thấy -->
	<div id="errorMessageContainer" class="mb-3"></div>

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
							<c:set var="fileExt"
								value="${fn:toLowerCase(fn:substringAfter(m.originalFilename, '.'))}" />
							<c:choose>
								<%-- HÌNH ẢNH --%>
								<c:when
									test="${fileExt == 'png' || fileExt == 'jpg' || fileExt == 'jpeg' || fileExt == 'gif' || fileExt == 'webp'}">
									<div class="file-preview mt-2">
										<a href="${pageContext.request.contextPath}${m.content}"
											target="_blank"> <img
											src="${pageContext.request.contextPath}${m.content}"
											alt="${m.originalFilename}" class="img-fluid rounded"
											style="max-height: 200px; cursor: pointer;">
										</a>
										<div class="file-info mt-1">
											<a href="${pageContext.request.contextPath}${m.content}"
												download="${m.originalFilename}"
												class="text-white text-decoration-none">
												${m.originalFilename} <i class="bi bi-download"></i>
											</a>
										</div>
									</div>
								</c:when>
								<%-- CÁC FILE KHÁC --%>
								<c:otherwise>
									<div class="file-attachment p-2 rounded"
										style="background-color: rgba(255, 255, 255, 0.2);">
										<a href="${pageContext.request.contextPath}${m.content}"
											download="${m.originalFilename}"
											title="Tải xuống ${m.originalFilename}"
											class="text-white text-decoration-none d-flex align-items-center">
											<i class="bi bi-file-earmark-arrow-down fs-4 me-3"></i>
											<div class="flex-grow-1">
												<div class="fw-bold">${m.originalFilename}</div>
											</div> <i class="bi bi-download ms-2"></i>
										</a>
									</div>
								</c:otherwise>
							</c:choose>
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

	<%-- Phần input --%>
	<div class="d-flex align-items-center gap-2">
		<a href="${pageContext.request.contextPath}/admin/complaints"
			class="btn btn-secondary"> <i class="bi bi-arrow-left"></i>
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
		
		let errorTimeout;

		// Hàm hiển thị lỗi
		function showError(message) {
			const errorContainer = document.getElementById('errorMessageContainer');
			if (!errorContainer) {
				console.error('Error container not found!');
				alert(message);
				return;
			}
			
			clearTimeout(errorTimeout);
			
			// Escape HTML để tránh XSS
			const safeMessage = String(message || 'Có lỗi xảy ra')
				.replace(/&/g, '&amp;')
				.replace(/</g, '&lt;')
				.replace(/>/g, '&gt;')
				.replace(/"/g, '&quot;');
			
			const errorHtml = 
				'<div class="alert alert-danger alert-dismissible fade show" role="alert">' +
					'<i class="bi bi-exclamation-triangle-fill me-2"></i>' +
					'<strong>Lỗi:</strong> ' + safeMessage +
					'<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>' +
				'</div>';
			
			errorContainer.innerHTML = errorHtml;
			
			// Tự động ẩn sau 5 giây
			errorTimeout = setTimeout(function() {
				errorContainer.innerHTML = '';
			}, 5000);
		}

		// Hàm ẩn lỗi
		function hideError() {
			const errorContainer = document.getElementById('errorMessageContainer');
			if (errorContainer) {
				errorContainer.innerHTML = '';
			}
			clearTimeout(errorTimeout);
		}

		if (!complaintIdStr || !currentUserIdStr) {
			showError("Không tìm thấy thông tin cần thiết để bắt đầu cuộc trò chuyện.");
		} else {
			const complaintId = parseInt(complaintIdStr, 10);
			const currentUserId = parseInt(currentUserIdStr, 10);

			function scrollToBottom() {
				chatBox.scrollTop = chatBox.scrollHeight;
			}
			scrollToBottom();
			
			const wsUrl = wsProtocol + "://" + serverHost + contextPath + "/chat/" + complaintId + "/" + currentUserId;
			const socket = new WebSocket(wsUrl);

			socket.onopen = function(event) { 
				console.log("✅ WebSocket connected"); 
				hideError();
			};
			
			socket.onclose = function(event) { 
				console.log("❌ WebSocket closed"); 
				showError("Mất kết nối với máy chủ chat. Vui lòng tải lại trang."); 
			};
			
			socket.onerror = function(error) { 
				console.error("❌ WebSocket error:", error);
				showError("Không thể kết nối đến máy chủ chat."); 
			};
			
			socket.onmessage = function(event) {
				const messageData = JSON.parse(event.data);
				displayMessage(messageData);
			};

			function sendMessage(type, content, originalFilename) {
				originalFilename = originalFilename || null;
				if (!content || !content.trim()) return;
				
				if (socket.readyState !== WebSocket.OPEN) {
					showError("Mất kết nối. Không thể gửi tin nhắn.");
					return;
				}
				
				socket.send(JSON.stringify({
					type: type,
					content: content,
					originalFilename: originalFilename
				}));
				
				if (type.toUpperCase() === 'TEXT') {
					messageInput.value = '';
				}
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
						hideError();
						sendMessage('file', result.url, result.filename);
					} else {
						showError(result.message || 'Lỗi không xác định từ server.');
					}
				} catch (error) {
					console.error('Error uploading file:', error);
					showError('Lỗi mạng hoặc máy chủ không phản hồi khi tải file.');
				}
			}

			sendButton.addEventListener('click', function() {
				sendMessage('text', messageInput.value);
			});
			
			messageInput.addEventListener('keypress', function(e) {
				if (e.key === 'Enter') {
					sendMessage('text', messageInput.value);
				}
			});
			
			attachFileButton.addEventListener('click', function() {
				fileInput.click();
			});
			
			fileInput.addEventListener('change', function(event) {
				const file = event.target.files[0];
				if (!file) return;
				
				// Validate phía client
				const MAX_FILE_SIZE_MB = 10;
				const ALLOWED_EXTENSIONS = ['jpg', 'jpeg', 'png', 'gif', 'pdf', 'doc', 'docx', 'xls', 'xlsx', 'txt', 'ppt', 'pptx'];
				
				const fileExtension = file.name.split('.').pop().toLowerCase();
				
				if (!ALLOWED_EXTENSIONS.includes(fileExtension)) {
					showError('Định dạng file .' + fileExtension + ' không được hỗ trợ. Chỉ cho phép: ảnh, PDF, Word, Excel, PowerPoint, text.');
					event.target.value = null;
					return;
				}
				
				if (file.size > MAX_FILE_SIZE_MB * 1024 * 1024) {
					showError('Dung lượng file không được vượt quá ' + MAX_FILE_SIZE_MB + 'MB.');
					event.target.value = null;
					return;
				}
				
				uploadFileAndSend(file);
				event.target.value = null;
			});

			function getFileType(filename) {
				const ext = filename.toLowerCase().split('.').pop();
				if (['png', 'jpg', 'jpeg', 'gif', 'webp'].includes(ext)) return 'image';
				return 'document';
			}

			function displayMessage(msg) {
				const isSentByCurrentUser = msg.senderId === currentUserId;
				const avatarPath = msg.senderAvatar && msg.senderAvatar.trim() !== '' 
					? msg.senderAvatar 
					: '/avatars/default.jpg';

				let contentHtml = '';
				if (msg.type.toUpperCase() === 'FILE') {
					const fileType = getFileType(msg.originalFilename);
					
					if (fileType === 'image') {
						contentHtml = 
							'<div class="file-preview mt-2">' +
								'<a href="' + contextPath + msg.content + '" target="_blank">' +
									'<img src="' + contextPath + msg.content + '" alt="' + msg.originalFilename + '" ' +
									'class="img-fluid rounded" style="max-height: 200px; cursor: pointer;">' +
								'</a>' +
								'<div class="file-info mt-1">' +
								   '<a href="' + contextPath + msg.content + '" download="' + msg.originalFilename + '" class="text-white text-decoration-none">' +
									 msg.originalFilename + ' <i class="bi bi-download"></i>' +
								   '</a>' +
								'</div>' +
							'</div>';
					} else {
						contentHtml = 
							'<div class="file-attachment p-2 rounded" style="background-color: rgba(255, 255, 255, 0.2);">' +
								'<a href="' + contextPath + msg.content + '" download="' + msg.originalFilename + '" ' +
								'title="Tải xuống ' + msg.originalFilename + '" class="text-white text-decoration-none d-flex align-items-center">' +
									'<i class="bi bi-file-earmark-arrow-down fs-4 me-3"></i>' +
									'<div class="flex-grow-1">' +
										'<div class="fw-bold">' + msg.originalFilename + '</div>' +
									'</div>' +
									'<i class="bi bi-download ms-2"></i>' +
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
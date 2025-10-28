// Đợi cho toàn bộ cây DOM được tải xong mới chạy script
document.addEventListener('DOMContentLoaded', function() {
	let errorTimeout;

	// Lấy các phần tử DOM cần thiết
	const chatBox = document.getElementById('chatBox');
	const messageInput = document.getElementById('messageInput');
	const sendButton = document.getElementById('sendButton');
	const fileInput = document.getElementById('fileInput');
	const attachFileButton = document.getElementById('attachFileButton');

	// Lấy từ chatContainer thay vì body
	const chatContainer = document.getElementById('chatContainer');
	
	if (!chatContainer) {
		console.error('chatContainer not found!');
		alert('Lỗi: Không tìm thấy container chat');
		return;
	}

	// Lấy các biến từ JSP
	const contextPath = chatContainer.dataset.contextPath;
	const complaintIdStr = chatContainer.dataset.complaintId;
	const currentUserIdStr = chatContainer.dataset.currentUserId;
	
	console.log('Context Path:', contextPath);
	console.log('Complaint ID:', complaintIdStr);
	console.log('Current User ID:', currentUserIdStr);

	if (!contextPath) {
		showError("Thiếu contextPath. Vui lòng kiểm tra cấu hình server.");
		return;
	}
	if (!complaintIdStr) {
		showError("Thiếu complaintId. Khiếu nại không tồn tại hoặc chưa được load.");
		return;
	}
	if (!currentUserIdStr) {
		showError("Thiếu userId. Vui lòng đăng nhập lại.");
		return;
	}

	const wsProtocol = window.location.protocol === 'https:' ? 'wss' : 'ws';
	const serverHost = window.location.host;
	const complaintId = parseInt(complaintIdStr, 10);
	const currentUserId = parseInt(currentUserIdStr, 10);

	// Hàm hiển thị thông báo lỗi
	function showError(message) {
		const errorContainer = document.getElementById('errorMessageContainer');
		if (!errorContainer) {
			console.error('Error container not found!');
			alert(message);
			return;
		}
		clearTimeout(errorTimeout);

		const safeMessage = String(message || 'Có lỗi xảy ra').replace(/[&<>"']/g, char => ({
			'&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;'
		}[char]));

		errorContainer.innerHTML =
			`<div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i>
                <strong>Lỗi:</strong> ${safeMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>`;

		errorTimeout = setTimeout(() => errorContainer.innerHTML = '', 5000);
	}

	// Hàm ẩn lỗi
	function hideError() {
		const errorContainer = document.getElementById('errorMessageContainer');
		if (errorContainer) errorContainer.innerHTML = '';
		clearTimeout(errorTimeout);
	}

	// Cuộn xuống cuối hộp chat
	function scrollToBottom() {
		chatBox.scrollTop = chatBox.scrollHeight;
	}
	scrollToBottom();

	// Khởi tạo WebSocket
	const wsUrl = `${wsProtocol}://${serverHost}${contextPath}/chat/${complaintId}/${currentUserId}`;
	console.log('WebSocket URL:', wsUrl);
	const socket = new WebSocket(wsUrl);

	socket.onopen = () => {
		console.log("✅ WebSocket connected");
		hideError();
	};
	socket.onclose = () => {
		console.log("❌ WebSocket closed");
		showError("Mất kết nối với máy chủ chat. Vui lòng tải lại trang.");
	};
	socket.onerror = (error) => {
		console.error("❌ WebSocket error:", error);
		showError("Không thể kết nối đến máy chủ chat.");
	};
	socket.onmessage = (event) => {
		try {
			const messageData = JSON.parse(event.data);
			displayMessage(messageData);
		} catch (e) {
			console.error("Lỗi parse JSON từ server:", e);
		}
	};

	// Gửi tin nhắn qua WebSocket
	function sendMessage(type, content, originalFilename = null) {
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

	// Tải file lên server và gửi link qua WebSocket
	function uploadFileAndSend(file) {
		const formData = new FormData();
		formData.append("file", file);

		fetch(`${contextPath}/api/upload-chat-file`, {
			method: "POST",
			body: formData
		})
			.then(response => {
				if (!response.ok) {
					throw new Error(`Server responded with status: ${response.status}`);
				}
				return response.json();
			})
			.then(result => {
				if (result.success) {
					hideError();
					sendMessage("FILE", result.url, result.filename);
				} else {
					showError(result.message || "Lỗi không xác định từ server.");
				}
			})
			.catch(error => {
				console.error("Error uploading file:", error);
				showError("Lỗi mạng hoặc máy chủ không phản hồi khi tải file.");
			});
	}

	// Gán sự kiện cho các nút
	sendButton.addEventListener('click', () => sendMessage('TEXT', messageInput.value));
	messageInput.addEventListener('keypress', (e) => {
		if (e.key === 'Enter' && !e.shiftKey) {
			e.preventDefault();
			sendMessage('TEXT', messageInput.value);
		}
	});
	attachFileButton.addEventListener('click', () => fileInput.click());
	fileInput.addEventListener('change', (event) => {
		const file = event.target.files[0];
		if (!file) return;

		const MAX_SIZE_MB = 10;
		if (file.size > MAX_SIZE_MB * 1024 * 1024) {
			showError(`Dung lượng file không được vượt quá ${MAX_SIZE_MB}MB.`);
			return;
		}

		uploadFileAndSend(file);
		event.target.value = null;
	});

	// Hàm hiển thị tin nhắn
	function displayMessage(msg) {
		const isSentByCurrentUser = msg.senderId === currentUserId;
		const avatarUrl = msg.senderAvatar ? `${contextPath}/assets/images${msg.senderAvatar}` : `${contextPath}/assets/images/avatars/default.jpg`;
		const escapedContent = msg.content.replace(/</g, "&lt;").replace(/>/g, "&gt;");

		let contentHtml = '';
		if (msg.type.toUpperCase() === 'FILE') {
			const fileUrl = `${contextPath}${msg.content}`;
			const isImage = /\.(jpe?g|png|gif|webp)$/i.test(msg.originalFilename);

			if (isImage) {
				contentHtml = `
                    <div class="file-preview mt-2">
                        <a href="${fileUrl}" target="_blank">
                            <img src="${fileUrl}" alt="${msg.originalFilename}" class="img-fluid rounded" style="max-height: 200px; cursor: pointer;">
                        </a>
                        <div class="file-info mt-1">
                            <a href="${fileUrl}" download="${msg.originalFilename}" class="text-white text-decoration-none">
                                ${msg.originalFilename} <i class="bi bi-download"></i>
                            </a>
                        </div>
                    </div>`;
			} else {
				contentHtml = `
                    <div class="file-attachment p-2 rounded" style="background-color: rgba(255, 255, 255, 0.2);">
                        <a href="${fileUrl}" download="${msg.originalFilename}" title="Tải xuống ${msg.originalFilename}" class="text-white text-decoration-none d-flex align-items-center">
                            <i class="bi bi-file-earmark-arrow-down fs-4 me-3"></i>
                            <div class="flex-grow-1">
                                <div class="fw-bold">${msg.originalFilename}</div>
                            </div>
                            <i class="bi bi-download ms-2"></i>
                        </a>
                    </div>`;
			}
		} else {
			contentHtml = escapedContent;
		}

		const messageHtml = `
            <div class="message-container ${isSentByCurrentUser ? 'sent' : 'received'} mb-3" data-sender-id="${msg.senderId}">
                <img src="${avatarUrl}" alt="Avatar" class="chat-avatar" onerror="this.src='${contextPath}/assets/images/avatars/default.jpg';">
                <div class="message-bubble d-inline-block p-2 rounded ${isSentByCurrentUser ? 'bg-primary-custom' : 'bg-secondary'} text-white">
                    <strong>${isSentByCurrentUser ? 'Bạn' : msg.senderUsername}:</strong><br>
                    ${contentHtml}
                    <br>
                    <small style="opacity: 0.8;">${msg.createdAt}</small>
                </div>
            </div>`;

		chatBox.insertAdjacentHTML('beforeend', messageHtml);
		scrollToBottom();
	}
});
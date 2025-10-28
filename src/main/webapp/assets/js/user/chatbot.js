document.addEventListener("DOMContentLoaded", () => {
	// --- 1. LẤY CÁC PHẦN TỬ HTML (DOM ELEMENTS) ---
	// Lấy các phần tử HTML cần thiết để tương tác
	const chatbotContainer = document.getElementById("chatbot-container");
	const chatbotToggler = document.getElementById("chatbot-toggler"); // Nút bật/tắt chatbot
	const chatbotCloser = document.getElementById("chatbot-closer"); // Nút đóng chatbot
	const chatbotMessages = document.getElementById("chatbot-messages"); // Khung hiển thị tin nhắn
	const chatbotInput = document.getElementById("chatbot-input"); // Ô nhập tin nhắn
	const chatbotSendBtn = document.getElementById("chatbot-send"); // Nút gửi tin nhắn

	const scrollToTopBtn = document.getElementById("s"); // Nút cuộn lên đầu trang (ID từ home.jsp)

	console.log(scrollToTopBtn); // In ra để kiểm tra xem nút có được tìm thấy không

	// --- 2. XỬ LÝ NÚT CUỘN LÊN ĐẦU (SCROLL TO TOP) ---
	// Nếu nút tồn tại trên trang
	if (scrollToTopBtn) {
		// Logic để hiện/ẩn nút khi cuộn trang
		window.addEventListener("scroll", () => {
			if (window.scrollY > 100) { // Nếu cuộn xuống hơn 100px
				scrollToTopBtn.classList.add("show"); // Hiển thị nút
			} else {
				scrollToTopBtn.classList.remove("show"); // Ẩn nút
			}
		});

		// Logic để cuộn lên đầu khi nhấp vào nút
		scrollToTopBtn.addEventListener("click", () => {
			console.log("Scroll to top clicked"); // Kiểm tra sự kiện click
			// Cuộn lên đầu trang một cách mượt mà
			window.scrollTo({
				top: 0, // Vị trí cuộn về 0 (đầu trang)
				behavior: 'smooth' // Tạo hiệu ứng cuộn mượt
			});
		});
	}

	// --- 3. CƠ SỞ DỮ LIỆU CHO BOT ---

	// (Knowledge Base) Các câu trả lời được định nghĩa trước
	const responses = {
		"chào": "Chào bạn! Tôi là trợ lý ảo của UteShop. Tôi có thể giúp gì cho bạn?",
		"shop ở đâu": "Cửa hàng của chúng tôi ở địa chỉ: 1 Võ Văn Ngân, TP. Thủ Đức, TP.HCM.",
		"địa chỉ": "Cửa hàng của chúng tôi ở địa chỉ: 1 Võ Văn Ngân, TP. Thủ Đức, TP.HCM.",
		"vận chuyển": "Uteshop miễn phí vận chuyển cho đơn hàng từ 500.000đ. Thời gian giao hàng dự kiến từ 2-5 ngày.",
		"đổi trả": "Bạn có thể đổi trả sản phẩm trong vòng 7 ngày nếu có lỗi từ nhà sản xuất nhé.",
		"bảo hành": "Các sản phẩm điện tử được bảo hành chính hãng 12 tháng. Bạn vui lòng giữ lại hóa đơn để được hỗ trợ tốt nhất.",
		"sản phẩm": "Shop chúng tôi bán đa dạng các mặt hàng từ công nghệ như điện thoại, laptop, tai nghe,... Bạn có thể xem chi tiết ở mục 'Danh mục' nhé.",
		"tạm biệt": "Cảm ơn bạn đã quan tâm đến UteShop. Hẹn gặp lại bạn!",
		"cảm ơn": "Rất vui được hỗ trợ bạn!",
	};
	
	// Các câu hỏi gợi ý cho người dùng (ĐÃ CẬP NHẬT)
	const suggestions = [
		"Shop ở đâu?",
		"Chính sách vận chuyển", // Sẽ khớp với 'vận chuyển'
		"Chính sách đổi trả", // Sẽ khớp với 'đổi trả'
		"Sản phẩm bảo hành thế nào?", // Sẽ khớp với 'bảo hành'
		"Shop bán những sản phẩm gì?" // Sẽ khớp với 'sản phẩm'
	];
	
	// Câu trả lời mặc định khi bot không hiểu
	const defaultResponse = "Xin lỗi, tôi chưa hiểu câu hỏi của bạn. Bạn có thể hỏi về vận chuyển, đổi trả, hoặc địa chỉ cửa hàng không?";

	// --- 4. CÁC HÀM TIỆN ÍCH CỦA CHATBOT ---

	/**
	 * Tự động cuộn khung tin nhắn xuống dưới cùng
	 */
	const scrollToBottom = () => {
		chatbotMessages.scrollTop = chatbotMessages.scrollHeight;
	};

	/**
	 * Thêm một tin nhắn mới vào khung chat
	 * @param {string} sender - Người gửi ('user' hoặc 'bot')
	 * @param {string} message - Nội dung tin nhắn
	 */
	const addMessage = (sender, message) => {
		// Tạo một thẻ div mới cho tin nhắn
		const messageElement = document.createElement("div");
		// Thêm các lớp CSS (class) cần thiết
		messageElement.classList.add("chatbot-message", `${sender}-message`);
		// Đặt nội dung text cho tin nhắn
		messageElement.textContent = message;
		// Thêm tin nhắn vào khung chat
		chatbotMessages.appendChild(messageElement);
		// Tự động cuộn xuống
		scrollToBottom();
	};

	/**
	 * Tìm câu trả lời của bot dựa trên tin nhắn của người dùng
	 * @param {string} userInput - Tin nhắn người dùng nhập vào
	 * @returns {string} - Câu trả lời của bot
	 */
	const getBotResponse = (userInput) => {
		const lowerCaseInput = userInput.toLowerCase().trim();
		
		// Ưu tiên các từ khóa chính xác từ gợi ý
		if (lowerCaseInput.includes("vận chuyển")) {
			return responses["vận chuyển"];
		}
		if (lowerCaseInput.includes("đổi trả")) {
			return responses["đổi trả"];
		}
		if (lowerCaseInput.includes("bảo hành")) {
			return responses["bảo hành"];
		}
		if (lowerCaseInput.includes("sản phẩm gì")) {
			return responses["sản phẩm"];
		}

		// Duyệt qua các từ khóa trong 'responses'
		for (const keyword in responses) {
			// Nếu tin nhắn của người dùng chứa từ khóa
			if (lowerCaseInput.includes(keyword)) {
				return responses[keyword]; // Trả về câu trả lời tương ứng
			}
		}
		// Nếu không tìm thấy từ khóa nào, trả về câu mặc định
		return defaultResponse;
	};

	// --- 5. CÁC HÀM XỬ LÝ GỢI Ý ---

	/**
	 * Xóa các nút gợi ý đang hiển thị (nếu có)
	 */
	const removeSuggestions = () => {
		const suggestionsContainer = document.getElementById("chatbot-suggestions-container");
		if (suggestionsContainer) {
			suggestionsContainer.remove();
		}
	};

	/**
	 * Xử lý khi người dùng nhấp vào một nút gợi ý
	 * @param {Event} event - Sự kiện click
	 */
	const handleSuggestionClick = (event) => {
		const suggestionText = event.target.textContent;

		// 1. Xóa tất cả các nút gợi ý khác
		removeSuggestions();

		// 2. Hiển thị tin nhắn của người dùng (giống như họ tự gõ)
		addMessage("user", suggestionText);

		// 3. Giả lập bot suy nghĩ và trả lời
		setTimeout(() => {
			const botMessage = getBotResponse(suggestionText);
			addMessage("bot", botMessage);
		}, 500); // Trễ 0.5 giây
	};

	/**
	 * Tạo và hiển thị các nút gợi ý trong khung chat
	 */
	const addSuggestionButtons = () => {
		// Nếu đã có gợi ý rồi thì không thêm nữa
		if (document.getElementById("chatbot-suggestions-container")) return;

		// Tạo container cho các nút
		const suggestionsContainer = document.createElement("div");
		suggestionsContainer.id = "chatbot-suggestions-container";

		// Lặp qua mảng 'suggestions' để tạo từng nút
		suggestions.forEach(text => {
			const button = document.createElement("button");
			button.classList.add("chatbot-suggestion-btn");
			button.textContent = text;
			// Gán sự kiện click cho nút
			button.addEventListener("click", handleSuggestionClick);
			suggestionsContainer.appendChild(button);
		});

		// Thêm container (chứa các nút) vào khung chat
		chatbotMessages.appendChild(suggestionsContainer);
		scrollToBottom();
	};

	// --- 6. HÀM XỬ LÝ CHÍNH KHI GỬI TIN NHẮN ---

	/**
	 * Xử lý khi người dùng gửi tin nhắn (bằng nút Gửi hoặc Enter)
	 */
	const handleSendMessage = () => {
		const message = chatbotInput.value.trim(); // Lấy nội dung, xóa khoảng trắng thừa
		
		// Nếu không có tin nhắn thì không làm gì cả
		if (message === "") return;

		// 1. Xóa các nút gợi ý (nếu có)
		removeSuggestions();

		// 2. Hiển thị tin nhắn của người dùng
		addMessage("user", message);
		
		// 3. Xóa nội dung trong ô input
		chatbotInput.value = "";

		// 4. Giả lập bot suy nghĩ và trả lời
		setTimeout(() => {
			const botMessage = getBotResponse(message);
			addMessage("bot", botMessage);
			
			// --- THAY ĐỔI MỚI ---
			// Nếu bot không hiểu (trả về câu mặc định),
			// thì hiển thị lại các gợi ý để hướng dẫn người dùng
			if (botMessage === defaultResponse) {
				addSuggestionButtons();
			}
			// --- KẾT THÚC THAY ĐỔI ---
			
		}, 500); // Trễ 0.5 giây
	};

	// --- 7. GÁN CÁC SỰ KIỆN (EVENT LISTENERS) ---

	// Sự kiện click cho nút bật/tắt chatbot
	if (chatbotToggler) {
		chatbotToggler.addEventListener("click", () => {
			// Kiểm tra xem chatbot sắp ĐƯỢC MỞ hay không
			const isOpening = !chatbotContainer.classList.contains("show");
			
			// Thêm/xóa lớp 'show' để CSS xử lý việc hiện/ẩn
			chatbotContainer.classList.toggle("show");

			// Nếu chatbot đang được mở
			if (isOpening) {
				// Và nếu đây là lần mở đầu tiên (chưa có tin nhắn nào)
				if (chatbotMessages.children.length === 0) {
					// Hiển thị tin nhắn chào mừng và các nút gợi ý
					addMessage("bot", "Chào bạn! Tôi có thể giúp gì cho bạn về sản phẩm, vận chuyển hoặc đổi trả?");
					addSuggestionButtons();
				}
			}
		});
	}

	// Sự kiện click cho nút đóng chatbot
	chatbotCloser.addEventListener("click", () => chatbotContainer.classList.remove("show"));

	// Sự kiện click cho nút Gửi
	chatbotSendBtn.addEventListener("click", handleSendMessage);

	// Sự kiện nhấn phím Enter trong ô input
	chatbotInput.addEventListener("keydown", (event) => {
		if (event.key === "Enter") {
			handleSendMessage(); // Gọi hàm gửi
		}
	});
});


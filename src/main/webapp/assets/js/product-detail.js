console.log("product-detail.js buy");

// ==========================
// Đổi ảnh chính khi click thumbnail
// ==========================
function changeImage(el) {
	const mainImg = document.getElementById("mainImg");
	if (!mainImg) return;
	mainImg.src = el.src;
	document.querySelectorAll(".thumb-img").forEach(img => img.classList.remove("active"));
	el.classList.add("active");
}

// ==========================
// Tăng/giảm số lượng
// ==========================
function changeQty(delta) {
	const qty = document.getElementById("qty");
	if (!qty) return;
	let val = parseInt(qty.value) + delta;
	if (val < 1) val = 1;
	qty.value = val;
	syncQty();
}

// ==========================
// Đồng bộ số lượng vào form
// ==========================
function syncQty() {
	const qtyEl = document.getElementById("qty");
	if (!qtyEl) return;
	const qty = qtyEl.value;

	const formQty = document.getElementById("formQty");
	const formQtyNow = document.getElementById("formQtyNow");

	if (formQty) formQty.value = qty;
	if (formQtyNow) formQtyNow.value = qty;
}

// ==========================
// Khi DOM load xong
// ==========================
document.addEventListener("DOMContentLoaded", function() {
	const qtyInput = document.getElementById("qty");
	if (qtyInput) qtyInput.addEventListener("input", syncQty);

	// ==========================
	// Xử lý nút "Mua ngay"
	// ==========================
	const buyNowForm = document.getElementById("buyNowForm");
	if (buyNowForm) {
		buyNowForm.addEventListener("submit", async function(e) {
			e.preventDefault();

			if (!validateSelection()) return;

			const variantInput = document.getElementById("variantId");
			const qtyInput = document.getElementById("qty");
			const productDetail = document.getElementById("product-detail");
			const appContext = productDetail?.dataset.context || "";

			if (!variantInput?.value) {
				showTempAlert("Vui lòng chọn đầy đủ thuộc tính sản phẩm.", "warning");
				return;
			}

			const variantId = variantInput.value;
			const quantity = qtyInput ? parseInt(qtyInput.value) : 1;

			try {
				// ✅ Gửi dữ liệu kiểu form (application/x-www-form-urlencoded)
				const formData = new URLSearchParams();
				formData.append("variantId", variantId);
				formData.append("quantity", quantity);

				const res = await fetch(`${appContext}/user/cart/add-now`, {
					method: "POST",
					headers: { "Content-Type": "application/x-www-form-urlencoded" },
					body: formData
				});

				if (!res.ok) throw new Error("Không thể thêm sản phẩm vào giỏ hàng.");

				// ✅ Đọc JSON trả về từ servlet
				const data = await res.json();
				console.log("🛒 Kết quả thêm giỏ hàng:", data);

				if (!data.success) {
					showTempAlert(data.message || "Không thể thêm sản phẩm.", "danger");
					return;
				}

				// ✅ Điều hướng đến trang thanh toán
				const checkoutUrl = `${appContext}/user/checkout?selectedItems=${encodeURIComponent(data.cartItemId)}`;
				console.log("➡️ Chuyển hướng đến:", checkoutUrl);
				window.location.href = checkoutUrl;

			} catch (err) {
				console.error("Lỗi khi mua ngay:", err);
				showTempAlert("Không thể thêm sản phẩm vào giỏ để thanh toán.", "danger");
			}
		});
	}
});


// ==========================
// Xử lý chọn variant
// ==========================
function getSelectedOptions() {
	const options = {};
	document.querySelectorAll(".btn-check:checked").forEach(radio => {
		options[radio.name] = radio.value;
	});
	return options;
}

function validateSelection() {
	const isGuest = document.querySelector('form[action$="/login"]') !== null;
	if (isGuest) return true;

	const radios = document.querySelectorAll(".btn-check");
	const groups = [...new Set([...radios].map(r => r.name))];
	const selected = getSelectedOptions();

	for (let name of groups) {
		if (!selected[name]) {
			showTempAlert(`Vui lòng chọn ${name} trước khi thêm vào giỏ.`, "warning");
			return false;
		}
	}

	const variantInput = document.getElementById("variantId");
	if (!variantInput || !variantInput.value) {
		showTempAlert("Vui lòng chọn đủ thuộc tính sản phẩm.", "danger");
		return false;
	}

	return true;
}

// ==========================
// Alert tạm thời (toast nhẹ)
// ==========================
function showTempAlert(message, type = "success", duration = 3000) {
	const alertBox = document.getElementById("tempAlert");
	if (!alertBox) return;

	alertBox.className = `alert alert-${type} position-fixed top-0 start-50 translate-middle-x mt-3 shadow-lg`;
	alertBox.textContent = message;
	alertBox.classList.remove("d-none");

	clearTimeout(alertBox.hideTimeout);
	alertBox.hideTimeout = setTimeout(() => {
		alertBox.classList.add("d-none");
	}, duration);
}

// ==========================
// Fetch API khi chọn variant
// ==========================
document.addEventListener("DOMContentLoaded", function() {
	const productDetail = document.getElementById("product-detail");
	if (!productDetail) return;

	const appContext = productDetail.dataset.context || "";
	const currentProductId = productDetail.dataset.productId;

	document.querySelectorAll(".btn-check").forEach(radio => {
		radio.addEventListener("change", () => {
			const options = getSelectedOptions();
			options["productId"] = currentProductId;

			fetch(`${appContext}/api/variant/select`, {
				method: "POST",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify(options)
			})
				.then(res => {
					if (!res.ok) throw new Error("Không tìm thấy variant phù hợp");
					return res.json();
				})
				.then(data => {
					const currentPrice = document.querySelector("#current-price");
					const oldPrice = document.querySelector("#old-price");
					const stockValue = document.querySelector("#stock-value");
					const mainImg = document.querySelector("#mainImg");
					const variantInput = document.querySelector("#variantId");

					// Giá hiện tại
					if (currentPrice)
						currentPrice.innerHTML = data.price
							? new Intl.NumberFormat("vi-VN", { style: "currency", currency: "VND" }).format(data.price)
							: "-";

					// Giá cũ
					if (oldPrice)
						oldPrice.innerHTML = data.oldPrice && data.oldPrice > data.price
							? new Intl.NumberFormat("vi-VN", { style: "currency", currency: "VND" }).format(data.oldPrice)
							: "-";

					// Tồn kho
					if (stockValue)
						stockValue.textContent =
							data.stock !== undefined && data.stock !== null ? data.stock : "-";

					// Ảnh
					if (data.imageUrl && mainImg) {
						mainImg.src = `${appContext}${data.imageUrl}`;
					}

					// Gán variantId
					if (variantInput && data.variantId) {
						variantInput.value = data.variantId;
					}
				})
				.catch(err => console.error("Lỗi khi cập nhật variant:", err));
		});
	});
});

console.log("product-detail.js new2");

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
	syncQty(); // đồng bộ khi nhấn +/- luôn
}

// ==========================
// Đồng bộ số lượng vào 2 form
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

			console.log("Đã chọn variant:", options);

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
					console.log("Kết quả variant:", data);

					// Các phần tử cần cập nhật
					const currentPrice = document.querySelector("#current-price");
					const oldPrice = document.querySelector("#old-price");
					const stockValue = document.querySelector("#stock-value");
					const mainImg = document.querySelector("#mainImg");
					const variantInput = document.querySelector("#variantId");

					// Cập nhật giá hiện tại
					const formattedPrice = data.price !== undefined
						? new Intl.NumberFormat("vi-VN", { style: "currency", currency: "VND" }).format(data.price)
						: "-";
					if (currentPrice) currentPrice.innerHTML = formattedPrice;

					// Cập nhật giá cũ
					const formattedOldPrice = data.oldPrice && data.oldPrice > data.price
						? new Intl.NumberFormat("vi-VN", { style: "currency", currency: "VND" }).format(data.oldPrice)
						: "-";
					if (oldPrice) oldPrice.innerHTML = formattedOldPrice;

					if (stockValue) stockValue.textContent = (data.stock !== undefined && data.stock !== null) ? data.stock : "-";

					//Cập nhật ảnh
					if (data.imageUrl && mainImg) {
						mainImg.src = `${appContext}${data.imageUrl}`;
						console.log("Ảnh mới:", mainImg.src);
					}



					// Gán variantId vào form
					if (variantInput && data.variantId) {
						variantInput.value = data.variantId;
					}
				})
				.catch(err => {
					console.error("Lỗi khi cập nhật variant:", err);
				});
		});
	});
});

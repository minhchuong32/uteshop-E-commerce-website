console.log("product-detail.js new2");

// ==========================
// DOM Elements Cache
// ==========================
function getEl(id) {
	return document.getElementById(id);
}

function getEls(selector) {
	return document.querySelectorAll(selector);
}

// ==========================
// Utility Functions
// ==========================
function showAlert(message, type = "success", duration = 5000) {
	const alert = getEl("tempAlert");
		if (!alert) return;

		// Reset class nhưng vẫn giữ lại base layout
		alert.className = "alert position-fixed start-50 translate-middle-x shadow-lg rounded-3";
		alert.classList.add(`alert-${type}`);
		alert.textContent = message;

		alert.classList.remove("d-none");
		window.scrollTo({ top: 0, behavior: "smooth" });

		// Clear timeout cũ nếu có
		if (alert.hideTimeout) clearTimeout(alert.hideTimeout);
		alert.hideTimeout = setTimeout(() => {
			alert.classList.add("d-none");
		}, duration);
}

function formatCurrency(amount) {
	return new Intl.NumberFormat("vi-VN", { style: "currency", currency: "VND" }).format(amount);
}

// ==========================
// Image Gallery with Zoom
// ==========================
function changeImage(el) {
	var mainImg = getEl("mainImg");
	if (!mainImg) return;

	mainImg.src = el.src;

	var thumbs = document.querySelectorAll(".thumb-img");
	for (var i = 0; i < thumbs.length; i++) {
		thumbs[i].classList.remove("active");
	}

	el.classList.add("active");
}

function setupImageZoom() {
	var container = getEl("imageContainer");
	var mainImg = getEl("mainImg");
	var modal = getEl("imageZoomModal");
	var zoomedImg = getEl("zoomedImg");

	if (!container || !mainImg) return;

	// Click mở modal
	container.addEventListener("click", function() {
		if (zoomedImg) zoomedImg.src = mainImg.src;
		if (modal) {
			var bsModal = new bootstrap.Modal(modal);
			bsModal.show();
		}
	});

	// Hover zoom
	container.addEventListener("mouseenter", function() {
		container.style.cursor = "zoom-in";
	});

	container.addEventListener("mousemove", function(e) {
		var rect = container.getBoundingClientRect();
		var x = ((e.clientX - rect.left) / rect.width) * 100;
		var y = ((e.clientY - rect.top) / rect.height) * 100;
		mainImg.style.transformOrigin = x + "% " + y + "%";
		mainImg.style.transform = "scale(1.5)";
	});

	container.addEventListener("mouseleave", function() {
		mainImg.style.transform = "scale(1)";
		mainImg.style.transformOrigin = "center";
	});
}

// ==========================
// Quantity Control
// ==========================
function syncQty() {
	var qtyInput = getEl("qty");
	var qty = 1;
	if (qtyInput && qtyInput.value) {
		qty = qtyInput.value;
	}

	var targetIds = ["formQty", "formQtyNow"];
	for (var i = 0; i < targetIds.length; i++) {
		var input = getEl(targetIds[i]);
		if (input) input.value = qty;
	}
}

function changeQty(delta) {
	var qtyEl = getEl("qty");
	if (!qtyEl) return;

	var value = parseInt(qtyEl.value) || 1;
	qtyEl.value = Math.max(1, value + delta);
	syncQty();
}

window.changeQty = changeQty;

// ==========================
// Variant Selection
// ==========================
function getSelectedOptions() {
	var options = {};
	var radios = getEls(".btn-check:checked");
	for (var i = 0; i < radios.length; i++) {
		var radio = radios[i];
		options[radio.name] = radio.value;
	}
	return options;
}

function validateSelection() {
	var radios = getEls(".btn-check");
	var groupNames = [];
	for (var i = 0; i < radios.length; i++) {
		var name = radios[i].name;
		if (groupNames.indexOf(name) === -1) groupNames.push(name);
	}

	var selected = getSelectedOptions();

	for (var i = 0; i < groupNames.length; i++) {
		var name = groupNames[i];
		if (!selected[name]) {
			showAlert("Vui lòng chọn " + name + " trước khi thêm vào giỏ.", "warning");
			return false;
		}
	}

	var variantInput = getEl("variantId");
	if (!variantInput || !variantInput.value) {
		showAlert("Vui lòng chọn đủ thuộc tính sản phẩm.", "danger");
		return false;
	}

	return true;
}

window.validateSelection = validateSelection;

// ==========================
// Update Variant Info
// ==========================
function updateVariantInfo(data) {
	console.group("🔄 Cập nhật variant thông tin");
		console.log("Variant ID:", data.variantId );
		console.log("Giá hiện tại:", data.price);
		console.log("Giá cũ:", data.oldPrice );
		console.log("Tồn kho:", data.stock);
		console.log("Ảnh:", data.imageUrl);
		console.groupEnd();
	var currentPriceEl = getEl("current-price");
	var oldPriceEl = getEl("old-price");
	var stockEl = getEl("stock-value");

	if (currentPriceEl) {
		currentPriceEl.innerHTML = data.price ? formatCurrency(data.price) : "-";
	}

	if (oldPriceEl) {
		if (data.oldPrice && data.oldPrice > data.price) {
			oldPriceEl.innerHTML = formatCurrency(data.oldPrice);
		} else {
			oldPriceEl.innerHTML = "-";
		}
	}

	if (stockEl) {
		if (data.stock !== undefined && data.stock !== null) {
			stockEl.innerHTML = data.stock;
		} else {
			stockEl.innerHTML = "-";
		}
	}

	var mainImg = getEl("mainImg");
	var productDetail = getEl("product-detail");
	var appContext = "";
	if (productDetail && productDetail.dataset && productDetail.dataset.context) {
		appContext = productDetail.dataset.context;
	}

	if (mainImg && data.imageUrl) {
		mainImg.src = appContext + data.imageUrl;
	}

	var variantInput1 = getEl("variantId");
	var variantInput2 = getEl("variantIdNow");
	if (data.variantId) {
	    if (variantInput1) variantInput1.value = data.variantId;
	    if (variantInput2) variantInput2.value = data.variantId;
	}
}

// ==========================
// Fetch Variant on Selection
// ==========================
function setupVariantListener() {
	var productDetail = getEl("product-detail");
	if (!productDetail) return;

	var appContext = "";
	if (productDetail.dataset && productDetail.dataset.context) {
		appContext = productDetail.dataset.context;
	}
	var productId = productDetail.dataset.productId;

	var radios = getEls(".btn-check");
	for (var i = 0; i < radios.length; i++) {
		radios[i].addEventListener("change", function() {
			var selectedOptions = getSelectedOptions();
			console.log("🧩 Lựa chọn hiện tại:", selectedOptions);
			var options = Object.assign({}, selectedOptions, { productId: productId });
			console.log("📦 Gửi dữ liệu đến API:", options);
			fetch(appContext + "/api/variant/select", {
				method: "POST",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify(options)
			})
				.then(function(res) {
					if (!res.ok) throw new Error("Không tìm thấy variant");
					return res.json();
				})
				.then(function(data) {
					updateVariantInfo(data);
				})
				.catch(function(err) {
					console.error("Lỗi khi cập nhật variant:", err);
				});
		});
	}
}

// ==========================
// Buy Now Handler
// ==========================
function setupBuyNow() {
	var form = getEl("buyNowForm");
	if (!form) return;

	form.addEventListener("submit", function(e) {
		e.preventDefault();

		if (!validateSelection()) return;

		var variantInput = getEl("variantId");
		var qtyInput = getEl("qty");
		var productDetail = getEl("product-detail");
		var appContext = "";
		if (productDetail && productDetail.dataset && productDetail.dataset.context) {
			appContext = productDetail.dataset.context;
		}

		if (!variantInput || !variantInput.value) {
			showAlert("Vui lòng chọn đầy đủ thuộc tính sản phẩm.", "warning");
			return;
		}

		var params = "variantId=" + encodeURIComponent(variantInput.value) +
			"&quantity=" + encodeURIComponent(qtyInput ? qtyInput.value : 1);

		fetch(appContext + "/user/cart/add-now", {
			method: "POST",
			headers: { "Content-Type": "application/x-www-form-urlencoded" },
			body: params
		})
			.then(function(res) {
				if (!res.ok) throw new Error("Không thể thêm sản phẩm");
				return res.json();
			})
			.then(function(data) {
				console.log("🛒 Kết quả:", data);
				if (!data.success) {
					showAlert(data.message || "Không thể thêm sản phẩm.", "danger");
					return;
				}
				var checkoutUrl = appContext + "/user/checkout?selectedItems=" + encodeURIComponent(data.cartItemId);
				window.location.href = checkoutUrl;
			})
			.catch(function(err) {
				console.error("Lỗi:", err);
				showAlert("Không thể thêm sản phẩm vào giỏ để thanh toán.", "danger");
			});
	});
}

// ==========================
// Add to Cart Handler
// ==========================
function addToCart() {
	if (!validateSelection()) return;

		const variantInput = getEl("variantId");
		const qtyInput = getEl("qty");
		const productDetail = getEl("product-detail");
		let appContext = productDetail?.dataset?.context || "";

		const selected = getSelectedOptions();
		const variantText = Object.entries(selected)
			.map(([k, v]) => `${k}: ${v}`)
			.join(" – ");

		if (!variantInput || !variantInput.value) {
			showAlert("Vui lòng chọn đủ thuộc tính sản phẩm.", "warning");
			return;
		}

		const params = new URLSearchParams({
			variantId: variantInput.value,
			quantity: qtyInput ? qtyInput.value : 1
		});

		fetch(appContext + "/user/cart/add", {
			method: "POST",
			headers: {
				"Content-Type": "application/x-www-form-urlencoded",
				// ⚠️ nếu bạn dùng JWT thì giữ dòng dưới:
				"Authorization": "Bearer " + localStorage.getItem("token")
			},
			body: params.toString()
		})
			.then(res => {
				// Do servlet /user/cart/add đang redirect chứ không trả JSON
				// ta chỉ cần hiển thị alert mà không cần parse kết quả
				if (res.ok) {
					showAlert(
						`Đã thêm vào giỏ: ${variantText} (x${qtyInput.value})`,
						"success",
						8000 // 👉 hiển thị 8 giây
					);
				} else {
					showAlert("❌ Không thể thêm sản phẩm vào giỏ hàng.", "danger", 6000);
				}
			})
			.catch(err => {
				console.error("❌ Lỗi thêm giỏ:", err);
				showAlert("Không thể thêm sản phẩm vào giỏ.", "danger", 6000);
			});
}


// ==========================
// Initialize
// ==========================
document.addEventListener("DOMContentLoaded", function() {
	var qtyInput = getEl("qty");
	if (qtyInput) {
		qtyInput.addEventListener("input", syncQty);
	}

	setupImageZoom();
	setupBuyNow();
	setupVariantListener();
});

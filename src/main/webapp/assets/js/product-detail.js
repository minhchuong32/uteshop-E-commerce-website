console.log("product-detail.js optimized");

// ==========================
// DOM Elements Cache
// ==========================
const getEl = id => document.getElementById(id);
const getEls = selector => document.querySelectorAll(selector);

// ==========================
// Utility Functions
// ==========================
const showAlert = (message, type = "success", duration = 3000) => {
	const alert = getEl("tempAlert");
	if (!alert) return;
	
	alert.className = `alert alert-${type} position-fixed top-0 start-50 translate-middle-x mt-3 shadow-lg`;
	alert.textContent = message;
	alert.classList.remove("d-none");
	
	clearTimeout(alert.hideTimeout);
	alert.hideTimeout = setTimeout(() => alert.classList.add("d-none"), duration);
};

const formatCurrency = amount => 
	new Intl.NumberFormat("vi-VN", { style: "currency", currency: "VND" }).format(amount);

// ==========================
// Image Gallery with Zoom
// ==========================
function changeImage(el) {
    const mainImg = document.getElementById("mainImg");
    if (!mainImg) return;
    mainImg.src = el.src;
    document.querySelectorAll(".thumb-img").forEach(img => img.classList.remove("active"));
    el.classList.add("active");
}

// Zoom functionality
const setupImageZoom = () => {
	const container = getEl("imageContainer");
	const mainImg = getEl("mainImg");
	const modal = document.getElementById("imageZoomModal");
	const zoomedImg = getEl("zoomedImg");
	
	if (!container || !mainImg) return;
	
	// Click to open modal
	container.addEventListener("click", () => {
		if (zoomedImg) zoomedImg.src = mainImg.src;
		if (modal) new bootstrap.Modal(modal).show();
	});
	
	// Hover zoom effect
	container.addEventListener("mouseenter", () => {
		container.style.cursor = "zoom-in";
	});
	
	container.addEventListener("mousemove", e => {
		const rect = container.getBoundingClientRect();
		const x = ((e.clientX - rect.left) / rect.width) * 100;
		const y = ((e.clientY - rect.top) / rect.height) * 100;
		mainImg.style.transformOrigin = `${x}% ${y}%`;
		mainImg.style.transform = "scale(1.5)";
	});
	
	container.addEventListener("mouseleave", () => {
		mainImg.style.transform = "scale(1)";
		mainImg.style.transformOrigin = "center";
	});
};

// ==========================
// Quantity Control
// ==========================
const syncQty = () => {
	const qty = getEl("qty")?.value || 1;
	["formQty", "formQtyNow"].forEach(id => {
		const el = getEl(id);
		if (el) el.value = qty;
	});
};

window.changeQty = delta => {
	const qtyEl = getEl("qty");
	if (!qtyEl) return;
	
	qtyEl.value = Math.max(1, parseInt(qtyEl.value) + delta);
	syncQty();
};

// ==========================
// Variant Selection
// ==========================
const getSelectedOptions = () => {
	const options = {};
	getEls(".btn-check:checked").forEach(radio => {
		options[radio.name] = radio.value;
	});
	return options;
};

window.validateSelection = () => {
	const isGuest = document.querySelector('form[action$="/login"]');
	if (isGuest) return true;
	
	const radios = getEls(".btn-check");
	const groups = [...new Set([...radios].map(r => r.name))];
	const selected = getSelectedOptions();
	
	for (const name of groups) {
		if (!selected[name]) {
			showAlert(`Vui lòng chọn ${name} trước khi thêm vào giỏ.`, "warning");
			return false;
		}
	}
	
	const variantInput = getEl("variantId");
	if (!variantInput?.value) {
		showAlert("Vui lòng chọn đủ thuộc tính sản phẩm.", "danger");
		return false;
	}
	
	return true;
};

// ==========================
// Update Variant Info
// ==========================
const updateVariantInfo = data => {
	const updates = {
		"#current-price": data.price ? formatCurrency(data.price) : "-",
		"#old-price": data.oldPrice && data.oldPrice > data.price ? formatCurrency(data.oldPrice) : "-",
		"#stock-value": data.stock ?? "-"
	};
	
	Object.entries(updates).forEach(([selector, value]) => {
		const el = document.querySelector(selector);
		if (el) el.innerHTML = value;
	});
	
	const mainImg = getEl("mainImg");
	const productDetail = getEl("product-detail");
	const appContext = productDetail?.dataset.context || "";
	
	if (data.imageUrl && mainImg) {
		mainImg.src = `${appContext}${data.imageUrl}`;
	}
	
	const variantInput = getEl("variantId");
	if (variantInput && data.variantId) {
		variantInput.value = data.variantId;
	}
};

// ==========================
// Fetch Variant on Selection
// ==========================
const setupVariantListener = () => {
	const productDetail = getEl("product-detail");
	if (!productDetail) return;
	
	const appContext = productDetail.dataset.context || "";
	const productId = productDetail.dataset.productId;
	
	getEls(".btn-check").forEach(radio => {
		radio.addEventListener("change", async () => {
			const options = { ...getSelectedOptions(), productId };
			
			try {
				const res = await fetch(`${appContext}/api/variant/select`, {
					method: "POST",
					headers: { "Content-Type": "application/json" },
					body: JSON.stringify(options)
				});
				
				if (!res.ok) throw new Error("Không tìm thấy variant");
				
				const data = await res.json();
				updateVariantInfo(data);
			} catch (err) {
				console.error("Lỗi khi cập nhật variant:", err);
			}
		});
	});
};

// ==========================
// Buy Now Handler
// ==========================
const setupBuyNow = () => {
	const form = getEl("buyNowForm");
	if (!form) return;
	
	form.addEventListener("submit", async e => {
		e.preventDefault();
		
		if (!validateSelection()) return;
		
		const variantInput = getEl("variantId");
		const qtyInput = getEl("qty");
		const productDetail = getEl("product-detail");
		const appContext = productDetail?.dataset.context || "";
		
		if (!variantInput?.value) {
			showAlert("Vui lòng chọn đầy đủ thuộc tính sản phẩm.", "warning");
			return;
		}
		
		const formData = new URLSearchParams({
			variantId: variantInput.value,
			quantity: qtyInput ? parseInt(qtyInput.value) : 1
		});
		
		try {
			const res = await fetch(`${appContext}/user/cart/add-now`, {
				method: "POST",
				headers: { "Content-Type": "application/x-www-form-urlencoded" },
				body: formData
			});
			
			if (!res.ok) throw new Error("Không thể thêm sản phẩm");
			
			const data = await res.json();
			console.log("🛒 Kết quả:", data);
			
			if (!data.success) {
				showAlert(data.message || "Không thể thêm sản phẩm.", "danger");
				return;
			}
			
			const checkoutUrl = `${appContext}/user/checkout?selectedItems=${encodeURIComponent(data.cartItemId)}`;
			window.location.href = checkoutUrl;
			
		} catch (err) {
			console.error("Lỗi:", err);
			showAlert("Không thể thêm sản phẩm vào giỏ để thanh toán.", "danger");
		}
	});
};

// ==========================
// Initialize
// ==========================
document.addEventListener("DOMContentLoaded", () => {
	const qtyInput = getEl("qty");
	if (qtyInput) qtyInput.addEventListener("input", syncQty);
	
	setupImageZoom();
	setupBuyNow();
	setupVariantListener();
});



function changeImage(el) {
    document.getElementById("mainImg").src = el.src;
    document.querySelectorAll(".thumb-img").forEach(img => img.classList.remove("active"));
    el.classList.add("active");
}

function changeQty(delta) {
    let qty = document.getElementById("qty");
    let val = parseInt(qty.value) + delta;
    if (val < 1) val = 1;
    qty.value = val;
    syncQty(); // đồng bộ luôn khi bấm +/-
}



// Đồng bộ số lượng từ input qty vào 2 form
function syncQty() {
	let qty = document.getElementById("qty").value;
	const formQty = document.getElementById("formQty");
	const formQtyNow = document.getElementById("formQtyNow");

	if (formQty) formQty.value = qty;
	if (formQtyNow) formQtyNow.value = qty;
}

// Khi DOM load xong mới gắn event
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("qty").addEventListener("input", syncQty);
});

// =============================
// Xử lý chọn variant (từ radio button)
// =============================

function getSelectedOptions() {
    const options = {};
    document.querySelectorAll(".btn-check:checked").forEach(radio => {
        options[radio.name] = radio.value;
    });
    return options;
}

function validateSelection() {
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

document.addEventListener("DOMContentLoaded", function () {
	const productDetail = document.getElementById("product-detail");
	const appContext = productDetail.dataset.context;
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

                const currentPrice = document.querySelector("#current-price");
                const oldPrice = document.querySelector("#old-price");
                const stockStatus = document.querySelector("#stock-status");

                const priceValue = document.querySelector("#price-value");
                const oldPriceValue = document.querySelector("#oldprice-value");
                const stockValue = document.querySelector("#stock-value");

                const mainImg = document.querySelector("#mainImg");

                // 🟢 Cập nhật giá hiện tại
                const formattedPrice = data.price !== undefined
                    ? new Intl.NumberFormat("vi-VN", { style: "currency", currency: "VND" }).format(data.price)
                    : "-";
                if (currentPrice) currentPrice.innerHTML = formattedPrice;
                if (priceValue) priceValue.textContent = formattedPrice;

                // 🟢 Cập nhật giá cũ
                const formattedOldPrice = data.oldPrice && data.oldPrice > data.price
                    ? new Intl.NumberFormat("vi-VN", { style: "currency", currency: "VND" }).format(data.oldPrice)
                    : "-";
                if (oldPrice) oldPrice.innerHTML = formattedOldPrice;
                if (oldPriceValue) oldPriceValue.textContent = formattedOldPrice;

                // 🟢 Cập nhật tồn kho
                if (stockValue) {
                    stockValue.innerHTML = data.stock;
                }

                // 🟢 Cập nhật ảnh chính
                if (data.imageUrl && mainImg) {
                    const cleanPath = data.imageUrl.startsWith("/")
                        ? `${appContext}${data.imageUrl}`
                        : `${appContext}/${data.imageUrl}`;
                    mainImg.src = cleanPath;
                    console.log("Ảnh mới:", cleanPath);
                }
				// 🟢 Gán variantId vào form
				const variantInput = document.getElementById("variantId");
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





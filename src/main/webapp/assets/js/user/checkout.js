console.log("✅ checkout.js new (with dynamic shipping + product discount)");

document.addEventListener("DOMContentLoaded", function() {
	let shippingFee = 30000; // mặc định

	// 🪙 Định dạng tiền VND
	const formatCurrency = (num) =>
		num.toLocaleString("vi-VN", { style: "currency", currency: "VND" });

	// 🧮 Cập nhật tổng toàn đơn
	function updateGrandTotal() {
		let total = 0;
		document.querySelectorAll(".shop-summary-total").forEach((el) => {
			const val = parseFloat(el.dataset.value || "0");
			if (!isNaN(val)) total += val;
		});
		const grandEl = document.getElementById("grand-total");
		if (grandEl) grandEl.textContent = formatCurrency(total);
	}

	// 🚚 Khi thay đổi đơn vị vận chuyển
	const carrierSelect = document.querySelector('select[name="carrierId"]');
	if (carrierSelect) {
		carrierSelect.addEventListener("change", function() {
			const selected = this.selectedOptions[0];
			if (!selected) return;

			const text = selected.textContent;
			const feeMatch = text.match(/([\d.,]+)/); // lấy số trong "(+30.000₫)"
			if (feeMatch) {
				shippingFee = parseFloat(feeMatch[1].replace(/[^\d]/g, "")) || 0;
			}

			// ✅ Cập nhật hiển thị phí vận chuyển trong từng shop (bên trái)
			document.querySelectorAll(".card-body .d-flex").forEach((row) => {
				if (row.textContent.includes("Phí vận chuyển")) {
					const feeEl = row.querySelector("strong");
					if (feeEl) feeEl.textContent = formatCurrency(shippingFee);
				}
			});

			// ✅ Cập nhật phần tóm tắt bên phải
			document.querySelectorAll(".shop-summary-shipping").forEach((el) => {
				el.textContent = formatCurrency(shippingFee);
			});

			// ✅ Cập nhật lại tổng từng shop và toàn đơn
			document.querySelectorAll(".card-body").forEach((shopCard) => {
				// tìm phần tử "Tạm tính" (để biết shop này có bao nhiêu sản phẩm)
				const anyProductRow = shopCard.querySelector(".d-flex.align-items-center");
				if (anyProductRow) updateShopTotals(anyProductRow);
			});
		});
	}

	// 🧾 Khi chọn mã giảm giá theo sản phẩm
	document.querySelectorAll(".promotion-select").forEach((select) => {
		select.addEventListener("change", function() {
			const productRow = this.closest(".d-flex");
			const priceEl = productRow.querySelector(".text-end p");
			const basePrice = parseFloat(priceEl.textContent.replace(/[^\d]/g, ""));
			const type = this.selectedOptions[0].dataset.type;
			const value = parseFloat(this.selectedOptions[0].dataset.value || "0");

			let discount = 0;
			if (type === "percent") discount = basePrice * (value / 100);
			else if (type === "fixed") discount = value;
			if (discount > basePrice) discount = basePrice;

			const newPrice = Math.max(basePrice - discount, 0);

			priceEl.textContent = formatCurrency(newPrice);
			priceEl.dataset.discount = discount.toFixed(0);
			priceEl.title = discount > 0 ? `Đã giảm ${formatCurrency(discount)}` : "";

			updateShopTotals(productRow);
		});
	});

	// 🏪 Cập nhật tổng từng shop
	function updateShopTotals(productRow) {
		const shopCard = productRow.closest(".card-body");
		const shopId = shopCard.querySelector(".shop-subtotal").dataset.shopId;
		let subtotal = 0;
		let discountTotal = 0;

		shopCard.querySelectorAll(".text-end p").forEach((el) => {
			const price = parseFloat(el.textContent.replace(/[^\d]/g, "")) || 0;
			const discount = parseFloat(el.dataset.discount || "0");
			subtotal += price + discount;
			discountTotal += discount;
		});

		const newTotal = Math.max(subtotal + shippingFee - discountTotal, 0);

		const subtotalEl = shopCard.querySelector(".shop-subtotal");
		const totalEl = shopCard.querySelector(".shop-total");
		if (subtotalEl) subtotalEl.textContent = formatCurrency(subtotal);
		if (totalEl) {
			totalEl.textContent = formatCurrency(newTotal);
			totalEl.dataset.value = newTotal.toFixed(0);
		}

		const summarySubtotal = document.querySelector(
			`.shop-summary-subtotal[data-shop-id="${shopId}"]`
		);
		const summaryDiscount = document.querySelector(
			`.shop-summary-discount[data-shop-id="${shopId}"]`
		);
		const summaryShipping = document.querySelector(
			`.shop-summary-shipping[data-shop-id="${shopId}"]`
		);
		const summaryTotal = document.querySelector(
			`#shop-summary-total-${shopId}`
		);

		if (summarySubtotal)
			summarySubtotal.textContent = formatCurrency(subtotal);
		if (summaryDiscount)
			summaryDiscount.textContent = `- ${formatCurrency(discountTotal)}`;
		if (summaryShipping)
			summaryShipping.textContent = formatCurrency(shippingFee);
		if (summaryTotal) {
			summaryTotal.textContent = formatCurrency(newTotal);
			summaryTotal.dataset.value = newTotal.toFixed(0);
		}

		updateGrandTotal();
	}

	updateGrandTotal();
});

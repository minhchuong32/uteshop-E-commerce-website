console.log("checkout.js fixed ✅");
document.addEventListener("DOMContentLoaded", function () {
	const shippingFee = 30000;

	// ✅ Định dạng tiền tệ
	const formatCurrency = (num) =>
		num.toLocaleString("vi-VN", { style: "currency", currency: "VND" });

	// ✅ Cập nhật tổng toàn đơn (chỉ lấy từ phần tóm tắt)
	function updateGrandTotal() {
		let total = 0;
		document.querySelectorAll(".shop-summary-total").forEach((el) => {
			const val = parseFloat(el.dataset.value || "0");
			if (!isNaN(val)) total += val;
		});
		const grandEl = document.getElementById("grand-total");
		if (grandEl) grandEl.textContent = formatCurrency(total);
		

	}

	// ✅ Khi người dùng chọn mã giảm giá
	document.querySelectorAll(".promotion-select").forEach((select) => {
		select.addEventListener("change", function () {
			const shopId = this.dataset.shopId;
			const subtotalEl = document.querySelector(
				`.shop-subtotal[data-shop-id="${shopId}"]`
			);
			const totalEl = document.querySelector(
				`.shop-total[data-shop-id="${shopId}"]`
			); // chỉ phần bên trái
			if (!subtotalEl || !totalEl) return;

			// Lấy subtotal gốc
			const subtotal = parseFloat(subtotalEl.dataset.subtotal || "0");
			const type = this.selectedOptions[0].dataset.type;
			const value = parseFloat(this.selectedOptions[0].dataset.value || "0");

			let discount = 0;
			if (type === "percent") {
				discount = subtotal * (value / 100);
			} else if (type === "fixed") {
				discount = value;
			}

			// ✅ Tính lại tổng
			let newTotal = subtotal + shippingFee - discount;
			if (newTotal < 0) newTotal = 0;

			// ✅ Cập nhật phần hiển thị ở cột trái (shop chính)
			totalEl.textContent = formatCurrency(newTotal);
			totalEl.dataset.value = newTotal.toFixed(0);

			if (discount > 0) {
				totalEl.classList.add("text-success");
				totalEl.title = `Đã giảm ${formatCurrency(discount)}`;
			} else {
				totalEl.classList.remove("text-success");
				totalEl.removeAttribute("title");
			}

			// ✅ Cập nhật phần tóm tắt bên phải
			const summarySubtotal = document.querySelector(
				`.shop-summary-subtotal[data-shop-id="${shopId}"]`
			);
			const summaryDiscount = document.querySelector(
				`.shop-summary-discount[data-shop-id="${shopId}"]`
			);
			const summaryTotal = document.querySelector(
			    `#shop-summary-total-${shopId}`
			);

			if (summarySubtotal)
				summarySubtotal.textContent = formatCurrency(subtotal);
			if (summaryDiscount)
				summaryDiscount.textContent = `- ${formatCurrency(discount)}`;
			if (summaryTotal) {
				summaryTotal.textContent = formatCurrency(newTotal);
				summaryTotal.dataset.value = newTotal.toFixed(0);
			}

			// ✅ Cập nhật tổng toàn đơn (chỉ phần tóm tắt)
			updateGrandTotal();
		});
	});

	// ✅ Gọi ban đầu khi trang load
	updateGrandTotal();
});

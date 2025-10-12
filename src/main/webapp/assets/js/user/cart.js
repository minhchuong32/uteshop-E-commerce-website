console.log("✅ File cart.js đã load!");

document.addEventListener("DOMContentLoaded", function () {
    console.log("✅ DOMContentLoaded đã kích hoạt");

    const checkboxes = document.querySelectorAll(".cart-checkbox");
    const totalPriceEl = document.getElementById("total-price");
    const form = document.getElementById("cartForm"); 

    // 💰 Tính tổng tiền sản phẩm được chọn
    function calculateTotal() {
        let total = 0;

        checkboxes.forEach(cb => {
            if (cb.checked) {
                const itemRow = cb.closest(".cart-item");
                if (!itemRow) {
                    console.warn("Không tìm thấy dòng .cart-item cho checkbox:", cb);
                    return;
                }

                const itemTotalEl = itemRow.querySelector(".item-total");
                if (!itemTotalEl) {
                    console.warn("Không tìm thấy .item-total trong .cart-item:", itemRow);
                    return;
                }

                const price = parseFloat(itemTotalEl.dataset.price) || 0;
                const qty = parseInt(itemTotalEl.dataset.qty) || 0;
                total += price * qty;
            }
        });

        totalPriceEl.textContent =
            new Intl.NumberFormat("vi-VN").format(total) + " ₫";

        console.log("💰 Tổng tiền hiện tại:", total);
    }

    // 🧮 Lắng nghe checkbox thay đổi
    checkboxes.forEach(cb => cb.addEventListener("change", calculateTotal));
    calculateTotal();

    // 🗑️ Xử lý nút Xóa
    document.querySelectorAll(".btn-remove").forEach(btn => {
        btn.addEventListener("click", () => {
            const id = btn.dataset.id;
            if (confirm("Bạn có chắc muốn xóa sản phẩm này khỏi giỏ hàng?")) {
                fetch(`${window.location.origin}${window.contextPath || ""}/user/cart/remove`, {
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },
                    body: `cartItemId=${encodeURIComponent(id)}`
                }).then(() => location.reload());
            }
        });
    });

	// 🧾 Khi nhấn “Thanh toán” → chỉ gửi các sản phẩm đã chọn
	if (form) {
		form.addEventListener("submit", (e) => {
		    e.preventDefault();

		    // ✅ Lấy tất cả checkbox được tick trên toàn trang, không chỉ trong form
		    const checked = [...document.querySelectorAll(".cart-checkbox:checked")];
		    if (checked.length === 0) {
		        alert("Vui lòng chọn ít nhất một sản phẩm để thanh toán!");
		        return;
		    }

		    const selectedIds = checked.map(cb => cb.value).join(",");
		    console.log("🧺 Selected IDs gửi đi:", selectedIds);

		    const url = `${window.contextPath}/user/checkout?selectedItems=${encodeURIComponent(selectedIds)}`;
		    window.location.href = url;
		});
	}

});

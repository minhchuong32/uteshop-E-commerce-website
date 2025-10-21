// Đảm bảo code chỉ chạy sau khi DOM đã được tải hoàn toàn
document.addEventListener("DOMContentLoaded", function() {
	/* Dữ liệu được truyền từ JSP thông qua object `revenuePageData` */

	// 1. Biểu đồ cột: Doanh thu theo tháng
	const revenueChartEl = document.getElementById("revenueChart");
	if (revenueChartEl) {
		const ctx1 = revenueChartEl.getContext("2d");
		new Chart(ctx1, {
			type: "bar",
			data: {
				labels: revenuePageData.months,
				datasets: [
					{
						label: "Doanh thu (₫)",
						data: revenuePageData.revenues,
						backgroundColor: "rgba(54, 162, 235, 0.6)",
						borderColor: "rgb(54, 162, 235)",
						borderWidth: 1,
					},
				],
			},
			options: {
				responsive: true,
				scales: {
					y: {
						beginAtZero: true,
						title: { display: true, text: "VNĐ", font: { weight: "bold" } },
						ticks: { callback: (value) => value.toLocaleString("vi-VN") + " ₫" },
					},
					x: { title: { display: true, text: "Tháng", font: { weight: "bold" } } },
				},
				plugins: {
					legend: { display: false },
					tooltip: {
						callbacks: {
							label: (ctx) =>
								ctx.dataset.label +
								": " +
								ctx.parsed.y.toLocaleString("vi-VN") +
								" ₫",
						},
					},
				},
			},
		});
	}

	// 2. Biểu đồ tròn: Tỷ lệ doanh thu và phí
	const feeChartEl = document.getElementById("feeChart");
	if (feeChartEl) {
		const ctx2 = feeChartEl.getContext("2d");
		new Chart(ctx2, {
			type: "pie",
			data: {
				labels: ["Doanh thu sau phí", "Phí sàn"],
				datasets: [
					{
						data: [revenuePageData.totalRevenue, revenuePageData.platformFee],
						backgroundColor: ["#28a745", "#dc3545"],
					},
				],
			},
		});
	}

	// 3. Biểu đồ đường: Tăng trưởng
	const growthChartEl = document.getElementById("growthChart");
	if (growthChartEl) {
		const revenues = revenuePageData.revenues;
		const growthRates = [];

		for (let i = 1; i < revenues.length; i++) {
			const prev = revenues[i - 1];
			const curr = revenues[i];
			const rate = prev > 0 ? ((curr - prev) / prev) * 100 : 0;
			growthRates.push(rate.toFixed(2));
		}

		const growthCtx = growthChartEl.getContext("2d");
		new Chart(growthCtx, {
			type: "line",
			data: {
				labels: revenuePageData.months.slice(1),
				datasets: [
					{
						label: "Tăng trưởng (%)",
						data: growthRates,
						borderColor: "rgb(255, 99, 132)",
						tension: 0.3,
						fill: false,
						borderWidth: 2,
						pointRadius: 4,
					},
				],
			},
			options: {
				responsive: true,
				scales: {
					y: {
						beginAtZero: true,
						title: { display: true, text: "% Tăng trưởng" },
					},
					x: {
						title: { display: true, text: "Tháng" },
					},
				},
				plugins: {
					tooltip: {
						callbacks: {
							label: (ctx) => ctx.parsed.y + "%",
						},
					},
				},
			},
		});
	}
});

// Hàm validation cho form lọc ngày
function validateDateRange() {
	const start = document.getElementById("startDate").value;
	const end = document.getElementById("endDate").value;
	const alertBox = document.getElementById("alertBox");

	// Reset trạng thái alert
	alertBox.classList.add("d-none", "alert-warning");
	alertBox.classList.remove("alert-danger");
	alertBox.innerHTML = `⚠️ Vui lòng chọn đầy đủ <strong>Từ ngày</strong> và <strong>Đến
				ngày</strong> trước khi lọc.
			<button type="button" class="btn-close" data-bs-dismiss="alert"
				aria-label="Close"></button>`;


	// Nếu chưa chọn ngày → hiển thị cảnh báo
	if (!start || !end) {
		alertBox.classList.remove("d-none"); // hiện alert
		window.scrollTo({ top: 0, behavior: "smooth" }); // cuộn lên đầu trang
		return false; // chặn submit
	}

	// Nếu ngày bắt đầu sau ngày kết thúc → cảnh báo lỗi logic
	if (new Date(start) > new Date(end)) {
		alertBox.classList.remove("d-none", "alert-warning");
		alertBox.classList.add("alert-danger"); // Đổi màu alert
		alertBox.innerHTML = `⚠️ Ngày bắt đầu không thể sau ngày kết thúc.
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
		window.scrollTo({ top: 0, behavior: "smooth" });
		return false;
	}

	// Ẩn cảnh báo nếu hợp lệ
	alertBox.classList.add("d-none");
	return true;
}
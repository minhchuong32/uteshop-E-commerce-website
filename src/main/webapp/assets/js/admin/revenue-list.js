// ==========================
// Chart: Doanh thu theo tháng
// ==========================
const ctx1 = document.getElementById('revenueChart').getContext('2d');
new Chart(ctx1, {
    type: 'bar',
    data: {
        labels: months,
        datasets: [{
            label: 'Doanh thu (₫)',
            data: revenues,
            backgroundColor: 'rgba(54, 162, 235, 0.6)',
            borderColor: 'rgb(54, 162, 235)',
            borderWidth: 1
        }]
    },
    options: {
        responsive: true,
        scales: {
            y: {
                beginAtZero: true,
                title: { display: true, text: 'VNĐ', font: { weight: 'bold' } },
                ticks: { callback: value => value.toLocaleString('vi-VN') + ' ₫' }
            },
            x: { title: { display: true, text: 'Tháng', font: { weight: 'bold' } } }
        },
        plugins: {
            legend: { display: false },
            tooltip: {
                callbacks: {
                    label: ctx => ctx.dataset.label + ': ' + ctx.parsed.y.toLocaleString('vi-VN') + ' ₫'
                }
            }
        }
    }
});

// ==========================
// Chart: Tỷ lệ doanh thu & phí sàn
// ==========================
const ctx2 = document.getElementById('feeChart').getContext('2d');
new Chart(ctx2, {
    type: 'pie',
    data: {
        labels: ['Doanh thu sau phí', 'Phí sàn'],
        datasets: [{
            data: [totalRevenue, platformFee],
            backgroundColor: ['#28a745', '#dc3545']
        }]
    }
});

// ==========================
// Hàm kiểm tra khoảng ngày lọc
// ==========================
function validateDateRange() {
    const start = document.getElementById("startDate").value;
    const end = document.getElementById("endDate").value;
    const alertBox = document.getElementById("alertBox");

    // Nếu chưa chọn ngày → cảnh báo
    if (!start || !end) {
        alertBox.classList.remove("d-none");
        window.scrollTo({ top: 0, behavior: 'smooth' });
        return false;
    }

    // Nếu ngày bắt đầu > ngày kết thúc → lỗi logic
    if (new Date(start) > new Date(end)) {
        alertBox.classList.remove("d-none");
        alertBox.classList.add("alert-danger");
        alertBox.innerHTML = "⚠️ Ngày bắt đầu không thể sau ngày kết thúc.";
        window.scrollTo({ top: 0, behavior: 'smooth' });
        return false;
    }

    // Hợp lệ → ẩn cảnh báo
    alertBox.classList.add("d-none");
    return true;
}

// ==========================
// Chart: Phân tích tăng trưởng doanh thu
// ==========================
const growthCtx = document.getElementById('growthChart').getContext('2d');
const growthRates = [];

for (let i = 1; i < revenues.length; i++) {
    const prev = revenues[i - 1];
    const curr = revenues[i];
    const rate = prev > 0 ? ((curr - prev) / prev) * 100 : 0;
    growthRates.push(rate.toFixed(2));
}

new Chart(growthCtx, {
    type: 'line',
    data: {
        labels: months.slice(1),
        datasets: [{
            label: 'Tăng trưởng (%)',
            data: growthRates,
            borderColor: 'rgb(255, 99, 132)',
            tension: 0.3,
            fill: false,
            borderWidth: 2,
            pointRadius: 4
        }]
    },
    options: {
        responsive: true,
        scales: {
            y: {
                beginAtZero: true,
                title: { display: true, text: '% Tăng trưởng' }
            },
            x: {
                title: { display: true, text: 'Tháng' }
            }
        },
        plugins: {
            tooltip: {
                callbacks: {
                    label: ctx => ctx.parsed.y + '%'
                }
            }
        }
    }
});


document.addEventListener('DOMContentLoaded', () => {

  // Lấy canvas elements từ DOM
  const typeChartCanvas = document.getElementById('typeChart');
  const avgChartCanvas = document.getElementById('avgChart');

  // Kiểm tra xem các element có tồn tại không trước khi vẽ biểu đồ
  if (typeChartCanvas) {
    // === BIỂU ĐỒ PHÂN BỐ LOẠI (DOUGHNUT) ===

    // Lấy dữ liệu từ data attributes của canvas
    const percentCount = parseInt(typeChartCanvas.dataset.percentCount) || 0;
    const fixedCount = parseInt(typeChartCanvas.dataset.fixedCount) || 0;

    new Chart(typeChartCanvas, {
      type: 'doughnut',
      data: {
        labels: ['Giảm theo %', 'Giảm cố định'],
        datasets: [{
          data: [percentCount, fixedCount],
          backgroundColor: ['#36A2EB', '#4BC0C0']
        }]
      },
      options: {
        maintainAspectRatio: false,
        plugins: { legend: { position: 'bottom' } }
      }
    });
  }

  if (avgChartCanvas) {
    // === BIỂU ĐỒ GIÁ TRỊ TRUNG BÌNH (2 TRỤC Y) ===

    // Lấy dữ liệu từ data attributes của canvas
    const avgPercent = parseFloat(avgChartCanvas.dataset.avgPercent) || 0;
    const avgFixed = parseFloat(avgChartCanvas.dataset.avgFixed) || 0;

    new Chart(avgChartCanvas, {
      data: {
        labels: ['Giảm trung bình'],
        datasets: [
          {
            type: 'bar',
            label: 'Trung bình giảm (%)',
            data: [avgPercent],
            yAxisID: 'yPercent',
            backgroundColor: 'rgba(54, 162, 235, 0.7)',
            borderColor: 'rgb(54, 162, 235)',
            borderWidth: 2
          },
          {
            type: 'bar',
            label: 'Trung bình giảm (₫)',
            data: [avgFixed],
            yAxisID: 'yVND',
            backgroundColor: 'rgba(75, 192, 192, 0.7)',
            borderColor: 'rgb(75, 192, 192)',
            borderWidth: 2
          }
        ]
      },
      options: {
        maintainAspectRatio: false,
        plugins: {
          legend: { position: 'bottom' },
          tooltip: {
            callbacks: {
              label: function(context) {
                const val = context.parsed.y;
                if (context.dataset.yAxisID === 'yPercent') {
                  return ' ' + val.toFixed(2) + '%';
                } else {
                  return ' ' + new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(val);
                }
              }
            }
          }
        },
        scales: {
          yPercent: {
            position: 'left',
            beginAtZero: true,
            title: { display: true, text: 'Phần trăm (%)' },
            ticks: {
              callback: val => val + '%'
            }
          },
          yVND: {
            position: 'right',
            beginAtZero: true,
            grid: { drawOnChartArea: false }, // tránh chồng lưới
            title: { display: true, text: 'Giá trị (VNĐ)' },
            ticks: {
              callback: val => new Intl.NumberFormat('vi-VN').format(val)
            }
          }
        }
      }
    });
  }


  // === DATATABLE ===
  $('#promotionTable').DataTable({
    pageLength: 5,
    lengthMenu: [[5, 10, 25, 50, -1], [5, 10, 25, 50, "Tất cả"]],
    language: {
      lengthMenu: "Hiển thị _MENU_ dòng",
      search: "Tìm kiếm:",
      paginate: { previous: "Trước", next: "Sau" },
      info: "Hiển thị _START_–_END_ / _TOTAL_ khuyến mãi",
      emptyTable: "Không có dữ liệu"
    }
  });

  // === MODAL XÓA ===
  const modal = document.getElementById('confirmDeleteModal');
  if (modal) {
      modal.addEventListener('show.bs.modal', e => {
        document.getElementById('deleteConfirmBtn').href =
          e.relatedTarget.getAttribute('data-delete-url');
      });
  }
});
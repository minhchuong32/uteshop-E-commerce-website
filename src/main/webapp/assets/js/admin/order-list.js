// order-list.js - Quản lý đơn hàng

// Khởi tạo DataTable cho bảng đơn hàng
function initOrderTable() {
    const table = $('#ordersTable').DataTable({
        pageLength: 5,
        lengthMenu: [[5, 10, 25, 50, -1], [5, 10, 25, 50, "Tất cả"]],
        language: {
            lengthMenu: "Hiển thị _MENU_ dòng",
            search: "Tìm kiếm:",
            paginate: { 
                previous: "Trước", 
                next: "Sau" 
            },
            info: "Hiển thị _START_–_END_ / _TOTAL_ đơn hàng",
            emptyTable: "Không có dữ liệu"
        }
    });

    return table;
}

// Modal xóa
document.addEventListener("DOMContentLoaded", function () {
  const modal = document.getElementById('confirmDeleteOrderModal');
  if (modal) {
    modal.addEventListener('show.bs.modal', e => {
      const button = e.relatedTarget;
      const deleteUrl = button.getAttribute('data-delete-url');
      document.getElementById('deleteConfirmBtn').href = deleteUrl;
    });
  }
});


// Thiết lập custom filter cho DataTable
function setupTableFilters(table) {
    // Custom filter function
    $.fn.dataTable.ext.search.push(
        function(settings, data, dataIndex) {
            const orderStatus = $('#filterOrderStatus').val();
            const deliveryStatus = $('#filterDeliveryStatus').val();
            const paymentMethod = $('#filterPayment').val();

            const rowOrderStatus = data[5].replace(/<[^>]+>/g, '').trim(); // cột trạng thái đơn
            const rowDeliveryStatus = data[8].replace(/<[^>]+>/g, '').trim(); // cột trạng thái giao hàng
            const rowPayment = data[3].trim(); // cột thanh toán

            if ((orderStatus === "" || rowOrderStatus === orderStatus) &&
                (deliveryStatus === "" || rowDeliveryStatus === deliveryStatus) &&
                (paymentMethod === "" || rowPayment === paymentMethod)) {
                return true;
            }
            return false;
        }
    );

    // Khi thay đổi bộ lọc
    $('#filterOrderStatus, #filterDeliveryStatus, #filterPayment').on('change', function() {
        table.draw();
    });

    // Reset bộ lọc
    $('#resetFilters').on('click', function() {
        $('#filterOrderStatus').val('');
        $('#filterDeliveryStatus').val('');
        $('#filterPayment').val('');
        table.draw();
    });
}

// Vẽ biểu đồ hiệu suất giao hàng
function initDeliveryChart(shipperNames, totalDeliveries, successRates) {
    const ctx = document.getElementById('deliveryChart');
    if (!ctx) return;

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: shipperNames,
            datasets: [
                { 
                    label: 'Tổng đơn hàng', 
                    data: totalDeliveries, 
                    backgroundColor: 'rgba(0,85,141,0.6)',
                    yAxisID: 'y'
                },
                { 
                    label: 'Tỷ lệ giao thành công (%)', 
                    data: successRates, 
                    backgroundColor: 'rgba(40,167,69,0.6)', 
                    yAxisID: 'y1' 
                }
            ]
        },
        options: {
            responsive: true,
            interaction: {
                mode: 'index',
                intersect: false,
            },
            scales: {
                y: { 
                    beginAtZero: true, 
                    position: 'left',
                    title: { 
                        display: true, 
                        text: 'Tổng số đơn' 
                    } 
                },
                y1: { 
                    beginAtZero: true, 
                    position: 'right', 
                    title: { 
                        display: true, 
                        text: 'Tỷ lệ (%)' 
                    }, 
                    max: 100, 
                    grid: { 
                        drawOnChartArea: false 
                    } 
                }
            }
        }
    });
}

// Khởi tạo khi document ready
$(document).ready(function() {
	// Khởi tạo DataTable và lưu vào biến
	  const table = initOrderTable();

	  // Thiết lập bộ lọc cho bảng
	  setupTableFilters(table);

});
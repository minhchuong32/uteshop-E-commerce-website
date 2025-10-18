// dashboard-complaint.js - Quản lý khiếu nại

// Khởi tạo biểu đồ trạng thái
function initStatusChart(statusLabels, statusCounts) {
    const ctx = document.getElementById('statusChart');
    if (!ctx) return;
    
    new Chart(ctx, {
        type: 'pie',
        data: {
            labels: statusLabels,
            datasets: [{
                data: statusCounts,
                backgroundColor: ['#007bff', '#ffc107', '#28a745', '#dc3545', '#6c757d']
            }]
        }
    });
}

// Khởi tạo biểu đồ theo tháng
function initMonthChart(monthLabels, monthCounts) {
    const ctx = document.getElementById('monthChart');
    if (!ctx) return;
    
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: monthLabels,
            datasets: [{
                label: 'Số khiếu nại',
                data: monthCounts,
                backgroundColor: 'rgba(54, 162, 235, 0.6)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        },
        options: { 
            scales: { 
                y: { 
                    beginAtZero: true 
                } 
            } 
        }
    });
}

// Khởi tạo DataTable
function initComplaintTable() {
    $('#complaintTable').DataTable({
        pageLength: 5,
        lengthMenu: [[5, 10, 25, 50, -1], [5, 10, 25, 50, "Tất cả"]],
        language: {
            lengthMenu: "Hiển thị _MENU_ dòng",
            search: "Tìm kiếm:",
            paginate: { 
                previous: "Trước", 
                next: "Sau" 
            },
            info: "Hiển thị _START_–_END_ / _TOTAL_ Khiếu nại",
            emptyTable: "Không có dữ liệu"
        }
    });
}

// Xử lý khi hiển thị modal xóa khiếu nại
document.addEventListener('DOMContentLoaded', function() {
    // Khởi tạo DataTable
    initComplaintTable();

    // Lấy modal
    const modal = document.getElementById('confirmDeleteComplaintModal');
    if (modal) {
        modal.addEventListener('show.bs.modal', function(event) {
            const button = event.relatedTarget; // Nút được bấm
            const complaintId = button.getAttribute('data-id');
            const baseUrl = button.getAttribute('data-url');

            // Cập nhật href cho nút Xóa trong modal
            const deleteBtn = document.getElementById('deleteComplaintBtn');
            deleteBtn.href = `${baseUrl}?id=${complaintId}`;
        });
    }
});


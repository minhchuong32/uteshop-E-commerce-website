// Khởi tạo DataTable
function initUserTable() {
    $('#userTable').DataTable({
        pageLength: 5,
        lengthMenu: [[5, 10, 25, 50, -1], [5, 10, 25, 50, "Tất cả"]],
        language: {
            lengthMenu: "Hiển thị _MENU_ dòng",
            search: "Tìm kiếm:",
            paginate: { 
                previous: "Trước", 
                next: "Sau" 
            },
            info: "Hiển thị _START_–_END_ / _TOTAL_ Người dùng",
            emptyTable: "Không có dữ liệu"
        }
    });
}


// Khởi tạo khi DOM loaded
document.addEventListener('DOMContentLoaded', function() {
    // Khởi tạo DataTable
    initUserTable();
});

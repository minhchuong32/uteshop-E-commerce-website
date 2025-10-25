// Khởi tạo DataTable
function initShopTable() {
	$('#shopTable').DataTable({
		pageLength: 10,
		lengthMenu: [[10, 25, 50, -1], [10, 25, 50, "Tất cả"]],
		language: {
			lengthMenu: "Hiển thị _MENU_ dòng",
			search: "Tìm kiếm:",
			paginate: {
				previous: "Trước",
				next: "Sau"
			},
			info: "Hiển thị _START_–_END_ / _TOTAL_ Cửa hàng",
			emptyTable: "Không có dữ liệu"
		}
	});
}


// Khởi tạo khi DOM loaded
document.addEventListener('DOMContentLoaded', function() {
	// Khởi tạo DataTable
	initShopTable();
});
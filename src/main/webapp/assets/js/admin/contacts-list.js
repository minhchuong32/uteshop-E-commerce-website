// ======================
// Xử lý DataTable + Modal Quản lý Đơn vị Vận chuyển
// ======================

$(document).ready(function() {
	// ====================== KHỞI TẠO DATATABLE ======================
	$('#contactTable').DataTable({
		pageLength: 5,
		ordering: true,
		lengthMenu: [
			[5, 10, 25, 50, -1],
			[5, 10, 25, 50, 'Tất cả']
		],
		language: {
			lengthMenu: 'Hiển thị _MENU_ dòng',
			search: 'Tìm kiếm:',
			paginate: { previous: 'Trước', next: 'Sau' },
			info: 'Hiển thị _START_–_END_ / _TOTAL_ Liên hệ',
			emptyTable: 'Không có dữ liệu'
		}
	});

	// ====================== HIỆU ỨNG CẢNH BÁO (TÙY CHỌN) ======================
	const alertBox = $('#alertBox');
	if (alertBox.length) {
		setTimeout(() => alertBox.fadeOut(400), 3000);
	}
});

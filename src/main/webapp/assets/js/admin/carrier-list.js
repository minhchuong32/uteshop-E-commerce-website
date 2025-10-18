// ======================
// Xử lý DataTable + Modal Quản lý Đơn vị Vận chuyển
// ======================

$(document).ready(function () {
    // ====================== KHỞI TẠO DATATABLE ======================
    $('#carrierTable').DataTable({
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
            info: 'Hiển thị _START_–_END_ / _TOTAL_ Nhà Vận Chuyển',
            emptyTable: 'Không có dữ liệu'
        }
    });

    // ====================== MODAL THÊM ======================
    $('#btnAddCarrier').on('click', function () {
        $('#addCarrierForm')[0].reset(); // Xóa nội dung form cũ
        new bootstrap.Modal($('#addCarrierModal')).show();
    });

    // ====================== MODAL SỬA ======================
    $('#carrierTable tbody').on('click', '.editBtn', function () {
        const btn = $(this);
        $('#editId').val(btn.data('id'));
        $('#editName').val(btn.data('name'));
        $('#editFee').val(btn.data('fee'));
        $('#editDescription').val(btn.data('description'));

        new bootstrap.Modal($('#editCarrierModal')).show();
    });

    // ====================== MODAL XÓA ======================
    const deleteModal = new bootstrap.Modal($('#confirmDeleteModal'));

    $('#carrierTable tbody').on('click', '.deleteBtn', function () {
        const id = $(this).data('id');
        $('#deleteConfirmBtn').attr('href', `${contextPath}/admin/carriers/delete?id=${id}`);
        deleteModal.show();
    });

    // ====================== HIỆU ỨNG CẢNH BÁO (TÙY CHỌN) ======================
    const alertBox = $('#alertBox');
    if (alertBox.length) {
        setTimeout(() => alertBox.fadeOut(400), 3000);
    }
});

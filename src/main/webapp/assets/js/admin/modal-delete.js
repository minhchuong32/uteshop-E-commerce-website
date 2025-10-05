// modal-delete.js
document.addEventListener("DOMContentLoaded", function () {
    var deleteModal = document.getElementById('confirmDeleteModal');
    var confirmBtn = document.getElementById('deleteConfirmBtn');

    if (deleteModal) {
        deleteModal.addEventListener('show.bs.modal', function (event) {
            var button = event.relatedTarget;

            // Lấy id và baseUrl từ attribute
            var itemId = button.getAttribute('data-id');
            var baseUrl = button.getAttribute('data-url');

            // Ghép URL xóa
            if (itemId && baseUrl) {
                var deleteUrl = baseUrl + "?id=" + itemId;
                confirmBtn.setAttribute('href', deleteUrl);
            }
        });
    }
});

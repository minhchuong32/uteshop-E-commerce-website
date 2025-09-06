
document.addEventListener("DOMContentLoaded", function () {
    var deleteModal = document.getElementById('confirmDeleteModal');
    var confirmBtn = document.getElementById('deleteConfirmBtn');

    deleteModal.addEventListener('show.bs.modal', function (event) {
        var button = event.relatedTarget;
        var userId = button.getAttribute('data-user-id');
        var deleteUrl = '${pageContext.request.contextPath}/admin/users/delete?id=' + userId;
        confirmBtn.setAttribute('href', deleteUrl);
    });
});


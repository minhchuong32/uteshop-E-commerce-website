<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <h3>Chi tiết người dùng</h3>
    <table class="table">
        <tr><th>ID:</th><td>${user.user_id}</td></tr>
        <tr><th>Username:</th><td>${user.username}</td></tr>
        <tr><th>Email:</th><td>${user.email}</td></tr>
        <tr><th>Role:</th><td>${user.role}</td></tr>
        <tr><th>Status:</th><td>${user.status}</td></tr>
        <tr><th>Ngày tham gia:</th><td>${user.createDate}</td></tr>
    </table>
    <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-secondary">Quay lại</a>
</div>

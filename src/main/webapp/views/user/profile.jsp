<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<div class="container mt-4">
	<div class="card shadow p-4" style="max-width: 650px; margin: auto;">
		<h3 class="text-center mb-4">Hồ sơ cá nhân</h3>

		<!-- Thông báo -->
		<c:if test="${not empty error}">
			<div class="alert alert-danger text-center">${error}</div>
		</c:if>
		<c:if test="${not empty success}">
			<div class="alert alert-success text-center">${success}</div>
		</c:if>

		<c:url var="profileAction" value="/user/profile" />

		<!-- Form update -->
		<form action="${profileAction}" method="post"
			enctype="multipart/form-data">

			<div class="text-center mb-4">
				<c:choose>
					<c:when test="${not empty sessionScope.account.avatar}">
						<img
							src="${pageContext.request.contextPath}/uploads/${sessionScope.account.avatar}"
							alt="Avatar" class="rounded-circle img-thumbnail mx-auto d-block"
							style="width: 150px; height: 150px; object-fit: cover;">
					</c:when>
					<c:otherwise>
						<img src="${pageContext.request.contextPath}/assets/images/default_avatar.png"
							alt="Default Avatar"
							class="rounded-circle img-thumbnail mx-auto d-block"
							style="width: 150px; height: 150px; object-fit: cover;">
					</c:otherwise>
				</c:choose>

				<p class="text-muted mt-2">ID: ${sessionScope.account.user_id}</p>
			</div>

			<div class="mb-3">
				<label class="form-label">Tên đăng nhập</label> <input type="text"
					class="form-control" name="username"
					value="${sessionScope.account.username}" required>
			</div>

			<div class="mb-3">
				<label class="form-label">Email</label> <input type="email"
					class="form-control" name="email"
					value="${sessionScope.account.email}" required>
			</div>

			<div class="mb-3">
				<label class="form-label">Trạng thái</label> <input type="text"
					class="form-control" value="${sessionScope.account.status}"
					readonly>
			</div>

			<div class="mb-3">
				<label class="form-label">Ngày tạo</label> <input type="text"
					class="form-control" value="${sessionScope.account.createDate}"
					readonly>
			</div>

			<div class="mb-3">
				<label class="form-label">Ảnh đại diện (chọn file mới)</label> <input
					type="file" class="form-control" name="avatarFile" accept="image/*">
			</div>

			<hr>
			<h5 class="mb-3">🔒 Đổi mật khẩu</h5>

			<div class="mb-3">
				<label class="form-label">Mật khẩu hiện tại</label> <input
					type="password" class="form-control" name="oldPassword">
			</div>
			<div class="mb-3">
				<label class="form-label">Mật khẩu mới</label> <input
					type="password" class="form-control" name="newPassword">
			</div>
			<div class="mb-3">
				<label class="form-label">Xác nhận mật khẩu mới</label> <input
					type="password" class="form-control" name="confirmPassword">
			</div>

			<div class="text-center mt-4">
				<button type="submit" class="btn btn-success">💾 Lưu thay
					đổi</button>
			</div>
		</form>
	</div>
</div>

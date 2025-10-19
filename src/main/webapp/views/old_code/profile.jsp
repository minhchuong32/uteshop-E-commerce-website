<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<div class="container mt-4 mb-4">
	<div class="card shadow p-4" style="max-width: 900px; margin: auto;">
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
			<div class="row">
				<!-- Cột trái: Avatar -->
				<div class="col-md-4 text-center border-end">
					<c:choose>
						<c:when test="${not empty sessionScope.account.avatar}">
							<img
								src="${pageContext.request.contextPath}/assets/images${sessionScope.account.avatar}"
								alt="Avatar" class="rounded-circle img-thumbnail mb-3"
								style="width: 180px; height: 180px; object-fit: cover;">
						</c:when>
						<c:otherwise>
							<img
								src="${pageContext.request.contextPath}assets/images/avatar/default.jpg"
								alt="Default Avatar" class="rounded-circle img-thumbnail mb-3"
								style="width: 180px; height: 180px; object-fit: cover;">
						</c:otherwise>
					</c:choose>
					<p class="text-muted">ID: ${sessionScope.account.userId}</p>

					<div class="mb-3">
						<label class="form-label">Ảnh đại diện (chọn file mới)</label> <input
							type="file" class="form-control" name="avatarFile"
							accept="image/*">
					</div>
				</div>

				<!-- Cột phải: Thông tin -->
				<div class="col-md-8 ps-4">
					<div class="mb-3">
						<label class="form-label">Tên đăng nhập</label> <input type="text"
							class="form-control" name="username"
							value="${sessionScope.account.username}" required>
					</div>

					<div class="mb-3">
						<label class="form-label">Họ và tên</label> <input type="text"
							class="form-control" name="name"
							value="${sessionScope.account.name}" required>
					</div>

					<div class="mb-3">
						<label class="form-label">Email</label> <input type="email"
							class="form-control" name="email"
							value="${sessionScope.account.email}" required>
					</div>

					<div class="mb-3">
						<label class="form-label">Số điện thoại</label> <input type="text"
							class="form-control" name="phone"
							value="${sessionScope.account.phone}" required>
					</div>

					<div class="mb-3">
						<label class="form-label">Địa chỉ</label>
						<textarea class="form-control" name="address" rows="2">${sessionScope.account.address}</textarea>

					</div>

					<div class="mb-3">
						<label class="form-label">Trạng thái</label> <input type="text"
							class="form-control" value="${sessionScope.account.status}"
							readonly>
					</div>

					<hr>
					<h5 class="mb-3">🔒 Đổi mật khẩu</h5>

					<div class="row">
						<div class="col-md-6 mb-3">
							<label class="form-label">Mật khẩu hiện tại</label> <input
								type="password" class="form-control" name="oldPassword">
						</div>
						<div class="col-md-6 mb-3">
							<label class="form-label">Mật khẩu mới</label> <input
								type="password" class="form-control" name="newPassword">
						</div>
						<div class="col-md-6 mb-3">
							<label class="form-label">Xác nhận mật khẩu mới</label> <input
								type="password" class="form-control" name="confirmPassword">
						</div>
					</div>
					<!-- Link Quên mật khẩu -->
					<div class="mb-3">
						<a href="${pageContext.request.contextPath}/forgot-password"
							class="text-decoration-none text-primary-custom"> <i
							class="bi bi-question-circle me-1"></i> Quên mật khẩu?
						</a>
					</div>
					<!-- Nút cập nhật -->
					<div class="text-end mb-4">
						<button type="submit" class="btn btn-primary-custom btn-lg px-4">
							Cập nhật</button>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>
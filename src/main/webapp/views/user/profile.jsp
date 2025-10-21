<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<!-- CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/user/profile.css">



<div class="profile-container">
	<div class="profile-card">
		<div class="profile-header">
			<h3>
				<i class="fas fa-user-circle"></i> Hồ sơ cá nhân
			</h3>
		</div>

		<div class="profile-body">
			<!-- Alerts -->
			<c:if test="${not empty error}">
				<div class="alert alert-danger">
					<i class="fas fa-exclamation-circle"></i> ${error}
				</div>
			</c:if>
			<c:if test="${not empty success}">
				<div class="alert alert-success">
					<i class="fas fa-check-circle"></i> ${success}
				</div>
			</c:if>

			<c:url var="profileAction" value="/user/profile" />

			<!-- Form -->
			<form action="${profileAction}" method="post"
				enctype="multipart/form-data" id="profileForm">

				<!-- Avatar Section -->
				<div class="avatar-section">
					<div class="avatar-wrapper">
						<c:choose>
							<c:when test="${not empty account.avatar}">
								<img
									src="${pageContext.request.contextPath}/assets/images${account.avatar}"
									alt="Avatar" class="avatar-img" id="avatarPreview">
							</c:when>
							<c:otherwise>
								<img
									src="${pageContext.request.contextPath}assets/images/avatar/default.jpg"
									alt="Default Avatar" class="avatar-img" id="avatarPreview">
							</c:otherwise>
						</c:choose>
						<label for="avatarFile" class="avatar-overlay"
							title="Thay đổi ảnh đại diện"> <i class="fas fa-camera"></i>
						</label>
					</div>
					<div class="user-id-badge">
						<i class="fas fa-id-card"></i> ID: ${account.userId}
					</div>
					<input type="file" id="avatarFile" name="avatarFile"
						accept="image/*" style="display: none;"
						onchange="previewAvatar(event)">
				</div>

				<!-- Personal Information Section -->
				<div class="form-section">
					<div class="section-title">
						<i class="fas fa-user"></i> Thông tin cá nhân
					</div>

					<div class="row">
						<div class="col-md-6 mb-3">
							<label class="form-label"> <i class="fas fa-user-tag"></i>
								Tên đăng nhập
							</label> <input type="text" class="form-control" name="username"
								value="${account.username}" required>
						</div>

						<div class="col-md-6 mb-3">
							<label class="form-label"> <i class="fas fa-signature"></i>
								Họ và tên
							</label> <input type="text" class="form-control" name="name"
								value="${account.name}" required>
						</div>

						<div class="col-md-6 mb-3">
							<label class="form-label"> <i class="fas fa-envelope"></i>
								Email
							</label> <input type="email" class="form-control" name="email"
								value="${account.email}" required>
						</div>

						<div class="col-md-6 mb-3">
							<label class="form-label"> <i class="fas fa-phone"></i>
								Số điện thoại
							</label> <input type="text" class="form-control" name="phone"
								value="${account.phone}" required>
						</div>

						<div class="col-md-9 mb-3">
							<label class="form-label"> <i
								class="fas fa-map-marker-alt"></i> Địa chỉ
							</label>
							<textarea class="form-control" name="address" rows="2">${account.address}</textarea>
						</div>

						<div class="col-md-3 mb-3">
							<label class="form-label"> <i class="fas fa-info-circle"></i>
								Trạng thái
							</label> <input type="text" class="form-control"
								value="${account.status}" readonly>
						</div>
					</div>
				</div>

				<!-- Password Section -->
				<div class="form-section password-section">
					<div class="section-title">
						<i class="fas fa-lock"></i> Đổi mật khẩu
					</div>

					<div class="row">
						<div class="col-md-4 mb-3">
							<label class="form-label"> <i class="fas fa-key"></i> Mật
								khẩu hiện tại
							</label> <input type="password" class="form-control" name="oldPassword"
								placeholder="Nhập mật khẩu hiện tại">
						</div>

						<div class="col-md-4 mb-3">
							<label class="form-label"> <i class="fas fa-lock"></i>
								Mật khẩu mới
							</label> <input type="password" class="form-control" name="newPassword"
								placeholder="Nhập mật khẩu mới">
						</div>

						<div class="col-md-4 mb-3">
							<label class="form-label"> <i class="fas fa-lock"></i>
								Xác nhận mật khẩu
							</label> <input type="password" class="form-control"
								name="confirmPassword" placeholder="Xác nhận mật khẩu mới">
						</div>
					</div>

					<div class="mt-2">
						<a href="${pageContext.request.contextPath}/forgot-password"
							class="forgot-password-link"> <i
							class="fas fa-question-circle"></i> Quên mật khẩu?
						</a>
					</div>
				</div>

				<!-- Action Buttons -->
				<div class="action-buttons">
					<button type="submit" class="btn btn-primary-custom">
						<i class="fas fa-save"></i> Cập nhật hồ sơ
					</button>
				</div>
			</form>
		</div>
	</div>
</div>

<script
	src="${pageContext.request.contextPath}/assets/js/user/profile.js"></script>
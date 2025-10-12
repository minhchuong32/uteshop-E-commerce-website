<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container-fluid">
	<h3 class="mb-4 fw-bold">Cài đặt tài khoản Shipper</h3>

	<form action="${pageContext.request.contextPath}/shipper/settings"
		method="post" enctype="multipart/form-data">

		<!-- Thông tin cá nhân -->
		<div class="card shadow-sm mb-4">
			<div class="card-header fw-bold bg-light">Thông tin cá nhân</div>
			<div class="card-body">
				<!-- Username -->
				<div class="mb-3">
					<label class="form-label">Tên đăng nhập</label> <input type="text"
						class="form-control" name="username"
						value="${sessionScope.account.username}" readonly>
				</div>

				<!-- Họ tên -->
				<div class="mb-3">
					<label class="form-label">Họ tên</label> <input type="text"
						class="form-control" name="name"
						value="${sessionScope.account.name}">
				</div>

				<!-- Số điện thoại -->
				<div class="mb-3">
					<label class="form-label">Số điện thoại</label> <input type="text"
						class="form-control" name="phone"
						value="${sessionScope.account.phone}">
				</div>

				<!-- Địa chỉ -->
				<div class="mb-3">
					<label class="form-label">Địa chỉ</label>
					<textarea class="form-control" name="address" rows="2">${sessionScope.account.address}</textarea>
				</div>

				<!-- Avatar -->
				<div class="mb-3 text-center">
					<label class="form-label d-block">Ảnh đại diện</label>
					<c:choose>
						<c:when test="${not empty sessionScope.account.avatar}">
							<img
								src="${pageContext.request.contextPath}/assets/images${sessionScope.account.avatar}"
								alt="avatar"
								class="rounded-circle img-thumbnail mx-auto d-block" width="100"
								height="100" style="object-fit: cover;">

						</c:when>
						<c:otherwise>
							<img
								src="${pageContext.request.contextPath}/uploads/default_avatar.png"
								alt="avatar"
								class="rounded-circle img-thumbnail mx-auto d-block" width="100"
								height="100" style="object-fit: cover;">
						</c:otherwise>
					</c:choose>
					<input type="file" class="form-control mt-3" name="avatarFile"
						accept="image/*">
				</div>
			</div>
		</div>

		<!-- Đổi mật khẩu -->
		<div class="card shadow-sm mb-4">
			<div class="card-header fw-bold bg-light">Đổi mật khẩu</div>
			<div class="card-body">
				<div class="mb-3">
					<label class="form-label">Mật khẩu hiện tại</label> <input
						type="password" name="oldPassword" class="form-control">
				</div>
				<div class="mb-3">
					<label class="form-label">Mật khẩu mới</label> <input
						type="password" name="newPassword" class="form-control">
				</div>
				<div class="mb-3">
					<label class="form-label">Xác nhận mật khẩu mới</label> <input
						type="password" name="confirmPassword" class="form-control">
				</div>
			</div>
		</div>

		<!-- Nút cập nhật -->
		<div class="text-end mb-4">
			<button type="submit" class="btn btn-primary-custom btn-lg px-4">
				Cập nhật</button>
		</div>

	</form>
</div>

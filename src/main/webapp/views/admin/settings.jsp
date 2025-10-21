<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">
	<h3 class="mb-4 text-primary-custom fw-bold">
		<i class="bi bi-gear-fill me-2"></i> Cài đặt hệ thống
	</h3>

	<form action="${pageContext.request.contextPath}/admin/settings"
		method="post" enctype="multipart/form-data">

		<!-- Thông tin cửa hàng -->
		<div class="card shadow-sm mb-4">
			<div class="card-header fw-bold bg-primary-custom">Thông tin cửa hàng</div>
			<div class="card-body">
				<div class="mb-3">
					<label class="form-label">Tên cửa hàng</label>
					<input type="text" class="form-control" name="store_name" value="${store.storeName}">
				</div>
				<div class="mb-3">
					<label class="form-label">Email liên hệ</label>
					<input type="email" class="form-control" name="store_email" value="${store.email}">
				</div>
				<div class="mb-3">
					<label class="form-label">Hotline</label>
					<input type="text" class="form-control" name="hotline" value="${store.hotline}">
				</div>
				<div class="mb-3">
					<label class="form-label">Địa chỉ</label>
					<textarea class="form-control" name="address" rows="2">${store.address}</textarea>
				</div>
			</div>
		</div>

		<!-- Cấu hình thanh toán -->
		<div class="card shadow-sm mb-4">
			<div class="card-header fw-bold bg-primary-custom">Cấu hình thanh toán</div>
			<div class="card-body">
				<div class="form-check form-switch mb-2">
					<input class="form-check-input" type="checkbox" name="cod_enabled"
						${store.codEnabled ? "checked" : ""}>
					<label class="form-check-label">Thanh toán khi nhận hàng (COD)</label>
				</div>
				<div class="form-check form-switch mb-2">
					<input class="form-check-input" type="checkbox" name="momo_enabled"
						${store.momoEnabled ? "checked" : ""}>
					<label class="form-check-label">Thanh toán qua MoMo</label>
				</div>
				<div class="form-check form-switch mb-2">
					<input class="form-check-input" type="checkbox" name="vnpay_enabled"
						${store.vnpayEnabled ? "checked" : ""}>
					<label class="form-check-label">Thanh toán qua VNPAY</label>
				</div>
			</div>
		</div>

		<!-- Cấu hình giao diện -->
		<div class="card shadow-sm mb-4">
			<div class="card-header fw-bold bg-primary-custom">Cấu hình giao diện</div>
			<div class="card-body">
				<div class="mb-3">
					<label class="form-label">Chọn theme</label>
					<select class="form-select" name="theme">
						<option value="default" ${store.theme eq 'default' ? 'selected' : ''}>Mặc định</option>
						<option value="dark" ${store.theme eq 'dark' ? 'selected' : ''}>Tối</option>
						<option value="light" ${store.theme eq 'light' ? 'selected' : ''}>Sáng</option>
					</select>
				</div>

				<!-- Logo preview -->
				<div class="mb-3 text-center">
					<label class="form-label d-block">Logo</label>
					<input type="file" class="form-control" name="logo" id="logoInput" accept="image/*">

					<c:choose>
						<c:when test="${not empty store.logo}">
							<img id="logoPreview"
								src="${pageContext.request.contextPath}/assets${store.logo}"
								alt="logo" class="mt-2 mx-auto d-block" style="height: 80px;">
						</c:when>
						<c:otherwise>
							<img id="logoPreview"
								src="${pageContext.request.contextPath}/assets/images/logo/uteshop-logo.png"
								alt="default logo" class="mt-2 mx-auto d-block" style="height: 80px;">
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>

		<!-- Thông tin cá nhân -->
		<h3 class="mb-4 fw-bold text-primary-custom">
			<i class="bi bi-person"></i> Thông tin cá nhân
		</h3>
		<div class="card shadow-sm mb-4">
			<div class="card-header fw-bold bg-primary-custom">Cập nhật thông tin Admin</div>
			<div class="card-body">
				<div class="mb-3">
					<label class="form-label">Username</label>
					<input type="text" class="form-control" name="username" value="${account.username}">
				</div>

				<!-- Avatar preview -->
				<div class="mb-3 text-center">
					<label class="form-label d-block">Ảnh đại diện</label>

					<c:choose>
						<c:when test="${not empty account.avatar}">
							<img id="avatarPreview"
								src="${pageContext.request.contextPath}/assets/images${account.avatar}"
								alt="avatar"
								class="rounded-circle img-thumbnail mx-auto d-block"
								width="120" height="120" style="object-fit: cover;">
						</c:when>
						<c:otherwise>
							<img id="avatarPreview"
								src="${pageContext.request.contextPath}/assets/images/avatars/default.jpg"
								alt="avatar"
								class="rounded-circle img-thumbnail mx-auto d-block"
								width="120" height="120" style="object-fit: cover;">
						</c:otherwise>
					</c:choose>

					<input type="file" class="form-control mt-3" name="avatarFile" id="avatarInput" accept="image/*">
				</div>

				<div class="mb-3">
					<label class="form-label">Email</label>
					<input type="email" class="form-control" name="email" value="${account.email}">
				</div>
				<div class="mb-3">
					<label class="form-label">Mật khẩu hiện tại</label>
					<input type="password" name="oldPassword" class="form-control">
				</div>
				<div class="mb-3">
					<label class="form-label">Mật khẩu mới</label>
					<input type="password" name="newPassword" class="form-control">
				</div>
				<div class="mb-3">
					<label class="form-label">Xác nhận mật khẩu mới</label>
					<input type="password" name="confirmPassword" class="form-control">
				</div>
			</div>
		</div>

		<div class="text-end mb-4">
			<button type="submit" class="btn btn-primary-custom btn-lg px-4">Cập nhật</button>
		</div>
	</form>
</div>

<!--  Script xem trước ảnh -->
<script>
document.getElementById("logoInput").addEventListener("change", function (e) {
	const file = e.target.files[0];
	if (file) {
		document.getElementById("logoPreview").src = URL.createObjectURL(file);
	}
});

document.getElementById("avatarInput").addEventListener("change", function (e) {
	const file = e.target.files[0];
	if (file) {
		document.getElementById("avatarPreview").src = URL.createObjectURL(file);
	}
});
</script>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container-fluid">
	<h3 class="mb-4 fw-bold">Cài đặt hệ thống</h3>

	<form action="${pageContext.request.contextPath}/admin/settings"
		method="post" enctype="multipart/form-data">

		<!-- Thông tin cửa hàng -->
		<div class="card shadow-sm mb-4">
			<div class="card-header fw-bold bg-light">Thông tin cửa hàng</div>
			<div class="card-body">
				<div class="mb-3">
					<label class="form-label">Tên cửa hàng</label> <input type="text"
						class="form-control" name="store_name" value="${store.storeName}">
				</div>
				<div class="mb-3">
					<label class="form-label">Email liên hệ</label> <input type="email"
						class="form-control" name="store_email" value="${store.email}">
				</div>
				<div class="mb-3">
					<label class="form-label">Hotline</label> <input type="text"
						class="form-control" name="hotline" value="${store.hotline}">
				</div>
				<div class="mb-3">
					<label class="form-label">Địa chỉ</label>
					<textarea class="form-control" name="address" rows="2">${store.address}</textarea>
				</div>
			</div>
		</div>

		<!-- Cấu hình thanh toán -->
		<div class="card shadow-sm mb-4">
			<div class="card-header fw-bold bg-light">Cấu hình thanh toán</div>
			<div class="card-body">
				<div class="form-check form-switch mb-2">
					<input class="form-check-input" type="checkbox" name="cod_enabled"
						${store.codEnabled ? "checked" : ""}> <label
						class="form-check-label">Thanh toán khi nhận hàng (COD)</label>
				</div>
				<div class="form-check form-switch mb-2">
					<input class="form-check-input" type="checkbox" name="momo_enabled"
						${store.momoEnabled ? "checked" : ""}> <label
						class="form-check-label">Thanh toán qua MoMo</label>
				</div>
				<div class="form-check form-switch mb-2">
					<input class="form-check-input" type="checkbox"
						name="vnpay_enabled" ${store.vnpayEnabled ? "checked" : ""}>
					<label class="form-check-label">Thanh toán qua VNPAY</label>
				</div>

			</div>
		</div>

		<!-- Cấu hình giao diện -->
		<div class="card shadow-sm mb-4">
			<div class="card-header fw-bold bg-light">Cấu hình giao diện</div>
			<div class="card-body">
				<div class="mb-3">
					<label class="form-label">Chọn theme</label> <select
						class="form-select" name="theme">
						<option value="default"
							${store.theme eq 'default' ? 'selected' : ''}>Mặc định</option>
						<option value="dark" ${store.theme eq 'dark' ? 'selected' : ''}>Tối</option>
						<option value="light" ${store.theme eq 'light' ? 'selected' : ''}>Sáng</option>
					</select>
				</div>
				<div class="mb-3 text-center">
    <label class="form-label d-block">Logo</label>
    <input type="file" class="form-control" name="logo">

    <c:choose>
        <c:when test="${not empty store.logo}">
            <img src="${pageContext.request.contextPath}/uploads/${store.logo}"
                 alt="logo"
                 class="mt-2 mx-auto d-block"
                 style="height: 80px;">
        </c:when>
        <c:otherwise>
            <img src="${pageContext.request.contextPath}/assets/images/default_logo.png"
                 alt="default logo"
                 class="mt-2 mx-auto d-block"
                 style="height: 80px;">
        </c:otherwise>
    </c:choose>
</div>

			</div>
		</div>

		<!-- Thông tin cá nhân -->
		<h3 class="mb-4 fw-bold">Thông tin cá nhân</h3>
		<div class="card shadow-sm mb-4">
			<div class="card-header fw-bold bg-light">Cập nhật thông tin
				Admin</div>
			<div class="card-body">
				<div class="mb-3">
					<label class="form-label">Username</label> <input type="text"
						class="form-control" name="username"
						value="${sessionScope.account.username}">
				</div>
				<div class="mb-3 text-center">
					<label class="form-label d-block">Ảnh đại diện</label>

					<!-- Hiển thị avatar -->
					<c:choose>
						<c:when test="${not empty sessionScope.account.avatar}">
							<img
								src="${pageContext.request.contextPath}/uploads/${sessionScope.account.avatar}"
								alt="avatar"
								class="rounded-circle img-thumbnail mx-auto d-block" width="120"
								height="120" style="object-fit: cover;">
						</c:when>
						<c:otherwise>
							<img
								src="${pageContext.request.contextPath}/uploads/default_avatar.png"
								alt="avatar"
								class="rounded-circle img-thumbnail mx-auto d-block" width="120"
								height="120" style="object-fit: cover;">
						</c:otherwise>
					</c:choose>

					<!-- Input upload file -->
					<input type="file" class="form-control mt-3" name="avatarFile"
						accept="image/*">
				</div>

				<div class="mb-3">
					<label class="form-label">Email</label> <input type="email"
						class="form-control" name="email"
						value="${sessionScope.account.email}">
				</div>
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

		<!-- Gộp nút cập nhật chung -->
		<div class="text-center mb-4">
			<button type="submit" class="btn btn-primary btn-lg">💾 Cập
				nhật tất cả</button>
		</div>
	</form>
</div>

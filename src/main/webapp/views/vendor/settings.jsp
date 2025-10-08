<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container-fluid">
	<h3 class="mb-4 fw-bold">⚙️ Cài đặt cửa hàng & tài khoản</h3>

	<form action="${pageContext.request.contextPath}/vendor/settings"
		method="post" enctype="multipart/form-data">

		<!-- Thông tin cửa hàng -->
		<div class="card shadow-sm mb-4">
		    <div class="card-header fw-bold bg-light">Thông tin cửa hàng</div>
		    <div class="card-body">
		        <!-- Logo -->
		        <div class="mb-3 text-center">
		            <label class="form-label d-block">Logo cửa hàng</label>
		            <c:choose>
		                <c:when test="${not empty shop.logo}">
		                    <img src="${pageContext.request.contextPath}/uploads/${shop.logo}" 
		                         alt="logo" class="img-thumbnail mx-auto d-block" width="150" height="150" style="object-fit: cover;">
		                </c:when>
		                <c:otherwise>
		                    <img src="${pageContext.request.contextPath}/uploads/default_logo.png" 
		                         alt="logo" class="img-thumbnail mx-auto d-block" width="150" height="150" style="object-fit: cover;">
		                </c:otherwise>
		            </c:choose>
		            <input type="file" class="form-control mt-3" name="logoFile" accept="image/*">
		        </div>
		
		        <!-- Tên cửa hàng -->
		        <div class="mb-3">
		            <label class="form-label">Tên cửa hàng</label>
		            <input type="text" class="form-control" name="store_name" value="${shop.name}">
		        </div>
		
		        <!-- Mô tả -->
		        <div class="mb-3">
		            <label class="form-label">Mô tả</label>
		            <textarea class="form-control" name="description" rows="3">${shop.description}</textarea>
		        </div>
		
		        <!-- Ngày tạo -->
		        <div class="mb-3">
		            <label class="form-label">Ngày tạo</label>
		            <input type="text" class="form-control" value="${shop.createdAt}" disabled>
		        </div>
		    </div>
		</div>


		<!-- Thông tin cá nhân vendor (theo bảng users) -->
		<div class="card shadow-sm mb-4">
			<div class="card-header fw-bold bg-light">Thông tin tài khoản</div>
			<div class="card-body">
				<input type="hidden" name="user_id"
					value="${sessionScope.account.userId}">
				<div class="mb-3">
					<label class="form-label">Username</label> <input type="text"
						class="form-control" name="username"
						value="${sessionScope.account.username}">
				</div>
				<div class="mb-3 text-center">
					<label class="form-label d-block">Ảnh đại diện</label>
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
		<!-- Nút cập nhật -->
		<div class="text-end mb-4">
			<button type="submit" class="btn btn-primary-custom btn-lg px-4">
				Cập nhật</button>
		</div>
	</form>
</div>

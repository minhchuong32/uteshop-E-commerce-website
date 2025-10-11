<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">
	<h3 class="mb-4 fw-bold">Thêm cửa hàng mới</h3>

	<div class="card shadow-sm border-0">
		<div class="card-body">
			<form action="${pageContext.request.contextPath}/admin/shops/add"
				method="post" enctype="multipart/form-data">

				<!-- Chủ sở hữu -->
				<div class="mb-3">
					<label for="user_id" class="form-label">Chủ sở hữu (User)</label>
					<select class="form-select" id="user_id" name="user_id" required>
						<option value="">-- Chọn chủ sở hữu cửa hàng --</option>
						<c:forEach var="v" items="${vendors}">
							<option value="${v.userId}">ID: ${v.userId} - ${empty v.name ? v.username : v.name}
							</option>
						</c:forEach>
					</select> <small class="text-muted">Chỉ hiển thị người dùng có vai
						trò Vendor</small>
				</div>


				<!-- Tên cửa hàng -->
				<div class="mb-3">
					<label for="name" class="form-label">Tên cửa hàng</label> <input
						type="text" class="form-control" id="name" name="name"
						placeholder="Nhập tên cửa hàng" required>
				</div>

				<!-- Mô tả -->
				<div class="mb-3">
					<label for="description" class="form-label">Mô tả cửa hàng</label>
					<textarea class="form-control" id="description" name="description"
						rows="4" placeholder="Nhập mô tả chi tiết..." required></textarea>
				</div>

				<!-- Logo -->
				<div class="mb-4">
					<label for="logo" class="form-label">Logo cửa hàng</label> <input
						type="file" class="form-control" id="logo" name="logo"
						accept="image/*">
					<div class="mt-2">
						<img id="preview"
							src="${pageContext.request.contextPath}/assets/images/shops/default-shop-logo.png"
							alt="Preview"
							style="width: 100px; height: 100px; object-fit: cover; border: 1px solid #ddd; border-radius: 6px;">
					</div>
				</div>

				<!-- Buttons -->
				<button type="submit" class="btn btn-success rounded-pill px-4">Thêm
					cửa hàng</button>
				<a href="${pageContext.request.contextPath}/admin/shops"
					class="btn btn-secondary rounded-pill px-4">Hủy</a>
			</form>
		</div>
	</div>
</div>

<!-- Preview ảnh -->
<script>
document.getElementById('logo').addEventListener('change', function(e) {
    const file = e.target.files[0];
    if (file) {
        document.getElementById('preview').src = URL.createObjectURL(file);
    }
});
</script>

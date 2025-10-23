<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">
    <h3 class="mb-4 fw-bold">Chỉnh sửa cửa hàng</h3>

    <div class="card shadow-sm border-0">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/admin/shops/edit"
                  method="post" enctype="multipart/form-data">

                <!-- Hidden Shop ID -->
                <input type="hidden" name="id" value="${shop.shopId}">

                <!-- Chủ sở hữu -->
                <div class="mb-3">
                    <label class="form-label">Chủ sở hữu</label>
                    <input type="text" class="form-control"
                           value="ID: ${shop.user.userId} - ${shop.user.username}" readonly>
                </div>

                <!-- Tên cửa hàng -->
                <div class="mb-3">
                    <label for="name" class="form-label">Tên cửa hàng</label>
                    <input type="text" class="form-control" id="name" name="name"
                           value="${shop.name}" required>
                </div>

                <!-- Mô tả -->
                <div class="mb-3">
                    <label for="description" class="form-label">Mô tả</label>
                    <textarea class="form-control" id="description" name="description"
                              rows="4" required>${shop.description}</textarea>
                </div>

                <!-- Logo -->
                <div class="mb-4">
                    <label for="logo" class="form-label">Logo cửa hàng</label>
                    <input type="file" class="form-control" id="logo" name="logo" accept="image/*">
                    <div class="mt-2">
                        <img id="preview"
                             src="${empty shop.logo 
        ? pageContext.request.contextPath.concat('/assets/images/shops/default-shop-logo.png')
        : pageContext.request.contextPath.concat('/assets/images/shops/').concat(shop.logo.substring(shop.logo.lastIndexOf('/') + 1))}"
                             alt="Logo hiện tại"
                             style="width:100px; height:100px; object-fit:cover;
                                    border:1px solid #ddd; border-radius:6px;">
                    </div>
                </div>

                <!-- Ngày tạo -->
                <div class="mb-3">
                    <label class="form-label">Ngày tạo</label>
                    <input type="text" class="form-control"
                           value="<fmt:formatDate value='${shop.createdAt}' pattern='dd/MM/yyyy'/>"
                           readonly>
                </div>

                <!-- Buttons -->
                <button type="submit" class="btn btn-warning rounded-pill px-4">Cập nhật</button>
                <a href="${pageContext.request.contextPath}/admin/shops"
                   class="btn btn-secondary rounded-pill px-4">Hủy</a>
            </form>
        </div>
    </div>
</div>

<!-- JS Preview ảnh -->
<script>
document.getElementById('logo').addEventListener('change', function(e) {
    const file = e.target.files[0];
    if (file) {
        document.getElementById('preview').src = URL.createObjectURL(file);
    }
});
</script>

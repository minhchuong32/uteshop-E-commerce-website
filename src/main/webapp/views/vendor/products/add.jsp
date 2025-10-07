<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglib.jsp" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Thêm sản phẩm mới | UteShop</title>
</head>
<body>
<div class="container py-4">
    <h3 class="fw-bold text-primary-custom mb-4">
        <i class="bi bi-plus-circle"></i> Thêm sản phẩm mới
    </h3>

    <form action="${pageContext.request.contextPath}/vendor/products/add"
      method="post" enctype="multipart/form-data">

        <!-- Tên sản phẩm -->
        <div class="col-md-6">
            <label class="form-label fw-bold">Tên sản phẩm</label>
            <input type="text" name="name" class="form-control" required>
        </div>

        <!-- Danh mục -->
        <div class="col-md-6">
            <label class="form-label fw-bold">Danh mục</label>
            <select name="categoryId" class="form-select" required>
                <option value="">-- Chọn danh mục --</option>
                <c:forEach var="c" items="${categories}">
                    <option value="${c.categoryId}">${c.name}</option>
                </c:forEach>
            </select>
        </div>

        <!-- Giá -->
        <div class="col-md-6">
            <label class="form-label fw-bold">Giá bán (₫)</label>
            <input type="number" name="price" class="form-control" required>
        </div>

        <!-- Tồn kho -->
        <div class="col-md-6">
            <label class="form-label fw-bold">Tồn kho</label>
            <input type="number" name="stock" class="form-control" required>
        </div>

        <!-- Hình ảnh -->
        <div class="col-md-6">
            <label class="form-label fw-bold">Ảnh sản phẩm</label>
            <input type="file" name="imageFile" class="form-control" accept="image/*" required>
        </div>

        <!-- Mô tả -->
        <div class="col-12">
            <label class="form-label fw-bold">Mô tả</label>
            <textarea name="description" class="form-control" rows="5" placeholder="Nhập mô tả sản phẩm..."></textarea>
        </div>

        <div class="col-12 text-end">
            <a href="${pageContext.request.contextPath}/vendor/products" class="btn btn-outline-secondary">
                <i class="bi bi-arrow-left"></i> Quay lại
            </a>
            <button type="submit" class="btn btn-primary-custom">
                <i class="bi bi-save"></i> Lưu sản phẩm
            </button>
        </div>
    </form>
</div>
</body>
</html>

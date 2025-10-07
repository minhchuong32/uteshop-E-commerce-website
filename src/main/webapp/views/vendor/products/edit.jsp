<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglib.jsp" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Chỉnh sửa sản phẩm | UteShop</title>
</head>
<body>
<div class="container py-4">
    <h3 class="fw-bold text-primary-custom mb-4">
        <i class="bi bi-pencil-square"></i> Chỉnh sửa sản phẩm
    </h3>

    <form action="${pageContext.request.contextPath}/vendor/products/edit" 
          method="post" enctype="multipart/form-data" class="row g-3">

        <input type="hidden" name="id" value="${product.productId}"/>

        <!-- Tên sản phẩm -->
        <div class="col-md-6">
            <label class="form-label fw-bold">Tên sản phẩm</label>
            <input type="file" name="imageFile" class="form-control" accept="image/*" required>
        </div>

        <!-- Danh mục -->
        <div class="col-md-6">
            <label class="form-label fw-bold">Danh mục</label>
            <select name="categoryId" class="form-select" required>
                <c:forEach var="c" items="${categories}">
                    <option value="${c.categoryId}" 
                        <c:if test="${product.category.categoryId == c.categoryId}">selected</c:if>>
                        ${c.name}
                    </option>
                </c:forEach>
            </select>
        </div>

        <!-- Giá -->
        <div class="col-md-6">
            <label class="form-label fw-bold">Giá bán (₫)</label>
            <input type="number" name="price" value="${product.price}" class="form-control" required>
        </div>

        <!-- Tồn kho -->
        <div class="col-md-6">
            <label class="form-label fw-bold">Tồn kho</label>
            <input type="number" name="stock" value="${product.stock}" class="form-control" required>
        </div>

        <!-- Ảnh hiện tại -->
        <div class="col-md-6">
            <label class="form-label fw-bold">Ảnh hiện tại</label><br>
            <img src="${pageContext.request.contextPath}/${product.imageUrl}" 
                 class="rounded border" width="120">
        </div>

        <!-- Ảnh mới -->
        <div class="col-md-6">
            <label class="form-label fw-bold">Chọn ảnh mới (nếu có)</label>
            <input type="file" name="imageUrl" class="form-control" accept="image/*">
        </div>

        <!-- Mô tả -->
        <div class="col-12">
            <label class="form-label fw-bold">Mô tả</label>
            <textarea name="description" class="form-control" rows="5">${product.description}</textarea>
        </div>

        <div class="col-12 text-end">
            <a href="${pageContext.request.contextPath}/vendor/products" class="btn btn-outline-secondary">
                <i class="bi bi-arrow-left"></i> Quay lại
            </a>
            <button type="submit" class="btn btn-primary-custom">
                <i class="bi bi-save"></i> Cập nhật
            </button>
        </div>
    </form>
</div>
</body>
</html>

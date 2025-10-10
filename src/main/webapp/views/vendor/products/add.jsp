<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglib.jsp" %>
<!DOCTYPE html>
<html lang="vi">
<div class="container mt-4">
    <h4 class="mb-4">🛒 Thêm sản phẩm mới</h4>

    <form action="${pageContext.request.contextPath}/vendor/products/add"
          method="post" enctype="multipart/form-data">

        <!-- Thông tin cơ bản -->
        <div class="card mb-4 shadow-sm">
            <div class="card-header bg-primary text-white fw-bold">Thông tin cơ bản</div>
            <div class="card-body">
                <div class="row g-3">
                    <div class="col-md-6">
                        <label class="form-label">Tên sản phẩm</label>
                        <input type="text" name="name" class="form-control" required>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Danh mục</label>
                        <select name="categoryId" class="form-select" required>
                            <option value="">-- Chọn danh mục --</option>
                            <c:forEach var="c" items="${categories}">
                                <option value="${c.categoryId}">${c.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="mt-3">
                    <label class="form-label">Mô tả</label>
                    <textarea name="description" class="form-control" rows="3"></textarea>
                </div>

                <div class="row mt-3">
                    <div class="col-md-6">
                        <label class="form-label">Giá mặc định</label>
                        <input type="number" step="0.01" name="price" class="form-control" required>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Ảnh đại diện</label>
                        <input type="file" name="imageFile" class="form-control" accept="image/*">
                    </div>
                </div>
            </div>
        </div>

        <!-- Ảnh phụ -->
        <div class="card mb-4 shadow-sm">
            <div class="card-header bg-success text-white fw-bold">Ảnh sản phẩm khác</div>
            <div class="card-body">
                <input type="file" name="extraImages" multiple accept="image/*" class="form-control">
                <small class="text-muted">Bạn có thể chọn nhiều ảnh (ảnh chi tiết sản phẩm).</small>
            </div>
        </div>

        <!-- Biến thể sản phẩm -->
        <div class="card shadow-sm mb-4">
            <div class="card-header bg-warning text-dark fw-bold">
                Biến thể sản phẩm
                <button type="button" class="btn btn-sm btn-outline-dark float-end" id="addVariantBtn">
                    + Thêm biến thể
                </button>
            </div>
            <div class="card-body" id="variantContainer">
                <p class="text-muted">Chưa có biến thể nào. Bấm “Thêm biến thể”.</p>
            </div>
        </div>

        <div class="text-end">
            <button type="submit" class="btn btn-primary">💾 Lưu sản phẩm</button>
        </div>
    </form>
</div>

<script>
    let variantIndex = 0;

    document.getElementById('addVariantBtn').addEventListener('click', function () {
        const container = document.getElementById('variantContainer');
        const div = document.createElement('div');
        div.classList.add('border', 'p-3', 'mb-3', 'rounded', 'bg-light');

        div.innerHTML = `
            <div class="row g-3 align-items-end">
                <div class="col-md-3">
                    <label class="form-label">Tên tùy chọn</label>
                    <input type="text" name="variantOptionName_${variantIndex}" class="form-control" placeholder="VD: Màu sắc" required>
                </div>

                <div class="col-md-3">
                    <label class="form-label">Giá trị tùy chọn</label>
                    <input type="text" name="variantOptionValue_${variantIndex}" class="form-control" placeholder="VD: Đỏ, Xanh" required>
                </div>

                <div class="col-md-2">
                    <label class="form-label">Giá</label>
                    <input type="number" step="0.01" name="variantPrice_${variantIndex}" class="form-control" required>
                </div>

                <div class="col-md-2">
                    <label class="form-label">Giá cũ</label>
                    <input type="number" step="0.01" name="variantOldPrice_${variantIndex}" class="form-control">
                </div>

                <div class="col-md-2">
                    <label class="form-label">Tồn kho</label>
                    <input type="number" name="variantStock_${variantIndex}" class="form-control" required>
                </div>

                <div class="col-md-3 mt-3">
                    <label class="form-label">Ảnh biến thể</label>
                    <input type="file" name="variantImage_${variantIndex}" class="form-control" accept="image/*">
                </div>

                <div class="col-md-1 mt-4 text-center">
                    <button type="button" class="btn btn-danger btn-sm remove-variant">X</button>
                </div>
            </div>
        `;

        container.appendChild(div);
        container.querySelector('p')?.remove();
        variantIndex++;

        // Xóa biến thể
        div.querySelector('.remove-variant').addEventListener('click', () => {
            div.remove();
            if (container.children.length === 0) {
                container.innerHTML = `<p class="text-muted">Chưa có biến thể nào. Bấm “Thêm biến thể”.</p>`;
            }
        });
    });
</script>
</html>

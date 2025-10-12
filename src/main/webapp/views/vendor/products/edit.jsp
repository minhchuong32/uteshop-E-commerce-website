<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglib.jsp" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Chỉnh sửa sản phẩm | UteShop</title>
</head>
<body>
<div class="container mt-4">
    <h4 class="mb-4">✏️ Chỉnh sửa sản phẩm</h4>

    <form action="${pageContext.request.contextPath}/vendor/products/edit"
          method="post" enctype="multipart/form-data">

        <input type="hidden" name="id" value="${product.productId}"/>

        <!-- Thông tin cơ bản -->
        <div class="card mb-4 shadow-sm">
            <div class="card-header bg-primary text-white fw-bold">Thông tin cơ bản</div>
            <div class="card-body">
                <div class="row g-3">
                    <div class="col-md-6">
                        <label class="form-label">Tên sản phẩm</label>
                        <input type="text" name="name" value="${product.name}" class="form-control" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Danh mục</label>
                        <select name="categoryId" class="form-select" required>
                            <c:forEach var="c" items="${categories}">
                                <option value="${c.categoryId}"
                                        <c:if test="${product.category.categoryId == c.categoryId}">selected</c:if>>
                                    ${c.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="mt-3">
                    <label class="form-label">Mô tả</label>
                    <textarea name="description" class="form-control" rows="3">${product.description}</textarea>
                </div>

                <div class="row mt-3 align-items-center">
                    <div class="col-md-6">
                        <label class="form-label">Giá bán</label>
                        <input type="number" step="0.01" name="price" value="${product.price}" class="form-control" required>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Ảnh đại diện hiện tại</label><br>
                        <img src="${pageContext.request.contextPath}/assets/${product.imageUrl}"
                             class="border rounded" width="120" alt="Ảnh sản phẩm">
                        <input type="file" name="imageFile" class="form-control mt-2" accept="image/*">
                        <small class="text-muted">Chọn ảnh mới nếu muốn thay đổi.</small>
                    </div>
                </div>
            </div>
        </div>

        <!-- Ảnh phụ -->
        <div class="card mb-4 shadow-sm">
            <div class="card-header bg-success text-white fw-bold">Ảnh sản phẩm khác</div>
            <div class="card-body">
                <div class="mb-3">
                    <c:forEach var="img" items="${product.images}">
                        <div class="d-inline-block me-2 mb-2 text-center">
                            <img src="${pageContext.request.contextPath}/assets/${img.imageUrl}" 
                                 class="border rounded" width="100" height="100">
                            <div>
                                <input type="checkbox" name="deleteExtraImageIds" value="${img.id}"> Xóa
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <label class="form-label">Thêm ảnh phụ mới</label>
                <input type="file" name="images" multiple accept="image/*" class="form-control">
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
			    <c:if test="${empty product.variants}">
			        <p class="text-muted">Chưa có biến thể nào. Bấm “Thêm biến thể”.</p>
			    </c:if>
			
			    <c:forEach var="v" items="${product.variants}" varStatus="loop">
			        <div class="border p-3 mb-3 rounded variant-block">
			            <input type="hidden" name="variantId_${loop.index}" value="${v.id}">
			            <div class="row g-3 align-items-end">
			                <div class="col-md-3">
			                    <label class="form-label">Tên tùy chọn</label>
			                    <input type="text" name="variantOptionName_${loop.index}" value="${v.optionName}" class="form-control" required>
			                </div>
			
			                <div class="col-md-3">
			                    <label class="form-label">Giá trị tùy chọn</label>
			                    <input type="text" name="variantOptionValue_${loop.index}" value="${v.optionValue}" class="form-control" required>
			                </div>
			
			                <div class="col-md-2">
			                    <label class="form-label">Giá</label>
			                    <input type="number" step="0.01" name="variantPrice_${loop.index}" value="${v.price}" class="form-control" required>
			                </div>
			
			                <div class="col-md-2">
			                    <label class="form-label">Giá cũ</label>
			                    <input type="number" step="0.01" name="variantOldPrice_${loop.index}" value="${v.oldPrice}" class="form-control">
			                </div>
			
			                <div class="col-md-2">
			                    <label class="form-label">Tồn kho</label>
			                    <input type="number" name="variantStock_${loop.index}" value="${v.stock}" class="form-control" required>
			                </div>
			
			                <div class="col-md-3 mt-3">
			                    <label class="form-label">Ảnh hiện tại</label><br>
			                    <c:if test="${not empty v.imageUrl}">
			                        <img src="${pageContext.request.contextPath}/assets/${v.imageUrl}" class="border rounded mb-2" width="100">
			                    </c:if>
			                    <input type="file" name="variantImage_${loop.index}" class="form-control" accept="image/*">
			                    <small class="text-muted">Chọn ảnh mới nếu muốn thay đổi.</small>
			                </div>
			
			                <div class="col-md-1 mt-4 text-center">
			                    <input type="checkbox" name="deleteVariantIds" value="${v.id}"> Xóa
			                </div>
			            </div>
			        </div>
			    </c:forEach>
			</div>
        </div>

        <div class="text-end">
            <a href="${pageContext.request.contextPath}/vendor/products" class="btn btn-outline-secondary">
                ⬅ Quay lại
            </a>
            <button type="submit" class="btn btn-primary">
                💾 Cập nhật sản phẩm
            </button>
        </div>
    </form>
</div>

<script>
    let variantIndex = ${product.variants != null ? product.variants.size() : 0};

    document.getElementById('addVariantBtn').addEventListener('click', function () {
        const container = document.getElementById('variantContainer');
        const div = document.createElement('div');
        div.classList.add('border', 'p-3', 'mb-3', 'rounded');
        div.innerHTML = `
            <div class="row g-3 align-items-end">
                <div class="col-md-3">
                    <label class="form-label">Tên biến thể</label>
                    <input type="text" name="variantName_${variantIndex}" class="form-control" required>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Giá</label>
                    <input type="number" step="0.01" name="variantPrice_${variantIndex}" class="form-control" required>
                </div>
                <div class="col-md-2">
                    <label class="form-label">Tồn kho</label>
                    <input type="number" name="variantStock_${variantIndex}" class="form-control" required>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Ảnh biến thể</label>
                    <input type="file" name="variantImage_${variantIndex}" class="form-control" accept="image/*">
                </div>
                <div class="col-md-1 text-center">
                    <button type="button" class="btn btn-danger btn-sm remove-variant">X</button>
                </div>
            </div>
        `;
        container.appendChild(div);
        container.querySelector('p')?.remove();
        variantIndex++;

        div.querySelector('.remove-variant').addEventListener('click', () => div.remove());
    });
</script>
</body>
</html>

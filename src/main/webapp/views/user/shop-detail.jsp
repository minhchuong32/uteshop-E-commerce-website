<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglib.jsp" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>${shop.name} | UteShop</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
<div class="container py-4">

    <!--  Thông tin shop -->
    <div class="card mb-4 shadow-sm">
        <div class="card-body d-flex align-items-center">
            <img src="${pageContext.request.contextPath}/assets${shop.logo}"
                 alt="Shop logo"
                 class="rounded me-3 border"
                 style="width:80px; height:80px; object-fit:cover;">
            <div>
                <h4 class="fw-bold mb-1">${shop.name}</h4>
                <p class="text-muted mb-1">${shop.description}</p>
                <small class="text-muted">
                    <i class="bi bi-calendar-event"></i> Ngày tạo: 
                    <fmt:formatDate value="${shop.createdAt}" pattern="dd/MM/yyyy"/>
                </small>
            </div>
        </div>
    </div>

    <!-- 🧭 Bộ lọc -->
    <form action="${pageContext.request.contextPath}/user/shop/detail" method="get" class="row g-3 align-items-end mb-4">
        <input type="hidden" name="id" value="${shop.shopId}" />
        <input type="hidden" name="page" value="${currentPage}" />

        <div class="col-md-3">
            <label class="form-label fw-bold">Danh mục</label>
            <select class="form-select" name="categoryId">
                <option value="">Tất cả</option>
                <c:forEach var="c" items="${categories}">
                    <option value="${c.categoryId}" 
                        <c:if test="${c.categoryId == selectedCategoryId}">selected</c:if>>
                        ${c.name}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="col-md-3">
            <label class="form-label fw-bold">Khoảng giá</label>
            <div class="input-group">
                <input type="number" class="form-control" placeholder="Từ" name="minPrice"
                       value="${minPrice != null ? minPrice : ''}" />
                <span class="input-group-text">-</span>
                <input type="number" class="form-control" placeholder="Đến" name="maxPrice"
                       value="${maxPrice != null ? maxPrice : ''}" />
            </div>
        </div>

        <div class="col-md-3">
            <label class="form-label fw-bold">Sắp xếp</label>
            <select class="form-select" name="sortBy">
                <option value="newest" <c:if test="${sortBy == 'newest'}">selected</c:if>>Mới nhất</option>
                <option value="priceAsc" <c:if test="${sortBy == 'priceAsc'}">selected</c:if>>Giá: Thấp → Cao</option>
                <option value="priceDesc" <c:if test="${sortBy == 'priceDesc'}">selected</c:if>>Giá: Cao → Thấp</option>
            </select>
        </div>

        <div class="col-md-3 d-flex gap-2">
            <button type="submit" class="btn btn-primary-custom w-50">
                <i class="bi bi-filter"></i> Lọc
            </button>
            <a href="${pageContext.request.contextPath}/user/shop/detail?id=${shop.shopId}" class="btn btn-outline-secondary w-50">Xóa lọc</a>
        </div>
    </form>

    <!-- 🔄 Nút chuyển đổi layout -->
    <div class="d-flex justify-content-end mb-3">
        <div class="btn-group" role="group">
            <button type="button" class="btn btn-primary-custom active" id="btnGrid">
                <i class="bi bi-grid"></i>
            </button>
            <button type="button" class="btn btn-outline-secondary" id="btnList">
                <i class="bi bi-list"></i>
            </button>
        </div>
    </div>

    <!-- 🧱 Grid View -->
    <div class="row g-3" id="gridView">
        <c:choose>
            <c:when test="${not empty products}">
                <c:forEach var="p" items="${products}">
                    <div class="col-6 col-md-4 col-lg-2">
                        <div class="card product-card h-100 shadow-sm">
                            <a href="${pageContext.request.contextPath}/user/product/detail?id=${p.productId}"
                               class="text-decoration-none text-dark d-block h-100">
                                <div class="product-img-wrapper">
                                    <img src="${pageContext.request.contextPath}/assets/${p.imageUrl}"
                                         class="card-img-top" alt="${p.name}"
                                         style="height:180px; object-fit:cover;">
                                </div>

                                <div class="card-body d-flex flex-column justify-content-between p-2">
                                    <h6 class="card-title mb-1 text-truncate">${p.name}</h6>

                                    <!-- ⭐ Đánh giá -->
                                    <div class="mb-1">
                                        <span class="text-warning">
                                            <c:forEach var="i" begin="1" end="5">
                                                <i class="bi ${i <= p.averageRating ? 'bi-star-fill' : 'bi-star'}"></i>
                                            </c:forEach>
                                        </span>
                                        <small class="text-muted">(${p.reviewsCount} đánh giá)</small>
                                    </div>

                                    <!-- 💰 Giá -->
                                    <c:forEach var="v" items="${p.variants}" varStatus="status">
                                        <c:if test="${status.first}">
                                            <c:choose>
                                                <c:when test="${v.oldPrice != null && v.oldPrice > v.price}">
                                                    <p class="text-muted mb-0" style="text-decoration: line-through;">
                                                        <fmt:formatNumber value="${v.oldPrice}" type="currency" currencySymbol="₫" />
                                                    </p>
                                                    <p class="text-danger fw-bold mb-2">
                                                        <fmt:formatNumber value="${v.price}" type="currency" currencySymbol="₫" />
                                                    </p>
                                                </c:when>
                                                <c:otherwise>
                                                    <p class="text-danger fw-bold mb-2">
                                                        <fmt:formatNumber value="${v.price}" type="currency" currencySymbol="₫" />
                                                    </p>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </a>

                            <div class="p-2">
                                <a href="${pageContext.request.contextPath}/user/product/detail?id=${p.productId}"
                                   class="btn btn-sm btn-primary-custom w-100">
                                    <i class="bi bi-eye"></i> Xem chi tiết
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="text-center py-5">
                    <h6 class="text-muted">Shop chưa có sản phẩm nào.</h6>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- 📋 List View -->
    <div class="list-group d-none" id="listView">
        <c:choose>
            <c:when test="${not empty products}">
                <c:forEach var="p" items="${products}">
                    <a href="${pageContext.request.contextPath}/user/product/detail?id=${p.productId}"
                       class="list-group-item list-group-item-action d-flex align-items-center justify-content-between border text-decoration-none text-dark"
                       style="transition: all 0.2s;"
                       onmouseover="this.style.borderColor='var(--bs-primary)'"
                       onmouseout="this.style.borderColor='#dee2e6'">

                        <div class="d-flex align-items-center">
                            <img src="${pageContext.request.contextPath}/assets${p.imageUrl}"
                                 alt="${p.name}" class="img-thumbnail me-3"
                                 style="width:80px; height:80px; object-fit:cover;">
                            <div>
                                <h6 class="mb-1">${p.name}</h6>
                                <small class="text-muted">${p.reviewsCount} đánh giá</small>
                            </div>
                        </div>

                        <div class="text-end">
                            <c:forEach var="v" items="${p.variants}" varStatus="status">
                                <c:if test="${status.first}">
                                    <c:choose>
                                        <c:when test="${v.oldPrice != null && v.oldPrice > v.price}">
                                            <p class="text-muted mb-0" style="text-decoration: line-through;">
                                                <fmt:formatNumber value="${v.oldPrice}" type="currency" currencySymbol="₫" />
                                            </p>
                                            <p class="text-danger fw-bold mb-0">
                                                <fmt:formatNumber value="${v.price}" type="currency" currencySymbol="₫" />
                                            </p>
                                        </c:when>
                                        <c:otherwise>
                                            <p class="text-danger fw-bold mb-0">
                                                <fmt:formatNumber value="${v.price}" type="currency" currencySymbol="₫" />
                                            </p>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </c:forEach>
                            <a href="${pageContext.request.contextPath}/user/product/detail?id=${p.productId}"
                               class="btn btn-sm btn-primary-custom w-100 mt-2">
                                <i class="bi bi-eye"></i> Xem chi tiết
                            </a>
                        </div>
                    </a>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="text-center py-5">
                    <h6 class="text-muted">Shop chưa có sản phẩm nào.</h6>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- 📄 Phân trang -->
    <c:if test="${totalPages > 1}">
        <nav aria-label="Page navigation" class="mt-4">
            <ul class="pagination justify-content-center">
                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                    <a class="page-link nav-link"
                       href="${pageContext.request.contextPath}/user/shop/detail?id=${shop.shopId}&page=${currentPage - 1}">Trước</a>
                </li>
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                        <a class="page-link nav-link ${i == currentPage ? 'active' : ''}"
                           href="${pageContext.request.contextPath}/user/shop/detail?id=${shop.shopId}&page=${i}">${i}</a>
                    </li>
                </c:forEach>
                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                    <a class="page-link nav-link"
                       href="${pageContext.request.contextPath}/user/shop/detail?id=${shop.shopId}&page=${currentPage + 1}">Sau</a>
                </li>
            </ul>
        </nav>
    </c:if>

</div>

<!-- JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    const btnGrid = document.getElementById("btnGrid");
    const btnList = document.getElementById("btnList");
    const gridView = document.getElementById("gridView");
    const listView = document.getElementById("listView");

    btnGrid.addEventListener("click", () => {
        btnGrid.classList.add("btn-primary-custom");
        btnGrid.classList.remove("btn-outline-secondary");
        btnList.classList.remove("btn-primary-custom");
        btnList.classList.add("btn-outline-secondary");
        gridView.classList.remove("d-none");
        listView.classList.add("d-none");
    });

    btnList.addEventListener("click", () => {
        btnList.classList.add("btn-primary-custom");
        btnList.classList.remove("btn-outline-secondary");
        btnGrid.classList.remove("btn-primary-custom");
        btnGrid.classList.add("btn-outline-secondary");
        gridView.classList.add("d-none");
        listView.classList.remove("d-none");
    });
</script>

</body>
</html>

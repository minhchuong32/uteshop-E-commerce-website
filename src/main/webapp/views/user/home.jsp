<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>
<div id="bannerDiv" class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-inner">
        <div class="carousel-item active">
            <img src="${pageContext.request.contextPath}/assets/images/banner3.png"
                 class="d-block w-100 banner-img" alt="banner3">
        </div>
        <div class="carousel-item">
            <img src="${pageContext.request.contextPath}/assets/images/banner2.png"
                 class="d-block w-100 banner-img" alt="banner2">
        </div>
        <div class="carousel-item">
            <img src="${pageContext.request.contextPath}/assets/images/banner1.png"
                 class="d-block w-100 banner-img" alt="banner1">
        </div>
    </div>
    <!-- Nút điều hướng -->
    <button class="carousel-control-prev" type="button" data-bs-target="#bannerDiv" data-bs-slide="prev">
        <span class="carousel-control-prev-icon"></span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#bannerDiv" data-bs-slide="next">
        <span class="carousel-control-next-icon"></span>
    </button>
</div>

<!-- Danh mục -->
<div class="container my-4">
    <h2 class="mb-4 px-3 py-2 border rounded bg-light text-primary" style="font-size: 20px;">DANH MỤC</h2>

    <div class="row text-center g-3">
        <c:forEach var="cat" items="${categories}">
            <div class="col-6 col-md-3 col-lg-2 container-item">
                <img src="${pageContext.request.contextPath}/assets/images/${cat.image}"
                     alt="${cat.name}" class="img-fluid rounded-circle mb-2 shadow-sm" />
                <p class="fw-semibold">${cat.name}</p>
            </div>
        </c:forEach>
    </div>
</div>

<!-- Sản phẩm gợi ý -->
<div class="container my-4">
    <h2 class="mb-4 px-3 py-2 border rounded bg-light text-primary" style="font-size: 20px;">SẢN PHẨM GỢI Ý</h2>

    <!-- Bộ lọc / Sort -->
    <div class="d-flex flex-wrap align-items-center mb-4 gap-2">
        <select class="form-select w-auto" id="priceSort">
            <option value="">-- Sắp xếp theo giá --</option>
            <option value="asc">Giá: Thấp → Cao</option>
            <option value="desc">Giá: Cao → Thấp</option>
        </select>
        <button class="btn btn-outline-primary btn-sm" id="newestBtn">Mới nhất</button>
        <button class="btn btn-outline-primary btn-sm" id="popularBtn">Phổ biến</button>
    </div>

    <div class="row g-3">
        <c:forEach var="p" items="${products}">
            <div class="col-12 col-sm-6 col-lg-4">
                <div class="card border shadow-sm h-100 product-card">
                    <a href="${pageContext.request.contextPath}/product/${p.id}">
                        <img src="${p.image}" class="card-img-top img-fluid product-img" alt="${p.name}">
                    </a>
                    <div class="card-body d-flex flex-column justify-content-between">
                        <h5 class="card-title mb-1 fs-6">${p.name}</h5>

                        <!-- Rating -->
                        <div class="mb-1">
                            <span class="text-warning">★★★★☆</span>
                            <small class="text-muted">(${p.reviews} reviews)</small>
                        </div>

                        <!-- Giá -->
                        <div class="d-flex align-items-center gap-2">
                            <p class="text-danger fw-bold mb-0">${p.price}₫</p>
                            <c:if test="${p.oldPrice != null}">
                                <p class="text-decoration-line-through text-muted mb-0 small">${p.oldPrice}₫</p>
                            </c:if>
                        </div>

                        <!-- Button -->
                        <button class="btn btn-sm w-100 btn-primary">Thêm vào giỏ hàng</button>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<!-- Phân trang -->
<nav aria-label="Page navigation" class="mt-4 d-flex justify-content-center">
    <ul class="pagination">
        <li class="page-item"><a class="page-link" href="#">Trước</a></li>
        <li class="page-item active"><a class="page-link" href="#">1</a></li>
        <li class="page-item"><a class="page-link" href="#">2</a></li>
        <li class="page-item"><a class="page-link" href="#">3</a></li>
        <li class="page-item"><a class="page-link" href="#">Sau</a></li>
    </ul>
</nav>

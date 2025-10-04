<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglib.jsp" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>${shop.name} | UteShop</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container py-4">

    <!-- Thông tin shop -->
    <div class="card mb-4">
        <div class="card-body d-flex align-items-center">
            <img src="${pageContext.request.contextPath}/assets/images/shop-logo.png"
                 alt="Shop logo"
                 class="rounded me-3"
                 style="width:80px; height:80px; object-fit:cover;">
            <div>
                <h4 class="fw-bold mb-1">${shop.name}</h4>
                <p class="text-muted mb-1">${shop.description}</p>
                <small class="text-muted">
                    <i class="bi bi-calendar"></i> Ngày tạo: 
                    <fmt:formatDate value="${shop.createdAt}" pattern="dd/MM/yyyy"/>
                </small>
            </div>
        </div>
    </div>

    <!-- Danh sách sản phẩm -->
    <h5 class="fw-bold mb-3">Sản phẩm của shop</h5>
    <div class="row g-3">
        <c:forEach var="p" items="${products}">
            <div class="col-md-3 col-6">
                <div class="card h-100">
                    <img src="${pageContext.request.contextPath}/assets/images/products/${p.imageUrl}"
                         class="card-img-top"
                         alt="${p.name}"
                         style="height:200px; object-fit:cover;">
                    <div class="card-body p-2">
                        <h6 class="card-title text-truncate">${p.name}</h6>
                        <p class="text-danger fw-bold mb-1">
                            <fmt:formatNumber value="${p.price}" type="currency" currencySymbol="₫"/>
                        </p>
                        <a href="${pageContext.request.contextPath}/web/product/detail?id=${p.productId}"
                           class="btn btn-sm btn-primary-custom w-100">Xem chi tiết</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <c:if test="${empty products}">
        <p class="text-muted">Shop chưa có sản phẩm nào.</p>
    </c:if>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

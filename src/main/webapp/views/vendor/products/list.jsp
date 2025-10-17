<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!-- CSS riêng -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/product-vendor.css">
	
<div class="container mt-4">
    <!-- DANH MỤC -->
    <div class="bg-white shadow-sm p-3 mb-4 rounded">
        <h5 class="fw-bold text-uppercase text-primary-custom mb-3">
            <i class="bi bi-grid me-2"></i> Danh mục sản phẩm
        </h5>

       <!-- Thanh danh mục -->
<div class="category-container">
    <div class="d-flex flex-row flex-nowrap overflow-auto gap-3 pb-3 px-2">
        <!-- Tất cả danh mục -->
        <div class="text-center flex-shrink-0">
            <a href="${pageContext.request.contextPath}/vendor/products"
               class="category-item d-block text-decoration-none ${empty selectedCategory ? 'active' : ''}">
                <div class="category-card">
                    <div class="category-icon-wrapper all-category">
                        <i class="bi bi-grid-3x3-gap fs-3"></i>
                    </div>
                    <div class="category-name mt-2">Tất cả</div>
                </div>
            </a>
        </div>

        <!-- Danh mục động -->
        <c:forEach var="c" items="${categories}">
            <div class="text-center flex-shrink-0">
                <a href="${pageContext.request.contextPath}/vendor/products?category=${c.categoryId}"
                   class="category-item d-block text-decoration-none ${selectedCategory == c.categoryId ? 'active' : ''}">
                    <div class="category-card">
                        <div class="category-icon-wrapper">
                            <img src="${pageContext.request.contextPath}/assets/${c.image}"
                                 class="category-image"
                                 alt="${c.name}">
                        </div>
                        <div class="category-name mt-2">${c.name}</div>
                    </div>
                </a>
            </div>
        </c:forEach>
    </div>
</div>


    <!-- SẢN PHẨM -->
    <div class="bg-white rounded shadow-sm p-3">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h5 class="fw-bold text-uppercase text-primary-custom">
                <i class="bi bi-box-seam me-2"></i> Sản phẩm trong cửa hàng
            </h5>
            <a href="${pageContext.request.contextPath}/vendor/products/add" class="btn btn-primary-custom btn-sm">
                <i class="bi bi-plus-circle"></i> Thêm sản phẩm
            </a>
        </div>

        <!-- Grid View -->
        <div class="row g-3">
            <c:choose>
                <c:when test="${empty list}">
                    <div class="text-center py-5">
                        <h6 class="text-muted">Chưa có sản phẩm nào</h6>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="p" items="${list}">
                        <div class="col-12 col-sm-6 col-md-4">
                            <div class="card h-100 shadow-sm product-card">
                                <div class="product-img-wrapper position-relative">
                                    <img src="${pageContext.request.contextPath}/assets/${p.imageUrl}" 
                                         class="card-img-top" alt="${p.name}"
                                         style="height:180px;object-fit:cover;">
                                </div>
                                <div class="card-body d-flex flex-column justify-content-between">
                                    <h6 class="card-title mb-1 text-truncate">${p.name}</h6>
                                    <div class="text-muted small mb-1">${p.category.name}</div>
                                    <p class="fw-bold text-danger mb-2">
                                        <fmt:formatNumber value="${p.price}" type="currency" currencySymbol="₫"/>
                                    </p>
                                    <div class="d-flex justify-content-between align-items-center">
									    <div class="btn-group">
									        <a href="${pageContext.request.contextPath}/vendor/products/edit?id=${p.productId}" 
									           class="btn btn-sm btn-warning">
									           <i class="bi bi-pencil"></i> Sửa
									        </a>
									        <a href="${pageContext.request.contextPath}/vendor/products/detail?id=${p.productId}"
									           class="btn btn-sm btn-info text-white">
									           <i class="bi bi-info-circle"></i> Xem
									        </a>
									    </div>
									
									    <a href="${pageContext.request.contextPath}/vendor/products/delete?id=${p.productId}" 
									       class="btn btn-sm btn-danger"
									       onclick="return confirm('Bạn có chắc muốn xóa sản phẩm này?');">
									       <i class="bi bi-trash"></i> Xóa
									    </a>
									</div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- PHÂN TRANG -->
		<c:set var="categoryParam" value="${not empty selectedCategory ? '&category=' += selectedCategory : ''}" />

		<c:if test="${totalPages > 1}">
		    <nav aria-label="Page navigation">
		        <ul class="pagination justify-content-center mt-4">
		
		            <!-- Trang trước -->
		            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
		                <a class="page-link" href="${pageContext.request.contextPath}/vendor/products?page=${currentPage - 1}${categoryParam}" aria-label="Previous">
		                    <span aria-hidden="true">&laquo; Trước</span>
		                </a>
		            </li>
		
		            <!-- Các số trang -->
		            <c:forEach var="i" begin="1" end="${totalPages}">
		                <li class="page-item ${i == currentPage ? 'active' : ''}">
		                    <a class="page-link" href="${pageContext.request.contextPath}/vendor/products?page=${i}${categoryParam}">${i}</a>
		                </li>
		            </c:forEach>
		
		            <!-- Trang sau -->
		            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
		                <a class="page-link" href="${pageContext.request.contextPath}/vendor/products?page=${currentPage + 1}${categoryParam}" aria-label="Next">
		                    <span aria-hidden="true">Sau &raquo;</span>
		                </a>
		            </li>
		
		        </ul>
		    </nav>
		</c:if>
    </div>
</div>

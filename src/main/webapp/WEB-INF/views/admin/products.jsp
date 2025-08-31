<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div class="container-fluid">
    <h3 class="mb-4 fw-bold">Quản lý sản phẩm</h3>

    <div class="card shadow-sm">
        <div class="card-body">
            <table class="table align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th>Sản phẩm</th>
                        <th>Danh mục</th>
                        <th>Giá</th>
                        <th>Tồn kho</th>
                        <th class="text-center">Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="p" items="${products}">
                        <tr>
                            <!-- Thông tin sản phẩm -->
                            <td>
                                <div class="d-flex align-items-center">
                                    <img src="images/${p.image_url}" 
                                         alt="${p.name}" 
                                         class="rounded me-3" 
                                         style="width: 60px; height: 60px; object-fit: cover;">
                                    <div>
                                        <div class="fw-bold">${p.name}</div>
                                        <small class="text-muted">${p.description}</small>
                                    </div>
                                </div>
                            </td>

                            <!-- Danh mục -->
                            <td>
                                <span class="badge rounded-pill bg-info-subtle text-info">
                                    ${p.category_id}
                                </span>
                            </td>

                            <!-- Giá -->
                            <td>
                                <fmt:formatNumber value="${p.price}" type="currency" currencySymbol="₫" />
                            </td>

                            <!-- Tồn kho -->
                            <td>
                                <c:choose>
                                    <c:when test="${p.stock > 10}">
                                        <span class="badge rounded-pill bg-success-subtle text-success">${p.stock}</span>
                                    </c:when>
                                    <c:when test="${p.stock > 0}">
                                        <span class="badge rounded-pill bg-warning-subtle text-warning">${p.stock}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge rounded-pill bg-danger-subtle text-danger">Hết hàng</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <!-- Hành động -->
                            <td class="text-center">
                                <!-- Xem chi tiết -->
                                <a href="product?action=view&id=${p.product_id}" title="Xem chi tiết">
                                    <i class="bi bi-eye text-primary me-3"></i>
                                </a>
                                <!-- Sửa -->
                                <a href="product?action=edit&id=${p.product_id}" title="Sửa">
                                    <i class="bi bi-pencil-square text-warning me-3"></i>
                                </a>
                                <!-- Xóa -->
                                <a href="product?action=delete&id=${p.product_id}" title="Xóa"
                                   onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này?');">
                                    <i class="bi bi-trash text-danger"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

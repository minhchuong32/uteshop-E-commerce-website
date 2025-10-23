<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container-fluid">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>Chương trình khuyến mãi</h3>
        <a href="${pageContext.request.contextPath}/vendor/promotions/add" class="btn btn-primary">Tạo khuyến mãi mới</a>
    </div>

    <c:if test="${not empty promotions}">
        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Sản phẩm</th>
                        <th>Loại</th>
                        <th>Giá trị</th>
                        <th>Thời gian</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="p" items="${promotions}" varStatus="st">
                        <tr>
                            <td>${st.index + 1}</td>
                            <td>
                                <c:choose>
	                                <c:when test="${not empty p.product}">
                                        <div class="d-flex align-items-center">
                                            <!-- Hiển thị ảnh sản phẩm -->
                                            <c:if test="${not empty p.product.imageUrl}">
                                                <img src="${pageContext.request.contextPath}/assets/${p.product.imageUrl}"
                                                     alt="${p.product.name}"
                                                     style="width:60px; height:60px; object-fit:cover; border-radius:8px; margin-right:10px;">
                                            </c:if>
                                            <div>
                                                <strong>${p.product.name}</strong>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        Toàn shop
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${p.discountType}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${p.discountType == 'PERCENT'}">
                                        ${p.value}% 
                                    </c:when>
                                    <c:otherwise>
                                        ${p.value}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${p.startDate} → ${p.endDate}</td>
                         
                            <td>
                                <a href="${pageContext.request.contextPath}/vendor/promotions/detail?id=${p.promotionId}" class="btn btn-sm btn-info">Xem</a>
                                <a href="${pageContext.request.contextPath}/vendor/promotions/edit?id=${p.promotionId}" class="btn btn-sm btn-warning">Sửa</a>
                                <a href="${pageContext.request.contextPath}/vendor/promotions/delete?id=${p.promotionId}"
                                   onclick="return confirm('Xóa khuyến mãi này?');" class="btn btn-sm btn-danger">Xóa</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>

    <c:if test="${empty promotions}">
        <div class="alert alert-info">Chưa có chương trình khuyến mãi nào.</div>
    </c:if>
</div>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container">
    <div class="card">
        <div class="card-header">
            <h5>Chi tiết khuyến mãi</h5>
        </div>
        <div class="card-body">
            <c:if test="${not empty promotion}">
                <dl class="row">
                    <dt class="col-sm-3">ID</dt>
                    <dd class="col-sm-9">${promotion.promotionId}</dd>

                    <dt class="col-sm-3">Áp dụng</dt>
                    <dd class="col-sm-9">
                        <c:choose>
                            <c:when test="${not empty promotion.product}">${promotion.product.name}</c:when>
                            <c:otherwise>Toàn shop</c:otherwise>
                        </c:choose>
                    </dd>

                    <dt class="col-sm-3">Loại</dt>
                    <dd class="col-sm-9">${promotion.discountType}</dd>

                    <dt class="col-sm-3">Giá trị</dt>
                    <dd class="col-sm-9">${promotion.value}</dd>

                    <dt class="col-sm-3">Từ ngày</dt>
                    <dd class="col-sm-9">${promotion.startDate}</dd>

                    <dt class="col-sm-3">Đến ngày</dt>
                    <dd class="col-sm-9">${promotion.endDate}</dd>
                </dl>

                <a href="${pageContext.request.contextPath}/vendor/promotions/edit?id=${promotion.promotionId}" class="btn btn-warning">Sửa</a>
                <a href="${pageContext.request.contextPath}/vendor/promotions" class="btn btn-secondary">Quay lại</a>
            </c:if>

            <c:if test="${empty promotion}">
                <div class="alert alert-info">Không tìm thấy khuyến mãi.</div>
            </c:if>
        </div>
    </div>
</div>

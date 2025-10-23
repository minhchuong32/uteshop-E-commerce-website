<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container">
    <div class="card">
        <div class="card-header">
            <h5 class="mb-0">
                <c:choose>
                    <c:when test="${not empty promotion}">Chỉnh sửa khuyến mãi</c:when>
                    <c:otherwise>Tạo khuyến mãi mới</c:otherwise>
                </c:choose>
            </h5>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/vendor/promotions/${not empty promotion ? 'edit' : 'add'}" method="post">
                <c:if test="${not empty promotion}">
                    <input type="hidden" name="promotion_id" value="${promotion.promotionId}" />
                </c:if>

                <div class="mb-3">
                    <label for="product_id" class="form-label">Áp dụng cho (tùy chọn)</label>
                    <select name="product_id" id="product_id" class="form-select">
                        <option value="">-- Toàn shop --</option>
                        <c:forEach var="prod" items="${products}">
                            <option value="${prod.productId}" ${promotion != null && promotion.product != null && prod.productId == promotion.product.productId ? 'selected' : ''}>${prod.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label">Loại giảm</label>
                    <select name="discount_type" class="form-select" required>
                        <option value="PERCENT" ${promotion != null && promotion.discountType == 'PERCENT' ? 'selected' : ''}>Phần trăm (%)</option>
                        <option value="FIXED" ${promotion != null && promotion.discountType == 'FIXED' ? 'selected' : ''}>Tiền cố định</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label">Giá trị</label>
                    <input type="text" name="value" class="form-control" required value="${promotion != null ? promotion.value : ''}" />
                    <div class="form-text">Nếu là phần trăm ghi số (ví dụ 10 cho 10%). Nếu là tiền ghi dạng 10000.</div>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Ngày bắt đầu</label>
                        <input type="date" name="start_date" class="form-control" required value="${promotion != null ? promotion.startDate : ''}" />
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Ngày kết thúc</label>
                        <input type="date" name="end_date" class="form-control" required value="${promotion != null ? promotion.endDate : ''}" />
                    </div>
                </div>

                <div class="d-flex gap-2">
                    <button class="btn btn-success" type="submit">Lưu</button>
                    <a href="${pageContext.request.contextPath}/vendor/promotions" class="btn btn-secondary">Hủy</a>
                </div>
            </form>

            <c:if test="${not empty error}">
                <div class="alert alert-danger mt-3">${error}</div>
            </c:if>
        </div>
    </div>
</div>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp" %>

<h3 class="fw-bold text-primary-custom mb-4">
  <c:choose>
    <c:when test="${promotion != null}">
      <i class="bi bi-pencil"></i> Chỉnh sửa khuyến mãi
    </c:when>
    <c:otherwise>
      <i class="bi bi-plus-circle"></i> Thêm khuyến mãi mới
    </c:otherwise>
  </c:choose>
</h3>

<form method="post" action="${pageContext.request.contextPath}/admin/promotions/add" class="card p-4 shadow-sm">
  <input type="hidden" name="promotionId" value="${promotion.promotionId}" />

  <!-- SHOP -->
  <div class="mb-3">
    <label class="form-label fw-semibold">Shop áp dụng</label>
    <select name="shopId" class="form-select" required>
      <c:forEach var="s" items="${shops}">
        <option value="${s.shopId}" 
          <c:if test="${promotion.shop.shopId == s.shopId}">selected</c:if>>
          ${s.name}
        </option>
      </c:forEach>
    </select>
  </div>

  <!-- SẢN PHẨM -->
  <div class="mb-3">
    <label class="form-label fw-semibold">Sản phẩm áp dụng (tuỳ chọn)</label>
    <select name="productId" class="form-select">
      <option value="">-- Không chọn (áp dụng toàn shop) --</option>
      <c:forEach var="pr" items="${products}">
        <option value="${pr.productId}"
          <c:if test="${promotion.product != null && promotion.product.productId == pr.productId}">selected</c:if>>
          ${pr.name}
        </option>
      </c:forEach>
    </select>
  </div>

  <!-- Loại giảm & Giá trị -->
  <div class="row">
    <div class="col-md-6">
      <label class="form-label fw-semibold">Loại giảm</label>
      <select name="discountType" class="form-select" required>
        <option value="percent" <c:if test="${promotion.discountType eq 'percent'}">selected</c:if>>Giảm theo %</option>
        <option value="fixed" <c:if test="${promotion.discountType eq 'fixed'}">selected</c:if>>Giảm cố định (VNĐ)</option>
      </select>
    </div>
    <div class="col-md-6">
      <label class="form-label fw-semibold">Giá trị giảm</label>
      <input type="number" step="0.01" name="value" class="form-control" value="${promotion.value}" required>
    </div>
  </div>

  <!-- Ngày -->
  <div class="row mt-3">
    <div class="col-md-6">
      <label class="form-label fw-semibold">Ngày bắt đầu</label>
      <input type="date" name="startDate" class="form-control"
             value="<fmt:formatDate value='${promotion.startDate}' pattern='yyyy-MM-dd'/>" required>
    </div>
    <div class="col-md-6">
      <label class="form-label fw-semibold">Ngày kết thúc</label>
      <input type="date" name="endDate" class="form-control"
             value="<fmt:formatDate value='${promotion.endDate}' pattern='yyyy-MM-dd'/>" required>
    </div>
  </div>

  <div class="mt-4 d-flex gap-2">
    <button class="btn btn-primary-custom"><i class="bi bi-save"></i> Lưu</button>
    <a href="${pageContext.request.contextPath}/admin/promotions" class="btn btn-outline-secondary">Hủy</a>
  </div>
</form>

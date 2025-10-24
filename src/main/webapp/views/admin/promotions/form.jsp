<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp" %>

<%-- Xác định xem đây là trang Thêm mới hay Chỉnh sửa --%>
<c:set var="isEdit" value="${promotion != null && promotion.promotionId != null}" />
<c:url var="formAction" value="/admin/promotions/${isEdit ? 'edit' : 'add'}" />

<div class="container-fluid px-4">
  <div class="card shadow-sm border-0">
    <div class="card-header bg-primary-custom">
      <h4 class="mb-0 text-white">
        <c:choose>
          <c:when test="${isEdit}">
            <i class="bi bi-pencil-fill me-2"></i>Chỉnh sửa khuyến mãi
          </c:when>
          <c:otherwise>
            <i class="bi bi-plus-circle-fill me-2"></i>Thêm khuyến mãi mới
          </c:otherwise>
        </c:choose>
      </h4>
    </div>

    <div class="card-body p-4">
      <form method="post" action="${formAction}">
        <c:if test="${isEdit}">
          <input type="hidden" name="promotionId" value="${promotion.promotionId}" />
        </c:if>

        <div class="row">
          <div class="col-md-6">
            <div class="mb-3">
              <label for="shopId" class="form-label fw-bold">Shop áp dụng</label>
              <select id="shopId" name="shopId" class="form-select" required>
                <option value="" disabled selected>-- Vui lòng chọn shop --</option>
                <c:forEach var="s" items="${shops}">
                  <option value="${s.shopId}" 
                    <c:if test="${promotion.shop.shopId == s.shopId}">selected</c:if>>
                    ${s.name}
                  </option>
                </c:forEach>
              </select>
            </div>
          </div>
          <div class="col-md-6">
            <div class="mb-3">
              <label for="productId" class="form-label fw-bold">Sản phẩm áp dụng (tùy chọn)</label>
              <select id="productId" name="productId" class="form-select">
                <option value="">-- Áp dụng cho toàn bộ shop --</option>
                <c:forEach var="pr" items="${products}">
                  <option value="${pr.productId}"
                    <c:if test="${promotion.product != null && promotion.product.productId == pr.productId}">selected</c:if>>
                    ${pr.name}
                  </option>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-md-6">
            <div class="mb-3">
              <label class="form-label fw-bold">Loại giảm giá</label>
              <select name="discountType" class="form-select" required>
                <option value="percent" <c:if test="${promotion.discountType eq 'percent'}">selected</c:if>>Giảm theo %</option>
                <option value="fixed" <c:if test="${promotion.discountType eq 'fixed'}">selected</c:if>>Giảm cố định (VNĐ)</option>
              </select>
            </div>
          </div>
          <div class="col-md-6">
            <div class="mb-3">
              <label class="form-label fw-bold">Giá trị giảm</label>
              <input type="number" step="any" name="value" class="form-control" value="${promotion.value}" required>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-md-6">
            <div class="mb-3">
              <label class="form-label fw-bold">Ngày bắt đầu</label>
              <input type="date" name="startDate" class="form-control"
                     value="<fmt:formatDate value='${promotion.startDate}' pattern='yyyy-MM-dd'/>" required>
            </div>
          </div>
          <div class="col-md-6">
            <div class="mb-3">
              <label class="form-label fw-bold">Ngày kết thúc</label>
              <input type="date" name="endDate" class="form-control"
                     value="<fmt:formatDate value='${promotion.endDate}' pattern='yyyy-MM-dd'/>" required>
            </div>
          </div>
        </div>
        
        <hr class="hr-primary mt-3" style="opacity: 0.2;">
        
        <div class="d-flex justify-content-end gap-2">
          <a href="${pageContext.request.contextPath}/admin/promotions" class="btn btn-outline-secondary">
            <i class="bi bi-arrow-left me-1"></i> Quay lại
          </a>
          <button type="submit" class="btn btn-primary-custom">
            <c:choose>
              <c:when test="${isEdit}">
                <i class="bi bi-save-fill me-1"></i> Lưu thay đổi
              </c:when>
              <c:otherwise>
                <i class="bi bi-plus-lg me-1"></i> Thêm mới
              </c:otherwise>
            </c:choose>
          </button>
        </div>
      </form>
    </div>
  </div>
</div>
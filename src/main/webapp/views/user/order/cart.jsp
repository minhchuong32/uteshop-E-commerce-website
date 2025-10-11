<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container mt-4">
    <h3 class="mb-3">🛒 Giỏ hàng của bạn</h3>

    <c:if test="${empty cartItems}">
        <div class="alert alert-info">Giỏ hàng trống!</div>
    </c:if>

    <c:if test="${not empty cartByShop}">
        <form id="cartForm" action="${pageContext.request.contextPath}/user/checkout" method="get">
            <c:forEach var="entry" items="${cartByShop}">
                <div class="card mb-3 shadow-sm">
                    <div class="card-header bg-light fw-bold">
                        🏪 ${entry.key.name}
                    </div>

                    <div class="card-body">
                        <c:forEach var="item" items="${entry.value}">
                            <div class="d-flex align-items-center border-bottom py-2 cart-item">
                                <!-- Checkbox chọn -->
                                <input type="checkbox" class="form-check-input me-3 cart-checkbox"
                                       name="selectedItems" value="${item.cartItemId}">

                                <!-- Ảnh sản phẩm -->
                                <img src="${pageContext.request.contextPath}${item.productVariant.imageUrl}"
                                     class="me-3" style="width: 60px; height: 60px; object-fit: cover;">

                                <!-- Thông tin -->
                                <div class="flex-fill">
                                    <h6 class="mb-0">${item.productVariant.product.name}</h6>
                                    <small class="text-muted">${item.productVariant.optionValue}</small>
                                    <div><small class="text-muted">Số lượng: ${item.quantity}</small></div>
                                </div>

                                <!-- Tổng tiền -->
                                <div class="text-end me-3">
                                    <p class="mb-0 fw-semibold text-danger item-total"
                                       data-price="${item.productVariant.price}"
                                       data-qty="${item.quantity}">
                                        <fmt:formatNumber value="${item.productVariant.price * item.quantity}"
                                                          type="currency" currencySymbol="₫"/>
                                    </p>
                                    <small class="text-muted">
                                        (Đơn giá:
                                        <fmt:formatNumber value="${item.productVariant.price}"
                                                          type="currency" currencySymbol="₫"/>)
                                    </small>
                                </div>

								<!-- Nút Xóa (xử lý trong js) -->
								<button type="button"
									class="btn btn-outline-danger btn-sm ms-2 btn-remove"
									data-id="${item.cartItemId}">🗑️ Xóa</button>

							</div>
                        </c:forEach>
                    </div>
                </div>
            </c:forEach>

            <!-- Tổng tiền -->
            <div class="d-flex justify-content-between align-items-center border-top pt-3">
                <div><strong>Tổng tiền các sản phẩm đã chọn:</strong></div>
                <div><span id="total-price" class="text-danger fw-bold">0 ₫</span></div>
                <button type="submit" class="btn btn-success">Thanh toán</button>
            </div>
        </form>
    </c:if>
</div>
<script>
  window.contextPath = "${pageContext.request.contextPath}";
</script>

<script src="${pageContext.request.contextPath}/assets/js/user/cart.js"></script>

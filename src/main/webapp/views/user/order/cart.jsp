<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container py-4">
    <h3 class="fw-bold mb-4 text-primary-custom">
        <i class="bi bi-cart-check"></i> Giỏ hàng của bạn
    </h3>

    <!-- Nếu giỏ hàng trống -->
    <c:if test="${empty cartItems}">
        <div class="alert alert-info text-center shadow-sm py-3">
            <i class="bi bi-info-circle"></i> Giỏ hàng của bạn hiện đang trống.
        </div>
    </c:if>

    <!-- Nếu có sản phẩm -->
    <c:if test="${not empty cartByShop}">
        <form id="cartForm" action="${pageContext.request.contextPath}/user/checkout" method="get">
            <c:forEach var="entry" items="${cartByShop}">
                <div class="card border-0 shadow-sm mb-4 rounded-3">
                    <!-- Header tên shop -->
                    <div class="card-header bg-light fw-bold border-bottom d-flex align-items-center">
                        <i class="bi bi-shop me-2 text-primary"></i>
                        ${entry.key.name}
                    </div>

                    <div class="card-body">
                        <c:forEach var="item" items="${entry.value}">
                            <div class="cart-item d-flex align-items-center justify-content-between py-3 border-bottom">
                                <!-- Cột chọn -->
                                <div class="form-check me-3">
                                    <input type="checkbox" class="form-check-input cart-checkbox"
                                           name="selectedItems" value="${item.cartItemId}">
                                </div>

                                <!-- Ảnh sản phẩm -->
                                <div class="me-3">
                                    <img src="${pageContext.request.contextPath}/assets${item.productVariant.imageUrl}"
                                         class="rounded border"
                                         style="width: 70px; height: 70px; object-fit: cover;">
                                </div>

                                <!-- Thông tin sản phẩm -->
                                <div class="flex-fill">
                                    <h6 class="mb-1 fw-semibold">${item.productVariant.product.name}</h6>
                                    <small class="text-muted d-block mb-1">${item.productVariant.optionValue}</small>
                                    <span class="badge bg-light text-dark">SL: ${item.quantity}</span>
                                </div>

                                <!-- Giá và đơn giá -->
                                <div class="text-end me-3">
                                    <div class="fw-bold text-danger item-total"
                                         data-price="${item.productVariant.price}"
                                         data-qty="${item.quantity}">
                                        <fmt:formatNumber value="${item.productVariant.price * item.quantity}"
                                                          type="currency" currencySymbol="₫"/>
                                    </div>
                                    <small class="text-muted">
                                        (Đơn giá:
                                        <fmt:formatNumber value="${item.productVariant.price}"
                                                          type="currency" currencySymbol="₫"/>)
                                    </small>
                                </div>

                                <!-- Nút xóa -->
                                <button type="button" class="btn btn-outline-danger btn-sm btn-remove"
                                        data-id="${item.cartItemId}">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:forEach>

            <!-- Tổng tiền -->
            <div class="card border-0 shadow-sm mt-4">
                <div class="card-body d-flex flex-column flex-md-row justify-content-between align-items-center">
                    <div class="fw-semibold text-muted mb-2 mb-md-0">
                        Tổng tiền các sản phẩm đã chọn:
                    </div>
                    <div class="text-danger fs-5 fw-bold mb-2 mb-md-0" id="total-price">0 ₫</div>
                    <button type="submit" class="btn btn-success px-4">
                        <i class="bi bi-cash-coin me-1"></i> Thanh toán
                    </button>
                </div>
            </div>
        </form>
    </c:if>
</div>

<!-- Truyền contextPath -->
<script>
  window.contextPath = "${pageContext.request.contextPath}";
</script>

<!-- JS xử lý giỏ hàng -->
<script src="${pageContext.request.contextPath}/assets/js/user/cart.js"></script>

<!-- Thêm CSS tinh chỉnh -->
<style>
    .cart-item:hover {
        background-color: #f9f9f9;
        transition: background 0.3s ease;
    }
    .btn-remove:hover i {
        transform: rotate(-20deg);
        transition: transform 0.2s;
    }
</style>

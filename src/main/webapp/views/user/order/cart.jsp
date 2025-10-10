<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container mt-4">
    <h3 class="mb-3">🛒 Giỏ hàng của bạn</h3>

    <c:if test="${empty cartItems}">
        <div class="alert alert-info">Giỏ hàng trống!</div>
    </c:if>

    <c:if test="${not empty cartByShop}">
        <form id="cartForm" action="${pageContext.request.contextPath}/user/order/checkout" method="post">
            <c:forEach var="entry" items="${cartByShop}">
                <div class="card mb-3 shadow-sm">
                    <div class="card-header bg-light fw-bold">
                        🏪 ${entry.key.name}
                    </div>

                    <div class="card-body">
                        <c:forEach var="item" items="${entry.value}">
                            <div class="d-flex align-items-center border-bottom py-2">
                                <!-- ✅ Bỏ checked mặc định -->
                                <input type="checkbox" class="form-check-input me-3 cart-checkbox"
                                       name="selectedItems" value="${item.cartItemId}">

                                <img src="${pageContext.request.contextPath}${item.productVariant.imageUrl}"
                                     class="me-3" style="width: 60px; height: 60px; object-fit: cover;">

                                <div class="flex-fill">
                                    <h6 class="mb-0">${item.productVariant.product.name}</h6>
                                    <small class="text-muted">${item.productVariant.optionValue}</small>
                                    <div><small class="text-muted">Số lượng: ${item.quantity}</small></div>
                                </div>

                                <!-- ✅ Hiển thị tổng tiền từng item -->
                                <div class="text-end">
                                    <p class="mb-0 fw-semibold text-danger item-total"
                                       data-price="${item.productVariant.price}"
                                       data-qty="${item.quantity}">
                                        <fmt:formatNumber value="${item.productVariant.price * item.quantity}" 
                                                          type="currency" currencySymbol="₫" />
                                    </p>
                                    <small class="text-muted">
                                        (Đơn giá: 
                                        <fmt:formatNumber value="${item.productVariant.price}" type="currency" currencySymbol="₫"/>)
                                    </small>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:forEach>

            <!-- ✅ Tổng tiền cuối form -->
            <div class="d-flex justify-content-between align-items-center border-top pt-3">
                <div><strong>Tổng tiền các sản phẩm đã chọn:</strong></div>
                <div><span id="total-price" class="text-danger fw-bold">0 ₫</span></div>
                <button type="submit" class="btn btn-success">Thanh toán</button>
            </div>
        </form>
    </c:if>
</div>

<script>
document.addEventListener("DOMContentLoaded", function() {
    const checkboxes = document.querySelectorAll(".cart-checkbox");
    const totalPriceEl = document.getElementById("total-price");

    function calculateTotal() {
        let total = 0;
        checkboxes.forEach(cb => {
            if (cb.checked) {
                const itemTotalEl = cb.closest(".d-flex").querySelector(".item-total");
                const price = parseFloat(itemTotalEl.dataset.price);
                const qty = parseInt(itemTotalEl.dataset.qty);
                total += price * qty;
            }
        });
        totalPriceEl.textContent = new Intl.NumberFormat('vi-VN').format(total) + " ₫";
    }

    checkboxes.forEach(cb => cb.addEventListener("change", calculateTotal));
    calculateTotal(); // ✅ chạy lần đầu để hiển thị tổng ban đầu = 0
});
</script>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="vi_VN"/>

<div class="container my-5">
    <h2 class="mb-4">Checkout</h2>
    <form action="${pageContext.request.contextPath}/user/checkout" method="post">
        <div class="row">
            <!-- Cột trái -->
            <div class="col-md-8">
                <!-- Order Items -->
                <div class="card mb-4 shadow-sm">
                    <div class="card-header bg-white">
                        <h5 class="mb-0">Order Items</h5>
                    </div>
                    <div class="card-body">
                        <c:if test="${empty cartItems}">
                            <p class="text-muted">Giỏ hàng của bạn đang trống.</p>
                        </c:if>
                        <c:if test="${not empty cartItems}">
                            <c:set var="subtotal" value="0"/>
                            <c:forEach var="item" items="${cartItems}">
                                <div class="d-flex align-items-center justify-content-between border-bottom py-2">
                                    <!-- Hình ảnh -->
                                    <div class="d-flex align-items-center gap-3">
                                        <img src="${item.product.imageUrl}" alt="${item.product.name}"
                                             class="img-thumbnail" style="width:70px;height:70px;object-fit:cover;">
                                        <div>
                                            <p class="mb-1 fw-semibold">${item.product.name}</p>
                                            <small class="text-muted">
                                                <fmt:formatNumber value="${item.product.price}" type="number" groupingUsed="true"/>₫
                                            </small>
                                        </div>
                                    </div>

                                    <!-- Số lượng -->
                                    <div class="d-flex align-items-center gap-2">
                                        <a href="${pageContext.request.contextPath}/user/cart?action=decrease&productId=${item.product.productId}" class="btn btn-sm btn-outline-secondary">-</a>
                                        <span>${item.quantity}</span>
                                        <a href="${pageContext.request.contextPath}/user/cart?action=increase&productId=${item.product.productId}" class="btn btn-sm btn-outline-secondary">+</a>
                                    </div>

                                    <!-- Thành tiền -->
                                    <div class="fw-bold">
                                        <fmt:formatNumber value="${item.product.price * item.quantity}" type="number" groupingUsed="true"/>₫
                                    </div>

                                    <!-- Xóa -->
                                    <a href="${pageContext.request.contextPath}/user/cart?action=remove&cartItemId=${item.cartItemId}" class="text-danger ms-3">
                                        <i class="bi bi-trash"></i>
                                    </a>
                                </div>
                                <c:set var="subtotal" value="${subtotal + (item.product.price * item.quantity)}"/>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>

                <!-- Shipping Information -->
                <div class="card mb-4 shadow-sm">
                    <div class="card-header bg-white">
                        <h5 class="mb-0"><i class="bi bi-truck"></i> Shipping Information</h5>
                    </div>
                    <div class="card-body">
                        <div class="mb-3">
                            <label class="form-label">Full Name</label>
                            <input type="text" name="fullname" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Phone Number</label>
                            <input type="text" name="phone" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Shipping Address</label>
                            <textarea name="address" class="form-control" rows="3" required></textarea>
                        </div>
                    </div>
                </div>

                <!-- Payment Method -->
                <div class="card shadow-sm">
                    <div class="card-header bg-white">
                        <h5 class="mb-0"><i class="bi bi-credit-card"></i> Payment Method</h5>
                    </div>
                    <div class="card-body">
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="payment" value="COD" checked>
                            <label class="form-check-label">Cash on Delivery (COD)</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="payment" value="MoMo">
                            <label class="form-check-label">MoMo Wallet</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="payment" value="VNPAY">
                            <label class="form-check-label">VNPay</label>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Cột phải -->
            <div class="col-md-4">
                <div class="card shadow-sm">
                    <div class="card-header bg-white">
                        <h5 class="mb-0">Order Summary</h5>
                    </div>
                    <div class="card-body">
                        <p class="d-flex justify-content-between">
<%--                             <span>Subtotal (${fn:length(cartItems)} items)</span> --%>
                            <strong><fmt:formatNumber value="${subtotal}" type="number" groupingUsed="true"/>₫</strong>
                        </p>
                        <p class="d-flex justify-content-between">
                            <span>Shipping</span>
                            <strong>30,000₫</strong>
                        </p>
                        <hr>
                        <p class="d-flex justify-content-between fs-5">
                            <span>Total</span>
                            <strong><fmt:formatNumber value="${subtotal + 30000}" type="number" groupingUsed="true"/>₫</strong>
                        </p>
                        <button type="submit" class="btn btn-primary w-100">Place Order</button>
                        <small class="text-muted d-block text-center mt-2">
                            Free shipping on orders over 1,000,000₫
                        </small>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>

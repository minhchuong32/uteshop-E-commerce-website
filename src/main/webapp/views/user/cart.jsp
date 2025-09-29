<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/theme.css">

<div class="container my-5">

    <h3 class="fw-bold text-light mb-4">Checkout</h3>

    <div class="row g-4">
        <!-- LEFT SIDE -->
        <div class="col-lg-8">

            <!-- Order Items -->
            <div class="card shadow-sm mb-4">
                <div class="card-body">
                    <h5 class="fw-bold mb-3">Order Items</h5>
                    <c:forEach var="item" items="${cartItems}">
                        <div class="d-flex align-items-center justify-content-between border-bottom pb-3 mb-3">
                            <div class="d-flex align-items-center">
                                <img src="${pageContext.request.contextPath}/uploads/${item.product.image}" 
                                     alt="${item.product.name}" 
                                     class="rounded me-3" style="width:70px;height:70px;object-fit:cover;">
                                <div>
                                    <p class="mb-1 fw-bold">${item.product.name}</p>
                                    <p class="text-muted mb-0">$${item.product.price}</p>
                                </div>
                            </div>
                            <div class="d-flex align-items-center">
                                <form action="${pageContext.request.contextPath}/cart/update" method="post" class="d-flex align-items-center">
                                    <input type="hidden" name="productId" value="${item.product.id}">
                                    <button type="submit" name="action" value="decrease" class="btn btn-sm btn-outline-secondary">-</button>
                                    <input type="text" readonly class="form-control mx-1 text-center" style="width:50px;" value="${item.quantity}">
                                    <button type="submit" name="action" value="increase" class="btn btn-sm btn-outline-secondary">+</button>
                                </form>
                                <p class="fw-bold mb-0 ms-3">$${item.product.price * item.quantity}</p>
                                <form action="${pageContext.request.contextPath}/cart/remove" method="post" class="ms-3">
                                    <input type="hidden" name="productId" value="${item.product.id}">
                                    <button type="submit" class="btn btn-sm btn-danger">🗑</button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <!-- Shipping Information -->
            <div class="card shadow-sm mb-4">
                <div class="card-body">
                    <h5 class="fw-bold mb-3">Shipping Information</h5>
                    <form action="${pageContext.request.contextPath}/order/checkout" method="post">
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
                            <textarea name="address" class="form-control" rows="2" required></textarea>
                        </div>
                </div>
            </div>

            <!-- Payment Method -->
            <div class="card shadow-sm mb-4">
                <div class="card-body">
                    <h5 class="fw-bold mb-3">Payment Method</h5>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="payment" value="COD" checked>
                        <label class="form-check-label">Cash on Delivery (COD)</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="payment" value="Momo">
                        <label class="form-check-label">MoMo Wallet</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="payment" value="VnPay">
                        <label class="form-check-label">VNPay</label>
                    </div>
                </div>
            </div>

        </div>

        <!-- RIGHT SIDE -->
        <div class="col-lg-4">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h5 class="fw-bold mb-3">Order Summary</h5>
                    <p class="d-flex justify-content-between">
                        <span>Subtotal (${cartItems.size()} items)</span>
                        <span>$${subtotal}</span>
                    </p>
                    <p class="d-flex justify-content-between">
                        <span>Shipping</span>
                        <span>$${shippingFee}</span>
                    </p>
                    <hr>
                    <p class="d-flex justify-content-between fw-bold fs-5">
                        <span>Total</span>
                        <span>$${total}</span>
                    </p>
                    <button type="submit" class="btn btn-primary-custom w-100 mt-3">Place Order</button>
                    <small class="text-muted d-block mt-2">Free shipping on orders over $50</small>
                </div>
            </div>
        </div>
    </div>
    </form>
</div>

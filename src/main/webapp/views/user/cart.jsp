<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/theme.css">

<div class="container my-5">
    <h3 class="fw-bold text-light mb-4">Giỏ hàng của bạn</h3>

    <c:if test="${empty cartItems}">
        <div class="alert alert-info">Chưa có sản phẩm nào trong giỏ hàng!</div>
    </c:if>

    <c:if test="${not empty cartItems}">
        <div class="card shadow-sm">
            <div class="card-body">
                <c:forEach var="item" items="${cartItems}">
                    <div class="d-flex align-items-center justify-content-between border-bottom pb-3 mb-3">
                        <!-- Hình ảnh + tên sp -->
                        <div class="d-flex align-items-center">
                            <img src="${pageContext.request.contextPath}/uploads/${item.product.image}" 
                                 alt="${item.product.name}" 
                                 class="rounded me-3" style="width:70px;height:70px;object-fit:cover;">
                            <div>
                                <p class="mb-1 fw-bold">${item.product.name}</p>
                                <p class="text-muted mb-0">${item.product.price} ₫</p>
                            </div>
                        </div>

                        <!-- Nút tăng/giảm số lượng -->
                        <div class="d-flex align-items-center">
                            <form action="${pageContext.request.contextPath}/user/cart" method="post" class="d-flex align-items-center">
                                <input type="hidden" name="productId" value="${item.product.productId}">
                                <button type="submit" name="action" value="decrease" class="btn btn-sm btn-outline-secondary">-</button>
                                <input type="text" readonly class="form-control mx-1 text-center" style="width:50px;" value="${item.quantity}">
                                <button type="submit" name="action" value="increase" class="btn btn-sm btn-outline-secondary">+</button>
                            </form>

                            <!-- Tổng tiền -->
                            <p class="fw-bold mb-0 ms-3">${item.product.price * item.quantity} ₫</p>

                            <!-- Xóa sản phẩm -->
                            <form action="${pageContext.request.contextPath}/user/cart" method="post" class="ms-3">
                                <input type="hidden" name="productId" value="${item.product.productId}">
                                <input type="hidden" name="cartItemId" value="${item.cartItemId}">
                                <button type="submit" name="action" value="remove" class="btn btn-sm btn-danger">🗑</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:if>

    <!-- Nút thanh toán -->
    <c:if test="${not empty cartItems}">
        <div class="mt-4 text-end">
            <a href="${pageContext.request.contextPath}/user/checkout" class="btn btn-primary px-4">Thanh toán</a>
        </div>
    </c:if>
</div>

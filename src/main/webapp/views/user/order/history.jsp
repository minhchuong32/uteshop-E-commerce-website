<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/css/theme.css">

<div class="container">

    <h3 class="fw-bold text-primary-custom mt-4 mb-4">Lịch sử đơn hàng</h3>

    <!-- Nếu không có đơn hàng -->
    <c:if test="${not empty message}">
        <div class="text-center my-5">
            <h4 class="fw-bold">Giỏ hàng trống</h4>
            <p>Hãy thêm sản phẩm để tiếp tục mua sắm.</p>
            <a href="${pageContext.request.contextPath}/user/home"
               class="btn btn-primary"> Tiếp tục mua sắm </a>
        </div>
    </c:if>

    <!-- Nếu có đơn hàng -->
    <c:if test="${not empty orders}">
        <c:forEach var="o" items="${orders}">
            <div class="card shadow-sm mb-3">
                <div class="card-body d-flex justify-content-between align-items-center">
                    <div>
                        <!-- Mã đơn hàng -->
                        <h5 class="fw-bold">Đơn hàng #ORD-${o.orderId}</h5>

                        <!-- Trạng thái -->
                        <span class="badge
                            <c:choose>
                                <c:when test='${o.status eq "delivered"}'>bg-success</c:when>
                                <c:when test='${o.status eq "shipped"}'>bg-primary</c:when>
                                <c:when test='${o.status eq "processing"}'>bg-warning</c:when>
                                <c:when test='${o.status eq "cancelled"}'>bg-danger</c:when>
                                <c:otherwise>bg-secondary</c:otherwise>
                            </c:choose>">
                            ${o.status}
                        </span>

                        <p class="mb-1">
                            Ngày đặt: <fmt:formatDate value="${o.createdAt}" pattern="dd/MM/yyyy HH:mm" />
                        </p>
                        <p class="mb-1">
                            Tổng tiền: <fmt:formatNumber value="${o.totalAmount}" type="currency" currencySymbol="₫" />
                        </p>
                        <p class="mb-0">Phương thức thanh toán: ${o.paymentMethod}</p>
                    </div>

                    <div class="text-end">
                        <!-- Nút xem chi tiết -->
                        <a class="btn btn-link text-primary" data-bs-toggle="collapse"
                           href="#detail-${o.orderId}" role="button" aria-expanded="false">
                            Xem chi tiết
                        </a>

                        <!-- Nút khiếu nại chỉ hiển thị nếu đơn hàng đã giao -->
                        <c:if test="${o.status eq 'Hoàn tất'}">
                            <a href="${pageContext.request.contextPath}/user/complaints/add?orderId=${o.orderId}"
                               class="btn btn-outline-danger btn-sm mt-1">
                                <i class="bi bi-exclamation-octagon-fill me-1"></i> Gửi khiếu nại
                            </a>
                        </c:if>
                    </div>
                </div>

                <!-- Chi tiết đơn hàng -->
                <div class="collapse" id="detail-${o.orderId}">
                    <div class="card-body border-top">
                        <div class="row">
                            <!-- Sản phẩm -->
                            <div class="col-md-6">
                                <h6 class="fw-bold mb-2">Sản phẩm</h6>
                                <c:forEach var="d" items="${o.orderDetails}">
                                    <div class="d-flex mb-2">
                                        <img src="${pageContext.request.contextPath}/uploads/${d.product.imageUrl}"
                                             class="me-2 rounded" width="50" height="50" />
                                        <div>
                                            <p class="mb-0">${d.product.name}</p>
                                            <small>Số lượng: ${d.quantity} ×
                                                <fmt:formatNumber value="${d.price}" type="currency" currencySymbol="₫" />
                                            </small>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>

                            <!-- Thông tin giao hàng -->
                            <div class="col-md-6">
                                <h6 class="fw-bold mb-2">Thông tin giao hàng</h6>
                                <p class="mb-0">${o.user.username}</p>
                                <p class="mb-0">${o.user.email}</p>
                                <p class="mb-0">${o.address}</p>
                            </div>
                        </div>

                        <!-- Timeline (delivery history) -->
                        <div class="mt-3">
                            <h6 class="fw-bold mb-2">Tiến trình đơn hàng</h6>
                            <ul class="list-unstyled">
                                <c:forEach var="deli" items="${o.deliveries}">
                                    <li>
                                        <span class="fw-bold">●</span> ${deli.status}
                                        <small class="text-muted">
                                            (<fmt:formatDate value="${deli.createdAt}" pattern="dd/MM/yyyy HH:mm" />)
                                        </small>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </c:if>

</div>

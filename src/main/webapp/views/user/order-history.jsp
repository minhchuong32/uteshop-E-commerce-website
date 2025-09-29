<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/theme.css">

<div class="container my-5">

    <h3 class="fw-bold text-light mb-4">Lịch sử đơn hàng</h3>

    <!-- Nếu không có đơn hàng -->
    <c:if test="${not empty message}">
        <div class="alert alert-info text-center">
            ${message}
        </div>
    </c:if>

    <!-- Nếu có đơn hàng -->
    <c:if test="${not empty orders}">
        <div class="card shadow-sm">
            <div class="card-body">
                <table class="table table-bordered align-middle">
                    <thead class="table-light">
                        <tr>
                            <th>ID</th>
                            <th>Tổng tiền</th>
                            <th>Trạng thái</th>
                            <th>Phương thức thanh toán</th>
                            <th>Ngày tạo</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="o" items="${orders}">
                            <tr>
                                <td>${o.orderId}</td>
                                <td>
                                    <fmt:formatNumber value="${o.totalAmount}" type="currency" currencySymbol="₫"/>
                                </td>
                                <td>
                                    <span class="badge 
                                        <c:choose>
                                            <c:when test="${o.status eq 'new'}">bg-secondary</c:when>
                                            <c:when test="${o.status eq 'processing'}">bg-warning</c:when>
                                            <c:when test="${o.status eq 'completed'}">bg-success</c:when>
                                            <c:when test="${o.status eq 'cancelled'}">bg-danger</c:when>
                                            <c:otherwise>bg-info</c:otherwise>
                                        </c:choose>">
                                        ${o.status}
                                    </span>
                                </td>
                                <td>${o.paymentMethod}</td>
                                <td><fmt:formatDate value="${o.createdAt}" pattern="dd/MM/yyyy HH:mm"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </c:if>

    <div class="mt-4">
        <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Tiếp tục mua sắm</a>
    </div>
</div>

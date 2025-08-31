<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container-fluid">
    <h3 class="mb-4 fw-bold">Quản lý đơn hàng</h3>

    <div class="card shadow-sm">
        <div class="card-body">
            <table class="table align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th>ID Đơn hàng</th>
                        <th>ID Người dùng</th>
                        <th>Tổng tiền</th>
                        <th>Trạng thái</th>
                        <th>Phương thức thanh toán</th>
                        <th>Ngày tạo</th>
                        <th class="text-center">Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="o" items="${orders}">
                        <tr>
                            <!-- order_id -->
                            <td class="fw-bold">${o.order_id}</td>

                            <!-- user_id -->
                            <td>${o.user_id}</td>

                            <!-- total_amount -->
                            <td>
                                <fmt:formatNumber value="${o.total_amount}" type="currency" currencySymbol="₫"/>
                            </td>

                            <!-- status -->
                            <td>
                                <c:choose>
                                    <c:when test="${o.status eq 'new'}">
                                        <span class="badge bg-secondary">Mới</span>
                                    </c:when>
                                    <c:when test="${o.status eq 'confirmed'}">
                                        <span class="badge bg-primary">Đã xác nhận</span>
                                    </c:when>
                                    <c:when test="${o.status eq 'shipping'}">
                                        <span class="badge bg-warning text-dark">Đang giao</span>
                                    </c:when>
                                    <c:when test="${o.status eq 'delivered'}">
                                        <span class="badge bg-success">Đã giao</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-light text-dark">Khác</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <!-- payment_method -->
                            <td>${o.payment_method}</td>

                            <!-- created_at -->
                            <td>${o.created_at}</td>

                            <!-- Actions -->
                            <td class="text-center">
                                <!-- Xem chi tiết -->
                                <i class="bi bi-eye text-primary me-3" role="button"></i>
                                <!-- Cập nhật trạng thái -->
                                <i class="bi bi-pencil-square text-success me-3" role="button"></i>
                                <!-- Xóa -->
                                <i class="bi bi-trash text-danger" role="button"></i>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h3 class="mb-4">Danh sách đơn giao hàng - Shipper ID: ${shipperId}</h3>

<!-- 1️⃣ ĐƠN ĐANG TÌM SHIPPER -->
<div class="card mb-4 shadow-sm">
    <div class="card-header bg-warning text-dark fw-bold">
        🚚 Đơn đang tìm shipper
    </div>
    <div class="card-body p-0">
        <table class="table table-bordered table-striped mb-0 text-center align-middle">
            <thead class="table-light">
            <tr>
                <th>Mã giao hàng</th>
                <th>Khách hàng</th>
                <th>Địa chỉ</th>
                <th>Tổng tiền</th>
                <th>Trạng thái</th>
                <th>Thao tác</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="d" items="${unassigned}">
                <tr>
                    <td>${d.deliveryId}</td>
                    <td>${d.order.user.username}</td>
                    <td>${d.order.address}</td>
                    <td>${d.order.totalAmount}</td>
                    <td>${d.status}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/shipper/assign" method="post">
                            <input type="hidden" name="deliveryId" value="${d.deliveryId}" />
                            <button type="submit" class="btn btn-success btn-sm">
                                <i class="bi bi-hand-thumbs-up"></i> Nhận đơn
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty unassigned}">
                <tr><td colspan="6" class="text-muted">Không có đơn nào đang chờ shipper.</td></tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>

<!-- 2️⃣ ĐƠN ĐANG GIAO -->
<div class="card mb-4 shadow-sm">
    <div class="card-header bg-primary text-white fw-bold">
        📦 Đơn đang giao
    </div>
    <div class="card-body p-0">
        <table class="table table-bordered table-striped mb-0 text-center align-middle">
            <thead class="table-light">
            <tr>
                <th>Mã giao hàng</th>
                <th>Khách hàng</th>
                <th>Địa chỉ</th>
                <th>Tổng tiền</th>
                <th>Trạng thái</th>
                <th>Thao tác</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="d" items="${delivering}">
                <tr>
                    <td>${d.deliveryId}</td>
                    <td>${d.order.user.username}</td>
                    <td>${d.order.address}</td>
                    <td>${d.order.totalAmount}</td>
                    <td>${d.status}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/shipper/orders" method="post" class="d-flex justify-content-center">
                            <input type="hidden" name="deliveryId" value="${d.deliveryId}" />
                            <select name="status" class="form-select form-select-sm me-2 w-auto">
                                <option value="Đang giao" ${d.status=='Đang giao'?'selected':''}>Đang giao</option>
                                <option value="Đã giao" ${d.status=='Đã giao'?'selected':''}>Đã giao</option>
                                <option value="Trả lại" ${d.status=='Trả lại'?'selected':''}>Trả lại</option>
                            </select>
                            <button type="submit" class="btn btn-primary btn-sm">
                                <i class="bi bi-arrow-repeat"></i> Cập nhật
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty delivering}">
                <tr><td colspan="6" class="text-muted">Không có đơn hàng nào đang giao.</td></tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>

<!-- 3️⃣ ĐƠN ĐÃ GIAO / ĐÃ HỦY -->
<div class="card shadow-sm">
    <div class="card-header bg-secondary text-white fw-bold">
        ✅ Đơn đã giao & đã hủy
    </div>
    <div class="card-body p-0">
        <table class="table table-bordered table-striped mb-0 text-center align-middle">
            <thead class="table-light">
            <tr>
                <th>Mã giao hàng</th>
                <th>Khách hàng</th>
                <th>Địa chỉ</th>
                <th>Tổng tiền</th>
                <th>Trạng thái</th>
                <th>Thao tác</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="d" items="${finished}">
                <tr>
                    <td>${d.deliveryId}</td>
                    <td>${d.order.user.username}</td>
                    <td>${d.order.address}</td>
                    <td>${d.order.totalAmount}</td>
                    <td>${d.status}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/shipper/orders/delete?id=${d.deliveryId}"
                           class="btn btn-danger btn-sm"
                           onclick="return confirm('Bạn có chắc muốn xóa đơn này không?');">
                            <i class="bi bi-trash"></i> Xóa
                        </a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty finished}">
                <tr><td colspan="6" class="text-muted">Không có đơn hàng đã giao hoặc đã hủy.</td></tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">
    <h3 class="mb-4 fw-bold">Quản lý đơn hàng</h3>

    <!-- Đơn hàng mới -->
    <h4>Đơn hàng mới</h4>
    <table class="table table-bordered text-center align-middle">
        <thead>
            <tr>
                <th>ID</th>
                <th>Khách hàng</th>
                <th>Tổng tiền</th>
                <th>Trạng thái</th>
                <th>Shipper</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="o" items="${newOrders}">
                <tr>
                    <td>${o.orderId}</td>
                    <td>${o.user.username}</td>
                    <td><fmt:formatNumber value="${o.totalAmount}" type="currency" currencySymbol="₫"/></td>
                    <td>${o.status}</td>
                    <td>Chưa có</td>
                    <td>
                        <button class="btn btn-sm btn-success" onclick="confirmOrder(${o.orderId})">Xác nhận</button>
                        <button class="btn btn-sm btn-danger" onclick="cancelOrder(${o.orderId})">Hủy</button>
                        
                        <!-- Khung nhập lý do hủy (ẩn mặc định) -->
					    <div id="cancel-box-${o.orderId}" class="mt-2" style="display:none;">
					        <textarea id="reason-${o.orderId}" class="form-control mb-2" rows="2" placeholder="Nhập lý do hủy..."></textarea>
					        <button class="btn btn-sm btn-outline-danger" onclick="submitCancel(${o.orderId})">Gửi</button>
					        <button class="btn btn-sm btn-secondary" onclick="toggleCancelBox(${o.orderId})">Đóng</button>
					    </div>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- Đơn đã xác nhận / đang giao -->
    <h4>Đơn đã xác nhận / đang giao</h4>
    <table class="table table-bordered text-center align-middle">
        <thead>
            <tr>
                <th>ID</th>
                <th>Khách hàng</th>
                <th>Tổng tiền</th>
                <th>Trạng thái</th>
                <th>Shipper</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="o" items="${confirmedOrders}">
                <tr>
                    <td>${o.orderId}</td>
                    <td>${o.user.username}</td>
                    <td><fmt:formatNumber value="${o.totalAmount}" type="currency" currencySymbol="₫"/></td>
                    <td>${o.status}</td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty shippersMap[o.orderId]}">
                                ${shippersMap[o.orderId].username}
                            </c:when>
                            <c:otherwise>
                                Chưa có
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/vendor/orders/detail?id=${o.orderId}" class="btn btn-sm btn-primary">Xem chi tiết</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- Đơn đã giao -->
    <h4>Đơn đã giao</h4>
    <table class="table table-bordered text-center align-middle">
        <thead>
            <tr>
                <th>ID</th>
                <th>Khách hàng</th>
                <th>Tổng tiền</th>
                <th>Trạng thái</th>
                <th>Shipper</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="o" items="${deliveredOrders}">
                <tr>
                    <td>${o.orderId}</td>
                    <td>${o.user.username}</td>
                    <td><fmt:formatNumber value="${o.totalAmount}" type="currency" currencySymbol="₫"/></td>
                    <td>${o.status}</td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty shippersMap[o.orderId]}">
                                ${shippersMap[o.orderId].username}
                            </c:when>
                            <c:otherwise>
                                Chưa có
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <button class="btn btn-sm btn-danger" onclick="deleteOrder(${o.orderId})">Xóa</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- Đơn đã hủy -->
    <h4>Đơn đã hủy</h4>
    <table class="table table-bordered text-center align-middle">
        <thead>
            <tr>
                <th>ID</th>
                <th>Khách hàng</th>
                <th>Tổng tiền</th>
                <th>Trạng thái</th>
                <th>Shipper</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="o" items="${canceledOrders}">
                <tr>
                    <td>${o.orderId}</td>
                    <td>${o.user.username}</td>
                    <td><fmt:formatNumber value="${o.totalAmount}" type="currency" currencySymbol="₫"/></td>
                    <td>${o.status}</td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty shippersMap[o.orderId]}">
                                ${shippersMap[o.orderId].username}
                            </c:when>
                            <c:otherwise>
                                Chưa có
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <button class="btn btn-sm btn-danger" onclick="deleteOrder(${o.orderId})">Xóa</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</div>

<!-- JS xử lý confirm / cancel / delete -->
<script>
function confirmOrder(orderId) {
    if (confirm('Bạn có chắc chắn muốn xác nhận đơn hàng này không?')) {
        fetch('${pageContext.request.contextPath}/vendor/orders/action', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: 'action=confirm&id=' + orderId
        }).then(() => location.reload());
    }
}

function cancelOrder(orderId) {
    if (confirm('Bạn có chắc chắn muốn hủy đơn hàng này không?')) {
        fetch('${pageContext.request.contextPath}/vendor/orders/action', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: 'action=cancel&id=' + orderId
        }).then(() => location.reload());
    }
}

function deleteOrder(orderId) {
    if (confirm('Bạn có chắc chắn muốn xóa đơn hàng này không?')) {
        window.location.href = '${pageContext.request.contextPath}/vendor/orders/delete?id=' + orderId;
    }
}
</script>
<script>
function toggleCancelBox(orderId) {
    const box = document.getElementById('cancel-box-' + orderId);
    box.style.display = (box.style.display === 'none' || box.style.display === '') ? 'block' : 'none';
}

function submitCancel(orderId) {
    const reason = document.getElementById('reason-' + orderId).value.trim();
    if (reason === '') {
        alert('Vui lòng nhập lý do hủy đơn hàng.');
        return;
    }

    if (confirm('Bạn có chắc chắn muốn hủy đơn hàng này không?')) {
        fetch('${pageContext.request.contextPath}/vendor/orders/action', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: new URLSearchParams({
                action: 'cancel',
                id: orderId,
                reason: reason
            })
        }).then(() => location.reload());
    }
}
</script>


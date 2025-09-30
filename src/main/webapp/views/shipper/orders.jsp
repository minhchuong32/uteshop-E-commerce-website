<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h2>Danh sách đơn giao hàng - Shipper ID: ${shipperId}</h2>

<table class="table table-bordered table-striped">
    <thead>
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
    <c:forEach var="d" items="${deliveries}">
        <tr>
            <td>${d.deliveryId}</td>
            <td>${d.order.user.username}</td>
            <td>${d.order.address}</td>
            <td>${d.order.totalAmount}</td>
            <td>${d.status}</td>
            <td>
                <form action="${pageContext.request.contextPath}/shipper/orders" method="post" class="d-flex">
                    <input type="hidden" name="deliveryId" value="${d.deliveryId}"/>
                    <select name="status" class="form-select form-select-sm me-2">
                        <option value="Đã gán"     ${d.status=='Đã gán'?'selected':''}>Đã gán</option>
                        <option value="Đang giao"  ${d.status=='Đang giao'?'selected':''}>Đang giao</option>
                        <option value="Đã giao"    ${d.status=='Đã giao'?'selected':''}>Đã giao</option>
                        <option value="Hủy"        ${d.status=='Hủy'?'selected':''}>Hủy</option>
                        <option value="Trả lại"    ${d.status=='Trả lại'?'selected':''}>Trả lại</option>
                    </select>
                    <button type="submit" class="btn btn-primary btn-sm">Cập nhật</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">
    <h3 class="mb-4 fw-bold">Chi tiết đơn hàng #${order.orderId}</h3>

    <table class="table table-bordered text-center align-middle">
        <tr>
            <th>ID</th>
            <td>${order.orderId}</td>
        </tr>
        <tr>
            <th>Khách hàng</th>
            <td>${order.user.username}</td>
        </tr>
        <tr>
            <th>Tổng tiền</th>
            <td><fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="₫"/></td>
        </tr>
        <tr>
            <th>Trạng thái</th>
            <td>${order.status}</td>
        </tr>
        <tr>
            <th>Phương thức thanh toán</th>
            <td>${order.paymentMethod}</td>
        </tr>
        <tr>
            <th>Địa chỉ giao hàng</th>
            <td>${order.address}</td>
        </tr>
        <tr>
		    <th>Shipper</th>
		    <td>
		        <c:choose>
		            <c:when test="${not empty shipper}">
		                ${shipper.username}
		            </c:when>
		            <c:otherwise>
		                Chưa có
		            </c:otherwise>
		        </c:choose>
		    </td>
		</tr>
    </table>

    <a href="${pageContext.request.contextPath}/vendor/orders" class="btn btn-secondary mt-3">Quay lại</a>
</div>

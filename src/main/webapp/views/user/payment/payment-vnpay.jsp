<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN"/>

<div class="container mt-5 text-center">
    <h3>Thanh toán qua VNPay</h3>
    <p class="lead">Số tiền cần thanh toán:</p>
    <h2 class="text-primary">
        <fmt:formatNumber value="${amount}" type="currency" currencySymbol="₫" />
    </h2>
    <p>Quét mã QR dưới đây hoặc nhấn “Thanh toán ngay”:</p>
    <img src="${pageContext.request.contextPath}/assets/images/qr/vnpay-demo.png" alt="VNPay QR" width="250" height="250">
    <p class="mt-3">Sau khi thanh toán, vui lòng chờ xác nhận đơn hàng!</p>
    <a href="${pageContext.request.contextPath}/user/orders" class="btn btn-success mt-3">Xem đơn hàng</a>
</div>

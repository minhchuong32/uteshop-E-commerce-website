<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container py-4" style="max-width: 900px;">
    <h3 class="mb-4 fw-semibold text-primary-custom">
        <i class="bi bi-pencil-square"></i> Chỉnh sửa đơn hàng #${order.orderId}
    </h3>

    <form action="${pageContext.request.contextPath}/admin/orders/edit" method="post">
        <input type="hidden" name="orderId" value="${order.orderId}" />
        <c:if test="${delivery != null}">
            <input type="hidden" name="deliveryId" value="${delivery.deliveryId}" />
        </c:if>

        <!-- Thông tin khách hàng -->
        <div class="mb-3">
            <label class="form-label fw-bold">Khách hàng</label>
            <input type="text" class="form-control" value="${order.user.name}" readonly />
            <small class="text-muted">${order.user.email} | ${order.user.phone}</small>
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold">Địa chỉ giao hàng</label>
            <input type="text" name="address" class="form-control" value="${order.address}" />
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold">Trạng thái đơn hàng</label>
            <select name="status" class="form-select">
                <option value="Mới" ${order.status == 'Mới' ? 'selected' : ''}>Mới</option>
                <option value="Đang giao" ${order.status == 'Đang giao' ? 'selected' : ''}>Đang giao</option>
                <option value="Đã giao" ${order.status == 'Đã giao' ? 'selected' : ''}>Đã giao</option>
                <option value="Đã hủy" ${order.status == 'Đã hủy' ? 'selected' : ''}>Đã hủy</option>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold">Phương thức thanh toán</label>
            <select name="paymentMethod" class="form-select">
                <option value="COD" ${order.paymentMethod == 'COD' ? 'selected' : ''}>COD</option>
                <option value="MoMo" ${order.paymentMethod == 'MoMo' ? 'selected' : ''}>Chuyển MoMo</option>
                <option value="VNPay" ${order.paymentMethod == 'VNPay' ? 'selected' : ''}>Chuyển VNPay</option>
            </select>
        </div>

        <hr>

        <!-- Delivery -->
        <h5 class="mb-3 fw-semibold">Giao hàng</h5>
        <c:choose>
            <c:when test="${delivery != null}">
                <div class="card p-3 mb-3 border-primary">
                    <!-- Shipper (bắt buộc) -->
                    <div class="mb-2">
                        <label>Shipper <span class="text-danger">*</span></label>
                        <select name="shipperId" class="form-select" required>
                            <option value="">-- Chọn Shipper --</option>
                            <c:forEach var="s" items="${shippers}">
                                <option value="${s.userId}" ${delivery.shipper != null && delivery.shipper.userId == s.userId ? 'selected' : ''}>${s.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Carrier (optional) -->
                    <div class="mb-2">
                        <label>Carrier</label>
                        <select name="carrierId" class="form-select">
                            <option value="">-- Chọn Carrier --</option>
                            <c:forEach var="c" items="${carriers}">
                                <option value="${c.carrierId}" ${delivery.carrier != null && delivery.carrier.carrierId == c.carrierId ? 'selected' : ''}>${c.carrierName}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Status của Delivery -->
                    <div class="mb-2">
                        <label>Status</label>
                        <select name="deliveryStatus" class="form-select">
                            <option value="Đã gán" ${delivery.status == 'Đã gán' ? 'selected' : ''}>Đã gán</option>
                            <option value="Đang giao" ${delivery.status == 'Đang giao' ? 'selected' : ''}>Đang giao</option>
                            <option value="Đã giao" ${delivery.status == 'Đã giao' ? 'selected' : ''}>Đã giao</option>
                            <option value="Đã hủy" ${delivery.status == 'Đã hủy' ? 'selected' : ''}>Đã hủy</option>
                        </select>
                    </div>

                    <div class="mb-2">
                        <label>Ghi chú</label>
                        <textarea name="noteText" class="form-control">${delivery.noteText}</textarea>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-info">Chưa có Delivery cho đơn hàng này.</div>
            </c:otherwise>
        </c:choose>

        <!-- Nút quay lại & lưu -->
        <div class="d-flex justify-content-between">
            <a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-secondary">
                <i class="bi bi-arrow-left"></i> Quay lại
            </a>
            <button type="submit" class="btn btn-primary">
                <i class="bi bi-save"></i> Lưu
            </button>
        </div>
    </form>
</div>

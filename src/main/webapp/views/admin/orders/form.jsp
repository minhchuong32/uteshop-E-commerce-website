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

        <div class="card p-3 mb-4 shadow-sm">
            <h5 class="card-title fw-bold">Thông tin khách hàng</h5>
            <div class="mb-2">
                <label class="form-label small text-muted">Họ tên</label>
                <input type="text" class="form-control" value="${order.user.name}" readonly />
            </div>
            <div class="mb-2">
                <label class="form-label small text-muted">Email</label>
                <input type="text" class="form-control" value="${order.user.email}" readonly />
            </div>
             <div class="mb-2">
                <label class="form-label small text-muted">Số điện thoại</label>
                <input type="text" class="form-control" value="${order.user.phone}" readonly />
            </div>
        </div>

        <div class="card p-3 mb-4 shadow-sm">
            <h5 class="card-title fw-bold">Thông tin đơn hàng</h5>
            
            <div class="mb-3">
                <label for="shippingAddressId" class="form-label fw-bold">Địa chỉ giao hàng</label>
                <select name="shippingAddressId" id="shippingAddressId" class="form-select" required>
                    <option value="">-- Vui lòng chọn địa chỉ --</option>
                    <c:forEach var="addr" items="${shippingAddresses}">
                        <option value="${addr.addressId}" ${order.shippingAddress.addressId == addr.addressId ? 'selected' : ''}>
                            ${addr.recipientName} - ${addr.phoneNumber} - ${addr.addressLine}, ${addr.ward}, ${addr.district}, ${addr.city}
                        </option>
                    </c:forEach>
                </select>
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
        </div>

        <div class="card p-3 mb-4 shadow-sm">
            <h5 class="card-title fw-bold">Thông tin giao hàng</h5>
            <c:choose>
                <c:when test="${delivery != null}">
                    <div class="mb-3">
                        <label for="shipperId" class="form-label">Shipper <span class="text-danger">*</span></label>
                        <select name="shipperId" id="shipperId" class="form-select" required>
                            <option value="">-- Chọn Shipper --</option>
                            <c:forEach var="s" items="${shippers}">
                                <option value="${s.userId}" ${delivery.shipper != null && delivery.shipper.userId == s.userId ? 'selected' : ''}>${s.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="carrierId" class="form-label">Hãng vận chuyển</label>
                        <select name="carrierId" id="carrierId" class="form-select">
                            <option value="">-- Không chọn --</option>
                            <c:forEach var="c" items="${carriers}">
                                <option value="${c.carrierId}" ${delivery.carrier != null && delivery.carrier.carrierId == c.carrierId ? 'selected' : ''}>${c.carrierName}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="deliveryStatus" class="form-label">Trạng thái giao hàng</label>
                        <select name="deliveryStatus" id="deliveryStatus" class="form-select">
                        	<option value="Chờ xử lý" ${delivery.status == 'Chờ xử lý' ? 'selected' : ''}>Chờ xử lý</option>
                            <option value="Đã gán" ${delivery.status == 'Đã gán' ? 'selected' : ''}>Đã gán</option>
                            <option value="Đang giao" ${delivery.status == 'Đang giao' ? 'selected' : ''}>Đang giao</option>
                            <option value="Đã giao" ${delivery.status == 'Đã giao' ? 'selected' : ''}>Đã giao</option>
                            <option value="Đã hủy" ${delivery.status == 'Đã hủy' ? 'selected' : ''}>Đã hủy</option>
                        </select>
                    </div>

                    <div class="mb-2">
                        <label for="noteText" class="form-label">Ghi chú</label>
                        <textarea name="noteText" id="noteText" class="form-control" rows="3">${delivery.noteText}</textarea>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-info">Chưa có thông tin giao hàng cho đơn hàng này.</div>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="d-flex justify-content-between mt-4">
            <a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-secondary">
                <i class="bi bi-arrow-left"></i> Quay lại danh sách
            </a>
            <button type="submit" class="btn btn-primary">
                <i class="bi bi-save"></i> Lưu thay đổi
            </button>
        </div>
    </form>
</div>
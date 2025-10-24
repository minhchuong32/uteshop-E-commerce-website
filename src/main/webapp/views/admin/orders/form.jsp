<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid px-4">
	<form action="${pageContext.request.contextPath}/admin/orders/edit" method="post">
		<input type="hidden" name="orderId" value="${order.orderId}" />
		<c:if test="${delivery != null}">
			<input type="hidden" name="deliveryId" value="${delivery.deliveryId}" />
		</c:if>

		<div class="d-flex justify-content-between align-items-center mb-4">
			<h3 class="text-primary-custom fw-bold mb-0">
				<i class="bi bi-pencil-square me-2"></i> Chỉnh sửa đơn hàng #${order.orderId}
			</h3>
			<div class="d-flex gap-2">
				<a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-outline-secondary">
					<i class="bi bi-arrow-left me-1"></i> Quay lại
				</a>
				<button type="submit" class="btn btn-primary-custom">
					<i class="bi bi-save-fill me-1"></i> Lưu thay đổi
				</button>
			</div>
		</div>

		<div class="row">
			<div class="col-lg-8">
				<div class="card shadow-sm mb-4">
					<div class="card-header bg-primary-custom text-white">
						<h5 class="mb-0">Thông tin đơn hàng</h5>
					</div>
					<div class="card-body">
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
						<div class="row">
							<div class="col-md-6 mb-3 mb-md-0">
								<label class="form-label fw-bold">Trạng thái đơn hàng</label>
								<select name="status" class="form-select">
									<option value="Mới" ${order.status == 'Mới' ? 'selected' : ''}>Mới</option>
									<option value="Đang giao" ${order.status == 'Đang giao' ? 'selected' : ''}>Đang giao</option>
									<option value="Đã giao" ${order.status == 'Đã giao' ? 'selected' : ''}>Đã giao</option>
									<option value="Đã hủy" ${order.status == 'Đã hủy' ? 'selected' : ''}>Đã hủy</option>
								</select>
							</div>
							<div class="col-md-6">
								<label class="form-label fw-bold">Phương thức thanh toán</label>
								<select name="paymentMethod" class="form-select">
									<option value="COD" ${order.paymentMethod == 'COD' ? 'selected' : ''}>Thanh toán khi nhận hàng (COD)</option>
									<option value="MoMo" ${order.paymentMethod == 'MoMo' ? 'selected' : ''}>Ví điện tử MoMo</option>
									<option value="VNPay" ${order.paymentMethod == 'VNPay' ? 'selected' : ''}>Cổng thanh toán VNPay</option>
								</select>
							</div>
						</div>
					</div>
				</div>

				<div class="card shadow-sm">
					<div class="card-header bg-primary-custom text-white">
						<h5 class="mb-0">Thông tin giao hàng</h5>
					</div>
					<div class="card-body">
						<c:choose>
							<c:when test="${delivery != null}">
								<div class="row">
									<div class="col-md-6 mb-3">
										<label for="shipperId" class="form-label fw-bold">Shipper</label>
										<select name="shipperId" id="shipperId" class="form-select" required>
											<option value="">-- Chọn Shipper --</option>
											<c:forEach var="s" items="${shippers}">
												<option value="${s.userId}" ${delivery.shipper != null && delivery.shipper.userId == s.userId ? 'selected' : ''}>${s.name}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-6 mb-3">
										<label for="deliveryStatus" class="form-label fw-bold">Trạng thái giao hàng</label>
										<select name="deliveryStatus" id="deliveryStatus" class="form-select">
											<option value="Chờ xử lý" ${delivery.status == 'Chờ xử lý' ? 'selected' : ''}>Chờ xử lý</option>
											<option value="Đã gán" ${delivery.status == 'Đã gán' ? 'selected' : ''}>Đã gán</option>
											<option value="Đang giao" ${delivery.status == 'Đang giao' ? 'selected' : ''}>Đang giao</option>
											<option value="Đã giao" ${delivery.status == 'Đã giao' ? 'selected' : ''}>Đã giao</option>
											<option value="Đã hủy" ${delivery.status == 'Đã hủy' ? 'selected' : ''}>Đã hủy</option>
										</select>
									</div>
								</div>
                                <div class="mb-3">
									<label for="carrierId" class="form-label fw-bold">Hãng vận chuyển</label>
									<select name="carrierId" id="carrierId" class="form-select">
										<option value="">-- Không chọn --</option>
										<c:forEach var="c" items="${carriers}">
											<option value="${c.carrierId}" ${delivery.carrier != null && delivery.carrier.carrierId == c.carrierId ? 'selected' : ''}>${c.carrierName}</option>
										</c:forEach>
									</select>
								</div>
								<div>
									<label for="noteText" class="form-label fw-bold">Ghi chú</label>
									<textarea name="noteText" id="noteText" class="form-control" rows="3">${delivery.noteText}</textarea>
								</div>
							</c:when>
							<c:otherwise>
								<div class="alert alert-info mb-0">Chưa có thông tin giao hàng cho đơn hàng này.</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>

			<div class="col-lg-4">
				<div class="card shadow-sm">
					<div class="card-header bg-primary-custom text-white">
						<h5 class="mb-0">Thông tin khách hàng</h5>
					</div>
					<div class="card-body">
                        <dl class="row mb-0">
							<dt class="col-sm-4">Họ tên</dt>
							<dd class="col-sm-8">${order.user.name}</dd>
							<dt class="col-sm-4">Email</dt>
							<dd class="col-sm-8">${order.user.email}</dd>
							<dt class="col-sm-4">SĐT</dt>
							<dd class="col-sm-8">${order.user.phone}</dd>
						</dl>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
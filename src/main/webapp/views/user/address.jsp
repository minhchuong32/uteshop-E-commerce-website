<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglib.jsp" %>

<div class="container mt-5">
    <h3 class="mb-3">Địa chỉ giao hàng của bạn</h3>

    <!-- 🟢 Form thêm mới -->
    <form action="${pageContext.request.contextPath}/user/address" method="post" class="mb-4">
        <input type="hidden" name="action" value="add">
        <div class="row g-2">
            <div class="col-md-4">
                <input type="text" name="recipientName" class="form-control" placeholder="Tên người nhận" required>
            </div>
            <div class="col-md-3">
                <input type="text" name="phone" class="form-control" placeholder="Số điện thoại" required>
            </div>
            <div class="col-md-5">
                <input type="text" name="addressLine" class="form-control" placeholder="Địa chỉ cụ thể" required>
            </div>
            <div class="col-md-3">
                <input type="text" name="ward" class="form-control" placeholder="Phường/xã">
            </div>
            <div class="col-md-3">
                <input type="text" name="district" class="form-control" placeholder="Quận/huyện">
            </div>
            <div class="col-md-3">
                <input type="text" name="city" class="form-control" placeholder="Thành phố">
            </div>
            <div class="col-md-3 d-flex align-items-center">
                <input type="checkbox" name="isDefault" class="form-check-input me-2"> Đặt làm mặc định
            </div>
        </div>
        <button class="btn btn-primary mt-3">Thêm địa chỉ</button>
    </form>

    <!-- 🟡 Bảng danh sách -->
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Người nhận</th>
                <th>Điện thoại</th>
                <th>Địa chỉ</th>
                <th>Mặc định</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="a" items="${addresses}">
                <tr>
                    <td>${a.recipientName}</td>
                    <td class="text-center">${a.phoneNumber}</td>
                    <td>${a.addressLine}, ${a.ward}, ${a.district}, ${a.city}</td>
                    <td class="text-center"><c:if test="${a.isDefault}">✅</c:if></td>
                    <td class="text-center">
                        <!-- Nút sửa -->
                        <button type="button" class="btn btn-sm btn-warning"
                            onclick="showEditForm('${a.addressId}', '${a.recipientName}', '${a.phoneNumber}', '${a.addressLine}', '${a.ward}', '${a.district}', '${a.city}', '${a.isDefault}')">
                            ✏️ Sửa
                        </button>

                        <!-- Nút xóa -->
                        <form action="${pageContext.request.contextPath}/user/address" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="${a.addressId}">
                            <button class="btn btn-sm btn-danger" onclick="return confirm('Xóa địa chỉ này?')">Xóa</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- 🟣 Form sửa địa chỉ (ẩn) -->
    <div id="editFormContainer" class="card mt-4 mb-4" style="display:none;">
        <div class="card-body">
            <h5 class="card-title mb-3">Sửa địa chỉ</h5>
            <form id="editForm" action="${pageContext.request.contextPath}/user/address" method="post">
                <input type="hidden" name="action" value="edit">
                <input type="hidden" id="editAddressId" name="addressId">

                <div class="row g-2">
                    <div class="col-md-4">
                        <input type="text" id="editRecipientName" name="recipientName" class="form-control" placeholder="Tên người nhận" required>
                    </div>
                    <div class="col-md-3">
                        <input type="text" id="editPhone" name="phone" class="form-control" placeholder="Số điện thoại" required>
                    </div>
                    <div class="col-md-5">
                        <input type="text" id="editAddressLine" name="addressLine" class="form-control" placeholder="Địa chỉ cụ thể" required>
                    </div>
                    <div class="col-md-3">
                        <input type="text" id="editWard" name="ward" class="form-control" placeholder="Phường/xã">
                    </div>
                    <div class="col-md-3">
                        <input type="text" id="editDistrict" name="district" class="form-control" placeholder="Quận/huyện">
                    </div>
                    <div class="col-md-3">
                        <input type="text" id="editCity" name="city" class="form-control" placeholder="Thành phố">
                    </div>
                    <div class="col-md-3 d-flex align-items-center">
                        <input type="checkbox" id="editIsDefault" name="isDefault" class="form-check-input me-2"> Đặt làm mặc định
                    </div>
                </div>

                <div class="mt-3">
                    <button type="submit" class="btn btn-success">💾 Lưu thay đổi</button>
                    <button type="button" class="btn btn-secondary" onclick="cancelEdit()">Hủy</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/assets/js/user/address.js"></script>


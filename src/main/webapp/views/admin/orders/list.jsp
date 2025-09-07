<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">
    <h3 class="mb-4 fw-bold">Quản lý đơn hàng</h3>

    <!-- Nút thêm mới -->
    <div class="mb-3">
        <a href="${pageContext.request.contextPath}/admin/orders/add"
           class="btn btn-success"> 
            <i class="bi bi-cart-plus me-2"></i> Thêm đơn hàng
        </a>
    </div>

    <div class="card shadow-sm">
        <div class="card-body">
            <table class="table align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>User ID</th>
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
                            <td>${o.orderId}</td>
                            <td>${o.userId}</td>
                            <td><fmt:formatNumber value="${o.totalAmount}" type="currency" currencySymbol="₫"/></td>
                            <td>${o.status}</td>
                            <td>${o.paymentMethod}</td>
                            <td>${o.createdAt}</td>
                            <td class="text-center">
                                <!-- Edit -->
                                <a href="${pageContext.request.contextPath}/admin/orders/edit?id=${o.orderId}"
                                   class="text-warning me-3" title="Sửa">
                                   <i class="bi bi-pencil-square"></i>
                                </a> 

                                <!-- Delete -->
                                <a href="javascript:void(0);"
                                   class="text-danger me-3"
                                   data-bs-toggle="modal"
                                   data-bs-target="#confirmDeleteModal"
                                   data-id="${o.orderId}"
                                   data-url="${pageContext.request.contextPath}/admin/orders/delete"
                                   title="Xóa">
                                   <i class="bi bi-trash-fill"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Modal Xác nhận Xóa -->
<div class="modal fade" id="confirmDeleteModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content shadow-lg border-0 rounded-3">
            <div class="modal-header bg-danger text-white">
                <h5 class="modal-title">
                    <i class="bi bi-exclamation-triangle-fill me-2"></i> Xác nhận xóa
                </h5>
                <button type="button" class="btn-close btn-close-white"
                    data-bs-dismiss="modal" aria-label="Đóng"></button>
            </div>
            <div class="modal-body">
                <p>Bạn có chắc muốn xóa đơn hàng này không? 
                   <strong>Hành động này không thể hoàn tác</strong>.
                </p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary rounded-pill px-4"
                    data-bs-dismiss="modal">Hủy</button>
                <a id="deleteConfirmBtn" href="#" class="btn btn-danger rounded-pill px-4">Xóa</a>
            </div>
        </div>
    </div>
</div>

<!-- Import JS -->
<script src="${pageContext.request.contextPath}/assets/js/admin/modal-delete.js"></script>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container py-5">
    <h4 class="fw-semibold mb-4">Danh sách khiếu nại của bạn</h4>

    <div class="table-responsive">
        <table class="table table-bordered align-middle text-center">
            <thead class="table-light">
                <tr>
                    <th>Mã</th>
                    <th>Đơn hàng</th>
                    <th>Tiêu đề</th>
                    <th>Trạng thái</th>
                    <th>Ngày tạo</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="c" items="${complaints}">
                    <tr>
                        <td>${c.complaintId}</td>
                        <td>#${c.order.orderId}</td>
                        <td class="text-start">${c.title}</td>
                        <td><span class="badge bg-info">${c.status}</span></td>
                        <td>${c.createdAt}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="text-center mt-3">
        <a href="${pageContext.request.contextPath}/user/orders"
           class="btn btn-outline-primary rounded-pill px-4">
            <i class="bi bi-arrow-left me-2"></i> Quay lại đơn hàng
        </a>
    </div>
</div>

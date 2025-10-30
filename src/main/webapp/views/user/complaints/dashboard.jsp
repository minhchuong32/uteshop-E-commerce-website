<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglib.jsp" %>

<div class="container py-5">
    <h4 class="fw-semibold mb-4">Danh sách khiếu nại của bạn</h4>

    <div class="table-responsive">
        <table class="table table-bordered table-hover align-middle text-center">
            <thead class="table-light">
                <tr>
                    <th>Mã</th>
                    <th>Đơn hàng</th>
                    <th>Tiêu đề</th>
                    <th>Trạng thái</th>
                    <th>Ngày tạo</th>
                    <th style="width: 120px;">Hành động</th> 
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty complaints}">
                    <tr>
                        <td colspan="6" class="text-center p-4">Bạn chưa có khiếu nại nào.</td>
                    </tr>
                </c:if>
                <c:forEach var="c" items="${complaints}">
                    <tr>
                        <td>#${c.complaintId}</td>
                        <td>#${c.order.orderId}</td>
                        <td class="text-start">${c.title}</td>
                        <td>
                            <c:choose>
                                <c:when test="${c.status == 'Đã giải quyết'}">
                                    <span class="badge bg-success">${c.status}</span>
                                </c:when>
                                <c:when test="${c.status == 'Đang xử lý'}">
                                    <span class="badge bg-warning text-dark">${c.status}</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-info">${c.status}</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><fmt:formatDate value="${c.createdAt}" pattern="dd/MM/yyyy HH:mm" /></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/user/complaints/chat?complaintId=${c.complaintId}"
                               class="btn btn-primary btn-sm" title="Trao đổi với Admin">
                                <i class="bi bi-chat-dots-fill"></i> Chat
                            </a>
                        </td>
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
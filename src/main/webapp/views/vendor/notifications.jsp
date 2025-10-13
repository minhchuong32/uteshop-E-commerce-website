<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglib.jsp" %>

<div class="container py-5">
    <h3 class="mb-4 text-center text-primary fw-bold">
        <i class="bi bi-bell-fill me-2"></i>Thông báo của nhà bán hàng
    </h3>

    <c:choose>
        <c:when test="${empty notifications}">
            <div class="text-center text-muted py-5">
                <i class="bi bi-bell-slash fs-2"></i>
                <p>Bạn chưa có thông báo nào.</p>
            </div>
        </c:when>

        <c:otherwise>
            <c:forEach var="n" items="${notifications}">
                <div class="card mb-3 shadow-sm ${n.read ? '' : 'border-warning border-2'}">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <h6 class="fw-bold mb-0 text-dark">${n.message}</h6>
                            <small class="text-muted">
                                <fmt:formatDate value="${n.createdAt}" pattern="dd/MM/yyyy HH:mm" />
                            </small>
                        </div>

                        <!-- Nút xem chi tiết -->
                        <div class="mt-2">
                            <button class="btn btn-sm btn-outline-warning" type="button"
                                    data-bs-toggle="collapse"
                                    data-bs-target="#collapse${n.id}"
                                    aria-expanded="false"
                                    aria-controls="collapse${n.id}">
                                <i class="bi bi-eye-fill"></i> Xem chi tiết
                            </button>
                        </div>

                        <!-- Collapse chi tiết -->
                        <div class="collapse mt-3 border-top pt-3" id="collapse${n.id}">
                            <c:if test="${n.relatedOrder != null}">
                                <p><strong>Liên quan đơn hàng:</strong> #${n.relatedOrder.orderId}</p>
                                <p><strong>Trạng thái:</strong> ${n.relatedOrder.status}</p>
                                <a href="${pageContext.request.contextPath}/vendor/orders/detail?id=${n.relatedOrder.orderId}"
                                   class="btn btn-sm btn-primary">
                                   <i class="bi bi-box-seam"></i> Xem đơn hàng
                                </a>
                            </c:if>

                            <c:if test="${n.relatedComplaint != null}">
                                <div class="mt-3">
                                    <p><strong>Khiếu nại:</strong> ${n.relatedComplaint.title}</p>
                                    <p>${n.relatedComplaint.content}</p>
                                    <a href="${pageContext.request.contextPath}/vendor/chat?complaintId=${n.relatedComplaint.complaintId}"
                                       class="btn btn-sm btn-success">
                                        <i class="bi bi-chat-dots-fill"></i> Chat với khách hàng
                                    </a>
                                </div>
                            </c:if>

                            <c:if test="${n.relatedOrder == null && n.relatedComplaint == null}">
                                <div class="alert alert-warning mb-0">
                                    <i class="bi bi-exclamation-triangle-fill"></i>
                                    Thông báo này không còn liên kết với dữ liệu nào.
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>

    <div class="text-center mt-4">
        <a href="${pageContext.request.contextPath}/vendor/home" class="btn btn-outline-secondary">
            <i class="bi bi-arrow-left"></i> Quay lại trang chính
        </a>
    </div>
</div>
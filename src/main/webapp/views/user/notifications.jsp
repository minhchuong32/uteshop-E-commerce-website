<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglib.jsp" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thông báo của tôi | UteShop</title>
</head>
<body class="bg-light">

<%-- BỌC TOÀN BỘ NỘI DUNG CHÍNH BẰNG main-content --%>
<div class="main-content">
    <div class="container py-5">
        <h3 class="mb-4 text-center text-primary-custom fw-bold">
            <i class="bi bi-bell-fill me-2"></i>Thông báo của bạn
        </h3>
            
        <c:choose>
            <%-- ĐÃ SỬA: Dùng biến ${notis} để kiểm tra --%>
            <c:when test="${empty notis}">
                <div class="text-center text-muted py-5">
                    <i class="bi bi-bell-slash fs-2"></i>
                    <p>Hiện bạn chưa có thông báo nào.</p>
                </div>
            </c:when>

            <c:otherwise>
                <%-- Giữ nguyên vòng lặp ${notis} của bạn --%>
                <c:forEach var="n" items="${notis}">
                    <div class="card mb-3 shadow-sm ${n.read ? '' : 'border-primary border-2'}">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center">
                                <h6 class="fw-bold mb-0 text-primary-custom">${n.message}</h6>
                                <small class="text-muted">
                                    <fmt:formatDate value="${n.createdAt}" pattern="dd/MM/yyyy HH:mm" />
                                </small>
                            </div>

                            <!-- Nút xem chi tiết -->
                            <div class="mt-2">
                                <button class="btn btn-sm btn-outline-primary" type="button"
                                        data-bs-toggle="collapse"
                                        data-bs-target="#collapse${n.id}"
                                        aria-expanded="false"
                                        aria-controls="collapse${n.id}">
                                    <i class="bi bi-eye"></i> Xem chi tiết
                                </button>
                            </div>

                            <!-- Collapse chi tiết -->
                            <div class="collapse mt-3 border-top pt-3" id="collapse${n.id}">
                                <c:if test="${n.relatedComplaint != null}">
                                    <h6 class=" text-primary-custom-custom mb-2">
                                        <i class="bi bi-chat-left-text"></i> Chi tiết khiếu nại:
                                        ${n.relatedComplaint.title}
                                    </h6>
                                    <p class="mb-2">${n.relatedComplaint.content}</p>
                                    <p>
                                        <strong>Trạng thái:</strong> ${n.relatedComplaint.status}
                                    </p>

                                    <div class="bg-light rounded p-3"
                                         style="max-height: 300px; overflow-y: auto;">
                                        <c:forEach var="m" items="${n.relatedComplaint.messages}">
                                            <div class="mb-2">
                                                <strong class="${m.sender.role eq 'Admin' ? 'text-danger' : 'text-primary-custom'}">
                                                    ${m.sender.username}:
                                                </strong>
                                                <span>${m.content}</span><br>
                                                <small class="text-muted">
                                                    <fmt:formatDate value="${m.createdAt}" pattern="dd/MM/yyyy HH:mm" />
                                                </small>
                                            </div>
                                        </c:forEach>
                                    </div>

                                    <!-- Nút chat realtime -->
                                    <div class="text-end mt-3">
                                        <a href="${pageContext.request.contextPath}/user/chat?complaintId=${n.relatedComplaint.complaintId}"
                                           class="btn btn-sm btn-success">
                                            <i class="bi bi-chat-dots-fill"></i> Chat với Admin
                                        </a>
                                    </div>
                                </c:if>

                                <c:if test="${n.relatedComplaint == null}">
                                    <div class="alert alert-warning mb-0">
                                        <i class="bi bi-exclamation-triangle-fill"></i>
                                        Thông báo này không còn liên kết với khiếu nại nào.
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>

        <div class="text-center mt-4">
            <a href="${pageContext.request.contextPath}/user/home" class="btn btn-outline-secondary">
                <i class="bi bi-arrow-left"></i> Quay lại trang chủ
            </a>
        </div>
    </div>
</div> <%-- Đóng thẻ .main-content --%>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
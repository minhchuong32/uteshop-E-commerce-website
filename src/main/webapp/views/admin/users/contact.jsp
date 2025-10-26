<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglib.jsp" %>

<%--
    Form "Gửi Tin Nhắn Tới Người Dùng"
    Đã được cập nhật để sử dụng cấu trúc giao diện giống như trang "Phản hồi Liên hệ".
    - Sử dụng container-fluid
    - Sử dụng label và input tiêu chuẩn (thay vì floating label)
--%>

<div class="container-fluid px-4 mt-4">
    <div class="card shadow-sm border-0">
        
        <%-- Header của card --%>
        <div class="card-header bg-primary-custom">
            <h4 class="mb-0 text-white">
                <i class="bi bi-envelope-fill me-2"></i> Gửi Tin Nhắn Tới Người Dùng
            </h4>
        </div>

        <div class="card-body p-4">
            
            <form action="${pageContext.request.contextPath}/admin/users/contact" method="post">
                <%-- Truyền ID người dùng ẩn --%>
                <input type="hidden" name="id" value="${user.userId}" />

                <%-- Thông tin người nhận --%>
                <div class="mb-3">
                    <label for="recipient" class="form-label fw-bold">Gửi tới:</label>
                    <input type="text" class="form-control" id="recipient" 
                           value="${user.name} (${user.email})" disabled readonly 
                           style="background-color: #e9ecef;">
                </div>

                <%-- Chủ đề --%>
                <div class="mb-3">
                    <label for="subject" class="form-label fw-bold">Chủ đề:</label>
                    <input type="text" class="form-control" id="subject" name="subject" 
                           placeholder="Nhập chủ đề email..." required>
                </div>

                <%-- Nội dung --%>
                <div class="mb-3">
                    <label for="message" class="form-label fw-bold">Nội dung tin nhắn:</label>
                    <textarea class="form-control" id="message" name="message" rows="8" 
                              placeholder="Nhập nội dung bạn muốn gửi..." required></textarea>
                </div>

                <hr class="mt-4" style="opacity: 0.2;">

                <%-- Các nút hành động --%>
                <div class="d-flex justify-content-between align-items-center mt-3">
                    <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-outline-secondary">
                        <i class="bi bi-arrow-left me-1"></i> Quay lại
                    </a>
                    <button type="submit" class="btn btn-primary-custom">
                        <i class="bi bi-send-fill me-1"></i> Gửi Ngay
                    </button>
                </div>
            </form>
            
        </div>
    </div>
</div>
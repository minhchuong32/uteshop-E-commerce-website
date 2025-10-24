<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid px-4">
    <div class="card shadow-sm border-0">
        <div class="card-header bg-primary-custom">
            <h4 class="mb-0 text-white"><i class="bi bi-send-fill me-2"></i>Phản hồi Liên hệ</h4>
        </div>

        <div class="card-body p-4">
            <%-- ======================== ORIGINAL MESSAGE DISPLAY ======================== --%>
            <div class="card mb-4 border-light" style="background-color: #f8f9fa;">
                <div class="card-header bg-transparent border-bottom-0 pt-3">
                    <h5 class="mb-0 text-dark-emphasis">Nội dung từ Khách hàng</h5>
                </div>
                <div class="card-body">
                    <p class="card-text">
                        <strong>Từ:</strong> <c:out value="${contact.fullName}"/> (<c:out value="${contact.email}"/>)
                    </p>
                    <hr>
                    <p class="card-text fw-bold">Nội dung:</p>
                    <blockquote class="blockquote bg-white p-3 rounded border" style="font-size: 0.95rem;">
                        <p class="mb-0" style="white-space: pre-wrap;"><c:out value="${contact.content}"/></p>
                    </blockquote>
                </div>
            </div>

            <%-- ======================== REPLY FORM ======================== --%>
            <form action="${pageContext.request.contextPath}/admin/contacts/reply" method="post">
                <%-- Hidden input để gửi email người nhận cho controller --%>
                <input type="hidden" name="email" value="${contact.email}">

                <div class="mb-3">
                    <label for="toEmail" class="form-label fw-bold">Gửi đến</label>
                    <input type="email" class="form-control" id="toEmail" value="${contact.email}" disabled readonly>
                </div>

                <div class="mb-3">
                    <label for="subject" class="form-label fw-bold">Chủ đề</label>
                    <input type="text" class="form-control" id="subject" name="subject" value="Re: Phản hồi từ UteShop" required>
                </div>

                <div class="mb-3">
                    <label for="body" class="form-label fw-bold">Nội dung phản hồi</label>
                    <textarea class="form-control" id="body" name="body" rows="10" required placeholder="Nhập nội dung phản hồi của bạn tại đây..."></textarea>
                </div>

                <hr class="hr-primary mt-4" style="opacity: 0.2;">

                <%-- ======================== ACTION BUTTONS ======================== --%>
                <div class="d-flex justify-content-end gap-2 mt-3">
                    <a href="${pageContext.request.contextPath}/admin/contacts" class="btn btn-outline-secondary">
                        <i class="bi bi-arrow-left me-1"></i> Quay lại
                    </a>
                    <button type="submit" class="btn btn-primary-custom">
                        <i class="bi bi-send me-1"></i> Gửi Phản hồi
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
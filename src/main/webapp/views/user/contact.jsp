<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container mt-5 mb-5">
    <h2 class="fw-bold text-center mb-4">Liên hệ với chúng tôi</h2>

    <!-- Thông báo -->
    <c:if test="${not empty success}">
        <div class="alert alert-success text-center">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger text-center">${error}</div>
    </c:if>

    <div class="row g-4">
        <!-- Thông tin cửa hàng (lấy từ StoreSettings) -->
        <div class="col-md-5">
            <div class="card shadow-sm rounded-3">
                <div class="card-body">
                    <h5 class="fw-bold mb-3">Thông tin cửa hàng</h5>
                    <p class="mb-1"><strong>Tên:</strong> <c:out value="${store.storeName}" /></p>
                    <p class="mb-1"><strong>Hotline:</strong> <c:out value="${store.hotline}" /></p>
                    <p class="mb-1"><strong>Email:</strong> <c:out value="${store.email}" /></p>
                    <p class="mb-0"><strong>Địa chỉ:</strong> <c:out value="${store.address}" /></p>
                </div>
            </div>
        </div>

        <!-- Form liên hệ -->
        <div class="col-md-7">
            <div class="card shadow-sm rounded-3">
                <div class="card-body">
                    <h5 class="fw-bold mb-3">Gửi tin nhắn</h5>
                    <form action="${pageContext.request.contextPath}/user/contact" method="post">
                        <div class="mb-3">
                            <label class="form-label">Họ và tên</label>
                            <input type="text" class="form-control" name="fullname" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" class="form-control" name="email" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Nội dung</label>
                            <textarea class="form-control" name="message" rows="4" required></textarea>
                        </div>
                        <div class="text-end">
                            <button type="submit" class="btn btn-primary">Gửi liên hệ</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Google Map theo StoreSettings -->
    <div class="row mt-5">
        <div class="col-12">
            <h5 class="fw-bold mb-3">Bản đồ</h5>
            <div class="ratio ratio-16x9">
                <iframe src="${mapEmbedUrl}" width="100%" height="450" style="border:0;" allowfullscreen="" loading="lazy"></iframe>
            </div>
        </div>
    </div>
</div>

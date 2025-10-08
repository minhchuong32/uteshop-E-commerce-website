<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>
<h3 class="mb-3">Cập nhật trạng thái khiếu nại</h3>

<form action="${pageContext.request.contextPath}/admin/complaints" method="post">
    <input type="hidden" name="complaintId" value="${complaint.complaintId}" />

    <div class="mb-3">
        <label class="form-label">Tiêu đề</label>
        <input type="text" class="form-control" value="${complaint.title}" readonly />
    </div>

    <div class="mb-3">
        <label class="form-label">Nội dung</label>
        <textarea class="form-control" rows="5" readonly>${complaint.content}</textarea>
    </div>

    <div class="mb-3">
        <label class="form-label">Trạng thái</label>
        <select class="form-select" name="status">
            <option ${complaint.status eq 'Chờ xử lý' ? 'selected' : ''}>Chờ xử lý</option>
            <option ${complaint.status eq 'Đang xử lý' ? 'selected' : ''}>Đang xử lý</option>
            <option ${complaint.status eq 'Đã giải quyết' ? 'selected' : ''}>Đã giải quyết</option>
        </select>
    </div>

    <button class="btn btn-primary">Lưu thay đổi</button>
    <a href="${pageContext.request.contextPath}/admin/complaints" class="btn btn-secondary">Quay lại</a>
</form>

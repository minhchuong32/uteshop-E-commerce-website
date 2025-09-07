<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container-fluid">
    <h3 class="mb-4 fw-bold">Chỉnh sửa cửa hàng</h3>

    <div class="card shadow-sm">
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/admin/shops/edit" method="post">
                <input type="hidden" name="id" value="${shop.shopId}">

                <div class="mb-3">
                    <label for="user_id" class="form-label">User ID</label>
                    <input type="number" class="form-control" id="user_id" name="user_id" value="${shop.userId}" readonly>
                </div>

                <div class="mb-3">
                    <label for="name" class="form-label">Tên cửa hàng</label>
                    <input type="text" class="form-control" id="name" name="name" value="${shop.name}" required>
                </div>

                <div class="mb-3">
                    <label for="description" class="form-label">Mô tả</label>
                    <textarea class="form-control" id="description" name="description" rows="4" required>${shop.description}</textarea>
                </div>

                <button type="submit" class="btn btn-warning rounded-pill px-4">Cập nhật</button>
                <a href="${pageContext.request.contextPath}/admin/shops" class="btn btn-secondary rounded-pill px-4">Hủy</a>
            </form>
        </div>
    </div>
</div>

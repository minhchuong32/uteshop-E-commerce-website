<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container-fluid">
	<h3 class="mb-4 fw-bold">Quản lý người dùng</h3>

	<!-- Nút thêm mới -->
	<div class="mb-3">
		<a href="${pageContext.request.contextPath}/admin/users/add"
			class="btn btn-success"> <i class="bi bi-person-plus-fill me-2"></i>
			Thêm người dùng
		</a>
	</div>

	<div class="card shadow-sm">
		<div class="card-body">
			<table class="table align-middle mb-0">
				<thead class="table-light">
					<tr>
						<th>Người dùng</th>
						<th>Vai trò</th>
						<th>Trạng thái</th>
						<th>Ngày tham gia</th>
						<th class="text-center">Hành động</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="u" items="${users}">
						<tr>
							<!-- Tên + email -->
							<td>
								<div class="fw-bold">${u.username}</div> <small
								class="text-muted">${u.email}</small>
							</td>

							<!-- Vai trò -->
							<td><c:choose>
									<c:when test="${u.role eq 'Admin'}">
										<span class="badge rounded-pill bg-dark text-white">Admin</span>
									</c:when>
									<c:when test="${u.role eq 'Vendor'}">
										<span
											class="badge rounded-pill bg-primary-subtle text-primary">Vendor</span>
									</c:when>
									<c:when test="${u.role eq 'Shipper'}">
										<span
											class="badge rounded-pill bg-warning-subtle text-warning">Shipper</span>
									</c:when>
									<c:otherwise>
										<span class="badge rounded-pill bg-light text-dark">User</span>
									</c:otherwise>
								</c:choose></td>

							<!-- Trạng thái -->
							<td><c:choose>
									<c:when test="${u.status eq 'active'}">
										<span
											class="badge rounded-pill bg-success-subtle text-success">active</span>
									</c:when>
									<c:otherwise>
										<span class="badge rounded-pill bg-danger-subtle text-danger">blocked</span>
									</c:otherwise>
								</c:choose></td>

							<!-- Ngày tham gia -->
							<td>${u.createDate}</td>

							<!-- Action -->
							<td class="text-center">
								<!-- Xem chi tiết --> <a
								href="${pageContext.request.contextPath}/admin/view?id=${u.user_id}"
								class="text-primary me-3" title="Xem chi tiết"> <i
									class="bi bi-eye-fill"></i>
							</a> <!-- Sửa --> <a
								href="${pageContext.request.contextPath}/admin/users/edit?id=${u.user_id}"
								class="text-warning me-3" title="Sửa"> <i
									class="bi bi-pencil-square"></i>
							</a> <!-- Xóa --> <a
								href="${pageContext.request.contextPath}/admin/users/delete?id=${u.user_id}"
								class="text-danger me-3"
								onclick="return confirm('Bạn có chắc muốn xóa người dùng này?')"
								title="Xóa"> <i class="bi bi-trash-fill"></i>
							</a> <!-- Khóa/Mở khóa --> <c:choose>
									<c:when test="${u.status eq 'active'}">
										<a
											href="${pageContext.request.contextPath}/admin/users/lock?id=${u.user_id}"
											class="text-danger" title="Khóa tài khoản"> <i
											class="bi bi-lock-fill"></i>
										</a>
									</c:when>
									<c:otherwise>
										<a
											href="${pageContext.request.contextPath}/admin/users/unlock?id=${u.user_id}"
											class="text-success" title="Mở khóa tài khoản"> <i
											class="bi bi-unlock-fill"></i>
										</a>
									</c:otherwise>
								</c:choose>

							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

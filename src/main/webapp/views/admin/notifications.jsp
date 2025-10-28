<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<div class="container py-4">

	<!-- ===== Tiêu đề ===== -->
	<h3 class="text-primary-custom mb-4">
		<i class="bi bi-bell-fill"></i> Danh sách thông báo
	</h3>

	<!-- ===== Bảng thông báo ===== -->
	<div class="table-responsive shadow-sm">
		<table class="table table-hover align-middle">
			<thead class="table-light">
				<tr>
					<th class="text-center">#</th>
					<%-- Sửa 2: Xóa cột "Tiêu đề" vì object không có thuộc tính này --%>
					<th>Nội dung</th>
					<th>Khiếu nại</th>
					<th>Trạng thái</th>
					<th>Thời gian</th>
					<th class="text-center">Hành động</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<%-- Sửa 3: Đồng bộ tên biến. Dùng "notifications" để kiểm tra --%>
					<c:when test="${not empty notifications}">
						<c:forEach var="n" items="${notifications}">
							<!-- Nếu chưa đọc thì tô nền vàng nhạt -->
							<tr class="${n.read ? '' : 'table-warning'}">
								<td class="text-center">${n.id}</td>
								<%-- Sửa 4: Xóa <td> của ${n.title} --%>
								<td>${n.message}</td>
								<td><c:if test="${n.relatedComplaint.complaintId != null}">
                                        #${n.relatedComplaint.complaintId}
                                    </c:if> <c:if
										test="${n.relatedComplaint.complaintId == null}">
										<span class="text-muted">Không có</span>
									</c:if></td>
								<td><c:choose>
										<c:when test="${n.read}">
											<span class="badge bg-success">Đã đọc</span>
										</c:when>
										<c:otherwise>
											<span class="badge bg-warning text-dark">Chưa đọc</span>
										</c:otherwise>
									</c:choose></td>
								<td><fmt:formatDate value="${n.createdAt}"
										pattern="dd/MM/yyyy HH:mm" /></td>
								<td class="text-center"><a
									href="${pageContext.request.contextPath}/admin/notifications/view?id=${n.id}"
									class="btn btn-sm btn-outline-primary-custom"> <i
										class="bi bi-chat-dots-fill"></i> Xem chi tiết
								</a></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<%-- Sửa 5: Cập nhật colspan="6" (vì đã xóa 1 cột) --%>
							<td colspan="6" class="text-center text-muted py-4"><i
								class="bi bi-inbox fs-3"></i><br> Không có thông báo nào.</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
</div>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">

<div class="container my-4">
	<a href="${pageContext.request.contextPath}/user/orders"
		class="btn btn-link mb-3">&larr; Quay lại lịch sử đơn hàng</a>

	<c:set var="isEdit" value="${not empty review}" />

	<div class="row">
		<div class="col-lg-8 mx-auto">
			<div class="card shadow-sm">
				<div class="card-body">
					<h4 class="card-title mb-3">
						<c:choose>
							<c:when test="${isEdit}">Chỉnh sửa đánh giá</c:when>
							<c:otherwise>Viết đánh giá</c:otherwise>
						</c:choose>
					</h4>

					<!-- ✅ Thông tin sản phẩm -->
					<div class="d-flex mb-3 align-items-center gap-3">
						<img
							src="${pageContext.request.contextPath}/assets/${product.imageUrl}"
							alt="${product.name}"
							style="width: 90px; height: 90px; object-fit: cover; border-radius: 6px; border: 1px solid #eee;">
						<div>
							<h6 class="mb-1">${product.name}</h6>
							<small class="text-muted">Shop: ${product.shop.name}</small>
							<div class="mt-1 text-muted">
								<small>⭐ <fmt:formatNumber
										value="${product.averageRating}" type="number"
										maxFractionDigits="1" /> (${product.reviewsCount} đánh giá)
								</small>
							</div>
						</div>
					</div>

					<!-- ✅ Thông báo -->
					<c:if test="${not empty successMessage}">
						<div class="alert alert-success">${successMessage}</div>
					</c:if>
					<c:if test="${not empty errorMessage}">
						<div class="alert alert-danger">${errorMessage}</div>
					</c:if>

					<!-- ✅ Form đánh giá -->
					<form method="post" id="reviewForm"
						action="${pageContext.request.contextPath}/user/review${isEdit ? '/edit' : '/add'}">

						<input type="hidden" name="productId" value="${product.productId}" />
						<c:if test="${isEdit}">
							<input type="hidden" name="reviewId" value="${review.reviewId}" />
						</c:if>

						<!-- Rating -->
						<div class="mb-3">
							<label class="form-label fw-bold">Đánh giá</label>
							<div class="d-flex align-items-center gap-2 flex-row-reverse">
								<c:forEach var="i" begin="1" end="5" step="1">
									<input type="radio" class="btn-check" name="rating" id="r${i}"
										value="${i}"
										${isEdit && review.rating == i ? 'checked' : (!isEdit && i==5 ? 'checked' : '')}>
									<label class="btn btn-outline-warning" for="r${i}"> <span
										style="font-size: 1.2rem;">&#9733;</span> ${i}
									</label>
								</c:forEach>
							</div>

						</div>

						<!-- Comment -->
						<div class="mb-3">
							<label for="comment" class="form-label fw-bold">Bình luận</label>
							<textarea id="comment" name="comment" rows="5"
								class="form-control"
								placeholder="Viết cảm nhận của bạn về sản phẩm...">${isEdit ? review.comment : ''}</textarea>
						</div>

						<!-- Media -->
						<div class="mb-3">
							<label for="mediaUrl" class="form-label fw-bold">Hình/Video
								(URL)</label> <input id="mediaUrl" name="mediaUrl" type="text"
								class="form-control" placeholder="https://..."
								value="${isEdit ? review.mediaUrl : ''}" />
						</div>

						<!-- Buttons -->
						<div class="d-flex gap-2">
							<button type="submit" class="btn btn-primary">
								<c:choose>
									<c:when test="${isEdit}">Lưu thay đổi</c:when>
									<c:otherwise>Gửi đánh giá</c:otherwise>
								</c:choose>
							</button>

							<a
								href="${pageContext.request.contextPath}/user/product/detail?id=${product.productId}"
								class="btn btn-outline-secondary">Xem sản phẩm</a>
						</div>
					</form>

					<!-- ✅ Form xóa tách riêng -->
					<c:if test="${isEdit}">
						<form method="post" class="mt-3"
							action="${pageContext.request.contextPath}/user/review/delete"
							onsubmit="return confirm('Bạn chắc chắn muốn xóa đánh giá này?');">
							<input type="hidden" name="reviewId" value="${review.reviewId}" />
							<button type="submit" class="btn btn-danger">Xóa đánh
								giá</button>
						</form>
					</c:if>

				</div>
			</div>
		</div>
	</div>
</div>

<script>
	document
			.getElementById('reviewForm')
			.addEventListener(
					'submit',
					function(e) {
						const rating = document
								.querySelector('input[name="rating"]:checked');
						const comment = document.getElementById('comment').value
								.trim();
						if (!rating) {
							alert('Vui lòng chọn số sao.');
							e.preventDefault();
							return;
						}
						if (comment.length < 5) {
							if (!confirm('Bình luận quá ngắn. Bạn có muốn gửi không?')) {
								e.preventDefault();
							}
						}
					});
</script>

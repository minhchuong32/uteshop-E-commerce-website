<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

<!-- CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/user/review.css">


<div class="review-container">
	<a href="${pageContext.request.contextPath}/user/orders"
		class="back-link"> <i class="fas fa-arrow-left"></i> Quay lại lịch
		sử đơn hàng
	</a>

	<c:set var="isEdit" value="${not empty review}" />

	<div class="review-card">
		<div class="review-header">
			<h4>
				<i class="fas ${isEdit ? 'fa-edit' : 'fa-star'}"></i>
				<c:choose>
					<c:when test="${isEdit}">Chỉnh sửa đánh giá</c:when>
					<c:otherwise>Viết đánh giá sản phẩm</c:otherwise>
				</c:choose>
			</h4>
		</div>

		<div class="review-body">
			<!-- Product Info -->
			<div class="product-info">
				<img
					src="${pageContext.request.contextPath}/assets${product.imageUrl}"
					alt="${product.name}" class="product-image">
				<div class="product-details">
					<h6>${product.name}</h6>
					<div class="product-meta">
						<small><i class="fas fa-store"></i> ${product.shop.name}</small> <small>
							<i class="fas fa-star" style="color: #fbbf24;"></i> <fmt:formatNumber
								value="${product.averageRating}" type="number"
								maxFractionDigits="1" /> (${product.reviewsCount} đánh giá)
						</small>
					</div>
				</div>
			</div>

			<!-- Alerts -->
			<c:if test="${not empty successMessage}">
				<div class="alert alert-success">
					<i class="fas fa-check-circle"></i> ${successMessage}
				</div>
			</c:if>
			<c:if test="${not empty errorMessage}">
				<div class="alert alert-danger">
					<i class="fas fa-exclamation-circle"></i> ${errorMessage}
				</div>
			</c:if>

			<!-- Review Form -->
			<form method="post" enctype="multipart/form-data" id="reviewForm"
				action="${pageContext.request.contextPath}/user/review${isEdit ? '/edit' : '/add'}">

				<input type="hidden" name="productId" value="${product.productId}" />
				<c:if test="${isEdit}">
					<input type="hidden" name="reviewId" value="${review.reviewId}" />
				</c:if>

				<!-- Two Column Layout: Rating + Comment -->
				<div class="review-content">
					<!-- Left: Rating Section -->
					<div class="rating-section">
						<div class="rating-label">
							<i class="fas fa-star" style="color: #fbbf24;"></i> Đánh giá của
							bạn
						</div>
						<div class="star-rating">
							<c:forEach var="i" begin="1" end="5" step="1">
								<input type="radio" name="rating" id="r${i}" value="${i}"
									${isEdit && review.rating == i ? 'checked' : (!isEdit && i==5 ? 'checked' : '')}>
								<label for="r${i}">★</label>
							</c:forEach>
						</div>
					</div>

					<!-- Right: Comment Section -->
					<div class="comment-section">
						<label for="comment" class="form-label"> <i
							class="fas fa-comment-dots"></i> Nhận xét của bạn
						</label>
						<textarea id="comment" name="comment" class="form-control"
							placeholder="Chia sẻ trải nghiệm của bạn về sản phẩm này...">${isEdit ? review.comment : ''}</textarea>
					</div>
				</div>

				<!-- Media Upload Section -->
				<div class="media-section">
					<label class="form-label"> <i class="fas fa-images"></i>
						Thêm hình ảnh/video
					</label>
					<div class="file-upload-wrapper">
						<input id="mediaFile" name="mediaFile" type="file"
							accept="image/*,video/*" class="file-upload-input"
							onchange="previewMedia(event)"> <label for="mediaFile"
							class="file-upload-label"> <i
							class="fas fa-cloud-upload-alt fa-lg"></i> <span>Chọn ảnh
								hoặc video (tối đa 10MB)</span>
						</label>
					</div>

					<!-- Preview Container -->
					<div id="previewContainer" class="preview-container">
						<c:if test="${isEdit && not empty review.mediaUrl}">
							<c:choose>
								<c:when
									test="${fn:endsWith(review.mediaUrl, '.mp4') || fn:endsWith(review.mediaUrl, '.mov')}">
									<video controls>
										<source
											src="${pageContext.request.contextPath}/assets${review.mediaUrl}"
											type="video/mp4">
									</video>
								</c:when>
								<c:otherwise>
									<img
										src="${pageContext.request.contextPath}/assets${review.mediaUrl}"
										alt="Review media">
								</c:otherwise>
							</c:choose>
						</c:if>
					</div>
					<c:if test="${isEdit && not empty review.mediaUrl}">
						<input type="hidden" name="oldMediaUrl" value="${review.mediaUrl}" />
					</c:if>
				</div>

				<!-- Action Buttons -->
				<div class="action-buttons">
					<div class="action-buttons-left">
						<button type="submit" class="btn btn-primary-custom">
							<i class="fas ${isEdit ? 'fa-save' : 'fa-paper-plane'}"></i>
							<c:choose>
								<c:when test="${isEdit}">Lưu thay đổi</c:when>
								<c:otherwise>Gửi đánh giá</c:otherwise>
							</c:choose>
						</button>

						<a
							href="${pageContext.request.contextPath}/user/product/detail?id=${product.productId}"
							class="btn btn-outline-secondary"> <i class="fas fa-eye"></i>
							Xem sản phẩm
						</a>
					</div>

					<!-- Delete Button (if editing) -->
					<c:if test="${isEdit}">
						<button type="button" class="btn btn-danger"
							onclick="deleteReview()">
							<i class="fas fa-trash-alt"></i> Xóa đánh giá
						</button>
					</c:if>
				</div>
			</form>

			<!-- Hidden Delete Form -->
			<c:if test="${isEdit}">
				<form method="post" id="deleteForm" style="display: none;"
					action="${pageContext.request.contextPath}/user/review/delete">
					<input type="hidden" name="reviewId" value="${review.reviewId}" />
				</form>
			</c:if>
		</div>
	</div>
</div>

<script
	src="${pageContext.request.contextPath}/assets/js/user/review.js"></script>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>${shop.name}|UteShop</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
	rel="stylesheet">
</head>
<body>
	<div class="container py-4">

		<!--  Thông tin shop -->
		<div class="card mb-4 shadow-sm">
			<div class="card-body d-flex align-items-center">
				<img src="${pageContext.request.contextPath}/assets${shop.logo}"
					alt="Shop logo" class="rounded me-3 border"
					style="width: 80px; height: 80px; object-fit: cover;">
				<div>
					<h4 class="fw-bold mb-1">${shop.name}</h4>
					<p class="text-muted mb-1">${shop.description}</p>
					<small class="text-muted"> <i class="bi bi-calendar-event"></i>
						Ngày tạo: <fmt:formatDate value="${shop.createdAt}"
							pattern="dd/MM/yyyy" />
					</small>
				</div>
			</div>
		</div>

		<!-- Bộ lọc + Sản phẩm -->
		<div id="product" class="bg-white rounded shadow-sm p-3 mb-4">
			<div class="mb-3">
				<h5 class="fw-bold text-uppercase text-primary-custom">
					<i class="bi bi-box-seam me-2"></i> Sản phẩm
				</h5>
			</div>
			<!-- Bộ lọc -->
			<form action="${pageContext.request.contextPath}/web/shop/detail"
				method="get" class="row g-3 align-items-end mb-4">
				<!-- Giữ page hiện tại -->
				<input type="hidden" name="page" value="${currentPage}" />
				<!-- Giữ shopId -->
				<input type="hidden" name="id" value="${shop.shopId}" />

				<div class="col-md-3">
					<label class="form-label fw-bold">Danh mục</label> <select
						class="form-select" name="categoryId">
						<option value="">Tất cả</option>
						<c:forEach var="c" items="${categories}">
							<option value="${c.categoryId}"
								<c:if test="${c.categoryId == selectedCategoryId}">selected</c:if>>
								${c.name}</option>
						</c:forEach>
					</select>
				</div>

				<div class="col-md-3">
					<label class="form-label fw-bold">Khoảng giá</label>
					<div class="input-group">
						<input type="number" class="form-control" placeholder="Từ"
							name="minPrice" value="${minPrice != null ? minPrice : ''}" /> <span
							class="input-group-text">-</span> <input type="number"
							class="form-control" placeholder="Đến" name="maxPrice"
							value="${maxPrice != null ? maxPrice : ''}" />
					</div>
				</div>

				<div class="col-md-3">
					<label class="form-label fw-bold">Sắp xếp</label> <select
						class="form-select" name="sortBy">
						<option value="newest"
							<c:if test="${sortBy == 'newest'}">selected</c:if>>Mới
							nhất</option>
						<option value="priceAsc"
							<c:if test="${sortBy == 'priceAsc'}">selected</c:if>>Giá:
							Thấp → Cao</option>
						<option value="priceDesc"
							<c:if test="${sortBy == 'priceDesc'}">selected</c:if>>Giá:
							Cao → Thấp</option>
					</select>
				</div>

				<div class="col-md-3 d-flex gap-2">
					<button type="submit" class="btn btn-primary-custom w-50">
						<i class="bi bi-filter"></i> Lọc
					</button>
					<a
						href="${pageContext.request.contextPath}/web/shop/detail?id=${shop.shopId}"
						class="btn btn-outline-secondary w-50">Xóa lọc</a>
				</div>
			</form>
			<!-- Chuyển đổi layout -->
			<div class="d-flex justify-content-end mb-3">
				<div class="btn-group" role="group">
					<button type="button" class="btn btn-primary-custom active"
						id="btnGrid">
						<i class="bi bi-grid"></i>
					</button>
					<button type="button" class="btn btn-outline-secondary"
						id="btnList">
						<i class="bi bi-list"></i>
					</button>
				</div>
			</div>
			<!-- Grid View -->
			<div class="row g-3" id="gridView">
				<c:forEach var="p" items="${products}">
					<!-- col-6: 2 sp/hàng trên mobile, col-md-4: 3 sp/hàng tablet, col-lg-2: 6 sp/hàng desktop -->
					<div class="col-6 col-md-4 col-lg-2">
						<a
							href="${pageContext.request.contextPath}/web/product/detail?id=${p.productId}"
							class="text-decoration-none text-dark d-block h-100">
							<div class="card product-card h-100">
								<div class="product-img-wrapper">
									<img
										src="${pageContext.request.contextPath}/assets/${p.imageUrl}"
										class="card-img-top" alt="${p.name}">
								</div>
								<div
									class="card-body d-flex flex-column justify-content-between">
									<h6 class="card-title mb-1">${p.name}</h6>
									<div class="mb-1">
										<span class="text-warning">★★★★☆</span> <small
											class="text-muted">(${p.reviewsCount} đánh giá)</small>
									</div>
									<c:forEach var="v" items="${p.variants}" varStatus="status">
										<c:if test="${status.first}">
											<c:choose>
												<c:when test="${v.oldPrice != null && v.oldPrice > v.price}">
													<!-- Giá cũ gạch -->
													<p style="text-decoration: line-through;">
														<fmt:formatNumber value="${v.oldPrice}" type="currency"
															currencySymbol="₫" />
													</p>
													<!-- Giá mới nổi bật -->
													<p class="text-danger fw-bold">
														<fmt:formatNumber value="${v.price}" type="currency"
															currencySymbol="₫" />
													</p>
												</c:when>
												<c:otherwise>
													<p class="text-danger fw-bold">
														<fmt:formatNumber value="${v.price}" type="currency"
															currencySymbol="₫" />
													</p>
												</c:otherwise>
											</c:choose>
										</c:if>
									</c:forEach>




									<button type="button"
										class="btn btn-sm btn-primary-custom w-100"
										onclick="window.location.href='${pageContext.request.contextPath}/web/product/detail?id=${p.productId}'">
										<i class="bi bi-cart-plus"></i> Thêm vào giỏ hàng
									</button>


								</div>
							</div>
						</a>
					</div>
				</c:forEach>
			</div>
			<!-- List View -->
			<div class="list-group d-none" id="listView">
				<c:choose>
					<c:when test="${empty products}">
						<div class="text-center py-5">
							<h6 class="text-muted">Chưa có sản phẩm</h6>
						</div>
					</c:when>
					<c:otherwise>
						<c:forEach var="p" items="${products}">
							<a
								href="${pageContext.request.contextPath}/web/product/detail?id=${p.productId}"
								class="list-group-item list-group-item-action d-flex align-items-center justify-content-between border text-decoration-none text-dark"
								style="transition: all 0.2s;"
								onmouseover="this.style.borderColor='var(--bs-primary)'"
								onmouseout="this.style.borderColor='#dee2e6'">
								<div class="d-flex align-items-center">
									<img
										src="${pageContext.request.contextPath}/assets${p.imageUrl}"
										alt="${p.name}" class="img-thumbnail me-3"
										style="width: 80px; height: 80px; object-fit: cover;">
									<div>
										<h6 class="mb-1">${p.name}</h6>
										<small class="text-muted">${p.reviewsCount} đánh giá</small>
									</div>
								</div> <c:set var="variant" value="${p.variants[0]}" />
								<div class="text-end">
									<c:choose>
										<c:when
											test="${variant.oldPrice != null && variant.oldPrice > variant.price}">
											<p class="text-muted mb-0"
												style="text-decoration: line-through; font-size: 13px;">
												<fmt:formatNumber value="${variant.oldPrice}"
													type="currency" currencySymbol="₫" />
											</p>
											<p class="text-danger fw-bold mb-0">
												<fmt:formatNumber value="${variant.price}" type="currency"
													currencySymbol="₫" />
											</p>
										</c:when>
										<c:otherwise>
											<p class="text-danger fw-bold mb-0">
												<fmt:formatNumber value="${variant.price}" type="currency"
													currencySymbol="₫" />
											</p>
										</c:otherwise>
									</c:choose>
									<button type="button"
										class="btn btn-sm btn-primary-custom w-100"
										onclick="window.location.href='${pageContext.request.contextPath}/web/product/detail?id=${p.productId}'">
										<i class="bi bi-cart-plus"></i> Thêm vào giỏ hàng
									</button>
								</div>
							</a>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
			<!-- Pagination -->
			<c:if test="${totalPages > 1}">
				<nav aria-label="Page navigation" class="mt-4">
					<ul class="pagination justify-content-center">
						<!-- Nút Previous -->
						<li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
							<a class="page-link nav-link"
							href="${pageContext.request.contextPath}/web/shop/detail?page=${currentPage - 1}&id=${shop.shopId}">
								Trước </a>
						</li>
						<!-- Các số trang -->
						<c:forEach var="i" begin="1" end="${totalPages}">
							<li class="page-item ${i == currentPage ? 'active' : ''}"><a
								class="page-link nav-link ${i == currentPage ? 'active' : ''}"
								href="${pageContext.request.contextPath}/web/shop/detail?page=${i}&id=${shop.shopId}">
									${i} </a></li>
						</c:forEach>
						<!-- Nút Next -->
						<li
							class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
							<a class="page-link nav-link"
							href="${pageContext.request.contextPath}/web/shop/detail?page=${currentPage + 1}&id=${shop.shopId}">
								Sau </a>
						</li>
					</ul>
				</nav>
			</c:if>
		</div>
	</div>
	<script
		src="${pageContext.request.contextPath}/assets/js/user-shop-detail.js"></script>

</body>
</html>
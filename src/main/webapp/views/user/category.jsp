<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Uteshop | ${categoryName}</title>
</head>
<body>

	<!-- Banner -->
	<div class="bg-white shadow-sm mb-4 w-100">
		<div class="container py-3">
			<div id="bannerDiv" class="carousel slide" data-bs-ride="carousel">
				<div class="carousel-inner rounded">
					<div class="carousel-item active">
						<img
							src="${pageContext.request.contextPath}/assets/images/banner3.png"
							class="d-block w-100 banner-img rounded" alt="banner3">
					</div>
					<div class="carousel-item">
						<img
							src="${pageContext.request.contextPath}/assets/images/banner2.png"
							class="d-block w-100 banner-img rounded" alt="banner2">
					</div>
					<div class="carousel-item">
						<img
							src="${pageContext.request.contextPath}/assets/images/banner1.png"
							class="d-block w-100 banner-img rounded" alt="banner1">
					</div>
				</div>
				<button class="carousel-control-prev" type="button"
					data-bs-target="#bannerDiv" data-bs-slide="prev">
					<span class="carousel-control-prev-icon"></span>
				</button>
				<button class="carousel-control-next" type="button"
					data-bs-target="#bannerDiv" data-bs-slide="next">
					<span class="carousel-control-next-icon"></span>
				</button>
			</div>
		</div>
	</div>

	<div class="container">

		<!-- Tiêu đề danh mục -->
		<div class="bg-white shadow-sm p-3 mb-4">
			<h5 class="fw-bold text-uppercase text-primary-custom">
				<i class="bi bi-grid me-2"></i> ${categoryName}
			</h5>
		</div>

		<!-- Bộ lọc + Sản phẩm -->
		<div class="bg-white rounded shadow-sm p-3 mb-4">

			<!-- Bộ lọc -->
			<form action="${pageContext.request.contextPath}/user/products"
				method="get" class="row g-3 align-items-end mb-4">
				<input type="hidden" name="categoryId" value="${selectedCategoryId}" />
				<div class="col-md-3">
					<label class="form-label fw-bold">Khoảng giá</label>
					<div class="input-group">
						<input type="number" class="form-control" placeholder="Từ"
							name="minPrice"> <span class="input-group-text">-</span>
						<input type="number" class="form-control" placeholder="Đến"
							name="maxPrice">
					</div>
				</div>
				<div class="col-md-3">
					<label class="form-label fw-bold">Sắp xếp</label> <select
						class="form-select" name="sortBy">
						<option value="newest">Mới nhất</option>
						<option value="priceAsc">Giá: Thấp → Cao</option>
						<option value="priceDesc">Giá: Cao → Thấp</option>
					</select>
				</div>
				<div class="col-md-3 d-flex gap-2">
					<button type="submit" class="btn btn-primary-custom w-50">
						<i class="bi bi-filter"></i> Lọc
					</button>
					<a
						href="${pageContext.request.contextPath}/user/products?categoryId=${selectedCategoryId}"
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
				<c:choose>
					<c:when test="${empty products}">
						<div class="col-12 text-center py-5">
							<h6 class="text-muted">Chưa có sản phẩm</h6>
						</div>
					</c:when>
					<c:otherwise>
						<c:forEach var="p" items="${products}">
							<div class="col-6 col-md-4 col-lg-2">
								<a
									href="${pageContext.request.contextPath}/user/product/detail?id=${p.productId}"
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
											<div class="price-wrapper">
												<c:if test="${not empty p.variants}">
													<p class="text-danger fw-bold mb-1"
														style="font-size: 15px;">
														<fmt:formatNumber value="${p.variants[0].price}"
															type="currency" currencySymbol="₫" />
													</p>

													<c:if test="${p.variants[0].oldPrice ne null}">
														<p
															class="text-muted text-decoration-line-through mb-0 small">
															<fmt:formatNumber value="${p.variants[0].oldPrice}"
																type="currency" currencySymbol="₫" />
														</p>
													</c:if>
												</c:if>
											</div>
											<button class="btn btn-sm btn-primary-custom w-100 mt-2">
												<i class="bi bi-cart-plus"></i> Thêm vào giỏ hàng
											</button>
										</div>
									</div>
								</a>
							</div>
						</c:forEach>
					</c:otherwise>
				</c:choose>
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
								href="${pageContext.request.contextPath}/user/product/detail?id=${p.productId}"
								class="list-group-item list-group-item-action d-flex align-items-center justify-content-between border text-decoration-none text-dark"
								style="transition: all 0.2s;"
								onmouseover="this.style.borderColor='var(--bs-primary)'"
								onmouseout="this.style.borderColor='#dee2e6'">

								<div class="d-flex align-items-center">
									<img
										src="${pageContext.request.contextPath}/assets/${p.imageUrl}"
										alt="${p.name}" class="img-thumbnail me-3"
										style="width: 80px; height: 80px; object-fit: cover;">
									<div>
										<h6 class="mb-1">${p.name}</h6>
										<small class="text-muted">${p.reviewsCount} đánh giá</small>
									</div>
								</div>

								<div class="text-end">
									<c:if test="${not empty p.variants}">
										<p class="text-danger fw-bold mb-0">
											<fmt:formatNumber value="${p.variants[0].price}"
												type="currency" currencySymbol="₫" />
										</p>

										<c:if test="${p.variants[0].oldPrice ne null}">
											<p class="text-muted text-decoration-line-through mb-0 small">
												<fmt:formatNumber value="${p.variants[0].oldPrice}"
													type="currency" currencySymbol="₫" />
											</p>
										</c:if>
									</c:if>
									<button class="btn btn-sm btn-primary-custom mt-2">
										<i class="bi bi-cart-plus"></i> Thêm vào giỏ
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
						<!-- Previous -->
						<li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
							<a class="page-link nav-link"
							href="${pageContext.request.contextPath}/user/products?categoryId=${selectedCategoryId}&page=${currentPage - 1}">
								Trước </a>
						</li>
						<!-- Page numbers -->
						<c:forEach var="i" begin="1" end="${totalPages}">
							<li class="page-item ${i == currentPage ? 'active' : ''}"><a
								class="page-link nav-link ${i == currentPage ? 'active' : ''}"
								href="${pageContext.request.contextPath}/user/products?categoryId=${selectedCategoryId}&page=${i}">
									${i} </a></li>
						</c:forEach>
						<!-- Next -->
						<li
							class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
							<a class="page-link nav-link"
							href="${pageContext.request.contextPath}/user/products?categoryId=${selectedCategoryId}&page=${currentPage + 1}">
								Sau </a>
						</li>
					</ul>
				</nav>
			</c:if>
		</div>
	</div>

	<script src="${pageContext.request.contextPath}/assets/js/user-home.js"></script>
</body>
</html>

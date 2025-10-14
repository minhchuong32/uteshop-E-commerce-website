<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglib.jsp"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<title>Uteshop | Trang chủ</title>
</head>
<body id="main">

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

	<div id="category" class="container">

		<!-- Categories -->
		<div class="bg-white shadow-sm p-0 mb-4 position-relative">
			<div class="p-3">
				<h5 class="fw-bold text-uppercase text-primary-custom">
					<i class="bi bi-grid me-2"></i> Danh mục
				</h5>
			</div>

			<div class="position-relative">
				<div id="categorySlider"
					class="row flex-nowrap overflow-auto text-center g-0 p-0">
					<c:forEach var="c" items="${categories}" varStatus="status">
						<c:if test="${status.index < 12}">
							<div class="col-2 category-card border rounded">
								<a
									href="${pageContext.request.contextPath}/web/products?categoryId=${c.categoryId}"
									class="text-decoration-none text-dark d-block">
									<div class="category-img">
										<img
											src="${pageContext.request.contextPath}/assets/${c.image}"
											alt="${c.name}" class="img-fluid">
									</div>
									<div class="category-title fw-medium py-2">${c.name}</div>
								</a>
							</div>
						</c:if>
					</c:forEach>
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
			<form action="${pageContext.request.contextPath}/web/home"
				method="get" class="row g-3 align-items-end mb-4">
				<!-- Giữ page hiện tại -->
				<input type="hidden" name="page" value="${currentPage}" />

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
					<a href="${pageContext.request.contextPath}/web/home"
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
													<p style="text-decoration: line-through;">
														<fmt:formatNumber value="${v.oldPrice}" type="currency"
															currencySymbol="₫" />
													</p>
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
										onclick="window.location.href='${pageContext.request.contextPath}/login'">
										<i class="bi bi-cart-plus"></i> Đăng nhập để mua
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
										src="${pageContext.request.contextPath}/assets/${p.imageUrl}"
										alt="${p.name}" class="img-thumbnail me-3"
										style="width: 80px; height: 80px; object-fit: cover;">
									<div>
										<h6 class="mb-1">${p.name}</h6>
										<small class="text-muted">${p.reviewsCount} đánh giá</small>
									</div>
								</div> <c:forEach var="v" items="${p.variants}" varStatus="status">
									<c:if test="${status.first}">
										<div class="text-end">
											<c:choose>
												<c:when test="${v.oldPrice != null && v.oldPrice > v.price}">
													<p class="text-muted mb-0"
														style="text-decoration: line-through; font-size: 13px;">
														<fmt:formatNumber value="${v.oldPrice}" type="currency"
															currencySymbol="₫" />
													</p>
													<p class="text-danger fw-bold mb-0">
														<fmt:formatNumber value="${v.price}" type="currency"
															currencySymbol="₫" />
													</p>
												</c:when>
												<c:otherwise>
													<p class="text-danger fw-bold mb-0">
														<fmt:formatNumber value="${v.price}" type="currency"
															currencySymbol="₫" />
													</p>
												</c:otherwise>
											</c:choose>

											<button type="button"
												class="btn btn-sm btn-primary-custom w-100"
												onclick="window.location.href='${pageContext.request.contextPath}/login'">
												<i class="bi bi-cart-plus"></i> Đăng nhập để mua
											</button>
										</div>
									</c:if>
								</c:forEach>
							</a>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>



			<!-- Pagination -->
			<c:if test="${totalPages > 1}">
				<nav aria-label="Page navigation" class="mt-4">
					<ul class="pagination justify-content-center">
						<li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
							<a class="page-link nav-link"
							href="${pageContext.request.contextPath}/web/home?page=${currentPage - 1}">Trước</a>
						</li>
						<c:forEach var="i" begin="1" end="${totalPages}">
							<li class="page-item ${i == currentPage ? 'active' : ''}"><a
								class="page-link nav-link ${i == currentPage ? 'active' : ''}"
								href="${pageContext.request.contextPath}/web/home?page=${i}">${i}</a>
							</li>
						</c:forEach>
						<li
							class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
							<a class="page-link nav-link"
							href="${pageContext.request.contextPath}/web/home?page=${currentPage + 1}">Sau</a>
						</li>
					</ul>
				</nav>
			</c:if>

		</div>
	</div>

	<script src="${pageContext.request.contextPath}/assets/js/user-home.js"></script>
</body>
</html>

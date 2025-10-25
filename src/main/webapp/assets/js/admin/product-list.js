// product-list.js - Quản lý sản phẩm

// Khởi tạo DataTable cho bảng sản phẩm
function initProductTable() {
	$('#productTable').DataTable({
		destroy: true,
		pageLength: 5,
		ordering: true,
		lengthMenu: [[5, 10, 25, 50, -1], [5, 10, 25, 50, "Tất cả"]],
		language: {
			lengthMenu: "Hiển thị _MENU_ dòng",
			search: "Tìm kiếm:",
			paginate: {
				previous: "Trước",
				next: "Sau"
			},
			info: "Hiển thị _START_–_END_ / _TOTAL_ Sản Phẩm",
			emptyTable: "Không có dữ liệu"
		}
	});
}

// Xử lý click vào dòng sản phẩm để xem chi tiết
function handleProductRowClick(contextPath) {
	$('#productTable tbody').on('click', 'tr.product-row', function(e) {
		// Bỏ qua nếu click vào nút xóa / sửa
		if ($(e.target).closest('a').length > 0) return;

		const id = $(this).data('id');
		const name = $(this).data('name');
		const desc = $(this).data('desc') || 'Chưa có mô tả';
		const category = $(this).data('category');
		const shop = $(this).data('shop');
		const img = $(this).data('img');

		// Gán vào modal
		$('#detailImage').attr('src', img);
		$('#detailName').text(name);
		$('#detailDesc').text(desc);
		$('#detailCategory').text(category);
		$('#detailShop').text(shop);

		// Gọi AJAX lấy biến thể và review 
		loadProductVariants(id, contextPath);
		loadProductReviews(id, contextPath);

		// Hiển thị modal
		const modal = new bootstrap.Modal(document.getElementById('productDetailModal'));
		modal.show();
	});
}

// Load danh sách biến thể sản phẩm qua AJAX
function loadProductVariants(productId, contextPath) {
	$('#variantBody').html('<tr><td colspan="6" class="text-center text-muted">Đang tải...</td></tr>');

	$.ajax({
		url: contextPath + '/admin/products/variants?productId=' + productId,
		type: 'GET',
		dataType: 'json',
		cache: false,
		success: function(data) {
			console.log("Raw data:", data);
			console.log("Type:", typeof data);

			// Nếu data là string, parse nó
			if (typeof data === 'string') {
				try {
					data = JSON.parse(data);
				} catch (e) {
					console.error("JSON parse error:", e);
					$('#variantBody').html('<tr><td colspan="6" class="text-danger text-center">Lỗi định dạng dữ liệu!</td></tr>');
					return;
				}
			}

			if (!data || data.length === 0) {
				$('#variantBody').html('<tr><td colspan="6" class="text-center text-muted">Không có biến thể</td></tr>');
				return;
			}

			renderVariantRows(data, contextPath);
		},
		error: function(xhr, status, error) {
			console.error("AJAX Error:", status, error);
			$('#variantBody').html('<tr><td colspan="6" class="text-danger text-center">Lỗi tải dữ liệu!</td></tr>');
		}
	});
}

// Render các dòng biến thể sản phẩm
function renderVariantRows(variants, contextPath) {
	let rows = '';

	for (let i = 0; i < variants.length; i++) {
		const v = variants[i];

		// Chuyển giá trị chuỗi sang số để format đúng
		const price = parseFloat(v.price) || 0;
		const oldPrice = v.oldPrice ? parseFloat(v.oldPrice) : 0;

		// Xử lý URL ảnh
		let imgUrl = v.imageUrl || '/images/products/default-product.jpg';
		// Nếu imageUrl chưa có contextPath, thêm vào
		if (imgUrl.indexOf(contextPath) === -1) {
			imgUrl = contextPath + '/assets' + imgUrl;
		}

		rows += '<tr>' +
			'<td class="text-center"><img src="' + imgUrl + '" class="rounded border" width="50" height="50" style="object-fit: cover;"></td>' +
			'<td>' + (v.optionName || '-') + '</td>' +
			'<td>' + (v.optionValue || '-') + '</td>' +
			'<td>' + (price ? price.toLocaleString('vi-VN') + '₫' : '-') + '</td>' +
			'<td>' + (oldPrice ? oldPrice.toLocaleString('vi-VN') + '₫' : '-') + '</td>' +
			'<td>' + (v.stock || 0) + '</td>' +
			'</tr>';
	}

	$('#variantBody').html(rows);
}

// Load danh sách đánh giá sản phẩm qua AJAX
function loadProductReviews(productId, contextPath) {
	const reviewContainer = $('#reviewContainer');
	reviewContainer.html('<p class="text-center text-muted">Đang tải đánh giá...</p>');

	$.ajax({
		url: contextPath + '/admin/products/reviews?productId=' + productId,
		type: 'GET',
		dataType: 'json',
		cache: false,
		success: function(data) {
			if (!data || data.length === 0) {
				reviewContainer.html('<p class="text-center text-muted">Sản phẩm này chưa có đánh giá nào.</p>');
				return;
			}
			renderReviews(data, contextPath);
		},
		error: function(xhr, status, error) {
			console.error("AJAX Error (Reviews):", status, error);
			reviewContainer.html('<p class="text-danger text-center">Lỗi tải dữ liệu đánh giá!</p>');
		}
	});
}

// Render danh sách đánh giá vào container
function renderReviews(reviews, contextPath) { // ✅ Nhận thêm contextPath
	let html = '';
	reviews.forEach(review => {
		// Tạo chuỗi HTML cho các ngôi sao
		let stars = '';
		for (let i = 1; i <= 5; i++) {
			stars += `<i class="bi bi-star${i <= review.rating ? '-fill' : ''} text-warning"></i>`;
		}

		html += `
            <div class="d-flex mb-3 border-bottom pb-2">
                <div class="flex-shrink-0">
                    <i class="bi bi-person-circle fs-2 text-secondary"></i>
                </div>
                <div class="flex-grow-1 ms-3">
                    <div class="d-flex justify-content-between align-items-center">
                        <strong class="text-primary-custom">${review.username}</strong>
                        <div class="review-stars">${stars}</div>
                    </div>
                    <p class="mb-1 fst-italic text-muted">"${review.comment}"</p>

                    <div class="mt-2 text-end">
                        <button class="btn btn-sm btn-outline-warning me-2" 
                                onclick="openEditReviewModal(${review.reviewId}, ${review.rating}, '${review.comment.replace(/'/g, "\\'")}')">
                            <i class="bi bi-pencil-fill"></i> Sửa
                        </button>
						<button class="btn btn-sm btn-outline-danger"
						                                data-bs-toggle="modal"
						                                data-bs-target="#confirmDeleteReviewModal"
						                                data-delete-url="${contextPath}/admin/reviews/delete?id=${review.reviewId}">
						                           <i class="bi bi-trash-fill"></i> Xóa
						                        </button>
                    </div>
                </div>
            </div>
        `;
	});
	$('#reviewContainer').html(html);
}

function openEditReviewModal(reviewId, rating, comment) {
	// Điền dữ liệu cũ vào form trong modal
	$('#editReviewId').val(reviewId);
	$('#editReviewComment').val(comment);

	// Set sao cho đúng với rating
	$('#editReviewRating').val(rating);

	// Mở modal
	const modal = new bootstrap.Modal(document.getElementById('editReviewModal'));
	modal.show();
}

// Khởi tạo khi DOM loaded
document.addEventListener('DOMContentLoaded', function() {
	// Khởi tạo DataTable
	initProductTable();
});
// Khởi tạo khi DOM loaded
document.addEventListener('DOMContentLoaded', function() {
	// Khởi tạo DataTable
	initProductTable();
	// Script xử lý cho modal xác nhận xóa review
	const confirmDeleteReviewModal = document.getElementById('confirmDeleteReviewModal');
	if (confirmDeleteReviewModal) {
		confirmDeleteReviewModal.addEventListener('show.bs.modal', function(event) {
			// Lấy button đã kích hoạt modal
			const button = event.relatedTarget;

			// Lấy URL xóa từ thuộc tính data-delete-url của button
			const deleteUrl = button.getAttribute('data-delete-url');

			// Tìm nút "Xóa" bên trong modal
			const confirmBtn = confirmDeleteReviewModal.querySelector('#deleteReviewConfirmBtn');

			// Gán URL vào thuộc tính href của nút "Xóa"
			confirmBtn.href = deleteUrl;
		});
	}
});

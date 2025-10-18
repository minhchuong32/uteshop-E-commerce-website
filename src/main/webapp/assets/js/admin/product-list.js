// product-list.js - Quản lý sản phẩm

// Khởi tạo DataTable cho bảng sản phẩm
function initProductTable() {
    $('#productTable').DataTable({
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

        // Gọi AJAX lấy biến thể
        loadProductVariants(id, contextPath);

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
                } catch(e) {
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

// Khởi tạo khi DOM loaded
document.addEventListener('DOMContentLoaded', function() {
    // Khởi tạo DataTable
    initProductTable();
});

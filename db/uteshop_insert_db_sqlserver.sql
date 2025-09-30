
--------------------------------------------------------
-- 1. USERS
--------------------------------------------------------
INSERT INTO users (username, password, email, role, status, avatar, name, phone, address) VALUES
(N'khachhang1', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'kh1@example.com', N'User', N'active', N'avatar1.png', N'Nguyễn Văn A', N'0901111111', N'123 Lê Lợi, Q.1, TP.HCM'),
(N'khachhang2', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'kh2@example.com', N'User', N'active', N'avatar2.png', N'Trần Thị B', N'0902222222', N'456 Hai Bà Trưng, Q.3, TP.HCM'),
(N'admin', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'admin@example.com', N'Admin', N'active', N'admin.png', N'Quản trị viên', N'0903333333', N'789 Nguyễn Huệ, Q.1, TP.HCM'),
(N'shipper1', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'shipper1@example.com', N'Shipper', N'active', N'shipper1.png', N'Lê Văn Ship 1', N'0904444444', N'12 Trường Chinh, Tân Bình, TP.HCM'),
(N'shipper2', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'shipper2@example.com', N'Shipper', N'active', N'shipper2.png', N'Phạm Văn Ship 2', N'0905555555', N'34 Cách Mạng Tháng 8, Q.10, TP.HCM'),
(N'vendor1', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'vendor1@example.com', N'Vendor', N'active', N'vendor1.png', N'Công ty ABC', N'0906666666', N'88 Lý Thường Kiệt, Q.10, TP.HCM'),
(N'vendor2', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'vendor2@example.com', N'Vendor', N'active', N'vendor2.png', N'Cửa hàng XYZ', N'0907777777', N'99 Phan Đăng Lưu, Bình Thạnh, TP.HCM'),
(N'khachhang3', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'kh3@example.com', N'User', N'active', N'avatar3.png', N'Hoàng Văn C', N'0908888888', N'11 Điện Biên Phủ, Q.1, TP.HCM'),
(N'khachhang4', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'kh4@example.com', N'User', N'inactive', N'avatar4.png', N'Đặng Thị D', N'0909999999', N'22 Võ Văn Tần, Q.3, TP.HCM'),
(N'khachhang5', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'kh5@example.com', N'User', N'banned', N'avatar5.png', N'Phan Văn E', N'0910000000', N'55 Nguyễn Trãi, Q.5, TP.HCM'),
(N'vendor3', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'vendor3@example.com', N'Vendor', N'active', N'vendor3.png', N'Cửa hàng MNO', N'0912222333', N'12 Nguyễn Văn Cừ, Q.5, TP.HCM'),
(N'vendor4', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'vendor4@example.com', N'Vendor', N'active', N'vendor4.png', N'Siêu thị QRS', N'0914444555', N'45 Hai Bà Trưng, Q.1, TP.HCM'),
(N'vendor5', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'vendor5@example.com', N'Vendor', N'active', N'vendor5.png', N'Cửa hàng TUV', N'0916666777', N'67 Lý Thường Kiệt, Q.10, TP.HCM');
--------------------------------------------------------
-- 2. STORE SETTINGS
--------------------------------------------------------
INSERT INTO store_settings (store_name, email, hotline, address, logo, theme, cod_enabled, momo_enabled, vnpay_enabled, created_at, updated_at) VALUES
(N'UteShop', N'support@hcmute.edu.vn', N' +84 028 3896 8641', N' 01 Đ. Võ Văn Ngân, Linh Chiểu,Thủ Đức, Hồ Chí Minh', N'logo1.png', N'default', 1, 1, 0, GETDATE(), GETDATE());


--------------------------------------------------------
-- 3. SHOPS
--------------------------------------------------------
INSERT INTO shops (user_id, name, description, created_at) VALUES
(6, N'Shop Điện Thoại', N'Chuyên bán điện thoại chính hãng', GETDATE()),
(7, N'Shop Thời Trang', N'Chuyên bán quần áo thời trang', GETDATE()),
(11, N'Shop Gia Dụng', N'Đồ dùng gia đình tiện ích', GETDATE()),
(12, N'Shop Mỹ Phẩm', N'Mỹ phẩm chính hãng', GETDATE()),
(13, N'Shop Thực Phẩm', N'Thực phẩm sạch, an toàn', GETDATE());



--------------------------------------------------------
-- 4. CATEGORIES
--------------------------------------------------------
INSERT INTO categories (name, image, parent_id) VALUES
(N'Điện tử', N'/assets/images/categories/dien-tu.jpg', NULL),
(N'Điện thoại', N'/assets/images/categories/dien-thoai.jpg', 1),
(N'Laptop', N'/assets/images/categories/laptop.jpg', 1),
(N'Thời trang', N'/assets/images/categories/thoi-trang.jpg', NULL),
(N'Nam', N'/assets/images/categories/nam.jpg', 4),
(N'Nữ', N'/assets/images/categories/nu.jpg', 4),
(N'Giày dép', N'/assets/images/categories/giay-dep.jpg', 4),
(N'Gia dụng', N'/assets/images/categories/gia-dung.jpg', NULL),
(N'Nhà bếp', N'/assets/images/categories/nha-bep.jpg', 8),
(N'Phòng khách', N'/assets/images/categories/phong-khach.jpg', 8);


--------------------------------------------------------
-- 5. PRODUCTS
--------------------------------------------------------
INSERT INTO products (shop_id, category_id, name, price, old_price, stock, description, image_url) VALUES
(1, 2, N'iPhone 15',        25000000, 27000000, 50, N'Điện thoại Apple mới nhất', N'/assets/images/products/iphone15.png'),
(1, 2, N'Samsung Galaxy S23', 20000000, 22000000, 40, N'Điện thoại Samsung flagship', N'/assets/images/products/s23.png'),
(1, 3, N'MacBook Pro 14',   45000000, 48000000, 20, N'Laptop Apple cao cấp', N'/assets/images/products/macbook14.png'),
(1, 3, N'Dell XPS 13',      30000000, 32000000, 25, N'Laptop Dell mỏng nhẹ', N'/assets/images/products/dellxps.png'),
(2, 5, N'Áo sơ mi nam',        500000,   600000, 100, N'Áo sơ mi công sở', N'/assets/images/products/aosomi.png'),
(2, 6, N'Váy công chúa',       700000,   850000, 80, N'Váy cho nữ', N'/assets/images/products/vay.png'),
(2, 7, N'Giày sneaker',       1200000,  1500000, 60, N'Giày thể thao trẻ trung', N'/assets/images/products/sneaker.png'),
(2, 5, N'Quần jeans nam',      800000,   950000, 90, N'Quần jeans bền đẹp', N'/assets/images/products/jeans.png'),
(2, 6, N'Áo khoác nữ',         900000,  1100000, 70, N'Áo khoác mùa đông', N'/assets/images/products/aokhoac.png'),
(2, 7, N'Dép sandal',          300000,   400000, 50, N'Dép sandal tiện lợi', N'/assets/images/products/sandal.png'),
(3, 8, N'Nồi cơm điện 1.8L', 1200000, 1500000, 30, N'Nồi cơm điện cao cấp, tiết kiệm điện', N'/assets/images/products/noicom.png'),
(3, 9, N'Bộ dao nhà bếp', 500000, 700000, 50, N'Bộ dao inox Nhật Bản sắc bén', N'/assets/images/products/dao.png'),
(3, 10, N'Bàn trà gỗ sồi', 2500000, 3000000, 15, N'Bàn trà phòng khách gỗ sồi tự nhiên', N'/assets/images/products/bantra.png'),
(3, 8, N'Máy hút bụi mini', 1800000, 2200000, 20, N'Máy hút bụi mini cầm tay', N'/assets/images/products/mayhutbui.png'),
(4, 4, N'Son môi cao cấp', 350000, 450000, 100, N'Son môi lâu trôi, màu sắc tự nhiên', N'/assets/images/products/son.png'),
(4, 4, N'Kem dưỡng da ban đêm', 600000, 750000, 80, N'Kem dưỡng trắng và dưỡng ẩm', N'/assets/images/products/kem.png'),
(4, 4, N'Sữa rửa mặt thiên nhiên', 200000, 250000, 120, N'Sữa rửa mặt chiết xuất trà xanh', N'/assets/images/products/suaruamat.png'),
(4, 4, N'Nước hoa hồng', 400000, 500000, 90, N'Toner dưỡng ẩm, se khít lỗ chân lông', N'/assets/images/products/nuochoahong.png'),
(5, 8, N'Gạo ST25', 25000, 30000, 200, N'Gạo ngon nhất thế giới, hạt dài, dẻo thơm', N'/assets/images/products/gao.png'),
(5, 8, N'Nước mắm Phú Quốc', 70000, 90000, 150, N'Nước mắm truyền thống Phú Quốc 40 độ đạm', N'/assets/images/products/nuocmam.png'),
(5, 8, N'Trái cây sấy mix', 120000, 150000, 100, N'Hộp trái cây sấy dinh dưỡng', N'/assets/images/products/traicaysay.png'),
(5, 8, N'Cà phê hạt rang xay', 180000, 220000, 80, N'Cà phê hạt nguyên chất Buôn Ma Thuột', N'/assets/images/products/caphe.png');

--------------------------------------------------------
-- 6. PROMOTIONS
--------------------------------------------------------
INSERT INTO promotions (shop_id, discount_type, value, start_date, end_date) VALUES
-- Shop 1: Điện Thoại
(1, N'Phần trăm', 10, '2025-01-01', '2025-01-31'),
(1, N'Giảm trực tiếp', 200000, '2025-03-01', '2025-03-15'),
(1, N'Phần trăm', 5, '2025-06-01', '2025-06-30'),

-- Shop 2: Thời Trang
(2, N'Giảm trực tiếp', 50000, '2025-02-01', '2025-02-28'),
(2, N'Phần trăm', 20, '2025-05-01', '2025-05-07'),
(2, N'Phần trăm', 15, '2025-08-01', '2025-08-31'),

-- Shop 3: Gia Dụng
(3, N'Phần trăm', 15, '2025-03-01', '2025-03-31'),
(3, N'Giảm trực tiếp', 100000, '2025-07-01', '2025-07-15'),
(3, N'Phần trăm', 10, '2025-10-01', '2025-10-31'),

-- Shop 4: Mỹ Phẩm
(4, N'Giảm trực tiếp', 100000, '2025-04-01', '2025-04-30'),
(4, N'Phần trăm', 25, '2025-09-01', '2025-09-15'),
(4, N'Phần trăm', 10, '2025-12-01', '2025-12-31'),

-- Shop 5: Thực Phẩm
(5, N'Phần trăm', 20, '2025-05-01', '2025-05-15'),
(5, N'Giảm trực tiếp', 30000, '2025-06-01', '2025-06-30'),
(5, N'Phần trăm', 5, '2025-11-01', '2025-11-30');

--------------------------------------------------------
-- 7. ORDERS
--------------------------------------------------------
INSERT INTO orders (user_id, total_amount, status, payment_method, created_at, address) VALUES
(1, 25000000, N'Xác nhận', N'COD', GETDATE(), N'123 Lê Lợi, Q1, TP.HCM'),
(2, 20000000, N'Đang giao', N'MoMo', GETDATE(), N'45 Hai Bà Trưng, Q3, TP.HCM'),
(1, 550000, N'Hoàn tất', N'COD', GETDATE(), N'78 Nguyễn Huệ, Q1, TP.HCM'),
(8, 47000, N'Mới', N'VNPAY', GETDATE(), N'12 Phạm Văn Đồng, Q.Thủ Đức, TP.HCM'),
(9, 120000, N'Hủy', N'COD', GETDATE(), N'56 Điện Biên Phủ, Q.Bình Thạnh, TP.HCM'),
(2, 45000, N'Trà hàng', N'MoMo', GETDATE(), N'22 Võ Văn Ngân, Q.Thủ Đức, TP.HCM'),
(3, 30000, N'Hoàn tất', N'COD', GETDATE(), N'89 Nguyễn Văn Cừ, Q5, TP.HCM'),
(4, 12000, N'Mới', N'COD', GETDATE(), N'101 Lý Thường Kiệt, Q10, TP.HCM'),
(5, 20000, N'Xác nhận', N'VNPAY', GETDATE(), N'77 Hoàng Văn Thụ, Q.Phú Nhuận, TP.HCM'),
(6, 320000, N'Đang giao', N'COD', GETDATE(), N'55 Trường Chinh, Q.Tân Bình, TP.HCM');

--------------------------------------------------------
-- 8. ORDER DETAILS
--------------------------------------------------------
INSERT INTO order_details (order_id, product_id, quantity, price) VALUES
(1, 1, 1, 25000000),
(2, 2, 1, 20000000),
(3, 5, 1, 500000),
(4, 6, 2, 700000),
(5, 3, 1, 45000000),
(6, 10, 3, 300000),
(7, 7, 1, 1200000),
(8, 8, 1, 800000),
(9, 9, 2, 900000),
(10, 4, 1, 30000000);

--------------------------------------------------------
-- 9. DELIVERIES
--------------------------------------------------------
INSERT INTO deliveries (shipper_id, order_id, status) VALUES
(1, 1, N'Đã gán'),
(1, 2, N'Đang giao'),
(2, 3, N'Đã giao'),
(2, 4, N'Đang giao'),
(1, 5, N'Đã gán'),
(2, 6, N'Đã giao'),
(1, 7, N'Đã giao'),
(1, 8, N'Đã gán'),
(2, 9, N'Đang giao'),
(2, 10, N'Đã giao');


--------------------------------------------------------
-- 10. REVIEWS
--------------------------------------------------------
INSERT INTO reviews (product_id, user_id, rating, comment, media_url) VALUES
(1, 1, 5, N'Điện thoại rất tốt, pin lâu', N'review1.png'),
(2, 2, 4, N'Máy đẹp nhưng hơi nóng', N'review2.png'),
(3, 3, 5, N'Laptop cực nhanh', N'review3.png'),
(5, 8, 4, N'Áo sơ mi chất vải đẹp', N'review4.png'),
(6, 9, 5, N'Váy xinh, vừa vặn', N'review5.png'),
(7, 1, 3, N'Giày hơi cứng', N'review6.png'),
(8, 2, 4, N'Quần jeans thoải mái', N'review7.png'),
(9, 3, 5, N'Áo khoác rất ấm', N'review8.png'),
(10, 4, 4, N'Dép tiện lợi đi trong nhà', N'review9.png'),
(4, 5, 5, N'Dell XPS mỏng nhẹ, pin ổn', N'review10.png');

--------------------------------------------------------
-- 11. CONTACTS
--------------------------------------------------------
INSERT INTO contact (UserID, fullName, email, content, createdAt) VALUES
(1, N'Nguyễn Văn A', N'a@example.com', N'Tôi cần hỗ trợ về đơn hàng', GETDATE()),
(2, N'Trần Thị B', N'b@example.com', N'Làm sao đổi mật khẩu?', GETDATE()),
(3, N'Lê Văn C', N'c@example.com', N'Cần tư vấn sản phẩm', GETDATE()),
(4, N'Phạm Thị D', N'd@example.com', N'Tài khoản bị khóa', GETDATE()),
(5, N'Hồ Văn E', N'e@example.com', N'Giao hàng bị trễ', GETDATE()),
(6, N'Nguyễn Văn F', N'f@example.com', N'Tôi muốn đổi trả sản phẩm', GETDATE()),
(7, N'Nguyễn Văn G', N'g@example.com', N'Tôi muốn đăng ký làm vendor', GETDATE()),
(8, N'Lê Thị H', N'h@example.com', N'Xin tư vấn thêm sản phẩm mới', GETDATE()),
(9, N'Phan Văn I', N'i@example.com', N'Cần xuất hóa đơn đỏ', GETDATE()),
(10, N'Nguyễn Thị J', N'j@example.com', N'Cần đổi số điện thoại liên hệ', GETDATE());

--------------------------------------------------------
-- 12. CART ITEMS
--------------------------------------------------------
INSERT INTO cart_items (user_id, product_id, quantity) VALUES
(1, 1, 1),  -- khachhang1 chọn iPhone 15
(1, 5, 2),  -- khachhang1 chọn 2 áo sơ mi nam
(2, 2, 1),  -- khachhang2 chọn Galaxy S23
(2, 6, 1),  -- khachhang2 chọn váy công chúa
(3, 3, 1),  -- admin1 test mua MacBook Pro
(3, 7, 1),  -- admin1 thêm giày sneaker
(8, 8, 2),  -- khachhang3 chọn 2 quần jeans
(8, 10, 1), -- khachhang3 thêm dép sandal
(9, 4, 1),  -- khachhang4 chọn Dell XPS
(9, 9, 1),  -- khachhang4 thêm áo khoác nữ
(5, 7, 2),  -- shipper2 chọn 2 giày sneaker
(6, 1, 1),  -- vendor1 mua thử iPhone 15
(6, 5, 3),  -- vendor1 thêm 3 áo sơ mi nam
(7, 2, 1),  -- vendor2 test Samsung Galaxy S23
(10, 6, 2); -- khachhang5 thêm 2 váy công chúa

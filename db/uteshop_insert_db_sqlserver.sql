
--------------------------------------------------------
-- 1. USERS
--------------------------------------------------------
INSERT INTO users (username, password, email, role, status, avatar) VALUES
(N'khachhang1', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'kh1@example.com', N'User', N'active', N'avatar1.png'),
(N'khachhang2', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'kh2@example.com', N'User', N'active', N'avatar2.png'),
(N'admin1', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'admin@example.com', N'Admin', N'active', N'admin.png'),
(N'shipper1', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'shipper1@example.com', N'Shipper', N'active', N'shipper1.png'),
(N'shipper2', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'shipper2@example.com', N'Shipper', N'active', N'shipper2.png'),
(N'vendor1', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'vendor1@example.com', N'Vendor', N'active', N'vendor1.png'),
(N'vendor2', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'vendor2@example.com', N'Vendor', N'active', N'vendor2.png'),
(N'khachhang3', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'kh3@example.com', N'User', N'active', N'avatar3.png'),
(N'khachhang4', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'kh4@example.com', N'User', N'inactive', N'avatar4.png'),
(N'khachhang5', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'kh5@example.com', N'User', N'banned', N'avatar5.png');

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
(7, N'Shop Thời Trang', N'Chuyên bán quần áo thời trang', GETDATE());

--------------------------------------------------------
-- 4. CATEGORIES
--------------------------------------------------------
INSERT INTO categories (name, parent_id) VALUES
(N'Điện tử', NULL),
(N'Điện thoại', 1),
(N'Laptop', 1),
(N'Thời trang', NULL),
(N'Nam', 4),
(N'Nữ', 4),
(N'Giày dép', 4),
(N'Gia dụng', NULL),
(N'Nhà bếp', 8),
(N'Phòng khách', 8);

--------------------------------------------------------
-- 5. PRODUCTS
--------------------------------------------------------
INSERT INTO products (shop_id, category_id, name, price, stock, description, image_url) VALUES
(1, 2, N'iPhone 15', 25000000, 50, N'Điện thoại Apple mới nhất', N'iphone15.png'),
(1, 2, N'Samsung Galaxy S23', 20000000, 40, N'Điện thoại Samsung flagship', N's23.png'),
(1, 3, N'MacBook Pro 14', 45000000, 20, N'Laptop Apple cao cấp', N'macbook14.png'),
(1, 3, N'Dell XPS 13', 30000000, 25, N'Laptop Dell mỏng nhẹ', N'dellxps.png'),
(2, 5, N'Áo sơ mi nam', 500000, 100, N'Áo sơ mi công sở', N'aosomi.png'),
(2, 6, N'Váy công chúa', 700000, 80, N'Váy cho nữ', N'vay.png'),
(2, 7, N'Giày sneaker', 1200000, 60, N'Giày thể thao trẻ trung', N'sneaker.png'),
(2, 5, N'Quần jeans nam', 800000, 90, N'Quần jeans bền đẹp', N'jeans.png'),
(2, 6, N'Áo khoác nữ', 900000, 70, N'Áo khoác mùa đông', N'aokhoac.png'),
(2, 7, N'Dép sandal', 300000, 50, N'Dép sandal tiện lợi', N'sandal.png');

--------------------------------------------------------
-- 6. PROMOTIONS
--------------------------------------------------------
INSERT INTO promotions (shop_id, discount_type, value, start_date, end_date) VALUES
(1, N'percent', 10, '2025-01-01', '2025-12-31'),
(2, N'fixed', 50000, '2025-02-01', '2025-06-30');

--------------------------------------------------------
-- 7. ORDERS
--------------------------------------------------------
INSERT INTO orders (user_id, total_amount, status, payment_method, created_at) VALUES
(1, 25000000, N'Xác nhận', N'COD', GETDATE()),
(2, 20000000, N'Đang giao', N'MoMo', GETDATE()),
(1, 550000, N'Hoàn tất', N'COD', GETDATE()),
(8, 47000, N'Mới', N'VNPAY', GETDATE()),
(9, 120000, N'Hủy', N'COD', GETDATE()),
(2, 45000, N'Trà hàng', N'MoMo', GETDATE()),
(3, 30000, N'Hoàn tất', N'COD', GETDATE()),
(4, 12000, N'Mới', N'COD', GETDATE()),
(5, 20000, N'Xác nhận', N'VNPAY', GETDATE()),
(6, 320000, N'Đang giao', N'COD', GETDATE());

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
-- 9. SHIPPERS
--------------------------------------------------------
INSERT INTO shippers (user_id, name, phone) VALUES
(4, N'Nguyễn Văn A', '0901111111'),
(5, N'Trần Thị B', '0902222222');

--------------------------------------------------------
-- 10. DELIVERIES
--------------------------------------------------------
INSERT INTO deliveries (shipper_id, order_id, status) VALUES
(1, 1, N'assigned'),
(1, 2, N'delivering'),
(2, 3, N'delivered'),
(2, 4, N'delivering'),
(1, 5, N'assigned'),
(2, 6, N'delivered'),
(1, 7, N'delivered'),
(1, 8, N'assigned'),
(2, 9, N'delivering'),
(2, 10, N'delivered');

--------------------------------------------------------
-- 11. REVIEWS
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
-- 12. CONTACTS
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
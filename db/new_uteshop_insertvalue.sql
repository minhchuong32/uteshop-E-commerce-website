USE uteshopdb
GO

INSERT INTO users (username, password, email, role, status, avatar, name, phone, address) VALUES
(N'customer1', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'customer1@example.com', N'User', N'active', N'/uploads/avatar1.png', N'Nguyen Van A', N'0901234567', N'123 Đường A, TP.HCM'),
(N'vendor1', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'vendor1@example.com', N'Vendor', N'active', N'/uploads/avatar2.png', N'Tran Thi B', N'0902345678', N'456 Đường B, TP.HCM'),
(N'shipper1', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'shipper1@example.com', N'Shipper', N'active', N'/uploads/avatar3.png', N'Le Van C', N'0903456789', N'789 Đường C, TP.HCM');

INSERT INTO categories (name, image) VALUES
(N'Điện thoại', N'/uploads/category_phone.png'),
(N'Laptop', N'/uploads/category_laptop.png'),
(N'Quần áo', N'/uploads/category_clothes.png'),
(N'Giày dép', N'/uploads/category_shoes.png');

INSERT INTO shops (user_id, name, description, logo, created_at) VALUES
(2, N'Shop Điện Thoại ABC', N'Cửa hàng chuyên bán điện thoại', N'/uploads/shop1.png', GETDATE()),
(2, N'Shop Thời Trang XYZ', N'Cửa hàng quần áo, giày dép', N'/uploads/shop2.png', GETDATE());

INSERT INTO products (shop_id, category_id, name, description, image_url) VALUES
(1, 1, N'iPhone 15', N'iPhone 15 mới nhất, 128GB, 256GB, 512GB', N'/uploads/iphone15.png'),
(1, 1, N'Samsung Galaxy S23', N'Samsung S23 mới, nhiều màu', N'/uploads/s23.png'),
(2, 3, N'Áo sơ mi nam', N'Áo sơ mi nam nhiều size', N'/uploads/shirt.png'),
(2, 4, N'Giày sneaker', N'Giày sneaker nam nữ', N'/uploads/sneaker.png');

INSERT INTO product_variants (product_id, optionName, optionValue, price, oldPrice, stock, imageUrl) VALUES
(1, N'iPhone 15 128GB Đen', N'128GB, Đen', 25000000, 27000000, 20, N'/uploads/iphone15_black.png'),
(1, N'iPhone 15 256GB Trắng', N'256GB, Trắng', 28000000, 30000000, 15, N'/uploads/iphone15_white.png'),
(1, N'iPhone 15 512GB Đỏ', N'512GB, Đỏ', 32000000, 34000000, 10, N'/uploads/iphone15_red.png'),
(2, N'S23 128GB Đen', N'128GB, Đen', 20000000, 22000000, 20, N'/uploads/s23_black.png'),
(2, N'S23 256GB Bạc', N'256GB, Bạc', 22000000, 24000000, 10, N'/uploads/s23_silver.png'),
(3, N'Áo sơ mi Size M Trắng', N'M, Trắng', 500000, 550000, 30, N'/uploads/shirt_m.png'),
(3, N'Áo sơ mi Size L Trắng', N'L, Trắng', 500000, 550000, 40, N'/uploads/shirt_l.png'),
(4, N'Giày sneaker Size 38 Trắng', N'38, Trắng', 1200000, 1300000, 20, N'/uploads/sneaker38.png'),
(4, N'Giày sneaker Size 39 Trắng', N'39, Trắng', 1200000, 1300000, 20, N'/uploads/sneaker39.png');

INSERT INTO cart_items (user_id, product_variant_id, quantity, price) VALUES
(1, 1, 1, 25000000),
(1, 6, 2, 500000);

INSERT INTO orders (user_id, total_amount, status, payment_method, address, created_at) VALUES
(1, 26000000, N'new', N'COD', N'123 Đường A, TP.HCM', GETDATE());

INSERT INTO order_details (order_id, product_variant_id, quantity, price) VALUES
(1, 1, 1, 25000000),
(1, 6, 2, 500000);

INSERT INTO reviews (product_id, user_id, rating, comment, media_url) VALUES
(1, 1, 5, N'Sản phẩm tuyệt vời, giao hàng nhanh', N'/uploads/review1.png'),
(3, 1, 4, N'Áo mặc thoải mái, đúng size', NULL);

INSERT INTO promotions (shop_id, discount_type, value, start_date, end_date) VALUES
(1, N'percent', 10, '2025-10-01', '2025-10-31'),
(2, N'fixed', 50000, '2025-10-01', '2025-10-31');

INSERT INTO deliveries (shipper_id, order_id, status, note_text, delivery_note, created_at) VALUES
(3, 1, N'Đang giao', N'Giao trong giờ hành chính', NULL, GETDATE());

INSERT INTO contact (userID, fullName, email, content, createdAt) VALUES
(1, N'Nguyen Van A', N'customer1@example.com', N'Tôi cần hỗ trợ về sản phẩm', GETDATE()),
(2, N'Tran Thi B', N'vendor1@example.com', N'Tôi cần hỗ trợ kỹ thuật shop', GETDATE());

INSERT INTO complaints (order_id, user_id, title, content, status, created_at) VALUES
(1, 1, N'Sản phẩm lỗi', N'Sản phẩm bị trầy xước', N'Chờ xử lý', GETDATE());

INSERT INTO store_settings (store_name, email, hotline, address, logo, theme, cod_enabled, momo_enabled, vnpay_enabled, created_at, updated_at) VALUES
(N'Thuong Mai Store', N'support@example.com', N'19001234', N'123 Đường A, TP.HCM', N'/uploads/logo.png', N'default', 1, 1, 1, GETDATE(), GETDATE());

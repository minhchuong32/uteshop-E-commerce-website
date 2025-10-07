USE uteshopdb
GO

-- ============================
-- 1. USERS
-- ============================
INSERT INTO users (username, password, email, role, status, avatar, name, phone, address) VALUES
(N'customer1', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'customer1@example.com', N'User', N'active', NULL, N'Nguyen Van A', N'0901234567', N'123 Đường A, TP.HCM'),
(N'vendor1', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'vendor1@example.com', N'Vendor', N'active', NULL, N'Tran Thi B', N'0902345678', N'456 Đường B, TP.HCM'),
(N'shipper1', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'shipper1@example.com', N'Shipper', N'active', NULL, N'Le Van C', N'0903456789', N'789 Đường C, TP.HCM');

-- ============================
-- 2. CATEGORIES
-- ============================
INSERT INTO categories (name, image) VALUES
(N'Điện thoại', NULL),
(N'Laptop', NULL),
(N'Phụ kiện', NULL),
(N'Âm thanh', NULL),
(N'Gia dụng', NULL);

-- ============================
-- 3. SHOPS
-- ============================
INSERT INTO shops (user_id, name, description, logo, created_at) VALUES
(2, N'Shop Điện Thoại ABC', N'Cửa hàng chuyên bán điện thoại', NULL, GETDATE()),
(2, N'Shop Laptop XYZ', N'Cửa hàng chuyên bán laptop', NULL, GETDATE()),
(2, N'Shop Phụ Kiện Tech', N'Cửa hàng phụ kiện công nghệ', NULL, GETDATE());

-- ============================
-- 4. PRODUCTS
-- ============================
-- Điện thoại
INSERT INTO products (shop_id, category_id, name, description, image_url) VALUES
(1, 1, N'iPhone 15', N'iPhone 15 mới nhất, 128GB, 256GB, 512GB', NULL),
(1, 1, N'Samsung Galaxy S23', N'Samsung S23 mới, nhiều màu', NULL),
(1, 1, N'Xiaomi 13 Pro', N'Xiaomi 13 Pro, màn hình AMOLED', NULL);

-- Laptop
INSERT INTO products (shop_id, category_id, name, description, image_url) VALUES
(2, 2, N'MacBook Pro 16"', N'MacBook Pro 16 inch M2 Max', NULL),
(2, 2, N'Dell XPS 13', N'Dell XPS 13 2025', NULL),
(2, 2, N'Asus ROG Zephyrus', N'Laptop gaming Asus ROG Zephyrus', NULL);

-- Phụ kiện
INSERT INTO products (shop_id, category_id, name, description, image_url) VALUES
(3, 3, N'Tai nghe Bluetooth', N'Tai nghe không dây chất lượng cao', NULL),
(3, 3, N'Sạc nhanh 65W', N'Củ sạc nhanh USB-C 65W', NULL),
(3, 3, N'Bàn phím cơ', N'Bàn phím cơ gaming RGB', NULL);

-- Âm thanh
INSERT INTO products (shop_id, category_id, name, description, image_url) VALUES
(3, 4, N'Loa JBL Flip 6', N'Loa di động Bluetooth JBL Flip 6', NULL),
(3, 4, N'Sony WH-1000XM5', N'Tai nghe chống ồn Sony WH-1000XM5', NULL),
(3, 4, N'Soundbar Samsung', N'Soundbar Samsung 3.1 kênh', NULL);

-- Gia dụng
INSERT INTO products (shop_id, category_id, name, description, image_url) VALUES
(3, 5, N'Robot hút bụi', N'Robot hút bụi tự động thông minh', NULL),
(3, 5, N'Ấm đun siêu tốc', N'Ấm siêu tốc 1.7L', NULL),
(3, 5, N'Máy xay sinh tố', N'Máy xay sinh tố 2L', NULL);

-- ============================
-- 5. PRODUCT VARIANTS
-- ============================
-- Điện thoại
INSERT INTO product_variants (product_id, optionName, optionValue, price, oldPrice, stock, imageUrl) VALUES
-- iPhone 15
(1, N'iPhone 15 128GB Đen', N'128GB, Đen', 25000000, 27000000, 20, NULL),
(1, N'iPhone 15 256GB Trắng', N'256GB, Trắng', 28000000, 30000000, 15, NULL),
(1, N'iPhone 15 512GB Đỏ', N'512GB, Đỏ', 32000000, 34000000, 10, NULL),
-- Samsung S23
(2, N'S23 128GB Đen', N'128GB, Đen', 20000000, 22000000, 20, NULL),
(2, N'S23 256GB Bạc', N'256GB, Bạc', 22000000, 24000000, 10, NULL),
(2, N'S23 512GB Xanh', N'512GB, Xanh', 24000000, 26000000, 5, NULL),
-- Xiaomi 13 Pro
(3, N'Xiaomi 13 Pro 256GB', N'256GB, Đen', 19000000, 21000000, 15, NULL),
(3, N'Xiaomi 13 Pro 512GB', N'512GB, Trắng', 22000000, 24000000, 10, NULL),
(3, N'Xiaomi 13 Pro 1TB', N'1TB, Đỏ', 26000000, 28000000, 5, NULL);

-- Laptop
INSERT INTO product_variants (product_id, optionName, optionValue, price, oldPrice, stock, imageUrl) VALUES
-- MacBook Pro 16"
(4, N'MacBook Pro 16" 16GB RAM', N'16GB, 512GB SSD', 55000000, 58000000, 10, NULL),
(4, N'MacBook Pro 16" 32GB RAM', N'32GB, 1TB SSD', 65000000, 68000000, 8, NULL),
(4, N'MacBook Pro 16" 64GB RAM', N'64GB, 2TB SSD', 80000000, 85000000, 5, NULL),
-- Dell XPS 13
(5, N'Dell XPS 13 8GB RAM', N'8GB, 256GB SSD', 30000000, 32000000, 12, NULL),
(5, N'Dell XPS 13 16GB RAM', N'16GB, 512GB SSD', 40000000, 42000000, 10, NULL),
(5, N'Dell XPS 13 32GB RAM', N'32GB, 1TB SSD', 50000000, 53000000, 6, NULL),
-- Asus ROG Zephyrus
(6, N'Asus ROG 16GB RAM', N'16GB, 512GB SSD', 45000000, 48000000, 8, NULL),
(6, N'Asus ROG 32GB RAM', N'32GB, 1TB SSD', 55000000, 58000000, 6, NULL),
(6, N'Asus ROG 64GB RAM', N'64GB, 2TB SSD', 70000000, 72000000, 4, NULL);

-- Phụ kiện
INSERT INTO product_variants (product_id, optionName, optionValue, price, oldPrice, stock, imageUrl) VALUES
-- Tai nghe Bluetooth
(7, N'Tai nghe Bluetooth màu Đen', N'Đen', 1200000, 1400000, 25, NULL),
(7, N'Tai nghe Bluetooth màu Trắng', N'Trắng', 1200000, 1400000, 20, NULL),
(7, N'Tai nghe Bluetooth màu Xanh', N'Xanh', 1200000, 1400000, 15, NULL),
-- Sạc nhanh 65W
(8, N'Sạc 65W USB-C', N'USB-C', 500000, 600000, 30, NULL),
(8, N'Sạc 65W USB-A', N'USB-A', 450000, 550000, 20, NULL),
(8, N'Sạc 65W Dual Port', N'Dual', 700000, 800000, 10, NULL),
-- Bàn phím cơ
(9, N'Bàn phím cơ Red Switch', N'Red Switch', 1500000, 1700000, 20, NULL),
(9, N'Bàn phím cơ Blue Switch', N'Blue Switch', 1500000, 1700000, 15, NULL),
(9, N'Bàn phím cơ Brown Switch', N'Brown Switch', 1500000, 1700000, 10, NULL);

-- Âm thanh
INSERT INTO product_variants (product_id, optionName, optionValue, price, oldPrice, stock, imageUrl) VALUES
-- Loa JBL Flip 6
(10, N'Loa JBL Flip 6 Đen', N'Đen', 2000000, 2200000, 15, NULL),
(10, N'Loa JBL Flip 6 Xanh', N'Xanh', 2000000, 2200000, 10, NULL),
(10, N'Loa JBL Flip 6 Đỏ', N'Đỏ', 2000000, 2200000, 5, NULL),
-- Sony WH-1000XM5
(11, N'Sony WH-1000XM5 Đen', N'Đen', 9000000, 9500000, 12, NULL),
(11, N'Sony WH-1000XM5 Bạc', N'Bạc', 9500000, 10000000, 8, NULL),
(11, N'Sony WH-1000XM5 Xám', N'Xám', 9000000, 9500000, 5, NULL),
-- Soundbar Samsung
(12, N'Soundbar Samsung 3.1 kênh', N'Standard', 7000000, 7500000, 10, NULL),
(12, N'Soundbar Samsung 3.1 kênh Premium', N'Premium', 9000000, 9500000, 5, NULL),
(12, N'Soundbar Samsung 3.1 kênh Lite', N'Lite', 5000000, 5500000, 8, NULL);

-- Gia dụng
INSERT INTO product_variants (product_id, optionName, optionValue, price, oldPrice, stock, imageUrl) VALUES
-- Robot hút bụi
(13, N'Robot hút bụi Standard', N'Standard', 5000000, 5500000, 10, NULL),
(13, N'Robot hút bụi Pro', N'Pro', 7000000, 7500000, 8, NULL),
(13, N'Robot hút bụi Max', N'Max', 9000000, 9500000, 5, NULL),
-- Ấm siêu tốc
(14, N'Ấm siêu tốc 1.7L', N'Standard', 500000, 550000, 20, NULL),
(14, N'Ấm siêu tốc 2L', N'Large', 600000, 650000, 15, NULL),
(14, N'Ấm siêu tốc 1L', N'Small', 400000, 450000, 25, NULL),
-- Máy xay sinh tố
(15, N'Máy xay 1.5L', N'Small', 800000, 900000, 15, NULL),
(15, N'Máy xay 2L', N'Medium', 1000000, 1100000, 10, NULL),
(15, N'Máy xay 2.5L', N'Large', 1200000, 1300000, 8, NULL);


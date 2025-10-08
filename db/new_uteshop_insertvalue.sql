USE uteshopdb
GO

-- ============================
-- 1. USERS
-- ============================
INSERT INTO users (username, password, email, role, status, avatar, name, phone, address) VALUES
(N'customer1', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'customer1@example.com', N'User', N'active', NULL, N'Nguyen Van A', N'0901234567', N'123 Đường A, TP.HCM'),
(N'vendor1', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'vendor1@example.com', N'Vendor', N'active', NULL, N'Tran Thi B', N'0902345678', N'456 Đường B, TP.HCM'),
(N'vendor2', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'vendor2@example.com', N'Vendor', N'active', NULL, N'Le Van D', N'0904567890', N'789 Đường D, TP.HCM'),
(N'shipper1', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'shipper1@example.com', N'Shipper', N'active', NULL, N'Pham Thi E', N'0905678901', N'101 Đường E, TP.HCM'),
(N'admin', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'chuongminh3225@gmail.com', N'Admin', N'active', NULL, N'Minh Chuong', N'0905678901', N'24/7 Phung Van Cung , TP.HCM');

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
-- 1 user chỉ có 1 shop (user_id unique)
INSERT INTO shops (user_id, name, description, logo, created_at) VALUES
(2, N'Shop Điện Thoại ABC', N'Cửa hàng chuyên bán điện thoại', NULL, GETDATE()),
(3, N'Shop Laptop XYZ', N'Cửa hàng chuyên bán laptop', NULL, GETDATE()),
(4, N'Shop Phụ Kiện Tech', N'Cửa hàng phụ kiện công nghệ', NULL, GETDATE());

-- ============================
-- 4. PRODUCTS
-- ============================
-- Điện thoại (Shop 1)
INSERT INTO products (shop_id, category_id, name, description, image_url) VALUES
(1, 1, N'iPhone 15', N'iPhone 15 mới nhất, 128GB, 256GB, 512GB', NULL),
(1, 1, N'Samsung Galaxy S23', N'Samsung S23 mới, nhiều màu', NULL),
(1, 1, N'Xiaomi 13 Pro', N'Xiaomi 13 Pro, màn hình AMOLED', NULL);

-- Laptop (Shop 2)
INSERT INTO products (shop_id, category_id, name, description, image_url) VALUES
(2, 2, N'MacBook Pro 16"', N'MacBook Pro 16 inch M2 Max', NULL),
(2, 2, N'Dell XPS 13', N'Dell XPS 13 2025', NULL),
(2, 2, N'Asus ROG Zephyrus', N'Laptop gaming Asus ROG Zephyrus', NULL);

-- Phụ kiện (Shop 3)
INSERT INTO products (shop_id, category_id, name, description, image_url) VALUES
(3, 3, N'Tai nghe Bluetooth', N'Tai nghe không dây chất lượng cao', NULL),
(3, 3, N'Sạc nhanh 65W', N'Củ sạc nhanh USB-C 65W', NULL),
(3, 3, N'Bàn phím cơ', N'Bàn phím cơ gaming RGB', NULL);

-- Âm thanh (Shop 3)
INSERT INTO products (shop_id, category_id, name, description, image_url) VALUES
(3, 4, N'Loa JBL Flip 6', N'Loa di động Bluetooth JBL Flip 6', NULL),
(3, 4, N'Sony WH-1000XM5', N'Tai nghe chống ồn Sony WH-1000XM5', NULL),
(3, 4, N'Soundbar Samsung', N'Soundbar Samsung 3.1 kênh', NULL);

-- Gia dụng (Shop 3)
INSERT INTO products (shop_id, category_id, name, description, image_url) VALUES
(3, 5, N'Robot hút bụi', N'Robot hút bụi tự động thông minh', NULL),
(3, 5, N'Ấm đun siêu tốc', N'Ấm siêu tốc 1.7L', NULL),
(3, 5, N'Máy xay sinh tố', N'Máy xay sinh tố 2L', NULL);

-- ============================
-- 5. PRODUCT VARIANTS
-- ============================
-- Điện thoại
INSERT INTO product_variants (product_id, optionName, optionValue, price, oldPrice, stock, imageUrl) VALUES
(1, N'iPhone 15 128GB Đen', N'128GB, Đen', 25000000, 27000000, 20, NULL),
(1, N'iPhone 15 256GB Trắng', N'256GB, Trắng', 28000000, 30000000, 15, NULL),
(1, N'iPhone 15 512GB Đỏ', N'512GB, Đỏ', 32000000, 34000000, 10, NULL);

INSERT INTO product_variants (product_id, optionName, optionValue, price, oldPrice, stock, imageUrl) VALUES
(2, N'Samsung S23 128GB Đen', N'128GB, Đen', 20000000, 22000000, 20, NULL),
(2, N'Samsung S23 256GB Bạc', N'256GB, Bạc', 22000000, 24000000, 10, NULL),
(2, N'Samsung S23 512GB Xanh', N'512GB, Xanh', 24000000, 26000000, 5, NULL);

INSERT INTO product_variants (product_id, optionName, optionValue, price, oldPrice, stock, imageUrl) VALUES
(3, N'Xiaomi 13 Pro 256GB', N'256GB, Đen', 19000000, 21000000, 15, NULL),
(3, N'Xiaomi 13 Pro 512GB', N'512GB, Trắng', 22000000, 24000000, 10, NULL),
(3, N'Xiaomi 13 Pro 1TB', N'1TB, Đỏ', 26000000, 28000000, 5, NULL);

-- Laptop
INSERT INTO product_variants (product_id, optionName, optionValue, price, oldPrice, stock, imageUrl) VALUES
(4, N'MacBook Pro 16" 16GB RAM', N'16GB, 512GB SSD', 55000000, 58000000, 10, NULL),
(4, N'MacBook Pro 16" 32GB RAM', N'32GB, 1TB SSD', 65000000, 68000000, 8, NULL),
(4, N'MacBook Pro 16" 64GB RAM', N'64GB, 2TB SSD', 80000000, 85000000, 5, NULL);

INSERT INTO product_variants (product_id, optionName, optionValue, price, oldPrice, stock, imageUrl) VALUES
(5, N'Dell XPS 13 8GB RAM', N'8GB, 256GB SSD', 30000000, 32000000, 12, NULL),
(5, N'Dell XPS 13 16GB RAM', N'16GB, 512GB SSD', 40000000, 42000000, 10, NULL),
(5, N'Dell XPS 13 32GB RAM', N'32GB, 1TB SSD', 50000000, 53000000, 6, NULL);

INSERT INTO product_variants (product_id, optionName, optionValue, price, oldPrice, stock, imageUrl) VALUES
(6, N'Asus ROG 16GB RAM', N'16GB, 512GB SSD', 45000000, 48000000, 8, NULL),
(6, N'Asus ROG 32GB RAM', N'32GB, 1TB SSD', 55000000, 58000000, 6, NULL),
(6, N'Asus ROG 64GB RAM', N'64GB, 2TB SSD', 70000000, 72000000, 4, NULL);

-- Phụ kiện
INSERT INTO product_variants (product_id, optionName, optionValue, price, oldPrice, stock, imageUrl) VALUES
(7, N'Tai nghe Bluetooth Đen', N'Đen', 1200000, 1400000, 25, NULL),
(7, N'Tai nghe Bluetooth Trắng', N'Trắng', 1200000, 1400000, 20, NULL),
(7, N'Tai nghe Bluetooth Xanh', N'Xanh', 1200000, 1400000, 15, NULL);

INSERT INTO product_variants (product_id, optionName, optionValue, price, oldPrice, stock, imageUrl) VALUES
(8, N'Sạc 65W USB-C', N'USB-C', 500000, 600000, 30, NULL),
(8, N'Sạc 65W USB-A', N'USB-A', 450000, 550000, 20, NULL),
(8, N'Sạc 65W Dual Port', N'Dual', 700000, 800000, 10, NULL);

INSERT INTO product_variants (product_id, optionName, optionValue, price, oldPrice, stock, imageUrl) VALUES
(9, N'Bàn phím cơ Red Switch', N'Red Switch', 1500000, 1700000, 20, NULL),
(9, N'Bàn phím cơ Blue Switch', N'Blue Switch', 1500000, 1700000, 15, NULL),
(9, N'Bàn phím cơ Brown Switch', N'Brown Switch', 1500000, 1700000, 10, NULL);

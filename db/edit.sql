-- thêm cột shop id cho bảng orders
ALTER TABLE orders
ADD shop_id INT;

-- Tạo khóa ngoại
ALTER TABLE orders
ADD CONSTRAINT FK_orders_shops FOREIGN KEY (shop_id)
REFERENCES shops(shop_id);


-- Dữ liệu mẫu 
UPDATE orders SET shop_id = 1 WHERE shop_id IS NULL;



-- -------------- SỬA dữ liệu bảng ProductImage ------------------------
-- Xóa toàn bộ dữ liệu trong bảng ProductImage và reset IDENTITY về 1
TRUNCATE TABLE ProductImage;


-- ẢNH CHÍNH (is_main = 1)
INSERT INTO ProductImage (image_url, is_main, product_id)
SELECT image_url, 1 AS is_main, product_id
FROM products;

-- ẢNH PHỤ (is_main = 0)
INSERT INTO ProductImage (image_url, is_main, product_id)
SELECT imageUrl, 0 AS is_main, product_id
FROM product_variants;


-- -------------- SỬA optionName bảng VariantProduct ---------------------------
UPDATE product_variants
SET optionName = N'Màu sắc'
WHERE optionName LIKE '%Màu s?c%';

UPDATE product_variants
SET optionName = N'Cấu hình'
WHERE optionName LIKE '%C?u hình%';

UPDATE product_variants
SET optionName = N'Kích thước'
WHERE optionName LIKE '%Kích thu?c%';

UPDATE product_variants
SET optionName = N'Kết nối'
WHERE optionName LIKE '%K?t n?i%';

UPDATE product_variants
SET optionName = N'Dung lượng'
WHERE optionName LIKE '%Dung lu?ng%';

UPDATE product_variants
SET optionName = N'Phiên bản'
WHERE optionName LIKE '%Phiên b?n';

UPDATE product_variants
SET optionName = N'Nhà sản xuất'
WHERE optionName LIKE '%Nhà s?n xu?t';


-- Thêm bảng carriers
INSERT INTO carriers (carrier_name, carrier_fee, carrier_description)
VALUES
(N'Giao Hàng Nhanh (GHN)', 25000, N'Dịch vụ giao hàng nhanh trong nước, tốc độ 1-2 ngày.'),
(N'Giao Hàng Tiết Kiệm (GHTK)', 20000, N'Dịch vụ tiết kiệm, thời gian 2-4 ngày, toàn quốc.'),
(N'VNPost', 15000, N'Dịch vụ chuyển phát nhanh của bưu điện Việt Nam.'),
(N'J&T Express', 22000, N'Đơn vị vận chuyển phổ biến, hỗ trợ thu hộ COD.'),
(N'Shopee Express', 18000, N'Dịch vụ giao hàng nội vùng dành riêng cho sàn TMĐT.'),
(N'Ninja Van', 23000, N'Giao hàng tiêu chuẩn, phủ sóng toàn quốc.'),
(N'Best Express', 21000, N'Dịch vụ vận chuyển nhanh, hỗ trợ giao tận tay.'),
(N'FedEx Vietnam', 60000, N'Vận chuyển quốc tế, tốc độ cao.'),
(N'DHL Express', 70000, N'Vận chuyển quốc tế uy tín, giao tận nơi.'),
(N'UPS Vietnam', 65000, N'Dịch vụ giao nhận quốc tế chuyên nghiệp.');


-- -----Thêm dữ liệu cho cột mới carrier_id trong bảng deliveries liên kết đến carriers
UPDATE deliveries
SET carrier_id = 1  -- GHN
WHERE delivery_id IN (1, 2);

-- Gán GHTK cho các đơn đang giao
UPDATE deliveries
SET carrier_id = 2  -- GHTK
WHERE delivery_id IN (3, 7);

-- Gán VNPost cho các đơn đã nhận đơn
UPDATE deliveries
SET carrier_id = 3  -- VNPost
WHERE delivery_id IN (4);

-- Gán J&T Express cho các đơn giao nhanh
UPDATE deliveries
SET carrier_id = 4  -- J&T Express
WHERE delivery_id IN (5, 6, 8, 9);


---- Cập nhật cột product_id trong promotion (khuyến mại cho mỗi sản phẩm cố định)
UPDATE promotions SET product_id = 1 WHERE promotion_id = 1;
UPDATE promotions SET product_id = 2 WHERE promotion_id = 2;
UPDATE promotions SET product_id = 3 WHERE promotion_id = 3;
UPDATE promotions SET product_id = 4 WHERE promotion_id = 4;
UPDATE promotions SET product_id = 5 WHERE promotion_id = 5;
UPDATE promotions SET product_id = 6 WHERE promotion_id = 6;
UPDATE promotions SET product_id = 7 WHERE promotion_id = 7;
UPDATE promotions SET product_id = 8 WHERE promotion_id = 8;
UPDATE promotions SET product_id = 9 WHERE promotion_id = 9;
UPDATE promotions SET product_id = 10 WHERE promotion_id = 10;
UPDATE promotions SET product_id = 11 WHERE promotion_id = 11;
UPDATE promotions SET product_id = 12 WHERE promotion_id = 12;
UPDATE promotions SET product_id = 13 WHERE promotion_id = 13;
UPDATE promotions SET product_id = 14 WHERE promotion_id = 14;


-- Cho phép shipper_id null khi tạo delivery: 
ALTER TABLE deliveries 
ALTER COLUMN shipper_id INT NULL;

-- Thêm thông tin deliveries: 
INSERT INTO deliveries (shipper_id, order_id, status, note_text, created_at, carrier_id) VALUES
(NULL, 10, N'Chờ xử lý', NULL, DATEADD(day, -11, GETDATE()), NULL),
(NULL, 11, N'Chờ xử lý', NULL, DATEADD(day, -15, GETDATE()),NULL),
(NULL, 11, N'Chờ xử lý', NULL, DATEADD(day, -5, GETDATE()),NULL),
(NULL, 5, N'Chờ xử lý', NULL, DATEADD(day, -2, GETDATE()),NULL);




-- Update table users: [20/10/25]
ALTER TABLE users ADD google_id VARCHAR(255);

--  Tạo index cho google_id để tăng tốc độ truy vấn (và chỉ unique nếu không NULL)
CREATE UNIQUE INDEX idx_users_google_id_notnull
ON users(google_id)
WHERE google_id IS NOT NULL;

--  Tạo index cho email nếu chưa có
IF NOT EXISTS (
    SELECT 1 FROM sys.indexes WHERE name = 'idx_users_email' AND object_id = OBJECT_ID('users')
)
BEGIN
    CREATE INDEX idx_users_email ON users(email);
END

-- cho phep pass null (dang nhap = gg)
ALTER TABLE users ALTER COLUMN password VARCHAR(255) NULL;

-- pass or id gg not null 
ALTER TABLE users
ADD CONSTRAINT chk_auth_method 
CHECK ([password] IS NOT NULL OR [google_id] IS NOT NULL);

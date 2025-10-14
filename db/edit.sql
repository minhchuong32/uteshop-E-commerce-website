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





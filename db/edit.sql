-- thêm cột shop id cho bảng orders
ALTER TABLE orders
ADD shop_id INT;

-- Tạo khóa ngoại
ALTER TABLE orders
ADD CONSTRAINT FK_orders_shops FOREIGN KEY (shop_id)
REFERENCES shops(shop_id);


-- Dữ liệu mẫu 
UPDATE orders SET shop_id = 1 WHERE shop_id IS NULL;





-- chỉnh sửa dữ liệu bảng ProductImage
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

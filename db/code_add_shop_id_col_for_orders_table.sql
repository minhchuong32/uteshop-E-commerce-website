ALTER TABLE orders
ADD shop_id INT;

-- Tạo khóa ngoại
ALTER TABLE orders
ADD CONSTRAINT FK_orders_shops FOREIGN KEY (shop_id)
REFERENCES shops(shop_id);


-- Dữ liệu mẫu 
UPDATE orders SET shop_id = 1 WHERE shop_id IS NULL;
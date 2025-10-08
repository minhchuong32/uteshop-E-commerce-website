-- ==================== BẢNG USERS ====================
INSERT INTO users (username, password, email, role, status, avatar, name, phone, address) VALUES
(N'admin', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'chuongminh3225@gmail.com', N'Admin', N'active', N'/avatars/admin.jpg', N'Quản trị viên', N'0901234567', N'123 Nguyễn Huệ, Q1, TP.HCM'),
(N'vendor1', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'vendor1@techshop.vn', N'Vendor', N'active', N'/avatars/vendor1.jpg', N'Nguyễn Văn A', N'0902234567', N'456 Lê Lợi, Q1, TP.HCM'),
(N'vendor2', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'vendor2@techshop.vn', N'Vendor', N'active', N'/avatars/vendor2.jpg', N'Trần Thị B', N'0903234567', N'789 Điện Biên Phủ, Q3, TP.HCM'),
(N'shipper1', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'shipper1@techshop.vn', N'Shipper', N'active', N'/avatars/shipper1.jpg', N'Lê Văn C', N'0904234567', N'321 Cách Mạng Tháng 8, Q10, TP.HCM'),
(N'shipper2', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'shipper2@techshop.vn', N'Shipper', N'active', N'/avatars/shipper2.jpg', N'Phạm Thị D', N'0905234567', N'654 Võ Văn Tần, Q3, TP.HCM'),
(N'customer1', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'customer1@gmail.com', N'User', N'active', N'/avatars/customer1.jpg', N'Hoàng Văn E', N'0906234567', N'111 Lý Tự Trọng, Q1, TP.HCM'),
(N'customer2', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'customer2@gmail.com', N'User', N'active', N'/avatars/customer2.jpg', N'Vũ Thị F', N'0907234567', N'222 Hai Bà Trưng, Q3, TP.HCM'),
(N'customer3', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'customer3@gmail.com', N'User', N'active', N'/avatars/customer3.jpg', N'Đỗ Văn G', N'0908234567', N'333 Nguyễn Đình Chiểu, Q1, TP.HCM'),
(N'customer4', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'customer4@gmail.com', N'User', N'active', N'/avatars/customer4.jpg', N'Bùi Thị H', N'0909234567', N'444 Trần Hưng Đạo, Q5, TP.HCM'),
(N'customer5', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'customer5@gmail.com', N'User', N'active', N'/avatars/customer5.jpg', N'Ngô Văn I', N'0910234567', N'555 Pasteur, Q1, TP.HCM'),
(N'customer6', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'customer6@gmail.com', N'User', N'active', N'/avatars/customer6.jpg', N'Lý Thị K', N'0911234567', N'666 Nam Kỳ Khởi Nghĩa, Q3, TP.HCM'),
(N'customer7', N'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3', N'customer7@gmail.com', N'User', N'active', N'/avatars/customer7.jpg', N'Trương Văn L', N'0912234567', N'777 Võ Thị Sáu, Q3, TP.HCM');

-- ==================== BẢNG CATEGORIES ====================
INSERT INTO categories (name, image) VALUES
(N'Laptop', N'/images/categories/laptop.jpg'),
(N'Điện thoại', N'/images/categories/phone.jpg'),
(N'Tablet', N'/images/categories/tablet.jpg'),
(N'Đồng hồ thông minh', N'/images/categories/smartwatch.jpg'),
(N'Tai nghe', N'/images/categories/headphone.jpg'),
(N'Phụ kiện', N'/images/categories/accessory.jpg'),
(N'PC & Màn hình', N'/images/categories/pc.jpg'),
(N'Gaming Gear', N'/images/categories/gaming.jpg'),
(N'Camera & Drone', N'/images/categories/camera.jpg'),
(N'Smart Home', N'/images/categories/smarthome.jpg'),
(N'Loa & Audio', N'/images/categories/speaker.jpg'),
(N'Linh kiện máy tính', N'/images/categories/component.jpg');

-- ==================== BẢNG SHOPS ====================
INSERT INTO shops (user_id, name, description, logo, created_at) VALUES
(2, N'TechWorld Store', N'Chuyên cung cấp laptop, PC gaming cao cấp với giá tốt nhất thị trường. Bảo hành chính hãng, hỗ trợ trả góp 0%.', N'/images/shops/techworld-logo.png', GETDATE()),
(3, N'Mobile Galaxy', N'Điện thoại - Tablet - Smartwatch chính hãng. Cam kết giá rẻ nhất, thu cũ đổi mới, trả góp 0% lãi suất.', N'/images/shops/mobilegalaxy-logo.png', GETDATE());

-- ==================== BẢNG PRODUCTS ====================
INSERT INTO products (shop_id, category_id, name, description, image_url) VALUES
-- Shop 1: TechWorld Store - Laptop & PC
(1, 1, N'MacBook Pro 14 M3 Pro', N'Chip Apple M3 Pro 11-core CPU, 14-core GPU, RAM 18GB, SSD 512GB, Màn hình Liquid Retina XDR 14.2 inch', N'/images/products/macbook-pro-14-m3.jpg'),
(1, 1, N'Dell XPS 15 9530', N'Intel Core i7-13700H, RTX 4060 8GB, RAM 32GB DDR5, SSD 1TB, Màn hình 15.6 inch OLED 3.5K', N'/images/products/dell-xps-15.jpg'),
(1, 1, N'ASUS ROG Zephyrus G14', N'AMD Ryzen 9 7940HS, RTX 4060 8GB, RAM 16GB, SSD 1TB, Màn hình 14 inch QHD+ 165Hz', N'/images/products/asus-rog-g14.jpg'),
(1, 1, N'Lenovo ThinkPad X1 Carbon Gen 11', N'Intel Core i7-1365U, Intel Iris Xe, RAM 16GB, SSD 512GB, Màn hình 14 inch 2.8K OLED', N'/images/products/thinkpad-x1-carbon.jpg'),
(1, 7, N'iMac 24 inch M3', N'Chip Apple M3 8-core CPU, 10-core GPU, RAM 8GB, SSD 256GB, Màn hình Retina 4.5K 24 inch', N'/images/products/imac-24-m3.jpg'),
(1, 7, N'LG UltraGear 27GN950', N'Màn hình gaming 27 inch 4K UHD, Nano IPS, 144Hz, 1ms, G-Sync Compatible, HDR 600', N'/images/products/lg-ultragear-27.jpg'),
(1, 8, N'Logitech G Pro X Superlight', N'Chuột gaming wireless siêu nhẹ 63g, sensor HERO 25K, 70h pin, kết nối LIGHTSPEED', N'/images/products/logitech-gpro-x.jpg'),
(1, 8, N'Razer BlackWidow V4 Pro', N'Bàn phím cơ gaming, Razer Green Switch, RGB Chroma, wireless 2.4GHz/Bluetooth', N'/images/products/razer-blackwidow-v4.jpg'),
(1, 12, N'NVIDIA GeForce RTX 4080', N'Card đồ họa 16GB GDDR6X, CUDA Cores 9728, Boost Clock 2.51 GHz, hỗ trợ Ray Tracing', N'/images/products/rtx-4080.jpg'),
(1, 12, N'AMD Ryzen 9 7950X', N'CPU 16 nhân 32 luồng, xung nhịp base 4.5GHz boost 5.7GHz, AM5 socket, TDP 170W', N'/images/products/ryzen-9-7950x.jpg'),

-- Shop 2: Mobile Galaxy - Phone, Tablet, Smartwatch
(2, 2, N'iPhone 15 Pro Max', N'Chip A17 Pro, Camera 48MP, Zoom quang 5x, Titanium, Dynamic Island, USB-C, 256GB', N'/images/products/iphone-15-pro-max.jpg'),
(2, 2, N'Samsung Galaxy S24 Ultra', N'Snapdragon 8 Gen 3, Camera 200MP, S Pen, Màn hình Dynamic AMOLED 2X 6.8 inch', N'/images/products/samsung-s24-ultra.jpg'),
(2, 2, N'Xiaomi 14 Pro', N'Snapdragon 8 Gen 3, Camera Leica 50MP, Màn hình AMOLED 6.73 inch 120Hz, sạc nhanh 120W', N'/images/products/xiaomi-14-pro.jpg'),
(2, 2, N'Google Pixel 8 Pro', N'Chip Google Tensor G3, Camera AI 50MP, Màn hình LTPO OLED 6.7 inch 120Hz, Android 14', N'/images/products/pixel-8-pro.jpg'),
(2, 3, N'iPad Pro 12.9 M2', N'Chip Apple M2, Màn hình Liquid Retina XDR 12.9 inch, Face ID, 5G, 256GB', N'/images/products/ipad-pro-129.jpg'),
(2, 3, N'Samsung Galaxy Tab S9 Ultra', N'Snapdragon 8 Gen 2, Màn hình Dynamic AMOLED 14.6 inch 120Hz, S Pen, IP68', N'/images/products/tab-s9-ultra.jpg'),
(2, 4, N'Apple Watch Ultra 2', N'Chip S9, Màn hình Retina LTPO OLED 49mm, Titanium, GPS+Cellular, WR100m', N'/images/products/apple-watch-ultra2.jpg'),
(2, 4, N'Samsung Galaxy Watch 6 Classic', N'Exynos W930, Màn hình Super AMOLED 1.5 inch, Wear OS, viền xoay vật lý', N'/images/products/galaxy-watch6-classic.jpg'),
(2, 5, N'Sony WH-1000XM5', N'Tai nghe chống ồn cao cấp, LDAC, Hi-Res Audio, pin 30h, Multipoint connection', N'/images/products/sony-wh1000xm5.jpg'),
(2, 5, N'AirPods Pro Gen 2', N'Chip H2, Chống ồn chủ động ANC, Transparency mode, MagSafe charging, USB-C', N'/images/products/airpods-pro-2.jpg');

-- ==================== BẢNG PRODUCT_VARIANTS ====================
-- MacBook Pro 14 M3 Pro variants
INSERT INTO product_variants (product_id, optionName, optionValue, stock, price, oldPrice, imageUrl) VALUES
(1, N'Màu sắc', N'Space Black', 15, 52990000, 54990000, N'/images/variants/mbp14-black.jpg'),
(1, N'Màu sắc', N'Silver', 20, 52990000, 54990000, N'/images/variants/mbp14-silver.jpg'),

-- Dell XPS 15 variants
(2, N'Cấu hình', N'i7/32GB/1TB', 12, 48990000, 51990000, N'/images/variants/dell-xps15.jpg'),
(2, N'Cấu hình', N'i9/64GB/2TB', 8, 69990000, 74990000, N'/images/variants/dell-xps15-max.jpg'),

-- ASUS ROG G14 variants
(3, N'Màu sắc', N'Moonlight White', 18, 42990000, 45990000, N'/images/variants/rog-g14-white.jpg'),
(3, N'Màu sắc', N'Eclipse Gray', 15, 42990000, 45990000, N'/images/variants/rog-g14-gray.jpg'),

-- ThinkPad X1 Carbon variants
(4, N'Cấu hình', N'i7/16GB/512GB', 20, 38990000, 41990000, N'/images/variants/thinkpad-x1.jpg'),

-- iMac 24 variants
(5, N'Màu sắc', N'Blue', 10, 36990000, 38990000, N'/images/variants/imac24-blue.jpg'),
(5, N'Màu sắc', N'Silver', 12, 36990000, 38990000, N'/images/variants/imac24-silver.jpg'),
(5, N'Màu sắc', N'Pink', 8, 36990000, 38990000, N'/images/variants/imac24-pink.jpg'),

-- LG Monitor variant
(6, N'Kích thước', N'27 inch', 25, 18990000, 21990000, N'/images/variants/lg-monitor-27.jpg'),

-- Logitech Mouse variants
(7, N'Màu sắc', N'Black', 50, 3290000, 3690000, N'/images/variants/gpro-black.jpg'),
(7, N'Màu sắc', N'White', 35, 3290000, 3690000, N'/images/variants/gpro-white.jpg'),

-- Razer Keyboard variant
(8, N'Switch', N'Green Switch', 30, 5990000, 6490000, N'/images/variants/razer-bw-v4.jpg'),

-- RTX 4080 variant
(9, N'Nhà sản xuất', N'ASUS TUF Gaming', 15, 28990000, 32990000, N'/images/variants/rtx4080-asus.jpg'),

-- Ryzen 9 variant
(10, N'Phiên bản', N'Box', 20, 13990000, 15990000, N'/images/variants/ryzen9-box.jpg'),

-- iPhone 15 Pro Max variants
(11, N'Dung lượng', N'256GB - Natural Titanium', 25, 33990000, 34990000, N'/images/variants/iphone15pm-natural.jpg'),
(11, N'Dung lượng', N'512GB - Blue Titanium', 18, 38990000, 39990000, N'/images/variants/iphone15pm-blue.jpg'),
(11, N'Dung lượng', N'1TB - Black Titanium', 10, 45990000, 46990000, N'/images/variants/iphone15pm-black.jpg'),

-- Samsung S24 Ultra variants
(12, N'Dung lượng', N'256GB - Titanium Gray', 30, 29990000, 31990000, N'/images/variants/s24u-gray.jpg'),
(12, N'Dung lượng', N'512GB - Titanium Black', 20, 33990000, 35990000, N'/images/variants/s24u-black.jpg'),

-- Xiaomi 14 Pro variants
(13, N'Màu sắc', N'Black', 35, 21990000, 23990000, N'/images/variants/xiaomi14-black.jpg'),
(13, N'Màu sắc', N'White', 25, 21990000, 23990000, N'/images/variants/xiaomi14-white.jpg'),

-- Pixel 8 Pro variants
(14, N'Dung lượng', N'128GB - Bay', 20, 24990000, 26990000, N'/images/variants/pixel8-bay.jpg'),
(14, N'Dung lượng', N'256GB - Obsidian', 15, 27990000, 29990000, N'/images/variants/pixel8-obsidian.jpg'),

-- iPad Pro variants
(15, N'Kết nối', N'WiFi 256GB - Space Gray', 18, 28990000, 30990000, N'/images/variants/ipad-pro-gray.jpg'),
(15, N'Kết nối', N'5G 512GB - Silver', 12, 36990000, 38990000, N'/images/variants/ipad-pro-silver.jpg'),

-- Tab S9 Ultra variant
(16, N'Dung lượng', N'256GB - Graphite', 15, 26990000, 28990000, N'/images/variants/tabs9u-graphite.jpg'),

-- Apple Watch Ultra 2 variants
(17, N'Dây đeo', N'Alpine Loop', 20, 21990000, 22990000, N'/images/variants/awu2-alpine.jpg'),
(17, N'Dây đeo', N'Ocean Band', 15, 21990000, 22990000, N'/images/variants/awu2-ocean.jpg'),

-- Galaxy Watch 6 Classic variants
(18, N'Kích thước', N'43mm - Black', 25, 8990000, 9990000, N'/images/variants/gw6c-43-black.jpg'),
(18, N'Kích thước', N'47mm - Silver', 20, 9990000, 10990000, N'/images/variants/gw6c-47-silver.jpg'),

-- Sony WH-1000XM5 variants
(19, N'Màu sắc', N'Black', 40, 8990000, 9990000, N'/images/variants/sony-xm5-black.jpg'),
(19, N'Màu sắc', N'Silver', 30, 8990000, 9990000, N'/images/variants/sony-xm5-silver.jpg'),

-- AirPods Pro 2 variant
(20, N'Phiên bản', N'USB-C', 50, 6490000, 6990000, N'/images/variants/airpods-pro2-usbc.jpg');

-- ==================== BẢNG ORDERS ====================
INSERT INTO orders (user_id, total_amount, status, payment_method, created_at, address) VALUES 
(6, 52990000, N'Đã giao', N'VNPay', DATEADD(day, -15, GETDATE()), N'111 Lý Tự Trọng, Q1, TP.HCM'),
(7, 33990000, N'Đã giao', N'MoMo', DATEADD(day, -12, GETDATE()), N'222 Hai Bà Trưng, Q3, TP.HCM'),
(8, 42990000, N'Đang giao', N'COD', DATEADD(day, -3, GETDATE()), N'333 Nguyễn Đình Chiểu, Q1, TP.HCM'),
(9, 21990000, N'Đã xác nhận', N'VNPay', DATEADD(day, -1, GETDATE()), N'444 Trần Hưng Đạo, Q5, TP.HCM'),
(10, 28990000, N'Mới', N'COD', GETDATE(), N'555 Pasteur, Q1, TP.HCM'),
(11, 8990000, N'Đã giao', N'MoMo', DATEADD(day, -20, GETDATE()), N'666 Nam Kỳ Khởi Nghĩa, Q3, TP.HCM'),
(12, 48990000, N'Đã giao', N'VNPay', DATEADD(day, -18, GETDATE()), N'777 Võ Thị Sáu, Q3, TP.HCM'),
(6, 36990000, N'Đã hủy', N'COD', DATEADD(day, -10, GETDATE()), N'111 Lý Tự Trọng, Q1, TP.HCM'),
(7, 24990000, N'Đang giao', N'VNPay', DATEADD(day, -2, GETDATE()), N'222 Hai Bà Trưng, Q3, TP.HCM'),
(8, 6490000, N'Đã xác nhận', N'MoMo', DATEADD(hour, -12, GETDATE()), N'333 Nguyễn Đình Chiểu, Q1, TP.HCM'),
(9, 38990000, N'Mới', N'COD', GETDATE(), N'444 Trần Hưng Đạo, Q5, TP.HCM'),
(10, 29990000, N'Đã giao', N'VNPay', DATEADD(day, -25, GETDATE()), N'555 Pasteur, Q1, TP.HCM');


-- ==================== BẢNG ORDER_DETAILS ====================
INSERT INTO order_details (order_id, product_variant_id, quantity, price) VALUES
(1, CAST(1 AS BIGINT), 1, 52990000),
(2, CAST(21 AS BIGINT), 1, 33990000),
(3, CAST(5 AS BIGINT), 1, 42990000),
(4, CAST(25 AS BIGINT), 1, 21990000),
(5, CAST(13 AS BIGINT), 1, 28990000),
(6, CAST(31 AS BIGINT), 1, 8990000),
(7, CAST(3 AS BIGINT), 1, 48990000),
(8, CAST(8 AS BIGINT), 1, 36990000),
(9, CAST(27 AS BIGINT), 1, 24990000),
(10, CAST(35 AS BIGINT), 1, 6490000),
(11, CAST(22 AS BIGINT), 1, 38990000),
(12, CAST(23 AS BIGINT), 1, 29990000);

-- ==================== BẢNG CART_ITEMS ====================
INSERT INTO cart_items (user_id, product_variant_id, quantity, price) VALUES
(6, 2, 1, 52990000),
(6, 11, 1, 3290000),
(7, 24, 1, 33990000),
(7, 32, 1, 8990000),
(8, 28, 1, 27990000),
(9, 7, 1, 36990000),
(10, 19, 1, 21990000),
(11, 30, 1, 9990000),
(12, 4, 1, 69990000),
(6, 26, 1, 21990000),
(7, 15, 1, 28990000),
(8, 35, 2, 6490000);

-- ==================== BẢNG REVIEWS ====================
INSERT INTO reviews (product_id, user_id, rating, comment, media_url) VALUES
(1, 6, 5, N'MacBook Pro M3 Pro quá đỉnh! Hiệu năng mượt mà, màn hình đẹp xuất sắc, pin trâu. Đáng đồng tiền bát gạo!', N'/images/reviews/mbp-review1.jpg'),
(2, 12, 5, N'Dell XPS 15 màn hình OLED tuyệt vời, RTX 4060 chơi game mượt, thiết kế cao cấp sang trọng.', N'/images/reviews/dell-review1.jpg'),
(3, 8, 4, N'ROG G14 nhỏ gọn nhưng mạnh mẽ, chơi AAA game tốt, pin ổn. Trừ 1 sao vì hơi nóng khi chơi game nặng.', NULL),
(11, 7, 5, N'iPhone 15 Pro Max quá xuất sắc! Camera zoom 5x đỉnh cao, Titanium nhẹ, A17 Pro nhanh vượt trội.', N'/images/reviews/ip15pm-review1.jpg'),
(11, 11, 4, N'Máy đẹp, mượt, pin tốt. Nhưng giá hơi cao so với cấu hình. Camera thực sự ấn tượng!', NULL),
(12, 10, 5, N'S24 Ultra xứng đáng flagship! S Pen tiện lợi, camera 200MP sắc nét, Snapdragon 8 Gen 3 quá mạnh.', N'/images/reviews/s24u-review1.jpg'),
(13, 6, 5, N'Xiaomi 14 Pro giá tốt nhất phân khúc cao cấp. Camera Leica đẹp, sạc nhanh 120W thần thánh!', NULL),
(19, 11, 5, N'Sony WH-1000XM5 chống ồn đỉnh cao, âm thanh chi tiết, đeo cả ngày không mỏi tai. Đáng tiền!', N'/images/reviews/sony-review1.jpg'),
(20, 8, 4, N'AirPods Pro 2 chống ồn tốt, tích hợp Apple ecosystem hoàn hảo. Giá hơi cao nhưng chất lượng xứng đáng.', NULL),
(5, 9, 5, N'iMac 24 inch M3 đẹp lung linh, màn Retina 4.5K sắc nét, hiệu năng ổn cho văn phòng và sáng tạo.', N'/images/reviews/imac-review1.jpg'),
(6, 12, 5, N'Màn hình gaming LG 27 inch 4K 144Hz tuyệt vời! Màu sắc chính xác, độ trễ thấp, chơi game mượt mà.', N'/images/reviews/lg-monitor-review1.jpg'),
(7, 10, 5, N'Chuột Logitech G Pro X Superlight nhẹ như không, sensor chính xác, pin lâu. Top choice cho pro gamer!', NULL);

-- ==================== BẢNG DELIVERIES ====================
INSERT INTO deliveries (shipper_id, order_id, status, note_text, delivery_note, created_at) VALUES
(4, 1, N'Đã giao', N'Giao hàng thành công, khách hài lòng', N'Đã ký nhận, thanh toán đầy đủ', DATEADD(day, -14, GETDATE())),
(5, 2, N'Đã giao', N'Giao đúng hẹn, khách kiểm tra kỹ', N'Đã nhận hàng và thanh toán', DATEADD(day, -11, GETDATE())),
(4, 3, N'Đang giao', N'Đang trên đường giao cho khách', N'Dự kiến giao trong 2h tới', DATEADD(day, -2, GETDATE())),
(5, 4, N'Đã gán', N'Đã nhận đơn, chuẩn bị giao hàng', N'Sẽ liên hệ khách trong 1h', DATEADD(hour, -23, GETDATE())),
(4, 6, N'Đã giao', N'Giao hàng nhanh, khách hài lòng', N'Đã ký nhận', DATEADD(day, -19, GETDATE())),
(5, 7, N'Đã giao', N'Giao thành công, kiểm tra hàng kỹ', N'Đã thanh toán và nhận hàng', DATEADD(day, -17, GETDATE())),
(4, 8, N'Đã hủy', N'Khách hủy đơn, không nhận hàng nữa', N'Đã hoàn hàng về kho', DATEADD(day, -9, GETDATE())),
(5, 9, N'Đang giao', N'Đang trên đường tới địa chỉ khách', N'Khách đã xác nhận nhận hàng hôm nay', DATEADD(day, -1, GETDATE())),
(4, 12, N'Đã giao', N'Giao hàng thành công, khách đánh giá tốt', N'Đã ký nhận và thanh toán VNPay', DATEADD(day, -24, GETDATE()));

-- ==================== BẢNG PROMOTIONS ====================
INSERT INTO promotions (shop_id, discount_type, value, start_date, end_date) VALUES
(1, N'percent', 10, DATEADD(day, -5, GETDATE()), DATEADD(day, 25, GETDATE())),
(1, N'fixed', 2000000, DATEADD(day, -10, GETDATE()), DATEADD(day, 20, GETDATE())),
(1, N'percent', 15, DATEADD(day, -3, GETDATE()), DATEADD(day, 27, GETDATE())),
(2, N'percent', 12, DATEADD(day, -7, GETDATE()), DATEADD(day, 23, GETDATE())),
(2, N'fixed', 1500000, DATEADD(day, -2, GETDATE()), DATEADD(day, 28, GETDATE())),
(2, N'percent', 8, DATEADD(day, -15, GETDATE()), DATEADD(day, 15, GETDATE())),
(1, N'percent', 20, DATEADD(day, 1, GETDATE()), DATEADD(day, 31, GETDATE())),
(2, N'fixed', 3000000, DATEADD(day, 2, GETDATE()), DATEADD(day, 32, GETDATE())),
(1, N'percent', 5, DATEADD(day, -20, GETDATE()), DATEADD(day, 10, GETDATE())),
(2, N'percent', 18, DATEADD(day, -1, GETDATE()), DATEADD(day, 29, GETDATE())),
(1, N'fixed', 5000000, DATEADD(day, 5, GETDATE()), DATEADD(day, 35, GETDATE())),
(2, N'percent', 25, DATEADD(day, 7, GETDATE()), DATEADD(day, 37, GETDATE()));

-- ==================== BẢNG COMPLAINTS ====================
INSERT INTO complaints (order_id, user_id, title, content, status, attachment, created_at) VALUES
(1, 6, N'Sản phẩm bị lỗi bàn phím', N'MacBook Pro của tôi bị lỗi bàn phím, một số phím không nhận. Yêu cầu đổi trả hoặc sửa chữa bảo hành.', N'Đã giải quyết', N'/attachments/complaint1-keyboard.jpg', DATEADD(day, -13, GETDATE())),
(2, 7, N'Giao hàng chậm', N'Đơn hàng giao chậm hơn dự kiến 3 ngày, ảnh hưởng đến công việc của tôi.', N'Đã giải quyết', NULL, DATEADD(day, -11, GETDATE())),
(3, 8, N'Hàng không đúng mô tả', N'Laptop ROG G14 nhận được có màu khác với đơn đặt hàng. Tôi đặt màu trắng nhưng nhận màu xám.', N'Đang xử lý', N'/attachments/complaint3-color.jpg', DATEADD(day, -2, GETDATE())),
(7, 12, N'Thiếu phụ kiện đi kèm', N'Dell XPS 15 thiếu adapter USB-C và túi đựng theo hộp. Yêu cầu bổ sung đầy đủ.', N'Chờ xử lý', NULL, DATEADD(day, -17, GETDATE())),
(12, 10, N'Sản phẩm bị trầy xước', N'Galaxy S24 Ultra có vết trầy nhỏ ở viền màn hình, nghi do đóng gói không cẩn thận.', N'Đã giải quyết', N'/attachments/complaint5-scratch.jpg', DATEADD(day, -24, GETDATE())),
(6, 11, N'Tai nghe Sony bị lỗi ANC', N'Chức năng chống ồn không hoạt động tốt, có tiếng rè khi bật ANC.', N'Đang xử lý', N'/attachments/complaint6-anc.mp4', DATEADD(day, -19, GETDATE())),
(9, 7, N'Màn hình bị điểm chết', N'Google Pixel 8 Pro có 2 điểm chết trên màn hình, yêu cầu đổi máy mới.', N'Chờ xử lý', N'/attachments/complaint7-deadpixel.jpg', DATEADD(day, -1, GETDATE())),
(2, 7, N'Hộp bị rách khi nhận hàng', N'Hộp iPhone 15 Pro Max bị rách một góc, seal bị bong. Nghi ngờ hàng đã qua sử dụng.', N'Đã giải quyết', N'/attachments/complaint8-box.jpg', DATEADD(day, -10, GETDATE())),
(1, 6, N'Pin chai nhanh', N'MacBook Pro dùng được 2 tuần pin đã chai, chỉ dùng được 4-5 tiếng thay vì 10 tiếng như quảng cáo.', N'Đang xử lý', NULL, DATEADD(day, -8, GETDATE())),
(12, 10, N'Phần mềm bị lỗi', N'Samsung S24 Ultra bị lỗi phần mềm, thường xuyên khởi động lại tự động.', N'Chờ xử lý', NULL, DATEADD(day, -23, GETDATE())),
(6, 11, N'Không nhận được hóa đơn VAT', N'Đã yêu cầu xuất hóa đơn VAT khi đặt hàng nhưng không nhận được. Cần gấp để quyết toán công ty.', N'Đã giải quyết', NULL, DATEADD(day, -18, GETDATE())),
(7, 12, N'Sạc laptop không hoạt động', N'Adapter sạc Dell XPS 15 không hoạt động ngay từ khi mở hộp. Yêu cầu đổi mới.', N'Đang xử lý', N'/attachments/complaint12-charger.jpg', DATEADD(day, -16, GETDATE()));

-- ==================== BẢNG COMPLAINT_MESSAGES ====================
INSERT INTO complaint_messages (complaint_id, sender_id, content, created_at) VALUES
(1, 6, N'Bàn phím của tôi bị lỗi phím Space và Command, rất bất tiện khi sử dụng.', DATEADD(day, -13, GETDATE())),
(1, 1, N'Xin lỗi quý khách. Chúng tôi sẽ kiểm tra và hỗ trợ bảo hành ngay. Vui lòng mang máy đến trung tâm bảo hành gần nhất.', DATEADD(day, -13, DATEADD(hour, 2, GETDATE()))),
(1, 6, N'Tôi đã mang máy đến, kỹ thuật viên xác nhận lỗi và đang thay bàn phím mới.', DATEADD(day, -12, GETDATE())),
(1, 1, N'Cảm ơn quý khách đã phản hồi. Máy đã được sửa chữa xong và hoạt động bình thường. Xin lỗi vì sự bất tiện.', DATEADD(day, -12, DATEADD(hour, 6, GETDATE()))),

(2, 7, N'Đơn hàng của tôi đã quá hạn giao 3 ngày mà vẫn chưa nhận được. Rất thất望!', DATEADD(day, -11, GETDATE())),
(2, 1, N'Xin lỗi quý khách! Do thời tiết xấu đơn vị vận chuyển bị chậm trễ. Chúng tôi sẽ ưu tiên giao hàng cho quý khách ngay hôm nay.', DATEADD(day, -11, DATEADD(hour, 1, GETDATE()))),
(2, 7, N'Tôi đã nhận được hàng. Cảm ơn!', DATEADD(day, -10, GETDATE())),

(3, 8, N'Tôi đặt ROG G14 màu trắng nhưng nhận được màu xám. Đây không phải sản phẩm tôi muốn.', DATEADD(day, -2, GETDATE())),
(3, 1, N'Xin lỗi quý khách! Có vẻ nhầm lẫn trong khâu đóng gói. Chúng tôi sẽ đổi sản phẩm đúng màu cho quý khách ngay.', DATEADD(day, -2, DATEADD(hour, 3, GETDATE()))),
(3, 8, N'Khi nào tôi có thể nhận được máy màu trắng? Tôi cần gấp.', DATEADD(day, -1, GETDATE())),

(5, 10, N'Máy S24 Ultra có vết trầy nhỏ ở viền. Tôi muốn đổi máy mới hoàn toàn.', DATEADD(day, -24, GETDATE())),
(5, 1, N'Chúng tôi xin lỗi về sự cố này. Quý khách vui lòng gửi hình ảnh chi tiết để chúng tôi kiểm tra.', DATEADD(day, -24, DATEADD(hour, 2, GETDATE()))),
(5, 10, N'Tôi đã gửi ảnh. Vết trầy rõ ràng do đóng gói không cẩn thận.', DATEADD(day, -23, GETDATE())),
(5, 1, N'Chúng tôi đã xác nhận và sẽ đổi máy mới cho quý khách trong vòng 24h. Xin lỗi vì bất tiện này.', DATEADD(day, -23, DATEADD(hour, 4, GETDATE()))),

(6, 11, N'Tai nghe Sony WH-1000XM5 của tôi có tiếng rè khi bật chống ồn. Sản phẩm bị lỗi.', DATEADD(day, -19, GETDATE())),
(6, 1, N'Quý khách vui lòng thử reset tai nghe về cài đặt gốc và cập nhật firmware mới nhất.', DATEADD(day, -19, DATEADD(hour, 1, GETDATE()))),
(6, 11, N'Tôi đã thử nhưng vẫn bị lỗi. Tôi muốn đổi sản phẩm mới.', DATEADD(day, -18, GETDATE())),

(8, 7, N'Hộp iPhone bị rách và seal bị bong. Tôi nghi ngờ đây không phải hàng mới.', DATEADD(day, -10, GETDATE())),
(8, 1, N'Xin lỗi quý khách! Đây là lỗi trong quá trình vận chuyển. Sản phẩm là hàng mới 100%, chúng tôi có thể cung cấp giấy tờ chứng minh.', DATEADD(day, -10, DATEADD(hour, 2, GETDATE()))),
(8, 7, N'OK, tôi đã kiểm tra máy và xác nhận là mới. Cảm ơn!', DATEADD(day, -9, GETDATE())),

(11, 11, N'Tôi cần hóa đơn VAT gấp để quyết toán công ty. Đã 5 ngày vẫn chưa nhận được.', DATEADD(day, -18, GETDATE())),
(11, 1, N'Xin lỗi quý khách! Chúng tôi sẽ xuất hóa đơn ngay và gửi qua email trong vòng 2 giờ.', DATEADD(day, -18, DATEADD(hour, 1, GETDATE()))),
(11, 11, N'Đã nhận được hóa đơn. Cảm ơn!', DATEADD(day, -18, DATEADD(hour, 3, GETDATE())));

-- ==================== BẢNG NOTIFICATIONS ====================
INSERT INTO notifications (user_id, related_complaint_id, title, message, is_read, created_at) VALUES
(6, 1, N'Khiếu nại được xử lý', N'Khiếu nại "Sản phẩm bị lỗi bàn phím" của bạn đã được giải quyết.', 1, DATEADD(day, -12, GETDATE())),
(7, 2, N'Khiếu nại được xử lý', N'Khiếu nại "Giao hàng chậm" của bạn đã được giải quyết.', 1, DATEADD(day, -10, GETDATE())),
(8, 3, N'Admin đã phản hồi', N'Admin đã phản hồi khiếu nại "Hàng không đúng mô tả" của bạn.', 0, DATEADD(day, -2, DATEADD(hour, 3, GETDATE()))),
(12, 4, N'Khiếu nại mới', N'Bạn có khiếu nại mới: "Thiếu phụ kiện đi kèm"', 1, DATEADD(day, -17, GETDATE())),
(10, 5, N'Khiếu nại được xử lý', N'Khiếu nại "Sản phẩm bị trầy xước" của bạn đã được giải quyết.', 1, DATEADD(day, -23, DATEADD(hour, 4, GETDATE()))),
(11, 6, N'Admin đã phản hồi', N'Admin đã phản hồi khiếu nại "Tai nghe Sony bị lỗi ANC" của bạn.', 0, DATEADD(day, -18, GETDATE())),
(7, 7, N'Khiếu nại mới', N'Bạn có khiếu nại mới: "Màn hình bị điểm chết"', 0, DATEADD(day, -1, GETDATE())),
(7, 8, N'Khiếu nại được xử lý', N'Khiếu nại "Hộp bị rách khi nhận hàng" của bạn đã được giải quyết.', 1, DATEADD(day, -9, GETDATE())),
(6, 9, N'Admin đã phản hồi', N'Admin đã phản hồi khiếu nại "Pin chai nhanh" của bạn.', 0, DATEADD(day, -8, GETDATE())),
(10, 10, N'Khiếu nại mới', N'Bạn có khiếu nại mới: "Phần mềm bị lỗi"', 1, DATEADD(day, -23, GETDATE())),
(11, 11, N'Khiếu nại được xử lý', N'Khiếu nại "Không nhận được hóa đơn VAT" của bạn đã được giải quyết.', 1, DATEADD(day, -18, DATEADD(hour, 3, GETDATE()))),
(12, 12, N'Admin đã phản hồi', N'Admin đã phản hồi khiếu nại "Sạc laptop không hoạt động" của bạn.', 0, DATEADD(day, -16, GETDATE())),
(6, NULL, N'Đơn hàng mới', N'Đơn hàng #1 của bạn đã được tạo thành công.', 1, DATEADD(day, -15, GETDATE())),
(7, NULL, N'Đơn hàng đã giao', N'Đơn hàng #2 của bạn đã được giao thành công.', 1, DATEADD(day, -11, GETDATE())),
(8, NULL, N'Đơn hàng đang giao', N'Đơn hàng #3 của bạn đang được giao đến bạn.', 0, DATEADD(day, -2, GETDATE())),
(9, NULL, N'Đơn hàng đã xác nhận', N'Đơn hàng #4 của bạn đã được xác nhận và chuẩn bị giao.', 0, DATEADD(day, -1, GETDATE())),
(10, NULL, N'Khuyến mãi mới', N'Giảm giá 20% cho tất cả laptop gaming. Nhanh tay đặt hàng!', 0, DATEADD(day, 1, GETDATE())),
(11, NULL, N'Sản phẩm mới', N'iPhone 16 Pro Max đã có mặt tại cửa hàng. Đặt hàng ngay!', 0, GETDATE());

-- ==================== BẢNG CONTACT ====================
INSERT INTO contact (UserID, fullName, email, content, createdAt) VALUES
(6, N'Hoàng Văn E', N'customer1@gmail.com', N'Tôi muốn hỏi về chính sách bảo hành của MacBook Pro. Thời gian bảo hành là bao lâu?', DATEADD(day, -20, GETDATE())),
(7, N'Vũ Thị F', N'customer2@gmail.com', N'Cửa hàng có hỗ trợ trả góp 0% cho iPhone 15 Pro Max không? Thời gian trả góp tối đa là bao nhiêu tháng?', DATEADD(day, -18, GETDATE())),
(8, N'Đỗ Văn G', N'customer3@gmail.com', N'Tôi muốn đặt hàng ROG G14 nhưng màu trắng đang hết hàng. Khi nào có hàng lại?', DATEADD(day, -15, GETDATE())),
(9, N'Bùi Thị H', N'customer4@gmail.com', N'Cửa hàng có dịch vụ giao hàng nhanh trong ngày không? Tôi đang cần gấp một chiếc laptop.', DATEADD(day, -12, GETDATE())),
(10, N'Ngô Văn I', N'customer5@gmail.com', N'Samsung S24 Ultra có thêm ưu đãi gì nếu mua kèm Galaxy Watch 6 Classic không?', DATEADD(day, -10, GETDATE())),
(11, N'Lý Thị K', N'customer6@gmail.com', N'Tai nghe Sony WH-1000XM5 có kèm theo case cứng không? Tôi muốn bảo vệ tai nghe tốt hơn.', DATEADD(day, -8, GETDATE())),
(12, N'Trương Văn L', N'customer7@gmail.com', N'Cửa hàng có chương trình thu cũ đổi mới cho MacBook không? Tôi đang dùng MacBook Pro 2020.', DATEADD(day, -6, GETDATE())),
(6, N'Hoàng Văn E', N'customer1@gmail.com', N'RTX 4080 có sẵn hàng không? Tôi cần mua để nâng cấp PC gaming.', DATEADD(day, -5, GETDATE())),
(7, N'Vũ Thị F', N'customer2@gmail.com', N'iPad Pro M2 có hỗ trợ Apple Pencil thế hệ nào? Tôi cần dùng để vẽ.', DATEADD(day, -4, GETDATE())),
(8, N'Đỗ Văn G', N'customer3@gmail.com', N'Cửa hàng có tư vấn cấu hình PC gaming trong tầm giá 30 triệu không?', DATEADD(day, -3, GETDATE())),
(9, N'Bùi Thị H', N'customer4@gmail.com', N'Dell XPS 15 có thể nâng cấp RAM và SSD sau này không?', DATEADD(day, -2, GETDATE())),
(10, N'Ngô Văn I', N'customer5@gmail.com', N'Cửa hàng có dịch vụ cài đặt phần mềm và chuyển dữ liệu cho laptop mới không?', DATEADD(day, -1, GETDATE()));

-- ==================== BẢNG STORE_SETTINGS ====================
INSERT INTO store_settings (store_name, email, hotline, address, logo, theme, cod_enabled, momo_enabled, vnpay_enabled, created_at, updated_at) VALUES
(N'UteShop Vietnam', N'contact@uteshop.vn', N'+84 028 3896 8641', N'01 Đ. Võ Văn Ngân, Linh Chiểu, Thủ Đức, Hồ Chí Minh', N'/images/logo/techshop-logo.png', N'modern', 1, 1, 1, DATEADD(day, -365, GETDATE()), GETDATE());

-- ==================== BẢNG PRODUCT_IMAGE ====================
INSERT INTO ProductImage (image_url, is_main, product_id) VALUES
-- MacBook Pro 14 M3 Pro
(N'/images/products/mbp14-1.jpg', 1, 1),
(N'/images/products/mbp14-2.jpg', 0, 1),
(N'/images/products/mbp14-3.jpg', 0, 1),

-- Dell XPS 15
(N'/images/products/dell-xps15-1.jpg', 1, 2),
(N'/images/products/dell-xps15-2.jpg', 0, 2),
(N'/images/products/dell-xps15-3.jpg', 0, 2),

-- ROG G14
(N'/images/products/rog-g14-1.jpg', 1, 3),
(N'/images/products/rog-g14-2.jpg', 0, 3),
(N'/images/products/rog-g14-3.jpg', 0, 3),

-- iPhone 15 Pro Max
(N'/images/products/ip15pm-1.jpg', 1, 11),
(N'/images/products/ip15pm-2.jpg', 0, 11),
(N'/images/products/ip15pm-3.jpg', 0, 11),
(N'/images/products/ip15pm-4.jpg', 0, 11),

-- Samsung S24 Ultra
(N'/images/products/s24u-1.jpg', 1, 12),
(N'/images/products/s24u-2.jpg', 0, 12),
(N'/images/products/s24u-3.jpg', 0, 12),

-- Sony WH-1000XM5
(N'/images/products/sony-xm5-1.jpg', 1, 19),
(N'/images/products/sony-xm5-2.jpg', 0, 19),
(N'/images/products/sony-xm5-3.jpg', 0, 19),

-- AirPods Pro 2
(N'/images/products/airpods-pro2-1.jpg', 1, 20),
(N'/images/products/airpods-pro2-2.jpg', 0, 20);

-- ==================== KẾT THÚC ====================
-- Script đã hoàn tất với đầy đủ dữ liệu cho tất cả các bảng
-- Tổng số records: 
-- Users: 12, Categories: 12, Shops: 2, Products: 20
-- Product_Variants: 35, Orders: 12, Order_Details: 12
-- Cart_Items: 12, Reviews: 12, Deliveries: 9
-- Promotions: 12, Complaints: 12, Complaint_Messages: 20
-- Notifications: 18, Contact: 12, Store_Settings: 1, ProductImage: 20
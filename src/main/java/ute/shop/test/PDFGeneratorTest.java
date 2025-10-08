//package ute.shop.test;
//
//import ute.shop.entity.*;
//import ute.shop.utils.PDFGenerator;
//
//import java.io.File;
//import java.math.BigDecimal;
//import java.util.Date;
//
//public class PDFGeneratorTest {
//    public static void main(String[] args) {
//        try {
//            // ================== GIẢ LẬP DỮ LIỆU ==================
//            User shipper = new User();
//            shipper.setName("Phạm Hàn Minh Chương");
//            shipper.setPhone("0909123456");
//            shipper.setEmail("chuong@uteshop.vn");
//
//            Order order = new Order();
//            order.setOrderId(1001);
//            order.setStatus("Đang giao");
//            order.setPaymentMethod("COD");
//            order.setAddress("123 Nguyễn Văn Linh, Q.7, TP.HCM");
//            order.setTotalAmount(new BigDecimal("1250000"));
//            order.setCreatedAt(new Date());
//
//            Delivery delivery = new Delivery();
//            delivery.setDeliveryId(5);
//            delivery.setShipper(shipper);
//            delivery.setOrder(order);
//            delivery.setStatus("Đang giao");
//            delivery.setNoteText("Kiểm tra hàng kỹ, tránh va đập.");
//            delivery.setCreatedAt(new Date());
//
//            // ================== THƯ MỤC LƯU PDF ==================
//            String uploadPath = new File("src/main/webapp/uploads/delivery_notes").getAbsolutePath();
//            System.out.println("🟦 Đường dẫn lưu PDF thực tế:");
//            System.out.println(uploadPath);
//
//            // ================== GỌI SINH PDF ==================
//            String filePath = PDFGenerator.generateDeliveryNote(delivery, uploadPath);
//
//            // ================== KIỂM TRA FILE ==================
//            File file = new File(filePath);
//            System.out.println("\n✅ File PDF sinh ra:");
//            System.out.println(file.getAbsolutePath());
//            System.out.println("Tồn tại thật không? " + file.exists());
//
//            // ================== KIỂM TRA ĐƯỜNG DẪN WEB ==================
//            String relativePath = "/uploads/delivery_notes/" + file.getName();
//            System.out.println("\n🌐 Đường dẫn nên lưu trong database:");
//            System.out.println(relativePath);
//            System.out.println("=> Mở thử trình duyệt: http://localhost:8080/uteshop" + relativePath);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}

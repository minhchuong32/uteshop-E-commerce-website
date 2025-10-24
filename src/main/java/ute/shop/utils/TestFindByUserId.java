package ute.shop.utils;
import java.util.List;
import ute.shop.config.JPAConfig;
import ute.shop.entity.ShippingAddress;
import ute.shop.dao.impl.ShippingAddressDaoImpl;

public class TestFindByUserId {
    public static void main(String[] args) {
        // Khởi tạo DAO
        ShippingAddressDaoImpl dao = new ShippingAddressDaoImpl();

        // Giả sử user có ID = 1
        int testUserId = 6;

        try {
            List<ShippingAddress> addresses = dao.findByUserId(testUserId);

            // In kết quả ra console
            if (addresses.isEmpty()) {
                System.out.println("Không tìm thấy địa chỉ giao hàng cho userId = " + testUserId);
            } else {
                System.out.println("Danh sách địa chỉ giao hàng của userId = " + testUserId + ":");
                for (ShippingAddress s : addresses) {
                    System.out.println("--------------------------------");
                    System.out.println("ID: " + s.getUser().getUserId());
                    System.out.println("Tên người nhận: " + s.getRecipientName());
                    System.out.println("SĐT: " + s.getPhoneNumber());
                    System.out.println("Địa chỉ: " + s.getAddressLine());
                    System.out.println("Ngày tạo: " + s.getCreatedAt());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

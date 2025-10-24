package ute.shop.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class VnPayConfig {
	private static final Properties props = new Properties();
	static {
        try (InputStream input = VnPayConfig.class.getClassLoader().getResourceAsStream("vnpay_config.properties")) {
            if (input == null) {
                throw new RuntimeException("Không tìm thấy file cấu hình vnpay.config trong classpath!");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Không thể đọc file vnpay.config", e);
        }
    }

    public static String getTmnCode() {
        return props.getProperty("vnp_TmnCode");
    }

    public static String getHashSecret() {
        return props.getProperty("vnp_HashSecret");
    }

    public static String getVnpUrl() {
        return props.getProperty("vnp_Url");
    }

    public static String getReturnUrl() {
        return props.getProperty("vnp_ReturnUrl");
    }
    public static String hashAllFields(Map<String, String> fields) {
        // Sắp xếp các trường theo thứ tự alphabet
        Map<String, String> sortedFields = new TreeMap<>(fields);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedFields.entrySet()) {
            if (sb.length() > 0) {
                sb.append('&');
            }
            sb.append(URLEncoder.encode(entry.getKey(), StandardCharsets.US_ASCII))
              .append('=')
              .append(URLEncoder.encode(entry.getValue(), StandardCharsets.US_ASCII));
        }

        return hmacSHA512(getHashSecret(), sb.toString());
    }
    private static String hmacSHA512(String key, String data) {
        try {
            javax.crypto.Mac hmac512 = javax.crypto.Mac.getInstance("HmacSHA512");
            javax.crypto.spec.SecretKeySpec secretKey =
                new javax.crypto.spec.SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac512.init(secretKey);
            byte[] bytes = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hash = new StringBuilder();
            for (byte b : bytes) {
                hash.append(String.format("%02x", b));
            }
            return hash.toString();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo HMAC SHA512", e);
        }
    }

}

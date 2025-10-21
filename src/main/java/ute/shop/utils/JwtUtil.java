package ute.shop.utils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import ute.shop.entity.User;

public class JwtUtil {
	private static final Key SECRET_KEY;
	private static final long EXPIRATION_TIME;

	// Khối khởi tạo tĩnh (Static Initializer Block)
	// Code trong này sẽ chỉ chạy MỘT LẦN khi lớp JwtUtil được nạp vào bộ nhớ.
	static {
		try {
			Properties properties = new Properties();
			// Tìm và đọc file config.properties từ classpath
			InputStream inputStream = JwtUtil.class.getClassLoader().getResourceAsStream("config.properties");

			if (inputStream == null) {
				throw new RuntimeException("Xin lỗi, không thể tìm thấy file 'config.properties'");
			}

			// Tải các thuộc tính từ file
			properties.load(inputStream);

			// Lấy chuỗi bí mật từ file properties
			String secretString = properties.getProperty("JWT_SECRET_KEY");
			if (secretString == null || secretString.trim().isEmpty()) {
				throw new RuntimeException("JWT_SECRET_KEY không được đặt trong file config.properties");
			}

			// Chuyển đổi chuỗi bí mật thành đối tượng Key để sử dụng
			SECRET_KEY = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));

			// Lấy thời gian hết hạn từ file (ví dụ: 86400000 ms = 1 ngày)
			EXPIRATION_TIME = Long.parseLong(properties.getProperty("JWT_EXPIRATION_TIME", "86400000"));

		} catch (Exception e) {
			// Nếu có bất kỳ lỗi nào trong quá trình đọc file, chương trình sẽ dừng lại
			// và báo lỗi. Điều này tốt hơn là chạy với một cấu hình sai.
			throw new RuntimeException("Lỗi khi tải cấu hình cho JwtUtil", e);
		}
	}

	// === TẠO TOKEN ===
		public static String generateToken(User user) {
		    // DEBUG
		    System.out.println("=== GENERATING JWT TOKEN ===");
		    System.out.println("User ID: " + user.getUserId());
		    System.out.println("Email: " + user.getEmail());
		    System.out.println("Name: " + user.getName());
		    System.out.println("Role: " + user.getRole());
		    
		    Map<String, Object> claims = new HashMap<>();
		    claims.put("fullName", user.getName());
		    claims.put("role", user.getRole());
		    claims.put("userId", user.getUserId());

		    String token = Jwts.builder()
		            .setClaims(claims)
		            .setSubject(user.getEmail())
		            .setIssuedAt(new Date(System.currentTimeMillis()))
		            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
		            .signWith(SECRET_KEY)
		            .compact();
		    
		    System.out.println("Token generated: " + token.substring(0, 30) + "...");
		    System.out.println("============================");
		    
		    return token;
		
//		Map<String, Object> claims = new HashMap<>();
//		claims.put("fullName", user.getName());
//		claims.put("role", user.getRole());
//		claims.put("userId", user.getUserId());
//
//		return Jwts.builder().setClaims(claims).setSubject(user.getEmail())
//				.setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).signWith(SECRET_KEY) // Sử dụng
//																											// SECRET_KEY
//																											// đã được
//																											// khởi tạo
//				.compact();
	}

	// === LẤY THÔNG TIN TỪ TOKEN ===
	private static Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
	}

	public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public static String extractEmail(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public static Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public static String extractRole(String token) {
		return extractClaim(token, claims -> claims.get("role", String.class));
	}

	public static Integer extractUserId(String token) {
		return extractClaim(token, claims -> claims.get("userId", Integer.class));
	}

	// === KIỂM TRA TOKEN ===
	private static boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public static boolean validateToken(String token, User userDetails) {
		final String email = extractEmail(token);
		return (email.equals(userDetails.getEmail()) && !isTokenExpired(token));
	}

	// Overload để chỉ cần xác thực token mà không cần user object
	public static boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
			return !isTokenExpired(token);
		} catch (Exception e) {
			// Log lỗi nếu cần
			return false;
		}
	}
}
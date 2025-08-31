package ute.shop.utils;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class SendMail {

    private final String username = "chuongminh3225@gmail.com";  
    private final String password = "mlqh hojj wdwc cucm";     

    public void sendMail(String to, String subject, String content) {
        // Cấu hình SMTP server (Gmail)
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Tạo session
        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username, "UteShop")); 
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            message.setContent(content, "text/html; charset=UTF-8");

            Transport.send(message);

            System.out.println("Gửi email thành công đến: " + to);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi gửi email: " + e.getMessage());
        }
    }
}

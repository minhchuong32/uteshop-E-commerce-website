package ute.shop.command;

import ute.shop.entity.User;
import ute.shop.utils.SendMail;

/**
 * Lệnh gửi email liên hệ tới user
 */
public class ContactUserCommand implements IUserCommand {

    private final User   recipient;
    private final String subject;
    private final String body;

    public ContactUserCommand(User recipient, String subject, String body) {
        this.recipient = recipient;
        this.subject   = subject;
        this.body      = body;
    }

    @Override
    public void execute() throws Exception {
        // Xây dựng nội dung HTML email
        String html = "<div style='font-family:sans-serif'>"
                + "<h3>Chào " + recipient.getName() + ",</h3>"
                + "<p>" + body.replace("\n", "<br>") + "</p>"
                + "<p>Trân trọng,<br><strong>Đội ngũ UteShop</strong></p>"
                + "</div>";

        SendMail.getInstance().sendMail(recipient.getEmail(), subject, html);
    }

    @Override
    public String describe() {
        return "CONTACT user #" + recipient.getUserId()
                + " | subject=" + subject;
    }
}
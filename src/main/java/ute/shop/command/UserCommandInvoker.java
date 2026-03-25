package ute.shop.command;

/**
 * Invoker — nhận và thực thi lệnh, tự động ghi log
 * Đây là nơi duy nhất biết "lệnh đã được thực thi"
 */
public class UserCommandInvoker {

    /**
     * Thực thi một lệnh và ghi log kết quả.
     * @return true nếu thành công, false nếu có lỗi
     */
    public boolean invoke(IUserCommand command) {
        System.out.println("[COMMAND] Thực thi: " + command.describe());
        try {
            command.execute();
            System.out.println("[COMMAND] Thành công: " + command.describe());
            return true;
        } catch (Exception e) {
            System.err.println("[COMMAND] Lỗi: " + command.describe()
                    + " | " + e.getMessage());
            return false;
        }
    }
}
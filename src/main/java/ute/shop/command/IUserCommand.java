package ute.shop.command;

/**
 * COMMAND PATTERN — Interface cho các lệnh thao tác với User
 */
public interface IUserCommand {
    /** Thực thi lệnh */
    void execute() throws Exception;

    /** Mô tả lệnh để ghi log */
    String describe();
}
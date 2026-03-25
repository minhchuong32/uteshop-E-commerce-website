package ute.shop.command;

import ute.shop.service.IUserService;

/**
 * Lệnh mở khóa tài khoản user
 */
public class UnlockUserCommand implements IUserCommand {

    private final IUserService userService;
    private final int userId;

    public UnlockUserCommand(IUserService userService, int userId) {
        this.userService = userService;
        this.userId      = userId;
    }

    @Override
    public void execute() {
        userService.updateStatus(userId, "active");
    }

    @Override
    public String describe() {
        return "UNLOCK user #" + userId;
    }
}
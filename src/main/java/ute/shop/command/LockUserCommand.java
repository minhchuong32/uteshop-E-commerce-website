package ute.shop.command;

import ute.shop.service.IUserService;

/**
 * Lệnh khóa tài khoản user
 */
public class LockUserCommand implements IUserCommand {

    private final IUserService userService;
    private final int userId;

    public LockUserCommand(IUserService userService, int userId) {
        this.userService = userService;
        this.userId      = userId;
    }

    @Override
    public void execute() {
        userService.updateStatus(userId, "banned");
    }
    
    @Override
    public String describe() {
        return "LOCK user #" + userId;
    }
}
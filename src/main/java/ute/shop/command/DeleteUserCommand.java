package ute.shop.command;

import ute.shop.service.IUserService;

/**
 * Lệnh xóa user
 */
public class DeleteUserCommand implements IUserCommand {

    private final IUserService userService;
    private final int userId;

    public DeleteUserCommand(IUserService userService, int userId) {
        this.userService = userService;
        this.userId      = userId;
    }

    @Override
    public void execute() {
        userService.delete(userId);
    }

    @Override
    public String describe() {
        return "DELETE user #" + userId;
    }
}
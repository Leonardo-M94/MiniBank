package sorokin.dev.operations.commands;

import org.springframework.stereotype.Component;
import sorokin.dev.operations.ConsoleOperationType;
import sorokin.dev.operations.OperationCommand;
import sorokin.dev.user.UserService;

@Component
public class ShowAllUsersCommand implements OperationCommand {
    private final UserService userService;

    public ShowAllUsersCommand(UserService userService){
        this.userService = userService;
    }

    @Override
    public void execute() {
        System.out.println(userService.getUsers());
    }

    @Override
    public ConsoleOperationType getType() {
        return ConsoleOperationType.SHOW_ALL_USERS;
    }
}

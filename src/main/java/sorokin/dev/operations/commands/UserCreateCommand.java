package sorokin.dev.operations.commands;

import org.springframework.stereotype.Component;
import sorokin.dev.account.Account;
import sorokin.dev.account.AccountService;
import sorokin.dev.console.ConsoleInput;
import sorokin.dev.operations.ConsoleOperationType;
import sorokin.dev.operations.OperationCommand;
import sorokin.dev.user.User;
import sorokin.dev.user.UserService;

@Component
public class UserCreateCommand implements OperationCommand {
    private final UserService userService;
    private final ConsoleInput consoleInput;

    public UserCreateCommand(UserService userService, ConsoleInput consoleInput){
        this.userService = userService;
        this.consoleInput = consoleInput;
    }

    @Override
    public void execute() {
        String login = consoleInput.readStringValue("Enter login:");

        User user = userService.create(login);
        System.out.println("User created: " + user);

    }

    @Override
    public ConsoleOperationType getType() {
        return ConsoleOperationType.USER_CREATE;
    }
}

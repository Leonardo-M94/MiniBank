package sorokin.dev.operations.commands;

import org.springframework.stereotype.Component;
import sorokin.dev.account.Account;
import sorokin.dev.account.AccountService;
import sorokin.dev.console.ConsoleInput;
import sorokin.dev.operations.ConsoleOperationType;
import sorokin.dev.operations.OperationCommand;

@Component
public class AccountCreateCommand implements OperationCommand {

    private final AccountService accountService;
    private final ConsoleInput consoleInput;

    public AccountCreateCommand(AccountService accountService, ConsoleInput consoleInput){
        this.accountService = accountService;
        this.consoleInput = consoleInput;
    }

    @Override
    public void execute() {
        int userId = consoleInput.readIntValue("Enter user id: ");
        Account account = accountService.create(userId);
        System.out.println("Account created: " + account);
    }

    @Override
    public ConsoleOperationType getType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }
}

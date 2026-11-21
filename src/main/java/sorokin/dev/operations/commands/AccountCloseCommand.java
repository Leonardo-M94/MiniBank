package sorokin.dev.operations.commands;

import org.springframework.stereotype.Component;
import sorokin.dev.account.Account;
import sorokin.dev.account.AccountService;
import sorokin.dev.console.ConsoleInput;
import sorokin.dev.operations.ConsoleOperationType;
import sorokin.dev.operations.OperationCommand;

@Component
public class AccountCloseCommand implements OperationCommand {
    private final AccountService accountService;
    private final ConsoleInput input;

    public AccountCloseCommand(AccountService accountService, ConsoleInput input) {
        this.accountService = accountService;
        this.input = input;
    }

    @Override
    public void execute() {
        int accountID = input.readIntValue("Enter account id to close:");
        Account closedAccount = accountService.close(accountID);
        System.out.println("Account " + accountID + " closed. " +
                "Remaining balance " + closedAccount.getMoneyAmount() + " transferred to account 1.");
    }

    @Override
    public ConsoleOperationType getType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }
}

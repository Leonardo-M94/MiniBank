package sorokin.dev.operations.commands;

import org.springframework.stereotype.Component;
import sorokin.dev.account.Account;
import sorokin.dev.account.AccountService;
import sorokin.dev.console.ConsoleInput;
import sorokin.dev.operations.ConsoleOperationType;
import sorokin.dev.operations.OperationCommand;

@Component
public class AccountWithdrawCommand implements OperationCommand {
    private final AccountService accountService;
    private final ConsoleInput input;

    public AccountWithdrawCommand(AccountService accountService, ConsoleInput input) {
        this.accountService = accountService;
        this.input = input;
    }

    @Override
    public void execute() {
        int accountId = input.readIntValue("Enter account id:");
        int amount = input.readIntValue("Enter amount:");

        Account account = accountService.withdraw(accountId, amount);

        System.out.println("Withdrew " + amount + " from accoint " + accountId +
                ". New balance: " + account.getMoneyAmount());
    }

    @Override
    public ConsoleOperationType getType() {
        return ConsoleOperationType.ACCOUNT_WITHDRAW;
    }
}

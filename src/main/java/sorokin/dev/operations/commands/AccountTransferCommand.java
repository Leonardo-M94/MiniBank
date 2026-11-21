package sorokin.dev.operations.commands;

import org.springframework.stereotype.Component;
import sorokin.dev.account.AccountService;
import sorokin.dev.console.ConsoleInput;
import sorokin.dev.operations.ConsoleOperationType;
import sorokin.dev.operations.OperationCommand;

@Component
public class AccountTransferCommand implements OperationCommand {
    private final AccountService accountService;
    private final ConsoleInput input;

    public AccountTransferCommand(AccountService accountService, ConsoleInput input) {
        this.accountService = accountService;
        this.input = input;
    }

    @Override
    public void execute() {
        int sourceAccountId = input.readIntValue("Enter source account id:");
        int targetAccountId = input.readIntValue("Enter target account id:");
        int amount = input.readIntValue("Enter amount:");

        accountService.transfer(sourceAccountId, targetAccountId, amount);

        System.out.println("Transfer completed from account " + sourceAccountId + " to account " + targetAccountId);
    }

    @Override
    public ConsoleOperationType getType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }
}

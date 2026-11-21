package sorokin.dev.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sorokin.dev.user.User;
import sorokin.dev.user.UserService;

import java.util.*;

@Component
public class AccountService {

    private final AccountProperties accountProperties;
    private final Map<Integer, Account> accounts;
    private UserService userService;
    private int accCount;

    public AccountService(AccountProperties accountProperties){
        this.accountProperties = accountProperties;
        accounts = new HashMap<>();
        accCount = 0;
    }

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    public Account getAccountById(int accId){
        checkIdValue(accId);
        Account account = accounts.get(accId);
        if (account == null)
            throw new NoSuchElementException("Account with ID = " + accId + " is not found.");
        return account;
    }

    private void checkEnoughFundsInAccount(Account account, int amount){
        if (amount > account.getMoneyAmount())
            throw new IllegalStateException("Error: insufficient funds on account ID = " + account.getId() +
                    ", moneyAmount = " + account.getMoneyAmount() + ", required amount = " + amount);
    }

    public Account create(int userId){
        User user = userService.getUserById(userId);

        Account account = new Account(++accCount, userId, accountProperties.getDefaultAmount());
        accounts.put(accCount, account);
        user.addAccount(account);

        return account;
    }

    public Account deposit(int accId, int amount){
        checkIdValue(accId);
        checkAmountValue(amount);

        Account account = getAccountById(accId);

        account.setMoneyAmount(account.getMoneyAmount() + amount);
        return account;
    }

    public Account withdraw(int accId, int amount){
        checkIdValue(accId);
        checkAmountValue(amount);

        Account account = getAccountById(accId);

        checkEnoughFundsInAccount(account, amount);

        account.setMoneyAmount(account.getMoneyAmount() - amount);
        return account;
    }

    public void transfer(int sourceAccId, int targetAccId, int amount){
        checkAmountValue(amount);

        if (sourceAccId == targetAccId){
            throw new IllegalArgumentException("Source and target ID accounts must be a different.");
        }

        Account accSource = getAccountById(sourceAccId);
        Account accTarget = getAccountById(targetAccId);

        checkEnoughFundsInAccount(accSource, amount);

        int accrualAmount = amount;

        if (accSource.getUserId() != accTarget.getUserId())
            accrualAmount = (int)Math.round(amount * (1 - accountProperties.getTransferCommission()));

        accSource.setMoneyAmount(accSource.getMoneyAmount() - amount);

        accTarget.setMoneyAmount(accTarget.getMoneyAmount() + accrualAmount);
    }

    public Account close(int accId){
        checkIdValue(accId);

        Account closedAccount = getAccountById(accId);

        User user = userService.getUserById(closedAccount.getUserId());
        List<Account> userAccountList = user.getAccountList();

        if (userAccountList.size() <= 1)
            throw new IllegalStateException("It's impossible to close a single account.");

        int moneyTransfer = closedAccount.getMoneyAmount();

        userAccountList.remove(closedAccount);

        Account firstAccount = userAccountList.getFirst();

        firstAccount.setMoneyAmount(firstAccount.getMoneyAmount() + moneyTransfer);

        accounts.remove(accId);

        return closedAccount;
    }

    private void checkIdValue(int id){
        if (id <= 0)
            throw new IllegalArgumentException("Id account must be a positive number.");
    }

    private void checkAmountValue(int amount){
        if (amount <= 0)
            throw new IllegalArgumentException("Amount value must be a positive.");
    }
}

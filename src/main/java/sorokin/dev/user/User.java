package sorokin.dev.user;

import sorokin.dev.account.Account;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final int id;
    private String login;
    private final List<Account> accountList;

    public User(int id, String login){
        this.id = id;
        this.login = login;
        accountList = new ArrayList<>();
    }

    public void addAccount(Account account){
        accountList.add(account);
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", accountList=" + accountList +
                '}';
    }
}

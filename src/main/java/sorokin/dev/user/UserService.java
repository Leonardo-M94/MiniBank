package sorokin.dev.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sorokin.dev.account.Account;
import sorokin.dev.account.AccountService;

import java.util.*;

@Service
public class UserService {
    private final Map<Integer, User> users;
    private final Set<String> userLoginSet;
    private AccountService accountService;
    private int userCount;

    public UserService(){
        users = new HashMap<>();
        userLoginSet = new HashSet<>();
        userCount = 0;
    }

    @Autowired
    public void setAccountService(AccountService accountService){
        this.accountService = accountService;
    }

    public User create(String login){
        if (login == null || login.isBlank())
            throw new IllegalArgumentException("User login must not be empty.");

        if (userLoginSet.contains(login))
            throw new IllegalArgumentException("User with login '" + login + "' - already exists!");

        User user = new User(++userCount, login);
        users.put(user.getId(), user);
        userLoginSet.add(login);

        accountService.create(user.getId());

        return user;
    }

    public User getUserById(int userId){
        if (userId <= 0)
            throw new IllegalArgumentException("User ID must be a positive number.");

        User user = users.get(userId);
        if (user == null) {
            throw new IllegalArgumentException("User with ID = " + userId + " not found.");
        }
        return user;
    }

    public List<User> getUsers(){
        return users.values().stream().toList();
    }
}

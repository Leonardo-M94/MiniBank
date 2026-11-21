package sorokin.dev.console;

import org.springframework.stereotype.Component;
import sorokin.dev.exception.EOFException;
import sorokin.dev.operations.ConsoleOperationType;

import java.util.Scanner;

@Component
public class ConsoleInput {
    private final Scanner input;

    public ConsoleInput(){
        this.input = new Scanner(System.in);
    }

     public int readIntValue(String out){
        while(true) {
            System.out.println(out);
            String value = readLine();
            if (value.isBlank()){
                System.out.println("Input value must not be empty.");
                continue;
            }
            try{
                int num = Integer.parseInt(value);
                if (num > 0)
                    return num;
                else System.out.println("Input value must be a positive number.");
            } catch(NumberFormatException exception){
                System.out.println("Input value must be a number.");
            }
        }
     }

     public String readStringValue(String out){
        while(true) {
            System.out.println(out);
            String value = readLine();
            if (!value.isBlank())
                return value;
            System.out.println("Input value must not be empty.");
        }
     }

     public ConsoleOperationType readCommand(){
        while(true) {
            String value = readStringValue("Enter command:");
            if (value.isBlank()) {
                System.out.println("Input value must not be empty.");
                printAvailableCommands();
                continue;
            }
            try {
                return ConsoleOperationType.valueOf(value);
            } catch(IllegalArgumentException exception){
                System.out.println("Not found command '" + value + "'");
                printAvailableCommands();
            }
        }
     }

     private String readLine(){
        if (input.hasNext())
            return input.nextLine();
        throw new EOFException("");
     }

    public void printAvailableCommands(){
        System.out.println("\nAvailable commands: USER_CREATE, SHOW_ALL_USERS, " +
                "ACCOUNT_CREATE, ACCOUNT_DEPOSIT, ACCOUNT_WITHDRAW, ACCOUNT_TRANSFER, ACCOUNT_CLOSE, EXIT");
    }
}

package sorokin.dev.console;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import sorokin.dev.exception.EOFException;
import sorokin.dev.operations.ConsoleOperationType;
import sorokin.dev.operations.OperationCommand;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OperationsConsoleListener {
    private final ConsoleInput input;
    private final Map<ConsoleOperationType, OperationCommand> operations;

    public OperationsConsoleListener(ConsoleInput input, List<OperationCommand> commandList) {
        this.input = input;
        this.operations = commandList.stream()
                .collect(Collectors.toMap(
                        OperationCommand::getType, Function.identity()
                ));
    }

    @PostConstruct
    private void init(){
        System.out.println("MiniBank started. Type EXIT to stop.");
    }

    @PreDestroy
    private void close(){
        System.out.println("MiniBank stopped.");
    }

    public void open(){
        while(true){
            try {
                input.printAvailableCommands();
                ConsoleOperationType command = input.readCommand();

                if (command.equals(ConsoleOperationType.EXIT))
                    break;

                OperationCommand operationCommand = operations.get(command);

                if (operationCommand == null)
                    throw new NoSuchElementException("Not found operation by command '" + command + "'");

                operationCommand.execute();
            } catch (EOFException exception){
                break;
            } catch (NoSuchElementException | IllegalArgumentException | IllegalStateException exception){
                System.out.println("Error: " + exception.getMessage());
            }
        }
    }
}

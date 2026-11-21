package sorokin.dev.operations;

public interface OperationCommand {
    void execute();
    ConsoleOperationType getType();
}

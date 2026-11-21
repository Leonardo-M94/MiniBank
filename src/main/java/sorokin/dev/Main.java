package sorokin.dev;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sorokin.dev.config.ApplicationConfiguration;
import sorokin.dev.console.OperationsConsoleListener;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

        OperationsConsoleListener listener = context.getBean(OperationsConsoleListener.class);

        listener.open();

        context.close();
    }
}

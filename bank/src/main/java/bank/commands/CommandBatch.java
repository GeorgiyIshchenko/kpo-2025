package bank.commands;

import java.util.ArrayList;
import java.util.List;

public class CommandBatch {

    private final List<Command<?>> commands = new ArrayList<>();

    public void addCommand(Command<?> command) {
        commands.add(command);
    }

    public void executeAll() {
        for (Command<?> cmd : commands) {
            try {
                cmd.execute();
            } catch (Exception e) {
                System.err.println("Ошибка при выполнении команды " + cmd.getClass().getSimpleName()
                        + ": " + e.getMessage());
                break; // Прерываем дальнейшее выполнение
            }
        }
        commands.clear();
    }

    public boolean isEmpty() {
        return commands.isEmpty();
    }
}

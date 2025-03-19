package bank.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimingCommandDecorator<T> implements Command<T> {

    private final Command<T> wrappedCommand;

    public TimingCommandDecorator(Command<T> wrappedCommand) {
        this.wrappedCommand = wrappedCommand;
    }

    @Override
    public T execute() {
        long start = System.nanoTime();
        T result = null;
        try {
            result = wrappedCommand.execute();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        long end = System.nanoTime();

        long duration = end - start;
        log.info("Время выполнения команды {}: {} нс", wrappedCommand.getClass().getSimpleName(), duration);

        return result;
    }
}

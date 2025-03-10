package bank.commands;

public interface Command<T> {
    T execute();
}

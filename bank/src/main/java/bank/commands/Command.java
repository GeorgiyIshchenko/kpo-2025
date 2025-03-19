package bank.commands;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Command<T> {
    T execute() throws JsonProcessingException;
}

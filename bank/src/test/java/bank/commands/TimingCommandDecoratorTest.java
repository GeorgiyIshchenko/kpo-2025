package bank.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TimingCommandDecoratorTest {

    @Test
    void testExecuteReturnsWrappedResultAndMeasuresTime() throws JsonProcessingException {
        @SuppressWarnings("unchecked")
        Command<String> mockCommand = Mockito.mock(Command.class);
        when(mockCommand.execute()).thenReturn("Hello");
        TimingCommandDecorator<String> decorator = new TimingCommandDecorator<>(mockCommand);
        String result = decorator.execute();
        verify(mockCommand, times(1)).execute();
        assertEquals("Hello", result);
    }

    @Test
    void testExecuteWithSlightDelay() throws InterruptedException, JsonProcessingException {
        @SuppressWarnings("unchecked")
        Command<String> mockCommand = Mockito.mock(Command.class);
        when(mockCommand.execute()).thenAnswer(invocation -> {
            Thread.sleep(50);
            return "DelayedResult";
        });
        TimingCommandDecorator<String> decorator = new TimingCommandDecorator<>(mockCommand);
        long start = System.nanoTime();
        String result = decorator.execute();
        long end = System.nanoTime();
        verify(mockCommand).execute();
        assertEquals("DelayedResult", result);

        long duration = end - start;
        System.out.println("Test duration: " + duration + " ns");
    }
}
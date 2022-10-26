package ru.mail.polis.homework.generics.bot.handler;

import ru.mail.polis.homework.generics.bot.state.ExampleState;
import ru.mail.polis.homework.generics.bot.state.State;

public class ExampleStateHandler<T extends State> implements StateHandler<ExampleState> {

    @Override
    public void handle() {
        // do nothing
    }

    /**
     * Поправить в соответствии с изменениями в StateHandler
     * <p>
     * 1 тугрик
     */
    @Override
    public Class<ExampleState> getHandlingStateClass() {
        return ExampleState.class;
    }
}

package ru.mail.polis.homework.generics.bot.handler;

import ru.mail.polis.homework.generics.bot.state.ExampleState;
import ru.mail.polis.homework.generics.bot.state.State;

public class ExampleStateHandler implements StateHandler<State> {

    @Override
    public void handle() {
        // do nothing
    }

    /**
     * Поправить в соответствии с изменениями в StateHandler
     *
     * 1 тугрик
     * @return ExampleState
     */
    @Override
    public ExampleState getHandlingStateClass() {
        return null;
    }
}

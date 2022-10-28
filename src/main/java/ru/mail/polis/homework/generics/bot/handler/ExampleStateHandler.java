package ru.mail.polis.homework.generics.bot.handler;

import ru.mail.polis.homework.generics.bot.state.State;

public class ExampleStateHandler implements StateHandler<State> {

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
    public Class<State> getHandlingStateClass() {
        return State.class;
    }
}

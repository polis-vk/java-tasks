package ru.mail.polis.homework.generics.bot.handler;

import ru.mail.polis.homework.generics.bot.state.State;

public class ExampleStateHandler<T extends State> implements StateHandler<T> {

    @Override
    public void handle() {
        // do nothing
    }

    /**
     * Поправить в соответствии с изменениями в StateHandler
     *
     * 1 тугрик
     */
    @Override
    public T getHandlingStateClass() {
        return null;
    }
}

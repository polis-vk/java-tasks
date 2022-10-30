package ru.mail.polis.homework.generics.bot.handler;
import ru.mail.polis.homework.generics.bot.state.ExampleState;

public class ExampleStateHandler implements StateHandler<ExampleState> {

    @Override
    public void handle() {

    }


    /**
     * Поправить в соответствии с изменениями в StateHandler
     *
     * 1 тугрик
     */

    @Override
    public Class<ExampleState> getHandlingStateClass() {
        return ExampleState.class;
    }
}

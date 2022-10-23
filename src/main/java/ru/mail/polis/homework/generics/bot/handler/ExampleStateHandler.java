package ru.mail.polis.homework.generics.bot.handler;

import ru.mail.polis.homework.generics.bot.state.ExampleState;

// Имплементируем интерфейс, работающий с наследниками State. Так как мы хотим, чтобы этот StateHandler работал с
// конкретным наследником State - ExampleState, то указываем его тип для имплементации. Возвращаем также этот тип в
// getHandlerStateClass.
public class ExampleStateHandler implements StateHandler<ExampleState> {

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

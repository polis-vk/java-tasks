package ru.mail.polis.homework.generics.bot.handler;

/**
 * Интерфейс должен быть типизирован, в качестве типа должен быть обрабатываемый тип State
 * <p>
 * 1 тугрик за типизацию интерфейса и 1 тугрик за типизацию метода
 */
public interface StateHandler<State> {
    void handle();

    /**
     * Возвращает класс State, который этот handler умеет обрабатывать.
     * Необходимо типизировать таким образом, чтобы возвращаемый класс был не любым, а наследником State.
     */
    State getHandlingStateClass();
}

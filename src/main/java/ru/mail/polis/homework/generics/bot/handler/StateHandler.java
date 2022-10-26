package ru.mail.polis.homework.generics.bot.handler;

import ru.mail.polis.homework.generics.bot.state.State;

/**
 * Интерфейс должен быть типизирован, в качестве типа должен быть обрабатываемый тип State
 *
 * 1 тугрик за типизацию интерфейса и 1 тугрик за типизацию метода
 */
public interface StateHandler<T extends State> {
    void handle();

    /**
     * Возвращает класс State, который этот handler умеет обрабатывать.
     * Необходимо типизировать таким образом, чтобы возвращаемый класс был не любым, а наследником State.
     */
    Class<T> getHandlingStateClass();
}

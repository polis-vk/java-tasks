package ru.mail.polis.homework.exception;

/**
 * Подключение к роботу, которое позволяет его перемещать в заданную точку. Подключение в любой момент может
 * потерять связь с роботом и тогда любой из методов кидает исключение.
 * Подключение временное и оно тратит ресурсы.
 *
 * 2 тугрика
 */
public interface RobotConnection extends AutoCloseable {

    /**
     * Перемещает робота в заданную точку.
     */
    void moveRobotTo(int x, int y);

    @Override
    void close();
}

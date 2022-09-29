package ru.mail.polis.homework.exception;

/**
 * Класс отвечает за установку соединения с роботом.
 * Робот может управляться только через RobotConnection
 *
 * 2 тугрика
 */
public interface RobotConnectionManager {

    /**
     * Делает попытку соединиться с роботом, и если получилось, возвращает это соединение.
     * Если соединение не установилось - то метод кидает исключение
     */
    RobotConnection getConnection(int robotId);
}

package ru.mail.polis.homework.exception;

public class RobotConnectionException extends Exception{
    RobotConnectionException()
    {
        super("Подключение прервано");
    }
}

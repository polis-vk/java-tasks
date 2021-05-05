package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class Salary extends Incoming<Number> {
    public Salary(String receiver, String sender, Number incomingObject) {
        super(receiver, sender, incomingObject);
    }
}

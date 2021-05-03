package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class Salary extends InboxMessage<Double> {
    public Salary(String sender, String addressee, Double message) {
        super(sender, addressee, message);
    }
}

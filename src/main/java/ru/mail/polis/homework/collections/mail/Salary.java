package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class Salary extends Notification<Double>{

    public Salary(String recipient, String sender, Double content) {
        super(recipient, sender, content);
    }
}

package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class Salary<T> extends Incoming<T> {
    public Salary(String receiver, String sender, T content) {
        super(receiver, sender, content);
    }
}

package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Salary<T> extends Mail<T> {

    public Salary(String recipient, String sender, T massageContent) {
        super(recipient, sender, massageContent);
    }

}

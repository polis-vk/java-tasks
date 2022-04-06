package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Salary extends Mail<Long> {

    public Salary(String addressee, String addresser, long salary) {
        super(addressee, addresser, salary);
    }
}

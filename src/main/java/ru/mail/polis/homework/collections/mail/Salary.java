package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
class Salary extends Mail<Integer> {
    Salary(String recipient, String sender, int salary) {
        super(recipient, sender, salary);
    }
}

package ru.mail.polis.homework.collections.mail;

/**
 * 1 балл
 */
public class Salary extends MessagingUnit<Long> {

    Salary(String receiver, String sender, long salary) {
        super(receiver, sender, salary);
    }
}

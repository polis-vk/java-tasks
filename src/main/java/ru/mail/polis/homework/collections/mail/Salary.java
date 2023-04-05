package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Salary extends MailMessage<Integer> {

    public Salary(Mail sender, Mail recipient, Integer salary) {
        super(sender, recipient, salary);
    }
}

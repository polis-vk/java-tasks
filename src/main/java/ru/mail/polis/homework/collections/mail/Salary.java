package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Salary extends Mail<Integer> {
    private MailMessage<Integer> amount;

    public Salary(String addressee, String sender, MailMessage<Integer> amount) {
        super(addressee, sender, amount);
    }
}

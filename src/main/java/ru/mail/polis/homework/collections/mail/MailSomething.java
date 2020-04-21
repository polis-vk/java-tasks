package ru.mail.polis.homework.collections.mail;

public class MailSomething<T> {
    private final String mailReceiver;
    private final String mailSender;
    private final T sentThing;

    public MailSomething(String mailReceiver, String mailSender, T sentThing) {
        this.mailReceiver = mailReceiver;
        this.mailSender = mailSender;
        this.sentThing = sentThing;
    }

    public String getMailReceiver() {
        return this.mailReceiver;
    }

    public String getMailSender() {
        return this.mailSender;
    }

    public T getSentThing() {
        return this.sentThing;
    }

}

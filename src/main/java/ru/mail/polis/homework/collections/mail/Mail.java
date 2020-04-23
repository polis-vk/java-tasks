package ru.mail.polis.homework.collections.mail;

public class Mail<T> {
    private final String mailRecipient;
    private final String mailSender;
    private final T content;

    public Mail(String mailRecipient, String mailSender, T content) {
        this.mailRecipient = mailRecipient;
        this.mailSender = mailSender;
        this.content = content;
    }

    public String getMailRecipient() {
        return mailRecipient;
    }

    public String getMailSender() {
        return mailSender;
    }

    public T getContent() {
        return content;
    }

    @Override
    public String toString() {
        return
            "mailRecipient = '" + mailRecipient + '\'' +
                ", mailSender = '" + mailSender + '\'' +
                ", text = " + content + "\n";
    }
}

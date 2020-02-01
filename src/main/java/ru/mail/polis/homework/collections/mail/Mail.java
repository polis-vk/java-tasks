package ru.mail.polis.homework.collections.mail;

public class Mail {
    Mail(String sender, String recipient, MailType type) {
        this.sender = sender;
        this.recipient = recipient;
        this.type = type;
    }

    private final String sender;
    private final String recipient;
    private final MailType type;

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public MailType getType() {
        return type;
    }

    enum MailType {
        MAIL_TYPE_SALARY,
        MAIL_TYPE_MESSAGE
    }
}

package ru.mail.polis.homework.collections.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * 1 тугрик
 */
public class Mail<M extends MailMessage<?>> {
    private final String mailbox;
    private final List<M> receivedMessages;
    private final List<M> sentMessages;


    public Mail(String mailbox) {
        this.mailbox = mailbox;
        receivedMessages = new ArrayList<>();
        sentMessages = new ArrayList<>();
    }

    public String getMailbox() {
        return mailbox;
    }

    public List<M> getReceivedMessages() {
        return receivedMessages;
    }

    public List<M> getSentMessages() {
        return sentMessages;
    }
}

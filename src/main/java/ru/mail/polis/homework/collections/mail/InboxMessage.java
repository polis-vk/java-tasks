package ru.mail.polis.homework.collections.mail;

import java.util.Objects;

public class InboxMessage<T> {
    private final String sender;
    private final String addressee;
    private final T message;


    public InboxMessage(String sender, String addressee, T message) {
        this.sender = sender;
        this.addressee = addressee;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getAddressee() {
        return addressee;
    }

    public T getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InboxMessage<?> inboxMessage = (InboxMessage<?>) o;
        return Objects.equals(sender, inboxMessage.sender) &&
                Objects.equals(addressee, inboxMessage.addressee) &&
                Objects.equals(inboxMessage, inboxMessage.message);
    }
}

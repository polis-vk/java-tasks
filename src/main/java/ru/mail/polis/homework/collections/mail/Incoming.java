package ru.mail.polis.homework.collections.mail;

import java.util.Objects;

public class Incoming<T> {// класс входящий объект
    private final String receiver;
    private final String sender;
    private final T incomingObject; // зарплата или письмо

    public Incoming(String receiver, String sender, T incomingObject) {
        this.receiver = receiver;
        this.sender = sender;
        this.incomingObject = incomingObject;
    }

    public String getReceiver() {
        return receiver;
    }

    public T getIncomingObject() {
        return incomingObject;
    }

    public String getSender() {
        return sender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Incoming<?> incoming = (Incoming<?>) o;
        return Objects.equals(receiver, incoming.receiver) &&
                Objects.equals(sender, incoming.sender) &&
                Objects.equals(incomingObject, incoming.incomingObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiver, sender, incomingObject);
    }

    @Override
    public String toString() {
        return "Incoming{" +
                "receiver='" + receiver + '\'' +
                ", sender='" + sender + '\'' +
                ", incomingObject=" + incomingObject +
                '}';
    }
}


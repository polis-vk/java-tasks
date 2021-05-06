package ru.mail.polis.homework.collections.mail;

import java.util.Objects;

public class Mail<T> {
    private final String sender;
    private final String address;
    private final T message;


    public Mail(String sender, String address, T message) {
        this.sender = sender;
        this.address = address;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getAddressee() {
        return address;
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
        Mail<?> mail = (Mail<?>) o;
        return Objects.equals(this.address, mail.address)
                && Objects.equals(this.sender, mail.sender)
                && Objects.equals(this.message, mail.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.address, this.sender, this.message);
    }


}

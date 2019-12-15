package ru.mail.polis.homework.collections.mail.transmissions;

import ru.mail.polis.homework.collections.mail.users.User;

import java.util.Objects;

public class TrasmissionObject<T> {

    private final User receiver;
    private final User sender;
    private final T data;

    public TrasmissionObject(User receiver, User sender, T data) {
        this.receiver = receiver;
        this.sender = sender;
        this.data = data;
    }

    public User getReceiver() {
        return receiver;
    }

    public User getSender() {
        return sender;
    }

    public T getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrasmissionObject<?> that = (TrasmissionObject<?>) o;
        return receiver.equals(that.receiver)
                && sender.equals(that.sender)
                && data.equals(that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiver, sender, data);
    }

}

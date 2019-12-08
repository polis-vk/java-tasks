package ru.mail.polis.homework.collections.mail.mailsystem;

import ru.mail.polis.homework.collections.mail.person.Person;

import java.util.Objects;

public class AbstractSentReceivedObject<T> {

    private final Person receiver;
    private final Person sender;
    private final T data;

    public AbstractSentReceivedObject(Person receiver, Person sender, T data) {
        this.receiver = receiver;
        this.sender = sender;
        this.data = data;
    }

    public Person getReceiver() {
        return receiver;
    }

    public Person getSender() {
        return sender;
    }

    public T getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractSentReceivedObject<?> that = (AbstractSentReceivedObject<?>) o;
        return receiver.equals(that.receiver)
            && sender.equals(that.sender)
            && data.equals(that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiver, sender, data);
    }
}

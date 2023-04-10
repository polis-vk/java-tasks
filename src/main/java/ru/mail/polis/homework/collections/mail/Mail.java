package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    private final String consumer;
    private final String producer;
    private final T message;

    public Mail(String consumer, String producer, T message) {
        this.consumer = consumer;
        this.producer = producer;
        this.message = message;
    }
    public String getConsumer() {
        return consumer;
    }

    public String getProducer() {
        return producer;
    }

    public T getMessage()  {
        return message;
    }
}

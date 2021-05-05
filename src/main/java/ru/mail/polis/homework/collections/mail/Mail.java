package ru.mail.polis.homework.collections.mail;

import java.util.Objects;

public class Mail<T> {
    private final String destination;
    private final String source;
    private final T contents;

    public Mail(String dest, String src, T cont) {
        this.destination = dest;
        this.source = src;
        this.contents = cont;
    }

    public String getDestination() {
        return this.destination;
    }

    public String getSource() {
        return this.source;
    }

    public T getContents() {
        return this.contents;
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
        return Objects.equals(this.destination, mail.destination)
                && Objects.equals(this.source, mail.source)
                && Objects.equals(this.contents, mail.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.destination, this.source, this.contents);
    }

    @Override
    public String toString() {
        return "Mail{ destination = "+this.destination+", source = "+this.source
                +", contents = "+this.contents+" }";
    }
}

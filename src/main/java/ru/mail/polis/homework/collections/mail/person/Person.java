package ru.mail.polis.homework.collections.mail.person;

import ru.mail.polis.homework.collections.mail.mailsystem.AbstractSentReceivedObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Person implements AbleToReceive, AbleToSend {

    private List<AbstractSentReceivedObject<?>> listOfReceivedObjects;
    private String fio;
    private boolean isMan;
    private int age;

    public Person(String fio, boolean isMan, int age) {
        this.listOfReceivedObjects = new ArrayList<>();
        this.fio = fio;
        this.isMan = isMan;
        this.age = age;
    }

    @Override
    public void receive(AbstractSentReceivedObject<?> object) {
        listOfReceivedObjects.add(object);
    }

    @Override
    public AbstractSentReceivedObject<?> send(AbstractSentReceivedObject<?> object) {
        return object;
    }

    public String getFio() {
        return fio;
    }

    public boolean isMan() {
        return isMan;
    }

    public int getAge() {
        return age;
    }

    public List<AbstractSentReceivedObject<?>> getListOfReceivedObjects() {
        return listOfReceivedObjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return isMan() == person.isMan()
            && getAge() == person.getAge()
            && getFio().equals(person.getFio());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFio(), isMan(), getAge());
    }
}

package ru.mail.polis.homework.collections.mail.mailsystem;

import ru.mail.polis.homework.collections.mail.person.Person;

public class Salary extends AbstractSentReceivedObject<Integer> {

    public Salary(Person receiver, Person sender, Integer data) {
        super(receiver, sender, data);
    }
}

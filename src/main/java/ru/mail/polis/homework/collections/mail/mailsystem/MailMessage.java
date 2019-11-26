package ru.mail.polis.homework.collections.mail.mailsystem;

import ru.mail.polis.homework.collections.mail.person.Person;

public class MailMessage extends AbstractSentReceivedObject<String> {

    public MailMessage(Person receiver, Person sender, String data) {
        super(receiver, sender, data);
    }
}

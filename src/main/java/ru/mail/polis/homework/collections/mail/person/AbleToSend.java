package ru.mail.polis.homework.collections.mail.person;

import ru.mail.polis.homework.collections.mail.mailsystem.AbstractSentReceivedObject;

public interface AbleToSend {

    AbstractSentReceivedObject<?> send(AbstractSentReceivedObject<?> object);
}

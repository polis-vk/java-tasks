package ru.mail.polis.homework.collections.mail.person;

import ru.mail.polis.homework.collections.mail.mailsystem.AbstractSentReceivedObject;

public interface AbleToReceive {

    void receive(AbstractSentReceivedObject<?> object);
}

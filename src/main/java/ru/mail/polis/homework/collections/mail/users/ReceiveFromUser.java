package ru.mail.polis.homework.collections.mail.users;

import ru.mail.polis.homework.collections.mail.transmissions.TrasmissionObject;

public interface ReceiveFromUser {

    void receive(TrasmissionObject<?> object);

}

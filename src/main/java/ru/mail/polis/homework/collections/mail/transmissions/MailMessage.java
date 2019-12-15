package ru.mail.polis.homework.collections.mail.transmissions;

import ru.mail.polis.homework.collections.mail.users.User;

public class MailMessage extends TrasmissionObject<String> {

    public MailMessage(User receiver, User sender, String data) {
        super(receiver, sender, data);
    }

}

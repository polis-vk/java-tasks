package ru.mail.polis.homework.collections.mail.transmissions;

import ru.mail.polis.homework.collections.mail.users.User;

public class Salary extends TrasmissionObject<Integer> {

    public Salary(User receiver, User sender, Integer data) {
        super(receiver, sender, data);
    }

}

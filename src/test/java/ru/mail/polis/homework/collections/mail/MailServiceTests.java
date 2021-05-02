package ru.mail.polis.homework.collections.mail;

import junit.framework.TestCase;

public class MailServiceTests<T extends Incoming<?>> extends TestCase {
    Salary<Integer> salary1 = new Salary<>("Ira", "Vova", 5000);
    MailMessage<String> message1 = new MailMessage<>("Regina", "Dima", "How are you?");
    MailMessage<String> message2 = new MailMessage<>("Regina", "Dima", "Happy New Year!");
    MailService<Incoming<?>> service = new MailService<>();


    public void testGetPopularSender() {
        service.accept(salary1);
        service.accept(message1);
        service.accept(message2);
        assertEquals("Dima", service.getPopularSender());
    }

    public void testGetPopularRecipient() {
        service.accept(salary1);
        service.accept(message1);
        service.accept(message2);
        assertEquals("Regina", service.getPopularRecipient());
    }
}
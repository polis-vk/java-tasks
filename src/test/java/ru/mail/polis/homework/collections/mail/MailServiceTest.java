package ru.mail.polis.homework.collections.mail;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static ru.mail.polis.homework.collections.mail.MailService.process;

public class MailServiceTest {
    private MailService<SimpleMessage<?>> mailService1;
    private MailService<SimpleMessage<?>> mailService2;

    @Before
    public void setUp() {
        mailService1 = new MailService<>();
        mailService2 = new MailService<>();
    }

    @Test
    public void onlyOneSenderSalary() {
        List<SimpleMessage<Integer>> messages = new ArrayList<>();
        messages.add(new Salary("Jey", "Bob", 1000));
        process(mailService1, messages);
        Map<String, List<SimpleMessage<?>>> recipients = mailService1.getMailBox();

        assertTrue(recipients.containsKey("Bob"));
        assertEquals("Bob", mailService1.getPopularRecipient());
        assertEquals("Jey", mailService1.getPopularSender());
    }

    @Test
    public void onlyOneSenderMail() {
        List<SimpleMessage<String>> messages = new ArrayList<>();
        messages.add(new MailMessage("Jey", "Bob", "Hello!"));
        process(mailService1, messages);
        Map<String, List<SimpleMessage<?>>> recipients = mailService1.getMailBox();

        assertTrue(recipients.containsKey("Bob"));
        assertEquals("Bob", mailService1.getPopularRecipient());
        assertEquals("Jey", mailService1.getPopularSender());
    }

    @Test
    public void manySender() {
        List<SimpleMessage<?>> messages = new ArrayList<>();
        messages.add(new Salary("Jey", "Bob", 1000));
        messages.add(new MailMessage("Jey", "Bob", "С днем рожденья!"));
        messages.add(new MailMessage("Bob", "Jey", "Спасибо!"));
        process(mailService1, messages);
        Map<String, List<SimpleMessage<?>>> recipients = mailService1.getMailBox();

        assertTrue(recipients.containsKey("Bob"));
        assertTrue(recipients.containsKey("Jey"));
        assertEquals("Bob", mailService1.getPopularRecipient());
        assertEquals("Jey", mailService1.getPopularSender());
    }

    @Test
    public void manyServices() {
        List<SimpleMessage<?>> messages = new ArrayList<>();
        messages.add(new Salary("Jey", "Bob", 1000));
        messages.add(new MailMessage("Jey", "Bob", "С днем рожденья!"));
        messages.add(new MailMessage("Bob", "Jey", "Спасибо!"));

        process(mailService1, messages);
        process(mailService2, messages);
        Map<String, List<SimpleMessage<?>>> recipients1 = mailService1.getMailBox();
        Map<String, List<SimpleMessage<?>>> recipients2 = mailService1.getMailBox();

        assertTrue(recipients1.containsKey("Bob"));
        assertTrue(recipients1.containsKey("Jey"));
        assertEquals("Bob", mailService1.getPopularRecipient());
        assertEquals("Jey", mailService1.getPopularSender());

        assertTrue(recipients2.containsKey("Bob"));
        assertTrue(recipients2.containsKey("Jey"));
        assertEquals("Bob", mailService2.getPopularRecipient());
        assertEquals("Jey", mailService2.getPopularSender());
    }
}

package ru.mail.polis.homework.collections.mail;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class MailServiceTest extends MailService {

    private MailService service = new MailService();

    private List<Mail> mails = new ArrayList<>();


    @Test
    public void onlyPopularity() {
        mails.add(new Salary(1000, "Boss1", "Employee1"));

        process(service, mails);
        assertEquals("Boss1", service.getPopularSender());
        assertEquals("Employee1", service.getPopularRecipient());

        mails.add(new MailMessage("Hello1", "Boss1", "Employee2"));
        mails.add(new MailMessage("Hello2", "Boss2", "Employee1"));

        process(service, mails);
        assertEquals("Boss1", service.getPopularSender());
        assertEquals("Employee1", service.getPopularRecipient());
    }

    @Test
    public void mailBoxTest() {
        mails.add(new Salary(100, "Boss1", "Employee1"));
        mails.add(new Salary(200, "Boss1", "Employee2"));
        process(service, mails);
        Map<String, List> recipients = service.getMailBox();

        assertTrue(recipients.containsKey("Employee1"));
        assertTrue(recipients.containsKey("Employee2"));
    }
}
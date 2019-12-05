package ru.mail.polis.homework.collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import ru.mail.polis.homework.collections.mail.Mail;
import ru.mail.polis.homework.collections.mail.MailMessage;
import ru.mail.polis.homework.collections.mail.MailService;
import ru.mail.polis.homework.collections.mail.Salary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MailServiceTest {

    private MailService mailService;

    private MailMessage mailMessage1 = new MailMessage("sender1", "sender2", "text");
    private MailMessage mailMessage2 = new MailMessage("sender1", "sender3", "text");
    private MailMessage mailMessage3 = new MailMessage("sender2", "sender3", "100");

    private Salary salary1 = new Salary("sender3", "sender2", 500);
    private Salary salary2 = new Salary("sender2", "sender3", 100);

    @Before
    public void initService() {
        mailService = new MailService();
    }

    @Test
    public void accept() {
        assertNull(mailService.getPopularRecipient());
        assertNull(mailService.getPopularSender());

        mailService.accept(mailMessage1);

        Map<String, List<Mail>> mailBox = mailService.getMailBox();
        assertTrue(mailBox.containsKey("sender2"));

        mailService.accept(salary1);
        assertTrue(mailBox.containsKey("sender2"));
        assertEquals(2, mailBox.get("sender2").size());

        mailService.accept(mailMessage2);

        assertEquals("sender1", mailService.getPopularSender());
        assertEquals("sender2", mailService.getPopularRecipient());

        mailService.accept(salary2);

        mailService.accept(mailMessage3);
        mailService.accept(salary2);

        assertEquals("sender2", mailService.getPopularSender());
        assertEquals("sender3", mailService.getPopularRecipient());
    }

    @Test
    public void process() {
        List<Mail> mails = new ArrayList<>();

        mails.add(mailMessage1);
        mails.add(mailMessage2);
        mails.add(mailMessage3);
        mails.add(salary1);
        mails.add(salary2);

        MailService.process(mailService, mails);

        Map<String, List<Mail>> mailBox = mailService.getMailBox();

        assertFalse(mailBox.containsKey("sender1"));
        assertTrue(mailBox.containsKey("sender2"));
        assertTrue(mailBox.containsKey("sender3"));

        assertEquals(2, mailBox.get("sender2").size());
        assertEquals(3, mailBox.get("sender3").size());

        assertEquals("sender2", mailService.getPopularSender());
        assertEquals("sender3", mailService.getPopularRecipient());
    }
}
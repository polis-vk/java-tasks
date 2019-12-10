package ru.mail.polis.homework.collections;

import org.junit.Test;
import ru.mail.polis.homework.collections.mail.Mail;
import ru.mail.polis.homework.collections.mail.MailMessage;
import ru.mail.polis.homework.collections.mail.MailService;
import ru.mail.polis.homework.collections.mail.Salary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class MailServiceTest {

    private Map<String, List<Mail>> senders = new HashMap<>();
    private Map<String, List<Mail>> receivers = new HashMap<>();

    private MailService mailService = new MailService(senders, receivers);

    private MailMessage fromP1ToP2Message = new MailMessage("person1", "person2", "_");
    private MailMessage fromP1ToP3Message = new MailMessage("person1", "person3", "text");
    private MailMessage fromP2ToP3Message = new MailMessage("person2", "person3", "100");

    private Salary fromP3ToP3Salary = new Salary("person2", "person3", 200);
    private Salary fromP3ToP2Salary = new Salary("person3", "person2", 5000);

    @Test
    public void MailMethodsTest() {
        assertEquals(5000, fromP3ToP2Salary.getNumber());
        assertEquals("text", fromP1ToP3Message.getText());
    }

    @Test
    public void accept() {
        Map<String, List<Mail>> mailBox = mailService.getMailBox();

        mailService.accept(fromP1ToP2Message);

        assertTrue(mailBox.containsKey("person2"));


        mailService.accept(fromP3ToP2Salary);
        assertTrue(mailBox.containsKey("person2"));
        assertEquals(2, mailBox.get("person2").size());

        mailService.accept(fromP1ToP3Message);
        mailService.accept(fromP2ToP3Message);
        mailService.accept(fromP3ToP3Salary);

        assertEquals(5, flattenReceivers(mailBox).size());
    }

    @Test
    public void getPopularSender() {
        assertEquals(senders, new HashMap<>());

        mailService.accept(fromP1ToP2Message);
        mailService.accept(fromP1ToP3Message);
        mailService.accept(fromP2ToP3Message);

        assertEquals("person1", mailService.getPopularSender());
    }

    @Test
    public void getPopularReceiver() {
        assertEquals(senders, new HashMap<>());

        mailService.accept(fromP1ToP2Message);
        mailService.accept(fromP1ToP3Message);
        mailService.accept(fromP2ToP3Message);

        assertEquals("person3", mailService.getPopularRecipient());
    }

    @Test
    public void process() {
        List<Mail> mails = new ArrayList<>();

        mails.add(fromP1ToP2Message);
        mails.add(fromP1ToP3Message);
        mails.add(fromP2ToP3Message);
        mails.add(fromP3ToP2Salary);
        mails.add(fromP3ToP3Salary);

        MailService.process(mailService, mails);

        Map<String, List<Mail>> mailBox = mailService.getMailBox();

        assertFalse(mailBox.containsKey("person1"));
        assertTrue(mailBox.containsKey("person2"));
        assertTrue(mailBox.containsKey("person3"));

        assertEquals(2, mailBox.get("person2").size());
        assertEquals(3, mailBox.get("person3").size());

        assertEquals(5, flattenReceivers(mailBox).size());
    }

    private List<Mail> flattenReceivers(Map<String, List<Mail>> mailBox) {
        List<Mail> list = new ArrayList<>();

        mailBox.values().forEach(list::addAll);

        return list;
    }
}

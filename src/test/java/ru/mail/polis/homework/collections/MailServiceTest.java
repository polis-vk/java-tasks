package ru.mail.polis.homework.collections;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import ru.mail.polis.homework.collections.mail.Mail;
import ru.mail.polis.homework.collections.mail.MailMessage;
import ru.mail.polis.homework.collections.mail.MailService;
import ru.mail.polis.homework.collections.mail.Salary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MailServiceTest {

    private MailService service;

    @Before
    public void setUp() {
        service = new MailService();
    }


    @Test
    public void getMailBox() {
        MailMessage message1 = new MailMessage("sender1", "recipient2", "some text");
        MailMessage message2 = new MailMessage("sender2", "recipient1", "");
        MailMessage message3 = new MailMessage("sender1", "recipient2", "message");
        Salary salary1 = new Salary("sender3", "recipient2", 500);
        Salary salary2 = new Salary("sender4", "recipient3", 100500);

        service.accept(message1);
        service.accept(message2);
        service.accept(message3);
        service.accept(salary1);
        service.accept(salary2);

        Map<String, List<Mail>> requiredMailBox = new HashMap<>();
        requiredMailBox.put("recipient1", new ArrayList<>());
        requiredMailBox.get("recipient1").add(message2);
        requiredMailBox.put("recipient2", new ArrayList<>());
        requiredMailBox.get("recipient2").add(message1);
        requiredMailBox.get("recipient2").add(message3);
        requiredMailBox.get("recipient2").add(salary1);
        requiredMailBox.put("recipient3", new ArrayList<>());
        requiredMailBox.get("recipient3").add(salary2);

        assertEquals(requiredMailBox, service.getMailBox());
    }


    @Test
    public void popularity() {
        MailMessage message1 = new MailMessage("sender1", "recipient2", "some text");
        MailMessage message2 = new MailMessage("sender2", "recipient1", "");
        MailMessage message3 = new MailMessage("sender1", "recipient2", "message");
        Salary salary1 = new Salary("sender3", "recipient2", 500);
        Salary salary2 = new Salary("sender4", "recipient3", 100500);

        service.accept(message1);
        service.accept(message2);
        service.accept(salary1);
        service.accept(message1);
        service.accept(salary2);
        service.accept(message3);

        assertEquals("sender1", service.getPopularSender());
        assertEquals("recipient2", service.getPopularRecipient());
    }


    @Test
    public void process() {
        MailMessage message1 = new MailMessage("sender1", "recipient2", "some text");
        MailMessage message2 = new MailMessage("sender2", "recipient1", "");
        MailMessage message3 = new MailMessage("sender1", "recipient2", "message");
        Salary salary1 = new Salary("sender3", "recipient2", 500);
        Salary salary2 = new Salary("sender4", "recipient3", 100500);

        List<Mail> listOfMails = new ArrayList<>();
        listOfMails.add(message1);
        listOfMails.add(message2);
        listOfMails.add(message3);
        listOfMails.add(salary1);
        listOfMails.add(salary2);

        Map<String, List<Mail>> requiredMailBox = new HashMap<>();
        requiredMailBox.put("recipient1", new ArrayList<>());
        requiredMailBox.get("recipient1").add(message2);
        requiredMailBox.put("recipient2", new ArrayList<>());
        requiredMailBox.get("recipient2").add(message1);
        requiredMailBox.get("recipient2").add(message3);
        requiredMailBox.get("recipient2").add(salary1);
        requiredMailBox.put("recipient3", new ArrayList<>());
        requiredMailBox.get("recipient3").add(salary2);

        MailService.process(service, listOfMails);

        assertEquals(requiredMailBox, service.getMailBox());
    }

}

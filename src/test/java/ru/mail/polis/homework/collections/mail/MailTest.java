package ru.mail.polis.homework.collections.mail;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class MailTest {
    @Test
    public void mailTest() {
        String sender1 = "Sender1";
        String sender2 = "Sender2";
        String recipient1 = "Recipient1";
        String recipient2 = "Recipient2";
        MailMessage message1 = new MailMessage(sender1, recipient1, "text of first message");
        MailMessage message2 = new MailMessage(sender2, recipient2, "text of second message");
        MailMessage message3 = new MailMessage(sender1, recipient2, "text of third message");
        Salary salary1 = new Salary(sender1, recipient2, 300);
        List<Mail> mailList = new ArrayList<>();

        mailList.add(message1);
        mailList.add(message2);
        mailList.add(message3);
        mailList.add(salary1);

        MailService<Mail> mailService = new MailService<>();

        MailService.process(mailService, mailList);

        Map<String, List<Mail>> mailBox = new HashMap<>();
        List<Mail> recipient1MailBox = new ArrayList<>();
        recipient1MailBox.add(message1);
        mailBox.put(recipient1, recipient1MailBox);
        List<Mail> recipient2MailBox = new ArrayList<>();
        recipient2MailBox.add(message2);
        recipient2MailBox.add(message3);
        recipient2MailBox.add(salary1);
        mailBox.put(recipient2, recipient2MailBox);

        assertEquals(sender1, mailService.getPopularSender());
        assertEquals(recipient2, mailService.getPopularRecipient());
        assertEquals(mailBox, mailService.getMailBox());
    }
}

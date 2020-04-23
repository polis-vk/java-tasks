package ru.mail.polis.homework.collections;

import org.junit.Test;
import ru.mail.polis.homework.collections.mail.Mail;
import ru.mail.polis.homework.collections.mail.MailService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


public class MailServiceTest {
    @Test
    public void getPopularRecipient() {
        List<Mail> mails = new ArrayList<>();
        MailService<Mail> serverAlpha = new MailService();

        Mail<Integer> m1 = new Mail<>("Daniil", "Anton", 100);
        Mail<Integer> m2 = new Mail<>("Ant", "Ser", 30);
        Mail<Integer> m3 = new Mail<>("Roma", "Res", 34);
        Mail<Integer> m4 = new Mail<>("Daniil", "Egor", 25);

        mails.add(m1);
        mails.add(m2);
        mails.add(m3);
        mails.add(m4);

        MailService.process(serverAlpha, mails);
        assertEquals("Daniil", serverAlpha.getPopularRecipient());
    }

    @Test
    public void getPopularSender() {
        List<Mail> mails = new ArrayList<>();
        MailService<Mail> serverAlpha = new MailService();

        Mail<Integer> m1 = new Mail<>("Daniil", "Anton", 100);
        Mail<Integer> m2 = new Mail<>("Ant", "Ser", 30);
        Mail<Integer> m3 = new Mail<>("Roma", "Anton", 34);
        Mail<Integer> m4 = new Mail<>("Daniil", "Egor", 25);

        mails.add(m1);
        mails.add(m2);
        mails.add(m3);
        mails.add(m4);

        MailService.process(serverAlpha, mails);
        assertEquals("Anton", serverAlpha.getPopularSender());
    }

    @Test
    public void getMailBox() {
        List<Mail> mails = new ArrayList<>();
        MailService<Mail> serverAlpha = new MailService();

        Mail<Integer> m1 = new Mail<>("Daniil", "Anton", 100);
        Mail<Integer> m2 = new Mail<>("Ant", "Ser", 30);
        Mail<Integer> m3 = new Mail<>("Roma", "Anton", 34);

        mails.add(m1);
        mails.add(m2);
        mails.add(m3);

        MailService.process(serverAlpha, mails);
        Map<String, List<Mail>> map1 = serverAlpha.getMailBox();
        assertTrue(map1.containsKey("Daniil") && map1.containsKey("Ant") && map1.containsKey("Roma"));
    }

    @Test
    public void getMailBoxNull() {
        List<Mail> mails = new ArrayList<>();
        MailService<Mail> serverAlpha = new MailService();


        MailService.process(serverAlpha, mails);
        Map<String, List<Mail>> map1 = serverAlpha.getMailBox();
        assertNotNull(map1);
    }
}

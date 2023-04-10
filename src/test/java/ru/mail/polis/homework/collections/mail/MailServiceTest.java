package ru.mail.polis.homework.collections.mail;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MailServiceTest {
    @Test
    public void testMailService() {
        Mail mail_1 = new MailMessage("Gustav", "Oleg", "Hi, Gustav!");
        Mail mail_2 = new MailMessage("Gustav", "Linda", "Hi, Gustaviano!");
        Mail salary_1 = new Salary("Mark", "Oleg", 100);

        List<Mail> mails = new ArrayList<>(Arrays.asList(mail_1, mail_2, salary_1));
        MailService<Mail> service = new MailService<>();
        MailService.process(service, mails);

        assertEquals("Gustav", service.getPopularRecipient());
        assertEquals("Oleg", service.getPopularSender());

        // Демонстрация вывода
        Map<String, List<Mail>> storage = service.getMailBox();
        storage.forEach((receiver, mail) -> System.out.println(mail));
    }
}

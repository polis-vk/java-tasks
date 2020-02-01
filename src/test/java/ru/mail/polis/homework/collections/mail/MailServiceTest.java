package ru.mail.polis.homework.collections.mail;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MailServiceTest {
    private MailService mailService;

    @Before
    public void setUp() {
        mailService = new MailService();
    }

    @Test
    public void mailServiceTest() {
        List<Salary> salaries = new ArrayList<>();
        List<MailMessage> messages = new ArrayList<>();
        salaries.add(new Salary("lol@mail.ru", "kek@mail.ru", 300000));
        salaries.add(new Salary("stasyan@mail.ru", "kek@mail.ru", 3000));
        salaries.add(new Salary("yes@mail.ru", "no@mail.ru", 300000));
        salaries.add(new Salary("putin@kermlin.ru", "medvedev@sovbez.ru", 618713));
        salaries.add(new Salary("zapad@mail.ru", "navalny@mail.ru", 1000000000));
        salaries.add(new Salary("lol@mail.ru", "kek@mail.ru", 300000));
        salaries.add(new Salary("putin@kermlin.ru", "medvedev@sovbez.ru", 618713));
        salaries.add(new Salary("putin@kermlin.ru", "medvedev@sovbez.ru", 618713));

        messages.add(new MailMessage("medinsky@mail.ru", "tihon@mail.ru", "дратути"));
        messages.add(new MailMessage("h-klinton@mail.ru", "lobby@mail.ru", "это письмо утечет"));
        messages.add(new MailMessage("gurbanguli@mail.ru", "berdimuchamedov@mail.ru", "дратути приятно общаться с интересным человеком"));
        messages.add(new MailMessage("yes@mail.ru", "no@mail.ru", "дратути"));
        messages.add(new MailMessage("medinsky@mail.ru", "mihalkov@mail.ru", "дратути давайте утомленные солнцем три"));
        messages.add(new MailMessage("medinsky@mail.ru", "tihon@mail.ru", "досви дания"));
        messages.add(new MailMessage("a@mail.ru", "b@mail.ru", "дратути"));
        messages.add(new MailMessage("putin@kermlin.ru", "medvedev@sovbez.ru", "скучно..."));
        messages.add(new MailMessage("e@mail.ru", "f@mail.ru", "дратути"));

        mailService.accept(salaries);
        MailService.process(mailService, messages);

        assertEquals("putin@kermlin.ru", mailService.getPopularSender());
        assertEquals("medvedev@sovbez.ru", mailService.getPopularRecipient());
    }
}

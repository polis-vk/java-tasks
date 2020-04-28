package ru.mail.polis.homework.collections.mail;

import org.junit.Test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MailServiceTest extends MailService {


    private MailService mailService = new MailService();
     @Test
    public void boxTest() {
         List<Mail> mails =  new ArrayList<>();
         mails.add(new Message("Conor Clapton", "Eric Clapton", "Would you know my name If I saw you in Heaven?"));
         mails.add(new Message("Conor Clapton", "Eric Clapton", "Would it be the same If I saw you in Heaven?"));
         mails.add(new Message("Дарт Вейдер", "Люк Скайукоер", "Ты убил моего отца!"));
         mails.add(new Message("Люк Скайукоер", "Дарт Вейдер", "Нет. Я твой отец."));
         mails.add(new Message("Pattie Boyd", "Eric Clapton", "Layla..."));

         process(mailService, mails);
         Map<String, List> addressee = mailService.getMailBox();
         assertTrue(addressee.containsKey("Conor Clapton"));
         assertTrue(addressee.containsKey("Люк Скайукоер"));
         assertTrue(addressee.containsKey("Дарт Вейдер"));
         assertTrue(addressee.containsKey("Pattie Boyd"));
    }

    @Test
    public void popularityTest(){
        List<Mail> mails =  new ArrayList<>();
        mails.add(new Message("Conor Clapton", "Eric Clapton", "Would you know my name If I saw you in Heaven?"));
        mails.add(new Message("Conor Clapton", "Eric Clapton", "Would it be the same If I saw you in Heaven?"));
        mails.add(new Message("Дарт Вейдер", "Люк Скайукоер", "Ты убил моего отца!"));
        mails.add(new Message("Люк Скайукоер", "Дарт Вейдер", "Нет. Я твой отец."));
        mails.add(new Message("Pattie Boyd", "Eric Clapton", "Layla..."));

        process(mailService, mails);
        assertEquals("Conor Clapton", mailService.getPopularAddressee());
        assertEquals("Eric Clapton", mailService.getPopularSender());

        List<Mail> mailsNew =  new ArrayList<>();
        mailsNew.add(new Message("Граждане РФ", "Борис Ельцин", "Я устал... Я... ухожу."));
        mailsNew.add(new Salary("Малый бизнесс", "Владимир Путин", new Integer(12130)));
        mailsNew.add(new Message("Граждане РФ", "Владимир Путин", "Печенегам и половцам большой привет!!"));
        mailsNew.add(new Message("Василий Уткин", "Владимир Соловьев", "Смотри и озирайся по сторонам"));
        mailsNew.add(new Salary("Владимир Соловьев", "Владимир Путин", new Integer(12130)));
        mailsNew.add(new Message("Василий Уткин", "Александр Невзоров", "Вась!"));
        mailsNew.add(new Message("Граждане РФ", "Алексей Навальный", "Ретвитнул запись Василия Уткина"));
        process(mailService, mailsNew);
        assertEquals("Граждане РФ", mailService.getPopularAddressee());
        assertEquals("Владимир Путин", mailService.getPopularSender());
    }


}

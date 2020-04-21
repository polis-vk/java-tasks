package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.PopularMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 */
public class MailService implements Consumer {

    private Map<String, List> mailBox;
    private PopularMap popMap;

    MailService() {
        this.mailBox = new HashMap<String, List>();
        this.popMap = new PopularMap<String, String>();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(Object o) {
        Mail mail = (Mail) o;
        if (mailBox.containsKey(mail.getReceiver())) {
            mailBox.get(mail.getReceiver()).add(mail);
        } else {
            mailBox.put(mail.getReceiver(), new ArrayList<Mail>());
        }
        popMap.put(mail.getSender(), mail.getReceiver());
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List> getMailBox() {
        return mailBox;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        return (String) popMap.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return (String) popMap.getPopularValue();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List mails) {
        for (Object mail : mails) {
            service.accept(mail);
        }
    }
}

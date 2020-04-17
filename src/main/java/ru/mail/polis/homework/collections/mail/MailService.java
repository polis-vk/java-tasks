package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.PopularMap;
import sun.net.www.content.text.Generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 *
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 */
public class MailService implements Consumer {

    private Map<String, List> map;
    private PopularMap popMap;
    MailService(){
        map = new HashMap<String, List>();
        popMap = new PopularMap<String, String>();
    }
    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(Object o) {
        Mail mail = (Mail) o;
        if(map.containsKey(mail.Receiver)){
            map.get(mail.Receiver).add(mail);
        } else {
            map.put(mail.Receiver, new ArrayList<Mail>());
        }
        popMap.put(mail.Sender,mail.Receiver);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List> getMailBox() {
        return map;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        return (String)popMap.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return (String)popMap.getPopularValue();
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

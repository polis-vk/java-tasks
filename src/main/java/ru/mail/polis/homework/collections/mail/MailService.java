package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.PopularMap;

import java.util.*;
import java.util.function.Consumer;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 *
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 */
public class MailService implements Consumer {

    // получатель -> список писем
    private Map<String, List> mails;

    // отправитель -> получатель
    private PopularMap<String, String> popularMap;

    MailService() {
        mails = new HashMap<>();
        popularMap = new PopularMap<>();
    }
    
    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(Object o) {
        if (o == null) {
            return;
        }
        
        Envelop envelop = (Envelop) o;
        popularMap.put(envelop.getSender(), envelop.getRecipient());
        if (!mails.containsKey(envelop.getRecipient())) {
            mails.put(envelop.getRecipient(), new LinkedList<>(Collections.singleton(envelop)));
        } else {
            mails.get(envelop.getRecipient()).add(envelop);
        }
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List> getMailBox() {
        return mails;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        return popularMap.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return popularMap.getPopularValue();
    }

    /**
     * Метод должен заставить обработать service все recipientMap.
     */
    public static void process(MailService service, List recipientMap) {
        recipientMap.forEach(service);
    }
}

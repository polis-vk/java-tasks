package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.PopularMap;

import java.util.*;
import java.util.function.Consumer;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 */
public class MailService<T extends Mail<T>> implements Consumer<T> {

    private final Map<String, List<T>> mailBox;
    private final PopularMap<String, String> popMap;

    public MailService() {
        this.mailBox = new HashMap<String, List<T>>();
        this.popMap = new PopularMap<String, String>();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T mail) {
        mailBox.compute(mail.getReceiver(), (key, list) -> {
            if (list == null) {
                list = new ArrayList<T>();
            }
            list.add(mail);
            return list;
        });
        popMap.put(mail.getSender(), mail.getReceiver());
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<T>> getMailBox() {
        return mailBox;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        return popMap.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return popMap.getPopularValue();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List<? extends Mail> mails) {
        for (Mail mail : mails) {
            service.accept(mail);
        }
    }
}

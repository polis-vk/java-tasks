package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.PopularMap;

import java.util.ArrayList;
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
public class MailService<U, T extends Mail<U>> implements Consumer<T> {

    private final PopularMap<String, List<T>> recipients;
    private final PopularMap<String, List<T>> senders;

    public MailService() {
        this.recipients = new PopularMap();
        this.senders = new PopularMap();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T sentContent) {
        setNewMail(sentContent, sentContent.getRecipient(), this.recipients);
        setNewMail(sentContent, sentContent.getSender(), this.senders);
    }

    public void setNewMail(T sentContent, String name, PopularMap<String, List<T>> mapTypeUsers) {
        List<T> list = mapTypeUsers.computeIfAbsent(name, key -> new ArrayList());
        list.add(sentContent);
        mapTypeUsers.put(name, list);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<T>> getMailBox() {

        return recipients;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {

        return recipients.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {

        return senders.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static <U,T extends Mail<U>> void process(MailService<U,T> service, List<T> mails) {
        for (T mail : mails) {
            service.accept(mail);
        }
    }
}

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

    private final PopularMap<String, List<T>> recipients;
    private final PopularMap<String, List<T>> senders;

    public MailService() {
        this.recipients = new PopularMap<>();
        this.senders = new PopularMap<>();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T mail) {
        processing(mail, recipients, mail.getRecipient());
        processing(mail, senders, mail.getSender());
    }

    private void processing(T mail, PopularMap<String, List<T>> map, String human) {
        map.compute(human, (key, list) -> {
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(mail);
            return list;
        });
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
    public static void process(MailService service, List<Mail> mails) {
        for (Mail mail : mails) {
            service.accept(mail);
        }
    }
}

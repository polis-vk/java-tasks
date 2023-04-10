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
 * Всего 7 тугриков за пакет mail
 */
public class MailService<E extends Mail> implements Consumer<E> {

    private final Map<String, List<E>> receiverMailBox = new HashMap<>();
    private final PopularMap<String, String> popularReceiverSender = new PopularMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 тугрик
     */
    @Override
    public void accept(E mail) {
        popularReceiverSender.put(mail.getReceiver(), mail.getSender());
        List<E> receiverMails = new ArrayList<>(Collections.singletonList(mail));
        if (receiverMailBox.containsKey(mail.getReceiver())) {
            receiverMailBox.get(mail.getReceiver()).add(mail);
        } else {
            receiverMailBox.put(mail.getReceiver(), receiverMails);
        }
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 тугрик
     */
    public Map<String, List<E>> getMailBox() {
        return receiverMailBox;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 тугрик
     */
    public String getPopularSender() {
        return popularReceiverSender.getPopularValue();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 тугрик
     */
    public String getPopularRecipient() {
        return popularReceiverSender.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 тугрик
     */
    public static void process(MailService<Mail> service, List<? extends Mail> mails) {
        mails.forEach(service);
    }
}

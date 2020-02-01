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
 *
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 */
public class MailService implements Consumer<List<? extends Mail>> {

    private PopularMap<String, List<Mail>> senders;
    private PopularMap<String, List<Mail>> recipients;

    public MailService() {
        senders = new PopularMap<>();
        recipients = new PopularMap<>();
        senders.setCountingType(PopularMap.COUNTING_TYPE_WRITING);
        recipients.setCountingType(PopularMap.COUNTING_TYPE_WRITING);
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(List<? extends Mail> mails) {
        mails.forEach(mail -> {
            senders.computeIfAbsent(mail.getSender(), list -> new ArrayList<>()).add(mail);
            recipients.computeIfAbsent(mail.getRecipient(), list -> new ArrayList<>()).add(mail);
        });
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<Mail>> getMailBox() {
        return recipients;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        return senders.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return recipients.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List<? extends Mail> mails) {
        service.accept(mails);
    }
}

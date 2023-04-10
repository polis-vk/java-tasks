package ru.mail.polis.homework.collections.mail;


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
 * Всего 7 тугриков за пакет mail
 */
public class MailService<T extends Mail> implements Consumer<T> {
    private final Map<String, List<T>> mailBox = new HashMap<>();
    private final Map<String, Integer> senderPopularity = new HashMap<>();
    private final Map<String, Integer> recipientPopularity = new HashMap<>();
    private String mostPopularSender;
    private String mostPopularRecipient;

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 тугрик
     */
    @Override
    public void accept(T o) {
        if (o != null) {
            mailBox.getOrDefault(o.getRecipient(), new ArrayList<>()).add((T) o.getMessage());
            increaseRecipientPopularity(o.getRecipient());
            increaseSenderPopularity(o.getSender());
        }
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 тугрик
     */
    public Map<String, List<T>> getMailBox() {
        return mailBox;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 тугрик
     */
    public String getPopularSender() {
        return mostPopularSender;
    }

    /**
     * Возвращает самого популярного получателя
     * 1 тугрик
     */
    public String getPopularRecipient() {
        return mostPopularRecipient;
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 тугрик
     */
    public static <T extends Mail> void process(MailService service, List<T> mails) {
        for (T x : mails) {
            if (x == null) {
                return;
            }
            service.accept((T) mails);
        }
    }

    private void increaseSenderPopularity(String sender) {
        int popularity = senderPopularity.getOrDefault(sender, 1);
        senderPopularity.put(sender, senderPopularity.getOrDefault(sender, 1));
        if (mostPopularSender == null || popularity > senderPopularity.get(mostPopularSender)) {
            mostPopularSender = sender;
        }
    }

    private void increaseRecipientPopularity(String recipient) {
        int popularity = recipientPopularity.getOrDefault(recipient, 1);
        recipientPopularity.put(recipient, recipientPopularity.getOrDefault(recipient, 1));
        if (mostPopularRecipient == null || popularity > recipientPopularity.get(mostPopularRecipient)) {
            mostPopularRecipient = recipient;
        }
    }

}

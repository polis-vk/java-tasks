package ru.mail.polis.homework.collections.mail;


import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 * Всего 7 тугриков за пакет mail
 */
public class MailService<M extends Mail> implements Consumer<M> {
    private final Map<String, List<M>> mailBox = new HashMap<>();
    private final Map<String, Integer> sendersByPopularity = new HashMap<>();
    private final Map<String, Integer> recipientsByPopularity = new HashMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 тугрик
     */
    @Override
    public void accept(M mail) {
        sendersByPopularity.merge(mail.getSender(), 1, Integer::sum);
        recipientsByPopularity.merge(mail.getRecipient(), 1, Integer::sum);

        List<M> newMail = new ArrayList<>();
        newMail.add(mail);
        mailBox.merge(mail.getRecipient(), newMail, (currentList, additionalPart) -> Stream.concat(currentList.stream(), additionalPart.stream())
                .collect(Collectors.toList()));
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 тугрик
     */
    public Map<String, List<M>> getMailBox() {
        return mailBox;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 тугрик
     */
    public String getPopularSender() {
        return sendersByPopularity.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .findFirst().orElseGet(null);
    }

    /**
     * Возвращает самого популярного получателя
     * 1 тугрик
     */
    public String getPopularRecipient() {
        return recipientsByPopularity.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .findFirst().orElseGet(null);
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 тугрик
     */
    public static void process(MailService service, List<? extends Mail> mails) {
        mails.forEach(service);
    }
}

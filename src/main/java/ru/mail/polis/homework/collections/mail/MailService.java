package ru.mail.polis.homework.collections.mail;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 * Всего 7 тугриков за пакет mail
 */
public class MailService implements Consumer<Mail> {
    Map<String, List<Mail>> mails = new HashMap<>();
    Map<String, Integer> popularSenders = new HashMap<>();
    Map<String, Integer> popularRecipients = new HashMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 тугрик
     */
    @Override
    public void accept(Mail mail) {
        String recipient = mail.getRecipient();
        List<Mail> data = mails.get(recipient);
        raisePopular(popularRecipients, recipient);
        raisePopular(popularSenders, mail.getSender());
        if (data == null) {
            List<Mail> newList = new ArrayList<>();
            newList.add(mail);
            mails.put(recipient, newList);
            return;
        }
        mails.get(recipient).add(mail);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 тугрик
     *
     * @return
     */
    public Map<String, List<Mail>> getMailBox() {
        return mails;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 тугрик
     */
    public String getPopularSender() {
        return getPopular(popularSenders);
    }

    /**
     * Возвращает самого популярного получателя
     * 1 тугрик
     */
    public String getPopularRecipient() {
        return getPopular(popularRecipients);
    }

    public String getPopular(Map<String, Integer> popularMap) {
        return popularMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toList()).get(0).getKey();
    }

    private void raisePopular(Map<String, Integer> popularMap, String key) {
        if (popularMap.containsKey(key)) {
            popularMap.put(key, popularMap.get(key) + 1);
        } else {
            popularMap.put(key, 1);
        }
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 тугрик
     */
    public static void process(MailService service, List<Mail> mails) {
        for (Mail mail : mails) {
            service.accept(mail);
        }
    }
}

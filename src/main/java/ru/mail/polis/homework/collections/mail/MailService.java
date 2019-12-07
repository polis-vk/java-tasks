package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.PopularMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 *
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 */
public class MailService implements Consumer<Mail> {

    private PopularMap<String, List<Mail>> mailBox = new PopularMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(Mail mail) {
        mailBox.compute(mail.getReceiver(), (key, value) -> {
            List<Mail> list = mailBox.getOrDefault(key, new ArrayList<>());
            list.add(mail);
            return list;
        });
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<Mail>> getMailBox() {
        return mailBox;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        final String[] result = {null};

        mailBox.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream())
                .collect(Collectors.groupingBy(Mail::getSender, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry<String, Long>::getValue))
                .ifPresent(entry -> result[0] = entry.getKey());

        return result[0];
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return mailBox.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List<Mail> mails) {
        mails.forEach(service);
    }
}
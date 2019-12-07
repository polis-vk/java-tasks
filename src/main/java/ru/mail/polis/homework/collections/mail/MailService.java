package ru.mail.polis.homework.collections.mail;

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
    Map<String, List<Mail>> mailBox;
    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(Mail mail) {
        mailBox.compute(mail.getRecipient(), (key, value) -> {
            List<Mail> itemOfmailBox = mailBox.get(key);
            itemOfmailBox.add(mail);
            return itemOfmailBox;
        });
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * @return
     */
    public Map<String, List<Mail>> getMailBox() {
        return mailBox;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        return mailBox.entrySet().stream()
                .flatMap(item -> item.getValue().stream())
                .collect(Collectors.groupingBy(Mail::getSender, Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .get()
                .getKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return mailBox.entrySet().stream()
                .flatMap(item -> item.getValue().stream())
                .collect(Collectors.groupingBy(Mail::getRecipient, Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .get()
                .getKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List mails) {
        mails.forEach(service);
    }
}

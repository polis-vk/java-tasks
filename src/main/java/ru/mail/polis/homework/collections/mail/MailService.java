package ru.mail.polis.homework.collections.mail;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 */
public class MailService implements Consumer<Mail> {

    /**
     * Map receiver -> List<Sendable>
     */
    private Map<String, List<Mail>> mails;

    public MailService() {
        mails = new HashMap<>();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(Mail mail) {
        mails.compute(
            mail.getReceiver(),
            (receiver, mailList) -> {
                if (mailList == null) {
                    mailList = new ArrayList<>();
                }
                mailList.add(mail);
                return mailList;
            }
        );
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<Mail>> getMailBox() {
        return mails;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        Set<Map.Entry<String, List<Mail>>> entries = mails.entrySet();
        return entries
            .stream()
            .map(entry ->
                Map.entry(
                    entries
                        .stream()
                        .filter(entry1 -> entry1.getKey().equals(entry.getKey()))
                        .count(),
                    entry.getKey()
                )
            )
            .max(Comparator.comparingLong(Map.Entry::getKey))
            .orElseThrow(() -> {
                throw new ArrayIndexOutOfBoundsException("No mails were added");
            })
            .getValue();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return mails
            .entrySet()
            .stream()
            .max(Comparator.comparingInt((entry -> entry.getValue().size())))
            .orElseThrow(() -> {
                throw new ArrayIndexOutOfBoundsException("No mails were added");
            })
            .getKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List<Mail> mails) {
        mails.forEach(service);
    }
}

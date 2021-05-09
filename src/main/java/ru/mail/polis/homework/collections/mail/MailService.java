package ru.mail.polis.homework.collections.mail;


import java.util.ArrayList;
import java.util.Comparator;
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
 * Всего 7 баллов за пакет mail
 */
public class MailService implements Consumer<Mail> {
    private Map<String, List<Mail>> recipients;
    private Map<String, List<Mail>> senders;

    public MailService() {
        this.recipients = new HashMap<>();
        this.senders = new HashMap<>();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(Mail mail) {
        recipients.computeIfAbsent(mail.getRecipient(), x -> new ArrayList<>()).add(mail);
        senders.computeIfAbsent(mail.getSender(), x -> new ArrayList<>()).add(mail);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 балл
     */
    public Map<String, List<Mail>> getMailBox() {
        return recipients;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 балл
     */
    public String getPopularSender() {
        return senders
                .entrySet()
                .stream()
                .max(Comparator.comparing(x -> x.getValue().size()))
                .get()
                .getKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 балл
     */
    public String getPopularRecipient() {
        return recipients
                .entrySet()
                .stream()
                .max(Comparator.comparing(x -> x.getValue().size()))
                .get()
                .getKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 балл
     */
    public static void process(MailService service, List<Mail> mails) {
        mails.forEach(service);
    }
}

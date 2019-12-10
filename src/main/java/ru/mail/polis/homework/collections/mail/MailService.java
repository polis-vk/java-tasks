package ru.mail.polis.homework.collections.mail;


import java.util.ArrayList;
import java.util.Comparator;
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
public class MailService implements Consumer<Mail> {

    private Map<String, List<Mail>> receivers;
    private Map<String, List<Mail>> senders;

    public MailService(Map<String, List<Mail>> receivers, Map<String, List<Mail>> senders) {
        this.receivers = receivers;
        this.senders = senders;
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(Mail mail) {
        senders.computeIfAbsent(mail.getSender(), it -> new ArrayList<>()).add(mail);
        receivers.computeIfAbsent(mail.getReceiver(), it -> new ArrayList<>()).add(mail);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<Mail>> getMailBox() {
        return receivers;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        return senders
                .entrySet()
                .stream()
                .max(Comparator.comparing(entry -> entry.getValue().size()))
                .get()
                .getKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return receivers
                .entrySet()
                .stream()
                .max(Comparator.comparing(entry -> entry.getValue().size()))
                .get()
                .getKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List<Mail> mails) {
        mails.forEach(service);
    }
}

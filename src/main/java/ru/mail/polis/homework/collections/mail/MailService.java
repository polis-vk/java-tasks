package ru.mail.polis.homework.collections.mail;


import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 */
public class MailService implements Consumer<Mail> {

    private final Map<String, List<Mail>> mailBox = new HashMap<>();
    private final Map<String, List<Mail>> mailSend = new HashMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(Mail o) {
        mailBox.computeIfAbsent(o.getRecipient(), k -> new ArrayList<>(1)).add(o);
        mailSend.computeIfAbsent(o.getSender(), k -> new ArrayList<>(1)).add(o);
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
        if (mailSend.isEmpty())
            return null;
        return mailSend.entrySet()
                .stream()
                .max(Comparator.comparing(entry -> entry.getValue().size()))
                .get()
                .getKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        if (mailBox.isEmpty())
            return null;
        return mailBox.entrySet()
                .stream()
                .max(Comparator.comparingLong(entry -> entry.getValue().size()))
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

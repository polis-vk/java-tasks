package ru.mail.polis.homework.collections.mail;

import java.util.*;
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

    private Map<String, List<Mail>> mail = new HashMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(Mail o) {
        mail.computeIfAbsent(o.getReceiver(), (key) -> new ArrayList<>()).add(o);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<Mail>> getMailBox() {
        return mail;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        return mail
                .entrySet()
                .stream()
                .flatMap(x -> x
                        .getValue()
                        .stream())
                .collect(Collectors.groupingBy(Mail::getSender, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry<String, Long>::getValue))
                .get()
                .getKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularReceiver() {
        return mail
                .entrySet()
                .stream()
                .flatMap(x -> x
                        .getValue()
                        .stream())
                .collect(Collectors.groupingBy(Mail::getReceiver, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry<String, Long>::getValue))
                .get()
                .getKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List mails) {
        mails.forEach(service);
    }

    @Override
    public Consumer<Mail> andThen(Consumer<? super Mail> after) {
        return null;
    }
}

package ru.mail.polis.homework.collections.mail;


import org.graalvm.compiler.api.replacements.Snippet;

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

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(Mail o) {
        List<Mail> list = mailBox.remove(o.getRecipient());
        if (list == null) {
            List<Mail> temp = new ArrayList<>(1);
            temp.add(o);
            mailBox.put(o.getRecipient(), temp);
        } else {
            list.add(o);
            mailBox.put(o.getRecipient(), list);
        }
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
        if (mailBox.isEmpty())
            return null;
        return mailBox.entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().stream().map(Mail::getSender))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
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
                .max(Comparator.comparingLong(a -> a.getValue().size()))
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

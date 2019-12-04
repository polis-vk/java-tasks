package ru.mail.polis.homework.collections.mail;


import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения.
 * Зарплата состоит из получателя, отправителя и суммы.
 *
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 */
public class MailService implements Consumer<MailWorker> {

    private Map<String, List<MailWorker>> mailWorker = new HashMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(MailWorker obj) {
        // Если нам необходимо произвести какое-то действие со значением в мапе,
        // если оно там есть (2 params expected in lambda):
        mailWorker.computeIfPresent(obj.getListener(), (K, V) -> {
            List<MailWorker> array = mailWorker.get(K);
            array.add(obj);
            return array;
        });
        // Действие произойдет в том случае, если значения нет:
        mailWorker.computeIfAbsent(obj.getListener(), K -> {
            List<MailWorker> array = new Vector<>();
            array.add(obj);
            return array;
        });
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<MailWorker>> getMailBox() {
        return mailWorker;
    }

    private boolean isEmpty() {
        return mailWorker.isEmpty();
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        if (!isEmpty()) {
            return mailWorker.entrySet().stream()
                    .flatMap(e -> e.getValue().stream())
                    .collect(Collectors.groupingBy(MailWorker::getTalker, Collectors.counting()))
                    .entrySet().stream()
                    .max(Comparator.comparingLong(Map.Entry<String, Long>::getValue))
                    .get()
                    .getKey();
        }
        else {
            return null;
        }
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        if (!isEmpty()) {
            return mailWorker.entrySet().stream()
                    .flatMap(e -> e.getValue().stream())
                    .collect(Collectors.groupingBy(MailWorker::getListener, Collectors.counting()))
                    .entrySet().stream()
                    .max(Comparator.comparingLong(Map.Entry<String, Long>::getValue))
                    .get()
                    .getKey();
        }
        else {
            return null;
        }
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List mails) {
        mails.forEach(service);
    }
}

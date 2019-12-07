package ru.mail.polis.homework.collections.mail;


import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения.
 * Зарплата состоит из получателя, отправителя и суммы.
 *
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 *
 * modified by БорискинМА
 * 04.12.19
 */
public class MailService implements Consumer<MailWorker> {

    private Map<String, List<MailWorker>> mailWorker = new HashMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(MailWorker obj) {
        // Проверяем наличие ключа в мапе. Если ключ есть и значение по ключу не равно null, то ничего не делаем.
        // Иначе (нет ключа или значение по ключу равно null) считаем значение, применяя лямбда-выражение к key.
        // Если итоговое значение не равно null, то записываем пару ключ-значение в map:
        mailWorker.computeIfAbsent(obj.getListener(), (key) -> {
            List<MailWorker> array = new ArrayList<>();
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
        if (isEmpty()) {
            return null;
        }

        return mailWorker.entrySet()
                .stream()
                .flatMap(e -> e.getValue().stream())
                .collect(Collectors.groupingBy(MailWorker::getTalker, Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry<String, Long>::getValue))
                .get()
                .getKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        if (isEmpty()) {
            return null;
        }
        return mailWorker.entrySet()
                .stream()
                .flatMap(e -> e.getValue().stream())
                .collect(Collectors.groupingBy(MailWorker::getListener, Collectors.counting()))
                .entrySet().stream()
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
}

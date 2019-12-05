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
        // Метод добавит новый элемент в Map, но только в том случае, если элемент с таким ключом там отсутствует.
        // В качестве value ему будет присвоен результат выполнения лямбда-выражения.
        // Если же элемент с таким ключом уже есть — он не будет перезаписан, а останется на месте.
        // Для указанного ключа key этот метод устанавливает в качестве value результат выполнения лямбда-выражения.
        mailWorker.compute(obj.getListener(), (key, value) -> {
            List<MailWorker> array = mailWorker.getOrDefault(key, new ArrayList<>());
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
            return mailWorker.entrySet()
                    .stream()
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
            return mailWorker.entrySet()
                    .stream()
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

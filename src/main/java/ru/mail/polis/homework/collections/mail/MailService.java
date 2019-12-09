package ru.mail.polis.homework.collections.mail;


import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 *
 * В реализации нигде не должно быть классов Object и коллекций без типа.используйте дженерики.
 */
public class MailService<T extends Message> implements Consumer<T> {

    private List<T> list;

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    MailService() {
        list = new ArrayList<>();
    }

    @Override
    public void accept(T message) {
       list.add(message);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<T>> getMailBox() {
        return list.stream().collect(Collectors.toMap(T::getRec, man -> {
            List<T> l = new ArrayList<>();
            l.add(man);
            return l;
        }, (messages, messages2) -> {
            messages.addAll(messages2);
            return messages;
        }));
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        Map<String, Integer> map = new HashMap<>();
        list.forEach(t -> {
            Integer times = map.get(t.getSen());
            if  (times == null) {
                map.put(t.getSen(), 1);
            } else {
                map.put(t.getSen(), ++times);
            }
        });
        return Collections.max(map.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        Map<String, Integer> map = new HashMap<>();
        list.forEach(t -> {
            Integer times = map.get(t.getRec());
            if  (times == null) {
                map.put(t.getRec(), 1);
            } else {
                map.put(t.getRec(), ++times);
            }
        });
        return Collections.max(map.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List<Message> mails) {
        mails.forEach(service);
    }
}

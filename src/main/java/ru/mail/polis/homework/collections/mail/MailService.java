package ru.mail.polis.homework.collections.mail;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 * Всего 7 баллов за пакет mail
 */
public class MailService<T extends Incoming<?>> implements Consumer<T> {
    private final Map<String, List<T>> receiverMap = new HashMap<>();
    private final Map<String, Integer> senderMap = new HashMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T o) {
        int count = senderMap.getOrDefault(o.getSender(), 0);
        senderMap.put(o.getSender(), ++count);
        List<T> incomingMessage = receiverMap.computeIfAbsent(o.getReceiver(), k -> new ArrayList<>());
        incomingMessage.add(o);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 балл
     */
    public Map<String, List<T>> getMailBox() {
        return receiverMap;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 балл
     */
    public String getPopularSender() {
        return senderMap.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow()
                .getKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 балл
     */
    public String getPopularRecipient() {
        Map<String, Integer> numberOfReceiversMessage = receiverMap.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, o -> o.getValue().size()));
        return numberOfReceiversMessage.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow()
                .getKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 балл
     */
    public static <T extends Incoming<?>> void process(MailService<T> service, List<T> mails) {
        mails.forEach(service);
    }
}

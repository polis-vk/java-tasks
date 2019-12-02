package ru.mail.polis.homework.collections.mail;


import java.util.*;
import java.util.function.Consumer;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 */
public class MailService<T extends Mail<?>> implements Consumer<T> {
    private Map<String, List<T>> senders;
    private Map<String, List<T>> receivers;

    public MailService() {
        this.senders = new HashMap<>();
        this.receivers = new HashMap<>();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T mail) {
        String sender = mail.getSender();
        String receiver = mail.getReceiver();
        if (senders.containsKey(sender)) {
            senders.put(sender, List.of(mail));
        } else senders.get(sender).add(mail);
        if (receivers.containsKey(receiver)) {
            receivers.put(receiver, List.of(mail));
        } else receivers.get(receiver).add(mail);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<T>> getMailBox() {
        return senders;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        return senders
                .entrySet()
                .stream()
                .max(Comparator.comparingInt(e -> e.getValue().size()))
                .orElseThrow(NullPointerException::new)
                .getKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return receivers
                .entrySet()
                .stream()
                .max(Comparator.comparingInt(e -> e.getValue().size()))
                .orElseThrow(NullPointerException::new)
                .getKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService<Mail<?>> service, List<Mail<?>> mails) {
        for (Mail<?> mail : mails) {
            service.accept(mail);
        }
    }
}

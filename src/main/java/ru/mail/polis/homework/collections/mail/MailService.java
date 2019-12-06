package ru.mail.polis.homework.collections.mail;


import java.util.Comparator;
import java.util.HashMap;
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
public class MailService<M extends Mail> implements Consumer<M> {
    private Map<String, List<M>> receivers;
    private Map<String, List<M>> senders;


    public MailService() {
        this.senders = new HashMap<>();
        this.receivers = new HashMap<>();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(M mail) {
        String sender = mail.getSender();
        String receiver = mail.getReceiver();

        senders.computeIfAbsent(sender, (key) ->  List.of(mail));
        senders.computeIfPresent(sender, (key, value) -> {
            value = senders.get(key);
            value.add(mail);
            return value;
        });

        receivers.computeIfAbsent(receiver, (k) -> List.of(mail));
        receivers.computeIfPresent(receiver, (key, value) -> {
            value = receivers.get(key);
            value.add(mail);
            return value;
        });
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<M>> getMailBox() {
        return receivers;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        return senders
                .entrySet()
                .stream()
                .max(Comparator.comparing(receiver -> receiver.getValue().size()))
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
                .max(Comparator.comparing(sender -> sender.getValue().size()))
                .get()
                .getKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService<Mail> service, List<Mail> mails) {
        mails.forEach(service);
    }

}

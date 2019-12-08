package ru.mail.polis.homework.collections.mail;


import java.util.*;
import java.util.function.Consumer;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 *
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 */
public class MailService implements Consumer<Envelop> {

    // получатель -> список писем
    private Map<Client, List<Envelop>> mails;

    private Client mostPopularReceiver;
    private Client mostPopularSender;

    MailService() {
        mails = new HashMap<>();
        mostPopularReceiver = new Client();
        mostPopularSender = new Client();
    }
    
    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(Envelop envelop) {
        if (envelop == null) {
            return;
        }
        envelop.getSender().incrementMailsSent();
        envelop.getRecipient().incrementMailsCount();

        mails.computeIfAbsent(envelop.getRecipient(), recipient -> new ArrayList<>()).add(envelop);

        if (envelop.getRecipient().getMailsCount() > mostPopularReceiver.getMailsCount()) {
            mostPopularReceiver = envelop.getRecipient();
        }

        if (envelop.getSender().getMailsSent() > mostPopularSender.getMailsSent()) {
            mostPopularSender = envelop.getSender();
        }
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<Client, List<Envelop>> getMailBox() {
        return mails;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public Client getPopularSender() {
        return mostPopularSender;
    }

    /**
     * Возвращает самого популярного получателя
     */
    public Client getPopularRecipient() {
        return mostPopularReceiver;
    }

    /**
     * Метод должен заставить обработать service все recipientMap.
     */
    public static void process(MailService service, List<Envelop> recipientMap) {
        recipientMap.forEach(service);
    }
}

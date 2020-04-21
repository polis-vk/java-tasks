package ru.mail.polis.homework.collections.mail;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 */
public class MailService implements Consumer<Mail> {
    private final Map<String, ArrayList<Mail>> recipientMail = new HashMap<>();
    private final Map<String, Integer> senderMail = new HashMap<>();

    private String topRecipient; //за то без циклов...
    private String topSender;

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(Mail mail) {
        if (recipientMail.containsKey(mail.getRecipient())) {
            recipientMail.get(mail.getRecipient()).add(mail);
        } else {
            ArrayList<Mail> mails = new ArrayList<>();
            mails.add(mail);
            recipientMail.put(mail.getRecipient(), mails);
        }
        if (topRecipient == null) {
            topRecipient = mail.getRecipient();
        } else if (recipientMail.get(mail.getRecipient()).size() > recipientMail.get(topRecipient).size()) {
            topRecipient = mail.getRecipient();
        }
        if (senderMail.containsKey(mail.getSender())) {
            senderMail.put(mail.getSender(), senderMail.get(mail.getSender()) + 1);
        } else {
            senderMail.put(mail.getSender(), 1);
        }
        if (topSender == null) {
            topSender = mail.getSender();
        } else if (senderMail.get(mail.getSender()) > senderMail.get(topSender)) {
            topSender = mail.getSender();
        }
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, ArrayList<Mail>> getMailBox() {
        return recipientMail;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        return topSender;
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return topRecipient;
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List<Mail> mails) {
        for (Mail m : mails) {
            service.accept(m);
        }
    }
}

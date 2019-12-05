package ru.mail.polis.homework.collections.mail;

import ru.mail.polis.homework.collections.PopularMap;
import ru.mail.polis.homework.collections.mail.layout.MailLayout;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public class MailService<T extends MailLayout> implements Consumer<T> {
    private PopularMap<String, List<MailLayout>> senders = new PopularMap<>();
    private PopularMap<String, List<MailLayout>> recipient = new PopularMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */

    public void accept(T o) {
        senders.computeIfAbsent(o.getSender(), k -> new LinkedList<>()).add(o);
        recipient.computeIfAbsent(o.getRecipient(), k -> new LinkedList<>()).add(o);
    }
    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<MailLayout>> getMailBox() {
        return recipient;
    }

        public List<MailLayout> getMailBoxFromRecipient(String recipient) {
            return this.recipient.get(recipient);
        }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        return senders.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return recipient.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List<MailLayout> mails) {
        mails.forEach(service);
    }

}
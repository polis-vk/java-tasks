package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.PopularMap;

import java.util.ArrayList;
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
public class MailService<T extends Message> implements Consumer<T> {

    public final PopularMap<String,List<T>> recipients;
    public final PopularMap<String,List<T>> senders;

    public MailService(){
        this.recipients=new PopularMap<>();
        this.senders=new PopularMap<>();
    }
    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T mail) {
        processClient(mail,mail.getRecipient(),recipients);
        processClient(mail,mail.getSender(),senders);
    }

    private void processClient(T mail, String client, PopularMap<String, List<T>> clients) {
        List<T> mails =clients.getOrDefault(client, new ArrayList<>());
        mails.add(mail);
        clients.put(client,mails);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<T>> getMailBox() {
        return recipients;
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
        return recipients.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List<? extends Message> mails) {
        for (Message mail:mails) {
            service.accept(mail);
        }
    }
}

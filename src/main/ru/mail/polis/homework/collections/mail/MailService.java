package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.PopularMap;

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
public class MailService<T extends AbstractMailMessage> implements Consumer<T> {
    private PopularMap<String, List<AbstractMailMessage>> senders;
    private PopularMap<String, List<AbstractMailMessage>> recievers;

    MailService(){
        senders = new PopularMap<>();
        recievers = new PopularMap<>();
    }
    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */

    public void accept(T o) {
        List<AbstractMailMessage> lstSend = senders.get(o.getSender());
        lstSend.add((T) o);
        senders.put(o.getSender(), lstSend);
        List<AbstractMailMessage> lstRec = recievers.get(o.getSender());
        lstRec.add((T) o);
        recievers.put(o.getSender(), lstRec);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<AbstractMailMessage>> getMailBox() {
        return recievers;
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
        return recievers.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List<AbstractMailMessage> mails) {
        for (AbstractMailMessage mail:mails) {
            service.accept(mail);
        }
    }

}

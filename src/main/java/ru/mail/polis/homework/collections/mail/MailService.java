package ru.mail.polis.homework.collections.mail;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 * Всего 7 баллов за пакет mail
 */
public class MailService implements Consumer {
    private Map<String, List<Notification>> mailBox;
    private Map<String, Integer> sendersPopularity;
    private Map<String, Integer> recipientsPopularity;
    private String mostPopularSender;
    private String mostPopularRecipient;

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(Object o) {
        if (o instanceof Notification) {
            RefreshPopularity((Notification) o);
            mailBox.compute(((Notification<?>) o).getRecipient(), (k, v) -> {
                if (v == null) {
                    return new ArrayList<Notification>();
                } else {
                    List<Notification> temp = mailBox.get(k);
                    temp.add((Notification) o);
                    return temp;
                }
            });
        }
    }


    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 балл
     */
    public Map<String, List<Notification>> getMailBox() {
        return mailBox;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 балл
     */
    public String getPopularSender() {
        return mostPopularSender;
    }

    /**
     * Возвращает самого популярного получателя
     * 1 балл
     */
    public String getPopularRecipient() {
        return mostPopularRecipient;
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 балл
     */
    public static void process(MailService service, List<Notification> mails) {
        for (Notification mail : mails) {
            service.accept(mail);
        }
    }

    private void RefreshPopularity(Notification o) {
        Integer newPopularity = sendersPopularity.compute(o.getSender(), (k, v) -> (v == null) ? 1 : v + 1);
        if (sendersPopularity == null || newPopularity > sendersPopularity.get(mostPopularSender)) {
            mostPopularSender = o.getSender();
        }
        newPopularity = recipientsPopularity.compute(o.getRecipient(), (k, v) -> (v == null) ? 1 : v + 1);
        if (recipientsPopularity == null || newPopularity > recipientsPopularity.get(mostPopularRecipient)) {
            mostPopularRecipient = o.getRecipient();
        }
    }
}

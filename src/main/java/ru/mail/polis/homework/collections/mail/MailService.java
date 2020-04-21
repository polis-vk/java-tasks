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
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 */
public class MailService<T extends Correspondency> implements Consumer<T> {

    private final Map<String, List<T>> receivedMail = new HashMap<>();
    private final Map<String, List<T>> sentMail = new HashMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T mailOrSalary) {
        List<T> correspondencies = receivedMail.get(mailOrSalary.getSender());
        correspondencies.add(mailOrSalary);
        receivedMail.put(mailOrSalary.getSender(), correspondencies);

        correspondencies = sentMail.get(mailOrSalary.getReceiver());
        correspondencies.add(mailOrSalary);
        sentMail.put(mailOrSalary.getReceiver(), correspondencies);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<T>> getMailBox() {
        return receivedMail;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        return getPopular(receivedMail);
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return getPopular(sentMail);
    }

    private String getPopular(Map<String, List<T>> mails) {
        String topUser = null;
        for (String s : mails.keySet()) {
            if (topUser == null) {
                topUser = s;
                continue;
            }
            if (mails.get(s).size() > mails.get(topUser).size()) {
                topUser = s;
            }
        }
        return topUser;
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService<Correspondency> service, List<Correspondency> mails) {
        for (Correspondency mail : mails) {
            service.accept(mail);
        }
    }
}

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
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 */
public class MailService<T extends Correspondency<?>> implements Consumer<T> {

    private final PopularMap<String, List<T>> receivedMail = new PopularMap<>();
    private final PopularMap<String, List<T>> sentMail = new PopularMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T mailOrSalary) {
        List<T> correspondencies = receivedMail.getOrDefault(mailOrSalary.getSender(), new ArrayList<>());
        correspondencies.add(mailOrSalary);
        receivedMail.put(mailOrSalary.getSender(), correspondencies);

        correspondencies = sentMail.getOrDefault(mailOrSalary.getReceiver(), new ArrayList<>());
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
        return receivedMail.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return sentMail.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService<Correspondency<?>> service, List<Correspondency<?>> mails) {
        for (Correspondency<?> mailOrSalary : mails) {
            service.accept(mailOrSalary);
        }
    }
}

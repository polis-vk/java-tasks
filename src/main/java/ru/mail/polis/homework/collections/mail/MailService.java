package ru.mail.polis.homework.collections.mail;


import ru.mail.polis.homework.collections.PopularMap;

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
 * Всего 7 баллов за пакет mail
 */
public class MailService implements Consumer<BaseMail<?>> {
    private final Map<String, List<BaseMail<?>>> mailBox = new HashMap<>();
    private final PopularMap<String, Object> sendersPopularity = new PopularMap<>();
    private final PopularMap<String, Object> recipientsPopularity = new PopularMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(BaseMail<?> mail) {
        String recipient = mail.getRecipient();
        sendersPopularity.get(mail.getSender());
        recipientsPopularity.get(recipient);
        List<BaseMail<?>> recipientMails = mailBox.computeIfAbsent(recipient, (key) -> new ArrayList<>());
        recipientMails.add(mail);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 балл
     */
    public Map<String, List<BaseMail<?>>> getMailBox() {
        return mailBox;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 балл
     */
    public String getPopularSender() {
        return sendersPopularity.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 балл
     */
    public String getPopularRecipient() {
        return recipientsPopularity.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 балл
     */
    public static void process(MailService service, List<? extends BaseMail<?>> mails) {
        mails.forEach(mail -> service.accept(mail));
    }
}

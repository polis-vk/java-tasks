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
 * <p>
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 */
public class MailService<T extends Mail<?>> implements Consumer<T> {
    private Map<String, List<T>> senders;
    private Map<String, List<T>> addressees;

    public MailService() {
        this.senders = new HashMap<>();
        this.addressees = new HashMap<>();
    }

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(T mail) {
        senders.computeIfAbsent(mail.getSender(), key -> List.of(mail));
        addressees.computeIfAbsent(mail.getAddressee(), key -> List.of(mail));
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<T>> getMailBox() {
        return senders;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        return senders.entrySet()
                .stream()
                .max(Comparator.comparingInt(
                        e -> e.getValue().size()))
                .orElseThrow(NullPointerException::new)
                .getKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return addressees.entrySet()
                .stream()
                .max(Comparator.comparingInt(
                        e -> e.getValue().size()))
                .orElseThrow(NullPointerException::new)
                .getKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService<Mail<?>> service, List<Mail<?>> mails) {
        mails.forEach(service);
    }
}

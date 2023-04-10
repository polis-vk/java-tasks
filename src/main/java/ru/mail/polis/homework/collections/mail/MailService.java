package ru.mail.polis.homework.collections.mail;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import ru.mail.polis.homework.collections.PopularMap;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату.
 * Письма состоят из получателя, отправителя, текста сообщения
 * Зарплата состоит из получателя, отправителя и суммы.
 *
 * В реализации нигде не должно быть классов Object и коллекций без типа. Используйте дженерики.
 * Всего 7 тугриков за пакет mail
 */
public class MailService <V extends Mail<?>> implements Consumer<V> {
    private final PopularMap<String, String> mapMail = new PopularMap<>();
    private final Map<String, List<V>> mailsToReceiver = new HashMap<>();
    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 тугрик
     */
    @Override
    public void accept(V addresses) {
        if(addresses == null) {
            return;
        }
        if (addresses.getReceiverName().isEmpty() || addresses.getSenderName().isEmpty()) {
            return;
        }
        this.mapMail.put(addresses.getSenderName(), addresses.getReceiverName());
        this.mailsToReceiver.putIfAbsent(addresses.getReceiverName(), new ArrayList<>());
        this.mailsToReceiver.get(addresses.getReceiverName()).add(addresses);
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     * 1 тугрик
     */
    public Map<String, List<V>> getMailBox() {
        return mailsToReceiver;
    }

    /**
     * Возвращает самого популярного отправителя
     * 1 тугрик
     */
    public String getPopularSender() {
        return mapMail.getPopularValue();
    }

    /**
     * Возвращает самого популярного получателя
     * 1 тугрик
     */
    public String getPopularRecipient() {
        return mapMail.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     * 1 тугрик
     */
    public static void process(MailService service, List mails) {
    }
}

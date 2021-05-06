package ru.mail.polis.homework.collections.mail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import ru.mail.polis.homework.collections.PopularMap;

/**
 * Нужно создать сервис, который умеет обрабатывать письма и зарплату. Письма
 * состоят из получателя, отправителя, текста сообщения Зарплата состоит из
 * получателя, отправителя и суммы.
 *
 * В реализации нигде не должно быть классов Object и коллекций без типа.
 * Используйте дженерики. Всего 7 баллов за пакет mail
 * 
 *
 */
public class MailService<I extends CommonMessage<I>> implements Consumer<I> {
    private PopularMap<String, Integer> popularSender = new PopularMap<String, Integer>();
    private PopularMap<String, Integer> popularReceiver = new PopularMap<String, Integer>();
    private HashMap<String, List<I>> whatReceived = new HashMap<String, List<I>>();
    private List<I> tempList;

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты 1 балл
     */
    @Override
    public void accept(I Inf) {
        popularReceiver.KeyValueCheck(Inf.getTo());
        popularSender.KeyValueCheck(Inf.getFrom());
        if (whatReceived.containsKey(Inf.getTo())) {
            tempList.addAll(whatReceived.get(Inf.getTo()));
        }
        tempList.add(Inf.getInformation());
        whatReceived.put(Inf.getTo(), tempList);
        tempList.clear();
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому
     * получателю через данный почтовый сервис 1 балл
     */
    public Map<String, List<I>> getMailBox() {
        return whatReceived;
    }

    /**
     * Возвращает самого популярного отправителя 1 балл
     */
    public String getPopularSender() {
        return popularSender.getPopularKey();
    }

    /**
     * Возвращает самого популярного получателя 1 балл
     */
    public String getPopularRecipient() {
        return popularSender.getPopularKey();
    }

    /**
     * Метод должен заставить обработать service все mails. 1 балл
     */
    public static <I extends CommonMessage<I>> void process(MailService<I> service, List<I> mails) {
        for (I message : mails) {
            service.accept(message);
        }
    }
}

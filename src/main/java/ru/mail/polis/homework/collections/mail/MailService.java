package ru.mail.polis.homework.collections.mail;


import java.util.ArrayList;
import java.util.Comparator;
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
public class MailService implements Consumer<Mail> {

    private Map<String, Integer> sendersPopularityMap = new HashMap<>();
    private Map<String, Integer> recipientPopularityMap = new HashMap<>();
    private Map<String, List<Mail>> mailBox = new HashMap<>();

    /**
     * С помощью этого метода почтовый сервис обрабатывает письма и зарплаты
     * 1 балл
     */
    @Override
    public void accept(Mail mail) {
        if (!mailBox.containsKey(mail.getRecipient())) {
            mailBox.put(mail.getRecipient(), new ArrayList<>());
        }
        mailBox.get((mail).getRecipient()).add(mail);
        increaseSenderPopularity((mail).getSender());
        increaseRecipientPopularity((mail).getRecipient());
    }

    /**
     * Метод возвращает мапу получатель -> все объекты которые пришли к этому получателю через данный почтовый сервис
     */
    public Map<String, List<Mail>> getMailBox() {
        return mailBox;
    }

    /**
     * Возвращает самого популярного отправителя
     */
    public String getPopularSender() {
        return sendersPopularityMap.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getKey();
    }

    /**
     * Возвращает самого популярного получателя
     */
    public String getPopularRecipient() {
        return recipientPopularityMap.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getKey();
    }

    /**
     * Метод должен заставить обработать service все mails.
     */
    public static void process(MailService service, List<Mail> mails) {
        mails.forEach(service);
    }

    private void increaseRecipientPopularity(String recipient) {
        Integer curPopularity = recipientPopularityMap.get(recipient);
        recipientPopularityMap.put(recipient, curPopularity == null ? 1 : curPopularity + 1);
    }

    private void increaseSenderPopularity(String sender) {
        Integer curPopularity = sendersPopularityMap.get(sender);
        sendersPopularityMap.put(sender, curPopularity == null ? 1 : curPopularity + 1);
    }

}

package ru.mail.polis.homework.collections.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * Пример работы сервиса
 */
public class MailDriver {
    public static void main(String[] args) {
        MailMessage message1 = new MailMessage("Sender1", "Recipient1", "text of first message");
        MailMessage message2 = new MailMessage("Sender2", "Recipient2", "text of second message");
        MailMessage message3 = new MailMessage("Sender1", "Recipient2", "text of third message");
        Salary salary1 = new Salary("Sender1", "Recipient2", 300);
        List<Mail> mailList = new ArrayList<>();

        mailList.add(message1);
        mailList.add(message2);
        mailList.add(message3);
        mailList.add(salary1);

        MailService mailService = new MailService();

        MailService.process(mailService, mailList);

        System.out.println(mailService.getMailBox());
        System.out.println();
        System.out.println("Most popular recipient: " + mailService.getPopularRecipient());
        System.out.printf("Most popular sender: " + mailService.getPopularSender());
        System.out.println();

    }
}

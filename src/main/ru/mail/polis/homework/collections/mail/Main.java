package ru.mail.polis.homework.collections.mail;

import java.util.Arrays;
import java.util.LinkedList;

public class Main {
    public static  void main(String[] args){
        MailService service = new MailService<>();
        LinkedList<AbstractMailMessage> mails = new LinkedList<>();
        int c = 0;
        for (String i: "aabc".split("")){
            c++;
            Salary m = new Salary(String.valueOf(c), i, c);
            MailMessage k = new MailMessage(String.valueOf(c), i, "ffff");
            mails.add(m);
            mails.add(k);
        }
        MailService.process(service, mails);
        System.out.println(service.getMailBox("a").size());
        System.out.println(service.getMailBox("b").size());
        System.out.println(service.getMailBox("c").size());
        System.out.println(service.getPopularRecipient());
        System.out.println(service.getPopularSender());
        for (int i = 0; i < service.getMailBox("a").size(); i++) {
            System.out.println(service.getMailBox("a").get(i).toString());
        }

    }
}

package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class MailMessage extends Mail<String> {
    MailMessage(String g, String s, String txt)
    {
        super(g,s, txt);
    }

    public String getTxtMsg()
    {
        return getAdditionalInformation();
    }
}

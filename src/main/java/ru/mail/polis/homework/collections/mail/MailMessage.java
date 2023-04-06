package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class MailMessage extends Mail {
    private String sum;

    public MailMessage(String sender, String recipient, String sum) {
        super(sender, recipient);
        this.sum = sum;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}

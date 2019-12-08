package ru.mail.polis.homework.collections.mail;

public class MailMessage extends Mail {
  final private String text;

  public MailMessage(String sender, String reciever, String text) {
    super(sender, reciever);
    this.text = text;
  }

  public String getText() {
    return text;
  }
}

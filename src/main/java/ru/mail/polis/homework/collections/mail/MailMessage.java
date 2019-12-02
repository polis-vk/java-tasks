package ru.mail.polis.homework.collections.mail;

public class MailMessage extends Mail {
  private String text;

  public MailMessage(String sender, String reciever, String text) {
    super(sender, reciever);
    this.text = text;
  }
}

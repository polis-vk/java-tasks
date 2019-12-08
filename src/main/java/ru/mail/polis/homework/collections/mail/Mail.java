package ru.mail.polis.homework.collections.mail;

abstract public class Mail {
  final private String sender;
  final private String reciever;

  public Mail(String sender, String reciever) {
    this.sender = sender;
    this.reciever = reciever;
  }

  public String getSender() {
    return sender;
  }

  public String getReciever() {
    return reciever;
  }
}

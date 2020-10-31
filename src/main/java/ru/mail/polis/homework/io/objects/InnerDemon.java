package ru.mail.polis.homework.io.objects;

public class InnerDemon {
  private boolean active;

  public InnerDemon(boolean active) {
    this.active = active;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}

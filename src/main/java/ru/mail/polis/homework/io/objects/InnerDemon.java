package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

public class InnerDemon implements Serializable {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof InnerDemon)) return false;
    InnerDemon that = (InnerDemon) o;
    return isActive() == that.isActive();
  }

  @Override
  public int hashCode() {
    return Objects.hash(isActive());
  }
}

package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

public class SerializableInnerDemon implements Serializable {
  private boolean active;

  public SerializableInnerDemon(boolean active) {
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
    if (!(o instanceof SerializableInnerDemon)) return false;
    SerializableInnerDemon that = (SerializableInnerDemon) o;
    return isActive() == that.isActive();
  }

  @Override
  public int hashCode() {
    return Objects.hash(isActive());
  }
}

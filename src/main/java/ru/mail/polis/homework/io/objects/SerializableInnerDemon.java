package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
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

  private void writeObject(ObjectOutputStream objectStream) throws IOException {
    objectStream.writeBoolean(active);
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    active = in.readBoolean();
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

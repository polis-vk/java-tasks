package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

public class ExternalizableInnerDemon implements Externalizable {
  private boolean active;

  public ExternalizableInnerDemon(boolean active) {
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

  @Override
  public void writeExternal(ObjectOutput out) throws IOException {
    out.writeBoolean(active);
  }

  @Override
  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    active = in.readBoolean();
  }
}

package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

public class BrainExternalizable implements Externalizable {

  private int size;

  public BrainExternalizable() {
  }

  public BrainExternalizable(int size) {
    this.size = size;
  }

  public int getSize() {
    return size;
  }

  @Override
  public void writeExternal(ObjectOutput out) throws IOException {
    out.writeObject(this.size);
  }

  @Override
  public void readExternal(ObjectInput in) throws IOException {
    this.size = in.readInt();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BrainExternalizable)) return false;
    BrainExternalizable that = (BrainExternalizable) o;
    return getSize() == that.getSize();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getSize());
  }
}
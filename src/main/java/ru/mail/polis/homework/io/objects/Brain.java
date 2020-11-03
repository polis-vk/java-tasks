package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Objects;

public class Brain implements Serializable {
  private int size;

  public Brain(int size) {
    this.size = size;
  }

  public int getSize() {
    return size;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Brain)) return false;
    Brain brain = (Brain) o;
    return getSize() == brain.getSize();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getSize());
  }
}

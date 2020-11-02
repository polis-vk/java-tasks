package ru.mail.polis.homework.io.objects;

import java.io.Serializable;

public class Brain implements Serializable {
  private int size;

  public Brain(int size) {
    this.size = size;
  }

  public int getSize() {
    return size;
  }
}

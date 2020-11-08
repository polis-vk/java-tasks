package ru.mail.polis.homework.io.objects;

import java.io.Serializable;

public class Size implements Serializable {
    private int height;
    private int width;

    public Size(int height, int width) {
        this.height = height;
        this.width = width;
        if(height < 0 || width < 0) {
            throw new IllegalArgumentException();
        }
    }

    public Size() {
        height = 1;
        width = 1;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public String toString() {
        return "Size{" +
                "height=" + height +
                ", width=" + width +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Size size = (Size) o;
        return height == size.height &&
                width == size.width;
    }

}

package ru.mail.polis.homework.io.objects;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Heart implements Serializable {

    private boolean isAlive;

    public Heart(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public Heart() {
        this.isAlive = true;
    }

    public void kill() {
        isAlive = false;
    }
}

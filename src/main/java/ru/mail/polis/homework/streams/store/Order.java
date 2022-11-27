package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.Map;

public class Order {
    private final Timestamp time;
    private final Map<Item, Integer> itemCount;

    public Order(Timestamp time, Map<Item, Integer> itemCount) {
        this.time = time;
        this.itemCount = itemCount;
    }

    public Timestamp getTime() {
        return time;
    }

    public Map<Item, Integer> getItemCount() {
        return itemCount;
    }
}

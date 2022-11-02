package ru.mail.polis.homework.streams.store;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(time, order.time) && Objects.equals(itemCount, order.itemCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, itemCount);
    }
}

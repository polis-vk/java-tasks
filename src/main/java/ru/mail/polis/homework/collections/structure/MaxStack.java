package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * хранится в Stack. Отрабатывать метод должен за О(1).
 * <p>
 * Т.к. класс наследует еще и методы Vector, логично было бы проработать
 * добавление и удаление также и с ними, но т.к. рассматривается stack,
 * предположим, что у класса используются только 5 функций его родительского класса.
 *
 *                      | Add  | Remove | Get  | Next |  Offer | Peak |   Poll   |
 * ---------------------|------|--------|------|------|--------|------|----------|
 * LinkedList           | O(1) |  O(1)  | O(n) | O(1) |  O(1)  | O(1) | O(1)     |

 */
public class MaxStack extends Stack<Integer> {
    private final Deque<Integer> maxStack = new LinkedList<>();

    @Override
    public Integer push(Integer item) {
        if (maxStack.isEmpty() || item >= maxStack.peek()) {
            maxStack.push(item);
        }
        return super.push(item);
    }

    @Override
    public synchronized Integer pop() {
        if (super.peek().equals(maxStack.peek())) {
            maxStack.pop();
        }
        return super.pop();
    }

    public Integer getMaxValue() {
        if (super.isEmpty()) {
            throw new EmptyStackException();
        }
        return maxStack.peek();
    }
}

package ru.mail.polis.homework.collections.structure;

import java.util.Stack;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {
    int max = Integer.MIN_VALUE;

    @Override
    public Integer push(Integer item) {
        max = Math.max(item, max);
        super.push(item);

        return item;
    }

    @Override
    public synchronized Integer pop() {
        int temp = super.pop();
        if(temp == max){
            max = Integer.MIN_VALUE;
            for (int i = 0; i < super.elementAt(i); i++) {
                max = Math.max(max, super.elementAt(i));
            }
        }
        return temp;
    }

    public Integer getMaxValue() {
        if((super.size() == 0) && (max == Integer.MIN_VALUE)){
            throw new RuntimeException();
        }
        return max;
    }
}

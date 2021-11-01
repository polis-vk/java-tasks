package ru.mail.polis.homework.collections.structure;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {

    private int max = Integer.MIN_VALUE;

    @Override
    public Integer push(Integer item) {
        Integer ret = super.push(item);
        if (this.size() == 1) {
            this.max = ret;
        } else {
            this.max = Math.max(this.max, ret);
        }
        return ret;
    }

    @Override
    public synchronized Integer pop() {
        Integer ret = super.pop();
        if (this.size() != 0) {
            if (this.size() == 1) {
                this.max = this.firstElement();
            } else {
                // не знаю ситуаций, при которых извлечение из стека любого
                // элемента вдруг добавит новый элемент, который больше максимального
                if(this.max == ret) {
                    // нужно искать новый максимум в таком случае
                    this.max = this.stream().max((Integer i1, Integer i2) -> {
                        return i1 - i2;
                    }).get();
                }
            }
        } else {
            this.max = Integer.MIN_VALUE;
        }
        return ret;
    }

    public Integer getMaxValue() {
        if (this.size() == 0) {
            throw new EmptyStackException();
        }
        return max;
    }

}

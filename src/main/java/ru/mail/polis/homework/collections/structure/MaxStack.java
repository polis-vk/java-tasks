package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Задание оценивается в 2 балла.
 * Надо расширить стандартный класс Stack методом getMaxValue().
 * Этот метод должен возвращать какое максимальное значение сейчас
 * храниться в Stack. Отрабатывать метод должен за О(1).
 */
public class MaxStack extends Stack<Integer> {

    private final Deque<Integer> max = new ArrayDeque<>();

    @Override
    public Integer push(Integer element){
        if(max.isEmpty() || max.getFirst()<=element)
            max.push(element);
        return super.push(element);
    }

    @Override
    public synchronized Integer pop(){
        Integer tmp = super.pop();
        if(tmp.equals(max.getFirst())) {
            max.pop();
        }
        return tmp;
    }


    public Integer getMaxValue() {
        if(empty()) {
            throw new EmptyStackException();
        }
        return max.getFirst();
    }

}

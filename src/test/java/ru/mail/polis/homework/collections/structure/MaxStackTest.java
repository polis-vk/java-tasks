package ru.mail.polis.homework.collections.structure;

import java.util.EmptyStackException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class MaxStackTest {

    @Test
    public void checkMax() {
        MaxStack stack = new MaxStack();
        int size = 50;
        for (int i = 0; i <= size; i++) {
            stack.push(i * i);
        }
        stack.push(-456);
        assertEquals((int) stack.getMaxValue(), size * size);
    }

    @Test(expected = EmptyStackException.class)
    public void checkEmpty() {
        MaxStack stack = new MaxStack();
        stack.getMaxValue();
    }

}
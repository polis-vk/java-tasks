package ru.mail.polis.homework.collections.structure;

import java.util.EmptyStackException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MaxStackTest {

    @Test(expected = EmptyStackException.class)
    public void testGetMaxValueInEmptyStack() {
        MaxStack stack = new MaxStack();
        stack.getMaxValue();
    }

    @Test
    public void testGetMaxValueForAddElementsByAscending() {
        MaxStack stack = new MaxStack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        assertEquals(Integer.valueOf(5), stack.getMaxValue());
    }

    @Test
    public void testGetMaxValueForAddElementsByDescending() {
        MaxStack stack = new MaxStack();
        stack.push(5);
        stack.push(4);
        stack.push(3);
        stack.push(2);
        stack.push(1);

        assertEquals(Integer.valueOf(5), stack.getMaxValue());
    }

    @Test
    public void testGetMaxValueWhenMaxElementInMiddleStack() {
        MaxStack stack = new MaxStack();
        stack.push(1);
        stack.push(1);
        stack.push(1);
        stack.push(5);
        stack.push(1);
        stack.push(1);
        stack.push(1);

        assertEquals(Integer.valueOf(5), stack.getMaxValue());
    }

    @Test
    public void testGetMaxValueWhenRemoveElement() {
        MaxStack stack = new MaxStack();
        stack.push(1);
        stack.push(1);
        stack.push(5);

        assertEquals(Integer.valueOf(5), stack.getMaxValue());

        stack.pop();

        assertEquals(Integer.valueOf(1), stack.getMaxValue());
    }

    @Test
    public void testGetMaxValueWhenRemoveAndAddElements() {
        MaxStack stack = new MaxStack();

        stack.push(3);
        stack.push(3);
        stack.push(3);
        assertEquals(Integer.valueOf(3), stack.getMaxValue());

        stack.push(2);
        stack.push(1);
        stack.push(0);
        assertEquals(Integer.valueOf(3), stack.getMaxValue());

        stack.push(4);
        stack.push(2);
        stack.push(1);
        assertEquals(Integer.valueOf(4), stack.getMaxValue());

        stack.pop();
        assertEquals(Integer.valueOf(4), stack.getMaxValue());

        stack.pop();
        stack.pop();
        assertEquals(Integer.valueOf(3), stack.getMaxValue());
    }

    @Test
    public void testRemoveDuplicateMaxValue(){
        MaxStack stack = new MaxStack();

        stack.push(3);
        assertEquals(Integer.valueOf(3), stack.getMaxValue());

        stack.push(5);
        assertEquals(Integer.valueOf(5), stack.getMaxValue());
        stack.push(5);
        assertEquals(Integer.valueOf(5), stack.getMaxValue());

        stack.pop();
        assertEquals(Integer.valueOf(5), stack.getMaxValue());

        stack.pop();
        assertEquals(Integer.valueOf(3), stack.getMaxValue());

    }
}
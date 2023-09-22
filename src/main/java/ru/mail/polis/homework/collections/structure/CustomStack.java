package ru.mail.polis.homework.collections.structure;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class CustomStack<E> extends Stack<E> {
    private List<E> list = new LinkedList<>();
    private E curr_max;
    private int list_pos = -1;
    public E getMax()
    {
        return curr_max;
    }
    CustomStack()
    {
        super();
        curr_max = null;
    }
    @Override
    public E push(E val)
    {
        addElement(val);
        if(curr_max == null) {
            curr_max = val;
        }
        else
            if((Integer)curr_max < (Integer)val) curr_max = val;
        list.add(curr_max);
        ++list_pos;
        return val;
    }

    @Override
    public synchronized E pop() {
        E obj;
        int len = size();
        obj = peek();
        removeElementAt(len - 1);
        curr_max = list.get(--list_pos);
        return obj;
    }

}

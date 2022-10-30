package ru.mail.polis.homework.collections.structure;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 *
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    private int capacity = 10;
    private E[] list = (E[]) new Object[capacity];
    private int pos = 0;

    public CustomArrayList(){ }

    public CustomArrayList(int capacity){
        this.capacity = capacity;
    }

    public CustomArrayList(E[] list){
        this.list = list;
    }

    @Override
    public int size() {
        return list.length;
    }

    @Override
    public boolean isEmpty() {
        return list.length == 0;
    }

    @Override
    public boolean contains(Object o) {
        for(int i = 0; i < pos; i++){
            if (((list[i] == null) && (o == null)))
            {return  true;}
            if(o != null) {
                if (list[i].equals((E) o)) {
                    return true;
                }
            }
        }
       return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int position = 0;
            @Override
            public boolean hasNext() {
                return position < list.length;
            }

            @Override
            public E next() {
                if(!hasNext()){
                    throw new IndexOutOfBoundsException();
                }
                return list[position++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.stream(list).toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        T[] t = (T[]) new Object[a.length];
        for (T e : a) {
            t = (T[]) e;
        }
        return t;
    }

    @Override
    public boolean add(E e) {
        if (capacity > pos){
            list[pos++] = e;
            return true;
        }
        int newCapacity = capacity * 3 / 2 + 1;
        list = (E[]) new Object[newCapacity];
        System.arraycopy(list, 0, list,0, capacity);
        capacity = newCapacity;
        list[list.length-1] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        E[] e = (E[]) new Object[capacity];
        int i = 0;
        for(E es : list){
            i++;
            if(es.equals(o)) {
                System.arraycopy(list, 0, e, 0, i);
                System.arraycopy(list, i+1, e, i+1, list.length-i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        int i = 0;
        for(E e : list){
            for (Object q : c){
                if (e.equals((E) q)){
                    i++;
                }
            }
        }
        return i == c.size();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for(E e : c){
            this.add(e);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        E[] e1 = (E[]) new Object[index - 1];
        E[] e2 = (E[]) new Object[capacity - index - 1];
        System.arraycopy(list, 0, e1, 0, index - 1);
        System.arraycopy(list, index, e2, index, list.length - index - 1);
        this.addAll(c);
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean res = false;
        for(E e : list){
            for (Object o : c){
               if (e.equals((E) o)){
                   res = remove(e);
               }
            }
        }
        return res;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        E[] e = (E[]) new Object[c.size()];
        int j = 0;
        for(int i = 0; i < list.length; i++){
            for(Object q : c){
                if (this.list[i].equals(((E) q))) {
                    e[j++] = (E) q;
                }
            }
        }
        return j > 0;
    }

    @Override
    public void clear() {
        list = (E[]) new Object[0];
        pos =0;
    }

    @Override
    public E get(int index) {
        return list[index];
    }

    @Override
    public E set(int index, E element) {
        E e = list[index];
        list[index] = element;
        return e;
    }

    @Override
    public void add(int index, E element) {
        this.addAll(index, Collections.singleton(element));
    }

    @Override
    public E remove(int index) {
        int i = 0;
        E q = null;
        for (E e : list){
            i++;
            if (index == i){
                q = e;
                this.remove(e);
            }
        }
        return q;
    }

    @Override
    public int indexOf(Object o) {
        int i = 0;
       for(E e : list){
           i++;
           if(e.equals((E) o)){
               return i;
           }
       }
       return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int i = 0;
        int res = -1;
        for(E e : list){
            i++;
            if(e.equals((E) o)){
                res = i;
            }
        }
        return res;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListIterator<E>() {
            private int pos;
            @Override
            public boolean hasNext() {
                return pos++ < list.length;
            }

            @Override
            public E next() {
                if (hasNext()) {
                    return list[pos];
                }
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return pos > 0;
            }

            @Override
            public E previous() {
                return hasPrevious() ? list[--pos] : null;
            }

            @Override
            public int nextIndex() {
                return pos+1;
            }

            @Override
            public int previousIndex() {
                return pos-1;
            }

            @Override
            public void remove() {
                CustomArrayList.this.remove(pos);
            }

            @Override
            public void set(E e) {
                CustomArrayList.this.set(pos, e);
            }

            @Override
            public void add(E e) {
                CustomArrayList.this.add(pos, e);
            }

        };
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        ListIterator<E> e = listIterator();
        for (int i = 0; i < index; i++){
           e.next();
        }
        return e;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        E[] e = (E[]) new Object[toIndex - fromIndex];
        return new CustomArrayList<>(e);
    }
}

package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 * <p>
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    private E[] array;
    private int size;

    public CustomArrayList() {
        size = 0;
        array = (E[]) new Object[10];
    }

    public CustomArrayList(E[] inp){
        size = inp.length;
        array = inp.clone();
    }
    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            E e = array[i];
            if (o == null ? e == null : e.equals(o)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomIterator();
    }

    @Override
    public Object[] toArray() {
        E[] ans = (E[]) new Object[size];
        System.arraycopy(array, 0, ans, 0, size);
        return ans;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = Arrays.copyOf(a, size);
        }
        System.arraycopy(array, 0, a, 0, size);
        return a;
    }


    @Override
    public boolean add(E e) {
        if (size == array.length) {
            array = Arrays.copyOf(array, array.length * 2);
        }
        array[size++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int i;
        E[] newArr = (E[]) new Object[size];
        int offset = 0;
        for (i = 0; i < size; i++) {
            newArr[i - offset] = array[i];
            if (array[i] == o) {
                offset = 1;
                if (i == size - 1) {
                    newArr[i] = null;
                }
            }
        }
        size -= offset;
        if (offset == 0) {
            return false;
        }
        array = newArr;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            add(e);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        E[] newArr = (E[]) new Object[array.length + c.size()];
        Iterator<? extends E> iterator = c.iterator();
        for (int i = 0; i < size + c.size(); i++) {
            if (i < index) {
                newArr[i] = array[i];
            } else if (i < index + c.size()) {
                newArr[i] = iterator.next();
            } else {
                newArr[i] = array[i - c.size()];
            }
        }
        size += c.size();
        array = newArr;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean ans = false;
        for (Object o : c) {
            if (remove(o)) {
                ans = true;
            }
        }
        return ans;
    }

    // а size кто менять будет??????
    // везде добавить!!!!!

    @Override
    public boolean retainAll(Collection<?> c) {
        E[] newArr = (E[]) new Object[size];
        int cnt = 0;

        for (int i = 0; i < size; i++) {
            E element = array[i];
            if (c.contains(element)) {
                newArr[cnt++] = element;

            }
        }
        boolean ans = array.length != newArr.length;
        array = newArr;
        size = cnt;
        return ans;
    }

    @Override
    public void clear() {
        array = (E[]) new Object[10];
        size = 0;
    }

    @Override
    public E get(int index) {
        if (index >= size) {
            throw new NoSuchElementException();
        }
        return array[index];
    }

    @Override
    public E set(int index, E element) {
        if (index >= size) {
            throw new NoSuchElementException();
        }
        E prev = get(index);
        array[index] = element;
        return prev;
    }

    @Override
    public void add(int index, E element) {
        int i;
        if (index > size) {
            throw new NoSuchElementException();
        }
        E[] newArr;
        if (size == array.length) {
            newArr = Arrays.copyOf(array, array.length * 2);
        } else {
            newArr = Arrays.copyOf(array, array.length);
        }
        int offset = 0;
        for (i = 0; i < size + 1; i++) {
            newArr[i] = array[i - offset];
            if (i == index) {
                newArr[i] = element;
                offset = 1;
            }
        }
        size++;
        array = newArr;
    }

    @Override
    public E remove(int index) {
        if (index >= size || index < 0) {
            throw new NoSuchElementException();
        }
        int i;
        E ans = null;
        E[] newArr = (E[]) new Object[size];
        int offset = 0;
        for (i = 0; i < size; i++) {
            newArr[i - offset] = array[i];
            if (i == index) {
                ans = array[i];
                offset = 1;
            }
        }
        size--;
        array = newArr;
        return ans;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(o, array[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(o, array[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new CustomListIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new CustomListIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        E[] newArr = (E[]) new Object[size];
        System.arraycopy(array, 0, newArr, 0, size);
        return new CustomArrayList<>(newArr);
    }

    private class CustomIterator implements Iterator<E> {
        int pointer = 0;

        @Override
        public boolean hasNext() {
            return pointer != CustomArrayList.this.size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return CustomArrayList.this.array[pointer++];
        }
    }

    public class CustomListIterator implements ListIterator<E> {
        int pointer;
        List<E> clone;
        public CustomListIterator() {
            this(0);
        }

        public CustomListIterator(int index) {

            clone = CustomArrayList.this.subList(0, CustomArrayList.this.size);
            pointer = index - 1;
        }

        @Override
        public boolean hasNext() {
            return pointer < clone.size();
        }

        @Override
        public E next() {
            return clone.get(++pointer);
        }

        @Override
        public boolean hasPrevious() {
            return pointer > -1;
        }

        @Override
        public E previous() {
            return clone.get(--pointer);
        }

        @Override
        public int nextIndex() {
            return pointer;
        }

        @Override
        public int previousIndex() {
            return pointer - 1;
        }

        @Override
        public void remove() {
            CustomArrayList.this.remove(pointer);
        }

        @Override
        public void set(E e) {
            CustomArrayList.this.set(pointer, e);
        }

        @Override
        public void add(E e) {
            CustomArrayList.this.add(pointer + 1, e);

        }
    }


}

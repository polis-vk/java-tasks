package ru.mail.polis.homework.collections.structure;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 * <p>
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {

    private E[] arr;
    int size;

    public CustomArrayList() {
        arr = (E[]) new Object[10];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private int inx = 0;

            @Override
            public boolean hasNext() {
                return inx < size;
            }

            @Override
            public E next() {
                if (hasNext()) {
                    return arr[inx++];
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(arr, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length != size) {
            a = Arrays.copyOf(a, size);
        }
        Object[] tmp = a;
        for (int i = 0; i < size; ++i) {
            tmp[i] = get(i);
        }
        return a;
    }

    @Override
    public boolean add(E e) {
        if (size == arr.length) {
            resize(arr.length * 2);
        }
        arr[size++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; ++i) {
            if (arr[i].equals(o)) {
                System.arraycopy(arr, i + 1, arr, i, --size - i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Object[] tmp = c.toArray();
        for (int i = 0; i < tmp.length; ++i) {
            if (!contains(tmp[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > size) {
            return false;
        }
        Object[] tmp = c.toArray();
        if (tmp.length == 0) {
            return false;
        }
        if (arr.length - size < tmp.length) {
            resize(arr.length + tmp.length);
        }
        System.arraycopy(arr, index, arr, index + tmp.length, size - index);
        System.arraycopy(tmp, 0, arr, index, tmp.length);
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Object[] tmp = c.toArray();
        for (int i = 0; i < tmp.length; ++i) {
            remove(tmp[i]);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        for (int i = size - 1; i >= 0; --i) {
            Object o = get(i);
            if (!c.contains(o)) {
                remove(i);
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public E get(int index) {
        return arr[index];
    }

    @Override
    public E set(int index, E element) {
        E prev = arr[index];
        arr[index] = element;
        return prev;
    }

    @Override
    public void add(int index, E element) {
        if (!checkIndex(index)) {
            return;
        }
        if (size == arr.length) {
            resize(arr.length * 2);
        }
        System.arraycopy(arr, index, arr, index + 1, size++ - index);
        arr[index] = element;
    }

    @Override
    public E remove(int index) {
        if (!checkIndex(index)) {
            return null;
        }
        E tmp = arr[index];
        System.arraycopy(arr, index + 1, arr, index, size - index - 1);
        size--;
        return tmp;
    }

    @Override
    public int indexOf(Object o) {
        Object[] tmp = arr;
        if (o == null) {
            for (int i = 0; i < this.size; ++i) {
                if (tmp[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < this.size; ++i) {
                if (o.equals(tmp[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; --i) {
            if (arr[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new CustomListIterator<>(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new CustomListIterator<>(index);

    }

    private class CustomListIterator<T> implements ListIterator<T> {

        private int index;

        public CustomListIterator(int index) {
            this.index = index;
        }
        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            if (hasNext()) {
                return (T) arr[index++];
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public boolean hasPrevious() {
            return index >= 0;
        }

        @Override
        public T previous() {
            if (hasPrevious()) {
                return (T) arr[index--];
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public int nextIndex() {
            return (index == size) ? index : index + 1;
        }

        @Override
        public int previousIndex() {
            return (index == 0) ? index : index - 1;
        }

        @Override
        public void remove() {
            CustomArrayList.this.remove(index);
        }

        @Override
        public void set(T e) {
            CustomArrayList.this.set(index, (E) e);
        }

        @Override
        public void add(T e) {
            CustomArrayList.this.add(index, (E) e);
            next();
        }
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return Collections.emptyList();
    }

    private void resize(int newCapacity) {
        arr = Arrays.copyOf(arr, newCapacity);
    }

    private boolean checkIndex(int index) {
        return index >= 0 && index < size;
    }

}

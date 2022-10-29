package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 * <p>
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 10;

    private Object[] data;
    private int size;

    CustomArrayList(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + capacity);
        }

        this.size = 0;
        this.data = new Object[capacity];
    }

    CustomArrayList() {
        this(DEFAULT_CAPACITY);
    }

    private void resize() {
        E[] tmp = (E[]) new Object[data.length * 2];
        System.arraycopy(data, 0, tmp, 0, size);
        data = tmp;
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

    private class Itr implements Iterator<E> {
        int curInx;
        int prevInx = -1;

        @Override
        public boolean hasNext() {
            return curInx < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            prevInx = curInx;
            curInx++;

            return (E) CustomArrayList.this.data[prevInx];
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a == null) {
            throw new NullPointerException();
        }

        if (a.length < size) {
            return (T[]) Arrays.copyOf(data, size, a.getClass());
        }

        System.arraycopy(data, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public boolean add(E e) {
        if (data.length == size) {
            resize();
        }
        data[size] = e;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!contains(o)) {
            return false;
        }

        int inx = indexOf(o);

        int newSize = size - 1;
        if (newSize > inx) {
            System.arraycopy(data, inx + 1, data, inx, newSize - inx);
        }
        data[size = newSize] = null;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object el : c) {
            if (!this.contains(el)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (Object el : c) {
            add((E) el);
        }

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        int curInx = index;
        for (Object el : c) {
            add(curInx, (E) el);
            curInx++;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int startSize = size;
        for (Object el : c) {
            remove(el);
        }

        return startSize != size;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int index = 0;
        Object[] tmp = new Object[size];

        int startSize = size;

        for (Object el : c) {
            if (this.contains(el)) {
                tmp[index] = el;
                index++;
            }
        }

        size = index++;
        System.arraycopy(tmp, 0, data, 0, size);

        return startSize != size;
    }

    @Override
    public void clear() {
        data = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public E get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return (E) data[index];
    }

    @Override
    public E set(int index, E element) {
        checkOutOfBounds(index);

        data[index] = element;
        return (E) data[index];
    }

    @Override
    public void add(int index, E element) {
        checkOutOfBounds(index);

        if (size >= data.length) {
            resize();
        }

        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        checkOutOfBounds(index);

        E res = (E) data[index];
        if (index != size - 1) {
            System.arraycopy(data, index + 1, data, index, size - index - 1);
        }
        size--;

        return res;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;

        Object key = null;
        if(o != null){
            key = o;
        }

        for (; index < size; index++) {
            if (data[index] == key) {
                return index;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = size - 1;

        Object key = null;
        if(o != null){
            key = o;
        }

        for (; index >= 0; index--) {
            if (data[index] == key) {
                return index;
            }
        }

        return -1;
    }

    private class ListItr extends Itr implements ListIterator<E> {

        ListItr() {
            super();
        }

        ListItr(int index) {
            super();
            super.curInx = index;
        }

        public boolean hasPrevious() {
            return curInx != 0;
        }

        public int nextIndex() {
            return curInx;
        }

        public int previousIndex() {
            return curInx - 1;
        }

        @Override
        public void remove() {

        }

        @SuppressWarnings("unchecked")
        public E previous() {
            int i = curInx - 1;
            if (i < 0) {
                throw new NoSuchElementException();
            }
            Object[] elementData = CustomArrayList.this.data;

            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            curInx = i;
            return (E) elementData[prevInx = i];
        }

        public void set(E e) {
            if (prevInx < 0) {
                throw new IllegalStateException();
            }

            try {
                CustomArrayList.this.set(prevInx, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {
            try {
                int i = curInx;
                CustomArrayList.this.add(i, e);
                curInx = i + 1;
                prevInx = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListItr();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListItr(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }

        List<E> subList = new CustomArrayList<>();
        for (int i = fromIndex; i < toIndex + 1; i++) {
            subList.add(get(i));
        }
        return subList;
    }

    private void checkOutOfBounds(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }
}

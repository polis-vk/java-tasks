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
    E[] array;
    int size;

    public CustomArrayList() {
        array = (E[]) new Object[DEFAULT_CAPACITY];
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
        for (E e : this) {
            if (Objects.equals(o, e)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return listIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length >= size) {
            System.arraycopy(array, 0, a, 0, size);
            return a;
        }
        return (T[]) Arrays.copyOf(array, size, a.getClass());
    }

    @Override
    public boolean add(E e) {
        add(size, e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object v : c) {
            if (!contains(v)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.size() + size > array.length) {
            array = Arrays.copyOf(array, array.length * 2);
        }
        for (E n : c) {
            add(n);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c.size() + size > array.length) {
            array = Arrays.copyOf(array, array.length * 2);
        }
        System.arraycopy(array, index, array, index + c.size(), size() - index); // shift array to right
        System.arraycopy(c.toArray(), 0, array, index, c.size()); // copy from c to array
        size += c.size();
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        ListIterator<E> iter = listIterator();
        while (iter.hasNext()) {
            if (c.contains(iter.next())) {
                iter.remove();
            }
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // removes from this list all of its elements that are not contained in the specified collection
        E[] newArray = (E[]) new Object[array.length];
        int j = 0;
        for (int i = 0; i < size; i++) {
            if (c.contains(array[i])) {
                newArray[j] = array[i];
                j++;
            }
        }
        array = newArray;
        size = j;
        return true;
    }

    @Override
    public void clear() {
        array = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return array[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        E prevValue = array[index];
        array[index] = element;
        return prevValue;
    }

    @Override
    public void add(int index, E element) {
        if (index > size || index < 0) { // checkIndex is not used (index may be equal to size)
            throw new IndexOutOfBoundsException();
        }
        if (size == array.length - 1) {
            array = Arrays.copyOf(array, array.length * 2);
        }
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        E removedValue = array[index];
        System.arraycopy(array, index + 1, array, index, size-- - index);
        return removedValue;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(array[i], o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = -1;

        for (int i = 0; i < size; i++) {
            if (Objects.equals(array[i], o)) {
                index = i;
            }
        }
        return index;
    }

    public void checkIndex(int i) {
        if (i >= size || i < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        return new ListIterator<E>() {

            int curPos = index;
            int oldPos = index;

            @Override
            public boolean hasNext() {
                return curPos < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                oldPos = curPos;
                curPos++;
                return array[oldPos];
            }

            @Override
            public boolean hasPrevious() {
                return 0 < curPos && curPos <= size;
            }

            @Override
            public E previous() {
                if (hasPrevious()) {
                    return array[--curPos];
                }
                return null;
            }

            @Override
            public int nextIndex() {
                return curPos + 1;
            }

            @Override
            public int previousIndex() {
                return curPos - 1;
            }

            @Override
            public void remove() {
                if (oldPos != -1) {
                    CustomArrayList.this.remove(oldPos);
                    curPos = oldPos;
                    oldPos = -1;
                }
            }

            @Override
            public void set(E e) {
                CustomArrayList.this.set(curPos, e);
            }

            @Override
            public void add(E e) {
                CustomArrayList.this.add(curPos++, e);
            }
        };
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        CustomArrayList<E> list = new CustomArrayList<>();
        list.addAll(Arrays.asList(array).subList(fromIndex, toIndex));
        return list;
    }
}

package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
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

    private final int DEFAULT_CAPACITY = 10;

    private int size = 0;

    private Object[] array;
    private int modCount = 0;

    public CustomArrayList() {
        array = new Object[DEFAULT_CAPACITY];
    }

    public CustomArrayList(Collection<E> collection) {
        array = collection.toArray();
        size = collection.size();
    }

    public CustomArrayList(Object[] arr) {
        array = arr;
        size = arr.length;
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
        return new CustomIterator<>();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(array, size, a.getClass());
        } else {
            System.arraycopy(array, 0, a, 0, size);
            return a;
        }
    }

    @Override
    public boolean add(E e) {
        try {
            add(size, e);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean remove(Object o) {
        try {
            int removingObjectIndex = indexOf(o);
            if (removingObjectIndex == -1) {
                return false;
            }
            remove(removingObjectIndex);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if (!contains(e)) {
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
        try {
            checkIndexForValid(index);
            if (c.size() == 0) {
                return false;
            }
            if (array.length + c.size() >= array.length) {
                array = Arrays.copyOf(array, array.length + (array.length >> 1));
            }
            System.arraycopy(array, index, array, index + c.size(), size - index);
            System.arraycopy(c.toArray(), 0, array, index, c.size());
            size += c.size();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean isArrayChanged = false;
        for (Object o : c) {
            while (indexOf(o) != -1) {
                remove(indexOf(o));
                isArrayChanged = true;
            }
        }
        return isArrayChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean isArrayChanged = false;
        for (Object o : array) {
            if (!c.contains(o)) {
                remove(o);
                isArrayChanged = true;
            }
        }
        return isArrayChanged;
    }

    @Override
    public void clear() {
        size = 0;
        Arrays.fill(array, null);
    }

    @Override
    public E get(int index) {
        return (E) array[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndexForValid(index);
        Object previousElement = array[index];
        array[index] = element;
        return (E) previousElement;
    }

    @Override
    public void add(int index, E element) {
        checkIndexForValid(index);
        Object[] newArray = new Object[array.length + (array.length >> 1)];
        System.arraycopy(array, 0, newArray, 0, index);
        newArray[index] = element;
        System.arraycopy(array, index, newArray, index + 1, size - index);
        size++;
        array = newArray;
    }

    @Override
    public E remove(int index) {
        checkIndexForValid(index);
        Object removingObject = array[index];
        Object[] newArray = new Object[size - 1];
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index + 1, newArray, index, (size - 1) - index);
        size--;
        array = newArray;
        return (E) removingObject;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(array[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(array[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new CustomListIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("Index " + fromIndex + " is greater than " + toIndex);
        }
        checkIndexForValid(fromIndex);
        checkIndexForValid(toIndex);
        CustomArrayList<E> newList = new CustomArrayList<>();
        System.arraycopy(array, fromIndex, newList.array, 0, toIndex - fromIndex);
        return newList;
    }

    private class CustomIterator<E> implements Iterator<E> {
        int pointer = 0;
        int fixedModCount = modCount;

        @Override
        public boolean hasNext() {
            return pointer != size;
        }

        @Override
        public E next() {
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (hasNext()) {
                Object next = array[pointer];
                pointer++;
                return (E) next;
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    private void checkIndexForValid(int index) {
        if (index < 0 || index > array.length) {
            throw new IndexOutOfBoundsException();
        }
    }

    private class CustomListIterator extends CustomIterator<E> implements ListIterator<E> {

        CustomListIterator(int index) {
            this.pointer = index;
        }

        @Override
        public boolean hasPrevious() {
            return pointer != 0;
        }

        @Override
        public E previous() {
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (hasPrevious()) {
                Object previous = array[pointer];
                pointer--;
                return (E) previous;
            } else {
                throw new NoSuchElementException();
            }
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
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            int i = pointer;
            CustomArrayList.this.remove(i);
            pointer = i - 1;
            fixedModCount = modCount;
        }

        @Override
        public void set(E e) {
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            CustomArrayList.this.set(pointer, e);
        }

        @Override
        public void add(E e) {
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            int i = pointer;
            CustomArrayList.this.add(i, e);
            pointer = i + 1;
            fixedModCount = modCount;
        }
    }

}

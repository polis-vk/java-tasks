package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 *
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private E[] elementData;
    private int size;
    private int modCount;

    @SuppressWarnings("unchecked")
    public CustomArrayList() {
        this.elementData = (E[]) new Object[DEFAULT_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    public CustomArrayList(int capacity) {
        this.elementData = (E[]) new Object[capacity];
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
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        int cursor;
        int lastRet = -1;
        int expectedModCount = modCount;

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public E next() {
            checkForModification();
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            if (cursor >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            lastRet = cursor;
            cursor++;
            return elementData[lastRet];
        }

        @Override
        public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            checkForModification();
            try {
                CustomArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException exception) {
                throw new ConcurrentModificationException();
            }
        }

        final void checkForModification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @SuppressWarnings({"unchecked", "SuspiciousSystemArraycopy"})
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        }
        System.arraycopy(elementData, 0, a, 0, size);
        return a;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean add(E e) {
        modCount++;
        if (size == elementData.length) {
            elementData = (E[]) grow();
        }
        elementData[size++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int i = 0;
        if (o == null) {
            for (; i < size; i++) {
                if (elementData[i] == null) {
                    remove(i);
                    return true;
                }
            }
        } else {
            for (; i < size; i++) {
                if (o.equals(elementData[i])) {
                    remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (int i = 0; i < size; i++) {
            if (this.contains(c)) {
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean addAll(Collection<? extends E> c) {
        E[] arr = (E[]) c.toArray();
        if (arr.length == 0) {
            return false;
        }
        modCount++;
        if (arr.length > elementData.length - size) {
            elementData = (E[]) grow(size + arr.length);
        }
        System.arraycopy(arr, 0, elementData, size, arr.length);
        size += arr.length;
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkIndexForAdd(index);
        E[] arr = (E[]) c.toArray();
        if (arr.length == 0) {
            return false;
        }
        modCount++;
        if (arr.length > elementData.length - size) {
            elementData = (E[]) grow(size + arr.length);
        }
        int numMoved = size - index;
        if (numMoved > 0) {
            System.arraycopy(elementData, index,
                    elementData, index + arr.length, numMoved);
        }
        System.arraycopy(arr, 0, elementData, index, arr.length);
        size += arr.length;
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean removeAll(Collection<?> c) {
        E[] arr = (E[]) c.toArray();
        if (arr.length == 0) {
            return false;
        }
        int tempSize = size;
        for (int i = 0; i < size; i++) {
            for (E e : arr) {
                if (elementData[i].equals(e)) {
                    modCount++;
                    remove(i);
                    break;
                }
            }
            if (tempSize != size) {
                tempSize = size;
                i--;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean retainAll(Collection<?> c) {
        E[] arr = (E[]) c.toArray();
        if (arr.length == 0) {
            return false;
        }
        boolean isRetained = false;
        int tempSize = size;
        for (int i = 0; i < size; i++) {
            for (E e : arr) {
                if (elementData[i].equals(e)) {
                    isRetained = true;
                    break;
                }
            }
            if (!isRetained) {
                modCount++;
                remove(i);
            }
            if (tempSize != size) {
                tempSize = size;
                i--;
            }
            isRetained = false;
        }
        return true;
    }

    @Override
    public void clear() {
        modCount++;
        while (size != 0) {
            elementData[size - 1] = null;
            size--;
        }
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return elementData[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        E oldValue = elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void add(int index, E element) {
        checkIndexForAdd(index);
        modCount++;
        if (size == elementData.length) {
            elementData = (E[]) grow();
        }
        System.arraycopy(elementData, index,
                elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        modCount++;
        E removedValue = elementData[index];
        fastRemove(index);
        return removedValue;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elementData[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elementData[i])) {
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
                if (elementData[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListItr(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListItr(index);
    }

    private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public E previous() {
            checkForModification();
            int i = cursor - 1;
            if (i < 0) {
                throw new NoSuchElementException();
            }
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            cursor = i;
            lastRet = i;
            return elementData[lastRet];
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void set(E e) {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            checkForModification();
            try {
                CustomArrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException exception) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void add(E e) {
            checkForModification();
            try {
                int i = cursor;
                CustomArrayList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException exception) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        subListCheckIndex(fromIndex, toIndex, size);
        int subListLength = toIndex - fromIndex;
        E[] temp = (E[]) new Object[subListLength];
        System.arraycopy(elementData, fromIndex, temp, 0, subListLength);
        List<E> subList = new CustomArrayList<>(subListLength);
        subList.addAll(Arrays.asList(temp));
        return subList;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void fastRemove(int index) {
        modCount++;
        int newSize = size - 1;
        if (newSize > index) {
            System.arraycopy(elementData, index + 1, elementData, index, newSize - index);
        }
        size = newSize;
        elementData[size] = null;
    }

    private Object[] grow(int minCapacity) {
        int oldCapacity = elementData.length;
        int newCapacity = (oldCapacity + minCapacity) * 2;
        elementData = Arrays.copyOf(elementData, newCapacity);
        return elementData;
    }

    private Object[] grow() {
        return grow(size + 1);
    }

    private void subListCheckIndex(int fromIndex, int toIndex, int size) {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
        }
        if (toIndex > size) {
            throw new IndexOutOfBoundsException("toIndex = " + toIndex);
        }
        if (fromIndex >= toIndex) {
            throw new IllegalArgumentException("fromIndex(" + fromIndex +
                    ") >= toIndex(" + toIndex + ")");
        }
    }
}

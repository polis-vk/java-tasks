package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 *
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private E[] array;
    private int size;
    private int modCount;

    public CustomArrayList() {
        this.array = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public CustomArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Illegal capacity: "+ capacity);
        }
        this.array = (E[]) new Object[capacity];
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
        return new CustomListIterator(0);
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(array, size, a.getClass());
        }
        System.arraycopy(array, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public boolean add(E e) {
        add(size, e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        remove(index);
        return index >= 0;
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
        checkIndexForAdd(index);
        int sizeToAdd = c.size();
        ensureCapacity(size + sizeToAdd);
        if (size - index > 0) {
            System.arraycopy(array, index, array, index +sizeToAdd, size - index);
        }
        System.arraycopy(c.toArray(), 0, array, index, sizeToAdd);
        size += sizeToAdd;
        modCount += sizeToAdd;
        return sizeToAdd != 0;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return removeMultiple(c, false, 0, size);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return removeMultiple(c, true, 0, size);
    }

    @Override
    public void clear() {
        modCount++;
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
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
        E oldValue = array[index];
        array[index] = element;
        return oldValue;
    }

    @Override
    public void add(int index, E element) {
        checkIndexForAdd(index);
        modCount++;
        if (size >= array.length) {
            ensureCapacity(size + 1);
        }
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        modCount++;
        E value = array[index];
        if (--size > index) {
            System.arraycopy(array, index + 1, array, index, size - index);
        }
        array[size] = null;
        return value;
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
                if (array[i].equals(o)) {
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
                if (array[i].equals(o)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new CustomListIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new CustomListIterator(index);
    }

    private class CustomListIterator implements ListIterator<E> {
        private int expectedModCount = modCount;
        private int index;
        private int lastIndex = -1;

        CustomListIterator(int index) {
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return index != size;
        }

        @Override
        public E next() {
            checkForComodification();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastIndex = index;
            index++;
            return array[lastIndex];
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public E previous() {
            checkForComodification();
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            index--;
            lastIndex = index;
            return array[index];
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            checkForComodification();
            CustomArrayList.this.remove(lastIndex);
            index = lastIndex;
            lastIndex = -1;
            expectedModCount = modCount;
        }

        @Override
        public void set(E e) {
            checkForComodification();
            CustomArrayList.this.set(lastIndex, e);
            expectedModCount = modCount;
        }

        @Override
        public void add(E e) {
            checkForComodification();
            CustomArrayList.this.add(index, e);
            index++;
            lastIndex = -1;
            expectedModCount = modCount;
        }

        private void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        checkIndex(fromIndex);
        checkIndex(toIndex);
        return (List<E>) new SubList(this, fromIndex, toIndex);
    }

    private class SubList {
        private final CustomArrayList<E> root;
        private final int startIndex;
        private final int endIndex;
        private final int expectedModCount = modCount;

        public SubList(CustomArrayList<E> root, int startIndex, int endIndex) {
            this.root = root;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        public void add(int index, E element) {
            checkIndex(index);
            checkForComodification();
            root.add(startIndex + index, element);
        }

        public E get(int index) {
            checkIndex(index);
            checkForComodification();
            return root.get(index);
        }

        public int size() {
            checkForComodification();
            return endIndex - startIndex;
        }

        private void checkForComodification() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private boolean removeMultiple(Collection<?> c, boolean complement, int start, int end) {
        int read;
        for (read = start;; read++) {
            if (read == end) {
                return false;
            }
            if (c.contains(array[read]) != complement) {
                break;
            }
        }
        int write = read++;
        for (; read < end; read++) {
            E element = array[read];
            if (c.contains(element) == complement) {
                array[write++] = element;
            }
        }
        modCount += end - write;
        System.arraycopy(array, end, array, write, size - end);
        for (int to = size, i = (size -= end - write); i < to; i++) {
            array[i] = null;
        }
        return true;
    }

    private void ensureCapacity(int minCapacity) {
        modCount++;
        if (minCapacity - array.length > 0) {
            int oldCapacity = array.length;
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            if (newCapacity - minCapacity < 0) {
                newCapacity = minCapacity;
            }
            if (newCapacity - MAX_ARRAY_SIZE > 0) {
                if (minCapacity < 0) {
                    throw new OutOfMemoryError();
                }
                newCapacity = (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
            }
            array = Arrays.copyOf(array, newCapacity);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException(String.valueOf(index));
    }
}

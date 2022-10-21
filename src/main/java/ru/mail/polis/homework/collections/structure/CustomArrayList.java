package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.Collection;
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

    private static final int INITIAL_SIZE = 16;

    private static void checkIndex(int index, int upperBound) {
        if (index < 0 || index > upperBound) {
            throw new IndexOutOfBoundsException();
        }
    }

    private E[] list;
    private int size;

    public CustomArrayList() {
        size = 0;
        list = (E[]) new Object[INITIAL_SIZE];
    }

    private void checkIndex(int index) {
        checkIndex(index, size - 1);
    }

    private void growLazy() {
        growLazy(1);
    }

    private void growLazy(int count) {
        if (size + count > list.length) {
            grow(size + count - list.length);
        }
    }

    private void grow(int minCount) {
        list = Arrays.copyOf(
                list,
                list.length + Math.max(list.length >> 1, minCount)
        );
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
        return new CustomListIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(list, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(list, size, a.getClass());
        }
        for (int i = 0; i < size; i++) {
            a[i] = (T)list[i];
        }
        return a;
    }

    @Override
    public boolean add(E e) {
        growLazy();
        list[size++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int oIndex = indexOf(o);
        if (oIndex >= 0) {
            remove(oIndex);
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }
        growLazy(c.size());
        for (E element : c) {
            list[size++] = element;
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkIndex(index, size);
        if (c.isEmpty()) {
            return false;
        }
        growLazy(c.size());
        System.arraycopy(list, index, list, index + c.size(), size - index);
        size += c.size();
        int i = index;
        for (E element : c) {
            list[i++] = element;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        for (Object element : c) {
            changed |= remove(element);
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(list[i])) {
                remove(i--);
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public void clear() {
        Arrays.fill(list, null);
        size = 0;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return list[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        E oldValue = list[index];
        list[index] = element;
        return oldValue;
    }

    @Override
    public void add(int index, E element) {
        checkIndex(index, size);
        growLazy();
        System.arraycopy(list, index, list, index + 1, size - index);
        list[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        E oldValue = list[index];
        System.arraycopy(list, index + 1, list, index, size - index - 1);
        size--;
        return oldValue;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (list[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (list[i].equals(o)) {
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
        List<E> resultList = new CustomArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            resultList.add(get(i));
        }
        return resultList;
    }

    private class CustomListIterator implements ListIterator<E> {

        private int currentIndex;
        private final int fixedSize = size;

        public CustomListIterator() {
            this(-1);
        }

        public CustomListIterator(int fromInclusive) {
            currentIndex = fromInclusive;
        }

        @Override
        public boolean hasNext() {
            checkSize();
            return currentIndex < size - 1;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return list[++currentIndex];
        }

        @Override
        public boolean hasPrevious() {
            checkSize();
            return currentIndex > 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            return list[--currentIndex];
        }

        @Override
        public int nextIndex() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return currentIndex + 1;
        }

        @Override
        public int previousIndex() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            return currentIndex - 1;
        }

        @Override
        public void remove() {
            checkSize();
            CustomArrayList.this.remove(currentIndex);
        }

        @Override
        public void set(E e) {
            checkSize();
            CustomArrayList.this.set(currentIndex, e);
        }

        @Override
        public void add(E e) {
            checkSize();
            CustomArrayList.this.add(currentIndex, e);
        }
        private void checkSize() {
            if (fixedSize != size) {
                throw new UnsupportedOperationException();
            }
        }
    }
}
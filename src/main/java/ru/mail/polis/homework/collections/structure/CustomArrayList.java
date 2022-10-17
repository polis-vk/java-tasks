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

    private static final int INITIAL_SIZE = 16;
    private static final int EXPANSION_COEFFICIENT = 2;
    private Object[] list;
    private int size;

    public CustomArrayList() {
        size = 0;
        list = new Object[INITIAL_SIZE];
    }

    private void checkIndex(int index) {
        checkIndex(index, size - 1);
    }

    private void checkIndex(int index, int upperBound) {
        if (index < 0 || index > upperBound) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void boostIfNecessary() {
        boostIfNecessary(1);
    }

    private void boostIfNecessary(int elementsCount) {
        while (size + elementsCount > list.length) {
            list = Arrays.copyOf(list, list.length * EXPANSION_COEFFICIENT);
        }
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
        for (int i = 0; i < size; i++) {
            if (list[i].equals(o)) {
                return true;
            }
        }
        return false;
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
        boostIfNecessary();
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
        boolean changed = false;
        for (E element : c) {
            changed |= add(element);
        }
        return changed;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkIndex(index, size);
        boostIfNecessary(c.size());
        for (int i = index + c.size(); i < size + c.size(); i++) {
            list[i] = list[i - c.size()];
        }
        int i = index;
        for (E element : c) {
            list[i] = element;
            i++;
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
        return (E)list[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        Object oldValue = list[index];
        list[index] = element;
        return (E)oldValue;
    }

    @Override
    public void add(int index, E element) {
        checkIndex(index, size);
        boostIfNecessary();
        for (int i = size; i >= index + 1; i--) {
            list[i] = list[i - 1];
        }
        list[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        E oldValue = (E)list[index];
        for (int i = index; i < size - 1; i++) {
            list[i] = list[i + 1];
        }
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
            return (E)list[++currentIndex];
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
            return (E)list[--currentIndex];
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

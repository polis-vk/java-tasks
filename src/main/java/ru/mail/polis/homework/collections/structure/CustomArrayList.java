package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
    private static final int DEFAULT_CAPACITY = 15;
    private static final double GOLD_RATIO = 1.618D;

    private E[] data;
    private int size;
    private int modifications;

    public CustomArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public CustomArrayList(int capacity) {
        if (capacity <= 0) {
            throw new IndexOutOfBoundsException();
        }
        data = (E[]) new Object[capacity];
    }

    public CustomArrayList(Collection<? extends E> collection) {
        data = (E[]) collection.toArray();
        size = collection.size();
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
        return indexOf(o) != -1;
    }

    @Override
    public Iterator<E> iterator() {
        return listIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(data, size, a.getClass());
        }
        System.arraycopy(data, 0, a, 0, size);
        return a;
    }

    @Override
    public boolean add(E e) {
        add(size, e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int position = indexOf(o);
        if (position == -1) {
            return false;
        }
        remove(position);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c) {
            if (!contains(item)) {
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
        if (c == null || c.isEmpty()) {
            return false;
        }
        checkOutOfBoundException(index);
        int newCapacity = size + c.size();
        ensureCapacity(newCapacity);
        System.arraycopy(data, index, data, index + c.size(), size - index);
        System.arraycopy(c.toArray(), 0, data, index, c.size());
        size += c.size();
        modifications++;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean oneOrMoreElementsHaveBeenDeleted = false;
        for (int i = 0; i < size; i++) {
            if (c.contains(data[i])) {
                oneOrMoreElementsHaveBeenDeleted = true;
                remove(i);
                i--;
            }
        }
        modifications += oneOrMoreElementsHaveBeenDeleted ? 1 : 0;
        return oneOrMoreElementsHaveBeenDeleted;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean oneOrMoreElementsHaveBeenRetained = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(data[i])) {
                oneOrMoreElementsHaveBeenRetained = true;
                remove(i);
                i--;
            }
        }
        modifications += oneOrMoreElementsHaveBeenRetained ? 1 : 0;
        return oneOrMoreElementsHaveBeenRetained;
    }

    @Override
    public void clear() {
        Arrays.fill(data, null);
        size = 0;
        modifications++;
    }

    @Override
    public E get(int index) {
        checkOutOfBoundException(index);
        return data[index];
    }

    @Override
    public E set(int index, E element) {
        checkOutOfBoundException(index);
        E previousElement = data[index];
        data[index] = element;
        modifications++;
        return previousElement;
    }

    @Override
    public void add(int index, E element) {
        checkOutOfBoundException(index);
        ensureCapacity(size + 1);
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        modifications++;
        size++;
    }

    @Override
    public E remove(int index) {
        checkOutOfBoundException(index);
        E previousElement = data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        data[--size] = null;
        modifications++;
        return previousElement;
    }

    @Override
    public int indexOf(Object o) {
        return indexOfInRange(o, 0, size);
    }

    @Override
    public int lastIndexOf(Object o) {
        return lastIndexOfInRange(o, 0, size);
    }

    @Override
    public ListIterator<E> listIterator() {
        return new CustomListIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        checkOutOfBoundException(index);
        return new CustomListIterator(index);
    }

    private class CustomListIterator implements ListIterator<E> {
        private static final int DEFAULT_STARTING_INDEX = 0;

        private int modificationsCounter = modifications;
        private int currentIndex;

        public CustomListIterator() {
            this(DEFAULT_STARTING_INDEX);
        }

        public CustomListIterator(int startIndex) {
            currentIndex = startIndex;
        }

        @Override
        public boolean hasNext() {
            checkModificationsCount();
            return currentIndex < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return data[currentIndex++];
        }

        @Override
        public boolean hasPrevious() {
            checkModificationsCount();
            return currentIndex >= 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            return data[--currentIndex];
        }

        @Override
        public int nextIndex() {
            checkModificationsCount();
            return currentIndex;
        }

        @Override
        public int previousIndex() {
            checkModificationsCount();
            return currentIndex - 1;
        }

        @Override
        public void remove() {
            checkModificationsCount();
            CustomArrayList.this.remove(currentIndex);
            modificationsCounter = modifications;
        }

        @Override
        public void set(E e) {
            checkModificationsCount();
            CustomArrayList.this.set(currentIndex, e);
            modificationsCounter = modifications;
        }

        @Override
        public void add(E e) {
            checkModificationsCount();
            CustomArrayList.this.add(currentIndex, e);
            modificationsCounter = modifications;
            currentIndex++;
        }

        private void checkModificationsCount() {
            if (modifications != modificationsCounter) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        if (fromIndex == toIndex) {
            return Collections.emptyList();
        }
        return new CustomSubList(fromIndex, toIndex);
    }

    private class CustomSubList implements List<E> {
        private final int startIndex;
        private int size;

        public CustomSubList(int fromIndex, int toIndex) {
            this.startIndex = fromIndex;
            this.size = toIndex - fromIndex;
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
            int position = indexOf(o);
            return position >= startIndex && position < startIndex + size;
        }

        @Override
        public Iterator<E> iterator() {
            return listIterator();
        }

        @Override
        public Object[] toArray() {
            return Arrays.copyOfRange(data, startIndex, startIndex + size);
        }

        @Override
        public <T> T[] toArray(T[] a) {
            if (a.length < size) {
                return (T[]) Arrays.copyOfRange(data, startIndex, startIndex + size, a.getClass());
            }
            System.arraycopy(data, startIndex, a, 0, size);
            return a;
        }

        @Override
        public boolean add(E e) {
            add(size, e);
            return true;
        }

        @Override
        public boolean remove(Object o) {
            int position = indexOf(o);
            if (!isIndexInCorrectRange(position)) {
                return false;
            }
            remove(position);
            return true;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            for (Object item : c) {
                if (!contains(item)) {
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
            checkOutOfBoundException(index);
            CustomArrayList.this.addAll(startIndex + index, c);
            size += c.size();
            return true;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            boolean oneOrMoreElementsHaveBeenDeleted = false;
            for (int i = startIndex; i < startIndex + size; i++) {
                if (c.contains(data[i])) {
                    oneOrMoreElementsHaveBeenDeleted = true;
                    remove(i);
                    i--;
                }
            }
            modifications += oneOrMoreElementsHaveBeenDeleted ? 1 : 0;
            return oneOrMoreElementsHaveBeenDeleted;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            boolean oneOrMoreElementsHaveBeenRetained = false;
            for (int i = startIndex; i < startIndex + size; i++) {
                if (!c.contains(data[i])) {
                    oneOrMoreElementsHaveBeenRetained = true;
                    remove(i);
                    i--;
                }
            }
            modifications += oneOrMoreElementsHaveBeenRetained ? 1 : 0;
            return oneOrMoreElementsHaveBeenRetained;
        }

        @Override
        public void clear() {
            Arrays.fill(data, startIndex, startIndex + size, null);
            CustomArrayList.this.size -= size;
            size = 0;
            modifications++;
        }

        @Override
        public E get(int index) {
            checkOutOfBoundException(index);
            return CustomArrayList.this.get(startIndex + index);
        }

        @Override
        public E set(int index, E element) {
            checkOutOfBoundException(index);
            return CustomArrayList.this.set(startIndex + index, element);
        }

        @Override
        public void add(int index, E element) {
            checkOutOfBoundException(index);
            CustomArrayList.this.add(startIndex + index, element);
            size++;
        }

        @Override
        public E remove(int index) {
            checkOutOfBoundException(index);
            E previousElement = CustomArrayList.this.remove(startIndex + index);
            size--;
            return previousElement;
        }

        @Override
        public int indexOf(Object o) {
            return indexOfInRange(o, startIndex, startIndex + size);
        }

        @Override
        public int lastIndexOf(Object o) {
            return lastIndexOfInRange(o, startIndex, startIndex + size);
        }

        @Override
        public ListIterator<E> listIterator() {
            return new CustomSubListIterator();
        }

        @Override
        public ListIterator<E> listIterator(int index) {
            checkOutOfBoundException(index);
            return new CustomSubListIterator(index);
        }

        private class CustomSubListIterator implements ListIterator<E> {
            private int modificationsCounter = modifications;
            private int currentIndex;

            public CustomSubListIterator() {
                this(startIndex);
            }

            public CustomSubListIterator(int startIndex) {
                currentIndex = startIndex;
            }

            @Override
            public boolean hasNext() {
                checkModificationsCount();
                return currentIndex < startIndex + size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return data[currentIndex++];
            }

            @Override
            public boolean hasPrevious() {
                checkModificationsCount();
                return currentIndex >= startIndex;
            }

            @Override
            public E previous() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                return data[--currentIndex];
            }

            @Override
            public int nextIndex() {
                checkModificationsCount();
                return currentIndex;
            }

            @Override
            public int previousIndex() {
                checkModificationsCount();
                return currentIndex - 1;
            }

            @Override
            public void remove() {
                checkModificationsCount();
                CustomArrayList.this.remove(currentIndex);
                modificationsCounter = modifications;
            }

            @Override
            public void set(E e) {
                checkModificationsCount();
                CustomArrayList.this.set(currentIndex, e);
                modificationsCounter = modifications;
            }

            @Override
            public void add(E e) {
                checkModificationsCount();
                CustomArrayList.this.add(currentIndex, e);
                modificationsCounter = modifications;
                currentIndex++;
            }

            private void checkModificationsCount() {
                if (modifications != modificationsCounter) {
                    throw new ConcurrentModificationException();
                }
            }
        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            if (fromIndex < startIndex || toIndex > startIndex + size || fromIndex > toIndex) {
                throw new IndexOutOfBoundsException();
            }
            if (fromIndex == toIndex) {
                return Collections.emptyList();
            }
            return CustomArrayList.this.subList(startIndex + fromIndex, startIndex + toIndex);
        }

        private void checkOutOfBoundException(int index) {
            if (!isIndexInCorrectRange(index)) {
                throw new IndexOutOfBoundsException();
            }
        }

        private boolean isIndexInCorrectRange(int index) {
            return index >= startIndex && index < startIndex + size;
        }
    }

    private int indexOfInRange(Object o, int fromIndex, int toIndex) {
        int index = -1;
        for (int i = fromIndex; i < toIndex; i++) {
            if (Objects.equals(o, data[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    private int lastIndexOfInRange(Object o, int fromIndex, int toIndex) {
        int index = -1;
        for (int i = toIndex - 1; i >= fromIndex; i--) {
            if (Objects.equals(o, data[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity <= data.length) {
            return;
        }
        int newCapacity = (int) (minCapacity * GOLD_RATIO);
        E[] newArray = (E[]) new Object[newCapacity];
        System.arraycopy(data, 0, newArray, 0, size);
        data = newArray;
    }

    private void checkOutOfBoundException(int index) {
        if (index < 0 || index >= data.length) {
            throw new IndexOutOfBoundsException();
        }
    }
}

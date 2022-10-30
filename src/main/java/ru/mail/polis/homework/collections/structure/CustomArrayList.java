package ru.mail.polis.homework.collections.structure;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 * <p>
 * Задание оценивается в 10 тугриков
 */
@SuppressWarnings("unchecked")
public class CustomArrayList<E> implements List<E> {
    private static final int INIT_SIZE = 16;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private int size;
    private int modCount;
    private E[] array;

    public CustomArrayList() {
        size = 0;
        array = (E[]) new Object[INIT_SIZE];
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
        return new CustomIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] resArray = new Object[size];
        System.arraycopy(array, 0, resArray, 0, size);
        return resArray;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(array, size, a.getClass());
        }
        System.arraycopy(array, 0, a, 0, size);
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
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return c.stream().allMatch(this::contains);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addAll(size, c);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c == null) {
            return false;
        } else if (c.isEmpty()) {
            return true;
        }
        int colSize = c.size();
        ensureCapacity(colSize - 1);
        checkIndex(index, true);
        size += colSize;
        if (size - (index + colSize) < 0) {
            return false;
        }
        System.arraycopy(array, index + colSize - colSize, array, index + colSize, size - (index + colSize));
        for (E element : c) {
            array[index++] = element;
        }
        modCount++;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean wasRemoved = false;
        for (int i = 0; i < size; i++) {
            if (c.contains(array[i])) {
                remove(i--);
                wasRemoved = true;
            }
        }
        return wasRemoved;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean wasRemoved = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(array[i])) {
                remove(i--);
                wasRemoved = true;
            }
        }
        return wasRemoved;
    }

    @Override
    public void clear() {
        size = 0;
        modCount++;
        Arrays.fill(array, null);
    }

    @Override
    public E get(int index) {
        checkIndex(index, false);
        return array[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index, false);
        E prevValue = array[index];
        array[index] = element;
        modCount++;
        return prevValue;
    }

    @Override
    public void add(int index, E element) {
        ensureCapacity(0);
        checkIndex(index, true);
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
        modCount++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index, false);
        modCount++;
        E element = array[index];
        System.arraycopy(array, index + 1, array, index, size - (index + 1));
        size--;
        return element;
    }

    @Override
    public int indexOf(Object o) {
        return indexOfRange(o, 0, size);
    }

    @Override
    public int lastIndexOf(Object o) {
        return lastIndexOfRange(o, 0, size);
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
        if (fromIndex >= toIndex) {
            throw new IllegalArgumentException();
        }
        checkIndex(fromIndex, false);
        checkIndex(toIndex, true);
        return new CustomSubList(fromIndex, toIndex, this);
    }

    private class CustomIterator implements Iterator<E> {
        protected int fixedModCount = modCount;
        protected int currentIndex;
        protected int lastRet = -1;

        public CustomIterator() {
            this.currentIndex = 0;
        }

        public CustomIterator(int startIndex) {
            this.currentIndex = startIndex;
        }

        @Override
        public boolean hasNext() {
            checkModCount();
            return currentIndex < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastRet = currentIndex;
            return array[currentIndex++];
        }

        protected void checkModCount() {
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private class CustomListIterator extends CustomIterator implements ListIterator<E> {
        private final int startIndex;

        public CustomListIterator() {
            super(0);
            this.startIndex = 0;
        }

        public CustomListIterator(int startIndex) {
            super(startIndex);
            this.startIndex = startIndex;
        }

        @Override
        public boolean hasPrevious() {
            checkModCount();
            return currentIndex > startIndex;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            lastRet = currentIndex;
            return array[--currentIndex];
        }

        @Override
        public int nextIndex() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return currentIndex;
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
            if (lastRet == -1) {
                throw new IllegalStateException();
            }
            checkModCount();
            CustomArrayList.this.remove(currentIndex);
            currentIndex = lastRet;
            lastRet = -1;
            fixedModCount = CustomArrayList.this.modCount;
        }

        @Override
        public void set(E e) {
            if (lastRet == -1) {
                throw new IllegalStateException();
            }
            checkModCount();
            CustomArrayList.this.set(lastRet, e);
            lastRet = -1;
            fixedModCount = CustomArrayList.this.modCount;
        }

        @Override
        public void add(E e) {
            checkModCount();
            lastRet = -1;
            CustomArrayList.this.add(currentIndex++, e);
            fixedModCount = CustomArrayList.this.modCount;
        }
    }

    private class CustomSubList extends CustomArrayList<E> {
        private final CustomArrayList<E> originalList;
        private final int fixedModCount;
        private final int fromInclusive;

        private int size;
        private int modCount;

        public CustomSubList(int fromInclusive, int toExclusive, CustomArrayList<E> originalList) {
            this.originalList = originalList;
            this.fromInclusive = fromInclusive;
            this.size = toExclusive - fromInclusive;
            this.fixedModCount = originalList.modCount;
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
            checkSubListModCount();
            return indexOf(o) != -1;
        }

        @Override
        public Iterator<E> iterator() {
            return listIterator();
        }

        @Override
        public Object[] toArray() {
            checkSubListModCount();
            Object[] resArray = new Object[size];
            System.arraycopy(array, fromInclusive, resArray, 0, size);
            return resArray;
        }

        @Override
        public <T> T[] toArray(T[] ts) {
            checkSubListModCount();
            if (ts.length < size) {
                ts = (T[]) new Comparable[size];
            }
            System.arraycopy(array, fromInclusive, ts, 0, size);
            return ts;
        }

        @Override
        public boolean add(E e) {
            add(fromInclusive + size, e);
            return true;
        }

        @Override
        public boolean remove(Object o) {
            int index = indexOf(o);
            if (index == -1) {
                return false;
            }
            originalList.remove(fromInclusive + index);
            size--;
            modCount = originalList.modCount;
            return true;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return collection.stream().allMatch(this::contains);
        }

        @Override
        public boolean addAll(Collection<? extends E> collection) {
            return addAll(size, collection);
        }

        @Override
        public boolean addAll(int index, Collection<? extends E> collection) {
            checkSubListModCount();
            checkSubListIndex(index, true);
            originalList.addAll(fromInclusive + index, collection);
            size += collection.size();
            modCount = originalList.modCount;
            return true;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            boolean wasRemoved = false;
            for (int i = 0; i < size; i++) {
                if (collection.contains(get(i))) {
                    remove(i--);
                    wasRemoved = true;
                }
            }
            return wasRemoved;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            boolean wasRemoved = false;
            for (int i = 0; i < size; i++) {
                if (!collection.contains(get(i))) {
                    remove(i--);
                    wasRemoved = true;
                }
            }
            return wasRemoved;
        }

        @Override
        public void clear() {
            System.arraycopy(array, fromInclusive + size, array, fromInclusive,
                    originalList.size - (fromInclusive + size));
            Arrays.fill(array, originalList.size - size, originalList.size, null);
            originalList.size -= size;
            size = 0;
            modCount++;
        }

        @Override
        public E get(int index) {
            checkSubListModCount();
            checkSubListIndex(index, false);
            return array[fromInclusive + index];
        }

        @Override
        public E set(int index, E element) {
            checkSubListModCount();
            checkSubListIndex(index, false);
            E prevValue = array[fromInclusive + index];
            array[fromInclusive + index] = element;
            modCount++;
            return prevValue;
        }

        @Override
        public void add(int index, E e) {
            checkSubListModCount();
            checkSubListIndex(index, true);
            originalList.add(fromInclusive + index, e);
            size++;
            modCount = originalList.modCount;
        }

        @Override
        public E remove(int index) {
            checkSubListIndex(index, false);
            checkSubListModCount();
            E element = originalList.remove(fromInclusive + index);
            size--;
            modCount = originalList.modCount;
            return element;
        }

        @Override
        public int indexOf(Object o) {
            checkSubListModCount();
            int index = indexOfRange(o, fromInclusive, fromInclusive + size);
            return (index != -1) ? fromInclusive + index : -1;
        }

        @Override
        public int lastIndexOf(Object o) {
            checkSubListModCount();
            int index = lastIndexOfRange(o, fromInclusive, fromInclusive + size);
            return (index != -1) ? fromInclusive + index : -1;
        }

        @Override
        public ListIterator<E> listIterator() {
            return listIterator(fromInclusive);
        }

        @Override
        public ListIterator<E> listIterator(int index) {
            checkSubListIndex(index, false);
            checkSubListModCount();
            return new ListIterator<E>() {
                private final int startIndex = fromInclusive + index;

                private int fixedModCount = modCount;
                private int currentIndex = startIndex;
                private int lastRet = -1;

                @Override
                public boolean hasNext() {
                    checkModCount();
                    return currentIndex < size;
                }

                @Override
                public E next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    lastRet = currentIndex;
                    return array[currentIndex++];
                }

                @Override
                public boolean hasPrevious() {
                    checkModCount();
                    return currentIndex > startIndex;
                }

                @Override
                public E previous() {
                    if (!hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    lastRet = currentIndex;
                    return array[--currentIndex];
                }

                @Override
                public int nextIndex() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return currentIndex;
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
                    if (lastRet == -1) {
                        throw new IllegalStateException();
                    }
                    checkModCount();
                    CustomArrayList.this.remove(lastRet);
                    currentIndex = lastRet;
                    lastRet = -1;
                    fixedModCount = CustomArrayList.this.modCount;
                }

                @Override
                public void set(E e) {
                    if (lastRet == -1) {
                        throw new IllegalStateException();
                    }
                    checkModCount();
                    CustomArrayList.this.set(lastRet, e);
                    lastRet = -1;
                    fixedModCount = CustomArrayList.this.modCount;
                }

                @Override
                public void add(E e) {
                    checkModCount();
                    lastRet = -1;
                    CustomArrayList.this.add(currentIndex++, e);
                    fixedModCount = CustomArrayList.this.modCount;
                }

                private void checkModCount() {
                    if (fixedModCount != modCount) {
                        throw new ConcurrentModificationException();
                    }
                }
            };
        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            if (fromIndex >= toIndex) {
                throw new IllegalArgumentException();
            }
            checkSubListIndex(fromIndex, false);
            checkSubListIndex(toIndex, true);
            return new CustomSubList(fromInclusive + fromIndex,
                    fromInclusive + toIndex, this);
        }

        private void checkSubListIndex(int index, boolean inclusive) {
            if (!(0 <= index && index < size + (inclusive ? 1 : 0))) {
                throw new IndexOutOfBoundsException();
            }
        }

        protected void checkSubListModCount() {
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private void ensureCapacity(int index) {
        while (array.length - size <= index) {
            long newLength = ((long) array.length * 3 / 2) + 1;
            if (newLength > MAX_ARRAY_SIZE) {
                throw new OutOfMemoryError();
            }
            array = Arrays.copyOf(array, (array.length * 3 / 2) + 1);
        }
    }

    private void checkIndex(int index, boolean inclusive) {
        if (!(0 <= index && index < size + (inclusive ? 1 : 0))) {
            throw new IndexOutOfBoundsException();
        }
    }

    public int indexOfRange(Object o, int fromInclusive, int toExclusive) {
        for (int i = fromInclusive; i < toExclusive; i++) {
            if ((array[i] != null && array[i].equals(o)) || (array[i] == null && o == null)) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexOfRange(Object o, int fromInclusive, int toExclusive) {
        for (int i = toExclusive - 1; i >= fromInclusive; i--) {
            if ((array[i] != null && array[i].equals(o) || (array[i] == null && o == null))) {
                return i;
            }
        }
        return -1;
    }
}

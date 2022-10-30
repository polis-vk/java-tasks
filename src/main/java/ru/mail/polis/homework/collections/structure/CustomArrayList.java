package ru.mail.polis.homework.collections.structure;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.ConcurrentModificationException;
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
    private static final int DEFAULT_ARRAY_CAPACITY = 10;
    private int size;
    private int modCount;
    private E[] arr;

    @SuppressWarnings("unchecked")
    public CustomArrayList() {
        arr = (E[]) new Object[DEFAULT_ARRAY_CAPACITY];
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
        return containsInRange(0, size, o);
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomListIterator(0, size, 0);
    }

    @Override
    public Object[] toArray() {
        return toArrayInRange(0, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return toArrayInRange(a, 0, size);
    }

    @Override
    public boolean add(E e) {
        add(size, e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return removeInRange(0, size, o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return containsAllInRange(0, size, c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkIndex(0, size + 1, index);
        if (c.size() == 0) {
            return false;
        } else if (arr.length < size + c.size()) {
            resize(size + c.size());
        }
        System.arraycopy(arr, index, arr, index + c.size(), size - index);
        System.arraycopy(c.toArray(), 0, arr, index, c.size());
        updateSizeAndModCount(c.size());
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return removeAllInRange(0, size, c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return retainAllInRange(0, size, c);
    }

    @Override
    public void clear() {
        clearRange(0, size - 1);
    }

    @Override
    public E get(int index) {
        checkIndex(0, size, index);
        return arr[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndex(0, size, index);
        E oldEl = arr[index];
        arr[index] = element;
        modCount++;
        return oldEl;
    }

    @Override
    public void add(int index, E element) {
        checkIndex(0, size + 1, index);
        if (size == arr.length) {
            resize();
        }
        System.arraycopy(arr, index, arr, index + 1, size - index);
        arr[index] = element;
        updateSizeAndModCount(1);
    }

    @Override
    public E remove(int index) {
        checkIndex(0, size, index);
        E oldEl = arr[index];
        System.arraycopy(arr, index + 1, arr, index, size - index - 1);
        updateSizeAndModCount(-1);
        return oldEl;
    }

    @Override
    public int indexOf(Object o) {
        return indexOfInRange(0, size, o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return lastIndexOfInRange(0, size, o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new CustomListIterator(0, size, index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        checkSubListRange(0, size, fromIndex, toIndex);
        return new SubList(fromIndex, toIndex);
    }

    private class CustomListIterator implements ListIterator<E> {
        boolean nextWasCalled;
        boolean previousWasCalled;
        boolean removeWasCalled;
        boolean addWasCalled;
        int iteratorModCount;
        int nextI;
        final int from;
        int to;

        public CustomListIterator(int fromIndex, int toIndex, int index) {
            from = fromIndex;
            to = toIndex;
            nextI = index;
            iteratorModCount = CustomArrayList.this.modCount;
        }

        @Override
        public boolean hasNext() {
            return nextI < to;
        }

        @Override
        public E next() {
            checkModCount();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            previousWasCalled = false;
            nextWasCalled = true;
            addWasCalled = false;
            removeWasCalled = false;
            return arr[nextI++];
        }

        @Override
        public boolean hasPrevious() {
            return nextI - 1 >= from;
        }

        @Override
        public E previous() {
            checkModCount();
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            previousWasCalled = true;
            nextWasCalled = false;
            addWasCalled = false;
            removeWasCalled = false;
            return arr[nextI-- - 1];
        }

        @Override
        public int nextIndex() {
            return nextI;
        }

        @Override
        public int previousIndex() {
            return nextI - 1;
        }

        @Override
        public void remove() {
            checkModCount();
            checkState();
            if (nextWasCalled) {
                CustomArrayList.this.remove(nextIndex() - 1);
            } else {
                CustomArrayList.this.remove(previousIndex() + 1);
            }
            removeWasCalled = true;
            to--;
            iteratorModCount++;
        }

        @Override
        public void set(E e) {
            checkModCount();
            checkState();
            if (nextWasCalled) {
                CustomArrayList.this.set(nextIndex() - 1, e);
            } else {
                CustomArrayList.this.set(previousIndex() + 1, e);
            }
            iteratorModCount++;
        }

        @Override
        public void add(E e) {
            checkModCount();
            CustomArrayList.this.add(nextI++, e);
            addWasCalled = true;
            to++;
            iteratorModCount++;
        }

        private void checkModCount() {
            if (iteratorModCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }

        private void checkState() {
            if ((!nextWasCalled && !previousWasCalled) ||
                    addWasCalled || removeWasCalled) {
                throw new IllegalStateException();
            }
        }
    }

    private class SubList implements List<E> {
        final int from;
        int to;

        public SubList(int fromIndex, int toIndex) {
            from = fromIndex;
            to = toIndex;
        }

        @Override
        public int size() {
            return to - from;
        }

        @Override
        public boolean isEmpty() {
            return size() == 0;
        }

        @Override
        public boolean contains(Object o) {
            return containsInRange(from, to, 0);
        }

        @Override
        public Iterator<E> iterator() {
            return new CustomListIterator(from, to, from);
        }

        @Override
        public Object[] toArray() {
            return CustomArrayList.this.toArrayInRange(from, to);
        }

        @Override
        public <T> T[] toArray(T[] ts) {
            return CustomArrayList.this.toArrayInRange(ts, from, to);
        }

        @Override
        public boolean add(E e) { CustomArrayList.this.add(to++, e);
            return true;
        }

        @Override
        public boolean remove(Object o) {
            return CustomArrayList.this.removeInRange(from, to, o);
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return containsAllInRange(from, to, collection);
        }

        @Override
        public boolean addAll(Collection<? extends E> collection) {
            return addAll(to, collection);
        }

        @Override
        public boolean addAll(int i, Collection<? extends E> collection) {
            checkIndex(from, to + 1, i);
            return CustomArrayList.this.addAll(from + i, collection);
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return CustomArrayList.this.removeAllInRange(from, to, collection);
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return CustomArrayList.this.retainAllInRange(from, to, collection);
        }

        @Override
        public void clear() { CustomArrayList.this.clearRange(from, to);
            to = from;
        }

        @Override
        public E get(int i) {
            checkIndex(from, to, i);
            return CustomArrayList.this.get(from + i);
        }

        @Override
        public E set(int i, E e) {
            checkIndex(from, to, i);
            return CustomArrayList.this.set(from + i, e);
        }

        @Override
        public void add(int i, E e) {
            checkIndex(from, to + 1, i); CustomArrayList.this.add(from + i, e);
            to++;
        }

        @Override
        public E remove(int i) {
            checkIndex(from, to, i);
            to--;
            return CustomArrayList.this.remove(from + i);
        }

        @Override
        public int indexOf(Object o) {
            return CustomArrayList.this.indexOfInRange(from, to, o);
        }

        @Override
        public int lastIndexOf(Object o) {
            return CustomArrayList.this.lastIndexOfInRange(from, to, o);
        }

        @Override
        public ListIterator<E> listIterator() {
            return listIterator(0);
        }

        @Override
        public ListIterator<E> listIterator(int i) {
            checkIndex(from, to, i);
            return new CustomListIterator(from, to, i);
        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            checkSubListRange(from, to, fromIndex, toIndex);
            return new SubList(fromIndex, toIndex);
        }
    }

    @SuppressWarnings("unchecked")
    private void resize(int newSize) {
        E[] oldArray = arr;
        arr = (E[]) new Object[newSize];
        System.arraycopy(oldArray, 0, arr, 0, size);
    }

    private void resize() {
        resize((int) Math.ceil(1.5 * arr.length));
    }

    private static void checkIndex(int fromIndex, int toIndex, int i) {
        if (i < fromIndex || i >= toIndex) {
            throw new IndexOutOfBoundsException();
        }
    }

    private static void checkSubListRange(int fromIndex, int toIndex, int newFromIndex, int newToIndex) {
        if (newFromIndex < fromIndex || newToIndex > toIndex || newFromIndex > newToIndex) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void clearRange(int from, int to) {
        int removeSize = to - from + 1;
        System.arraycopy(arr, to + 1, arr, from, size - removeSize);
        resize(size - removeSize + DEFAULT_ARRAY_CAPACITY);
        updateSizeAndModCount(-removeSize);
    }

    private void updateSizeAndModCount(int delta) {
        size += delta;
        modCount++;
    }

    private Object[] toArrayInRange(int from, int to) {
        Object[] copyArr = new Object[to - from];
        System.arraycopy(arr, from, copyArr, 0, copyArr.length);
        return copyArr;
    }

    @SuppressWarnings("unchecked")
    private <T> T[] toArrayInRange(T[] a, int from, int to) {
        int rangeSize = to - from;
        if (a.length < rangeSize) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), rangeSize);
        } else if (a.length > rangeSize) {
            a[rangeSize] = null;
        }
        System.arraycopy(arr, from, a, 0, rangeSize);
        return a;
    }

    private int indexOfInRange(int from, int to, Object o) {
        for (int i = from; i < to; i++) {
            if (arr[i] == o) {
                return i;
            } else if (arr[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    private int lastIndexOfInRange(int from, int to, Object o) {
        int lastInd = -1;
        for (int i = from; i < to; i++) {
            if (arr[i] == o) {
                lastInd = i;
            } else if (arr[i].equals(o)) {
                lastInd = i;
            }
        }
        return lastInd;
    }

    private boolean removeInRange(int from, int to, Object o) {
        int index = indexOfInRange(from, to, o);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    private boolean containsInRange(int from, int to, Object o) {
        return indexOfInRange(from, to, o) != -1;
    }

    private boolean containsAllInRange(int from, int to, Collection<?> c) {
        for (Object el : c) {
            if (!containsInRange(from, to, el)) {
                return false;
            }
        }
        return true;
    }

    private boolean removeAllInRange(int from, int to, Collection<?> c) {
        boolean wasChanged = true;
        for (Object el : c) {
            wasChanged &= removeInRange(from, to, el);
        }
        return wasChanged;
    }

    private boolean retainAllInRange(int from, int to, Collection<?> c) {
        boolean wasChanged = false;
        for (int i = from; i < to; ) {
            if (!c.contains(get(i))) {
                remove(i);
                wasChanged = true;
                to--;
            } else {
                i++;
            }
        }
        return wasChanged;
    }
}

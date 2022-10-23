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
    int size;
    int modCount;
    int capacity;
    E[] arr;

    @SuppressWarnings("unchecked")
    public CustomArrayList() {
        capacity = 10;
        arr = (E[]) new Object[capacity];
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
            if (arr[i] == o) {
                return true;
            } else if (arr[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomIterator(0, this);
    }

    @Override
    public Object[] toArray() {
        return toArrayRange(0, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return toArrayRange(a, 0, size);
    }

    @Override
    public boolean add(E e) {
        if (size == capacity) {
            resize();
        }
        arr[size] = e;
        updateSize(1);
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
        for (Object el : c) {
            if (!contains(el)) {
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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        } else if (c.size() == 0) {
            return false;
        } else if (capacity < size + c.size()) {
            resize(size + c.size());
        }
        System.arraycopy(arr, index, arr, index + c.size(), size - index);
        System.arraycopy(c.toArray(), 0, arr, index, c.size());
        updateSize(c.size());
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean wasChanged = true;
        for (Object el : c) {
            wasChanged &= remove(el);
        }
        return wasChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean wasChanged = false;
        for (int i = 0; i < size; ) {
            if (!c.contains(get(i))) {
                remove(i);
                wasChanged = true;
            } else {
                i++;
            }
        }
        return wasChanged;
    }

    @Override
    public void clear() {
        clearRange(0, size - 1);
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return arr[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        E oldEl = arr[index];
        arr[index] = element;
        modCount++;
        return oldEl;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == capacity) {
            resize();
        }
        System.arraycopy(arr, index, arr, index + 1, size - index);
        arr[index] = element;
        updateSize(1);
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        E oldEl = arr[index];
        System.arraycopy(arr, index + 1, arr, index, size - index - 1);
        updateSize(-1);
        return oldEl;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (arr[i] == o) {
                return i;
            } else if (arr[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int lastInd = -1;
        for (int i = 0; i < size; i++) {
            if (arr[i] == o) {
                lastInd = i;
            } else if (arr[i].equals(o)) {
                lastInd = i;
            }
        }
        return lastInd;
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListIterator<E>() {
            boolean nextWasCalled;
            boolean previousWasCalled;
            boolean removeWasCalled;
            boolean addWasCalled;
            int iteratorModCount = modCount;
            int nextI = index;

            @Override
            public boolean hasNext() {
                return nextI < size;
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
                return nextI - 1 >= 0;
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
        };
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        return new SubList(fromIndex, toIndex);
    }

    private class CustomIterator implements Iterator<E> {
        private final int fixedModCount = modCount;
        private int nextI;
        private final List<E> list;
        public CustomIterator(int index, List<E> iterableList) {
            list = iterableList;
            nextI = index;
        }

        @Override
        public boolean hasNext() {
            return nextI < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            } else if (fixedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            return list.get(nextI++);
        }
    }

    private class SubList implements List<E> {
        int size;
        int subModCount = modCount;
        int from;
        int to;

        public SubList(int fromIndex, int toIndex) {
            from = fromIndex;
            to = toIndex;
            size = to - from;
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
            return false;
        }

        @Override
        public Iterator<E> iterator() {
            return new CustomIterator(from, this);
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] ts) {
            return null;
        }

        @Override
        public boolean add(E e) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends E> collection) {
            return false;
        }

        @Override
        public boolean addAll(int i, Collection<? extends E> collection) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public E get(int i) {
            return null;
        }

        @Override
        public E set(int i, E e) {
            return null;
        }

        @Override
        public void add(int i, E e) {

        }

        @Override
        public E remove(int i) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<E> listIterator() {
            return null;
        }

        @Override
        public ListIterator<E> listIterator(int i) {
            return null;
        }

        @Override
        public List<E> subList(int i, int i1) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private void resize(int newSize) {
        E[] oldArray = arr;
        capacity = newSize;
        arr = (E[]) new Object[capacity];
        System.arraycopy(oldArray, 0, arr, 0, size);
    }

    private void resize() {
        resize((int) Math.ceil(1.5 * capacity));
    }

    private void checkIndex(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void clearRange(int from, int to) {
        int removeSize = to - from + 1;
        System.arraycopy(arr, to + 1, arr, from, size - removeSize);
        resize(size - removeSize + 10);
        updateSize(-removeSize);
    }

    private void updateSize(int delta) {
        size += delta;
        modCount++;
    }

    private Object[] toArrayRange(int from, int to) {
        Object[] copyArr = new Object[to - from];
        System.arraycopy(arr, from, copyArr, 0, copyArr.length);
        return copyArr;
    }

    @SuppressWarnings("unchecked")
    private <T> T[] toArrayRange(T[] a, int from, int to) {
        int rangeSize = to - from;
        if (a.length < rangeSize) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), rangeSize);
        } else if (a.length > rangeSize) {
            a[rangeSize] = null;
        }
        System.arraycopy(arr, from, a, 0, rangeSize);
        return a;
    }
}

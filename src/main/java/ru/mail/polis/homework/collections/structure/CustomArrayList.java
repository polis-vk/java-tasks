package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 * <p>
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    private E[] array;
    private int size;
    private int modCount = 0;

    public CustomArrayList() {
        this.array = (E[]) new Object[10];
    }

    public CustomArrayList(int capacity) {
        this.array = (E[]) new Object[capacity];
    }

    public CustomArrayList(E[] array) {
        this.array = (E[]) new Object[array.length];
        Collections.addAll(this, array);

    }

    public CustomArrayList(Collection<E> collection) {
        this.array = (E[]) new Object[collection.size()];
        this.addAll(collection);
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
        return new Iterator<E>() {
            private int curIndex;
            private int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                return curIndex < size;
            }

            @Override
            public E next() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                if (curIndex >= size) {
                    throw new NoSuchElementException();
                }
                E res = array[curIndex];
                curIndex++;
                return res;
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a == null) {
            throw new NullPointerException();
        }

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
        if (size == array.length) {
            increaseCapacity();
        }
        array[size] = e;
        size++;
        modCount++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!contains(o)) {
            return false;
        }

        int position = indexOf(o);
        if (position != size - 1) {
            System.arraycopy(array, position + 1, array, position, size - position - 1);
        }
        size--;
        modCount++;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            add(e);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkIndex(index);
        int cursor = index;
        for (E e : c) {
            add(cursor, e);
            cursor++;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        final int capacityBefore = size;
        for (Object element : c) {
            if (contains(element)) {
                remove(element);
            }
        }

        return capacityBefore != size;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        final int capacityBefore = size;
        for (int i = 0; i < size; i++) {
            if (!c.contains(array[i])) {
                remove(i);
                i--;
            }
        }

        return capacityBefore != size;

    }

    @Override
    public void clear() {
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
        return array[index] = element;
    }

    @Override
    public void add(int index, E element) {
        checkIndex(index);

        if (size >= array.length) {
            increaseCapacity();
        }

        size++;
        modCount++;
        System.arraycopy(array, index, array, index + 1, size - index - 1);
        array[index] = element;

    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        E res = array[index];
        if (index != size - 1) {
            System.arraycopy(array, index + 1, array, index, size - index - 1);
        }
        size--;
        modCount++;

        return res;
    }

    @Override
    public int indexOf(Object o) {
        int index = -1;
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (null == array[i]) {
                    index = i;
                    return index;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(array[i])) {
                    index = i;
                    return index;
                }
            }
        }
        return index;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }

        int index = -1;
        if (!contains(o)) {
            return index;
        }

        for (int i = size - 1; i >= 0; i--) {
            if (o.equals(array[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListItr(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListItr(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }

        List<E> subList = new CustomArrayList<>(10);
        for (int i = fromIndex; i <= toIndex; i++) {
            subList.add(get(i));
        }
        return subList;
    }

    private void increaseCapacity() {
        E[] temp = (E[]) new Object[array.length * 3 / 2];
        System.arraycopy(array, 0, temp, 0, size);
        array = temp;
    }

    private class ListItr implements ListIterator<E> {
        private int curIndex;
        private int lastReturned = -1;
        private int expectedModCount = modCount;

        ListItr(int index) {
            curIndex = index;
        }

        @Override
        public boolean hasPrevious() {
            return curIndex != 0;
        }

        @Override
        public int nextIndex() {
            return curIndex;
        }

        @Override
        public int previousIndex() {
            return curIndex - 1;
        }

        @Override
        public E previous() {
            checkForComodification();

            int i = curIndex - 1;
            if (i < 0) {
                throw new NoSuchElementException();
            }
            if (i >= size) {
                throw new ConcurrentModificationException();
            }
            curIndex = i;
            lastReturned = i;
            return array[i];
        }

        @Override
        public void remove() {
            if (lastReturned < 0) {
                throw new IllegalStateException();
            }

            checkForComodification();

            if (curIndex >= size) {
                throw new IndexOutOfBoundsException();
            }

            CustomArrayList.this.remove(lastReturned);
            curIndex = lastReturned;
            lastReturned = -1;
            expectedModCount = modCount;
        }

        @Override
        public void set(E e) {
            if (lastReturned < 0) {
                throw new IllegalStateException();
            }

            checkForComodification();

            CustomArrayList.this.set(lastReturned, e);
            expectedModCount = modCount;
        }

        @Override
        public void add(E e) {
            checkForComodification();

            if (curIndex >= size) {
                throw new IndexOutOfBoundsException();
            }

            int i = curIndex;
            CustomArrayList.this.add(i, e);
            curIndex = i + 1;
            lastReturned = -1;
            expectedModCount = modCount;
        }

        @Override
        public boolean hasNext() {
            return curIndex < size;
        }

        @Override
        public E next() {
            checkForComodification();

            if (curIndex >= size) {
                throw new NoSuchElementException();
            }
            lastReturned = curIndex;
            curIndex++;
            return array[lastReturned];
        }

        private void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }
}

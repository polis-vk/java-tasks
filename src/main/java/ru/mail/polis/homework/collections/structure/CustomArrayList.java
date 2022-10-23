package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 * <p>
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    private int size;
    private int modCount;
    private Object[] array = {};

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int pos;
            private final int fixedModCount = modCount;

            @Override
            public boolean hasNext() {
                return pos < size();
            }

            @Override
            @SuppressWarnings("unchecked")
            public E next() {
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                } else if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (E) array[pos++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(array, size, a.getClass());
        }
        System.arraycopy(array, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    @Override
    public boolean add(E e) {
        if (array.length >= size()) {
            array = Arrays.copyOf(array, (array.length + 1) * 2);
        }
        array[size++] = e;
        modCount++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int currIndex = indexOf(o);
        if (currIndex < 0) {
            return false;
        } else {
            System.arraycopy(array, currIndex + 1, array, currIndex, size - currIndex - 1);
        }
        array[--size] = null;
        modCount++;
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        int index = 0;
        for (Object elem : c) {
            if (index >= size) {
                return false;
            }
            while (index <= size && !elem.equals(array[index])) {
                index++;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        System.arraycopy(c.toArray(), 0, array, size, c.size());
        size += c.size();
        modCount++;
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c == null) {
            throw new NullPointerException();
        } else if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        System.arraycopy(array, 0, array, c.size(), size + c.size());
        System.arraycopy(c.toArray(), 0, array, index, c.size());
        size += c.size();
        modCount++;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        int index = 0;
        for (Object elem : c) {
            if (index >= size) {
                return false;
            }
            while (index < size && elem.equals(array[index])) {
                index++;
            }
            remove(--index);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        int index = 0;
        while (index < size) {
            Object currElem = array[index];
            if (!c.contains(currElem)) {
                remove(index);
            } else {
                index++;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        array = new Object[]{};
        size = 0;
        modCount++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return (E) array[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        E prevElem = (E) array[index];
        array[index] = element;
        return prevElem;
    }

    @Override
    public void add(int index, E element) {
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
        modCount++;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        array[--size] = null;
        modCount++;
        return null;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(array[i], o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (array[i].equals(o)) {
                index = i;
            }
        }
        return index;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new MyListIterator<>(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new MyListIterator<>(index);
    }

    @SuppressWarnings("unchecked")
    private class MyListIterator<T> implements ListIterator<T> {
        int pos;
        Object currElem;
        Object prevElem;
        int fixedModCount = modCount;
        boolean nextCallFlag = false;

        public MyListIterator(int index) {
            if (index < 0 || index > size()) {
                throw new IndexOutOfBoundsException();
            }
            pos = index;
        }

        @Override
        public boolean hasNext() {
            return pos < size;
        }

        @Override
        public T next() {
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException();
            } else if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (pos > 0) {
                prevElem = currElem;
            }
            nextCallFlag = true;
            currElem = get(pos++);
            return (T) currElem;
        }

        @Override
        public boolean hasPrevious() {
            return prevElem != null;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            return (T) prevElem;
        }

        @Override
        public int nextIndex() {
            if (pos == size) {
                return size;
            } else {
                return pos + 1;
            }
        }

        @Override
        public int previousIndex() {
            return pos - 1;
        }

        @Override
        public void remove() {
            if (!nextCallFlag) {
                throw new IllegalArgumentException();
            }
            CustomArrayList.this.remove(previousIndex());
            fixedModCount++;
            nextCallFlag = false;
        }

        @Override
        public void set(T e) {
            if (!nextCallFlag) {
                throw new IllegalArgumentException();
            }
            CustomArrayList.this.set(previousIndex(), (E) currElem);
            nextCallFlag = false;
        }

        @Override
        public void add(T e) {
            CustomArrayList.this.add(pos, (E) e);
            fixedModCount++;
            pos++;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> subList(int fromIndex, int toIndex) {
        CustomArrayList<E> list = new CustomArrayList<>();
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = fromIndex; i < toIndex - 1; i++) {
            list.add((E) array[i]);
        }
        return list;
    }
}

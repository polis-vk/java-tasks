package ru.mail.polis.homework.collections.structure;

import java.util.*;
import java.util.stream.Stream;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 * <p>
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    private E[] array;
    private int size;
    private int modCnt;

    private int initialCapacityArray = 10;

    public CustomArrayList() {
        array = (E[]) new Object[initialCapacityArray];
    }

    public CustomArrayList(int size) {
        initialCapacityArray = size;
        array = (E[]) new Object[initialCapacityArray];
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
        return new Iterator<E>() {
            private int finalModCount = modCnt;
            private int position;

            @Override
            public boolean hasNext() {
                return position < size;
            }

            @Override
            public E next() {
                if (modCnt != finalModCount) {
                    throw new ConcurrentModificationException();
                }

                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                return array[position++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] masObjects = new Object[size];
        System.arraycopy(array, 0, masObjects, 0, size);
        return masObjects;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(array, size, a.getClass());
        }

        T[] newArray = a;
        System.arraycopy(array, 0, newArray, 0, newArray.length);
        return newArray;
    }

    @Override
    public boolean add(E e) {
        increasingArraySize();
        array[size++] = e;
        modCnt++;
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
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addAll(size, c);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index != size) {
            checkIndex(index);
        }

        if (c.isEmpty()) {
            return false;
        }

        if (size + c.size() > array.length) {
            array = Arrays.copyOf(array, size + c.size());
        }

        System.arraycopy(array, index, array, index + c.size(), size - index);
        System.arraycopy(c.toArray(), 0, array, index, c.size());

        size += c.size();
        modCnt++;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c.isEmpty()) {
            return false;
        }

        for (Object element : c) {
            if (!remove(element)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c.isEmpty()) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (!c.contains(array[i])) {
                remove(i--);
            }
        }

        return true;
    }

    @Override
    public void clear() {
        modCnt++;
        Arrays.fill(array, null);
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
        modCnt++;
        E currentElement = array[index];
        array[index] = element;
        return array[index];
    }

    @Override
    public void add(int index, E element) {
        checkIndex(index);
        increasingArraySize();

        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        modCnt++;
        size++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        E removeValue = array[index];

        System.arraycopy(array, index + 1, array, index, size - index);

        size--;
        modCnt++;

        return removeValue;
    }

    @Override
    public int indexOf(Object o) {
        int index = (int) Arrays.stream(array)
                .limit(size)
                .takeWhile(element -> {
                    if (element == null && o == null) {
                        return false;
                    }

                    return !element.equals(o);
                }).count();

        return (index != size) ? index : -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int iterator = size - 1;

        for (int i = size - 1; i >= 0; i++) {
            if (array[i] == null && o == null) {
                return iterator;
            }

            if (array[i].equals(o)) {
                return iterator;
            }

            iterator--;
        }

        return 0;
    }


    @Override
    public ListIterator<E> listIterator() {
        return new CustomListIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new CustomListIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        checkIndex(fromIndex);
        checkIndex(toIndex);

        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }

        CustomArrayList<E> subList = new CustomArrayList<>(toIndex - fromIndex + 1);
        System.arraycopy(array, fromIndex, subList.array, 0, toIndex - fromIndex + 1);
        subList.size = toIndex - fromIndex + 1;
        return subList;
    }

    public class CustomListIterator implements ListIterator<E> {
        int currentPosition;

        public CustomListIterator(int index) {
            currentPosition = index;
        }

        @Override
        public boolean hasNext() {
            return currentPosition < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return array[++currentPosition];
        }

        @Override
        public boolean hasPrevious() {
            return currentPosition >= 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }

            return array[--currentPosition];
        }

        @Override
        public int nextIndex() {
            return (hasNext()) ? currentPosition + 1 : currentPosition;
        }

        @Override
        public int previousIndex() {
            return (hasPrevious()) ? currentPosition - 1 : currentPosition;
        }

        @Override
        public void remove() {
            CustomArrayList.this.remove(currentPosition);
        }

        @Override
        public void set(E e) {
            CustomArrayList.this.set(currentPosition, e);
        }

        @Override
        public void add(E e) {
            CustomArrayList.this.add(currentPosition, e);
        }
    }

    private void increasingArraySize() {
        if (size == initialCapacityArray) {
            initialCapacityArray = (int) (array.length * 1.5);
            array = Arrays.copyOf(array, initialCapacityArray);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException(index);
        }
    }
}
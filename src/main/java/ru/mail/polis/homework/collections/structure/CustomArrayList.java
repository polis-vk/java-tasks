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
 *
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private E[] array;
    private int size;
    private int modCount;

    public CustomArrayList() {
        array = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public CustomArrayList(E[] array) {
        this.array = array;
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
        return Arrays.copyOf(array, size);
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
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null || c.isEmpty()) {
            return false;
        }
        for (E element : c) {
            add(element);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (c == null || c.isEmpty()) {
            return false;
        }
        for (E element : c) {
            add(index, element);
            index++;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean hasChanged = false;
        for (Object element : c) {
            if (remove(element)) {
                hasChanged = true;
            }
        }
        return hasChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean hasChanged = false;
        int index = 0;
        while (index < size) {
            if (!c.contains(array[index])) {
                remove(index);
                hasChanged = true;
            } else {
                index++;
            }
        }
        return hasChanged;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
        modCount++;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return array[index];
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        E value = array[index];
        array[index] = element;
        modCount++;
        return value;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == array.length) {
            increaseCapacity(size * 3 / 2);
        }
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
        E value = array[index];
        size--;
        System.arraycopy(array, index + 1, array, index, size - index);
        array[size] = null;
        modCount++;
        return value;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (o == null && array[i] == null || array[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (o == null && array[i] == null || array[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListIterator<E>() {
            private int expectedModCount = modCount;
            private int position = index;

            @Override
            public boolean hasNext() {
                return position < size;
            }

            @Override
            public E next() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return array[position++];
            }

            @Override
            public boolean hasPrevious() {
                return position > -1;
            }

            @Override
            public E previous() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                return array[position--];
            }

            @Override
            public int nextIndex() {
                return position + 1;
            }

            @Override
            public int previousIndex() {
                return position - 1;
            }

            @Override
            public void remove() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                CustomArrayList.this.remove(position);
                expectedModCount = modCount;
            }

            @Override
            public void set(E e) {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                CustomArrayList.this.set(position, e);
            }

            @Override
            public void add(E e) {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                CustomArrayList.this.add(position++, e);
                expectedModCount = modCount;
            }
        };
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        E[] subArray = (E[]) new Object[toIndex - fromIndex];
        System.arraycopy(array, fromIndex, subArray, 0, toIndex - fromIndex);
        return new CustomArrayList<>(subArray);
    }

    public void increaseCapacity(int newCapacity) {
        E[] newArray = (E[]) new Object[newCapacity];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }
}

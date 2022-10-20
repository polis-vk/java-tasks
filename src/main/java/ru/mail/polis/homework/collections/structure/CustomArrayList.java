package ru.mail.polis.homework.collections.structure;

import java.util.Collection;
import java.util.Iterator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 * <p>
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final float INCREASE_FACTOR = 1.5F;
    private static final Object[] EMPTY_ELEMENTDATA = {};
    private Object[] elementData;
    private int size;
    private int modCount;

    public CustomArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }
    }

    public CustomArrayList() {
        this(DEFAULT_CAPACITY);
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

            final int expectedModCount = modCount;
            int curIndex;

            @Override
            public boolean hasNext() {
                return curIndex < size;
            }

            @Override
            public E next() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (E) elementData[curIndex++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        }
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public boolean add(E e) {
        modCount++;
        add(size, e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        modCount++;
        int index = indexOf(o);
        for (int i = 0; i < size; i++) {
            if (elementData[i].equals(o)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object elem : c) {
            if (!contains(elem)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        modCount++;
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        modCount++;
        boolean changeFlag = false;
        int curIndex = index;
        for (E elem : c) {
            changeFlag = true;
            add(curIndex++, elem);
        }
        return changeFlag;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changeFlag = false;
        for (Object elem : c) {
            while (remove(elem)) {
                changeFlag = true;
            }
        }
        return changeFlag;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changeFlag = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(elementData[i])) {
                remove(i--);
                changeFlag = true;
            }
        }
        return changeFlag;
    }

    @Override
    public void clear() {
        modCount++;
        Arrays.fill(elementData, null);
        size = 0;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return (E) elementData[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        modCount++;
        Object prevValue = elementData[index];
        elementData[index] = element;
        return (E) prevValue;
    }

    @Override
    public void add(int index, E element) {
        checkIndex(index);
        modCount++;
        if (size == elementData.length) {
            elementData = Arrays.copyOf(elementData, (int) (size * INCREASE_FACTOR));
        }
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        modCount++;
        E prevValue = (E) elementData[index];
        System.arraycopy(elementData, index + 1, elementData, index, size - index - 1);
        size--;
        return prevValue;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (elementData[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (elementData[i].equals(o)) {
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
        checkIndex(index);
        return new ListIterator<E>() {

            int expectedModCount = modCount;
            int curIndex = index;

            @Override
            public boolean hasNext() {
                return curIndex < size;
            }

            @Override
            public E next() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (E) elementData[curIndex++];
            }

            @Override
            public boolean hasPrevious() {
                return curIndex > 0;
            }

            @Override
            public E previous() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                return (E) elementData[--curIndex];
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
            public void remove() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                CustomArrayList.this.remove(curIndex);
                expectedModCount = modCount;
            }

            @Override
            public void set(E e) {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                CustomArrayList.this.set(curIndex, e);

            }

            @Override
            public void add(E e) {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                CustomArrayList.this.add(curIndex, e);
                expectedModCount = modCount;
            }
        };

    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        checkIndex(fromIndex);
        checkIndex(toIndex);
        if (fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        List<E> list = new ArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            list.add((E) elementData[i]);
        }
        return list;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }
}

package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 * <p>
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    private static final int DEFAULT_CAPACITY = 10;
    private static final double GROW_FACTOR = 1.5;
    private E[] data;
    private int size;
    private int modCount;

    private CustomArrayList(E[] subList, int toIndex, int fromIndex) {
        size = toIndex - fromIndex;
        data = subList;
    }

    public CustomArrayList(int initCapacity) {
        if (initCapacity < 0) {
            throw new IllegalArgumentException("Capacity should be non-negative");
        }
        this.data = (E[]) new Object[initCapacity];
        modCount = 0;
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
        return indexOf(o) != -1;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private final int expectedModCount = modCount;
            private int currentIndex;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public E next() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return data[currentIndex++];
            }
        };
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
        modCount++;
        if (size == data.length) {
            data = grow();
        }
        data[size++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int firstCurrIndex = indexOf(o);
        if (firstCurrIndex != -1) {
            remove(firstCurrIndex);
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
        if (c.isEmpty()) {
            return false;
        }

        modCount++;
        int cSize = c.size();
        if (data.length - size < cSize) {
            data = grow(size + cSize);
        }
        if (index != size) {
            System.arraycopy(data, index, data, index + cSize, size - index);
        }
        System.arraycopy(c.toArray(), 0, data, index, cSize);
        size += cSize;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return removeOrRetainAll(c, true);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return removeOrRetainAll(c, false);
    }

    @Override
    public void clear() {
        modCount++;
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    @Override
    public E get(int index) {
        return data[index];
    }

    @Override
    public E set(int index, E element) {
        modCount++;
        E oldValue = data[index];
        data[index] = element;
        return oldValue;
    }

    @Override
    public void add(int index, E element) {
        modCount++;
        if (size == data.length) {
            data = grow();
        }
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        E oldValue = data[index];
        modCount++;
        int newSize;
        if ((newSize = size - 1) > index) {
            System.arraycopy(data, index + 1, data, index, newSize - index);
        }
        size = newSize;
        data[size] = null;
        return oldValue;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (data[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(data[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (data[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(data[i])) {
                    return i;
                }
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
            private int currentIndex = index;
            private int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public E next() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return data[++currentIndex];
            }

            @Override
            public boolean hasPrevious() {
                return currentIndex > 0;
            }

            @Override
            public E previous() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                return data[--currentIndex];
            }

            @Override
            public int nextIndex() {
                return currentIndex;
            }

            @Override
            public int previousIndex() {
                return currentIndex - 1;
            }

            @Override
            public void remove() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                CustomArrayList.this.remove(currentIndex);
                expectedModCount = modCount;
            }

            @Override
            public void set(E e) {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                CustomArrayList.this.set(currentIndex, e);
                expectedModCount = modCount;
            }

            @Override
            public void add(E e) {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                CustomArrayList.this.add(currentIndex, e);
                expectedModCount = modCount;
            }
        };
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        E[] subList = (E[]) new Object[toIndex - fromIndex];
        System.arraycopy(data, fromIndex, subList, 0, toIndex - fromIndex);
        return new CustomArrayList<>(subList, toIndex, fromIndex);
    }

    private E[] grow() {
        return grow((int) (GROW_FACTOR * size));
    }

    private E[] grow(int newCapacity) {
        if (newCapacity > MAX_ARRAY_SIZE) {
            throw new OutOfMemoryError();
        }
        return Arrays.copyOf(data, newCapacity);
    }


    private boolean removeOrRetainAll(Collection<?> c, boolean isRemoved) {
        boolean isModified = false;
        for (ListIterator<E> iterator = this.listIterator(); iterator.hasNext(); ) {
            E currElem = iterator.next();
            if (isRemoved ^ !c.contains(currElem)) {
                iterator.previous();
                iterator.remove();
                isModified = true;
            }
        }
        return isModified;
    }
}


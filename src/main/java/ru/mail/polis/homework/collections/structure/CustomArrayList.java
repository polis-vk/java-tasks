package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 * <p>
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    private int size;
    private Object[] array;
    private int modCount;
    static private final int DEFAULT_CAPACITY = 16;

    public CustomArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public CustomArrayList(int Capacity) {
        array = new Object[Capacity];
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
    @SuppressWarnings("unchecked")
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int i = 0;
            private final int fixedModCount = modCount;

            @Override
            public boolean hasNext() {
                return i < size;
            }

            @Override
            public E next() {
                if (i >= size) {
                    throw new IndexOutOfBoundsException();
                }
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return (E) array[i++];
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
        if (a.length > size) {
            Arrays.fill(a, size, a.length, null);
        }
        return a;

    }

    @Override
    public boolean add(E e) {
        return addAll(Collections.singleton(e));
    }

    @Override
    public void add(int index, E element) {
        addAll(index, Collections.singletonList(element));
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index == -1) {
            return false;
        }
        System.arraycopy(array, index + 1, array, index, size - 1 - index);
        array[--size] = null;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object obj : c) {
            if (!contains(obj)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkIndex(index);
        if (array.length < size + c.size()) {
            array = grow((size + c.size()) - array.length);
        }
        if (index != size) {
            System.arraycopy(array, index, array, index + c.size(), size - index);
        }
        System.arraycopy(c.toArray(), 0, array, index, c.size());
        size += c.size();
        modCount++;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        for (Object obj : c) {
            if (remove(obj)) {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(array[i])) {
                remove(array[i]);
                changed = true;
                i--;
            }
        }
        return changed;
    }

    @Override
    public void clear() {
        array = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        checkIndex(index);
        return (E) array[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        checkIndex(index);
        E prev = (E) array[index];
        array[index] = element;
        return prev;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        Object prev = array[index];
        System.arraycopy(array, index + 1, array, index, size - 1 - index);
        array[--size] = null;
        return (E) prev;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (o.equals(array[i])) {
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
    @SuppressWarnings("unchecked")
    public ListIterator<E> listIterator(int index) {
        return new ListIterator<E>() {
            public int currentIndex = index;
            private int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public E next() {
                if (currentIndex >= size) {
                    throw new IndexOutOfBoundsException(Integer.toString(currentIndex));
                }
                checkModificationCount();
                return (E) array[currentIndex++];
            }

            @Override
            public boolean hasPrevious() {
                return currentIndex != 0;
            }

            @Override
            public E previous() {
                checkModificationCount();
                if (currentIndex < 0) {
                    throw new IndexOutOfBoundsException(Integer.toString(currentIndex));
                }
                return (E) array[--currentIndex];
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
                checkModificationCount();
                CustomArrayList.this.remove(currentIndex);
                expectedModCount = modCount;
            }

            @Override
            public void set(E e) {
                checkModificationCount();
                CustomArrayList.this.set(currentIndex, e);
            }

            @Override
            public void add(E e) {
                checkModificationCount();
                CustomArrayList.this.add(e);
                expectedModCount = modCount;
            }

            private void checkModificationCount() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        checkIndex(fromIndex);
        checkIndex(toIndex);
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
        }
        CustomArrayList<E> customArrayList = new CustomArrayList<>(toIndex - fromIndex);
        System.arraycopy(this.array, fromIndex, customArrayList.array, 0, toIndex - fromIndex);
        customArrayList.size = toIndex - fromIndex;
        return customArrayList;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
    }

    private Object[] grow(int notEnough) {
        int newCapacity = array.length + Math.max(notEnough, (array.length >> 1));
        return Arrays.copyOf(array, newCapacity);
    }

}

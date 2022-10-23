package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 * <p>
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final int INCREASE = 2;
    private E[] elements;
    private int size = 0;
    private int operations = 0;

    public CustomArrayList() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    private CustomArrayList(E[] subList, int toIndex, int fromIndex) {
        size = fromIndex - toIndex;
        elements = subList;
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
            final int operationsInIteration = operations;
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                if (operationsInIteration != operations) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return elements[index++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(elements, size, a.getClass());
        }
        System.arraycopy(elements, 0, a, 0, size);
        return a;
    }

    @Override
    public boolean add(E e) {
        operations++;
        if (size == elements.length) {
            elements = increase(size * INCREASE);
        }
        elements[size++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        operations++;
        int index = indexOf(o);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
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
        operations++;
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }
        operations++;
        int cSize = c.size();
        if (elements.length - size < cSize) {
            elements = increase(size + cSize);
        }
        if (index != size) {
            System.arraycopy(elements, index, elements, index + cSize, size - index);
        }
        System.arraycopy(c.toArray(), 0, elements, index, cSize);
        size += cSize;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c.isEmpty()) {
            return false;
        }
        operations++;
        for (Object o : c) {
            if (contains(o)) {
                remove(o);
            }
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c.isEmpty()) {
            return false;
        }
        operations++;
        for (int i = 0; i < size; i++) {
            if (!c.contains(elements[i])) {
                remove(i);
                i--;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        operations++;
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public E get(int index) {
        if (index >= size || index < 0) {
            throw new NoSuchElementException();
        }
        return elements[index];
    }

    @Override
    public E set(int index, E element) {
        operations++;
        if (index >= size || index < 0) {
            throw new NoSuchElementException();
        }
        E value = elements[index];
        elements[index] = element;
        return value;
    }

    @Override
    public void add(int index, E element) {
        operations++;
        if (size == elements.length) {
            elements = increase(size * INCREASE);
        }
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        operations++;
        E value = elements[index];
        int newSize = size - 1;
        if (newSize > index) {
            System.arraycopy(elements, index + 1, elements, index, newSize - index);
        }
        size = newSize;
        elements[size] = null;
        return value;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (elements[i].equals(o)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int lastIndex = -1;
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) {
                    lastIndex = i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (elements[i].equals(o)) {
                    lastIndex = i;
                }
            }
        }
        return lastIndex;
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListIterator<E>() {
            int currentIndex = index;
            int operationsInIteration = operations;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public E next() {
                if (operationsInIteration != operations) {
                    throw new ConcurrentModificationException();
                }
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return elements[currentIndex++];
            }

            @Override
            public boolean hasPrevious() {
                return currentIndex > 0;
            }

            @Override
            public E previous() {
                if (operationsInIteration != operations) {
                    throw new ConcurrentModificationException();
                }
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                return elements[currentIndex--];
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
                if (operationsInIteration != operations) {
                    throw new ConcurrentModificationException();
                }
                CustomArrayList.this.remove(currentIndex);
                operationsInIteration = operations;
            }

            @Override
            public void set(E e) {
                if (operationsInIteration != operations) {
                    throw new ConcurrentModificationException();
                }
                CustomArrayList.this.set(currentIndex, e);
                operationsInIteration = operations;
            }

            @Override
            public void add(E e) {
                if (operationsInIteration != operations) {
                    throw new ConcurrentModificationException();
                }
                CustomArrayList.this.add(currentIndex, e);
                currentIndex++;
                operationsInIteration = operations;
            }
        };
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex > toIndex || fromIndex < 0 || toIndex > size) {
            throw new IndexOutOfBoundsException();
        }
        E[] sublist = (E[]) new Object[toIndex - fromIndex];
        System.arraycopy(elements, fromIndex, sublist, 0, toIndex - fromIndex);
        return new CustomArrayList<>(sublist, toIndex, fromIndex);
    }


    private E[] increase(int newCapacity) {
        return Arrays.copyOf(elements, newCapacity);
    }

}

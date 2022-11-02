package ru.mail.polis.homework.collections.structure;

import java.util.*;
import java.util.function.Consumer;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 * <p>
 * Задание оценивается в 10 тугриков
 */

public class CustomArrayList<E> implements List<E> {
    private int size;
    private Object[] elementData;
    private int modCount = 0;

    public CustomArrayList(int size) {
        if (size >= 0) {
            this.elementData = new Object[size];
            this.size = size;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public CustomArrayList() {
        this.elementData = new Object[10];
    }

    public CustomArrayList(Collection<? extends E> collection) {
        size = collection.size();
        if (size != 0) {
            elementData = Arrays.copyOf(collection.toArray(), size, Object[].class);
        } else {
            elementData = new Object[0];
        }
    }

    public CustomArrayList(Object[] elementData) {
        this.elementData = elementData;
        size = elementData.length;
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
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        int cursor;
        int lastRet = -1;
        int expectedModCount = modCount;

        Itr() {
        }

        public boolean hasNext() {
            return cursor != size;
        }

        public E next() {
            checkForComodification();
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            Object[] elementData = CustomArrayList.this.elementData;
            if (cursor >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            cursor++;
            lastRet = cursor - 1;
            return (E) elementData[lastRet];
        }

        public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            checkForComodification();
            try {
                CustomArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            final int size = CustomArrayList.this.size;
            if (cursor < size) {
                final Object[] es = elementData;
                if (cursor >= es.length) {
                    throw new ConcurrentModificationException();
                }
                int i = cursor;
                while (i < size && modCount == expectedModCount) {
                    action.accept(elementAt(es, i));
                    i++;
                }
                cursor = i;
                lastRet = cursor - 1;
                checkForComodification();
            }
        }

        final void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private static <E> E elementAt(Object[] es, int index) {
        return (E) es[index];
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
        return a;
    }

    @Override
    public boolean add(E e) {
        modCount++;
        if (size == elementData.length) {
            elementData = grow(this.size + 1);
        }
        elementData[size] = e;
        this.size = size + 1;
        return true;
    }

    private Object[] grow(int minCapacity) {
        int newCapacity = elementData.length + (elementData.length >> 1);
        if (newCapacity - minCapacity <= 0) {
            newCapacity = Math.max(elementData.length, minCapacity);
        }
        return elementData = Arrays.copyOf(elementData, newCapacity);
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
    public boolean containsAll(Collection<?> collection) {
        for (Object o : collection) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        Object[] a = collection.toArray();
        modCount++;
        if (a.length == 0) {
            return false;
        }
        if (a.length > elementData.length - size) {
            elementData = grow(size + a.length);
        }
        System.arraycopy(a, 0, elementData, size, a.length);
        size += a.length;
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        rangeCheckForAdd(index);
        Object[] array = collection.toArray();
        modCount++;
        if (array.length == 0) {
            return false;
        }
        if (array.length > elementData.length - size) {
            elementData = grow(size + array.length);
        }
        if (size - index > 0) {
            System.arraycopy(elementData, index, elementData, index + array.length, size - index);
        }
        System.arraycopy(array, 0, elementData, index, array.length);
        size += array.length;
        return true;
    }

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean removed = false;
        for (Object elem : collection) {
            while (remove(elem)) {
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean retained = false;
        int i = 0;
        while (i < size) {
            if (!collection.contains(elementData[i])) {
                remove(i);
                retained = true;
            } else {
                i++;
            }
        }
        return retained;
    }

    @Override
    public void clear() {
        modCount++;
        final Object[] copy = elementData;
        for (int to = size, i = size = 0; i < to; i++) {
            copy[i] = null;
        }
    }

    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);
        return (E) elementData[index];
    }

    @Override
    public E set(int index, E element) {
        Objects.checkIndex(index, size);
        E oldValue = (E) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        modCount++;
        if (size == elementData.length) {
            elementData = grow(size + 1);
        }
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheckForAdd(index);
        E oldValue = (E) elementData[index];
        modCount++;
        size--;
        if (size > index) {
            System.arraycopy(elementData, index + 1, elementData, index, size - index);
        }
        elementData[size] = null;
        return oldValue;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elementData[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Object[] es = elementData;
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListItr(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        rangeCheckForAdd(index);
        return new ListItr(index);
    }

    private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        public E previous() {
            checkForComodification();
            cursor--;
            if (cursor < 0) {
                throw new NoSuchElementException();
            }
            Object[] elementData = CustomArrayList.this.elementData;
            if (cursor >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            lastRet = cursor;
            return (E) elementData[lastRet];
        }

        public void set(E e) {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            checkForComodification();
            try {
                CustomArrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {
            checkForComodification();
            try {
                CustomArrayList.this.add(cursor, e);
                cursor++;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }


    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        rangeCheckForAdd(fromIndex);
        rangeCheckForAdd(toIndex);
        if (fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        E[] newData = (E[]) new Object[toIndex - fromIndex];
        System.arraycopy(elementData, fromIndex, newData, 0, toIndex - fromIndex);
        return new CustomArrayList<>(List.of(newData));
    }
}
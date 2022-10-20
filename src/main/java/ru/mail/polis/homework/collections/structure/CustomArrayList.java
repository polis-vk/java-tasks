package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 *
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {

    private static final String ILLEGAL_CAPACITY = "Illegal capacity: ";
    private static final String NO_SUCH_ELEMENT = "There is no such element.";
    private static final String ILLEGAL_STATE = "The corresponding method was not called that would return the value.";
    private static final String CONCURRENT_MODIFICATION = "During iteration, " +
            "only the iterator can modify the collection.";
    private static final String INVALID_INDEX = "Invalid index: ";
    private static final String INVALID_SUBLIST_INDEXES = "Invalid fromIndex or toIndex: ";
    private static final String SIZE = ", List size: ";

    private static final int DEFAULT_CAPACITY = 16;
    private static final Object[] EMPTY_LIST = {};

    private Object[] dataArray;
    private int size;
    private int modCount;

    public CustomArrayList() {
        dataArray = EMPTY_LIST;
    }

    public CustomArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            dataArray = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            dataArray = EMPTY_LIST;
        } else {
            throw new IllegalArgumentException(ILLEGAL_CAPACITY + initialCapacity);
        }
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
        return new Iter(0);
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(dataArray, size);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(dataArray, size, a.getClass());
        }

        System.arraycopy(dataArray, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public boolean add(E e) {
        checkCapacity(size + 1);
        dataArray[size] = e;
        size++;
        modCount++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o != null) {
            for (int i = 0; i < size; i++) {
                if (o.equals(dataArray[i])) {
                    removeWithoutCheck(i);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (dataArray[i] == null) {
                    removeWithoutCheck(i);
                    return true;
                }
            }
        }

        return false;
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
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkIndexForAdd(index);

        Object[] array = c.toArray();
        int arraySize = array.length;
        if (arraySize == 0) {
            return false;
        }
        checkCapacity(size + arraySize);

        int numbersShift = size - index;
        if (numbersShift > 0) {
            System.arraycopy(dataArray, index, dataArray, index + arraySize, numbersShift);
        }

        System.arraycopy(array, 0, dataArray, index, arraySize);
        size += arraySize;
        modCount++;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);

        boolean isChanged = false;
        for (int i = size - 1; i >= 0 ; i--) {
            if (c.contains(dataArray[i])) {
                remove(i);
                isChanged = true;
            }
        }

        return isChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);

        boolean isChanged = false;
        for (int i = size - 1; i >= 0 ; i--) {
            if (!c.contains(dataArray[i])) {
                remove(i);
                isChanged = true;
            }
        }

        return isChanged;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            dataArray[i] = null;
        }

        modCount++;
        size = 0;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return getWithCast(index);
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);

        E value = getWithCast(index);
        dataArray[index] = element;
        modCount++;
        return value;
    }

    @Override
    public void add(int index, E element) {
        checkIndexForAdd(index);
        checkCapacity(size + 1);

        System.arraycopy(dataArray, index, dataArray, index + 1, size - index);
        dataArray[index] = element;
        size++;
        modCount++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        E value = getWithCast(index);
        removeWithoutCheck(index);
        return value;
    }

    @Override
    public int indexOf(Object o) {
        if (o != null) {
            for (int i = 0; i < size; i++) {
                if (o.equals(dataArray[i])) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (dataArray[i] == null) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o != null) {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(dataArray[i])) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (dataArray[i] == null) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new CustomArrayListIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new CustomArrayListIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException(INVALID_SUBLIST_INDEXES + fromIndex + ", " + toIndex + SIZE);
        }

        CustomArrayList<E> list = new CustomArrayList<>(toIndex - fromIndex);
        for (int i = fromIndex; i < toIndex; i++) {
            list.add(getWithCast(i));
        }

        return list;
    }

    private class Iter implements Iterator<E> {
        int cursor;
        int lastReturnedIndex = -1;
        int fixedModCount = modCount;

        private Iter(int index) {
            cursor = index;
        }

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public E next() {
            checkModification();

            if (cursor >= size) {
                throw new NoSuchElementException(NO_SUCH_ELEMENT);
            }

            E value = getWithCast(cursor);
            lastReturnedIndex = cursor;
            cursor++;
            return value;
        }

        @Override
        public void remove() {
            checkLastReturnedIndex();
            checkModification();

            try {
                CustomArrayList.this.remove(lastReturnedIndex);
                cursor = lastReturnedIndex;
                lastReturnedIndex = -1;
                fixedModCount = modCount;
            } catch (IndexOutOfBoundsException exception) {
                throw new ConcurrentModificationException(CONCURRENT_MODIFICATION);
            }
        }

        void checkModification() {
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException(CONCURRENT_MODIFICATION);
            }
        }

        void checkLastReturnedIndex() {
            if (lastReturnedIndex < 0) {
                throw new IllegalStateException(ILLEGAL_STATE);
            }
        }
    }

    private class CustomArrayListIterator extends Iter implements ListIterator<E> {
        private CustomArrayListIterator(int index) {
            super(index);
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public E previous() {
            checkModification();

            int index = cursor - 1;
            if (index < 0) {
                throw new NoSuchElementException(NO_SUCH_ELEMENT);
            }

            E value = getWithCast(index);
            lastReturnedIndex = index;
            cursor--;
            return value;
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void set(E e) {
            checkLastReturnedIndex();
            checkModification();

            try {
                CustomArrayList.this.set(lastReturnedIndex, e);
                fixedModCount = modCount;
            } catch (IndexOutOfBoundsException exception) {
                throw new ConcurrentModificationException(CONCURRENT_MODIFICATION);
            }
        }

        @Override
        public void add(E e) {
            checkModification();

            try {
                CustomArrayList.this.add(lastReturnedIndex, e);
                cursor++;
                lastReturnedIndex = -1;
                fixedModCount = modCount;
            } catch (IndexOutOfBoundsException exception) {
                throw new ConcurrentModificationException(CONCURRENT_MODIFICATION);
            }
        }
    }

    private void checkCapacity(int capacity) {
        int requiredCapacity;
        if (dataArray == EMPTY_LIST) {
            requiredCapacity = Math.max(DEFAULT_CAPACITY, capacity);
        } else {
            requiredCapacity = capacity;
        }

        if (requiredCapacity < 0) {
            throw new OutOfMemoryError();
        }

        if (requiredCapacity > dataArray.length) {
            int newCapacity = dataArray.length << 1;
            if (newCapacity < requiredCapacity) {
                newCapacity = requiredCapacity;
            }

            dataArray = Arrays.copyOf(dataArray, newCapacity);
        }
    }

    private void checkIndex(int index) {
        if  (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(indexOutOfBoundMessage(index));
        }
    }

    private void checkIndexForAdd(int index) {
        if  (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(indexOutOfBoundMessage(index));
        }
    }

    private String indexOutOfBoundMessage(int index) {
        return INVALID_INDEX + index + SIZE + size;
    }

    private void removeWithoutCheck(int index) {
        int numbersShift = size - index - 1;
        if (numbersShift > 0) {
            System.arraycopy(dataArray, index + 1, dataArray, index, numbersShift);
        }

        size--;
        dataArray[size] = null;
        modCount++;
    }

    @SuppressWarnings("unchecked")
    private E getWithCast(int index) {
        return (E) dataArray[index];
    }
}

package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 *
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final Object[] EMPTY_LIST = {};

    private Object[] dataArray;
    private int size;
    private int modCount;

    public CustomArrayList() {
        dataArray = new Object[DEFAULT_CAPACITY];
    }

    public CustomArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal capacity: " + initialCapacity);
        }

        dataArray = new Object[initialCapacity];
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
        for (int i = 0; i < size; i++) {
            if (Objects.equals(o, dataArray[i])) {
                removeWithoutCheck(i);
                return true;
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
        checkIndexForAdd(index, size);
        if (c.size() == 0) {
            return false;
        }

        checkCapacity(size + c.size());
        Object[] array = c.toArray();

        int numbersShift = size - index;
        if (numbersShift > 0) {
            System.arraycopy(dataArray, index, dataArray, index + c.size(), numbersShift);
        }

        System.arraycopy(array, 0, dataArray, index, c.size());
        size += c.size();
        modCount++;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);

        boolean isChanged = false;
        for (Object element : c) {
            while (remove(element)) {
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
        while (size != 0) {
            size--;
            dataArray[size] = null;
        }

        modCount++;
    }

    @Override
    public E get(int index) {
        checkIndex(index, size);
        return (E) dataArray[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index, size);

        E value = (E) dataArray[index];
        dataArray[index] = element;
        modCount++;
        return value;
    }

    @Override
    public void add(int index, E element) {
        checkIndexForAdd(index, size);
        checkCapacity(size + 1);

        System.arraycopy(dataArray, index, dataArray, index + 1, size - index);
        dataArray[index] = element;
        size++;
        modCount++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index, size);

        E value = (E) dataArray[index];
        removeWithoutCheck(index);
        return value;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(o, dataArray[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(o, dataArray[i])) {
                return i;
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
        checkIndexForAdd(index, size);
        return new CustomArrayListIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        checkSubListIndexes(fromIndex, toIndex, size);
        return new SubList(this, fromIndex, toIndex);
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

    private void checkIndex(int index, int listSize) {
        if  (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(indexOutOfBoundMessage(index, listSize));
        }
    }

    private void checkIndexForAdd(int index, int listSize) {
        if  (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(indexOutOfBoundMessage(index, listSize));
        }
    }

    private static String indexOutOfBoundMessage(int index, int listSize) {
        return "Invalid index: " + index + ", List size: " + listSize;
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

    private void checkSubListIndexes(int fromIndex, int toIndex, int listSize) {
        if (fromIndex < 0 || toIndex > listSize || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("Invalid fromIndex or toIndex: " + fromIndex + ", " +
                    toIndex + ", List size: " + listSize);
        }
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
                throw new NoSuchElementException("There is no such element.");
            }

            E value = (E) dataArray[cursor];
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
                throw new ConcurrentModificationException("During iteration, " +
                        "only the iterator can modify the collection.");
            }
        }

        protected void checkModification() {
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException("During iteration, " +
                        "only the iterator can modify the collection.");
            }
        }

        protected void checkLastReturnedIndex() {
            if (lastReturnedIndex < 0) {
                throw new IllegalStateException("The corresponding method was not called that would return the value.");
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
                throw new NoSuchElementException("There is no such element.");
            }

            E value = (E) dataArray[index];
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
                throw new ConcurrentModificationException("During iteration, " +
                        "only the iterator can modify the collection.");
            }
        }

        @Override
        public void add(E e) {
            checkModification();

            try {
                CustomArrayList.this.add(cursor, e);
                cursor++;
                lastReturnedIndex = -1;
                fixedModCount = modCount;
            } catch (IndexOutOfBoundsException exception) {
                throw new ConcurrentModificationException("During iteration, " +
                        "only the iterator can modify the collection.");
            }
        }
    }

    private class SubList implements List<E> {
        private final List<E> parent;
        private final int offset;
        private int size;
        private int modCount;

        protected SubList(List<E> parent, int fromIndex, int toIndex) {
            this.parent = parent;
            this.offset = fromIndex;
            this.size = toIndex - fromIndex;
            this.modCount = CustomArrayList.this.modCount;
        }

        @Override
        public int size() {
            checkModification();
            return size;
        }

        @Override
        public boolean isEmpty() {
            checkModification();
            return size != 0;
        }

        @Override
        public boolean contains(Object o) {
            checkModification();
            return indexOf(o) >= 0;
        }

        @Override
        public Iterator<E> iterator() {
            return listIterator();
        }

        @Override
        public Object[] toArray() {
            checkModification();
            return Arrays.copyOfRange(CustomArrayList.this.dataArray, offset, offset + size);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] toArray(T[] a) {
            checkModification();
            if (a.length < this.size) {
                return (T[]) Arrays.copyOfRange(CustomArrayList.this.dataArray, offset, offset + size, a.getClass());
            }

            System.arraycopy(CustomArrayList.this.dataArray, offset, a, 0, size);
            if (a.length > size) {
                a[size] = null;
            }
            return a;
        }

        @Override
        public boolean add(E e) {
            checkModification();
            parent.add(e);
            size++;
            modCount = CustomArrayList.this.modCount;
            return true;
        }

        @Override
        public boolean remove(Object o) {
            checkModification();

            for (int i = offset; i < offset + size; i++) {
                if (Objects.equals(o, CustomArrayList.this.dataArray[i])) {
                    CustomArrayList.this.removeWithoutCheck(i);
                    return true;
                }
            }

            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            checkModification();

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
            checkModification();
            checkIndexForAdd(index, size);
            if (c.size() == 0) {
                return false;
            }

            parent.addAll(offset + index, c);
            size += c.size();
            modCount = CustomArrayList.this.modCount;
            return true;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            checkModification();
            Objects.requireNonNull(c);

            boolean isChanged = false;
            for (Object element : c) {
                while (remove(element)) {
                    isChanged = true;
                }
            }

            return isChanged;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            checkModification();
            Objects.requireNonNull(c);

            boolean isChanged = false;
            for (int i = offset + size - 1; i >= offset ; i--) {
                if (!c.contains(CustomArrayList.this.dataArray[i])) {
                    remove(i);
                    isChanged = true;
                }
            }

            return isChanged;
        }

        @Override
        public void clear() {
            checkModification();
            System.arraycopy(CustomArrayList.this.dataArray, offset + size,
                    CustomArrayList.this.dataArray, offset, CustomArrayList.this.size - offset + size);

            CustomArrayList.this.size -= size;
            size = 0;
            CustomArrayList.this.modCount++;
            modCount = CustomArrayList.this.modCount;
        }

        @Override
        public E get(int index) {
            checkModification();
            checkIndex(index, size);
            return (E) CustomArrayList.this.dataArray[offset + index];
        }

        @Override
        public E set(int index, E element) {
            checkModification();
            checkIndex(index, size);

            E value = (E) CustomArrayList.this.dataArray[offset + index];
            CustomArrayList.this.dataArray[offset + index] = element;
            CustomArrayList.this.modCount++;
            modCount = CustomArrayList.this.modCount;
            return value;
        }

        @Override
        public void add(int index, E element) {
            checkIndexForAdd(index, size);
            checkModification();

            parent.add(offset + index, element);
            size++;
            modCount = CustomArrayList.this.modCount;
        }

        @Override
        public E remove(int index) {
            checkIndex(index, size);
            checkModification();

            E value = parent.remove(offset + index);
            size--;
            modCount = CustomArrayList.this.modCount;
            return value;
        }

        @Override
        public int indexOf(Object o) {
            checkModification();
            for (int i = offset; i < offset + size; i++) {
                if (Objects.equals(o, dataArray[i])) {
                    return i;
                }
            }

            return -1;
        }

        @Override
        public int lastIndexOf(Object o) {
            checkModification();
            for (int i = offset + size - 1; i >= offset; i--) {
                if (Objects.equals(o, dataArray[i])) {
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
            checkModification();
            checkIndexForAdd(index, size);

            return new ListIterator<E>() {
                int cursor = index;
                int lastReturnedIndex = -1;
                int fixedModCount = CustomArrayList.this.modCount;

                @Override
                public boolean hasNext() {
                    return cursor != size;
                }

                @Override
                public E next() {
                    checkModification();

                    if (cursor >= size) {
                        throw new NoSuchElementException("There is no such element.");
                    }

                    E value = (E) CustomArrayList.this.dataArray[offset + cursor];
                    lastReturnedIndex = cursor;
                    cursor++;
                    return value;
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
                        throw new NoSuchElementException("There is no such element.");
                    }

                    E value = (E) CustomArrayList.this.dataArray[offset + index];
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
                public void remove() {
                    checkLastReturnedIndex();
                    checkModification();

                    try {
                        SubList.this.remove(lastReturnedIndex);
                        cursor = lastReturnedIndex;
                        lastReturnedIndex = -1;
                        fixedModCount = CustomArrayList.this.modCount;
                    } catch (IndexOutOfBoundsException exception) {
                        throw new ConcurrentModificationException("During iteration, " +
                                "only the iterator can modify the collection.");
                    }
                }

                @Override
                public void set(E e) {
                    checkLastReturnedIndex();
                    checkModification();

                    try {
                        CustomArrayList.this.set(offset + lastReturnedIndex, e);
                        fixedModCount = CustomArrayList.this.modCount;
                    } catch (IndexOutOfBoundsException exception) {
                        throw new ConcurrentModificationException("During iteration, " +
                                "only the iterator can modify the collection.");
                    }
                }

                @Override
                public void add(E e) {
                    checkModification();

                    try {
                        CustomArrayList.this.add(cursor, e);
                        cursor++;
                        lastReturnedIndex = -1;
                        fixedModCount = CustomArrayList.this.modCount;
                    } catch (IndexOutOfBoundsException exception) {
                        throw new ConcurrentModificationException("During iteration, " +
                                "only the iterator can modify the collection.");
                    }
                }

                private void checkModification() {
                    if (fixedModCount != CustomArrayList.this.modCount) {
                        throw new ConcurrentModificationException("During iteration, " +
                                "only the iterator can modify the collection.");
                    }
                }

                private void checkLastReturnedIndex() {
                    if (lastReturnedIndex < 0) {
                        throw new IllegalStateException("The corresponding method was not called " +
                                "that would return the value.");
                    }
                }
            };
        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            CustomArrayList.this.checkSubListIndexes(fromIndex, toIndex, size);
            return new SubList(this, fromIndex, toIndex);
        }

        private void checkModification() {
            if (this.modCount != CustomArrayList.this.modCount) {
                throw new ConcurrentModificationException("The parent list has been changed.");
            }
        }
    }
}

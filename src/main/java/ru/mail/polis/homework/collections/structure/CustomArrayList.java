package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 * <p>
 * Задание оценивается в 10 тугриков
 */
@SuppressWarnings("unchecked")
public class CustomArrayList<E> implements List<E> {

    private static final String CONCURRENT_MODIFICATION = "It's prohibited to modify list while iterating!";
    private static final String NO_ELEMENT = "There is no such element left!";
    private static final String ILLEGAL_STATE = "It's not permitted to do this!";

    private static final int MODIFICATION_INDICATOR = -1;

    private final E[] data;
    private int size;
    private int modCount;

    public CustomArrayList() {
        data = (E[]) new Object[16];
    }

    public CustomArrayList(E[] data) {
        this.data = data;
        this.size = data.length;
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
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (data[i] == null) {
                    return true;
                }
            }
            return false;
        }
        return indexOf(o) != -1;
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIteratorImpl(0);
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        T[] array;

        if (a.length < size) {
            array = (T[]) Arrays.copyOf(data, size, a.getClass());
        } else {
            array = a;
        }

        System.arraycopy((T[]) data, 0, array, 0, size);

        return array;
    }

    @Override
    public boolean add(E e) {
        if (size >= data.length) {
            expandArray();
        }
        data[size] = e;
        size++;
        modCount++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (o.equals(data[i])) {
                remove(i);
                modCount++;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c.isEmpty()) {
            return true;
        }

        for (Object o : c) {
            if (!contains(o)) {
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
        checkIndexInclusive(index);

        if (c.isEmpty()) {
            return false;
        }

        int fullSize = size + c.size();
        int startIndex = index + c.size();

        expandArray(fullSize);

        System.arraycopy(data, index, data, startIndex, fullSize - startIndex);
        System.arraycopy(c.toArray(), 0, (Object[]) data, index, c.size());

        size = fullSize;
        modCount++;

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean hasChanged = false;

        for (int i = 0; i < size; ) {
            if (c.contains(data[i])) {
                hasChanged = true;
                remove(i);
            } else {
                i++;
            }
        }

        return hasChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean hasChanged = false;

        for (int i = 0; i < size; ) {
            if (!c.contains(data[i])) {
                hasChanged = true;
                remove(i);
            } else {
                i++;
            }
        }

        return hasChanged;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
        modCount++;
    }

    @Override
    public E get(int index) {
        checkIndexExclusive(index);
        return data[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndexExclusive(index);
        E prev = data[index];
        data[index] = element;
        modCount++;
        return prev;
    }

    @Override
    public void add(int index, E element) {
        checkIndexInclusive(index);

        if (size >= data.length) {
            expandArray();
        }

        System.arraycopy(data, index, data, index + 1, size - index);

        data[index] = element;

        size++;
        modCount++;
    }

    @Override
    public E remove(int index) {
        checkIndexExclusive(index);

        E element = data[index];

        for (int j = index + 1; j < size; j++) {
            swap(j, j - 1);
        }

        size--;
        modCount++;

        return element;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            throw new NullPointerException("Given argument is null!");
        }

        for (int i = 0; i < size; i++) {
            if (data[i].equals(o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            throw new NullPointerException("Given argument is null!");
        }

        for (int i = size - 1; i >= 0; i--) {
            if (data[i].equals(o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListIteratorImpl(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        checkIndexInclusive(index);

        return new ListIteratorImpl(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        checkIndexExclusive(fromIndex);
        checkIndexInclusive(toIndex);

        return new SubList(fromIndex, toIndex, this);
    }

    private class SubList extends CustomArrayList<E> {

        private final CustomArrayList<E> superList;
        private final int fixedModCount;
        private final int from;

        private int size;
        private int modCount;

        public SubList(int from, int to, CustomArrayList<E> superList) {
            this.from = from;
            this.size = to - from;
            this.superList = superList;
            this.fixedModCount = superList.modCount;
        }

        @Override
        public E get(int index) {
            checkIndexInRangeExclusive(index);

            return data[from + index];
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public E set(int index, E e) {
            checkIndexInRangeExclusive(index);
            concurrentModificationCheck();

            E element = data[index];
            data[index] = e;
            return element;
        }

        @Override
        public void add(int index, E e) {
            checkIndexInRangeInclusive(index);
            concurrentModificationCheck();

            superList.add(index, e);

            this.modCount = superList.modCount;
            this.size++;
        }

        @Override
        public E remove(int index) {
            checkIndexInRangeExclusive(index);
            concurrentModificationCheck();

            E element = superList.remove(index);
            this.modCount = superList.modCount;
            this.size--;
            return element;
        }

        @Override
        protected void removeRange(int fromIndex, int toIndex) {
            checkIndexInRangeExclusive(fromIndex);
            checkIndexInRangeInclusive(toIndex);

            superList.removeRange(from + fromIndex, fromIndex + toIndex);
            this.modCount = superList.modCount;
            this.size -= toIndex - fromIndex;
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            return addAll(this.size, c);
        }

        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            checkIndexInRangeInclusive(index);
            concurrentModificationCheck();

            boolean result = superList.addAll(from + index, c);
            this.modCount = superList.modCount;
            this.size += c.size();
            return result;
        }

        @Override
        public Iterator<E> iterator() {
            return listIterator();
        }

        @Override
        public void clear() {
            removeRange(from, from + size);
            size = 0;
            this.modCount = superList.modCount;
        }

        @Override
        public ListIterator<E> listIterator(final int index) {
            concurrentModificationCheck();
            checkIndexInRangeExclusive(index);

            return new ListIterator<E>() {
                private final int from = SubList.this.from + index;
                private int fixedModCount = SubList.this.modCount;
                private int cursor = from;
                private int lastReturnedElementIndex = MODIFICATION_INDICATOR;

                @Override
                public boolean hasNext() {
                    this.concurrentModificationCheck();

                    return cursor < SubList.this.size;
                }

                @Override
                public E next() {
                    if (hasNext()) {
                        E element = data[cursor];
                        lastReturnedElementIndex = cursor;
                        cursor++;
                        return element;
                    }
                    throw new NoSuchElementException(NO_ELEMENT);
                }

                @Override
                public boolean hasPrevious() {
                    this.concurrentModificationCheck();

                    return cursor > this.from;
                }

                @Override
                public E previous() {
                    if (hasPrevious()) {
                        E element = data[cursor];
                        lastReturnedElementIndex = cursor;
                        cursor--;
                        return element;
                    }
                    throw new NoSuchElementException(NO_ELEMENT);
                }

                @Override
                public void forEachRemaining(Consumer<? super E> consumer) {
                    if (consumer == null) {
                        throw new NullPointerException("This argument can not be nullable!");
                    }

                    this.concurrentModificationCheck();

                    for (int i = cursor; i < SubList.this.size; i++) {
                        consumer.accept(data[i]);
                    }
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
                    if (lastReturnedElementIndex == MODIFICATION_INDICATOR) {
                        throw new IllegalStateException(ILLEGAL_STATE);
                    }

                    this.concurrentModificationCheck();

                    SubList.this.remove(lastReturnedElementIndex);
                    cursor = lastReturnedElementIndex;
                    lastReturnedElementIndex = MODIFICATION_INDICATOR;
                    fixedModCount = SubList.this.modCount;
                }

                @Override
                public void set(E e) {
                    if (lastReturnedElementIndex == MODIFICATION_INDICATOR) {
                        throw new IllegalStateException(ILLEGAL_STATE);
                    }

                    this.concurrentModificationCheck();

                    SubList.this.set(lastReturnedElementIndex, e);
                }

                @Override
                public void add(E e) {
                    if (lastReturnedElementIndex == MODIFICATION_INDICATOR) {
                        throw new IllegalStateException(ILLEGAL_STATE);
                    }

                    this.concurrentModificationCheck();

                    SubList.this.add(cursor, e);
                    cursor++;
                    lastReturnedElementIndex = MODIFICATION_INDICATOR;
                    fixedModCount = SubList.this.modCount;
                }

                private void concurrentModificationCheck() {
                    if (fixedModCount != SubList.this.modCount) {
                        throw new ConcurrentModificationException(CONCURRENT_MODIFICATION);
                    }
                }
            };
        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            checkIndexInRangeExclusive(fromIndex);
            checkIndexInRangeInclusive(toIndex);

            return new SubList(from + fromIndex, from + toIndex, this);
        }

        private void checkIndexInRangeExclusive(int index) {
            if (index < from || index >= size) {
                throw new IndexOutOfBoundsException("Index is out of range!");
            }
        }

        private void checkIndexInRangeInclusive(int index) {
            if (index < from || index > size) {
                throw new IndexOutOfBoundsException("Index is out of range!");
            }
        }

        private void concurrentModificationCheck() {
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException(CONCURRENT_MODIFICATION);
            }
        }
    }

    private class ListIteratorImpl implements ListIterator<E> {
        private final int startBound;
        private int fixedModCount = modCount;
        private int lastReturnedElementIndex;
        private int cursor;

        public ListIteratorImpl(int startBound) {
            this.cursor = startBound;
            this.startBound = startBound;
        }

        @Override
        public boolean hasNext() {
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException(CONCURRENT_MODIFICATION);
            }
            return cursor < size;
        }

        @Override
        public E next() {
            if (hasNext()) {
                E element = data[cursor];
                cursor++;
                lastReturnedElementIndex = cursor;
                return element;
            }
            throw new NoSuchElementException(NO_ELEMENT);
        }

        @Override
        public boolean hasPrevious() {
            if (fixedModCount != modCount) {
                throw new ConcurrentModificationException(CONCURRENT_MODIFICATION);
            }
            return cursor > startBound;
        }

        @Override
        public E previous() {
            if (hasPrevious()) {
                cursor--;
                lastReturnedElementIndex = cursor;
                return data[cursor];
            }
            throw new NoSuchElementException(NO_ELEMENT);
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
            if (lastReturnedElementIndex == MODIFICATION_INDICATOR) {
                throw new IllegalStateException(ILLEGAL_STATE);
            }

            cursor = lastReturnedElementIndex;
            lastReturnedElementIndex = MODIFICATION_INDICATOR;

            CustomArrayList.this.remove(cursor);
            fixedModCount = CustomArrayList.this.modCount;
        }

        @Override
        public void set(E e) {
            if (lastReturnedElementIndex == MODIFICATION_INDICATOR) {
                throw new IllegalStateException(ILLEGAL_STATE);
            }

            CustomArrayList.this.set(lastReturnedElementIndex, e);
            fixedModCount = CustomArrayList.this.modCount;
            lastReturnedElementIndex = MODIFICATION_INDICATOR;
        }

        @Override
        public void add(E e) {
            CustomArrayList.this.add(cursor, e);
            fixedModCount = CustomArrayList.this.modCount;
            cursor++;
            lastReturnedElementIndex = MODIFICATION_INDICATOR;
        }
    }

    private void expandArray() {
        expandArray(size * 2);
    }

    private void expandArray(int newSize) {
        System.arraycopy(data, 0, data, 0, newSize);
    }

    private void swap(int i, int j) {
        E temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    private void checkIndexExclusive(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("There is no element with index " + index + "!");
        }
    }

    private void checkIndexInclusive(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("There is no element with index " + index + "!");
        }
    }

    protected void removeRange(int fromInclusive, int toExclusive) {
        checkIndexExclusive(fromInclusive);
        checkIndexInclusive(toExclusive);

        for (int i = fromInclusive; i < toExclusive; i++) {
            data[i] = null;
        }

        System.arraycopy(data, toExclusive, data, fromInclusive, size - toExclusive);

        size -= toExclusive - fromInclusive;
        modCount++;
    }
}

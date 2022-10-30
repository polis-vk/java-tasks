package ru.mail.polis.homework.collections.structure;

import java.util.Collection;
import java.util.Iterator;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 * <p>
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int MAX_SIZE = Integer.MAX_VALUE - 8;
    private static final float INCREASE_FACTOR = 1.5F;

    private E[] elementData;
    private int size;
    private int modCount;

    @SuppressWarnings("unchecked")
    public CustomArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        this.elementData = (E[]) new Object[initialCapacity];
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
                return elementData[curIndex++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        }
        System.arraycopy(elementData, 0, a, 0, size);
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
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c.size() == 0) {
            return false;
        }
        if (index < size) {
            checkIndex(index);
        }
        if (size + c.size() > elementData.length) {
            elementData = grow(size + c.size());
        }
        System.arraycopy(elementData, index, elementData, index + c.size(), size - index);
        System.arraycopy(c.toArray(), 0, elementData, index, c.size());
        modCount++;
        size += c.size();
        return true;
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
        return elementData[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        modCount++;
        E prevValue = elementData[index];
        elementData[index] = element;
        return prevValue;
    }

    @Override
    public void add(int index, E element) {
        checkIndex(index);
        modCount++;
        if (elementData.length == size) {
            elementData = grow((int) (elementData.length * INCREASE_FACTOR));
        }
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        modCount++;
        E prevValue = elementData[index];
        System.arraycopy(elementData, index + 1, elementData, index, size - index - 1);
        elementData[--size] = null;
        return prevValue;
    }

    @Override
    public int indexOf(Object o) {
        return indexOfRange(o, 0, size);
    }

    @Override
    public int lastIndexOf(Object o) {
        return lastIndexOfRange(o, 0, size);
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
            int lastRet = -1;

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
                lastRet = curIndex;
                return elementData[curIndex++];
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
                return elementData[--curIndex];
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
                if (lastRet < 0) {
                    throw new IllegalStateException();
                }
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                lastRet = -1;
                CustomArrayList.this.remove(curIndex);
                expectedModCount = modCount;
            }

            @Override
            public void set(E e) {
                if (lastRet < 0) {
                    throw new IllegalStateException();
                }
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                lastRet = -1;
                CustomArrayList.this.set(curIndex, e);
            }

            @Override
            public void add(E e) {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                CustomArrayList.this.add(curIndex, e);
                curIndex++;
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
        return new SubList(this, fromIndex, toIndex);
    }

    private class SubList implements List<E> {
        private final CustomArrayList<E> root;
        private final SubList parent;
        private final int offset;
        private int modCount;
        private int size;

        public SubList(CustomArrayList<E> root, int fromInclusive, int toExclusive) {
            this.root = root;
            this.parent = null;
            this.offset = fromInclusive;
            this.size = toExclusive - fromInclusive;
            this.modCount = root.modCount;
        }

        public SubList(SubList parent, int fromInclusive, int toExclusive) {
            this.root = parent.root;
            this.parent = parent;
            this.offset = parent.offset + fromInclusive;
            this.size = toExclusive - fromInclusive;
            this.modCount = parent.modCount;
        }

        @Override
        public int size() {
            checkForModification();
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
            checkForModification();
            return new Iterator<E>() {
                final int expectedModCount = CustomArrayList.this.modCount;
                int curIndex;

                @Override
                public boolean hasNext() {
                    return curIndex < size;
                }

                @Override
                public E next() {
                    if (expectedModCount != CustomArrayList.this.modCount) {
                        throw new ConcurrentModificationException();
                    }
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return root.elementData[curIndex++ + offset];
                }
            };
        }

        @Override
        public Object[] toArray() {
            checkForModification();
            return Arrays.copyOfRange(root.elementData, offset, offset + size);
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> T[] toArray(T[] a) {
            checkForModification();
            if (a.length < size) {
                return (T[]) Arrays.copyOfRange(root.elementData, offset, offset + size, a.getClass());
            }
            System.arraycopy(root.elementData, offset, a, 0, size);
            return a;
        }

        @Override
        public boolean add(E e) {
            checkForModification();
            add(size, e);
            return true;
        }

        @Override
        public boolean remove(Object o) {
            checkForModification();
            int index = indexOf(o);
            if (index != -1) {
                remove(index);
                return true;
            }
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            checkForModification();
            for (Object elem : c) {
                if (!contains(elem)) {
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
            checkIndex(index);
            checkForModification();
            if (c.size() == 0) {
                return false;
            }
            if (index < size) {
                checkIndex(index);
            }
            root.addAll(offset + index, c);
            updateSizeAndModCount(c.size());
            return true;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            checkForModification();
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
            checkForModification();
            boolean changeFlag = false;
            for (int i = 0; i < size; i++) {
                if (!c.contains(root.elementData[i])) {
                    remove(i--);
                    changeFlag = true;
                }
            }
            return changeFlag;
        }

        @Override
        public void clear() {
            int shift = offset + size;
            System.arraycopy(root.elementData, shift, root.elementData, offset, root.size - shift);
            Arrays.fill(root.elementData, root.size - size, root.size, null);
            root.size -= size;
            modCount++;
            updateSizeAndModCount(-size);
        }

        @Override
        public E get(int index) {
            checkIndex(index);
            checkForModification();
            return root.elementData[offset + index];
        }

        @Override
        public E set(int index, E element) {
            checkIndex(index);
            checkForModification();
            E prevValue = root.get(offset + index);
            root.elementData[offset+index] = element;
            return prevValue;
        }

        @Override
        public void add(int index, E element) {
            checkIndex(index);
            checkForModification();
            root.add(offset + index, element);
            updateSizeAndModCount(1);
        }

        @Override
        public E remove(int index) {
            checkIndex(index);
            checkForModification();
            E result = root.remove(offset + index);
            updateSizeAndModCount(-1);
            return result;
        }

        @Override
        public int indexOf(Object o) {
            checkForModification();
            int index = root.indexOfRange(o, offset, offset + size);
            if (index >= 0) {
                return index - offset;
            }
            return -1;
        }

        @Override
        public int lastIndexOf(Object o) {
            checkForModification();
            int lastIndex = root.lastIndexOfRange(o, offset, offset + size);
            if (lastIndex >= 0) {
                return lastIndex - offset;
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
            checkForModification();
            return new ListIterator<E>() {
                int expectedModCount = modCount;
                int curIndex = index + offset;
                int lastRet = -1;

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
                    lastRet = curIndex;
                    return elementData[curIndex++];
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
                    return elementData[--curIndex];
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
                    if (lastRet < 0) {
                        throw new IllegalStateException();
                    }
                    if (expectedModCount != modCount) {
                        throw new ConcurrentModificationException();
                    }
                    lastRet = -1;
                    SubList.this.remove(curIndex);
                    expectedModCount = modCount;
                }

                @Override
                public void set(E e) {
                    if (lastRet < 0) {
                        throw new IllegalStateException();
                    }
                    if (expectedModCount != modCount) {
                        throw new ConcurrentModificationException();
                    }
                    lastRet=-1;
                    SubList.this.set(curIndex, e);
                }

                @Override
                public void add(E e) {
                    if (expectedModCount != modCount) {
                        throw new ConcurrentModificationException();
                    }
                    SubList.this.add(curIndex, e);
                    curIndex++;
                    expectedModCount = modCount;
                }
            };
        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            checkIndex(fromIndex);
            checkIndex(toIndex);
            return new SubList(this, fromIndex, toIndex);
        }

        private void checkForModification() {
            if (modCount != CustomArrayList.this.modCount)
                throw new ConcurrentModificationException();
        }

        private void updateSizeAndModCount(int sizeChange) {
            modCount = CustomArrayList.this.modCount;
            size += sizeChange;
            if (parent != null) {
                parent.updateSizeAndModCount(sizeChange);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private E[] grow(int newCapacity) {
        if (newCapacity > MAX_SIZE) {
            throw new OutOfMemoryError();
        }
        E[] newElementData = (E[]) new Object[newCapacity];
        System.arraycopy(elementData, 0, newElementData, 0, size);
        return newElementData;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int indexOfRange(Object o, int fromInclusive, int toExclusive) {
        for (int i = fromInclusive; i < toExclusive; i++) {
            if (Objects.equals(elementData[i], o)) {
                return i;
            }
        }
        return -1;
    }

    private int lastIndexOfRange(Object o, int fromInclusive, int toExclusive) {
        for (int i = toExclusive - 1; i >= fromInclusive; i--) {
            if (Objects.equals(elementData[i], o)) {
                return i;
            }
        }
        return -1;
    }

}

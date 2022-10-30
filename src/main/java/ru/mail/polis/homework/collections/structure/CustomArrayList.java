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
 * <p>
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 16;

    private int size;
    Object[] array;
    private int modCount;

    public CustomArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public CustomArrayList(int capacity) {
        array = new Object[capacity];
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
        return listIterator();
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
        add(size, e);
        return true;
    }

    @Override
    public void add(int index, E element) {
        checkIndex(index);
        if (array.length < size + 1) {
            array = grow((size + 1) - array.length);
        }
        if (index != size) {
            System.arraycopy(array, index, array, index + 1, size - index);
        }
        modCount++;
        size++;
        array[index] = element;
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
        for (Object obj : c) {
            if (!contains(obj)) {
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
            while (remove(obj)) {
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
                remove(i);
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
        modCount++;
        Object prev = array[index];
        System.arraycopy(array, index + 1, array, index, size - 1 - index);
        array[--size] = null;
        return (E) prev;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (Objects.equals(array[i], null)) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(array[i])) {
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
                if (Objects.equals(array[i], null)) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(array[i])) {
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
    @SuppressWarnings("unchecked")
    public ListIterator<E> listIterator(int index) {
        return new ListIterator<E>() {
            public int currentIndex = index;
            private int expectedModCount = modCount;
            private boolean currentNotNull = false;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public E next() {
                checkModificationCount();
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                currentNotNull = true;
                return (E) array[currentIndex++];
            }

            @Override
            public boolean hasPrevious() {
                return currentIndex != 0;
            }

            @Override
            public E previous() {
                checkModificationCount();
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                currentNotNull = true;
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
                if (!currentNotNull) {
                    throw new IllegalStateException();
                }
                checkModificationCount();
                CustomArrayList.this.remove(currentIndex);
                currentNotNull = false;
                expectedModCount = modCount;
            }

            @Override
            public void set(E e) {
                if (!currentNotNull) {
                    throw new IllegalStateException();
                }
                checkModificationCount();
                CustomArrayList.this.set(currentIndex, e);
            }

            @Override
            public void add(E e) {
                checkModificationCount();
                currentIndex += expectedModCount - modCount;
                CustomArrayList.this.add(currentIndex, e);
                currentIndex++;
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
        if (fromIndex < 0 || fromIndex > size || toIndex < 0 || toIndex > size) {
            throw new IndexOutOfBoundsException();
        }
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
        }
        return new SubList<E>(this, fromIndex, toIndex);
    }

    private static class SubList<E> implements List<E> {
        private final CustomArrayList<E> root;
        private final SubList<E> parent;
        private int size;
        private final int offset;
        private int modCount;

        public SubList(CustomArrayList<E> root, int fromInclusive, int toExclusive) {
            this.offset = fromInclusive;
            this.root = root;
            this.size = toExclusive - fromInclusive;
            this.parent = null;
            this.modCount = root.modCount;
        }

        private SubList(SubList<E> parent, int fromInclusive, int toExclusive) {
            this.offset = parent.offset + fromInclusive;
            this.root = parent.root;
            this.size = toExclusive - fromInclusive;
            this.parent = parent;
            this.modCount = parent.modCount;
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
            int index = indexOf(o);
            if (index == -1) {
                return false;
            }
            remove(index);
            return true;
        }

        @Override
        public Iterator<E> iterator() {
            return listIterator();
        }

        @Override
        public Object[] toArray() {
            return Arrays.copyOfRange(root.array, offset, offset + size);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] toArray(T[] a) {
            if (a.length < size) {
                return (T[]) Arrays.copyOfRange(root.array, offset, offset + size, a.getClass());
            }
            System.arraycopy(root.array, offset, a, 0, size);
            if (a.length > size) {
                Arrays.fill(a, size, a.length, null);
            }
            return a;
        }

        @Override
        public boolean add(E e) {
            add(size, e);
            return true;
        }

        @Override
        public void add(int index, E element) {
            root.add(index + offset, element);
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
            for (Object obj : c) {
                if (!contains(obj)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            return addAll(offset + size, c);
        }

        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            checkIndex(index);
            if (root.array.length < size + c.size()) {
                root.array = root.grow((size + c.size()) - root.array.length);
            }
            if (index != root.size) {
                System.arraycopy(root.array, index + offset, root.array, index + c.size() + offset, size - index);
            }
            System.arraycopy(c.toArray(), 0, root.array, index + offset, c.size());
            size += c.size();
            modCount++;
            return true;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            boolean changed = false;
            for (Object obj : c) {
                while (remove(obj)) {
                    changed = true;
                }
            }
            return changed;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            boolean changed = false;
            for (int i = offset; i < offset + size; i++) {
                if (!c.contains(root.array[i])) {
                    remove(i);
                    changed = true;
                    i--;
                }
            }
            return changed;
        }

        @Override
        public void clear() {
            Object[] temp = new Object[root.size - size];
            System.arraycopy(root.array, 0, temp, 0, offset);
            System.arraycopy(root.array, offset + size, temp, offset, root.size - offset - size);
            root.array = temp;
            root.size = temp.length;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E get(int index) {
            checkIndex(index);
            return (E) root.array[index + offset];
        }

        @Override
        @SuppressWarnings("unchecked")
        public E set(int index, E element) {
            checkIndex(index);
            E prev = (E) root.array[index + offset];
            root.array[index + offset] = element;
            return prev;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E remove(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException(Integer.toString(index));
            }
            modCount++;
            Object prev = root.array[offset + index];
            System.arraycopy(root.array, index + 1 + offset, root.array, index + offset, size - 1 - index + offset);
            root.array[--size + offset] = null;
            return (E) prev;
        }

        @Override
        public int indexOf(Object o) {
            if (o == null) {
                for (int i = offset; i < offset + size; i++) {
                    if (Objects.equals(root.array[i], null)) {
                        return i;
                    }
                }
            } else {
                for (int i = offset; i < offset + size; i++) {
                    if (o.equals(root.array[i])) {
                        return i;
                    }
                }
            }
            return -1;
        }

        @Override
        public int lastIndexOf(Object o) {
            if (o == null) {
                for (int i = offset + size - 1; i >= offset; i--) {
                    if (Objects.equals(root.array[i], null)) {
                        return i;
                    }
                }
            } else {
                for (int i = size - 1; i >= 0; i--) {
                    if (o.equals(root.array[i])) {
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
                public int currentIndex = index;
                private int expectedModCount = root.modCount;
                private boolean currentNotNull = false;

                @Override
                public boolean hasNext() {
                    return currentIndex < size;
                }

                @Override
                @SuppressWarnings("unchecked")
                public E next() {
                    checkModificationCount();
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    currentNotNull = true;
                    return (E) root.array[currentIndex++ + offset];
                }

                @Override
                public boolean hasPrevious() {
                    return currentIndex != 0;
                }

                @Override
                @SuppressWarnings("unchecked")
                public E previous() {
                    checkModificationCount();
                    if (!hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    currentNotNull = true;
                    return (E) root.array[--currentIndex];
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
                    if (!currentNotNull) {
                        throw new IllegalStateException();
                    }
                    checkModificationCount();
                    SubList.this.remove(currentIndex + offset);
                    currentNotNull = false;
                    expectedModCount = modCount;
                }

                @Override
                public void set(E e) {
                    if (!currentNotNull) {
                        throw new IllegalStateException();
                    }
                    checkModificationCount();
                    SubList.this.set(currentIndex + offset, e);
                }

                @Override
                public void add(E e) {
                    checkModificationCount();
                    currentIndex += expectedModCount - modCount;
                    SubList.this.add(currentIndex + offset, e);
                    currentIndex++;
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
            return new SubList<>(this, fromIndex, toIndex);
        }

        private void checkIndex(int index) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException(Integer.toString(index));
            }
        }
    }

    void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
    }

    private Object[] grow(int notEnough) {
        int newCapacity = array.length + Math.max(notEnough, (array.length >> 1));
        return Arrays.copyOf(array, newCapacity);
    }

}

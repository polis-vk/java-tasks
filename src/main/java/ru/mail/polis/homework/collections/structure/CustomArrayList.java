package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 *
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    private static final int INITIAL_CAPACITY = 10;
    private static final int GROW_FACTOR = 2;

    private E[] data;
    private int size = 0;
    private int modCount = 0;

    @SuppressWarnings("unchecked")
    public CustomArrayList(int initialCapacity) {
        this.data = (E[]) new Object[initialCapacity];
    }

    public CustomArrayList() {
        this(INITIAL_CAPACITY);
    }

    public CustomArrayList(E[] data) {
        this.data = data;
        this.size = data.length;
    }

    @SuppressWarnings("unchecked")
    public CustomArrayList(Collection<? extends E> c) {
        this.data = (E[]) new Object[c.size()];
        addAll(c);
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
            final int fixedModCount = modCount;
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (index >= size) {
                    throw new NoSuchElementException();
                }
                return data[index++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(data, size, a.getClass());
        }

        // В переданном массиве достаточно места.
        System.arraycopy(data, 0, a, 0, size);
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

        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
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
        if (index != size) {
            checkIndexBounds(index, size);
        }

        if (c.size() == 0) {
            return false;
        }

        if (data.length - size < c.size()) {
            data = grow(size + c.size());
        }

        // Нужно сдвинуть уже имеющиеся элементы.
        if (index != size) {
            System.arraycopy(data, index, data, index + c.size(), size - index);
        }

        System.arraycopy(c.toArray(), 0, data, index, c.size());
        modCount++;
        size += c.size();

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isChanged = false;

        for (Object elem : c) {
            while (remove(elem)) {
                isChanged = true;
            }
        }

        return isChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isChanged = false;

        int curIndex = 0;
        while (curIndex < size) {
            if (!c.contains(data[curIndex])) {
                remove(curIndex);
                isChanged = true;
            } else {
                curIndex++;
            }
        }

        return isChanged;
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
        checkIndexBounds(index, size);
        return data[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndexBounds(index, size);
        modCount++;
        E result = get(index);

        data[index] = element;
        return result;
    }

    @Override
    public void add(int index, E element) {
        if (index != size) {
            checkIndexBounds(index, size);
        }
        modCount++;

        // Увеличиваем емкость, если больше нет места.
        if (size == data.length) {
            data = grow(data.length * GROW_FACTOR);
        }

        // Сдвигаем существующие элементы.
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        checkIndexBounds(index, size);
        modCount++;
        E result = data[index];

        size--;
        System.arraycopy(data, index + 1, data, index, size - index);
        data[size] = null;
        return result;
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
        checkIndexBounds(index, size);

        return new ListIterator<E>() {
            int fixedModCount = modCount;
            int curIndex = index;

            @Override
            public boolean hasNext() {
                return curIndex < size;
            }

            @Override
            public E next() {
                checkForMod();
                if (curIndex >= size) {
                    throw new IndexOutOfBoundsException();
                }
                return data[curIndex++];
            }

            @Override
            public boolean hasPrevious() {
                return curIndex > 0;
            }

            @Override
            public E previous() {
                checkForMod();
                if (curIndex <= 0) {
                    throw new NoSuchElementException();
                }
                return data[--curIndex];
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
                checkForMod();
                CustomArrayList.this.remove(curIndex);
                fixedModCount = modCount;
            }

            @Override
            public void set(E e) {
                checkForMod();
                CustomArrayList.this.set(curIndex, e);
                fixedModCount = modCount;
            }

            @Override
            public void add(E e) {
                checkForMod();
                CustomArrayList.this.add(curIndex, e);
                curIndex++;
                fixedModCount = modCount;
            }

            private void checkForMod() {
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> subList(int fromIndex, int toIndex) {
        checkSubListIndexes(fromIndex, toIndex, size);
        return new SubList<>(this, fromIndex, toIndex);
    }

    private static class SubList<E> implements List<E> {
        private final CustomArrayList<E> root;
        private final SubList<E> parent;
        private final int offset;
        private int size;
        private int modCount;

        public SubList(CustomArrayList<E> root, int fromIndex, int toIndex) {
            this.root = root;
            this.parent = null;
            this.offset = fromIndex;
            this.size = toIndex - fromIndex;
            this.modCount = root.modCount;
        }

        @Override
        public int size() {
            checkForMod();
            return size;
        }

        @Override
        public boolean isEmpty() {
            checkForMod();
            return size == 0;
        }

        @Override
        public boolean contains(Object o) {
            return indexOf(o) != -1;
        }

        @Override
        public Iterator<E> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            checkForMod();
            return Arrays.copyOfRange(root.data, offset, offset + size);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] toArray(T[] a) {
            checkForMod();
            if (a.length < size) {
                return (T[]) Arrays.copyOfRange(root.data, offset, offset + size, a.getClass());
            }

            // В переданном массиве достаточно места.
            System.arraycopy(root.data, offset, a, 0, size);
            return a;
        }

        @Override
        public boolean add(E e) {
            add(size, e);
            return true;
        }

        @Override
        public boolean remove(Object o) {
            checkForMod();
            int index = indexOf(o);

            if (index == -1) {
                return false;
            }
            remove(index);
            return true;
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
            checkForMod();
            if (index != size) {
                checkIndexBounds(index, size);
            }

            if (c.size() == 0) {
                return false;
            }

            root.addAll(index + offset, c);
            updateSizeAndModCount(c.size());
            return true;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public E get(int index) {
            checkIndexBounds(index, size);
            checkForMod();
            return root.get(offset + index);
        }

        @Override
        public E set(int index, E element) {
            checkIndexBounds(index, size);
            checkForMod();
            updateSizeAndModCount(0);
            E result = root.get(offset + index);

            root.set(offset + index, element);
            return result;
        }

        @Override
        public void add(int index, E element) {
            checkForMod();
            if (index != size) {
                checkIndexBounds(index, size);
            }

            root.add(index + offset, element);
            updateSizeAndModCount(1);
        }

        @Override
        public E remove(int index) {
            checkForMod();
            checkIndexBounds(index, size);

            E result = root.remove(index + offset);
            updateSizeAndModCount(-1);
            return result;
        }

        @Override
        public int indexOf(Object o) {
            checkForMod();
            int result = root.indexOfRange(o, offset, offset + size);
            return result != -1 ? result - offset : -1;
        }

        @Override
        public int lastIndexOf(Object o) {
            checkForMod();
            int result = root.lastIndexOfRange(o, offset, offset + size);
            return result != -1 ? result - offset : -1;
        }

        @Override
        public ListIterator<E> listIterator() {
            return null;
        }

        @Override
        public ListIterator<E> listIterator(int index) {
            return null;
        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            checkSubListIndexes(fromIndex, toIndex, size);
            return new SubList<>(this, fromIndex, toIndex);
        }

        private SubList(SubList<E> parent, int fromIndex, int toIndex) {
            this.root = parent.root;
            this.parent = parent;
            this.offset = parent.offset + fromIndex;
            this.size = toIndex - fromIndex;
            this.modCount = parent.modCount;
        }

        private void checkForMod() {
            if (root.modCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }

        private void updateSizeAndModCount(int sizeChange) {
            SubList<E> slist = this;
            do {
                slist.size += sizeChange;
                slist.modCount = root.modCount;
                slist = slist.parent;
            } while (slist != null);
        }
    }

    /**
     * Увеличить емкость содержащего элементы массива. Причем, если новое значение ёмкости будет меньше или равно
     * исходной - ничего не произойдет. Если же оно будет больше исходной - выделится новая память, куда переместятся
     * все элементы.
     * @param newCapacity - новое значение ёмкости коллекции.
     * @return Массив новой (или старой ёмкости), содержащий предыдущие элементы.
     */
    @SuppressWarnings("unchecked")
    private E[] grow(int newCapacity) {
        if (newCapacity <= data.length) {
            return data;
        }

        E[] newArray = (E[]) new Object[newCapacity];
        System.arraycopy(data, 0, newArray, 0, size);
        return newArray;
    }

    private static void checkIndexBounds(int index, int size) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds.");
        }
    }

    private static void checkSubListIndexes(int fromIndex, int toIndex, int size) {
        if (fromIndex < 0 || toIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("Incorrect indexes");
        }
    }

    public int indexOfRange(Object o, int from, int to) {
        if (o == null) {
            for (int i = from; i < to; i++) {
                if (data[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = from; i < to; i++) {
                if (o.equals(data[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    public int lastIndexOfRange(Object o, int from, int to) {
        if (o == null) {
            for (int i = to - 1; i >= from; i--) {
                if (data[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = to - 1; i >= from; i--) {
                if (o.equals(data[i])) {
                    return i;
                }
            }
        }

        return -1;
    }
}

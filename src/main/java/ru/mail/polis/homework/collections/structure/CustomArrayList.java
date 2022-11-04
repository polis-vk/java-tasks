package ru.mail.polis.homework.collections.structure;


import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
import java.util.Objects;
import java.util.ListIterator;
import java.util.AbstractList;
import java.util.RandomAccess;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 * <p>
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    private Object[] internalArray;
    private int size;
    private int modCount;
    private final int DEFAULT_SIZE = 16;

    public CustomArrayList() {
        this.internalArray = new Object[DEFAULT_SIZE];
    }

    public CustomArrayList(Collection<E> collection) {
        this.internalArray = collection.toArray();
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
            int lastEl = 0;
            final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                return lastEl != size;
            }

            @Override
            public E next() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }

                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                return (E) internalArray[lastEl++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(internalArray, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(internalArray, size, a.getClass());
        }
        System.arraycopy(internalArray, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public boolean add(E e) {

        if (size >= internalArray.length) {
            Object[] tempArr = new Object[(int) (internalArray.length * 1.5 + 1)];
            System.arraycopy(internalArray, 0, tempArr, 0, internalArray.length);
            tempArr[size] = e;
            internalArray = tempArr;
        } else {
            internalArray[size] = e;
        }

        size++;
        modCount++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int indexOfO = 0;
        if (Objects.equals(null, o)) {
            for (int i = 0; i < size; i++) {
                if (internalArray[i] == null) {
                    indexOfO = i;
                    break;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (internalArray[i].equals(o)) {
                    indexOfO = i;
                    break;
                }
            }
        }

        if (indexOfO == size - 1 && !internalArray[indexOfO].equals(o)) {
            return false;
        }

        if (indexOfO == size - 1) {
            internalArray[size--] = null;
            return true;
        }

        System.arraycopy(internalArray, indexOfO + 1, internalArray, indexOfO, size - indexOfO);
        internalArray[size--] = null;
        modCount++;

        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Object[] arrayFromC = c.toArray();

        for (int i = 0; i < arrayFromC.length; i++) {
            if (!contains(arrayFromC[i])) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Object[] arrayOfNewElements = c.toArray();

        if (internalArray.length <= size + arrayOfNewElements.length) {
            Object[] newInternalArray = new Object[internalArray.length + arrayOfNewElements.length + 1];
            System.arraycopy(internalArray, 0, newInternalArray, 0, size);
            System.arraycopy(arrayOfNewElements, 0, newInternalArray, size, arrayOfNewElements.length);
            internalArray = newInternalArray;
        } else {
            System.arraycopy(arrayOfNewElements, 0, internalArray, size, arrayOfNewElements.length);
        }

        size += arrayOfNewElements.length;
        modCount += arrayOfNewElements.length;
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        Object[] arrayOfNewElements = c.toArray();

        if (internalArray.length <= size + arrayOfNewElements.length) {
            Object[] newInternalArray = new Object[internalArray.length + arrayOfNewElements.length + 1];
            System.arraycopy(internalArray, 0, newInternalArray, 0, index);
            System.arraycopy(arrayOfNewElements, 0, newInternalArray, index, arrayOfNewElements.length);
            System.arraycopy(internalArray, index, newInternalArray, arrayOfNewElements.length + index, internalArray.length - index);
            internalArray = newInternalArray;
        } else {
            System.arraycopy(internalArray, index, internalArray, arrayOfNewElements.length + index, size - index);
            System.arraycopy(arrayOfNewElements, 0, internalArray, index, arrayOfNewElements.length);
        }

        size += arrayOfNewElements.length;
        modCount += arrayOfNewElements.length;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Object[] arrFromC = c.toArray();

        for (Object o : arrFromC) {
            while (this.contains(o)) {
                this.remove(o);
            }
        }

        modCount++;

        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {

        for (int i = 0; i < size; i++) {
            if (!c.contains(internalArray[i])) {
                this.remove(i);
                i--;
            }
        }

        return true;
    }

    @Override
    public void clear() {
        this.size = 0;
        internalArray = new Object[0];
    }

    @Override
    public E get(int index) {
        return (E) internalArray[index];
    }

    @Override
    public E set(int index, E element) {

        if (index >= size || index < 0) {
            throw new NoSuchElementException();
        }

        E result = (E) internalArray[index];
        internalArray[index] = element;

        return result;
    }

    @Override
    public void add(int index, E element) {
        Object[] newInternalArray;
        if (size >= internalArray.length) {
            newInternalArray = new Object[(int) (internalArray.length * 1.5 + 1)];
            System.arraycopy(internalArray, 0, newInternalArray, 0, index);
            newInternalArray[index] = element;
            System.arraycopy(internalArray, index, newInternalArray, index + 1, size - index);
            internalArray = newInternalArray;
        } else {
            System.arraycopy(internalArray, index, internalArray, index + 1, size - index);
            internalArray[index] = element;
        }

        size++;
        modCount++;
    }

    @Override
    public E remove(int index) {

        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        E result = (E) internalArray[index];
        System.arraycopy(internalArray, index + 1, internalArray, index, size - index);
        internalArray[size--] = null;
        modCount++;

        return result;
    }

    @Override
    public int indexOf(Object o) {
        if (Objects.equals(null, o)) {
            for (int i = 0; i < size; i++) {
                if (internalArray[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (internalArray[i].equals(o)) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int i = size - 1;
        if (o == null) {
            for (; i >= 0; i--) {
                if (internalArray[i] == null) {
                    return i;
                }
            }
        } else {
            for (; i >= 0; i--) {
                if (internalArray[i].equals(o)) {
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
        return new ListItr(index);
    }

    private class ListItr implements ListIterator<E> {
        int pointer;
        int lastReturned = -1;
        int expectedModCount = modCount;

        ListItr(int index) {
            this.pointer = index;
        }

        @Override
        public boolean hasNext() {
            return pointer != size;
        }

        @Override
        public E next() {
            if (hasNext()) {
                lastReturned = pointer;
                return (E) internalArray[pointer++];
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public boolean hasPrevious() {
            return pointer != 0;
        }

        @Override
        public E previous() {
            int i = pointer - 1;
            if (i < 0) {
                throw new NoSuchElementException();
            }

            if (i >= internalArray.length) {
                throw new ConcurrentModificationException();
            }
            pointer = i;
            lastReturned = i;
            return (E) internalArray[i];
        }

        @Override
        public int nextIndex() {
            return pointer;
        }

        @Override
        public int previousIndex() {
            return pointer - 1;
        }

        @Override
        public void remove() {
            if (lastReturned < 0) {
                throw new IllegalStateException();
            }

            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }

            try {
                CustomArrayList.this.remove(lastReturned);
                pointer = lastReturned;
                lastReturned = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void set(E e) {
            if (lastReturned < 0) {
                throw new IllegalStateException();
            }

            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }

            try {
                CustomArrayList.this.set(lastReturned, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }

            try {
                int i = pointer;
                CustomArrayList.this.add(i, e);
                pointer = i + 1;
                lastReturned = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        return new SubList<E>(this, fromIndex, toIndex);
    }

    private static class SubList<E> extends AbstractList<E> implements RandomAccess {

        private final CustomArrayList<E> root;
        private final SubList<E> parent;
        private final int offset;
        private int size;

        public SubList(CustomArrayList<E> root, int fromIndex, int toIndex) {
            this.root = root;
            this.parent = null;
            this.offset = fromIndex;
            this.size = toIndex - fromIndex;
            this.modCount = root.modCount;
        }

        public SubList(SubList<E> parent, int fromIndex, int toIndex) {
            this.root = parent.root;
            this.parent = parent;
            this.offset = parent.offset + fromIndex;
            this.size = toIndex - fromIndex;
            this.modCount = parent.modCount;
        }

        @Override
        public E get(int index) {
            checkForComodification();
            checkIndex(index, size);
            return (E) root.internalArray[offset + index];
        }

        @Override
        public int size() {
            checkForComodification();
            return size;
        }

        public E set(int index, E element) {
            checkIndex(index, size);
            checkForComodification();
            E oldValue = (E) root.internalArray[offset + index];
            root.internalArray[offset + index] = element;
            return oldValue;
        }

        public void add(int index, E element) {
            checkIndex(index, size);
            checkForComodification();
            root.add(offset + index, element);
            this.size++;
            this.modCount = root.modCount;
        }

        private void checkForComodification() {
            if (root.modCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }

        private void checkIndex(int index, int length) {
            if (index < 0 || index >= length) {
                throw new IndexOutOfBoundsException();
            }
        }
    }
}

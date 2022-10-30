package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;


/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 * <p>
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    private final static int DEFAULT_ARRAY_SIZE = 10;
    private Object[] array;
    private int size = 0;
    private int modCount = 0;

    public CustomArrayList(int capacity) {
        array = capacity > 0 ? new Object[capacity] : new Object[]{};
    }

    public CustomArrayList(Object[] array) {
        this.array = array;
        this.size = array.length;
    }

    public CustomArrayList(CustomArrayList<E> list, int fromIndex, int toIndex) {
        array = new Object[toIndex - fromIndex];
        System.arraycopy(list.array, fromIndex, array, 0, toIndex - fromIndex);
        size = array.length;
    }

    public CustomArrayList() {
        array = new Object[DEFAULT_ARRAY_SIZE];
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
        return new Itr(modCount);
    }

    private class Itr implements Iterator<E> {
        int curPos;
        int expectedModCount;

        public Itr(int modCount) {
            this.expectedModCount = modCount;
        }

        public boolean hasNext() {
            return curPos < size;
        }

        public E next() {
            checkForComodification();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E res = (E) array[curPos];
            curPos++;
            return res;
        }

        final void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(array, size, a.getClass());
        }
        System.arraycopy(array, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public boolean add(E e) {
        add(size, e);
        return true;
    }

    private Object[] grow() {
        Object[] grownArray = new Object[(int) (array.length * 1.5)];
        System.arraycopy(array, 0, grownArray, 0, array.length);
        return grownArray;
    }

    @Override
    public boolean remove(Object o) {
        int i = indexOf(o);
        if (i < 0) {
            return false;
        }
        remove(i);
        return true;
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
        for (E elem : c) {
            add(elem);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        int curIndex = index;
        for (E elem : c) {
            add(curIndex++, elem);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object o : c) {
            if (!remove(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int i = 0;
        while (i < size) {
            if (!c.contains(array[i])) {
                remove(array[i]);
                continue;
            }
            i++;
        }
        return true;
    }

    @Override
    public void clear() {
        array = new Object[DEFAULT_ARRAY_SIZE];
        size = 0;
        modCount++;
    }

    @Override
    public E get(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return (E) array[index];
    }

    @Override
    public E set(int index, E element) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        E res = (E) array[index];
        array[index] = element;
        modCount++;
        return res;
    }

    @Override
    public void add(int index, E element) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (size == array.length) {
            array = grow();
        }
        System.arraycopy(array, index,
                array, index + 1,
                size - index);
        array[index] = element;
        size++;
        modCount++;
    }

    @Override
    public E remove(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        E res = (E) array[index];
        System.arraycopy(array, index + 1,
                array, index,
                size - index);
        modCount++;
        size--;
        return res;
    }

    @Override
    public int indexOf(Object o) {
        Object[] es = array;
        for (int i = 0; i < size; i++) {
            if (o == null && es[i] == null || Objects.equals(o, es[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Object[] es = array;
        for (int i = size - 1; i >= 0; i--) {
            if (o == null && es[i] == null || Objects.equals(o, es[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListItr(modCount, 0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListItr(modCount, index);
    }

    private class ListItr extends Itr implements ListIterator<E> {
        public ListItr(int modCount, int index) {
            super(modCount);
            this.curPos = index;
        }

        @Override
        public boolean hasPrevious() {
            return curPos != 0;
        }

        @Override
        public E previous() {
            checkForComodification();
            int i = curPos - 1;
            if (i < 0) {
                throw new NoSuchElementException();
            }
            if (i >= array.length) {
                throw new ConcurrentModificationException();
            }
            curPos = i;
            return (E) array[i];
        }

        @Override
        public int nextIndex() {
            return curPos;
        }

        @Override
        public int previousIndex() {
            return curPos - 1;
        }

        @Override
        public void remove() {
            if (curPos - 1 < 0) {
                throw new IllegalStateException();
            }
            checkForComodification();

            try {
                CustomArrayList.this.remove(curPos - 1);
                curPos--;
                expectedModCount++;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            super.forEachRemaining(action);
        }

        @Override
        public void set(E e) {
            if (curPos - 1 < 0) {
                throw new IllegalStateException();
            }
            checkForComodification();
            try {
                CustomArrayList.this.set(curPos - 1, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void add(E e) {
            checkForComodification();
            try {
                int i = curPos;
                CustomArrayList.this.add(i, e);
                curPos++;
                expectedModCount++;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
        } else if (toIndex > size) {
            throw new IndexOutOfBoundsException("toIndex = " + toIndex);
        } else if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex(" + fromIndex +
                    ") > toIndex(" + toIndex + ")");
        }
        return new CustomArrayList<E>(this, fromIndex, toIndex);
    }
}

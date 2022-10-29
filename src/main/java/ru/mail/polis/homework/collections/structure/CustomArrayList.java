package ru.mail.polis.homework.collections.structure;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.ListIterator;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 *
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {

    private static final int BASE_SIZE_MULTIPLIER = 2;
    private static final int BASE_SIZE = 10;
    private int size;
    private int timesChanged;
    private E[] array;

    public CustomArrayList() {
        array = (E[]) new Object[BASE_SIZE];
    }

    public CustomArrayList(E[] array) {
        this.array = array;
        this.size = array.length;
    }

    public CustomArrayList(Collection<E> collection) {
        array = (E[]) collection.toArray();
        size = array.length;
    }

    private void enlarge(int times) {
        E[] newArray = (E[]) new Object[size * times];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
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

            private final int timesChangedAtStart = timesChanged;
            private int next;

            @Override
            public boolean hasNext() {
                return (next < size());
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                } else if (timesChangedAtStart != timesChanged) {
                    throw new ConcurrentModificationException();
                } else {
                    E ans = array[next];
                    next++;
                    return ans;
                }
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length >= size) {
            System.arraycopy(array, 0, a, 0, size);
            return a;
        } else {
            return (T[]) Arrays.copyOf(array, size, a.getClass());
        }
    }

    @Override
    public boolean add(E e) {
        if (size == array.length) {
            enlarge(BASE_SIZE_MULTIPLIER);
        }
        array[size] = e;
        size++;
        timesChanged++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int idOfObject = indexOf(o);
        if (idOfObject == -1) {
            return false;
        }
        remove(idOfObject);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if (!contains(e)) {
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
        int offset = 0;
        for (E e : c) {
            add(index + offset, e);
            offset++;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean listChanged = false;
        for (Object e : c) {
            boolean isRemovalSuccessful = remove(e);
            listChanged = listChanged || isRemovalSuccessful;
        }
        return listChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean listChanged = false;
        for (int i = 0; i < size; i++) {
            Object e = array[i];
            if (!c.contains(e)) {
                remove(e);
                i--;
                listChanged = true;
            }
        }
        return listChanged;
    }

    @Override
    public void clear() {
        array = (E[]) new Object[BASE_SIZE];
        size = 0;
        timesChanged++;
    }

    @Override
    public E get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return array[index];
    }

    @Override
    public E set(int index, E element) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        timesChanged++;
        E answer = array[index];
        array[index] = element;
        return answer;
    }

    @Override
    public void add(int index, E element) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (size == array.length) {
            enlarge(BASE_SIZE_MULTIPLIER);
        }
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
        timesChanged++;
    }

    @Override
    public E remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        E answer = array[index];
        System.arraycopy(array, index + 1, array, index, size - index);
        size--;
        timesChanged++;
        return answer;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(o, get(i))) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(o, get(i))) {
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
        return new ListIterator<E>() {

            private int timesChangedAtStart = timesChanged;
            private int current = index;

            @Override
            public boolean hasNext() {
                return (current < size());
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                } else if (timesChangedAtStart != timesChanged) {
                    throw new ConcurrentModificationException();
                } else {
                    E ans = array[current];
                    current++;
                    return ans;
                }
            }

            @Override
            public boolean hasPrevious() {
                return !(current == 0);
            }

            @Override
            public E previous() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                } else if (timesChangedAtStart != timesChanged) {
                    throw new ConcurrentModificationException();
                } else {
                    E ans = array[current - 1];
                    current--;
                    return ans;
                }
            }

            @Override
            public int nextIndex() {
                return current;
            }

            @Override
            public int previousIndex() {
                return current - 1;
            }

            @Override
            public void remove() {
                if (timesChangedAtStart != timesChanged) {
                    throw new ConcurrentModificationException();
                }
                CustomArrayList.this.remove(current);
                current--;
                timesChangedAtStart++;
            }

            @Override
            public void set(E e) {
                if (timesChangedAtStart != timesChanged) {
                    throw new ConcurrentModificationException();
                }
                CustomArrayList.this.set(current, e);
                timesChangedAtStart++;
            }

            @Override
            public void add(E e) {
                if (timesChangedAtStart != timesChanged) {
                    throw new ConcurrentModificationException();
                }
                CustomArrayList.this.add(current, e);
                current++;
                timesChangedAtStart++;
            }
        };
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex > size || fromIndex < 0 || toIndex > size || toIndex < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (toIndex < fromIndex) {
            throw new IllegalArgumentException();
        }
        E[] newArray = (E[]) new Object[toIndex - fromIndex];
        System.arraycopy(array, fromIndex, newArray, 0, toIndex - fromIndex);
        return Arrays.asList(newArray);
    }
}

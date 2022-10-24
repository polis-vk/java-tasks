package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 * <p>
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    final private int defaultSize = 2;
    private int size;
    private Object[] array;
    private int currentPosition;
    private int modCount;

    public CustomArrayList() {
        array = new Object[defaultSize];
    }

    public CustomArrayList(E[] newArray) {
        array = newArray;
        size = newArray.length;
    }

    public void increaseDimension() {
        Object[] newArray = new Object[size + 1];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        currentPosition = 0;
        return new Iterator<>() {
            final int fixedModCount = modCount;

            @Override
            public boolean hasNext() {
                return currentPosition < size;
            }

            @Override
            public E next() {
                if (size == 0) {
                    throw new NoSuchElementException();
                }
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (currentPosition >= size) {
                    throw new IndexOutOfBoundsException();
                }
                return (E) array[currentPosition++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] newArray = new Object[size];
        System.arraycopy(array, 0, newArray, 0, size);
        return newArray;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a == null) {
            throw new NullPointerException();
        }
        if (size <= a.length) {
            System.arraycopy(array, 0, a, 0, size);
            return a;
        }
        return (T[]) Arrays.copyOf(array, size, a.getClass());
    }

    @Override
    public boolean add(E e) {
        if (size >= defaultSize) {
            increaseDimension();
        }
        array[size++] = e;
        modCount++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (array[i] == o) {
                array[i] = null;
            }
        }
        size--;
        modCount++;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object number : c) {
            if (!contains(number)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addAll(size, c);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i <= c.size() - 1; i++){
            increaseDimension();
            size ++;
        }
        System.arraycopy(array, index, array, index + c.size() ,size - index - c.size());
        System.arraycopy(c.toArray(), 0, array, index, c.size());
        modCount++;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object number : c) {
            remove(indexOf(number));
        }
        modCount++;
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            return true;
        }
        if (c.size() == 0) {
            clear();
            return true;
        }
        Object[] buffer = new Object[c.size()];
        c.toArray(buffer);
        for (int j = 0; j < c.size(); j++) {
            for (int i = 0; i < size(); i++) {
                if (!buffer[j].equals(array[i]) && !c.contains(array[i])) {
                    remove(i);
                    i--;
                    size--;
                }
            }
        }
        modCount++;
        return true;
    }

    @Override
    public void clear() {
        array = new Object[defaultSize];
        size = 0;
        currentPosition = 0;
        modCount++;
    }

    @Override
    public E get(int index) {
        return (E) array[index];
    }

    @Override
    public E set(int index, E element) {
        array[index] = element;
        modCount++;
        return null;
    }

    @Override
    public void add(int index, E element) {
        increaseDimension();
        if (index == size - 1 || (size == 0 && index == 0)) {
            add(element);
            return;
        }
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        size++;
        for (int i = size - 1; i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = element;
        modCount++;
    }

    @Override
    public E remove(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        E result = (E) array[index];
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[size - 1] = null;
        size--;
        modCount++;
        return result;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (array[i] != null && array[i].equals(o)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = -1;
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (array[i] != null && array[i] == null) {
                    index = i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (array[i].equals(o)) {
                    index = i;
                }
            }
        }
        return index;
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        currentPosition = index;

        return new ListIterator<>() {
            int fixedModCount = modCount;

            @Override
            public boolean hasNext() {
                return currentPosition < size;
            }

            @Override
            public E next() {
                if (size == 0) {
                    throw new NoSuchElementException();
                }
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (currentPosition >= size) {
                    throw new IndexOutOfBoundsException();
                }
                return (E) array[++currentPosition];
            }

            @Override
            public boolean hasPrevious() {
                return currentPosition >= 1;
            }

            @Override
            public E previous() {
                if (size == 0) {
                    throw new NoSuchElementException();
                }
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (currentPosition <= 0) {
                    throw new IndexOutOfBoundsException();
                }
                return (E) array[--currentPosition];
            }

            @Override
            public int nextIndex() {
                if (hasNext()) {
                    return currentPosition + 1;
                }
                return currentPosition;
            }

            @Override
            public int previousIndex() {
                if (hasPrevious()) {
                    return currentPosition - 1;
                }
                return -1;
            }

            @Override
            public void remove() {
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                CustomArrayList.this.remove(currentPosition);
                fixedModCount = modCount;
            }

            @Override
            public void set(E e) {
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                CustomArrayList.this.set(currentPosition, e);
                fixedModCount = modCount;
            }

            @Override
            public void add(E e) {
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                CustomArrayList.this.add(currentPosition, e);
                fixedModCount = modCount;
            }
        };
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || fromIndex > toIndex || toIndex > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        E[] subList = (E[]) new Object[toIndex - fromIndex];
        System.arraycopy(array, fromIndex, subList, 0, toIndex - fromIndex);
        return new CustomArrayList<>(subList);
    }
}

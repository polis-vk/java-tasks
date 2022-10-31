package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 * <p>
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    final private int defaultCapacity = 10;
    private int size;
    private E[] array;
    private int modCount;

    public CustomArrayList() {
        array = (E[]) new Object[defaultCapacity];
    }

    public void increaseDimension(int increaseValue) {
        E[] newArray = (E[]) new Object[increaseValue];
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
        return listIterator();

    }

    @Override
    public Object[] toArray() {
        Object[] newArray = new Object[size];
        System.arraycopy(array, 0, newArray, 0, size);
        return newArray;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (size <= a.length) {
            System.arraycopy(array, 0, a, 0, size);
            return a;
        }
        return (T[]) Arrays.copyOf(array, size, a.getClass());
    }

    @Override
    public boolean add(E e) {
        if (size >= array.length) {
            increaseDimension(array.length * 2);
        }
        array[size++] = e;
        modCount++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int indexOfObject = indexOf(o);
        if (indexOfObject != -1) {
            System.arraycopy(array, indexOfObject + 1, array, indexOfObject, size - indexOfObject - 1);
            size--;
            modCount++;
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object elementOfCollection : c) {
            if (!contains(elementOfCollection)) {
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
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (c.size() == 0 || c == null) {
            return false;
        }
        if (array.length < size + c.size()) {
            increaseDimension((c.size() + array.length) * 2);
        }
        System.arraycopy(array, index, array, index + c.size(), size - index);
        System.arraycopy(c.toArray(), 0, array, index, c.size());
        modCount++;
        size += c.size();
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isRemoved = false;
        for (Object elementOfCollection : c) {
            isRemoved = remove(elementOfCollection);
        }
        if (isRemoved) {
            modCount++;
        }
        return isRemoved;
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
        for (int j = 0; j < c.size(); j++) {
            for (int i = 0; i < size(); i++) {
                if (!c.toArray()[j].equals(array[i]) && !c.contains(array[i])) {
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
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
        modCount++;
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
        E replacedElement = array[index];
        array[index] = element;
        modCount++;
        return replacedElement;
    }

    @Override
    public void add(int index, E element) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (array.length == size) {
            increaseDimension(array.length * 2);
        }
        if (index == size - 1 || (size == 0 && index == 0)) {
            add(element);
            return;
        }
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
        modCount++;
    }

    @Override
    public E remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        E result = array[index];
        if (index == size - 1) {
            remove(array[index]);
        } else {
            System.arraycopy(array, index + 1, array, index, size - index - 1);
            size--;
            modCount++;
        }
        return result;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (array[i] == null && o == null || array[i] != null && array[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (array[i] == null && o == null || array[i] != null && array[i].equals(o)) {
                index = i;
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

        return new ListIterator<E>() {
            private int fixedModCount = modCount;
            private int currentPosition = index;

            @Override
            public boolean hasNext() {
                return currentPosition < size;
            }

            @Override
            public E next() {
                if (currentPosition >= size) {
                    throw new NoSuchElementException();
                }
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return array[currentPosition++];
            }

            @Override
            public boolean hasPrevious() {
                return currentPosition >= 1;
            }

            @Override
            public E previous() {
                if (currentPosition <= 0) {
                    throw new NoSuchElementException();
                }
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return array[currentPosition--];
            }

            @Override
            public int nextIndex() {
                return currentPosition;
            }

            @Override
            public int previousIndex() {
                return currentPosition - 1;
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
                CustomArrayList.this.add(currentPosition++, e);
                fixedModCount = modCount;
            }
        };
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || fromIndex > toIndex || toIndex > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        return new CustomSubList(fromIndex, toIndex);
    }

    private class CustomSubList implements List<E> {
        private final int offset;
        private final CustomSubList parent;
        private int fixedModCount = modCount;
        private int size;

        public CustomSubList(int fromInclusive, int toExclusive) {
            this(null, fromInclusive, toExclusive);
        }

        public CustomSubList(CustomSubList parent, int fromInclusive, int toExclusive) {
            this.parent = parent;
            this.offset = fromInclusive;
            this.size = toExclusive - fromInclusive;
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
            return listIterator();

        }

        @Override
        public Object[] toArray() {
            Object[] newArray = new Object[size];
            System.arraycopy(array, offset, newArray, 0, size);
            return newArray;
        }

        @Override
        public <T> T[] toArray(T[] a) {
            if (size <= a.length) {
                System.arraycopy(array, offset, a, 0, size);
                return a;
            }
            return (T[]) Arrays.copyOfRange(a, offset, offset + size, a.getClass());
        }

        @Override
        public boolean add(E e) {
            add(offset + size, e);
            return true;
        }

        @Override
        public boolean remove(Object o) {
            int indexOfObject = indexOf(o);
            if (indexOfObject != -1) {
                remove(indexOfObject);
                return true;
            }
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            for (Object elementOfCollection : c) {
                if (!contains(elementOfCollection)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            return addAll(size + offset, c);
        }

        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            if (index > size || index < 0) {
                throw new IndexOutOfBoundsException();
            }
            if (c.size() == 0 || c == null) {
                return false;
            }
            CustomArrayList.this.addAll(offset + index, c);
            size += c.size();
            return true;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            boolean isRemoved = false;
            for (Object elementOfCollection : c) {
                isRemoved = remove(elementOfCollection);
            }
            if (isRemoved) {
                modCount++;
            }
            return isRemoved;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            boolean isChanged = false;
            for (int i = 0; i < size; i++) {
                if (!c.contains(get(i))) {
                    remove(i--);
                    isChanged = true;
                }
            }
            modCount ++;
            return isChanged;
        }

        @Override
        public void clear() {
            for (int i = offset; i < size; i++) {
                array[i] = null;
            }
            CustomArrayList.this.size -= size;
            size = 0;
            modCount++;
        }

        @Override
        public E get(int index) {
            if (index >= size || index < 0) {
                throw new IndexOutOfBoundsException();
            }
            return CustomArrayList.this.get(offset + index);
        }

        @Override
        public E set(int index, E element) {
            if (index >= size || index < 0) {
                throw new IndexOutOfBoundsException();
            }
            E replacedValue = CustomArrayList.this.set(offset + index, element);
            return replacedValue;
        }

        @Override
        public void add(int index, E element) {
            if (index > size || index < 0) {
                throw new IndexOutOfBoundsException();
            }
            CustomArrayList.this.add(offset + index, element);
            size++;
        }

        @Override
        public E remove(int index) {
            if (index >= size || index < 0) {
                throw new IndexOutOfBoundsException();
            }
            E result = CustomArrayList.this.remove(offset + index);
            size --;
            return result;
        }

        @Override
        public int indexOf(Object o) {
            for (int i = offset; i < size + offset; i++) {
                if (array[i] == null && o == null || array[i] != null && array[i].equals(o)) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public int lastIndexOf(Object o) {
            int index = -1;
            for (int i = offset; i < size + offset; i++) {
                if (array[i] == null && o == null || array[i] != null && array[i].equals(o)) {
                    index = i;
                }
            }
            return index;
        }

        @Override
        public ListIterator<E> listIterator() {
            return listIterator(offset);
        }

        @Override
        public ListIterator<E> listIterator(int index) {

            return new ListIterator<E>() {
                private int fixedModCount = modCount;
                private int currentPosition = index + offset;

                @Override
                public boolean hasNext() {
                    return currentPosition < size;
                }

                @Override
                public E next() {
                    if (currentPosition >= size) {
                        throw new NoSuchElementException();
                    }
                    if (fixedModCount != modCount) {
                        throw new ConcurrentModificationException();
                    }
                    return array[currentPosition++];
                }

                @Override
                public boolean hasPrevious() {
                    return currentPosition >= 1;
                }

                @Override
                public E previous() {
                    if (currentPosition <= offset) {
                        throw new NoSuchElementException();
                    }
                    if (fixedModCount != modCount) {
                        throw new ConcurrentModificationException();
                    }
                    return array[currentPosition--];
                }

                @Override
                public int nextIndex() {
                    return currentPosition;
                }

                @Override
                public int previousIndex() {
                    return currentPosition - 1;
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
                    CustomArrayList.this.add(currentPosition++, e);
                    fixedModCount = modCount;
                }
            };
        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            if (fromIndex < 0 || fromIndex > toIndex || toIndex > size - 1) {
                throw new IndexOutOfBoundsException();
            }
            return new CustomSubList(this, fromIndex, toIndex);
        }
    }
}

package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.Collection;
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
    public int modCount;
    private E[] array;
    private int size;

    public CustomArrayList() {
        size = 0;
        array = (E[]) new Object[10];
        modCount = 0;
    }

    public CustomArrayList(E[] inp){
        size = inp.length;
        array = inp.clone();
    }
    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            E e = array[i];
            if (Objects.equals(o, e)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomIterator();
    }

    @Override
    public Object[] toArray() {
        E[] ans = (E[]) new Object[size];
        System.arraycopy(array, 0, ans, 0, size);
        return ans;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = Arrays.copyOf(a, size);
        }
        System.arraycopy(array, 0, a, 0, size);
        return a;
    }


    @Override
    public boolean add(E e) {
        if (size == array.length) {
            array = Arrays.copyOf(array, array.length * 2);
        }
        array[size++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int i = indexOf(o);
        if (i == -1) {
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
        if (array.length - size < c.size()){
            array = Arrays.copyOf(array, array.length + (c.size() * 2));
        }
        for (E e : c) {
            add(e);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c.size() == 0) {
            return false;
        }
        E[] newArr = (E[]) new Object[array.length + c.size()];
        System.arraycopy(array, 0, newArr, 0, index);
        int cnt = 0;
        for (E e: c) {
            newArr[index + cnt++] = e;
        }
        System.arraycopy(array, index, newArr, index + c.size(), array.length - index);
        size += c.size();
        array = newArr;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int cnt = 0;
        for (int i = 0; i < size; i++){
            E e = array[i];
            if (!c.contains(e)) {
                array[cnt++] = e;
            }
        }

        boolean ans = cnt != size;
        size = cnt;
        return ans;
    }
    @Override
    public boolean retainAll(Collection<?> c) {
        int cnt = 0;
        for (int i = 0; i < size; i++) {
            E element = array[i];
            if (c.contains(element)) {
                array[cnt++] = element;

            }
        }
        size = cnt;
        return cnt != 0;
    }

    @Override
    public void clear() {
        size = 0;
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
            throw new  IndexOutOfBoundsException();
        }
        E prev = array[index];
        array[index] = element;
        return prev;
    }

    @Override
    public void add(int index, E element) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        E[] newArr;
        if (size == array.length) {
            newArr = Arrays.copyOf(array, array.length * 2);
        } else {
            newArr = Arrays.copyOf(array, array.length);
        }
        System.arraycopy(array, 0, newArr, 0, index);
        newArr[index] = element;
        System.arraycopy(array, index, newArr, index+1, size - index);
        size++;
        array = newArr;
    }

    @Override
    public E remove(int index) {
        if (index >= size || index < 0) {
            throw new NoSuchElementException();
        }
        E ans = array[index];
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        size--;
        return ans;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(o, array[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(o, array[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new CustomListIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new CustomListIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        E[] newArr = (E[]) new Object[size];
        System.arraycopy(array, 0, newArr, 0, size);
        return new CustomArrayList<>(newArr);
    }

    private class CustomIterator implements Iterator<E> {
        int pointer = 0;

        @Override
        public boolean hasNext() {
            return pointer != CustomArrayList.this.size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return CustomArrayList.this.array[pointer++];
        }
    }

    public class CustomListIterator implements ListIterator<E> {
        int pointer;

        public CustomListIterator(int index) {

            pointer = index - 1;
        }

        @Override
        public boolean hasNext() {
            return pointer < CustomArrayList.this.size();
        }

        @Override
        public E next() {
            return CustomArrayList.this.get(++pointer);
        }

        @Override
        public boolean hasPrevious() {
            return pointer > -1;
        }

        @Override
        public E previous() {
            return CustomArrayList.this.get(--pointer);
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
            CustomArrayList.this.remove(pointer);
        }

        @Override
        public void set(E e) {
            CustomArrayList.this.set(pointer, e);
        }

        @Override
        public void add(E e) {
            CustomArrayList.this.add(pointer + 1, e);
            pointer++;
        }
    }


}

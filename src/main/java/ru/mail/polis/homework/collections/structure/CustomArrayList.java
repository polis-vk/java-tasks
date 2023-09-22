package ru.mail.polis.homework.collections.structure;

import java.util.*;

/**
 * Необходимо реализовать свой ArrayList (динамический массив).
 * При изменении размера массива помните про метод System.arraycopy()
 *
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    private Object[] arr;
    private int max_size;
    private int curr_size;
    private boolean have_null;

    CustomArrayList() {
        max_size = 1;
        curr_size = 0;
        arr = (E[]) (new Object[max_size]);
        have_null = false;
    }

    @Override
    public int size() {
        return curr_size;
    }

    @Override
    public boolean isEmpty() {
        return curr_size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            if (have_null) return true;
            else return false;
        }
        for (var a : arr) {
            if (a.equals((E) (o))) return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        //return new CustomArrayListIterator(0,1);
        return new CustomArrayListIterator2();
    }

    @Override
    public Object[] toArray() {
        Object[] obj = new Object[curr_size];
        obj = Arrays.copyOf(arr, curr_size);
        return obj;
    }

    @Override
    public <T> T[] toArray(T... a) {
        if (a.length < curr_size) return (T[]) Arrays.copyOf(arr, curr_size, a.getClass());
        System.arraycopy(arr, 0, a, 0, curr_size);
        if (a.length > curr_size) a[curr_size] = null;
        return a;
    }

    @Override
    public boolean add(E e) {
        if (curr_size == max_size) {
            arr = Arrays.copyOf(arr, 2 * max_size);
            max_size *= 2;
        }
        arr[curr_size++] = e;
        if (e == null) have_null = true;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int i = 0;
        found:
        {
            if (o == null) {
                for (; i < curr_size; i++)
                    if (arr[i] == null) break found;
            } else {
                for (; i < curr_size; i++)
                    if (o.equals((E) arr[i]))
                        break found;
            }
            return false;
        }
        fastRemove(arr, i);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) return false;
        if (c.isEmpty()) return true;
        for (var a : c)
            if (!contains(a)) return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        int start_size = size();
        for (var a : c)
            add(a);
        if (size() > start_size) return true;
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        int start_size = size();
        for (var a : c)
            add(index++, a);
        if (size() > start_size) return true;
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) return false;
        if (c.isEmpty() || size() == 0) return false;
        boolean mod = false;
        int i = 0;
        while (i < size()) {
            if (c.contains(arr[i])) {
                remove(i);
                mod = true;
            } else ++i;
        }
        return mod;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) return false;
        if (c.isEmpty() || size() == 0) return false;
        boolean mod = false;
        int i = 0;
        while (i < size()) {
            if (c.contains(arr[i])) {
                ++i;
            } else {
                remove(i);
                mod = true;
            }
        }
        return mod;
    }

    @Override
    public void clear() {
        for (int i = 0; i < curr_size; ++i) remove(i);
        curr_size = 0;
    }

    @Override
    public E get(int index) {
        return (E) arr[index];
    }

    @Override
    public E set(int index, E element) {
        E temp = (E) arr[index];
        arr[index] = element;
        return temp;
    }

    @Override
    public void add(int index, E element) {
        if (max_size < curr_size + 1) {
            arr = Arrays.copyOf(arr, 2 * max_size);
            max_size *= 2;
        }
        System.arraycopy(arr, index, arr, index + 1, curr_size - index);
        arr[index] = element;
        ++curr_size;
    }

    @Override
    public E remove(int index) {
        //Objects.checkIndex(index, curr_size);
        E oldValue = (E) arr[index];
        fastRemove(arr, index);
        return oldValue;
    }

    @Override
    public int indexOf(Object o) {
        int idx = -1;
        for (int i = 0; i < curr_size; ++i)
            if (o.equals((E) arr[i])) {
                idx = i;
                break;
            }
        return idx;
    }

    @Override
    public int lastIndexOf(Object o) {
        int idx = -1;
        for (int i = curr_size - 1; i >= 0; --i)
            if (o.equals((E) arr[i])) {
                idx = i;
                break;
            }
        return idx;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new CustomArrayListListIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        rangeCheckForAdd(index);
        return new CustomArrayListListIterator();
    }

    private void rangeCheckForAdd(int index) {
        if (index > curr_size || index < 0)
            throw new IndexOutOfBoundsException();
    }

    private void fastRemove(Object[] es, int i) {
        if ((curr_size - 1) > i)
            System.arraycopy(es, i + 1, es, i, curr_size - 1 - i);
        es[curr_size] = null;
        --curr_size;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return (List<E>) Arrays.asList(arr).subList(fromIndex, toIndex);
    }

    public class CustomArrayListIterator2 implements Iterator<E> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = curr_size;

        CustomArrayListIterator2() {
        }

        public boolean hasNext() {
            return cursor != curr_size;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            checkForComodification();
            int i = cursor;
            if (i >= curr_size)
                throw new NoSuchElementException();
            Object[] elementData = arr;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        final void checkForComodification() {
            if (curr_size != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    private class CustomArrayListListIterator extends CustomArrayListIterator2 implements ListIterator<E> {
        CustomArrayListListIterator() {
            super();
        }

        CustomArrayListListIterator(int index) {
            super();
            cursor = index;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public E previous() {
            return null;
        }

        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public int previousIndex() {
            return 0;
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(E e) {

        }

        @Override
        public void add(E e) {
            checkForComodification();
            try {
                int i = cursor;
                // CustomArrayList.this - ссылка на внешний класс
                CustomArrayList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
                expectedModCount = curr_size;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    public class CustomArrayListIterator implements Iterator<E> {
        private int curr_pos;
        private int step;
        private int unmodifyable_size = curr_size;

        CustomArrayListIterator(int curr_pos, int step) {
            this.curr_pos = curr_pos;
            this.step = step;
        }

        @Override
        public boolean hasNext() {
            return curr_pos < unmodifyable_size;
        }

        @Override
        public E next() {
            if (unmodifyable_size == 0) throw new NoSuchElementException();
            return (E) arr[curr_pos++];
        }
    }
}

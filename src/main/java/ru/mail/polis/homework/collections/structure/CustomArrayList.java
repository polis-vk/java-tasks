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
 * <p>
 * Задание оценивается в 10 тугриков
 */
public class CustomArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int GROW_COUNT = 10;
    private int capacity;
    private E[] data;
    private int size = 0;
    private int modCount = 0;

    CustomArrayList(E[] array) {
        this();
        addAll(Arrays.asList(array));
    }

    CustomArrayList(int initCapacity) {
        data = (E[]) new Object[initCapacity];
        capacity = initCapacity;
    }

    CustomArrayList() {
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
        return indexOf(o) != -1;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private final int   fixedModCount = modCount;
            private int index = 0;

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

        // Создаем массив той же размерности, чтобы не ссылаться на данные из класса.
        Object[] newArr = new Object[size];
        System.arraycopy(data, 0, newArr, 0, size);
        return newArr;
    }

    @Override
    public <T> T[] toArray(T[] a) {

        // Проверяем на null.
        if (a == null) {
            throw new NullPointerException();
        }

        // Если размеры совпадают, перекопируем все в массив. Иначе - создадим массив с переопределением типов.
        if (a.length == size) {
            System.arraycopy(data, 0, a, 0, size);
            return a;
        }
        return (T[]) Arrays.copyOf(data, size, a.getClass());
    }

    @Override
    public boolean add(E e) {
        if (size < capacity) {
            data[size] = e;
        } else {
            capacity += GROW_COUNT;
            E[] newArray = (E[]) new Object[capacity];
            System.arraycopy(data, 0, newArray, 0, size);
            newArray[size] = e;
            data = newArray;
        }
        size++;
        modCount++;
        return true;
    }

    @Override
    public boolean remove(Object o) {

        int index = indexOf(o);
        if (index != -1) {
            System.arraycopy(data, index + 1, data, index, size - index - 1);
            size--;
            modCount++;
            return true;
        }
        return false;
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

        // Используем здесь вариант с индексом, как добавление в конец массива.
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {

        // Проверка на неизменяемость.
        if (c.size() == 0) {
            return false;
        }
        // Проверяем на выход за границы.
        if (index > size | index < 0) {
            throw new IndexOutOfBoundsException();
        }

        // Новый размер массива.
        int allSize = size + c.size();
        /*
         * Проверяем, выходит ли новый размер за значение емкости. Если да, то создаем новый массив. Далее по частям
         * копируем старый массив и добавляем кусок из переданной коллекции.
         */

        if (allSize >= capacity) {
            E[] newArr = (E[]) new Object[allSize];
            System.arraycopy(data, 0, newArr, 0, index);
            System.arraycopy(c.toArray(), 0, newArr, index, c.size());
            System.arraycopy(data, index, newArr, index + c.size(), size - index);
            data = newArr;
        } else {
            System.arraycopy(data, 0, data, 0, index);
            System.arraycopy(c.toArray(), 0, data, index, c.size());
            System.arraycopy(data, index, data, index + c.size(), size - index);
        }
        size = allSize;
        modCount++;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {

        boolean isChanged = false;

        // Каждый элемент переданной коллекции удаляем в массиве, если он найден.
        for (Object obj : c) {
            isChanged = remove(obj);
        }
        return isChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {

        // Счетчик изменений.
        boolean Changed = false;

        // Ищем несовпадающий элемент. После удаления начинаем искать с той же позиции.
        int i = 0;
        while (i < size) {
            if (!c.contains(data[i])) {
                Changed = remove(data[i]);
            } else {
                i++;
            }
        }
        return Changed;
    }

    @Override
    public void clear() {

        // Проходимся по всему массиву и присваиваем null. Изменяем размер и фиксируем изменения.
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
        modCount++;
    }

    @Override
    public E get(int index) {

        // Если вышли за границы - выкидываем исключаение.
        if (index >= size | index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return data[index];
    }

    @Override
    public E set(int index, E element) {

        // Проверка на выход за границы.
        if (index >= size | index < 0) {
            throw new IndexOutOfBoundsException();
        }

        // Запоминаем элемент, котроый заменили. Заменяем.
        E returnElement = data[index];
        data[index] = element;
        modCount++;
        return returnElement;
    }

    @Override
    public void add(int index, E element) {

        // Првоеряем на выход за границы.
        if (index > size | index < 0) {
            throw new IndexOutOfBoundsException();
        }
        /*
         * Если  размер равен емкости, то необходимо сначала создать новый массив и скопировать туда необходимые значе-
         * ния.
         */
        if (size == capacity) {

            // Увеличиваем емкость. Создаем новый массив, котроый потом будем использовать.
            capacity += GROW_COUNT;
            E[] newArray = (E[]) new Object[capacity];
            /*
             * Случай, когда элемент добавляется не в конец. Вставляем значение. Далее копируем старые данные со смеще-
             * нием.
             */
            if (index < size) {
                newArray[index] = element;
                System.arraycopy(data, 0, newArray, 0, index);
                System.arraycopy(data, index, newArray, index + 1, size - index);

                //Случай, когда элемент добавляется в конец. Копируем старые данные и вставляем элемент в конец.
            } else {
                System.arraycopy(data, 0, newArray, 0, size);
                newArray[index] = element;
            }

            // Перессылаемся.
            data = newArray;

            // Случай, когда емкость увеличивать не надо.
        } else {

            // Если добавляем не в конец - смещаем в тот же массив. Добавляем элемент. Если в конец - просто добавляем.
            if (index < size) {
                System.arraycopy(data, index, data, index + 1, size - index);
            }
            data[index] = element;
        }

        // Изменение размера и фиксация изменений.
        size++;
        modCount++;
    }

    @Override
    public E remove(int index) {

        // Првоеряем на выход за границы.
        if (index >= size | index < 0) {
            throw new IndexOutOfBoundsException();
        }
        // Запоминаем элемент. Сдвигаем все последующие.
        E removeElement = data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);

        // Изменяем размер, отслеживаем изменения.
        size--;
        modCount++;
        return removeElement;
    }

    @Override
    public int indexOf(Object o) {
        // Если объект null, то ищем в массиве элемент с null и возвращаем его индекс.
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (data[i] == null) {
                    return i;
                }
            }
        }

        for (int i = 0; i < size; i++) {
            if (data[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        // Если объект null, то ищем в массиве элемент с null и возвращаем его индекс.
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (data[i] == null) {
                    return i;
                }
            }
        }

        for (int i = size - 1; i >= 0; i--) {
            if (data[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {

        // Определеним итератор через итератор с индексом - началом.
        return listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListIterator<E>() {
            private int curPosition = index;
            private int fixedModCount = modCount;

            @Override
            public boolean hasNext() {
                return curPosition < size;
            }

            @Override
            public E next() {
                if (curPosition >= size) {
                    throw new NoSuchElementException();
                }
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return data[curPosition++];
            }

            @Override
            public boolean hasPrevious() {
                return curPosition >= 0;
            }

            @Override
            public E previous() {
                if (curPosition < 0) {
                    throw new NoSuchElementException();
                }
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return data[curPosition--];
            }

            @Override
            public int nextIndex() {
                if (index == size) {
                    return size;
                }
                return curPosition + 1;
            }

            @Override
            public int previousIndex() {
                if (curPosition == 0) {
                    return -1;
                }
                return curPosition - 1;
            }

            @Override
            public void remove() {
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                CustomArrayList.this.remove(curPosition);
                fixedModCount = modCount;
            }

            @Override
            public void set(E e) {
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                CustomArrayList.this.set(curPosition, e);
                fixedModCount = modCount;
            }

            @Override
            public void add(E e) {
                if (fixedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                CustomArrayList.this.add(curPosition, e);
                curPosition++;
                fixedModCount = modCount;
            }
        };
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {

        // Проверим индексы на валидность.
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }

        // Создадим массив, куда дальше сложим часть массива.
        E[] newArr = (E[]) new Object[toIndex - fromIndex];
        System.arraycopy(data, fromIndex, newArr, 0, toIndex - fromIndex);

        // Возвращаем конструктор, принимающий массив элементов.
        return new CustomArrayList(newArr);
    }
}

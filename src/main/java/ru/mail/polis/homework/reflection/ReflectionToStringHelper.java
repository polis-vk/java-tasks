package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Необходимо реализовать метод reflectiveToString, который для произвольного объекта
 * возвращает его строковое описание в формате:
 *
 * {field_1: value_1, field_2: value_2, ..., field_n: value_n}
 *
 * где field_i - имя поля
 *     value_i - его строковое представление (String.valueOf),
 *               за исключением массивов, для которых value формируется как:
 *               [element_1, element_2, ..., element_m]
 *                 где element_i - строковое представление элемента (String.valueOf)
 *                 элементы должны идти в том же порядке, что и в массиве.
 *
 * Все null'ы следует представлять строкой "null".
 *
 * Порядок полей
 * Сначала следует перечислить в алфавитном порядке поля, объявленные непосредственно в классе объекта,
 * потом в алфавитном порядке поля объявленные в родительском классе и так далее по иерархии наследования.
 * Примеры можно посмотреть в тестах.
 *
 * Какие поля выводить
 * Необходимо включать только нестатические поля. Также нужно пропускать поля, помеченные аннотацией @SkipField
 *
 * Упрощения
 * Чтобы не усложнять задание, предполагаем, что нет циклических ссылок, inner классов, и transient полей
 *
 * Реализация
 * В пакете ru.mail.polis.homework.reflection можно редактировать только этот файл
 * или добавлять новые (не рекомендуется, т.к. решение вполне умещается тут в несколько методов).
 * Редактировать остальные файлы нельзя.
 *
 * Баллы
 * В задании 3 уровня сложности, для каждого свой набор тестов:
 *   Easy - простой класс, нет наследования, массивов, статических полей, аннотации SkipField (4 балла)
 *   Medium - нет наследования, массивов, но есть статические поля и поля с аннотацией SkipField (6 баллов)
 *   Hard - нужно реализовать все требования задания (10 баллов)
 *
 * Итого, по заданию можно набрать 20 баллов
 * Баллы могут снижаться за неэффективный или неаккуратный код
 */
public class ReflectionToStringHelper {

    private static final String COMPLEX_CLASS_START = "{";
    private static final String NULL = "null";
    private static final String PAIR_SEPARATOR = ": ";
    private static final String ELEMENTS_SEPARATOR = ", ";
    private static final String ARRAY_BEGIN = "[";
    private static final String ARRAY_END = "]";
    private static final String COMPLEX_CLASS_END = "}";

    public static String reflectiveToString(Object object) {
        StringBuilder sb = new StringBuilder();
        try {
            printFields(object, sb);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static void printFields(Object o, StringBuilder sb) throws IllegalAccessException {
        if (o == null) {
            sb.append(NULL);
            return;
        }
        Class<?> curClass = o.getClass();
        List<List<Field>> allFields = new ArrayList<>();
        int curIndex = 0;
        sb.append(COMPLEX_CLASS_START);
        while (!curClass.equals(Object.class)) {
            if (curIndex != 0) {
                sb.append(ELEMENTS_SEPARATOR);
            }
            List<Field> fields = filterFields(curClass.getDeclaredFields());
            sortFields(fields);
            if (!fields.isEmpty()) {
                for (int i = 0; i < fields.size() - 1; i++) {
                    printField(o, fields.get(i), sb);
                    sb.append(ELEMENTS_SEPARATOR);
                }
                printField(o, fields.get(fields.size() - 1), sb);
            }
            allFields.add(new LinkedList<>());
            allFields.set(curIndex, fields);
            ++curIndex;
            curClass = curClass.getSuperclass();
        }
        sb.append(COMPLEX_CLASS_END);
    }

    private static ArrayList<Field> filterFields(Field[] fields) {
        ArrayList<Field> res = new ArrayList<>();
        for (Field field: fields) {
            if (isRequiredField(field)) {
                res.add(field);
            }
        }
        return res;
    }

    private static void sortFields(List<Field> fields) {
        fields.sort(Comparator.comparing(Field::getName));
    }

    private static void printField(Object o, Field field, StringBuilder sb) throws IllegalAccessException {
        field.setAccessible(true);
        sb.append(field.getName()).append(PAIR_SEPARATOR);
        if (field.getType().isArray()) {
            try {
                field.get(o);
            } catch (NullPointerException e) {
                sb.append(NULL);
                return;
            }
            Object arr = field.get(o);
            if (arr == null) {
                sb.append(NULL);
                return;
            }
            if (Array.getLength(arr) == 0) {
                sb.append(ARRAY_BEGIN).append(ARRAY_END);
                return;
            }
            sb.append(ARRAY_BEGIN);
            for (int i = 0; i < Array.getLength(arr) - 1; i++) {
                sb.append(Array.get(arr, i)).append(ELEMENTS_SEPARATOR);
            }
            sb.append(Array.get(arr, Array.getLength(arr) - 1)).append(ARRAY_END);
        } else {
            try {
                sb.append(field.get(o));
            } catch (NullPointerException e) {
                sb.append(NULL);
            }
        }
    }

    private static boolean isRequiredField(Field f) {
        return !f.isAnnotationPresent(SkipField.class) && !Modifier.isStatic(f.getModifiers());
    }

}

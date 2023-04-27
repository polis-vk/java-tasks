package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Необходимо реализовать метод reflectiveToString, который для произвольного объекта
 * возвращает его строковое описание в формате:
 * <p>
 * {field_1: value_1, field_2: value_2, ..., field_n: value_n}
 * <p>
 * где field_i - имя поля
 * value_i - его строковое представление (String.valueOf),
 * за исключением массивов, для которых value формируется как:
 * [element_1, element_2, ..., element_m]
 * где element_i - строковое представление элемента (String.valueOf)
 * элементы должны идти в том же порядке, что и в массиве.
 * <p>
 * Все null'ы следует представлять строкой "null".
 * <p>
 * Порядок полей
 * Сначала следует перечислить в алфавитном порядке поля, объявленные непосредственно в классе объекта,
 * потом в алфавитном порядке поля объявленные в родительском классе и так далее по иерархии наследования.
 * Примеры можно посмотреть в тестах.
 * <p>
 * Какие поля выводить
 * Необходимо включать только нестатические поля. Также нужно пропускать поля, помеченные аннотацией @SkipField
 * <p>
 * Упрощения
 * Чтобы не усложнять задание, предполагаем, что нет циклических ссылок, inner классов, и transient полей
 * <p>
 * Реализация
 * В пакете ru.mail.polis.homework.reflection можно редактировать только этот файл
 * или добавлять новые (не рекомендуется, т.к. решение вполне умещается тут в несколько методов).
 * Редактировать остальные файлы нельзя.
 * <p>
 * Баллы
 * В задании 3 уровня сложности, для каждого свой набор тестов:
 * Easy - простой класс, нет наследования, массивов, статических полей, аннотации SkipField (4 балла)
 * Easy + Medium - нет наследования, массивов, но есть статические поля и поля с аннотацией SkipField (6 баллов)
 * Easy + Medium + Hard - нужно реализовать все требования задания (10 баллов)
 * <p>
 * Итого, по заданию можно набрать 10 баллов
 * Баллы могут снижаться за неэффективный или неаккуратный код
 */
public class ReflectionToStringHelper {

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return "null";
        }

        Class<?> c = object.getClass();
        List<Class<?>> classes = new ArrayList<>();
        classes.add(c);
        fillAncestors(c, classes);

        List<Field> fields = new ArrayList<>();
        for (Class<?> clazz : classes) {
            fields.addAll(extractClassFields(clazz));
        }
        return getStringOfFieldsList(fields, object);
    }

    private static void fillAncestors(Class<?> c, List<Class<?>> classes) {
        Class<?> ancestor = c.getSuperclass();
        if (ancestor != null) {
            classes.add(ancestor);
            fillAncestors(ancestor, classes);
        }
    }

    private static List<Field> extractClassFields(Class<?> c) {
        return Arrays
                .stream(c.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers())
                        && !field.isAnnotationPresent(SkipField.class))
                .sorted(Comparator.comparing(Field::getName))
                .collect(Collectors.toList());
    }

    private static String getStringOfFieldsList(List<Field> fields, Object object) {
        if (fields.isEmpty()) {
            return "{}";
        }
        StringBuilder data = new StringBuilder("{");
        for (int i = 0; i < fields.size() - 1; i++) {
            getStringOfFieldData(data, fields.get(i), object);
            data.append(", ");
        }
        getStringOfFieldData(data, fields.get(fields.size() - 1), object);
        data.append("}");
        return data.toString();
    }

    private static void getStringOfFieldData(StringBuilder data, Field field, Object object) {
        field.setAccessible(true);
        Object value;
        try {
            value = field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        data.append(field.getName()).append(": ");
        if (value == null) {
            data.append("null");
            return;
        } else if (!field.getType().isArray()) {
            data.append(value);
            return;
        }
        data.append("[");
        for (int i = 0; i < Array.getLength(value); i++) {
            data.append(Array.get(value, i));
            if (i + 1 != Array.getLength(value)) {
                data.append(", ");
            }
        }
        data.append("]");
    }
}


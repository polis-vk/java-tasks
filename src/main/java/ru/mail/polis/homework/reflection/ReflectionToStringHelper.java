package ru.mail.polis.homework.reflection;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Comparator;

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
 *   Easy + Medium - нет наследования, массивов, но есть статические поля и поля с аннотацией SkipField (6 баллов)
 *   Easy + Medium + Hard - нужно реализовать все требования задания (10 баллов)
 *
 * Итого, по заданию можно набрать 10 баллов
 * Баллы могут снижаться за неэффективный или неаккуратный код
 */
public class ReflectionToStringHelper {

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return ("null");
        }
        Class<?> objectClass = object.getClass();
        Class<?> objectSuperclass = objectClass.getSuperclass();
        StringBuilder result = new StringBuilder();
        result.append("{");
        while (objectClass != objectSuperclass && objectSuperclass != null) {
            Arrays.stream(objectClass.getDeclaredFields())
                    .filter(field -> !field.isAnnotationPresent(SkipField.class))
                    .filter(field -> !Modifier.isStatic(field.getModifiers()))
                    .sorted(Comparator.comparing(Field::getName))
                    .forEach(field -> result.append(fieldToString(field, object)));
            objectClass = objectSuperclass;
            objectSuperclass = objectSuperclass.getSuperclass();
        }
        if (result.length() > 2) {
            result.setLength(result.length() - 2);
        }
        return result.append("}").toString();
    }

    private static StringBuilder fieldToString(Field field, Object object) {
        StringBuilder result = new StringBuilder();
        try {
            field.setAccessible(true);
            result.append(field.getName()).append(": ");
            if (field.getType().isArray()) {
                result.append(arrayToString(field.get(object)));
            } else {
                result.append(field.get(object));
            }
            result.append(", ");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static StringBuilder arrayToString(Object array) {
        StringBuilder result = new StringBuilder();
        if (array == null) {
            result.append("null");
            return result;
        }
        result.append("[");
        for (int i = 0; i < Array.getLength(array); i++) {
            result.append(Array.get(array, i)).append(", ");
        }
        if (result.length() > 2) {
            result.setLength(result.length() - 2);
        }
        return result.append("]");
    }
}

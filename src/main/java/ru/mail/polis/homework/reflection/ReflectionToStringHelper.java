package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringJoiner;

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
            return "null";
        }

        Class<?> reflectionClass = object.getClass();
        StringJoiner joiner = new StringJoiner(", ", "{", "}");
        while (!reflectionClass.equals(Object.class)) {
            Arrays.stream(reflectionClass.getDeclaredFields())
                    .filter(field -> !Modifier.isStatic(field.getModifiers()) &&
                            !field.isAnnotationPresent(SkipField.class))
                    .sorted(Comparator.comparing(Field::getName))
                    .forEach(field -> addFieldToStringJoiner(joiner, field, object));
            reflectionClass = reflectionClass.getSuperclass();
        }
        return joiner.toString();
    }

    private static void addFieldToStringJoiner(StringJoiner joiner, Field field, Object object) {
        boolean fieldAccessFlag = field.isAccessible();
        if (!fieldAccessFlag) {
            field.setAccessible(true);
        }

        try {
            Object value = field.get(object);
            if (field.getType().isArray() && value != null) {
                addArrayToStringJoiner(joiner, field, value);
            } else {
                joiner.add(field.getName() + ": " + (value == null ? "null" : value));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            field.setAccessible(fieldAccessFlag);
        }
    }

    private static void addArrayToStringJoiner(StringJoiner joiner, Field field, Object arrayObject) {
        int arraySize = Array.getLength(arrayObject);
        switch (arraySize) {
            case 0:
                joiner.add(field.getName() + ": []");
                break;
            case 1:
                joiner.add(field.getName() + ": [" + Array.get(arrayObject, 0) + "]");
                break;
            default:
                joiner.add(field.getName() + ": [" + Array.get(arrayObject, 0));
                for (int i = 1; i < arraySize - 1; i++) {
                    joiner.add(String.valueOf(Array.get(arrayObject, i)));
                }
                joiner.add(Array.get(arrayObject, arraySize - 1) + "]");
                break;
        }
    }
}

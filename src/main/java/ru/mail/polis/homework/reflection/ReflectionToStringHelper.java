package ru.mail.polis.homework.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

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
        Class<?> clazz = object.getClass();
        Stream<Field> fieldsStream = Stream.empty();
        while (clazz != null) {
            fieldsStream = Stream.concat(fieldsStream, getConsideringFieldsStream(clazz));
            clazz = clazz.getSuperclass();
        }
        return '{' +
                fieldsStream.map(field -> field.getName() + ": " + getRepresentationOf(field, object))
                            .reduce((expression, fieldStringified) -> expression + ", " + fieldStringified)
                            .orElse("") +
                '}';
    }

    private static Stream<Field> getConsideringFieldsStream(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                     .filter(field -> !field.isAnnotationPresent(SkipField.class) &&
                                      (field.getModifiers() & Modifier.STATIC) == 0)
                     .sorted(Comparator.comparing(Field::getName));
    }

    private static String getRepresentationOf(Field field, Object object) {
        field.setAccessible(true);
        try {
            Object value = field.get(object);
            if (value instanceof Object[]) {
                return Arrays.toString((Object[]) value);
            }
            if (value instanceof boolean[]) {
                return Arrays.toString((boolean[]) value);
            }
            if (value instanceof byte[]) {
                return Arrays.toString((byte[]) value);
            }
            if (value instanceof short[]) {
                return Arrays.toString((short[]) value);
            }
            if (value instanceof int[]) {
                return Arrays.toString((int[]) value);
            }
            if (value instanceof long[]) {
                return Arrays.toString((long[]) value);
            }
            if (value instanceof char[]) {
                return Arrays.toString((char[]) value);
            }
            if (value instanceof float[]) {
                return Arrays.toString((float[]) value);
            }
            if (value instanceof double[]) {
                return Arrays.toString((double[]) value);
            }
            return String.valueOf(value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}

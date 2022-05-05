package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
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
 * Medium - нет наследования, массивов, но есть статические поля и поля с аннотацией SkipField (6 баллов)
 * Hard - нужно реализовать все требования задания (10 баллов)
 * <p>
 * Итого, по заданию можно набрать 20 баллов
 * Баллы могут снижаться за неэффективный или неаккуратный код
 */
public class ReflectionToStringHelper {

    public static String reflectiveToString(Object obj) {
        if (obj == null) {
            return "null";
        }

        StringBuilder stringBuilder = new StringBuilder("{");
        stringBuilder.append(stringifyObject(obj));

        if (stringBuilder.length() < 3) {
            return "{}";
        }

        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    private static StringBuilder stringifyObject(Object obj) {
        StringBuilder stringBuilder = new StringBuilder();

        Class<?> clazz = obj.getClass();

        do {
            stringBuilder.append(Arrays.stream(clazz.getDeclaredFields())
                    .sorted(Comparator.comparing(Field::getName))
                    .filter(field -> !field.isAnnotationPresent(SkipField.class))
                    .filter(field -> !Modifier.isStatic(field.getModifiers()))
                    .map(field -> String.format("%s: %s",
                            field.getName(), field.getType().isArray()
                                    ? stringifyArray(getField(obj, field))
                                    : getField(obj, field)))
                    .collect(Collectors.joining(", ")));

            if ((clazz = clazz.getSuperclass()) != Object.class) {
                stringBuilder.append(", ");
            }
        } while (clazz != Object.class);

        return stringBuilder;
    }

    private static StringBuilder stringifyArray(Object obj) {
        if (obj == null) {
            return new StringBuilder("null");
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < Array.getLength(obj); i++) {
            stringBuilder.append(Array.get(obj, i));
            stringBuilder.append(", ");
        }

        if (stringBuilder.length() < 3) {
            return new StringBuilder("[]");
        }

        int resultLength = stringBuilder.length();
        stringBuilder.delete(resultLength - 2, resultLength);
        stringBuilder.append("]");

        return stringBuilder;
    }

    private static Object getField(Object obj, Field field) {
        try {
            field.setAccessible(true);
            Object value = field.get(obj);
            field.setAccessible(false);
            return value;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}

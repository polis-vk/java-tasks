package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;

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

    private static final String NULL_VALUE = "null";

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return NULL_VALUE;
        }

        AtomicBoolean isEmpty = new AtomicBoolean(true);
        StringBuilder result = new StringBuilder("{");
        Class<?> objectClass = object.getClass();
        while (objectClass != Object.class) {
            Arrays.stream(objectClass.getDeclaredFields())
                .sorted(Comparator.comparing(Field::getName))
                .filter(field -> !Modifier.isStatic(field.getModifiers()) &&
                    !field.isAnnotationPresent(SkipField.class))
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        isEmpty.set(false);
                        Object value = field.get(object);
                        if (field.getType().isArray()) {
                            value = arrayToString(value);
                        }
                        value = value == null ? NULL_VALUE : value;

                        result
                            .append(field.getName())
                            .append(": ")
                            .append(value)
                            .append(", ");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            objectClass = objectClass.getSuperclass();
        }

        if (!isEmpty.get()) {
            deleteTwoLastChars(result);
        }
        result.append("}");
        return result.toString();
    }

    private static String arrayToString(Object array) {
        if (array == null) {
            return NULL_VALUE;
        }

        StringBuilder result = new StringBuilder("[");
        int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            result.append(Array.get(array, i)).append(", ");
        }
        if (length != 0) {
            deleteTwoLastChars(result);
        }
        result.append("]");
        return result.toString();
    }

    private static void deleteTwoLastChars(StringBuilder stringBuilder) {
        int charsToDelete = 2;
        stringBuilder.delete(stringBuilder.length() - charsToDelete, stringBuilder.length());
    }
}

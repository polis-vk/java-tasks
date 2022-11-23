package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;

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
    private static final String EMPTY_RESULT = "{}";

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return NULL_VALUE;
        }

        StringBuilder result = new StringBuilder("{");
        Class<?> objectClass = object.getClass();

        while (objectClass != Object.class) {
            Arrays.stream(objectClass.getDeclaredFields())
                .sorted(Comparator.comparing(Field::getName))
                .filter(field -> !Modifier.isStatic(field.getModifiers()) &&
                    !field.isAnnotationPresent(SkipField.class))
                .forEach(field -> {
                    try {
                        boolean isFieldNotAccessible = !field.canAccess(object);
                        if (isFieldNotAccessible) {
                            field.setAccessible(true);
                        }
                        Object value = field.get(object);
                        result.append(field.getName()).append(": ");
                        if (field.getType().isArray()) {
                            arrayToString(result, value);
                        } else {
                            value = value == null ? NULL_VALUE : value;
                            result.append(value);
                        }
                        result.append(", ");
                        if (isFieldNotAccessible) {
                            field.setAccessible(false);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            objectClass = objectClass.getSuperclass();
        }

        if (result.length() > EMPTY_RESULT.length()) {
            deleteTwoLastChars(result);
        }
        return result.append("}").toString();
    }

    private static void arrayToString(StringBuilder stringBuilder, Object array) {
        if (array == null) {
            stringBuilder.append(NULL_VALUE);
            return;
        }

        stringBuilder.append("[");
        int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(Array.get(array, i)).append(", ");
        }
        if (length != 0) {
            deleteTwoLastChars(stringBuilder);
        }
        stringBuilder.append("]");
    }

    private static void deleteTwoLastChars(StringBuilder stringBuilder) {
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
    }
}

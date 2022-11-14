package ru.mail.polis.homework.reflection;

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
    private static final String NULL = "null";
    private static final String EMPTY = "{}";
    private static final String ELEMENTS_SEPARATOR = ", ";
    private static final String BETWEEN_SEPARATOR = ": ";

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return NULL;
        }

        String objectFieldsInString = toStringRecursively(object.getClass(), object);

        if (objectFieldsInString == null) {
            return EMPTY;
        }

        return "{" + objectFieldsInString + "}";
    }

    private static String toStringRecursively(Class<?> clazz, Object object) {
        if (clazz == null) {
            return null;
        }

        // Make strings out of all fields of current object (can be empty if none of those suits)
        StringBuilder thisClassFieldsInString = new StringBuilder(fieldsToString(clazz.getDeclaredFields(), object));

        // Recursively get superclass' fields stringified
        String superclassFieldsInString = toStringRecursively(clazz.getSuperclass(), object);

        // This condition can be false in cases when current object does not have superclass,
        // Or superclass does not have any suitable fields to stringify
        if (superclassFieldsInString != null) {
            thisClassFieldsInString.append(ELEMENTS_SEPARATOR).append(superclassFieldsInString);
        }

        return thisClassFieldsInString.length() > 0 ? thisClassFieldsInString.toString() : null;
    }

    private static String fieldsToString(Field[] fields, final Object object) {
        return Arrays.stream(fields)
                .filter(field -> (field.getModifiers() & Modifier.STATIC) == 0)
                .filter(field -> !field.isAnnotationPresent(SkipField.class))
                .sorted(Comparator.comparing(Field::getName))
                .map(field -> {
                    String value;
                    boolean isAccessible = field.isAccessible();
                    try {
                        // Turn accessibility on
                        field.setAccessible(true);
                        value = objectFieldToString(field.getName(), field.get(object));
                    } catch (IllegalAccessException e) {
                        value = objectFieldToString(field.getName(), NULL);
                    }
                    // Turn accessibility back
                    field.setAccessible(isAccessible);
                    return value;
                })
                .reduce("", (result, current) -> result.isEmpty() ? current : result + ELEMENTS_SEPARATOR + current);
    }

    private static String objectFieldToString(String field, Object object) {
        String value;

        if (object == null) {
            value = NULL;
        } else if (isArray(object)) {
            value = arrayToString(object);
        } else {
            value = String.valueOf(object);
        }

        return field + BETWEEN_SEPARATOR + value;
    }

    private static String arrayToString(Object array) {
        // All the data types are covered
        if (array instanceof Object[]) {
            return Arrays.toString((Object[]) array);
        } else if (array instanceof byte[]) {
            return Arrays.toString((byte[]) array);
        } else if (array instanceof short[]) {
            return Arrays.toString((short[]) array);
        } else if (array instanceof int[]) {
            return Arrays.toString((int[]) array);
        } else if (array instanceof long[]) {
            return Arrays.toString((long[]) array);
        } else if (array instanceof float[]) {
            return Arrays.toString((float[]) array);
        } else if (array instanceof double[]) {
            return Arrays.toString((double[]) array);
        }
        return Arrays.toString((char[]) array);
    }

    private static boolean isArray(Object object) {
        return object instanceof Object[] || object instanceof byte[] || object instanceof short[]
                || object instanceof int[] || object instanceof long[] || object instanceof float[]
                || object instanceof double[] || object instanceof char[];
    }
}

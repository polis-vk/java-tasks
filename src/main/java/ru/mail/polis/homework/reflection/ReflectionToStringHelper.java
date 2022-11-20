package ru.mail.polis.homework.reflection;

import java.util.Arrays;
import java.util.Comparator;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

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

    private static final String EMPTY = "{}";
    private static final String EMPTY_ARRAY = "[]";
    private static final String NULL = "null";

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return NULL;
        }
        return reflect(object);
    }

    private static String reflect(Object object) {
        StringBuilder result = new StringBuilder();
        Class<?> objectClass = object.getClass();
        Field[] fields;
        result.append("{");
        while (objectClass.getSuperclass() != null) {
            fields = objectClass.getDeclaredFields();
            if (fields.length == 0) {
                return EMPTY;
            }
            Arrays.sort(fields, Comparator.comparing(Field::getName));
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers()) || field.isAnnotationPresent(SkipField.class)) {
                    continue;
                }
                result.append(field.getName()).append(": ").append(reflectFieldValues(field, object)).append(", ");
            }
            objectClass = objectClass.getSuperclass();
        }
        result.delete(result.length() - 2, result.length());
        return result.append("}").toString();
    }

    private static Object reflectFieldValues(Field field, Object object) {
        Object value = null;
        try {
            field.setAccessible(true);
            if (field.getType().isArray()) {
                value = makeDisplayOfArray(field.get(object));
                return value;
            }
            value = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    private static String makeDisplayOfArray(Object array) throws IllegalAccessException {
        StringBuilder stringBuilderArray = new StringBuilder();
        if (array == null) {
            stringBuilderArray.append(NULL);
            return stringBuilderArray.toString();
        }
        int length = Array.getLength(array);
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        stringBuilderArray.append("[");
        for (int i = 0; i < length; i++) {
            stringBuilderArray.append(Array.get(array, i)).append(", ");
        }
        stringBuilderArray.delete(stringBuilderArray.length() - 2, stringBuilderArray.length());
        return stringBuilderArray.append("]").toString();
    }
}

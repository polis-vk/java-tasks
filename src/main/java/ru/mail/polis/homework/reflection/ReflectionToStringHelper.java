package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

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

    public static String reflectiveToString(Object object) {
        if (Objects.isNull(object)) {
            return "null";
        }
        StringBuilder result = new StringBuilder("{");
        Class<?> clazz = object.getClass();
        while (clazz != Object.class) {
            Field[] fields = getSortedFields(clazz);
            boolean isFirstField = true;
            for (Field field : fields) {
                if (field.getAnnotation(SkipField.class) != null || Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                if (!isFirstField) {
                    result.append(", ");
                }
                isFirstField = false;
                result.append(field.getName()).append(": ");
                Object fieldValue = getFieldValue(object, field);
                if (fieldValue != null && fieldValue.getClass().isArray()) {
                    arrayParser(result, fieldValue);
                } else {
                    result.append(fieldValue);
                }
            }
            clazz = clazz.getSuperclass();
            if (clazz != Object.class) {
                result.append(", ");
            }
        }
        return result + "}";
    }

    private static Object getFieldValue(Object object, Field field) {
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            field.setAccessible(false);
        }
        return null;
    }

    private static Field[] getSortedFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Arrays.sort(fields, Comparator.comparing(Field::getName));
        return fields;
    }

    private static void arrayParser(StringBuilder result, Object fieldValue) {
        int length = Array.getLength(fieldValue);
        result.append("[");
        for (int i = 0; i < length; i++) {
            result.append(Array.get(fieldValue, i));
            if (i != Array.getLength(fieldValue) - 1) {
                result.append(", ");
            }
        }
        result.append("]");
    }
}

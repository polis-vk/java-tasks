package ru.mail.polis.homework.reflection;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;

import static java.lang.reflect.Modifier.isPrivate;
import static java.lang.reflect.Modifier.isStatic;

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
    private static final String NULL_STRING = "null";
    private static final String SEPARATOR_BETWEEN_FIELDS = ", ";
    private static final String SEPARATOR_INSIDE_FIELDS = ": ";
    private static final String OPENING_BRACKET_FOR_ARRAY = "[";
    private static final String CLOSING_BRACKET_FOR_ARRAY = "]";
    private static final String OPENING_BRACKET_FOR_CLASS = "{";
    private static final String CLOSING_BRACKET_FOR_CLASS = "}";

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return NULL_STRING;
        }

        StringBuilder res = new StringBuilder(OPENING_BRACKET_FOR_CLASS);
        Class<?> clazz = object.getClass();

        boolean first = true;
        do {
            if (!first) {
                res.append(SEPARATOR_BETWEEN_FIELDS);
            }
            first = false;

            appendObj(object, clazz, false, res);
            clazz = clazz.getSuperclass();
        } while (clazz != Object.class);

        res.append(CLOSING_BRACKET_FOR_CLASS);

        return res.toString();
    }


    private static void appendObj(Object object, Class<?> clazz, boolean needBrackets, StringBuilder sb) {
        if (needBrackets) {
            sb.append(OPENING_BRACKET_FOR_CLASS);
        }

        Field[] fields = clazz.getDeclaredFields();
        Arrays.sort(fields, Comparator.comparing(Field::getName));

        boolean first = true;
        for (Field field : fields) {
            if (isStatic(field.getModifiers()) || field.isAnnotationPresent(SkipField.class)) {
                continue;
            }

            if (isPrivate(field.getModifiers())) {
                field.setAccessible(true);
            }

            if (!first) {
                sb.append(SEPARATOR_BETWEEN_FIELDS);
            }
            first = false;

            sb.append(field.getName()).append(SEPARATOR_INSIDE_FIELDS);
            appendFieldContent(field, object, sb);
        }

        if (needBrackets) {
            sb.append(CLOSING_BRACKET_FOR_CLASS);
        }
    }

    private static void appendFieldContent(Field field, Object object, StringBuilder sb) {
        Class<?> type = field.getType();
        Object value;

        try {
            value = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return;
        }

        if (value == null) {
            sb.append(NULL_STRING);
            return;
        }

        if (type.isArray()) {
            appendArray((Object[]) value, sb);
            return;
        }

        if (type.isPrimitive() || type.isAssignableFrom(String.class) || type.isEnum()) {
            sb.append(value);
            return;
        }

        appendObj(value, type, true, sb);
    }

    private static void appendArray(Object[] array, StringBuilder sb) {
        sb.append(OPENING_BRACKET_FOR_ARRAY);

        boolean first = true;
        for (Object o : array) {
            if (!first) {
                sb.append(SEPARATOR_BETWEEN_FIELDS);
            }

            first = false;
            sb.append(o);
        }

        sb.append(CLOSING_BRACKET_FOR_ARRAY);
    }
}

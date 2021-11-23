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
 * Medium - нет наследования, массивов, но есть статические поля и поля с аннотацией SkipField (6 баллов)
 * Hard - нужно реализовать все требования задания (10 баллов)
 * <p>
 * И    того, по заданию можно набрать 20 баллов
 * Баллы могут снижаться за неэффективный или неаккуратный код
 */
public class ReflectionToStringHelper {
    private static final String NULL = "null";
    private static final String OPEN_CLASS_BRACKET = "{";
    private static final String CLOSE_CLASS_BRACKET = "}";
    private static final String OPEN_ARRAY_BRACKET = "[";
    private static final String CLOSE_ARRAY_BRACKET = "]";
    private static final String SEPARATOR_BEFORE_ENUMERATION = ": ";
    private static final String SEPARATOR_BETWEEN_ENUMERATION = ", ";

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return NULL;
        }
        Class<?> current = object.getClass();
        StringBuilder stringBuilder = new StringBuilder(OPEN_CLASS_BRACKET);
        do {
            Field[] fields = current.getDeclaredFields();
            Arrays.sort(fields, Comparator.comparing(Field::getName));
            for (Field field : fields) {
                field.setAccessible(true);
                if (isAppendable(field)) {
                    Object value = null;
                    try {
                        value = field.get(object);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    appendInString(field, value, stringBuilder);
                }
            }
            current = current.getSuperclass();
        } while (current != null);
        if (stringBuilder.length() > 1) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }
        return stringBuilder.append(CLOSE_CLASS_BRACKET).toString();
    }

    private static boolean isAppendable(Field field) {
        return !field.isAnnotationPresent(SkipField.class) && !Modifier.isStatic(field.getModifiers());
    }

    private static void appendInString(Field field, Object value, StringBuilder stringBuilder) {
        stringBuilder.append(field.getName()).append(SEPARATOR_BEFORE_ENUMERATION);
        if (value != null && value.getClass().isArray()) {
            appendArrayToString(stringBuilder, value);
        } else {
            stringBuilder.append(value);
        }
        stringBuilder.append(SEPARATOR_BETWEEN_ENUMERATION);
    }

    private static void appendArrayToString(StringBuilder stringBuilder, Object object) {
        stringBuilder.append(OPEN_ARRAY_BRACKET);
        for (int i = 0; i < Array.getLength(object); i++) {
            stringBuilder.append(Array.get(object, i));
            if (i != Array.getLength(object) - 1) {
                stringBuilder.append(SEPARATOR_BETWEEN_ENUMERATION);
            }
        }
        stringBuilder.append(CLOSE_ARRAY_BRACKET);
    }
}
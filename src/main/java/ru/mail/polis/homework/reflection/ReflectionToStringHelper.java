package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

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
    private static final Logger logger = LogManager.getLogManager().getLogger(ReflectionToStringHelper.class.getName());
    private static final String SEPARATOR = ", ";

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return "null";
        }
        StringBuilder builder = new StringBuilder("{");
        Class<?> currentClass = object.getClass();
        while (currentClass != Object.class) {
            fieldsToString(currentClass, object, builder);
            currentClass = currentClass.getSuperclass();
        }
        int commaPos = builder.lastIndexOf(SEPARATOR);
        if (commaPos > 0) {
            builder.setLength(builder.length() - SEPARATOR.length());
        }
        builder.append("}");
        return builder.toString();
    }

    private static void fieldsToString(Class<?> clazz, Object object, StringBuilder builder) {
        Field[] fields = clazz.getDeclaredFields();
        Arrays.sort(fields, Comparator.comparing(Field::getName));
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(SkipField.class) || Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            builder.append(field.getName()).append(": ");
            Object value = null;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                logger.log(Level.WARNING, "Error while reading field", e);
            }
            if (value == null) {
                builder.append("null");
            } else if (field.getType().isArray()) {
                arrayToString(builder, value);
            } else {
                builder.append(value);
            }
            builder.append(SEPARATOR);
        }
    }

    private static void arrayToString(StringBuilder builder, Object value) {
        builder.append("[");
        int length = Array.getLength(value);
        if (length > 0) {
            builder.append(Array.get(value, 0));
        }
        for (int i = 1; i < length; i++) {
            builder.append(SEPARATOR).append(Array.get(value, i));
        }
        builder.append("]");
    }
}

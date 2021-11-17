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
 * Итого, по заданию можно набрать 20 баллов
 * Баллы могут снижаться за неэффективный или неаккуратный код
 */
public class ReflectionToStringHelper {

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return "null";
        }
        Class<?> clazz = object.getClass();
        if (clazz.getDeclaredFields().length == 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder("{");
        try {
            while (!clazz.equals(Object.class)) {
                Field[] fields = clazz.getDeclaredFields();
                Arrays.sort(fields, Comparator.comparing(Field::getName));
                for (Field field : fields) {
                    if (isFieldWritable(field)) {
                        writeField(field, object, sb);
                    }
                }
                clazz = clazz.getSuperclass();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        closeString(sb);
        return sb.toString();
    }

    private static void writeField(Field field, Object object, StringBuilder sb)
            throws IllegalAccessException {
        field.setAccessible(true);
        sb.append(field.getName());
        sb.append(": ");
        Object valueOfField = field.get(object);
        if (valueOfField == null) {
            sb.append("null");
        } else if (field.getType().isArray()) {
            writeArray(valueOfField, sb);
        } else {
            sb.append(valueOfField);
        }
        sb.append(", ");
    }

    private static void closeString(StringBuilder sb) {
        sb.delete(sb.length() - 2, sb.length());
        sb.append("}");
    }

    private static boolean isFieldWritable(Field field) {
        return !Modifier.isStatic(field.getModifiers()) && !field.isAnnotationPresent(SkipField.class);
    }

    private static void writeArray(Object obj, StringBuilder sb) {
        int length = Array.getLength(obj);
        if (length == 0) {
            sb.append("[]");
            return;
        }
        sb.append("[");
        for (int i = 0; i < length; i++) {
            Object currentElement = Array.get(obj, i);
            if (currentElement != null) {
                sb.append(currentElement);
            } else {
                sb.append("null");
            }
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("]");
    }
}

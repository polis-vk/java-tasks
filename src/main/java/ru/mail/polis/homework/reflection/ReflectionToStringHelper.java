package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
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

        StringBuilder result = new StringBuilder("{");

        Class currentClass = object.getClass();
        while (currentClass != null) {
            result.append(writeAllClassFields(currentClass, object));
            currentClass = currentClass.getSuperclass();
        }
        removeLastDelimiter(result);
        result.append("}");

        return result.toString();
    }

    private static String writeAllClassFields(Class theClass, Object object) {
        Field[] fields = theClass.getDeclaredFields();
        Arrays.sort(fields, Comparator.comparing(Field::getName));

        StringBuilder result = new StringBuilder();
        for (Field field : fields) {
            if (!isFieldSuitable(field)) {
                continue;
            }
            field.setAccessible(true);
            result.append(field.getName());
            result.append(": ");
            Object value;
            try {
                value = field.get(object);
            } catch (IllegalAccessException exception) {
                value = null;
            }
            result.append(valueToString(value));
            result.append(", ");
        }

        return result.toString();
    }

    private static boolean isFieldSuitable(Field field) {
        return !java.lang.reflect.Modifier.isStatic(field.getModifiers())
                && !field.isAnnotationPresent(SkipField.class);
    }

    private static String valueToString(Object value) {
        if (value == null) {
            return "null";
        }

        if (!value.getClass().isArray()) {
            return value.toString();
        }

        StringBuilder result = new StringBuilder("[");
        int length = Array.getLength(value);
        for (int i = 0; i < length; i++) {
            result.append(valueToString(Array.get(value, i)));
            result.append(", ");
        }
        removeLastDelimiter(result);
        result.append("]");
        return result.toString();
    }

    static void removeLastDelimiter(StringBuilder stringBuilder) {
        if (stringBuilder.length() > 1) {
            stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), "");
        }
    }
}


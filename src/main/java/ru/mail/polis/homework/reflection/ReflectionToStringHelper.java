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
    private static final String NULLSTRING = "null";

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return NULLSTRING;
        }

        StringBuilder totalString = new StringBuilder("{");
        Class<?> clss = object.getClass();

        while (clss != Object.class) {
            try {
                convertToStringForOneClass(clss.getDeclaredFields(), object, totalString);
            } catch (IllegalAccessException exc) {
                exc.getStackTrace();
            }
            clss = clss.getSuperclass();
        }
        deleteExtraChar(totalString);
        totalString.append("}");

        return totalString.toString();
    }

    private static void convertToStringForOneClass(Field[] fields, Object obj, StringBuilder strBuilder) throws IllegalAccessException {
        if (fields.length == 0) {
            strBuilder.append(NULLSTRING).append(", ");
        }

        Arrays.sort(fields, Comparator.comparing(Field::getName));
        for (Field field : fields) {
            boolean isAccessChanged = false;
            if (field.isAnnotationPresent(SkipField.class) || Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            if (!field.isAccessible()) {
                field.setAccessible(true);
                isAccessChanged = true;
            }

            strBuilder.append(field.getName()).append(": ");
            if (field.getType().isArray()) {
                convertArrayToString(strBuilder, field.get(obj));
            } else {
                strBuilder.append(field.get(obj));
            }
            strBuilder.append(", ");

            if (isAccessChanged) {
                field.setAccessible(false);
            }
        }
    }

    private static void convertArrayToString(StringBuilder strBuilder, Object fieldVal) {
        if (Objects.equals(fieldVal, null)) {
            strBuilder.append(NULLSTRING);
            return;
        }

        int len = Array.getLength(fieldVal);
        if (len == 0) {
            strBuilder.append("[]");
            return;
        }

        strBuilder.append("[");
        for (int iter = 0; iter < len; iter++) {
            strBuilder.append(Array.get(fieldVal, iter)).append(", ");
        }

        deleteExtraChar(strBuilder);
        strBuilder.append("]");
    }

    private static void deleteExtraChar(StringBuilder strBuilder) {
        strBuilder.delete(strBuilder.length() - 2, strBuilder.length());
    }
}

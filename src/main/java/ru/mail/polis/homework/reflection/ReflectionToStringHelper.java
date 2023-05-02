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

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return "null";
        }
        StringBuilder returnSB = new StringBuilder("{");
        Class<?> clazz = object.getClass();
        while (clazz != Object.class) {
            Arrays.stream(clazz.getDeclaredFields())
                    .filter(ReflectionToStringHelper::isFieldGood)
                    .sorted(Comparator.comparing(Field::getName))
                    .forEach(field -> {
                        try {
                            field.setAccessible(true);
                            returnSB.append(field.getName()).append(":").append(" ");
                            appendFieldValueToSB(returnSB, field, object);
                            returnSB.append(",").append(" ");
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
            clazz = clazz.getSuperclass();
        }
        deleteEndSuffix(returnSB);
        return returnSB.append("}").toString();
    }

    private static void appendFieldValueToSB(StringBuilder returnSB, Field field, Object object) throws IllegalAccessException {
        Object obj = field.get(object);
        if (field.get(object) == null) {
            returnSB.append(obj);
            return;
        }
        if (!field.getType().isArray()) {
            returnSB.append(obj.toString());
            return;
        }
        returnSB.append("[");
        int length = Array.getLength(obj);
        if (length != 0) {
            for (int i = 0; i < length; i++) {
                Object toStringValue = Array.get(obj, i);
                returnSB.append(toStringValue == null ? null : toStringValue.toString()).append(",").append(" ");
            }
            deleteEndSuffix(returnSB);
        }
        returnSB.append("]");
    }

    private static boolean isFieldGood(Field field) {
        return !(Modifier.isStatic(field.getModifiers()) || field.isAnnotationPresent(SkipField.class));
    }

    private static void deleteEndSuffix(StringBuilder returnString) {
        if (returnString.length() > 1) {
            returnString.setLength(returnString.length() - 2);
        }
    }
}

package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
        List<Field> fields = getFields(object);
        StringBuilder stringBuilder = new StringBuilder("{");
        for (Field field : fields) {
            field.setAccessible(true);
            stringBuilder.append(field.getName()).append(": ");
            Object value = null;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value != null && field.getType().isArray()) {
                appendArray(value, stringBuilder);
            } else if (value != null) {
                stringBuilder.append(value);
            } else {
                stringBuilder.append("null");
            }

            stringBuilder.append(", ");
        }
        if (stringBuilder.length() > 1) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }
        stringBuilder.append("}");
//        System.out.println(stringBuilder);
        return stringBuilder.toString();
    }


    private static <T> List<Field> getFields(T t) {
        List<Field> fields = new ArrayList<>();
        Class clazz = t.getClass();
        while (clazz != Object.class) {
            Field[] arrayField = clazz.getDeclaredFields();
            Arrays.sort(arrayField, Comparator.comparing(Field::getName));
            List<Field> allFields = Arrays.asList(arrayField);
            for (Field field : allFields) {
                if (!(field.isAnnotationPresent(SkipField.class) || Modifier.isStatic(field.getModifiers()))) {
                    fields.add(field);
                }
            }
            clazz = clazz.getSuperclass();
        }

        return fields;
    }

    private static void appendArray(Object object, StringBuilder stringBuilder) {
        int length = Array.getLength(object);
        stringBuilder.append("[");
        if (length > 0) {
            stringBuilder.append(Array.get(object, 0));
        }
        for (int i = 1; i < length; i++) {
            stringBuilder.append(", ").append(Array.get(object, i));
        }
        stringBuilder.append("]");
    }
}
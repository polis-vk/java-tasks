package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;

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
        if (object == null) {
            return "null";
        }
        StringBuilder builder = new StringBuilder("{");
        try {
            Class<?> clazz = Class.forName(object.getClass().getName());
            getFieldsToString(clazz.getDeclaredFields(), builder, object);
            Class<?> parent = clazz.getSuperclass();
            while (parent != null) {
                getFieldsToString(parent.getDeclaredFields(), builder, object);
                parent = parent.getSuperclass();
            }
            if (builder.length() > 1) {
                builder.setLength(builder.length() - 2);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        builder.append("}");
        return builder.toString();
    }

    private static void getFieldsToString(Field[] fields, StringBuilder builder, Object object) {
        if (fields == null || fields.length == 0) {
            return;
        }
        Arrays.sort(fields, Comparator.comparing(Field::getName));
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(SkipField.class) || Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            Object value = null;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value != null && value.getClass().isArray()) {
                StringBuilder tmpArray = new StringBuilder("[");
                for (int i = 0; i < Array.getLength(value); i++) {
                    tmpArray.append(Array.get(value, i));
                    if (i != Array.getLength(value) - 1) {
                        tmpArray.append(", ");
                    }
                }
                tmpArray.append("]");
                value = tmpArray.toString();
            }
            builder.append(field.getName()).append(": ").append(value).append(", ");
        }
    }
}

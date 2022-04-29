package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
 *   Easy + Medium - нет наследования, массивов, но есть статические поля и поля с аннотацией SkipField (6 баллов)
 *   Easy + Medium + Hard - нужно реализовать все требования задания (10 баллов)
 *
 * Итого, по заданию можно набрать 10 баллов
 * Баллы могут снижаться за неэффективный или неаккуратный код
 */
public class ReflectionToStringHelper {

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return "null";
        }

        StringBuilder objectToString = new StringBuilder();
        objectToString.append("{");

        Class<?> objectClass = object.getClass();
        while (objectClass != null) {
            List<Field> fields = Arrays.stream(objectClass.getDeclaredFields())
                    .filter(field -> !Modifier.isStatic(field.getModifiers())
                            && !field.isAnnotationPresent(SkipField.class))
                    .sorted(Comparator.comparing(Field::getName))
                    .collect(Collectors.toList());

            if (fields.size() != 0 && !objectClass.equals(object.getClass())) {
                objectToString.append(", ");
            }

            for (Field field : fields) {
                objectToString.append(parseField(object, field));

                if (fields.indexOf(field) != fields.size() - 1) {
                    objectToString.append(", ");
                }
            }

            objectClass = objectClass.getSuperclass();
        }

        return objectToString.append("}").toString();
    }

    private static StringBuilder parseField(Object object, Field field) {
        StringBuilder currentField = new StringBuilder().append(field.getName()).append(": ");

        if (!field.canAccess(object)) {
            field.setAccessible(true);
        }

        Object value = null;
        try {
            value = field.get(object);
        } catch (IllegalAccessException ignored) {
        }

        return (value == null || !value.getClass().isArray()) ? currentField.append(value)
                : currentField.append(parseValueArrays(value));
    }

    private static StringBuilder parseValueArrays(Object value) {
        StringBuilder arraysToString = new StringBuilder();

        arraysToString.append("[");
        for (int i = 0; i < Array.getLength(value); i++) {
            if (i != 0) {
                arraysToString.append(", ");
            }
            arraysToString.append(Array.get(value, i));
        }
        arraysToString.append("]");

        return arraysToString;
    }

}

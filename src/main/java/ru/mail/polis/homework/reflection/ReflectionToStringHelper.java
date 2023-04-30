package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        StringBuilder returnString = new StringBuilder("{");
        List<Field> fields = getAllFields(object.getClass());
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                returnString.append(field.getName()).append(": ");
                createDescription(returnString, field, object);
                returnString.append(", ");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        deleteEndComma(returnString);
        return String.valueOf(returnString.append("}"));
    }

    private static void createDescription(StringBuilder returnString, Field field, Object object) throws IllegalAccessException {
        Object obj = field.get(object);
        if (field.get(object) == null) {
            returnString.append(obj);
            return;
        }
        if (!field.getType().isArray()) {
            returnString.append(obj.toString());
            return;
        }
        returnString.append("[");
        int length = Array.getLength(obj);
        if (length != 0) {
            for (int i = 0; i < length; i++) {
                Object toStringValue = Array.get(obj, i);
                returnString.append(toStringValue == null ? null : toStringValue.toString()).append(", ");
            }
            deleteEndComma(returnString);
        }
        returnString.append("]");
    }

    private static boolean shouldAddField(Field field) {
        return !(Modifier.isStatic(field.getModifiers()) || field.isAnnotationPresent(SkipField.class));
    }

    private static void deleteEndComma(StringBuilder returnString) {
        if (returnString.length() > 1) {
            returnString.setLength(returnString.length() - 2);
        }
    }

    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != Object.class) {
            fields.addAll(Arrays.stream(clazz.getDeclaredFields())
                    .filter(ReflectionToStringHelper::shouldAddField)
                    .sorted(Comparator.comparing(Field::getName))
                    .collect(Collectors.toList()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}

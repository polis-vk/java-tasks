package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
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
    private static final String DELIMITER_OF_FIELDS = ", ";

    public static String reflectiveToString(Object object) {
        final String nullValue = "null";
        if (object == null) {
            return nullValue;
        }
        Class<?> classOfObject = object.getClass();
        StringBuilder result = new StringBuilder("{");
        boolean hasExtraDelimiterOfFields = false;
        while (!Objects.equals(classOfObject, Object.class)) {
            List<Field> requiredFields = getSortedRequiredFields(classOfObject);
            for (Field field : requiredFields) {
                Object value = null;
                try {
                    if (isPrivate(field)) {
                        field.setAccessible(true);
                    }
                    value = field.get(object);
                    if (isPrivate(field)) {
                        field.setAccessible(false);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } finally {
                    field.setAccessible(false);
                }
                result.append(field.getName()).append(": ");
                if (value == null) {
                    result.append(nullValue);
                } else if (field.getType().isArray()) {
                    addArrayToResult(result, value);
                } else {
                    result.append(value);
                }
                result.append(DELIMITER_OF_FIELDS);
                hasExtraDelimiterOfFields = true;
            }
            classOfObject = classOfObject.getSuperclass();
        }
        if (hasExtraDelimiterOfFields) {
            deleteExtraDelimiterOfFields(result);
        }
        return result.append("}").toString();
    }

    private static boolean shouldBeIgnored(Field field) {
        return !field.isAnnotationPresent(SkipField.class) && !Modifier.isStatic(field.getModifiers());
    }

    private static void deleteExtraDelimiterOfFields(StringBuilder src) {
        int lastIndexWithDelimiter = src.lastIndexOf(DELIMITER_OF_FIELDS);
        src.delete(lastIndexWithDelimiter, lastIndexWithDelimiter + 2);
    }

    private static void addArrayToResult(StringBuilder result, Object value) {
        result.append("[");
        int length = Array.getLength(value);
        if (length != 0) {
            for (int i = 0; i < length; i++) {
                result.append(Array.get(value, i)).append(DELIMITER_OF_FIELDS);
            }
            deleteExtraDelimiterOfFields(result);
        }
        result.append("]");
    }

    private static List<Field> getSortedRequiredFields(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(ReflectionToStringHelper::shouldBeIgnored)
                .sorted(Comparator.comparing(Field::getName))
                .collect(Collectors.toList());
    }

    private static boolean isPrivate(Field field) {
        final int privateModifier = Modifier.PRIVATE;
        return (field.getModifiers() & privateModifier) == privateModifier;
    }
}

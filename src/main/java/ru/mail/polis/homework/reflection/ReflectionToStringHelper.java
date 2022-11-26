package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

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
    private static final String NULL_STRING = "null";

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return NULL_STRING;
        }
        List<Field> fields = findAllFields(object);
        StringBuilder answer = new StringBuilder("{");
        StringBuilder arrayToString = new StringBuilder();
        for (Field field : fields) {
            answer.append(field.getName());
            answer.append(": ");
            answer.append(getValue(object, field, arrayToString));
            answer.append(", ");
            arrayToString.delete(0, arrayToString.length());
        }
        removeExtraChars(answer);
        answer.append("}");
        return answer.toString();
    }

    private static List<Field> findAllFields(Object object) {
        Class<?> someClass = object.getClass();
        Field[] currentFields;
        List<Field> resultFields = new ArrayList<>();
        while (someClass != Object.class) {
            currentFields = someClass.getDeclaredFields();
            currentFields = Arrays.stream(currentFields)
                    .filter(field -> !Modifier.isStatic(field.getModifiers()))
                    .filter(field -> field.getAnnotation(SkipField.class) == null)
                    .sorted(Comparator.comparing(Field::getName)).toArray(Field[]::new);
            Collections.addAll(resultFields, currentFields);
            someClass = someClass.getSuperclass();
        }
        return resultFields;
    }

    private static String getValue(Object object, Field field, StringBuilder arrayToString) {
        field.setAccessible(true);
        Object value = null;
        try {
            value = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (value == null) {
            return NULL_STRING;
        }
        if (field.getType().isArray()) {
            arrayToString.append("[");
            for (int i = 0; i < Array.getLength(value); i++) {
                if (Array.get(value, i) == null) {
                    arrayToString.append(NULL_STRING);
                } else {
                    arrayToString.append(Array.get(value, i).toString());
                }
                arrayToString.append(", ");
            }
            removeExtraChars(arrayToString);
            arrayToString.append("]");
            return arrayToString.toString();
        }
        return value.toString();
    }

    private static void removeExtraChars(StringBuilder result) {
        if (result.length() != 1) {
            result.delete(result.length() - 2, result.length());
        }
    }
}

package ru.mail.polis.homework.reflection;

import ru.mail.polis.homework.reflection.objects.easy.Easy;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.lang.Object;
import java.lang.annotation.Annotation;

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

    private static final String NULL_VALUE = "null";

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return NULL_VALUE;
        }

        StringBuilder result = new StringBuilder();
        result.append("{");
        ArrayList<Field> fields = getSortedFields(object);
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            field.setAccessible(true);
            result.append(field.getName());
            result.append(": ");
            result.append(getValue(field, object));
            if (i != fields.size() - 1) {
                result.append(", ");
            }
            field.setAccessible(false);
        }
        result.append("}");
        return result.toString();
    }

    private static ArrayList<Field> getCorrectField(Field[] fields) {
        ArrayList<Field> correctField = new ArrayList<>();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())
                    || field.isAnnotationPresent(SkipField.class)) {
                continue;
            }
            correctField.add(field);
        }
        return correctField;
    }

    private static Object[] unpackArray(Object array) {
        if (array == null) {
            return null;
        }

        Object[] unpackedArray = new Object[Array.getLength(array)];
        for (int i = 0; i < unpackedArray.length; i++) {
            unpackedArray[i] = Array.get(array, i);
        }
        return unpackedArray;
    }

    private static String getValue(Field field, Object object) {
        try {
            StringBuilder fieldValue = new StringBuilder();
            if (field.getType().isArray()) {
                fieldValue.append("[");
                Object[] values = unpackArray(field.get(object));
                if (values == null) {
                    return NULL_VALUE;
                }

                for (int j = 0; j < values.length; j++) {
                    fieldValue.append(values[j]);
                    if (j != values.length - 1) {
                        fieldValue.append(", ");
                    }
                }
                fieldValue.append("]");
            } else {
                Object objectValue = field.get(object);
                if (objectValue == null) {
                    return NULL_VALUE;
                }
                fieldValue.append(objectValue);
            }
            return fieldValue.toString();
        } catch (IllegalAccessException  e) {
            e.printStackTrace();
            return NULL_VALUE;
        }
    }

    private static ArrayList<Field> getSortedFields (Object object) {
        Class<?> currentClass = object.getClass();
        ArrayList<Field> result = new ArrayList<>();
        do {
            ArrayList<Field> fields = getCorrectField(currentClass.getDeclaredFields());
            fields.sort(Comparator.comparing(Field::getName));
            result.addAll(fields);
            currentClass = currentClass.getSuperclass();
        } while (currentClass != null);
        return result;
    }
}

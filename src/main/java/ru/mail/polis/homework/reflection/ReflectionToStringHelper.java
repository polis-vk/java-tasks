package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

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
    private static final String NULL_RESULT = "null";

    public static String reflectiveToString(Object object) {
        // TODO: implement
        if (object == null) {
            return NULL_RESULT;
        }

        List<String> result = new ArrayList<>();
        try {
            List<Field> allFields = getAllFields(object);
            boolean isStaticFld;
            boolean isSkip;
            boolean isArray;
            StringBuilder currentElement = new StringBuilder();

            for (Field fld : allFields) {
                fld.setAccessible(true);
                isStaticFld = Modifier.isStatic(fld.getModifiers());
                isSkip = fld.isAnnotationPresent(SkipField.class);
                isArray = fld.getType().isArray();

                if (!isStaticFld && !isSkip) {
                    currentElement.append(fld.getName()).append(": ");
                    if (fld.get(object) == null) {
                        result.add(currentElement.append("null").toString());
                    } else if (isArray) {
                        result.add(currentElement.append(arrayToString(fld.get(object))).toString());
                    } else {
                        result.add(currentElement.append(fld.get(object)).toString());
                    }
                }
                fld.setAccessible(false);
                currentElement.setLength(0);
            }
        }
        catch (Throwable e) {
            System.err.println(e);
        }
        return new StringBuilder(String.join(", ", result)).insert(0, "{").append("}").toString();
    }

    private static List<Field> getAllFields(Object object) {
        List<Field> allSortedFields = new ArrayList<>();
        Field[] currentSortedFields;
        Class<?> currentClass = object.getClass();

        while (!currentClass.equals(Object.class)) {
            currentSortedFields = currentClass.getDeclaredFields();
            Arrays.sort(currentSortedFields, Comparator.comparing(Field::getName));
            allSortedFields.addAll(List.of(currentSortedFields));
            currentClass = currentClass.getSuperclass();
        }

        return allSortedFields;
    }

    private static String arrayToString(Object array) {
        if (array == null) {
            return "null";
        }

        int arrayLength = Array.getLength(array);
        if (arrayLength == 0) {
            return "[]";
        }

        List<String> result = new ArrayList<>();

        for (int i = 0; i < arrayLength; i++) {
            result.add(String.valueOf(Array.get(array, i)));
        }
        return new StringBuilder(String.join(", ", result)).insert(0, "[").append("]").toString();
    }
}

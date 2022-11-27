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
    private static final String NULL_RESULT = "null";

    public static String reflectiveToString(Object object) {
        // TODO: implement
        if (object == null) {
            return NULL_RESULT;
        }

        StringBuilder resultString = new StringBuilder("{");
        try {
            List<Field> allFields = getAllFields(object);
            boolean isStaticFld;
            boolean isSkip;
            boolean isArray;
            boolean isAccess;
            Field currentFld;

            for (int i = 0; i < allFields.size(); i++) {
                currentFld = allFields.get(i);
                isStaticFld = Modifier.isStatic(currentFld.getModifiers());
                isSkip = currentFld.isAnnotationPresent(SkipField.class);
                isArray = currentFld.getType().isArray();

                if (isStaticFld || isSkip) {
                    continue;
                }

                isAccess = currentFld.canAccess(object);
                if (!isAccess) {
                    currentFld.setAccessible(true);
                }

                resultString.append(currentFld.getName()).append(": ");

                if (currentFld.get(object) == null) {
                    resultString.append("null");
                } else if (isArray) {
                    resultString.append(arrayToString(currentFld.get(object)));
                } else {
                    resultString.append(currentFld.get(object));
                }

                if (i != allFields.size() - 1) {
                    resultString.append(", ");
                }

                if (!isAccess) {
                    currentFld.setAccessible(false);
                }
            }
        } catch (Throwable e) {
            System.err.println(e);
        }

        if (resultString.charAt(resultString.length() - 1) == ' ') {
            resultString.delete(resultString.length() - 2, resultString.length());
        }

        return resultString.append("}").toString();
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

        StringBuilder result = new StringBuilder("[");

        for (int i = 0; i < arrayLength; i++) {
            result.append(Array.get(array, i));
            if (i != arrayLength - 1) {
                result.append(", ");
            }
        }
        return result.append("]").toString();
    }
}
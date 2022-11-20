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
        // TODO: implement
        if (object == null) {
            return "null";
        }

        List<String> result = new ArrayList<>();
        try {
            Field[] allFields = getAllFields(object);
            boolean isStaticFld;
            boolean isSkip;
            boolean isArray;
            StringBuilder currentElement;

            for (Field fld : allFields) {
                fld.setAccessible(true);
                isStaticFld = Modifier.isStatic(fld.getModifiers());
                isSkip = fld.isAnnotationPresent(SkipField.class);
                isArray = fld.getType().isArray();
                currentElement = new StringBuilder();

                if (!isStaticFld && !isSkip) {
                    if (fld.get(object) == null) {
                        result.add(currentElement
                                .append(fld.getName())
                                .append(": null").toString());
                    } else if (isArray) {
                        result.add(currentElement
                                .append(fld.getName())
                                .append(": ")
                                .append(arrayToString(fld.get(object))).toString());
                    } else {
                        result.add(currentElement
                                .append(fld.getName())
                                .append(": ")
                                .append(fld.get(object)).toString());
                    }
                }
            }
        }
        catch (Throwable e) {
            System.err.println(e);
        }
        return "{" + String.join(", ", result) + "}";
    }

    private static Field[] getAllFields(Object object) {
        Field[] currentFieldsList = object.getClass().getDeclaredFields();
        Field[] allFields = currentFieldsList;
        Field[] tempArrFields;
        Arrays.sort(currentFieldsList, Comparator.comparing(Field::getName));
        Class<?> superClass = object.getClass().getSuperclass();

        while (!superClass.equals(Object.class)) {
            currentFieldsList = superClass.getDeclaredFields();
            Arrays.sort(currentFieldsList, Comparator.comparing(Field::getName));
            tempArrFields = new Field[allFields.length + currentFieldsList.length];
            System.arraycopy(allFields, 0, tempArrFields, 0, allFields.length);
            System.arraycopy(currentFieldsList, 0, tempArrFields, allFields.length, currentFieldsList.length);
            superClass = superClass.getSuperclass();
            allFields = tempArrFields;
        }

        return allFields;
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
        return "[" + String.join(", ", result) + "]";
    }
}

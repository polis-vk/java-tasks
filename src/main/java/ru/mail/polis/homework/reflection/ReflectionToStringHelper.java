package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;

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
        StringBuilder result = new StringBuilder();
        Class<?> givenClass = object.getClass();
        addFieldsInfo(object, givenClass, result);

        Class<?> superClass = givenClass.getSuperclass();

        while (superClass != null) {
            addFieldsInfo(object, superClass, result);
            superClass = superClass.getSuperclass();
        }

        if (result.toString().isEmpty()) {
            return "{}";
        }
        return result.insert(0, "{").insert(result.length() - 2, "}").substring(0, result.length() - 2);
    }

    private static void addFieldsInfo(Object object, Class<?> givenClass, StringBuilder currentResult) {
        Arrays.stream(givenClass.getDeclaredFields())
                .sorted(Comparator.comparing(Field::getName))
                .forEach(field -> {
                    field.setAccessible(true);

                    String fieldName = field.getName();
                    try {
                        if (!Modifier.isStatic(field.getModifiers()) && field.getAnnotation(SkipField.class) == null) { //проверка поля на статичность и на присутствие аннотации SkipField
                            currentResult.append(fieldName).append(": ");
                            appendFiledToStringBuilder(object, field, currentResult);
                            currentResult.append(", ");
                        }
                    } catch (IllegalAccessException e) {
                        currentResult.append(fieldName).append(": null, ");
                    }

                    field.setAccessible(false);
                });
    }

    private static void appendFiledToStringBuilder(Object object, Field field, StringBuilder currentResult) throws IllegalAccessException {
        if (field.get(object) == null) {
            currentResult.append("null");
            return;
        }
        Class<?> fieldType = field.getType();
        int startIndex = currentResult.length(); //индекс в возвращаемой строке, куда надо вставить строковое представление элемента

        if (!fieldType.isArray()) {
            currentResult.append(field.get(object));
            return;
        }

        Object array = field.get(object);
        int arrLength = Array.getLength(array);

        if (arrLength == 0) {
            currentResult.append("[]");
            return;
        }

        for (int i = 0; i < arrLength; i++) {
            currentResult.append((Array.get(field.get(object), i))).append(", ");
        }

        currentResult.insert(startIndex, "[").insert(currentResult.length() - 2, "]").delete(currentResult.length() - 2, currentResult.length());
    }
}
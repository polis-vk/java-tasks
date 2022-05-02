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
        // TODO: implement
        if (object == null) {
            return "null";
        }
        Class<?> inputClass = object.getClass();
        StringBuilder resultString = new StringBuilder();
        resultString.append("{");
        while (!Object.class.equals(inputClass)) {
            convertFieldsToString(inputClass, object, resultString);
            inputClass = inputClass.getSuperclass();
        }
        int index = resultString.lastIndexOf(",");
        if (index >= 0) {
            resultString.setLength(index);
        }
        resultString.append("}");
        return resultString.toString();
    }

    private static void convertFieldsToString(Class<?> inputClass, Object object, StringBuilder builder) {
        Field[] fields = inputClass.getDeclaredFields();
        Arrays.sort(fields, Comparator.comparing(Field::getName));
        try {
            for (Field field : fields) {
                if ((field.isAnnotationPresent(SkipField.class) || Modifier.isStatic(field.getModifiers()))) {
                    continue;
                }
                builder.append(field.getName()).append(": ");
                Object content = null;
                field.setAccessible(true);
                content = field.get(object);
                if (content != null) {
                    if (field.getType().isArray()) {
                        convertArrayToString(builder, content);
                    } else {
                        builder.append(content);
                    }
                } else {
                    builder.append("null");
                }
                builder.append(", ");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void convertArrayToString(StringBuilder builder, Object value) {
        builder.append("[");
        int length = Array.getLength(value);
        for (int i = 0; i < length; i++) {
            builder.append(Array.get(value, i));
            if (i != length - 1) {
                builder.append(", ");
            }
        }
        builder.append("]");
    }
}

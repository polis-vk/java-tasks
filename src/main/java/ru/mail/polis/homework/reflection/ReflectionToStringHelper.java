package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

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
 * того, по заданию можно набрать 10 баллов
 * Баллы могут снижаться за неэффективный или неаккуратный код
 */
public class ReflectionToStringHelper {

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return "null";
        }
        Field[] fields = findAllFields(object);
        StringBuilder answer = new StringBuilder("{");
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers()) && field.getAnnotation(SkipField.class) == null) {
                answer.append(field.getName());
                answer.append(": ");
                answer.append(getValue(object, field));
                answer.append(", ");
            }
        }
        if (answer.length() != 1) {
            answer.deleteCharAt(answer.length() - 1);
            answer.deleteCharAt(answer.length() - 1);
        }
        answer.append("}");
        return answer.toString();
    }

    private static Field[] findAllFields(Object object) {
        Class<?> someClass = object.getClass();
        Field[] currentFields;
        Field[] resultFields = new Field[0];
        while (someClass != Object.class) {
            currentFields = someClass.getDeclaredFields();
            Arrays.sort(currentFields, Comparator.comparing(Field::getName));
            resultFields = Stream.concat(Arrays.stream(resultFields), Arrays.stream(currentFields))
                    .toArray(Field[]::new);
            someClass = someClass.getSuperclass();
        }
        return resultFields;
    }

    private static String getValue(Object object, Field field) {
        field.setAccessible(true);
        Object value = null;
        try {
            value = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (value == null) {
            return "null";
        }
        if (field.getType().isArray()) {
            StringBuilder result = new StringBuilder("[");
            for (int i = 0; i < Array.getLength(value); i++) {
                if (Array.get(value, i) == null) {
                    result.append("null");
                } else {
                    result.append(Array.get(value, i).toString());
                }
                result.append(", ");
            }
            if (result.length() != 1) {
                result.deleteCharAt(result.length() - 1);
                result.deleteCharAt(result.length() - 1);
            }
            result.append("]");
            return result.toString();
        }
        return value.toString();
    }
}

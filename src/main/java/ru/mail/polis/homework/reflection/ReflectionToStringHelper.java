package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;

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
 *   Medium - нет наследования, массивов, но есть статические поля и поля с аннотацией SkipField (6 баллов)
 *   Hard - нужно реализовать все требования задания (10 баллов)
 *
 * �?того, по заданию можно набрать 20 баллов
 * Баллы могут снижаться за неэффективный или неаккуратный код
 */
public class ReflectionToStringHelper {

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return "null";
        }
        Class<?> objectClass = object.getClass();
        StringBuilder answer = new StringBuilder();
        while (objectClass != Object.class) {
            Field[] fields = objectClass.getDeclaredFields();
            Arrays.sort(fields, Comparator.comparing(Field::getName));
            for (Field field : fields) {
                if (field.isAnnotationPresent(SkipField.class) || Modifier.isStatic(field.getModifiers())) {
                } else {
                    field.setAccessible(true);
                    if (field.getType().isArray()) {
                        arrayFieldToStringBuilder(field, answer, object);
                    } else {
                        toStringBuilder(field, answer, object);
                    }
                    answer.append(", ");
                }
            }
            objectClass = objectClass.getSuperclass();
        }
        if (answer.length() >= 2) {
            answer.setLength(answer.length() - 2); // im too lazy to make it better
        }

        return wrapInBracketsCurly(answer);
    }

    private static void arrayFieldToStringBuilder(Field field, StringBuilder answer, Object object) {
        try {
            answer.append(field.getName()).append(": ");
            if (field.get(object) == null) {
                answer.append("null");
            } else {
                StringBuilder answerPart = new StringBuilder();
                Object arrayObject = field.get(object);
                for (int iter = 0; iter < Array.getLength(arrayObject); iter++) {
                    answerPart.append(Array.get(arrayObject, iter)).append(", ");
                }
                if (Array.getLength(arrayObject) > 0) {
                    answerPart.setLength(answerPart.length() - 2); // im too lazy to make it better
                }
                answer.append(wrapInBracketsSquare(answerPart));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void toStringBuilder(Field field, StringBuilder answer, Object object) {
        try {
            answer.append(field.getName()).append(": ");
            if (field.get(object) == null) {
                answer.append("null");
            } else {
                answer.append(field.get(object));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static String wrapInBracketsCurly(StringBuilder answer){
        return "{" + answer.toString() + "}";
    }

    private static StringBuilder wrapInBracketsSquare(StringBuilder answer){
        return new StringBuilder("[").append(answer).append("]");
    }
}

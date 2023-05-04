package ru.mail.polis.homework.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

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
        StringBuilder result = new StringBuilder();
        Class<?> c = object.getClass();
        boolean haveComma = false;

        while (c != Object.class) {

            Field[] fields = c.getDeclaredFields();
            Field[] goodFields = new Field[fields.length];
            int indexGoodFields = 0;

            for (Field field : fields) {
                if (checkHaveAnnotationSkipField(field)) {
                    continue;
                }
                if (checkHaveModifierStatic(field)) {
                    continue;
                }

                goodFields[indexGoodFields] = field;
                indexGoodFields++;
            }

            Arrays.sort(goodFields, 0, indexGoodFields, Comparator.comparing(Field::getName));

            for (int i = 0; i < indexGoodFields; i++) {
                result.append(goodFields[i].getName()).append(": ");
                goodFields[i].setAccessible(true);

                try {
                    Object value = goodFields[i].get(object);
                    if (value == null) {
                        result.append("null");
                    } else {
                        if (goodFields[i].getType().isArray()) {
                            result.append(getArrayValues(value));
                        } else {
                            result.append(value);
                        }
                    }
                } catch (IllegalAccessException e) {
                    result.append("null");
                } finally {
                    result.append(", ");
                    haveComma = true;
                }

            }
            c = c.getSuperclass();
        }
        if (haveComma) {
            result.delete(result.length() - 2, result.length());
        }
        result = addBrackets(result);
        return result.toString();
    }

    public static boolean checkHaveAnnotationSkipField(Field field) {
        for (Annotation a : field.getAnnotations()) {
            if (a.annotationType().getSimpleName().equals("SkipField")) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkHaveModifierStatic(Field field) {
        return (field.getModifiers() & Modifier.STATIC) > 0;
    }

    public static String getArrayValues(Object field) {
        StringBuilder arrayToString = new StringBuilder();

        for (int i = 0; i < Array.getLength(field); i++) {
            arrayToString.append(Array.get(field, i));
            if (i != Array.getLength(field) - 1) {
                arrayToString.append(", ");
            }
        }

        arrayToString = addSquareBrackets(arrayToString);
        return arrayToString.toString();
    }

    public static StringBuilder addSquareBrackets(StringBuilder string) {
        return new StringBuilder("[").append(string).append("]");
    }

    public static StringBuilder addBrackets(StringBuilder string) {
        return new StringBuilder("{").append(string).append("}");
    }
}

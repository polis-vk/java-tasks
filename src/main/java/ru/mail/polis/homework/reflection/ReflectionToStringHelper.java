package ru.mail.polis.homework.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ru.mail.polis.homework.reflection.objects.hard.Gender;
import ru.mail.polis.homework.reflection.objects.medium.Medium;

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
 * Medium - нет наследования, массивов, но есть статические поля и поля с аннотацией SkipField (6 баллов)
 * Hard - нужно реализовать все требования задания (10 баллов)
 * <p>
 * Итого, по заданию можно набрать 20 баллов
 * Баллы могут снижаться за неэффективный или неаккуратный код
 */
public class ReflectionToStringHelper {

    //Работает корректно на всех уровнях сложности
    public static String reflectiveToString(Object object) {
        if (object == null) {
            return "null";
        }
        Class<?> clazz = object.getClass();
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<Field> fields;

        stringBuilder.append('{');
        do {
            fields = new ArrayList<Field>(List.of(clazz.getDeclaredFields()));
            fields.sort(Comparator.comparing(Field::getName));


            for (Field field : fields) {
                if (field.isAnnotationPresent(SkipField.class) || Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                stringBuilder.append(field.getName());
                Object value = getValue(field, object);
                stringBuilder.append(": ");

                if (value == null) {
                    stringBuilder.append("null, ");
                    continue;
                }
                if (value.getClass().equals(Medium.class)) {
                    reflectiveToString(value);
                }

                if (value.getClass().isArray()) {
                    stringBuilder.append('[');
                    for (Object el : (Object[]) value) {
                        stringBuilder.append(el).append(", ");
                    }
                    deleteEndIfEndsWith(stringBuilder, ", ").append("], ");
                } else {
                    stringBuilder.append(value).append(", ");
                }
            }
            clazz = clazz.getSuperclass();
        } while (!clazz.equals(Object.class));
        deleteEndIfEndsWith(stringBuilder, ", ").append("}");
        return stringBuilder.toString();
    }

    private static StringBuilder deleteEndIfEndsWith(StringBuilder stringBuilder, String oldEnd) {
        if (stringBuilder.length() < oldEnd.length()){
            return stringBuilder;
        }
        if (stringBuilder.substring(stringBuilder.length() - oldEnd.length(), stringBuilder.length()).equals(oldEnd)){
            stringBuilder.delete(stringBuilder.length() - oldEnd.length(), stringBuilder.length());
        }
        return stringBuilder;
    }

    private static Object getValue(Field field, Object object) {
        boolean isAccessible = field.canAccess(object);
        field.setAccessible(true);
        Object value;
        try {
            value = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        field.setAccessible(isAccessible);
        return value;
    }


    private static String toString(Gender gender) {
        return (gender.equals(Gender.MALE)) ? "MALE" : "FEMALE";
    }
}

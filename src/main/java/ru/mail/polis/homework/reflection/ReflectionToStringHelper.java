package ru.mail.polis.homework.reflection;

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
 * Medium - нет наследования, массивов, но есть статические поля и поля с аннотацией SkipField (6 баллов)
 * Hard - нужно реализовать все требования задания (10 баллов)
 * <p>
 * Итого, по заданию можно набрать 20 баллов
 * Баллы могут снижаться за неэффективный или неаккуратный код
 */
public class ReflectionToStringHelper {

    private static final String BEGIN_TO_STRING = "{";
    private static final String END_TO_STRING = "}";
    private static final String VALUE_SEPARATOR = ": ";
    private static final String FIELD_SEPARATOR = ", ";

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder(BEGIN_TO_STRING);
        Class<?> clazz = object.getClass();
        while (clazz != Object.class) {
            Arrays.stream(clazz.getDeclaredFields())
                    .peek(field -> field.setAccessible(true))
                    .filter(ReflectionToStringHelper::toStringFilter)
                    .sorted(Comparator.comparing(Field::getName))
                    .forEach(field -> {
                        try {
                            sb.append(field.getName())
                                    .append(VALUE_SEPARATOR)
                                    .append(!field.getType().isArray() ? field.get(object) : Arrays.toString((Object[]) field.get(object)))
                                    .append(FIELD_SEPARATOR);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
            clazz = clazz.getSuperclass();
        }
        int stringLength = sb.length();
        if (stringLength > BEGIN_TO_STRING.length()) {
            sb.delete(stringLength - FIELD_SEPARATOR.length(), stringLength);
        }
        return sb.append(END_TO_STRING).toString();
    }

    private static boolean toStringFilter(Field field) {
        return !Modifier.isStatic(field.getModifiers())
                && !field.isAnnotationPresent(SkipField.class);
    }
}

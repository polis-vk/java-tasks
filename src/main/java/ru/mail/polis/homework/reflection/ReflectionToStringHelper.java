package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;
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
 * Итого, по заданию можно набрать 10 баллов
 * Баллы могут снижаться за неэффективный или неаккуратный код
 */
public class ReflectionToStringHelper {

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return "null";
        }
        String fieldsRepresentation = getClassesChain(object)
                .flatMap(ReflectionToStringHelper::getConsideringFieldsStream)
                .map(field -> field.getName() + ": " + getRepresentationOf(field, object))
                .reduce((expression, fieldStringified) -> expression + ", " + fieldStringified)
                .orElse("");
        return '{' + fieldsRepresentation + '}';
    }

    private static Stream<Class<?>> getClassesChain(Object object) {
        Stream.Builder<Class<?>> superClassesBuilder = Stream.builder();
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            superClassesBuilder.add(clazz);
            clazz = clazz.getSuperclass();
        }
        return superClassesBuilder.build();
    }

    private static Stream<Field> getConsideringFieldsStream(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(SkipField.class))
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .sorted(Comparator.comparing(Field::getName));
    }

    private static String getRepresentationOf(Field field, Object object) {
        final boolean initialAccessible = field.isAccessible();
        if (!initialAccessible) {
            field.setAccessible(true);
        }
        Object value = null;
        try {
            value = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (!initialAccessible) {
                field.setAccessible(false);
            }
        }
        if (value == null || !value.getClass().isArray()) {
            return String.valueOf(value);
        }
        return getRepresentationOfArray(value);
    }

    private static String getRepresentationOfArray(Object array) {
        String elementsRepresentation = IntStream.range(0, Array.getLength(array))
                .mapToObj(index -> String.valueOf(Array.get(array, index)))
                .reduce((expression, elementStringified) -> expression + ", " + elementStringified)
                .orElse("");
        return '[' + elementsRepresentation + ']';
    }
}

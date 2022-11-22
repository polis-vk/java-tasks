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
        result.append("{");
        Class<?> currentClass = object.getClass();
        while (currentClass != Object.class) {
            Arrays.stream(currentClass.getDeclaredFields())         // Убираем "неподходящие" поля
                    .filter(it -> !(Modifier.isStatic(it.getModifiers()) || it.isAnnotationPresent(SkipField.class)))
                    .sorted(Comparator.comparing(Field::getName))   // и сортируем их
                    .forEach(it -> processField(object, result, it));
            currentClass = currentClass.getSuperclass();
        }
        if (result.length() > 1) {          // (result.length() == 1) верно только в случае, если нам передан объект без полей
            result.delete(result.length() - 2, result.length());     // И только в этом случае у нас нет лишних запятой и пробела
        }
        return result.append("}").toString();
    }

    private static void processField(Object object, StringBuilder result, Field field) {
        boolean initAccess = field.isAccessible();
        field.setAccessible(true);
        try {
            result.append(field.getName()).append(": ");
            if (field.getType().isArray()) {
                appendArrayFieldToResult(object, result, field);
            }
            else {
                result.append(field.get(object));
            }
            result.append(", ");
        } catch (IllegalAccessException a) {
            a.printStackTrace();
        } finally {
            field.setAccessible(initAccess);
        }
    }

    private static void appendArrayFieldToResult(Object object, StringBuilder result, Field field) throws IllegalAccessException {
        Object array = field.get(object);
        if (array == null) {
            result.append("null");
        } else {
            int len = Array.getLength(array);
            result.append("[");
            for (int i = 0; i < len; i++) {
                result.append(Array.get(array, i));
                if (!(i == len - 1)) {
                    result.append(", ");
                }
            }
            result.append("]");
        }
    }
}

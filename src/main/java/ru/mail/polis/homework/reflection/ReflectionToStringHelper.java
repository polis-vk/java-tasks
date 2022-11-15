package ru.mail.polis.homework.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import ru.mail.polis.homework.reflection.objects.easy.Easy;
import ru.mail.polis.homework.reflection.objects.hard.Gender;
import ru.mail.polis.homework.reflection.objects.hard.Hard;
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
 * Easy + Medium - нет наследования, массивов, но есть статические поля и поля с аннотацией SkipField (6 баллов)
 * Easy + Medium + Hard - нужно реализовать все требования задания (10 баллов)
 * <p>
 * Итого, по заданию можно набрать 10 баллов
 * Баллы могут снижаться за неэффективный или неаккуратный код
 */
public class ReflectionToStringHelper {

    private static void filterForFields(List<Field> fields, Class<?> clazz) {
        fields.addAll(Arrays.stream(clazz.getDeclaredFields())
                .filter(o -> !o.toString().contains("static") && o.getAnnotation(SkipField.class) == null)
                .collect(Collectors.toList())
                .stream()
                .sorted(Comparator.comparing(Field::getName))
                .collect(Collectors.toList()));
        if (!clazz.getSuperclass().getSimpleName().equals("Object")) {
            filterForFields(fields, clazz.getSuperclass());
        }
    }

    private static String getArrayElements(Object obj) {
        StringBuilder sb = new StringBuilder(Arrays.deepToString(new Object[]{obj}));
        return sb.deleteCharAt(sb.length() - 1).deleteCharAt(0).toString();
    }

    public static String reflectiveToString(Object object) {
        List<Field> fields = new ArrayList<>();
        if (object == null) {
            return "null";
        }
        filterForFields(fields, object.getClass());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < fields.size(); ++i) {
            Field field = fields.get(i);
            try {
                if (Modifier.isPrivate(field.getModifiers())) {
                    field.setAccessible(true);
                }
                builder.append(field.getName()).append(": ");
                Object obj = field.get(object);
                builder.append((obj != null && field.getType().isArray()) ? getArrayElements(obj) :obj);
                if (i < fields.size() - 1) {
                    builder.append(", ");
                }
            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage());
            }

        }
        builder.insert(0, "{").append("}");
        return builder.toString();
    }

    public static void main(String[] args) {
    }
}

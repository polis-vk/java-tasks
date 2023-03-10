package ru.mail.polis.homework.objects;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валдино
     * Любой класс-обертка StringTasksTest над примитивами наследуется от Number
     * Если вы используете функции типа Double.valueOf() -- получите только половину тугриков.
     * Для полного количества тугриков надо парсить в ручную.
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     */
    public static Number valueOf(String str) {
        if (str == null || str.length() == 0 || str.charAt(str.length() - 1) == '-' || str.charAt(str.length() - 1) == 'e') {
            return null;
        }

        StringBuilder returnValue = new StringBuilder();
        int dotAmount = 0;
        int eAmount = 0;

        for (int i = 0; i < str.length() - 1; ++i) {

            if (str.charAt(i) != '-' && str.charAt(i) != 'e' && str.charAt(i) != '.' && !Character.isDigit(str.charAt(i))) {
                continue;
            }
            if (str.charAt(i) == '.') {
                ++dotAmount;
                if (dotAmount > 1) {
                    return null;
                }
            }
            if (str.charAt(i) == 'e') {
                ++eAmount;
                if (eAmount > 1) {
                    return null;
                }
            }
            returnValue.append(str.charAt(i));
        }
        if (Character.isDigit(str.charAt(str.length() - 1))) {
            returnValue.append(str.charAt(str.length() - 1));
        }

        for (int i = 1; i < returnValue.length() - 1; ++i) {
            if (returnValue.charAt(i) == '-' && (returnValue.charAt(i - 1) != 'e'
                    || !Character.isDigit(returnValue.charAt(i + 1)))) {
                return null;
            }
        }

        if (returnValue.indexOf(".") != -1 || returnValue.indexOf("e") != -1) {
            return Double.valueOf(returnValue.toString());
        }
        if (Long.valueOf(returnValue.toString()) > Integer.MAX_VALUE
                || Long.valueOf(returnValue.toString()) < Integer.MIN_VALUE) {
            return Long.valueOf(returnValue.toString());
        }
        return Integer.valueOf(returnValue.toString());
    }
}

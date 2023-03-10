package ru.mail.polis.homework.objects;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валидино
     * Любой класс-обертка StringTasksTest над примитивами наследуется от Number
     * Можно использовать функции типа Double.valueOf()
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     * БЕЗ РЕГУЛЯРОК!
     * 6 тугриков
     */
    public static Number valueOf(String str) {
        if (str == null || str.length() == 0 || str.charAt(str.length() - 1) == '-' || str.charAt(str.length() - 1) == 'e') {
            return null;
        }
        StringBuilder returnValue = new StringBuilder();
        int dotAmount = 0;
        int eAmount = 0;
        for (int i = 0; i < str.length(); ++i) {
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

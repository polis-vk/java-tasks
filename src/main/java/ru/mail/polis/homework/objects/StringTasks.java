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
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static Number convertToNumber(String str, int dotAmount, int eAmount) {
        if (eAmount == 1 || dotAmount == 1) {
            return Double.valueOf(str);
        }
        long longResult = Long.valueOf(str);
        if (longResult > Integer.MAX_VALUE || longResult < Integer.MIN_VALUE) {
            return longResult;
        }
        return Integer.valueOf(str);
    }

    public static Number valueOf(String str) {
        if (isEmpty(str) || str.endsWith("-") || str.startsWith("e") || str.endsWith("e")) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        int dotAmount = 0;
        int eAmount = 0;
        for (char c : str.toCharArray()) {
            if (c != '-' && c != 'e' && c != '.' && !Character.isDigit(c)) {
                continue;
            }
            if (c == '.') {
                ++dotAmount;
                if (dotAmount > 1) {
                    return null;
                }
            } else if (c == 'e') {
                ++eAmount;
                if (eAmount > 1) {
                    return null;
                }
            }
            result.append(c);
        }
        for (int i = 1; i < result.length() - 1; ++i) {
            if (result.charAt(i) == '-' && (result.charAt(i - 1) != 'e'
                    || !Character.isDigit(result.charAt(i + 1)))) {
                return null;
            }
        }
        return convertToNumber(result.toString(), dotAmount, eAmount);
    }
}

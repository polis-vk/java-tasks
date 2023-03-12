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
        if (str == null || str.isEmpty() || str.charAt(str.length() - 1) == '-'
                || str.charAt(str.length() - 1) == 'e' || str.charAt(str.length() - 1) == '.') {
            return null;
        }
        StringBuilder strBuilder = new StringBuilder(str);
        int i = 0;
        while (i <= strBuilder.length() - 1) {
            if (Character.isLetter(strBuilder.charAt(i)) && strBuilder.charAt(i) != 'e') {
                strBuilder.deleteCharAt(i);
            } else {
                i++;
            }
        }
        String resultStr = strBuilder.toString();
        for (int j = 1; j < resultStr.length() - 1; j++) {
            boolean previousSymbolIsDigit = Character.isDigit(resultStr.charAt(j - 1));
            boolean nextSymbolIsDigit = Character.isDigit(resultStr.charAt(j + 1));
            if ((resultStr.charAt(j) == '-') && (resultStr.charAt(j - 1) == 'e') && nextSymbolIsDigit) {
                continue;
            } else if ((resultStr.charAt(j) == '.') && previousSymbolIsDigit && nextSymbolIsDigit) {
                continue;
            } else if ((resultStr.charAt(j) == 'e') && previousSymbolIsDigit
                    && (nextSymbolIsDigit || (resultStr.charAt(j + 1) == '-'))) {
                continue;
            } else if (!Character.isDigit(resultStr.charAt(j))) {
                return null;
            }
        }
        if (resultStr.contains("e") || resultStr.contains(".")) {
            return Double.valueOf(resultStr);
        }
        if (Long.parseLong(resultStr) < Integer.MIN_VALUE || Long.parseLong(resultStr) > Integer.MAX_VALUE) {
            return Long.valueOf(resultStr);
        }
        return Integer.valueOf(resultStr);
    }
}

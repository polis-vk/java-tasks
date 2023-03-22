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
        if (str == null || str.equals("") || str.charAt(0) == 'e') {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        int countDots = 0;
        int countE = 0;
        int countMinus = 0;
        for (int i = 0; i < str.length(); i++) {
            char tempChar = str.charAt(i);
            if (Character.isDigit(tempChar) || tempChar == '-' || tempChar == '.' || tempChar == 'e') {
                builder.append(tempChar);
                countDots = tempChar == '.' ? countDots + 1 : countDots;
                countE = tempChar == 'e' ? countE + 1 : countE;
                countMinus = tempChar == '-' ? countMinus + 1 : countMinus;

                if (countDots > 1 || countE > 1 || countMinus > 2) {
                    return null;
                }

                if (i == str.length() - 1 && str.charAt(i) == 'e') {
                    return null;
                }
            }
        }
        String cleanStr = builder.toString();
        if (countMinus == 2 && (!cleanStr.contains("e-") || cleanStr.charAt(0) != '-')) {
            return null;
        }
        if (countMinus == 1 && (!cleanStr.contains("e-") && cleanStr.charAt(0) != '-')) {
            return null;
        }
        return handParse(cleanStr, countE == 1, countDots == 1);
    }

    private static Number handParse(String str, boolean isE, boolean isDot) {
        if (isE || isDot) {
            return Double.valueOf(str);
        }
        long digitLong = Long.parseLong(str);
        if (digitLong > Integer.MAX_VALUE || digitLong < Integer.MIN_VALUE) {
            return digitLong;
        }
        return Integer.parseInt(str);
    }
}

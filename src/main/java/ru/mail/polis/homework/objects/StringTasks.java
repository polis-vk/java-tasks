package ru.mail.polis.homework.objects;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число. Разрешенные
     * символы: цифры, '-', '.', 'e' Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валдино Любой
     * класс-обертка StringTasksTest над примитивами наследуется от Number Нельзя
     * использовать функции Double.valueOf() и другие такие же.
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно
     * использовать только для цикла) У класса Character есть полезные методы,
     * например Character.isDigit()
     */
    public static Number valueOf(String str) {
        if (str == null || str.length() == 0)
            return null;
        boolean negative = false;
        boolean expNeg = false;
        int activeStagePos = 0;
        int exp = 0;
        long longResult = 0;
        double doubleResult = 0;
        StringBuffer clrStr = new StringBuffer();
        for (int i = 0, len = str.length(); i < len; i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c) || c == '.' || c == '-' || c == 'e')
                clrStr.append(c);
        }
        str = clrStr.toString();
        if (str.charAt(0) == '-')
            negative = true;
        int dotPosition = str.indexOf('.');
        int ePosition = str.indexOf('e');
        for (int i = str.length() - 1; i >= (negative ? 1 : 0); i--) {
            char c = str.charAt(i);
            int distToDot = dotPosition >= 0 ? -dotPosition + i : -1;
            int distToE = ePosition >= 0 ? -ePosition + i : -1;
            if (Character.isDigit(c)) {
                if (dotPosition == -1 && ePosition == -1) {
                    longResult += Character.digit(c, 10) * Math.pow(10, activeStagePos);
                } else {
                    if (distToE >= 0) {
                        exp += Character.digit(c, 10) * Math.pow(10, activeStagePos);
                    } else if (distToDot >= 0) {
                        doubleResult += Character.digit(c, 10) * Math.pow(10, -distToDot);
                    } else {
                        doubleResult += Character.digit(c, 10) * Math.pow(10, activeStagePos);
                    }
                }
                activeStagePos++;
            } else if (c == '-') {
                if (!expNeg && i - 1 > 0 && str.charAt(i - 1) == 'e')
                    expNeg = true;
                else
                    return null;
            } else if (c == '.') {
                if (distToDot != 0)
                    return null;
                activeStagePos = 0;
            } else if (c == 'e') {
                if (distToE != 0 || exp == 0)
                    return null;
                activeStagePos = 0;
            }
        }
        exp = expNeg ? -exp : exp;
        longResult = negative ? -longResult : longResult;
        doubleResult = negative ? -doubleResult : doubleResult;
        if (doubleResult != 0)
            return doubleResult * Math.pow(10, exp);
        if (longResult >= Integer.MIN_VALUE && longResult <= Integer.MAX_VALUE && doubleResult == 0)
            return (int) longResult;
        return longResult;
    }
}
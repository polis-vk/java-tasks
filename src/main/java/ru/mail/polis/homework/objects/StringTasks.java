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
        if (str == null || str.length() == 0) {
            return null;
        }
        boolean negative = false;
        boolean expNeg = false;
        int activeStagePos = 0;
        long longResult = 0;
        int eCount = 0;
        int dotCount = 0;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0, len = str.length(); i < len; i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c) || c == '.' || c == '-' || c == 'e') {
                stringBuffer.append(c);
                if (c == 'e') {
                    if (activeStagePos == 0 || eCount > 1) {
                        return null;
                    }
                    eCount++;
                }
                if (c == '.') {
                    if (dotCount > 1) {
                        return null;
                    }
                    dotCount++;
                }
                if (c == '-') {
                    if (activeStagePos == 0) {
                        negative = true;
                    } else {
                        if (stringBuffer.charAt(activeStagePos - 1) != 'e') {
                            return null;
                        }
                        expNeg = true;
                    }
                }
                activeStagePos++;
            }
        }
        String clearStr = stringBuffer.toString();
        int dotPosition = str.indexOf('.');
        int ePosition = str.indexOf('e');
        for (int i = 0; i < clearStr.length(); i++) {
            char c = clearStr.charAt(i);
            if (c == '-' || c == 'e' || c == '.') {
                if (i == clearStr.length() - 1) {
                    return null;
                }
            }
        }
        if (dotPosition == -1 && ePosition == -1) {
            longResult = longOut(clearStr, negative);
            if (longResult >= Integer.MIN_VALUE && longResult <= Integer.MAX_VALUE) {
                return (int) longResult;
            }
            return longResult;
        }
        return doubleOut(clearStr, negative, expNeg);
    }

    private static Long longOut(String clearStr, boolean negative) {
        Long temp = 0L;
        for (int i = 0; i < clearStr.length(); i++) {
            char c = clearStr.charAt(i);
            if (Character.isDigit(c)) {
                temp = temp * 10 + Character.digit(c, 10);
                System.out.println(temp);
            }
        }
        temp = negative ? -temp : temp;
        return temp;
    }

    private static double doubleOut(String clearStr, boolean negative, boolean expNeg) {
        int exp = 0;
        double temp = 0;
        int activeStagePos = 1;
        boolean ePass = false;
        boolean dotPass = false;
        for (int i = 0; i < clearStr.length(); i++) {
            char c = clearStr.charAt(i);
            if (c == 'e') {
                ePass = true;
            }
            if (c == '.') {
                dotPass = true;
            }
            if (Character.isDigit(c)) {
                if (ePass) {
                    exp = exp * 10 + Character.digit(c, 10);
                    System.out.println("exp = " + exp);
                }
                if (dotPass && !ePass) {
                    temp += Character.digit(c, 10) * Math.pow(10, -activeStagePos);
                    activeStagePos++;
                }
                if (!dotPass && !ePass) {
                    temp = temp * 10 + Character.digit(c, 10);
                }

            }
        }
        temp = negative ? -temp : temp;
        exp = expNeg ? -exp : exp;
        System.out.println("exp = " + exp);
        System.out.println("temp = " + temp);
        return temp * Math.pow(10, exp);
    }
}
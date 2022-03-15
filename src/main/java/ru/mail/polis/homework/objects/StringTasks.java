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
        if (str == null || str.isEmpty()) {
            return null;
        }
        int eCnt = 0;
        int pointCnt = 0;
        int minCnt = 0;
        boolean checkDoubleMinus = true;
        String numberStr = new String();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'e') {
                ++eCnt;
            }
            if (str.charAt(i) == '.') {
                ++pointCnt;
            }
            if (str.charAt(i) == '-') {
                ++minCnt;
                if (i != 0 && str.charAt(i - 1) == str.charAt(i)) {
                    checkDoubleMinus = false;
                }
            }
            if (eCnt >= 2 || pointCnt >= 2 || minCnt > 2 || checkDoubleMinus == false) {
                return null;
            }
            if (Character.isDigit(str.charAt(i)) || str.charAt(i) == '-' || str.charAt(i) == '.' || str.charAt(i) == 'e') {
                numberStr = numberStr + str.charAt(i);
            }

        }
        if (minCnt == 1 && numberStr.indexOf('-') != 0 && numberStr.lastIndexOf('-') != numberStr.indexOf('e') + 1) {
            return null;
        }
        if (minCnt == 2 && (numberStr.indexOf('-') != 0 || numberStr.lastIndexOf('-') != numberStr.indexOf('e') + 1)) {
            return null;
        }


        if (eCnt != 0 || pointCnt != 0) {
            if (numberStr.indexOf('e') == 0 || numberStr.indexOf('e') == numberStr.length() - 1) {
                return null;
            }
            if (numberStr.indexOf('e') != -1 && (numberStr.indexOf('.') == 0 || numberStr.indexOf('.') == numberStr.length() - 1 || numberStr.indexOf('.') >= numberStr.indexOf('e') - 1)) {
                return null;
            }
            return RedefineAsDouble(numberStr);
        }
        if (RedefineAsLong(numberStr) <= Integer.MAX_VALUE && RedefineAsLong(numberStr) >= Integer.MIN_VALUE) {
            return (int) RedefineAsLong(numberStr);
        }
        return RedefineAsLong(numberStr);
    }

    public static long RedefineAsLong(String str) {
        long num = 0;
        boolean negativeCheck = true;
        if (str.indexOf('-') == 0) {
            negativeCheck = false;
            str = str.substring(1, str.length());
        }
        for (int i = 0; i < str.length(); i++) {
            num = 10 * num + (int) (str.charAt(i) - '0');
        }
        if (negativeCheck == false) {
            num = -1 * num;
        }

        return num;
    }

    public static double RedefineAsDouble(String str) {
        String intPart = new String();
        String floatPart = new String();
        String expPart = new String();
        double num = 0;
        if (str.indexOf('.') != -1) {
            intPart = str.split("\\.")[0];
            if (str.indexOf('e') != -1) {
                floatPart = str.split("\\.")[1].split("e")[0];
                expPart = str.split("\\.")[1].split("e")[1];
            } else {
                floatPart = str.split("\\.")[1];
            }
        } else {
            intPart = str.split("e")[0];
            expPart = str.split("e")[1];
        }
        boolean negativeCheck = true;
        if (intPart.indexOf('-') == 0) {
            negativeCheck = false;
            intPart = intPart.substring(1, intPart.length());
        }
        for (int i = 0; i < intPart.length(); i++) {
            num = 10 * num + (int) (intPart.charAt(i) - '0');
        }
        if (floatPart.length() > 0) {
            for (int i = 0; i < floatPart.length(); i++) {
                num += ((int) (floatPart.charAt(i) - '0')) / Math.pow(10, i + 1);
            }
        }
        num = num * Math.pow(10, RedefineAsLong(expPart));
        if (negativeCheck == false) {
            num = -1 * num;
        }
        return num;
    }
}

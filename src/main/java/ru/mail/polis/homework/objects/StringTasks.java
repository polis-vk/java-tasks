package ru.mail.polis.homework.objects;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валдино
     * Любой класс-обертка StringTasksTest над примитивами наследуется от Number
     * Нельзя использовать функции Double.valueOf() и другие такие же.
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     */
    public static Long strToLong(String str) {
        Long result = 0L;
        byte sign = 1;
        if (str.charAt(0) == '-') {
            sign = -1;
        } else {
            result = (long) Character.getNumericValue(str.charAt(0));
        }
        for (int i = 1; i < str.length(); i++) {
            result = result * 10 + Character.getNumericValue(str.charAt(i));
        }
        result *= sign;
        return result;
    }

    public static Double strToDot(String str) {
        if (str.contains(".")) {
            int dotPlace = str.indexOf('.');
            String afterDotStr = str.substring(dotPlace + 1);
            Long beforeDot = strToLong(str.substring(0, dotPlace));
            Double afterDot = 0.0;
            for (int i = 0; i < afterDotStr.length(); i++) {
                double test = ((double) Character.getNumericValue(afterDotStr.charAt(i))) / Math.pow(10, (i + 1));
                afterDot = afterDot + test;
            }
            return (double) beforeDot + afterDot;
        } else {
            return (double) strToLong(str);
        }
    }

    public static Double strToDouble(String str) {
        if (str.contains("e")) {
            int ePlace = str.indexOf('e');
            double number = strToDot(str.substring(0, ePlace));
            double degree = strToDot(str.substring(ePlace + 1));
            return number * Math.pow(10, degree);
        } else {
            return strToDot(str);
        }
    }

    public static Number valueOf(String str) {
        String tempStr = str;
        int dotCount = 0;
        int eCount = 0;
        for (int i = 0; i < tempStr.length(); i++) {
            char currChar = tempStr.charAt(i);
            if (Character.isDigit(currChar)) {
            } else if (currChar == '-') {
                if (((i != 0) && (tempStr.charAt(i - 1) == currChar)) || (i == tempStr.length() - 1)) {
                    return null;
                }
            } else if (currChar == '.') {
                dotCount += 1;
                if (dotCount > 1) {
                    return null;
                }
            } else if (currChar == 'e') {
                eCount += 1;
                if ((eCount > 1) || (i == 0) || (!Character.isDigit(tempStr.charAt(i - 1))) || (i == tempStr.length() - 1)) {
                    return null;
                }
            } else {
                tempStr = tempStr.substring(0, i) + tempStr.substring(i + 1);
                i--;
            }
        }

        if (dotCount == 0 && eCount == 0) {
            long result = strToLong(tempStr);

            if ((int) result == result) {
                return (int) result;
            } else {
                return result;
            }
        } else {
            return strToDouble(tempStr);
        }
    }
}

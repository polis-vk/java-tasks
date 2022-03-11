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
        //Make a normal form
        String curStr = str;
        byte counterE = 0;
        byte counterPoint = 0;
        byte counterDash = 0;
        for (int i = 0; i < curStr.length(); i++) {
            if (curStr.charAt(i) == 'e') {
                if (i != curStr.length() - 1) {
                    counterE++;
                } else {
                    return null;
                }
                if (counterE == 2) {
                    return null;
                }
            } else if (curStr.charAt(i) > '9' || curStr.charAt(i) < '-' || curStr.charAt(i) == '/') {
                curStr = curStr.substring(0, i) + curStr.substring(i + 1);
                i--;
            } else if (curStr.charAt(i) == '.') {
                counterPoint++;
                if (counterPoint == 2) {
                    return null;
                }
            } else if (curStr.charAt(i) == '-') {
                if (i != 0 && (curStr.charAt(i - 1) == '-' || curStr.charAt(i - 1) != 'e')) {
                    return null;
                }
                counterDash++;
                if (counterDash == 3) {
                    return null;
                }
            }
        }
        //For int or long
        if (counterPoint == 0 && counterE == 0) {
            long result = 0;
            for (int i = 0; i < curStr.length(); i++) {
                if (Character.isDigit(curStr.charAt(i))) {
                    result += (curStr.charAt(i) - 48) * Math.pow(10, curStr.length() - i - 1);
                }
            }
            if (counterDash == 1) {
                result *= -1;
                if (Math.abs(result) - 1 <= Integer.MAX_VALUE) {
                    return (int) result;
                }
                return result;
            }
            if (result <= Integer.MAX_VALUE) {
                return (int) result;
            }
            return result;
        }
        //For double
        if (counterPoint == 1 || counterE >= 1) {
            double result = 0;
            boolean isNumFractional = false;
            int numAfterPoint = 1;
            for (int i = 0; i < curStr.length(); i++) {
                if (curStr.charAt(i) == '.') {
                    result /= Math.pow(10, curStr.length() - i);
                    isNumFractional = true;
                } else if (Character.isDigit(curStr.charAt(i))) {
                    if (isNumFractional) {
                        result += (curStr.charAt(i) - 48) / Math.pow(10, numAfterPoint);
                        numAfterPoint++;
                    } else {
                        result += (curStr.charAt(i) - 48) * Math.pow(10, curStr.length() - i - 1);
                    }
                } else if (curStr.charAt(i) == 'e') {
                    if (!isNumFractional) {
                        result /= Math.pow(10, curStr.length() - i);
                    }
                    int degree = 0;
                    boolean isNegativeDegree = false;
                    if (curStr.charAt(i + 1) == '-') {
                        i++;
                        counterDash--;
                        isNegativeDegree = true;
                    }
                    for (int j = i + 1; j < curStr.length(); j++) {
                        if (Character.isDigit(curStr.charAt(j))) {
                            degree += (curStr.charAt(j) - 48) * Math.pow(10, curStr.length() - j - 1);
                        }
                    }
                    if (isNegativeDegree) {
                        result /= Math.pow(10, degree);
                    } else {
                        result *= Math.pow(10, degree);
                    }
                    break;
                }
            }
            if (counterDash == 1) {
                result *= -1;
            }
            if (result <= Double.MAX_VALUE) {
                return result;
            }
            return null;
        }
        return null;
    }
}

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
    public static Number valueOf(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        str = correctExpression(str);
        if (str == null) {
            return null;
        }
        return countExpression(str);
    }

    public static Number countExpression(String correctExpression) {
        int exponentIndex = correctExpression.indexOf('e');
        int pointIndex = correctExpression.indexOf('.');
        if (exponentIndex != -1 && pointIndex != -1) {
            return countExponentPointExpression(correctExpression, pointIndex, exponentIndex);
        }

        if (exponentIndex != -1) {
            return countExponentExpression(correctExpression, exponentIndex);
        }

        if (pointIndex != -1) {
            return countDoubleExpression(correctExpression, pointIndex);
        }
        return convertNonDoubleExpression(countNonDoubleExpression(correctExpression,
                correctExpression.length() - 1, 0));
    }

    public static Number countExponentPointExpression(String correctExpression, int pointIndex, int exponentIndex) {
        return (double) countDoubleExpression(correctExpression.substring(0, exponentIndex), pointIndex)
                * Math.pow(10, countNonDoubleExpression(correctExpression
                , correctExpression.length() - 1,
                exponentIndex + 1));
    }

    public static Number countDoubleExpression(String correctExpression, int pointIndex) {
        return countNonDoubleExpression(correctExpression, pointIndex - 1, 0)
                + countAfterPointExpression(correctExpression, pointIndex);
    }

    public static double countAfterPointExpression(String correctExpression, int pointIndex) {
        double exponent = 0.0;
        double rank = Math.pow(10, pointIndex - correctExpression.length() + 1);
        for (int i = correctExpression.length() - 1; i > pointIndex; i--, rank *= 10) {
            exponent += (correctExpression.charAt(i) - 48) * rank;
        }
        return exponent;
    }

    public static double countNonDoubleExpression(String correctExpression, int startIndex, int endIndex) {
        long number = 0;
        long rank = 1;
        for (int i = startIndex; i >= endIndex; i--, rank *= 10) {
            if (correctExpression.charAt(i) == '-') {
                number *= -1;
                continue;
            }
            number += (correctExpression.charAt(i) - 48) * rank;
        }

        return (double) number;
    }

    public static Number convertNonDoubleExpression(double expression) {
        long number = (long) expression;
        if (number <= Integer.MAX_VALUE && number >= Integer.MIN_VALUE) {
            return (int) number;
        }
        return number;
    }

    public static Number countExponentExpression(String correctExpression, int exponentIndex) {
        double tempExp = Math.pow(10, countNonDoubleExpression(correctExpression
                , correctExpression.length() - 1
                , exponentIndex + 1));
        return countNonDoubleExpression(correctExpression, exponentIndex - 1, 0)
                * tempExp;
    }

    //Возвращает Null если выражение содержит лишние знаки (.., -- и тд)
    public static String correctExpression(String str) {
        String newStr = str;
        int countPoints = 0;
        int countMinuses = 0;
        int countExponent = 0;
        char sign;
        for (int i = 0; i < newStr.length(); i++) {
            sign = newStr.charAt(i);
            switch (sign) {
                case '.':
                    if (++countPoints > 1) {
                        return null;
                    }
                    break;
                case '-':
                    if (++countMinuses > 2) {
                        return null;
                    }
                    if (i != 0 && (newStr.charAt(i - 1) == '-'
                            || newStr.charAt(i - 1) != 'e')) {
                        return null;
                    }
                    if (i == newStr.length() - 1) {
                        return null;
                    }
                    break;
                case 'e':
                    if (++countExponent > 1 || i == 0) {
                        return null;
                    }
                    if (newStr.charAt(i - 1) == '-') {
                        return null;
                    }
                    if (i == newStr.length() - 1) {
                        return null;
                    }
                    break;
                default:
                    if (!(Character.isDigit(sign))) {
                        newStr = newStr.substring(0, i).concat(newStr.substring(i + 1));
                        i--;
                    }
            }
        }
        return newStr;
    }

}

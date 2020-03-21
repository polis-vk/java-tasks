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
        int exponentIndex = findFirstIndexOfElement(correctExpression, 'e');
        int pointIndex = findFirstIndexOfElement(correctExpression, '.');
        if (exponentIndex != -1 && pointIndex != -1) {
            return countExponentPointExpression(correctExpression, pointIndex, exponentIndex);
        }

        if (exponentIndex != -1) {
            return countExponentExpression(correctExpression, exponentIndex);
        }

        if (pointIndex != -1) {
            return countDoubleExpression(correctExpression, pointIndex);
        }
        return countNonExponentExpression(correctExpression);
    }

    public static Number countExponentPointExpression(String correctExpression, int pointIndex, int exponentIndex) {
        return (double) countDoubleExpression(correctExpression.substring(0, exponentIndex), pointIndex)
                * countExponent(correctExpression.substring(exponentIndex), 0);
    }

    public static Number countDoubleExpression(String correctExpression, int pointIndex) {
        return countBeforePointExpression(correctExpression, pointIndex)
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

    public static int countBeforePointExpression(String correctExpression, int pointIndex) {
        int mantissa = 0;
        int rank = 1;
        for (int i = pointIndex - 1; i >= 0; i--, rank *= 10) {
            if (correctExpression.charAt(i) == '-') {
                mantissa *= -1;
                continue;
            }
            mantissa += (correctExpression.charAt(i) - 48) * rank;
        }
        return mantissa;
    }

    public static Number countNonExponentExpression(String correctExpression) {
        long number = 0;
        long rank = 1;
        for (int i = correctExpression.length() - 1; i >= 0; i--, rank *= 10) {
            if (correctExpression.charAt(i) == '-') {
                number *= -1;
                continue;
            }
            number += (correctExpression.charAt(i) - 48) * rank;
        }
        if (number <= Integer.MAX_VALUE && number >= Integer.MIN_VALUE) {
            return (int) number;
        }
        return number;
    }

    public static Number countExponentExpression(String correctExpression, int exponentIndex) {
        return (countMantissa(correctExpression, exponentIndex) * countExponent(correctExpression, exponentIndex));
    }

    public static int countMantissa(String correctExpression, int exponentIndex) {
        int mantissa = 0;
        int rank = 1;
        for (int i = exponentIndex - 1; i >= 0; i--, rank *= 10) {
            if (correctExpression.charAt(i) == '-') {
                mantissa *= -1;
                continue;
            }
            mantissa += (correctExpression.charAt(i) - 48) * rank;
        }
        return mantissa;
    }

    public static double countExponent(String correctExpression, int exponentIndex) {
        double exponent = 0.0;
        int rank = 1;
        for (int i = correctExpression.length() - 1; i > exponentIndex; i--, rank *= 10) {
            if (correctExpression.charAt(i) == '-') {
                exponent *= -1;
                continue;
            }
            exponent += (correctExpression.charAt(i) - 48) * rank;
        }
        return Math.pow(10, exponent);
    }

    public static int findFirstIndexOfElement(String str, char element) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == element) {
                return i;
            }
        }
        return -1;
    }

    //Возвращает Null если выражение содержит лишние знаки (.., -- и тд)
    public static String correctExpression(String str) {
        int countPoints = 0;
        int countMinuses = 0;
        int countExponent = 0;
        char sign;
        for (int i = 0; i < str.length(); i++) {
            sign = str.charAt(i);
            if (sign == '.') {
                if (++countPoints > 1) {
                    return null;
                }
            } else if (sign == '-') {
                if (++countMinuses > 2) {
                    return null;
                }
                if (i != 0 && (str.charAt(i - 1) == '-' || str.charAt(i - 1) != 'e')) {
                    return null;
                }
            } else if (sign == 'e') {
                if (++countExponent > 1) {
                    return null;
                }
                if (i != 0 && str.charAt(i - 1) == '-') {
                    return null;
                }
            } else if (!(Character.isDigit(sign))) {
                str = replaceSign(str, i);
                i--;
            }
        }
        return str;
    }

    public static String replaceSign(String str, int index) {
        StringBuilder newString = new StringBuilder();

        int i = 0;
        while (i < index) {
            newString.append(str.charAt(i));
            i++;
        }
        i++;
        while (i < str.length()) {
            newString.append(str.charAt(i));
            i++;
        }
        return newString.toString();
    }

}

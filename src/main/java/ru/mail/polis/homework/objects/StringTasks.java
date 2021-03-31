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
        if (str == null || str.isEmpty()) {
            return null;
        }

        String resultStr = str.replaceAll("[^0-9e.-]", "");

        boolean meetingDot = false;
        boolean meetingExp = false;
        boolean meetingDash = false;

        if (resultStr.charAt(resultStr.length() - 1) == 'e') {
            return null;
        }
        for (int i = 0; i < resultStr.length(); ++i) {
            switch (resultStr.charAt(i)) {
                case 'e':
                    if (meetingExp) {
                        return null;
                    }
                    meetingExp = true;
                    break;

                case '.':
                    if (meetingDot) {
                        return null;
                    }
                    meetingDot = true;
                    break;
            }

            if (resultStr.charAt(i) == '-') {
                if (meetingDash || (i != 0 && resultStr.charAt(i - 1) != 'e')) {
                    return null;
                }
                meetingDash = true;
            } else {
                meetingDash = false;
            }
        }

        if (meetingDot || meetingExp) {
            return parseDouble(resultStr);
        }

        long result = (long) parseIntegralNumber(resultStr);
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE)
        {
            return result;
        }
        return (int) result;
    }

    //Не придумал лучше названия, всё равно странно парсить целое число, а возвращать double
    //Как вариант BigInteger ¯\_(ツ)_/¯

    //Вы обещали не стукать и посмотреть на варианты прошлых лет
    private static double parseIntegralNumber(String str) {
        double result = 0.0;

        int i = 0;
        if (str.charAt(i) == '-') {
            ++i;
            result = Character.getNumericValue(str.charAt(i));
        } else {
            result = Character.getNumericValue(str.charAt(i));
        }
        ++i;

        for (; i < str.length(); ++i) {
            result = result * 10 + Character.getNumericValue(str.charAt(i));
        }

        return (str.charAt(0) == '-' ? -result : result);
    }

    private static double parseDouble(String str) {
        double result = 0.0;
        int dotIndex = str.indexOf('.');
        int startExpPartPos = 0;

        if (dotIndex != -1) {
            result = parseIntegralNumber(str.substring(0, dotIndex));
            startExpPartPos = dotIndex + 1;
        }

        String expPartStr = str.substring(startExpPartPos);
        int expIndex = expPartStr.indexOf('e');

        if (expIndex == -1) {
            return result + parseIntegralNumber(expPartStr) / Math.pow(10, expPartStr.length());
        }

        double temp = parseIntegralNumber(expPartStr.substring(0, expIndex));
        double expDegree = parseIntegralNumber(expPartStr.substring(expIndex + 1));

        if (dotIndex == -1) {
            return temp * Math.pow(10, expDegree);
        }

        return (result + temp / Math.pow(10, expIndex)) * Math.pow(10, expDegree);
    }
}

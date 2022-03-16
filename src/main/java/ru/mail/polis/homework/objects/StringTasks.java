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
        StringBuilder copy = new StringBuilder();
        char temp;
        boolean hasExp = false;
        boolean hasPoint = false;
        for (int i = 0; i < str.length(); i++) {
            temp = str.charAt(i);
            if (hasExp && temp == 'e' || hasPoint && temp == '.') {
                return null;
            }
            if (temp == 'e') {
                hasExp = true;
            }
            if (temp == '.') {
                hasPoint = true;
            }
            if (Character.isDigit(temp) || temp == 'e' || temp == '-' || temp == '.') {
                copy.append(temp);
            }
        }
        if (copy.length() == 0) {
            return null;
        }
        if (hasExp) {
            String[] numbers = copy.toString().split("e");
            if (numbers.length != 2 || !isCorrectFormat(numbers[0]) || !isCorrectFormat(numbers[1])) {
                return null;
            }
            return toNumber(numbers[0]).doubleValue() * Math.pow(10, toNumber(numbers[1]).doubleValue());
        }
        String copyToString = copy.toString();
        if (!isCorrectFormat(copyToString)) {
            return null;
        }
        if (hasPoint) {
            return toNumber(copyToString);
        }
        long numberLongValue = toNumber(copyToString).longValue();
        if ((int) numberLongValue != numberLongValue) {
            return numberLongValue;
        }
        return (int) numberLongValue;
    }

    private static Number toNumber(String str) {
        double result = 0;
        int tenPow = 0;
        int indexOfPoint = str.length();
        boolean hasPoint = false;
        boolean hasMinus = false;
        char temp;
        for (int i = str.length() - 1; i >= 0; i--) {
            temp = str.charAt(i);
            if (temp == '.') {
                indexOfPoint = i;
                hasPoint = true;
            } else if (temp == '-') {
                hasMinus = true;
            } else if (Character.isDigit(temp)) {
                result += (temp - '0') * Math.pow(10.0, tenPow);
                tenPow++;
            }
        }
        if (hasMinus) {
            result = -result;
        }
        if (hasPoint) {
            return result / Math.pow(10.0, str.length() - indexOfPoint - 1);
        }
        return result;
    }

    private static boolean isCorrectFormat(String number) {
        return !(number.contains("-") && (number.length() == 1 || number.lastIndexOf('-') != 0));
    }
}

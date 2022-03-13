package ru.mail.polis.homework.objects;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валидно
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
        String copy = "";
        char temp;
        boolean findExp = false;
        boolean findPoint = false;
        for (int i = 0; i < str.length(); i++) {
            temp = str.charAt(i);
            if ((findExp && temp == 'e') || (findPoint && temp == '.')) {
                return null;
            }
            if (temp == 'e') {
                findExp = true;
            }
            if (temp == '.') {
                findPoint = true;
            }
            if (Character.isDigit(temp) || temp == 'e' || temp == '-' || temp == '.') {
                copy = copy + temp;
            }
        }
        if (copy.isEmpty()) {
            return null;
        }
        if (findExp) {
            String[] numbers = copy.split("e");
            if (numbers.length != 2 || checkFormat(numbers[0]) || checkFormat(numbers[1])) {
                return null;
            }
            return toNumber(numbers[0]).doubleValue() * Math.pow(10, toNumber(numbers[1]).doubleValue());
        }
        if (checkFormat(copy)) {
            return null;
        }
        if (findPoint) {
            return toNumber(copy);
        }
        if (toNumber(copy).longValue() == toNumber(copy).intValue()) {
            return toNumber(copy).intValue();
        }
        return toNumber(copy).longValue();
    }

    private static Number toNumber(String str) {
        double result = 0;
        int tenPow = 0;
        int indexOfPoint = str.length();
        boolean findPoint = false;
        boolean findMinus = false;
        char temp;
        for (int i = str.length() - 1; i >= 0; i--) {
            temp = str.charAt(i);
            if (temp == '.') {
                indexOfPoint = i;
                findPoint = true;
            } else if (temp == '-') {
                findMinus = true;
            } else if (Character.isDigit(temp)) {
                result += (temp - '0') * Math.pow(10.0, tenPow);
                tenPow++;
            }
        }
        if (findMinus) {
            result = -result;
        }
        if (findPoint) {
            return result / Math.pow(10.0, str.length() - indexOfPoint - 1);
        }
        return result;
    }

    private static boolean checkFormat(String number) {
        return number.contains("-") && (number.length() == 1 || number.lastIndexOf('-') != 0);
    }
}

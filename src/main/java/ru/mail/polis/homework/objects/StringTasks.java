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
    static final String minus = "-";
    static final String point = ".";
    static final String e = "e";
    static final String MinusMinus = "--";
    static final String MinusE = "-e";
    static final String MinusPoint = "-.";

    public static Number valueOf(String str) {
        if (str == null || str == ""){
            return null;
        }
        int numPoint = str.length() - str.replace(minus, "").length();
        int numMinus = str.length() - str.replace(point, "").length();
        int numE = str.length() - str.replace(e, "").length();
        int doubleSeparators = numPoint + numE;
        StringBuilder digitStringBuilder = new StringBuilder();
        if (numPoint > 1 || numMinus > 1 || numE > 1 || doubleSeparators > 1) {
            return null;
        }
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c) || c == '-' || c == '.' || c == 'e') {
                digitStringBuilder.append(c);
            }
        }
        String digitString = digitStringBuilder.toString();
        if (digitString.length() - digitString.replace(MinusMinus, "").length() != 0
                || digitString.length() - digitString.replace(MinusE, "").length() != 0
                || digitString.length() - digitString.replace(MinusPoint, "").length() != 0
                || digitString.endsWith(minus)) {
            return null;
        }
        if (doubleSeparators == 0) {
            long longResult = Long.valueOf(digitString);
            if (longResult <= Integer.MAX_VALUE && longResult >= Integer.MIN_VALUE) {
                return Integer.valueOf(str);
            }
        }
        return Long.valueOf(str);
    }
}

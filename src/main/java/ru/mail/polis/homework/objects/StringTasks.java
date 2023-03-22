package ru.mail.polis.homework.objects;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валидино
     * Любой класс-обертка StringTasksTest над примитивами наследуется от Number
     * Можно использовать функции типа Double.valueOf()
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     * БЕЗ РЕГУЛЯРОК!
     * 6 тугриков
     */
    public static Number valueOf(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        boolean containsE = false;
        boolean containsDot = false;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char currSymbol = str.charAt(i);
            switch (currSymbol) {
                case '-':
                    if (i == str.length() - 1 || str.charAt(i + 1) == '-') {
                        return null;
                    }
                    if (result.length() != 0 && result.charAt(result.length() - 1) != 'e') {
                        return null;
                    }
                    result.append(currSymbol);
                    break;
                case '.':
                    if (containsDot) {
                        return null;
                    }
                    containsDot = true;
                    result.append(currSymbol);
                    break;
                case 'e':
                    if (containsE) {
                        return null;
                    }
                    if (result.length() != 0 && !Character.isDigit(result.charAt(result.length() - 1))) {
                        return null;
                    }
                    if (result.length() == 0 || i == str.length() - 1) {
                        return null;
                    }
                    containsE = true;
                    result.append(currSymbol);
                    break;
                default:
                    if (Character.isDigit(currSymbol)) {
                        result.append(currSymbol);
                    }
            }
        }
        if (containsE || containsDot) {
            return Double.parseDouble(result.toString());
        }
        long resultLong = Long.parseLong(result.toString());
        if (resultLong < Integer.MIN_VALUE || resultLong > Integer.MAX_VALUE) {
            return resultLong;
        }
        return Integer.parseInt(result.toString());
    }
}

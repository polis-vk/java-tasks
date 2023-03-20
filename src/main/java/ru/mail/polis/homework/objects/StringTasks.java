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
        if (str == null || str.equals("")) {
            return null;
        }

        if (str.length() == 1 & Character.isDigit(str.charAt(0))) {
            return Integer.parseInt(str);
        }

        StringBuilder clearStr = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {  // очистка строки от букв
            char symbol = str.charAt(i);
            if (Character.isDigit(symbol)
                    || (symbol == '.')
                    || (symbol == '-')
                    || (symbol == 'e')) {
                clearStr.append(symbol);
            }
        }

        if (clearStr.charAt(clearStr.length() - 1) == '-'
                || clearStr.charAt(clearStr.length() - 1) == 'e') {
            return null;
        }

        char prevPrevSymbol = ' ';
        char prevSymbol = ' ';
        byte exps = 0;
        byte points = 0;

        for (int i = 0; i < clearStr.length(); i++) {
            char symbol = clearStr.charAt(i);
            if (symbol == '-') {
                if (prevSymbol == '-') {  // проверка на "--"
                    return null;
                }

            }

            if (symbol == 'e') {
                if (prevSymbol == '-') {  // проверка на "-е"
                    return null;
                }
                exps++;
            }

            if (symbol == '.') {
                points++;
            }

            if (exps == 2 || points == 2) {
                return null;
            }


            if (i > 1) {
                if (Character.isDigit(prevPrevSymbol)
                        && prevSymbol == '-'
                        && Character.isDigit(symbol)) {  // проверка на "цифра-цифра"
                    return null;
                }
                prevPrevSymbol = clearStr.charAt(i - 1);
            }
            prevSymbol = symbol;
        }

        if (exps != 0 || points != 0) {
            return Double.parseDouble(String.valueOf(clearStr));
        }
        if (Long.parseLong(String.valueOf(clearStr)) > Integer.MAX_VALUE
                || Long.parseLong(String.valueOf(clearStr)) < Integer.MIN_VALUE) {
            return Long.parseLong(String.valueOf(clearStr));
        }
        return Integer.parseInt(String.valueOf(clearStr));
    }
}

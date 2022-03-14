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

        boolean existExp = false;
        boolean existDot = false;
        boolean existsFirstMinus = false;
        boolean existsSecondMinus = false;

        StringBuilder resultBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                resultBuilder.append(str.charAt(i));
            } else {
                if (str.charAt(i) == 'e') {
                    if (existExp) {
                        return null;
                    }
                    resultBuilder.append(str.charAt(i));
                    existExp = true;
                } else if (str.charAt(i) == '-') {
                    if (existExp) {
                        if (existsSecondMinus || resultBuilder.toString().indexOf('e') != resultBuilder.length() - 1) {
                            return null;
                        }
                        existsSecondMinus = true;
                    } else {
                        if (existsFirstMinus || resultBuilder.length() != 0) {
                            return null;
                        }
                        existsFirstMinus = true;
                    }
                    resultBuilder.append(str.charAt(i));
                } else if (str.charAt(i) == '.') {
                    if (existDot || existExp) {
                        return null;
                    }
                    existDot = true;
                    resultBuilder.append(str.charAt(i));
                }
            }
        }

        String resultString = resultBuilder.toString();
        if (resultString.indexOf('e') == resultString.length() - 1) {
            return null;
        }

        if (existDot || existExp) {
            return Double.parseDouble(resultString);
        } else {
            try {
                return Integer.parseInt(resultString);
            } catch (NumberFormatException e) {
                return Long.parseLong(resultString);
            }
        }
    }
}
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
        if (str == null) {
            return null;
        }
        if (str.equals("")) {
            return null;
        }

        StringBuilder newStr = new StringBuilder();

        int countE = 1;
        int countDots = 1;

        char[] arrayOfStr = str.toCharArray();
        for (int i = 0; i < arrayOfStr.length; i++) {
            if (!Character.isDigit(arrayOfStr[i])) {
                if (arrayOfStr[i] == 'e') {
                    if (countE > 1 || i == str.length() - 1) {
                        return null;
                    }
                    newStr.append(str.charAt(i));
                    countE++;
                    continue;
                } else if (arrayOfStr[i] == '.') {
                    if (countDots > 1) {
                        return null;
                    }
                    newStr.append(str.charAt(i));
                    countDots++;
                    continue;
                } else if (arrayOfStr[i] == '-') {
                    if (newStr.length() == 0 || newStr.charAt(newStr.length() - 1) == 'e') {
                        newStr.append(str.charAt(i));
                        continue;
                    }
                    return null;
                }
                continue;
            }
            newStr.append(str.charAt(i));
        }

        if (countE > 1 || countDots > 1) {
            return Double.valueOf(newStr.toString());
        } else if (Integer.MIN_VALUE > Long.valueOf(newStr.toString()) ||
                Long.valueOf(newStr.toString()) > Integer.MAX_VALUE) {
            return Long.valueOf(newStr.toString());
        }

        return Integer.valueOf(newStr.toString());
    }
}

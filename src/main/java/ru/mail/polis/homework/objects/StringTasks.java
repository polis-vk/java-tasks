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
        StringBuilder newStr = new StringBuilder();
        int dotCount = 0;
        int eCount = 0;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i)) || str.charAt(i) == '.' || str.charAt(i) == 'e' || str.charAt(i) == '-') {
                newStr.append(str.charAt(i));
            }
            if (str.charAt(i) == '.') {
                dotCount++;
            }
            if (str.charAt(i) == 'e') {
                eCount++;
            }
            if (dotCount > 1 || eCount > 1) {
                return null;
            }
        }
        char previousLetter = '.';
        int indexLastLetter = newStr.length() - 1;
        for (int i = 0; i < newStr.length(); i++) {
            char currentLetter = newStr.charAt(i);
            if (previousLetter == '-' && (previousLetter == currentLetter || currentLetter == 'e')) {
                return null;
            }
            if (Character.isDigit(previousLetter) && currentLetter == '-') {
                return null;
            }
            if (newStr.charAt(indexLastLetter) == 'e') {
                return null;
            }
            previousLetter = currentLetter;
        }
        if (dotCount > 0 || eCount > 0) {
            return Double.parseDouble(newStr.toString());
        }
        long num = Long.parseLong(newStr.toString());
        if (num <= Integer.MAX_VALUE && num >= Integer.MIN_VALUE) {
            return Integer.parseInt(newStr.toString());
        }
        return num;
    }
}

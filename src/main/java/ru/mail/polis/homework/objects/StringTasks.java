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
        StringBuilder myStr = new StringBuilder();
        boolean isEpsPresent = false;
        boolean isPointPresent = false;
        int posOfLastChar;
        char lastMyStrChar = 0;
        boolean isLastMyStrCharDigit = false;
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            posOfLastChar = myStr.length() - 1;
            if (posOfLastChar >= 0) {
                lastMyStrChar = myStr.charAt(posOfLastChar);
                isLastMyStrCharDigit = Character.isDigit(myStr.charAt(posOfLastChar));
            }
            if (Character.isDigit(currentChar)) {
                myStr.append(currentChar);
            } else if (currentChar == 'e') {
                if (myStr.length() == 0) {
                    return null;
                }
                else if (isLastMyStrCharDigit && !isEpsPresent) {
                    myStr.append(str.charAt(i));
                    isEpsPresent = true;
                }
            } else if (currentChar == '-') {
                if (myStr.length() == 0 || lastMyStrChar == 'e') {
                    myStr.append(currentChar);
                } else if (lastMyStrChar == '-' || isLastMyStrCharDigit || lastMyStrChar == '.') {
                    return null;
                }
            } else if (currentChar == '.') {
                if (!isPointPresent && isLastMyStrCharDigit) {
                    myStr.append(currentChar);
                    isPointPresent = true;
                } else {
                    return null;
                }
            }
        }
        if (isPointPresent || isEpsPresent) {
            if (!Character.isDigit(myStr.charAt(myStr.length() - 1))) {
                return null;
            }
            return Double.parseDouble(myStr.toString());
        }
        long longValue = Long.parseLong(myStr.toString());
        if (longValue <= Integer.MAX_VALUE && longValue >= Integer.MIN_VALUE) {
            return (int) longValue;
        }
        return longValue;
    }
}

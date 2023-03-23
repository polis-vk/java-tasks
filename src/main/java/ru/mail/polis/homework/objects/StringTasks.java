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
        String newStr = "";
        int digetCount = 0;
        int dotCount = 0;
        int eCount = 0;
        int afterECount = 0;
        int minusCount = 0;
        for (int i = 0; i < str.length(); i++) {
            char strNumber = str.charAt(i);
            if (strNumber == '.' || strNumber == '-' || strNumber == 'e' || Character.isDigit(strNumber)) {
                switch (strNumber) {
                    case '.':
                        dotCount++;
                        break;
                    case '-':
                        minusCount++;
                        if (i != 0 & digetCount > 0) {
                            if (eCount == 0) {
                                return null;
                            }
                        }
                        if (eCount == 1) {
                            afterECount++;
                            if (digetCount > 0) {
                                return null;
                            }
                        }
                        break;
                    case 'e':
                        if (digetCount == 0) {
                            return null;
                        }
                        digetCount = 0;
                        eCount++;
                        break;
                    default:
                        digetCount++;
                }
                if (dotCount > 1 || (minusCount > 1 & eCount == 0) || eCount > 1 || afterECount > 1) {
                    return null;
                }
                newStr += strNumber;
            }
        }
        if (digetCount == 0) {
            return null;
        }
        if (newStr.contains(".") || newStr.contains("e")) {
            return Double.valueOf(newStr);
        }
        long newStrLongParse = Long.parseLong(newStr);
        if (newStrLongParse > Integer.MAX_VALUE || newStrLongParse < Integer.MIN_VALUE) {
            return newStrLongParse;
        }
        return Integer.valueOf(newStr);
    }
}
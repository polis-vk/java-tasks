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
        if (str == null || str.length() == 0) {
            return null;
        }
        boolean dotIsExist = false;
        boolean eIsExist = false;
        StringBuilder result = new StringBuilder();
        String strTemp = str;
        for (int i = 0; i < strTemp.length(); ++i) {
            if (Character.isDigit(strTemp.charAt(i))) {
                result.append(strTemp.charAt(i));
            } else switch (strTemp.charAt(i)) {
                case '.':
                    if (dotIsExist) {
                        return null;
                    }
                    dotIsExist = true;
                    if (!Character.isDigit(result.charAt(result.length() - 1))) {
                        return null;
                    }
                    result.append(".");
                    break;
                case '-':
                    if (result.length() == 0 || result.charAt(result.length() - 1) == 'e') {
                        result.append('-');
                    } else {
                        return null;
                    }
                    break;
                case 'e':
                    if (eIsExist) {
                        return null;
                    }
                    eIsExist = true;
                    if (Character.isDigit(result.charAt(result.length() - 1))) {
                        result.append('e');
                    } else {
                        return null;
                    }
                    break;
            }
        }
        String strResult = result.toString();
        if (!Character.isDigit(strResult.charAt(strResult.length() - 1))) {
            return null;
        }
        if (dotIsExist || eIsExist) {
            return Double.valueOf(strResult);
        }
        if (Double.parseDouble(strResult) > Integer.MAX_VALUE || Double.parseDouble(strResult) < Integer.MIN_VALUE) {
            return Long.valueOf(result.toString());
        }
        return Integer.valueOf(strResult);
    }
}
























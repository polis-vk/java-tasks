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
        StringBuilder futureNum = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'e' || str.charAt(i) == '.' || str.charAt(i) == '-'
                    || Character.isDigit(str.charAt(i))) {
                futureNum.append(str.charAt(i));
            }
        }
        int exponentQtt = 0;
        int dotQtt = 0;
        char prevCharacter = 'a';
        char prevPrevCharacter = 'e';
        if (futureNum.charAt(futureNum.length() - 1) == 'e'
                || futureNum.charAt(futureNum.length() - 1) == '-'
                || futureNum.charAt(futureNum.length() - 1) == '.') {
            return null;
        }
        for (int j = 0; j < futureNum.length(); j++) {
            char futureNumChar = futureNum.charAt(j);
            if (j > 0) {
                prevCharacter = futureNum.charAt(j - 1);
            }
            if (j > 1) {
                prevPrevCharacter = futureNum.charAt(j - 2);
            }
            if (prevPrevCharacter != 'e' & prevCharacter == '-' & Character.isDigit(futureNumChar)) {
                return null;
            }
            if (prevCharacter == '-' && futureNumChar == prevCharacter) {
                return null;
            }
            if (prevCharacter == '-' && futureNumChar == 'e') {
                return null;
            }
            if (futureNumChar == 'e') {
                exponentQtt += 1;
                if (exponentQtt > 1) {
                    return null;
                }
            }
            if (futureNumChar == '.') {
                dotQtt += 1;
                if (dotQtt > 1) {
                    return null;
                }
            }
        }
        if (exponentQtt != 0 || dotQtt != 0) {
            return Double.parseDouble(String.valueOf(futureNum));
        } else if (Integer.MAX_VALUE < Long.parseLong(String.valueOf(futureNum))
                || Integer.MIN_VALUE > Long.parseLong(String.valueOf(futureNum))) {
            return Long.parseLong(String.valueOf(futureNum));
        }
        return Integer.parseInt(String.valueOf(futureNum));
    }
}

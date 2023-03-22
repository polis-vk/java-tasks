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
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'e' || str.charAt(i) == '.' || str.charAt(i) == '-'
                    || Character.isDigit(str.charAt(i))) {
                number.append(str.charAt(i));
            }
        }
        int exponentQtt = 0;
        int dotQtt = 0;
        char prevCharacter = 'a';
        char prevPrevCharacter = 'e';
        if (number.charAt(number.length() - 1) == 'e'
                || number.charAt(number.length() - 1) == '-'
                || number.charAt(number.length() - 1) == '.') {
            return null;
        }
        if (number.charAt(0) == 'e' || number.charAt(0) == '.'){
            return null;
        }
        for (int j = 0; j < number.length(); j++) {
            char currentNumChar = number.charAt(j);
            if (j > 0) {
                prevCharacter = number.charAt(j - 1);
            }
            if (j > 1) {
                prevPrevCharacter = number.charAt(j - 2);
            }
            if (prevPrevCharacter != 'e' && prevCharacter == '-' && Character.isDigit(currentNumChar)) {
                return null;
            }
            if (prevCharacter == '-' && currentNumChar == prevCharacter) {
                return null;
            }
            if (prevCharacter == '-' && currentNumChar == 'e') {
                return null;
            }
            if (currentNumChar == 'e') {
                exponentQtt += 1;
                if (exponentQtt > 1) {
                    return null;
                }
            }
            if (currentNumChar == '.') {
                dotQtt += 1;
                if (dotQtt > 1) {
                    return null;
                }
            }
        }
        if (exponentQtt != 0 || dotQtt != 0) {
            return Double.parseDouble(String.valueOf(number));
        } else {
            long futureNumLong = Long.parseLong(String.valueOf(number));
            if (Integer.MAX_VALUE < futureNumLong
                    || Integer.MIN_VALUE > futureNumLong) {
                return futureNumLong;
            }
        }
        return Integer.parseInt(String.valueOf(number));
    }
}

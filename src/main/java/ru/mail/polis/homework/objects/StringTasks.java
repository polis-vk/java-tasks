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
        StringBuilder filteredStr = new StringBuilder();
        int dotCount = 0;
        int eCount = 0;
        int indexE = Integer.MAX_VALUE;
        int indexDot = 0;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i)) || str.charAt(i) == '.' || str.charAt(i) == 'e' || str.charAt(i) == '-') {
                filteredStr.append(str.charAt(i));
            }
            if (str.charAt(i) == 'e') {
                eCount++;
                indexE = i;
            }
            if (str.charAt(i) == '.') {
                dotCount++;
                indexDot = i;
            }
            if (indexE < indexDot) {
                return null;
            }
            if (dotCount > 1 || eCount > 1) {
                return null;
            }
        }
        char firstSymbol = filteredStr.charAt(0);
        char lastSymbol = filteredStr.charAt(filteredStr.length() - 1);
        if (firstSymbol == 'e' || firstSymbol == '.' || lastSymbol == 'e' || lastSymbol == '-') {
            return null;
        }
        char previousLetter = filteredStr.charAt(0);
        for (int i = 1; i < filteredStr.length(); i++) {
            char currentLetter = filteredStr.charAt(i);
            if (previousLetter == '-' && (previousLetter == currentLetter || currentLetter == 'e')) {
                return null;
            }
            if (Character.isDigit(previousLetter) && currentLetter == '-') {
                return null;
            }
            previousLetter = currentLetter;
        }
        if (dotCount > 0 || eCount > 0) {
            return Double.parseDouble(filteredStr.toString());
        }
        long num = Long.parseLong(filteredStr.toString());
        if (num <= Integer.MAX_VALUE && num >= Integer.MIN_VALUE) {
            return (int) num;
        }
        return num;
    }
}

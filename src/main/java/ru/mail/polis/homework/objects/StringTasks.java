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

        StringBuilder sb = new StringBuilder();
        int dotCount = 0;
        int eCount = 0;
        boolean lastCharWasE = false;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (Character.isDigit(c)) {
                sb.append(c);
                lastCharWasE = false;
            } else if (c == '.') {
                dotCount++;
                if (dotCount > 1) {
                    return null;
                }
                sb.append(c);
                lastCharWasE = false;
            } else if (c == 'e' || c == 'E') {
                eCount++;
                if (eCount > 1) {
                    return null;
                }
                sb.append('e');
                lastCharWasE = true;
            } else if (c == '-') {
                if (sb.length() > 0 && !lastCharWasE) {
                    return null;
                }
                sb.append(c);
                lastCharWasE = false;
            }
        }
        try {
            Double result = Double.valueOf(sb.toString());
            if(eCount != 0) {
                return result;
            }
            if(result == result.intValue() ){
                return result.intValue();
            }
            if(result > Integer.MAX_VALUE || result < Integer.MIN_VALUE){
                return result.longValue();
            }
            return result;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
